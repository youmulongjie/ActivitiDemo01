/**
 * processList 页面JS
 */
$(function() {
	// 申请日期 时间范围控件
	$('#data_5 .input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true,// 选中之后自动隐藏日期选择框
		clearBtn : true,// 清除按钮
		format : "yyyy-mm-dd"
	});

	// 来源标识（流程结束：end|待办事宜：todo|审批中：progress|草稿箱：draft|管理员查看申请单：manage）
	var flag = $("#flag").val();

	// 查询分页数据的URL地址
	var url = "";
	// 业务管理工作区 列表中，选择中菜单项ID
	var itemId = "";
	if (flag == "todo") {
		url = "pageToDo.do";
		itemId = "todo";
	} else if (flag == "progress") {
		url = "pageProgress.do";
		itemId = "processProgress";
	} else if (flag == "end") {
		url = "pageEnd.do";
		itemId = "processEnd";
	} else if (flag == "draft") {
		url = "pageDraft.do";
		itemId = "draft";
	} else if (flag == "manage") {
		url = "pageManage.do";
		itemId = "processManage";
	} else {
		alert("设置有误，找不到有效标识");
		return;
	}

	// 分页参数
	var pageOption = {
		"url" : url,// URL 地址
		"param" : {
			"page" : 1,
			"rows" : 10
		},// 当前页数，每页展示数量（有其他参数自行添加）
		"th_length" : 8,// 行列数（没有数据时合并列使用）
	};

	// 初始化加载页面数据
	new $.personalWork(itemId, pageOption, {});

	/**
	 * 查询按钮 事件
	 */
	$("#queryBtn").click(function() {
		// 序列化表单元素，返回json数据
		var params = $("#form").serializeArray();
		// 添加 分页参数
		params.push({
			"name" : "page",
			"value" : 1
		});
		params.push({
			"name" : "rows",
			"value" : 10
		});

		// 分页参数
		var pageOption = {
			"url" : url,// URL 地址
			"param" : params,// 当前页数，每页展示数量，页面查询条件（有其他参数自行添加）
			"th_length" : 8,// 行列数（没有数据时合并列使用）
		};

		new $.personalWork(itemId, pageOption, {});
	});

	$("#invoiceStatus,#currentWork,#loantype,#procStatus").change(function() {
		$("#queryBtn").click();
	});
});