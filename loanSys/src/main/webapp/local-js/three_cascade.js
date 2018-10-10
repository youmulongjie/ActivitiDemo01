/*！
 * 三级级联 JS 
 * 
 * <pre>
 * 调用方法：
 * var cascade_option = {
		"id1" : "replaceDept",
		"id2" : "replaceBusiness",
		"id3" : "replaceLine",
		"dataUrl1" : "getShiYeBuMap.do",
		"dataUrl2" : "getReplaceBusinessOrLine.do",
		"dataUrl3" : "getReplaceBusinessOrLine.do",
	};
	
	// 新增初始化（-1 表示新增）
	new $.three_cascade(cascade_option).init(-1, -1, -1, {});
	
	// 回显初始化（12,13,14 分别为 回显对应的值）
	new $.three_cascade(cascade_option).init(12, 13, 14, {});
	
 * </pre>
 */
(function($) {
	/**
	 * 加载列表 方法
	 * 
	 * @param id
	 *            三层中select 元素ID
	 * @param message
	 *            数据源对象，引用{@link com.changhongit.loan.bean.Message}，此时 data
	 *            属性统一为 map对象
	 * @param cascade1Value
	 *            回显值（选中值）
	 * @returns
	 */
	loadOptionFun = function(id, message, cascadeValue) {
		// 先清空option选项
		$("#" + id).empty();

		// 默认的第一个option的value值
		var param_cascadeValue = null;
		if (message.code == "success" && message.data.length != 0) {
			var data = message.data;
			var index = 0;
			// 循环map，加载option选项
			$.each(data, function(key, value) {
				if (index == 0) {
					// 获取 第一个option的value值
					param_cascadeValue = key;
				}
				var selectOption;
				if (cascadeValue == value) {
					param_cascadeValue = cascadeValue;
					selectOption = "<option value=\"" + value
							+ "\" selected=\"selected\">" + key + "</option>";
				} else {
					selectOption = "<option value=\"" + value + "\" >" + key
							+ "</option>";
				}
				$("#" + id).append(selectOption);

				index++;
			});
			return param_cascadeValue;
		} else if (message.code == "success" && message.data.length == 0) {
			layer.msg("没有找到【事业部】组织！");
			return "";
		}
	};

	/**
	 * 三级级联对象
	 */
	$.three_cascade = function(option) {
		var method = {
			/**
			 * 初始化 方法
			 */
			initData : function(cascade1Value, cascade2Value, cascade3Value) {
				var dataUrl1 = option.dataUrl1;
				var dataUrl2 = option.dataUrl2;
				var dataUrl3 = option.dataUrl3;

				// 开启 同步，默认是异步
				$.ajaxSettings.async = false;

				// 加载 第一级别 列表
				var param_cascade1Value = "";
				$.post(dataUrl1, {}, function(message) {
					param_cascade1Value = loadOptionFun(option.id1, message,
							cascade1Value);
				}, "json");

				// 加载 第二级别 列表
				var param_cascade2Value = "";
				$.post(dataUrl2, {
					"param" : param_cascade1Value
				}, function(message) {
					param_cascade2Value = loadOptionFun(option.id2, message,
							cascade2Value);
				}, "json");

				// 加载 第三级别 列表
				$.post(dataUrl3, {
					"param" : param_cascade2Value
				}, function(message) {
					loadOptionFun(option.id3, message, cascade3Value);
				}, "json");

				// 关闭 同步
				$.ajaxSettings.async = true;
			},

			/**
			 * 1级列表 change事件
			 */
			cascade1Change : function() {
				var dataUrl2 = option.dataUrl2;
				var dataUrl3 = option.dataUrl3;
				$("#" + option.id1).change(
						function() {
							var cascade1Value = $(
									"#" + option.id1 + " option:selected")
									.val();

							// 开启 同步，默认是异步
							$.ajaxSettings.async = false;

							// 加载 第二级别 列表
							var param_cascade2Value = "";
							$.post(dataUrl2, {
								"param" : cascade1Value
							}, function(message) {
								param_cascade2Value = loadOptionFun(option.id2,
										message, -1);
							}, "json");

							// 加载 第三级别 列表
							$.post(dataUrl3, {
								"param" : param_cascade2Value
							}, function(message) {
								loadOptionFun(option.id3, message, -1);
							}, "json");

							// 关闭 同步
							$.ajaxSettings.async = true;
						});
			},

			/**
			 * 2级列表 change事件
			 */
			cascade2Change : function() {
				var dataUrl3 = option.dataUrl3;
				$("#" + option.id2).change(
						function() {
							var cascade2Value = $(
									"#" + option.id2 + " option:selected")
									.val();
							// 加载 第三级别 列表
							$.post(dataUrl3, {
								"param" : cascade2Value
							}, function(message) {
								loadOptionFun(option.id3, message, -1);
							}, "json");
						});
			}
		};

		/**
		 * 初始化 三级列表方法
		 * 
		 * @param cascade1Value
		 *            第1级别初始化Value值
		 * @param cascade2Value
		 *            第2级别初始化Value值
		 * @param cascade3Value
		 *            第3级别初始化Value值
		 */
		this.init = function(cascade1Value, cascade2Value, cascade3Value) {
			method.initData(cascade1Value, cascade2Value, cascade3Value);
			method.cascade1Change();
			method.cascade2Change();
		};
	};

})(jQuery);