/**
 * 创建申请 JS
 */
$(function() {
	new $.personalWork("processCreate", {}, {});

	/**
	 * 本部门借款 “是” 点击事件<br>
	 * 隐藏 借款所属部门、借款所属业务部、借款所属产品线
	 */
	$("#departmentLoan_yes").click(function() {
		$("#self").hide();
	});

	/**
	 * 本部门借款 “否” 点击事件<br>
	 * 显示 借款所属部门、借款所属业务部、借款所属产品线，并初始化三级级联
	 */
	$("#departmentLoan_no").click(function() {
		$("#self").show();
		// 事业部、业务部、产品线 三级级联对象 参数
		var cascade_option = {
			"id1" : "replaceDept", // 第一级 select 元素ID
			"id2" : "replaceBusiness", // 第二级 select 元素ID
			"id3" : "replaceLine", // 第三级 select 元素ID
			"dataUrl1" : "getShiYeBuMap.do", // 第一级 获取数据源 URL地址
			"dataUrl2" : "getReplaceBusiness.do", // 第二级 获取数据源 URL地址
			"dataUrl3" : "getReplaceLine.do",// 第三级 获取数据源 URL地址
		};
		new $.three_cascade(cascade_option).init(-1, -1, -1, {});
	});

	/**
	 * 借款人职位 change规则：<br>
	 * 1：页面上“所属部门”级联改变；<br>
	 * 2：后台需获取的“领导链”级联改变（后台操作，页面不影响）
	 */
	$("#position").change(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "changePosition.do",
			data : {
				"erpNum" : $("#erpNum").val(),
				"position" : $(this).val(),
			},
			success : function(message) {
				if (message.code == "success") {
					// 成功，更改所在部门
					$("#span_upDept").empty();
					$("#span_upDept").html(message.data.upDept);
					$("#upDept").val(message.data.upDept);

					$("#span_dept").empty();
					$("#span_dept").html(message.data.dept);
					$("#dept").val(message.data.dept);

					$("#assignNum").val(message.data.assignNum);
				} else {
					layer.msg(message.result);
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

	/**
	 * 借款类型 change规则： <br>
	 * 1：“小额工程采购、研发、其他" 会出现单项选择“是否有合同或预算”，其余的借款类型无此项选择<br>
	 * 2："押金、质保金类" 含有 "预计还款日项"，其余的借款类型无此项选择<br>
	 */
	$("#loantype").change(function() {
		var type = $(this).val();
		if (type == "小额工程采购" || type == "研发" || type == "其他") {
			$("#div_contractOrBudget").show();
		} else {
			$("#div_contractOrBudget").hide();
		}

		if (type == "押金、质保金类") {
			$("#div_plan_date").show();
		} else {
			$("#div_plan_date").hide();
		}
	});

	var loantype = $("#loantype").val();
	if (loantype == "押金、质保金类") {
		$("#planDate").val("");
		$("#div_plan_date").show();
	} else {
		$("#div_plan_date").hide();
	}
	if (loantype == "小额工程采购" || loantype == "研发" || loantype == "其他") {
		$("#div_contractOrBudget").show();
	} else {
		$("#div_contractOrBudget").hide();
	}

	// 预计还款时间 时间范围控件
	$('#data_5 .input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true,
		clearBtn : true,// 清除按钮
		format : "yyyy-mm-dd",
		startDate : $("#currentDate").val()
	});

	/**
	 * 借款用途及说明：字数限制
	 */
	$("#loanExplain").on("input propertychange", function() {
		var $this = $(this), _val = $this.val();
		if (_val.length > 100) {
			$this.val(_val.substring(0, 100));
		}
		if (_val.length != 0) {
			$("#span_length").html("（还剩余" + (100 - _val.length) + "字）");
		} else {
			$("#span_length").html("");
		}
	});

	/**
	 * 增加一行 按钮事件
	 */
	$(".btn_add_line").click(function() {
		var max_rows = $("#table_pay_info>tbody").children("tr").length;

		// 克隆第一行
		var tr = $("#table_pay_info>tbody>tr").eq(0).clone();
		tr.find("td").eq(1).text(max_rows + 1);
		// 清空每列的值
		tr.find("checkbox").prop("checked", false);
		tr.find("select").val("");
		tr.find("input").val("");

		tr.appendTo("#table_pay_info>tbody");

		// 重新排序序号，并重写 元素的 name、id属性
		sortAndReplaceNameAttr();
	});

	/**
	 * 删除所选 按钮事件
	 */
	$(".btn_del_line").click(function() {
		var length = $(".pay_info_checkbox:checked").length;
		if (length == 0) {
			layer.msg("请选择需要删除的行！");
			return;
		}
		if (length == $(".pay_info_checkbox").length) {
			layer.msg("付款信息 至少保留一行！");
			return;
		}
		// 删除选中行
		$(".pay_info_checkbox:checked").each(function() {
			$(this).parent().parent().remove();
		});

		// 重新排序序号，并重写 元素的 name、id属性
		sortAndReplaceNameAttr();

		// 重新计算 总金额
		calculateToltalMoney();
	});

	/**
	 * 删除 按钮事件
	 */
	$("button.deleteDraft").click(function() {
		new $.confirm("是否确定删除此草稿箱？", function() {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "deleteDraft.do",
				data : {
					"mainId" : $("#mainId").val(),
				},
				success : function(data) {
					if (data.code == "success") {
						layer.alert(data.result, 1);
						window.location.href = "/loanSys/draft.do";
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
	});

	/**
	 * 保存退出 按钮事件
	 */
	$("button.save").click(function() {
		// 保存草稿箱
		$("#draftsFlag").val(0);
		if ($("#totalMoney").val() == "" || $("#totalMoney").val() == "NaN") {
			$("#totalMoney").val("0");
		}
		$("#createForm").submit();
	});

	/**
	 * 提交 按钮事件
	 */
	$("button.submits").click(function() {
		// 提交
		$("#draftsFlag").val(1);
		if (validate()) {
			new $.confirm("是否确定提交？", function() {
				layer.load("正在提交中...");
				$("#createForm").submit();
			});
		}
	});

	calculateToltalMoney();
});

/**
 * 提交验证
 * 
 * @returns {Boolean}
 */
function validate() {
	if ($.trim($("#loantype").val()) == "") {
		layer.msg("【借款类型】不能为空！");
		return false;
	}

	if ($("#loantype").val() == "押金、质保金类" && $.trim($("#planDate").val()) == "") {
		layer.msg("【预计还款日】不能为空！");
		return false;
	}

	if ($.trim($("#ou").val()) == "") {
		layer.msg("【借款所属OU】不能为空！");
		return false;
	}
	
	var flag = true;
	$("#table_pay_info>tbody>tr").each(function() {
		var money = $(this).find("td").eq(6).find("input").val();
		flag = isRealNum(money);
		if (!flag) {
			return false;
		}
	});
	if (!flag) {
		layer.msg("【付款金额】只能是数字！");
		return;
	}

	if ($("#totalMoney").val() == "" || $("#totalMoney").val() == "NaN") {
		layer.msg("【合计金额】不能为空！");
		return false;
	}

	return true;
}

/**
 * 合计总金额
 */
function calculateToltalMoney() {
	var totalMoney = 0;
	$("#table_pay_info>tbody>tr").each(function() {
		var money = $(this).find("td").eq(6).find("input").val();
		totalMoney += parseFloat(money);
	});
	$("#span_total_money").text(totalMoney);
	$("#totalMoney").val(totalMoney);
};

/**
 * 重新排序序号，并重写 元素的 name、id属性
 */
function sortAndReplaceNameAttr() {
	var index = 1;
	$("#table_pay_info>tbody>tr").each(function() {
		// 重写 元素的 name、id属性
		var td_index = 0;
		$(this).find("td").each(function() {
			if (td_index >= 2) {
				var $this = $(this).children(":first");
				var name = $this.attr("name");

				var pattern = /\[(.+?)\]/g;
				var text = name.match(pattern);
				name = name.replace(text, "[" + (index - 1) + "]");
				$this.attr("name", name);
				$this.attr("id", name);
			}
			td_index++;
		});

		// 重写序号
		$(this).find("td").eq(1).text(index);
		index++;
	});
}

/**
 * 判断是否是数字
 * 
 * @param val
 * @returns {Boolean}
 */
function isRealNum(val) {
	// isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
	if (val === "" || val == null) {
		return false;
	}
	if (!isNaN(val)) {
		return true;
	} else {
		return false;
	}
}