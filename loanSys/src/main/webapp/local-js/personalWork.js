/*!
 * 业务管理工作区 managerWork JS
 */
(function($) {
	// 左边菜单栏 展示“个人工作区”，并打开状态
	$("#personalWork").addClass("active");
	$('#personalWorkOpen').addClass('collapse in');

	/**
	 * 创建 managerWork 对象
	 * 
	 * @param el
	 *            “业务管理工作区”列表中，选择中菜单项ID
	 * @param pageOption
	 *            分页查询 参数
	 * @param deleteOption
	 *            删除操作 参数
	 */
	$.personalWork = function(el, pageOption, deleteOption) {
		// 激活该菜单项（即选中）
		$("#" + el).addClass("active");

		// 是否分页标识（页面隐藏域，列表展示页面分页，新增修改页面不分页）
		var isShowPage = $("#isShowPage").val();
		if (isShowPage == "true" || isShowPage == true) {
			page(pageOption);
		}

		// 默认删除方法参数
		$.defaultDeleteOption = {
			// 选择的 checkbox
			checkboxData : ".data :checkbox",
			url : ""
		};
		var deleteVars = $.extend({}, $.defaultDeleteOption, deleteOption);

		// 默认 保存、更新方法参数
		$.defaultSaveOption = {};
		var saveVars = {};

		// 定义内部方法
		var method = {
			// 设置 保存参数的参数
			setSaveOption : function(saveOption) {
				saveVars = $.extend({}, $.defaultSaveOption, saveOption);
				return this;
			},
			// 删除操作
			deleteEntity : function() {
				var arr = new Array();
				$("" + deleteVars.checkboxData).each(function() {
					var $this = $(this);
					if ($this.prop("checked")) {
						arr.push($this.val());
					}
				});
				if (arr.length == 0) {
					layer.msg("删除所选项为空！");
					return;
				} else {
					new $.confirm("是否确定删除？", function() {
						var ids = arr.join(",");
						$.ajax({
							type : "POST",
							dataType : "json",
							data : {
								"ids" : ids
							},
							url : deleteVars.url,
							success : function(data) {
								if (data.code == "success") {
									layer.alert(data.result, 1);
									location.reload();
								} else {
									layer.msg(data.result);
									return;
								}
							},
							error : function(textStatus) {
								console.error(textStatus);
							},
							complete : function(XMLHttpRequest, status) {
								XMLHttpRequest = null;
							}
						});
					});

				}
			},
			// 保存（新增和更新）操作
			saveOrUpdateEntity : function(validate) {
				if (!validate()) {
					return;
				}
				new $.confirm("是否确定保存操作？", function() {
					$.ajax({
						type : "POST",
						dataType : "json",
						data : saveVars.data,
						url : saveVars.url,
						success : function(data) {
							if (data.code == "success") {
								layer.alert(data.result, 1);
								window.location.href = saveVars.href;
							} else {
								layer.msg(data.result);
								return;
							}
						},
						error : function(textStatus) {
							console.error(textStatus);
						},
						complete : function(XMLHttpRequest, status) {
							XMLHttpRequest = null;
						}
					});
				});
			}
		};

		// 定义外部方法，共调用
		// 删除
		this.deleteEntity = function() {
			method.deleteEntity();
		};
		// 保存
		this.saveOrUpdateEntity = function(validate) {
			method.saveOrUpdateEntity(validate);
		};
		// 保存参数（该方法 用在 saveOrUpdateEntity 之前）
		this.setSaveOption = function(saveOption) {
			method.setSaveOption(saveOption);
			return this;
		};

	};
})(jQuery);
