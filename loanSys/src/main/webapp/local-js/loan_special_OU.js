/**
 * 专用OU JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pageLoan_special_OU.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 5,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deleteLoan_special_OU.do"
	};

	var work = new $.managerWork("loan_special_OU", pageOption, deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(function() {
		// 保存参数
		var saveOption = {
			url : "loan_special_ou_new.do",
			data : {
				"ou" : $("#newOU").val(),
			},
			href : "/loanSys/loan_special_OU.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return true;
		});
	});

});