/**
 * 平台部长配置 JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pagePlatformMinister.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 8,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deletePlatformMinister.do"
	};

	var work = new $.managerWork("platformMinister", pageOption, deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(function() {
		var entityId = $("#entityId").val();
		var platformName = $.trim($("#platformName").val());
		var ministerName = $.trim($("#ministerName").val());
		// 保存参数
		var saveOption = {
			url : "platformMinister_new.do",
			data : {
				"id" : entityId,
				"platformName" : platformName,
				"ministerName" : ministerName
			},
			href : "/loanSys/platformMinister.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return validate(platformName, ministerName);
		});

	});

	/**
	 * 验证
	 */
	function validate(platformName, ministerName) {
		if (platformName == "" || platformName.trim().length == 0) {
			layer.msg("【平台部门名称】不能为空！");
			return false;
		}
		if (ministerName == "" || ministerName.trim().length == 0) {
			layer.msg("【平台部长名称】不能为空！");
			return false;
		}
		return true;
	}

});