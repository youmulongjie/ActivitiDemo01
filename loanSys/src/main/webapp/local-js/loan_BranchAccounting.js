/**
 * 分公司会计配置 JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pageLoan_BranchAccounting.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 8,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deleteLoan_BranchAccounting.do"
	};

	var work = new $.managerWork("branchAccounting", pageOption, deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(function() {
		var entityId = $("#entityId").val();
		var branch = $("#branch").val();
		var accounting = $("#accounting").val();
		// 保存参数
		var saveOption = {
			url : "loan_BranchAccounting_new.do",
			data : {
				"id" : entityId,
				"branch" : branch,
				"accounting" : accounting
			},
			href : "/loanSys/branchAccounting.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return validate(branch, accounting);
		});

	});

	/**
	 * 验证
	 */
	function validate(branch, accounting) {
		if (branch == "" || branch.trim().length == 0) {
			layer.msg("【分公司名称】不能为空！");
			return false;
		}
		if (accounting == "" || accounting.trim().length == 0) {
			layer.msg("【分公司会计】不能为空！");
			return false;
		}
		return true;
	}

});