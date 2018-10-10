/*!*
 * 最长清欠期限配置 JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pageLoan_maxduration_config.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 9,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deleteLoan_maxduration_config.do"
	};

	var work = new $.managerWork("loan_MaxDuration_Config", pageOption,
			deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(function() {
		var entityId = $("#entityId").val();
		var loanType = $("#loanType").val();
		var repaymentType = $("#repaymentType").val();
		var maxDuration = $("#maxDuration").val();
		// 保存参数
		var saveOption = {
			url : "loan_naxduration_config_new.do",
			data : {
				"id" : entityId,
				"loanType" : loanType,
				"repaymentType" : repaymentType,
				"maxDuration" : maxDuration
			},
			href : "/loanSys/loan_maxduration_config.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return validate(loanType, repaymentType, maxDuration);
		});

	});

	/**
	 * 验证
	 */
	function validate(loanType, repaymentType, maxDuration) {
		if (loanType == "" || loanType.trim().length == 0) {
			layer.msg("【借款类型】不能为空！");
			return false;
		}
		if (repaymentType == "" || repaymentType.trim().length == 0) {
			layer.msg("【还款类型】不能为空！");
			return false;
		}
		if (repaymentType != "提交日期当年最后一天"
				&& (maxDuration == "" || maxDuration.trim().length == 0)) {
			layer.msg("【还款类型】非\"提交日期当年最后一天\"时，【最长清欠期限】不能为空！");
			return false;
		}
		if(loanType == "押金、质保金类" && repaymentType != "指定日期加N天"){
			layer.msg("【押金、质保金类】只能选择【指定日期加N天】！");
			return false;
		}
		if(loanType != "押金、质保金类" && repaymentType == "指定日期加N天"){
			layer.msg("非【押金、质保金类】不能选择【指定日期加N天】！");
			return false;
		}
		return true;
	}

});