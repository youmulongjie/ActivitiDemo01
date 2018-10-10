/**
 * 借款单对应发票类型 JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pageLoanToAccounting.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 8,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deleteLoanToAccounting.do"
	};

	var work = new $.managerWork("loan_LoanToAccounting", pageOption,
			deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(
			function() {
				var entityId = $("#entityId").val(), loanType = $("#loanType")
						.val(), accounting = $("#accounting").val();
				// 保存参数
				var saveOption = {
					url : "loanToAccounting_new.do",
					data : {
						"id" : entityId,
						"loanType" : loanType,
						"accounting" : accounting
					},
					href : "/loanSys/loanToAccounting.do",
				};
				work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
					return validate(loanType, accounting);
				});
			});

	/**
	 * 验证（新增和修改时）
	 */
	function validate(loanType, accounting) {
		if (loanType == "" || loanType.trim().length == 0) {
			layer.msg("【借款类型】不能为空！");
			return false;
		}
		if (accounting == "" || accounting.trim().length == 0) {
			layer.msg("【对应会计】不能为空！");
			return false;
		}
		return true;
	}
});
