/**
 * 系统管理员配置 JS
 */
$(function() {
	// 分页参数
	var pageOption = {
		"url" : "pageLoan_sysAdmin.do",// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 7,// 行列数（没有数据时合并列使用）
	};

	// 删除参数
	var deleteOption = {
		url : "deleteLoan_sysAdmin.do"
	};

	var work = new $.managerWork("loan_sys_admin", pageOption, deleteOption);

	// 删除按钮 事件
	$(".delete_draft").click(function() {
		work.deleteEntity();
	});

	/**
	 * 保存按钮 事件
	 */
	$(".save2").click(function() {
		var entityId = $("#entityId").val();
		var username = $("#username").val();
		// 保存参数
		var saveOption = {
			url : "loan_sys_admin_new.do",
			data : {
				"id" : entityId,
				"username" : username
			},
			href : "/loanSys/loan_sys_admin.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return validate(username);
		});

	});

	/**
	 * 验证
	 */
	function validate(username) {
		if (username == "" || username.trim().length == 0) {
			layer.msg("【系统管理员名称】不能为空！");
			return false;
		}
		return true;
	}

});