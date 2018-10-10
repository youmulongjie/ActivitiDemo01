/**
 * 详情 JS
 */
$(function() {
	var flag = $("#flag").val();

	if (flag == "todo") {
		// 从待办页面进入的详情
		new $.personalWork("todo", {}, {});
		
		/**
		 * 撤销 按钮事件
		 */
		$("button.revoke").click(function() {
			new $.confirm("是否确定撤销？", function() {
				$.ajax({
					type : "post",
					url : "revoke.do",
					data : {
						"mainId" : $("#mainId").val()
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							// layer.alert(message.result, 1);
							window.location.href = "/loanSys/progress.do";
						} else {
							layer.msg(message.result);
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
		 * 同意按钮 事件
		 */
		$("button.agree").click(function() {
			new $.confirm("是否确定提交？", function() {
				layer.load("正在提交中...");
				$.ajax({
					type : "post",
					url : "agree.do",
					data : {
						"mainId" : $("#mainId").val(),
						"comment" : $("#approveContent").val(),
						"flag" : 1
					// 标识：同意
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							// layer.alert("操作成功", 1);
							window.location.href = "/loanSys/todo.do";
						} else {
							layer.alert(message.result);
						}
					},
					error : function(textStatus) {
						console.error(textStatus);
						layer.closeAll();
					},
					complete : function(XMLHttpRequest, status) {
						XMLHttpRequest = null;
						if (status == 'timeout') {
							xhr.abort(); // 超时后中断请求
							$.alert("网络超时，请刷新", function() {
								location.reload();
							});
						}
					}
				});
			});

		});

		/**
		 * 生成凭证号 按钮事件
		 */
		$("button.createPZ").click(function() {
			$.ajax({
				type : "post",
				url : "createPZNumber.do",
				data : {
					"mainId" : $("#mainId").val()
				},
				dataType : "json",
				success : function(message) {
					if (message.code == "success") {
						var pzNumber = message.data;
						layer.alert(message.result + " : " + pzNumber, 1);
						$("#div_pzNumber").html(pzNumber);
					} else {
						layer.alert(message.result);
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
		 * 驳回 按钮事件
		 */
		$("button.reject").click(function() {
			new $.confirm("是否确定驳回？", function() {
				if ($.trim($("#approveContent").val()) == "") {
					layer.alert("【审批意见】 不能为空！");
					return;
				}
				layer.load("正在提交中...");
				$.ajax({
					type : "post",
					url : "rejectOrCorrections.do",
					data : {
						"mainId" : $("#mainId").val(),
						"comment" : $("#approveContent").val(),
						"operator" : "驳回"
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							// layer.alert("操作成功", 1);
							window.location.href = "/loanSys/todo.do";
						} else {
							layer.alert(message.result);
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
		 * 补正 按钮事件
		 */
		$("button.corrections").click(function() {
			new $.confirm("是否确定补正？", function() {
				if ($.trim($("#approveContent").val()) == "") {
					layer.alert("【审批意见】 不能为空！");
					return;
				}
				layer.load("正在提交中...");
				$.ajax({
					type : "post",
					url : "rejectOrCorrections.do",
					data : {
						"mainId" : $("#mainId").val(),
						"comment" : $("#approveContent").val(),
						"operator" : "补正"
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							// layer.alert("操作成功", 1);
							window.location.href = "/loanSys/todo.do";
						} else {
							layer.alert(message.result);
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
		 * 转复核 按钮事件
		 */
		$("button.check").click(function() {
			new $.confirm("是否确定转复核？", function() {
				layer.load("正在提交中...");
				$.ajax({
					type : "post",
					url : "agree.do",
					data : {
						"mainId" : $("#mainId").val(),
						"comment" : $("#approveContent").val(),
						"flag" : 2
					// 标识：转复核
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							// layer.alert("操作成功", 1);
							window.location.href = "/loanSys/todo.do";
						} else {
							layer.alert(message.result);
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
		 * 提交驳回|补正 按钮事件
		 */
		$("button.submit").click(function() {
			var text = $(this).text().trim();
			new $.confirm("是否确定" + text + "？", function() {
				if ($.trim($("#loanExplain").val()) == "") {
					layer.alert("【借款用途及说明】 不能为空！");
					return;
				}

				// 判断每行上的金额不能大于原金额
				var flag = true;
				$("#table_pay_info>tbody>tr").each(function() {
					var moneyObj = $(this).find("td").eq(6).find("input");
					var money = moneyObj.val();
					var old_value = moneyObj.next().val();
					if (parseFloat(old_value) < parseFloat(money)) {
						flag = false;
						moneyObj.focus();
						return;
					}
				});
				if (!flag) {
					layer.msg("【付款金额】不能大于原金额【" + old_value + "】！");
					$(obj).val(old_value);
					return;
				}

				var old_totalMoney = parseFloat($("#old_totalMoney").val());
				var totalMoney = parseFloat($("#totalMoney").val());
				if (totalMoney - old_totalMoney > 0) {
					layer.alert("【借款金额】 不能大于原借款金额：" + old_totalMoney + "！");
					return;
				}
				layer.load("正在提交中...");
				var params = $("#detailForm").serializeArray();
				params.push({
					"approveContent" : $("#approveContent").val()
				});
				$.ajax({
					type : "post",
					url : "submitCorrections2.do",
					data : params,
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							// layer.alert("操作成功", 1);
							window.location.href = "/loanSys/todo.do";
						} else {
							layer.alert(message.result);
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
		 * 转分公司会计 按钮事件
		 */
		$("button.filiale")
				.click(
						function() {
							new $.confirm(
									$("#filiale_div").html(),
									function() {
										if ($
												.trim($(
														"input[name='branchAccounting']")
														.val()) == "") {
											layer.msg("【分公司会计名称】为空！");
											return;
										}

										var branch = $("#hidden_branch").val();
										var branchAccounting = $(
												"#hidden_branchAccounting")
												.val();

										new $.confirm(
												"是否确定转【" + branch + "】会计【"
														+ branchAccounting
														+ "】？",
												function() {
													$
															.ajax({
																type : "post",
																url : "turnBranchAccounting.do",
																data : {
																	"mainId" : $(
																			"#mainId")
																			.val(),
																	"branch" : branch,
																	"branchAccounting" : branchAccounting
																},
																dataType : "json",
																success : function(
																		message) {
																	if (message.code == "success") {
																		// layer.alert("操作成功",1);
																		window.location.href = "/loanSys/todo.do";
																	} else {
																		layer
																				.alert(message.result);
																	}
																},
																error : function(
																		textStatus) {
																	console
																			.error(textStatus);
																},
																complete : function(
																		XMLHttpRequest,
																		status) {
																	XMLHttpRequest = null;
																}
															});
												});

									});
						});

		/**
		 * 转平台部长 按钮事件
		 */
		$("button.terrace")
				.click(
						function() {
							new $.confirm(
									$("#terrace_div").html(),
									function() {
										if ($.trim($(
												"input[name='ministerName']")
												.val()) == "") {
											layer.msg("【平台部长】为空！");
											return;
										}

										var hidden_platformName = $(
												"#hidden_platformName").val();
										var hidden_minister = $(
												"#hidden_minister").val();

										new $.confirm(
												"是否确定转【" + hidden_platformName
														+ "】部长【"
														+ hidden_minister
														+ "】？",
												function() {
													$
															.ajax({
																type : "post",
																url : "trunPlatformMinister.do",
																data : {
																	"mainId" : $(
																			"#mainId")
																			.val(),
																	"platformName" : hidden_platformName,
																	"ministerName" : hidden_minister
																},
																dataType : "json",
																success : function(
																		message) {
																	if (message.code == "success") {
																		// layer.alert("操作成功",1);
																		window.location.href = "/loanSys/todo.do";
																	} else {
																		layer
																				.alert(message.result);
																	}
																},
																error : function(
																		textStatus) {
																	console
																			.error(textStatus);
																},
																complete : function(
																		XMLHttpRequest,
																		status) {
																	XMLHttpRequest = null;
																}
															});
												});

									});
						});

	} else if (flag == "progress") {
		// 从审批中页面进入的详情
		new $.personalWork("processProgress", {}, {});

		/**
		 * 撤销 按钮事件
		 */
		$("button.revoke").click(function() {
			new $.confirm("是否确定撤销？", function() {
				$.ajax({
					type : "post",
					url : "revoke.do",
					data : {
						"mainId" : $("#mainId").val()
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							// layer.alert(message.result, 1);
							window.location.href = "/loanSys/progress.do";
						} else {
							layer.msg(message.result);
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
		 * 催办 按钮事件
		 */
		$("button.urge").click(function() {
			new $.confirm("是否确定催办？", function() {
				layer.load("正在催办中...");
				$.ajax({
					type : "post",
					url : "urge.do",
					data : {
						"mainId" : $("#mainId").val()
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.closeAll();
							layer.alert(message.result, 1);
						} else {
							layer.msg(message.result);
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
	} else if (flag == "end") {
		// 从审批结束页面进入的详情
		new $.personalWork("processEnd", {}, {});

		/**
		 * 生成预付款发票 按钮事件
		 */
		$("button.invoice").click(function() {
			new $.confirm("是否确定生成预付款发票？", function() {
				layer.load("正在生成预付款发票中...");
				$.ajax({
					type : "post",
					url : "createInvoice.do",
					data : {
						"mainId" : $("#mainId").val()
					},
					dataType : "json",
					success : function(message) {
						if (message.code == "success") {
							layer.alert("生成预付款发票成功，发票号：" + message.data, 1);
							location.reload();
						} else {
							layer.msg(message.result);
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
	} else if (flag == "manage") {
		// 从管理员查看申请单页面进入的详情
		new $.personalWork("processManage", {}, {});
	}
	
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

});

/**
 * 所属分公司 change事件
 * 
 * @param branch
 *            分公司
 */
function changeBranch(branch) {
	if (branch != "") {
		$("#hidden_branch").val(branch);
		$.ajax({
			type : "post",
			url : "getBranchAccounting.do",
			data : {
				"branch" : branch
			},
			dataType : "json",
			success : function(message) {
				if (message.code == "success") {
					$("#hidden_branchAccounting").val(message.data);
					$("input[name='branchAccounting']").val(message.data);
				} else {
					layer.msg(message.result);
				}
			}
		});
	}
}

/**
 * 平台部门 change事件
 * 
 * @param platformName
 *            平台部门名称
 */
function changePlatform(platformName) {
	if (platformName != "") {
		$("#hidden_platformName").val(platformName);
		$.ajax({
			type : "post",
			url : "getPlatformMinister.do",
			data : {
				"platformName" : platformName
			},
			dataType : "json",
			success : function(message) {
				if (message.code == "success") {
					$("#hidden_minister").val(message.data);
					$("input[name='ministerName']").val(message.data);
				} else {
					layer.msg(message.result);
				}
			},
			error : function(textStatus) {
				console.error(textStatus);
			},
			complete : function(XMLHttpRequest, status) {
				XMLHttpRequest = null;
			}
		});
	}
}

/**
 * 合计总金额
 */
function calculateToltalMoney(obj) {
	var totalMoney = 0;

	var flag = isRealNum(obj.value);
	if (!flag) {
		layer.msg("【付款金额】只能是数字！");
		$(obj).focus();
		return;
	}

	var old_value = $(obj).next().val();
	if (parseFloat(old_value) < parseFloat(obj.value)) {
		layer.msg("【付款金额】不能大于原金额【" + old_value + "】！");
		$(obj).val(old_value);
		return;
	}

	$("#table_pay_info>tbody>tr").each(function() {
		var money = $(this).find("td").eq(6).find("input").val();
		totalMoney += parseFloat(money);
	});
	$("#span_total_money").text(totalMoney);
	$("#totalMoney").val(totalMoney);
};

/**
 * @deprecated
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
 * @deprecated
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
 * @deprecated
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