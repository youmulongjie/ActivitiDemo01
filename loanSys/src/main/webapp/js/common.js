$(function() {
	/**
	 * 1、设置 待办个数
	 */
	$.ajax({
		type : "post", // get请求 浏览器会缓存，数据不正确
		url : "todoCount.do",
		dataType : "json",
		success : function(count) {
			$("#todoCount").html(count);
		}
	});

	/**
	 * 2、封装 提示信息，并设置回调
	 * 
	 * @param msg
	 *            提示信息
	 * @param callback
	 *            点击确定时 回调函数
	 */
	$.confirm = function(msg, callback) {
		$.layer({
			title : "提示信息",
			dialog : {
				msg : msg,
				type : -1,
				btns : 2,
				btn : [ '确定', '取消' ],
				yes : callback,
			}
		});
	};
});