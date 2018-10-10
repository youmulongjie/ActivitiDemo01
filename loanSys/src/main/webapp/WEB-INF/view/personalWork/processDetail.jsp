<%@page import="com.changhongit.loan.enums.PaymentEnum"%>
<%@page import="com.changhongit.loan.enums.OuMapEnum"%>
<%@page import="com.changhongit.loan.enums.BranchEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--借款申请单详情</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<form id="detailForm">
			<div id="page-wrapper" class="gray-bg dashbard-1">
				<%@ include file="/WEB-INF/view/top.jsp"%>
				<div class="row wrapper border-bottom white-bg page-heading">
					<div class="col-lg-10">
						<h2>查看借款单</h2>
						<ol class="breadcrumb">
							<li><strong>查看借款单详情</strong></li>
						</ol>
					</div>
				</div>

				<input type="hidden" name="isShowPage" id="isShowPage" value="false" />
				<input type="hidden" name="flag" id="flag" value="${flag }" /> <input
					type="hidden" name="mainId" id="mainId" value="${entity.mainId }" />
				<input type="hidden" name="hidden_branch" id="hidden_branch" /> <input
					type="hidden" name="hidden_branchAccounting"
					id="hidden_branchAccounting" /> <input type="hidden"
					name="hidden_platformName" id="hidden_platformName" /> <input
					type="hidden" name="hidden_minister" id="hidden_minister" />
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h5 class="font14">借款申请单</h5>
								</div>
								<div class="panel-body bde7">
									<div class="panel panel-default panel2">
										<div class="panel-heading lht30">
											<span class="font18">${entity.title } </span>
										</div>
									</div>
									<c:if test="${entity.procStatus == '已撤销' }">
										<div class="panel panel-default panel2">
											<div class="panel-heading">
												<span class="font14 lht30 c-red">此借款单已主动撤销！</span>
											</div>
										</div>
									</c:if>
									<c:if
										test="${not empty entity.invoiceFailure && entity.invoiceStatus != 1}">
										<div class="panel panel-default panel2">
											<div class="panel-heading">
												<span class="font14 lht30 c-red">创建费用报表失败原因：${entity.invoiceFailure }</span>
											</div>
										</div>
									</c:if>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h5 class="font14">个人基本信息</h5>
										</div>
										<div class="panel-body">
											<div class="form-horizontal">
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">申请人：</label>
													<div class="col-md-2 lht30">${entity.person }</div>
													<label class="col-sm-2 control-label">借款人职位：</label>
													<div class="col-md-2 lht30">${entity.position }</div>
													<label class="col-sm-2 control-label">所属部门：</label>
													<div class="col-md-2 lht30">
														<span>${entity.upDept }</span>\<span>${entity.dept }</span>
													</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">借款类型：</label>
													<div class="col-md-2 lht30">${entity.loantype }</div>
													<label class="col-sm-2 control-label">申请日期：</label>
													<div class="col-md-2 lht30">${entity.applyDate }</div>
													<label class="col-sm-2 control-label">所属分支机构：</label>
													<div class="col-md-2 lht30">${entity.branch }</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">本部门借款：</label>
													<div class="col-md-2 lht30">
														<c:choose>
															<c:when
																test="${session_user.username == entity.person && entity.procStatus == '驳回'}">
																<input id="departmentLoan_yes" name="departmentLoan"
																	type="radio" value="是" checked="checked" />是 <input
																	name="departmentLoan" id="departmentLoan_no"
																	type="radio" value="否" />否
															</c:when>
															<c:otherwise>
																${entity.departmentLoan }
															</c:otherwise>
														</c:choose>
													</div>
													<label class="col-sm-2 control-label">借款所属OU：</label>
													<div class="col-md-2 lht30">
														<c:choose>
															<c:when
																test="${session_user.username == entity.person && (entity.procStatus == '驳回' || entity.procStatus == '补正')}">
																<select name="ou" id="ou"
																	class="form-control input-sm pdl6 pdt4">
																	<option value="">请选择</option>
																	<c:set var="ous" value="<%=OuMapEnum.values()%>" />
																	<c:forEach var="type" items="${ous}">
																		<option value="${type.name()}">${type.name()}</option>
																	</c:forEach>
																</select>
															</c:when>
															<c:otherwise>
																${entity.ou }
															</c:otherwise>
														</c:choose>
													</div>
													<label class="col-sm-2 control-label">币种：</label>
													<div class="col-md-2 lht30">${entity.cny }</div>
												</div>
												<c:choose>
													<c:when
														test="${session_user.username == entity.person && entity.procStatus == '驳回'}">
														<div class="form-group tar row" id="self"
															style="display:none;">
															<label class="col-sm-2 control-label">借款所属部门：</label>
															<div class="col-md-2 lht30">
																<select name="replaceDept" id="replaceDept"
																	class="form-control input-sm pdl6 pdt4">
																</select>
															</div>
															<label class="col-sm-2 control-label">借款所属业务部：</label>
															<div class="col-md-2 lht30">
																<select name="replaceBusiness" id="replaceBusiness"
																	class="form-control input-sm pdl6 pdt4">
																</select>
															</div>
															<label class="col-sm-2 control-label">借款所属产品线：</label>
															<div class="col-md-2 lht30">
																<select name="replaceLine" id="replaceLine"
																	class="form-control input-sm pdl6 pdt4">
																</select>
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="form-group tar row" id="self">
															<label class="col-sm-2 control-label">借款所属部门：</label>
															<div class="col-md-2 lht30">${entity.replaceDept }</div>
															<label class="col-sm-2 control-label">借款所属业务部：</label>
															<div class="col-md-2 lht30">${entity.replaceBusiness }</div>
															<label class="col-sm-2 control-label">借款所属产品线：</label>
															<div class="col-md-2 lht30">${entity.replaceLine }</div>
														</div>
													</c:otherwise>
												</c:choose>
												<c:if
													test="${entity.loantype =='小额工程采购' || entity.loantype =='研发' || entity.loantype =='其他'}">
													<div class="form-group tar row">
														<label class="col-sm-2 control-label">是否有合同或预算：</label>
														<div class="col-md-2 lht30">
															<c:choose>
																<c:when
																	test="${session_user.username == entity.person && entity.procStatus == '驳回'}">
																	<input name="contractOrBudget" type="radio" value="是" />是
																	<input name="contractOrBudget" type="radio" value="否" />否
																</c:when>
																<c:otherwise>
																	${entity.contractOrBudget }
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</c:if>
												<c:if test="${entity.loantype =='押金、质保金类'}">
													<div class="form-group tar row">
														<label class="col-sm-2 control-label">预计还款日项：</label>
														<div class="col-md-2 lht30">${entity.planDate }</div>
													</div>
												</c:if>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">申请单编号：</label>
													<div class="col-md-2 lht30">${entity.loanNumber }</div>
													<label class="col-sm-2 control-label">借款单编号：</label>
													<div class="col-md-2 lht30" id="div_pzNumber">${entity.pzNumber }</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">借款用途及说明：</label>
													<div class="col-md-10 lht30">
														<c:choose>
															<c:when
																test="${session_user.username == entity.person && (entity.procStatus == '驳回' || entity.procStatus == '补正')}">
																<textarea rows="4" class="form-control" placeholder=""
																	name="loanExplain" id="loanExplain" maxlength="100">${entity.loanExplain }</textarea>
															</c:when>
															<c:otherwise>
																${entity.loanExplain }
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h5 class="font14">付款信息</h5>
										</div>
										<div class="panel-body bde7">
											<div class="table-responsive">
												<table class="table table-bordered mbt0" id="table_pay_info">
													<c:choose>
														<c:when
															test="${session_user.username == entity.person && (entity.procStatus == '驳回' || entity.procStatus == '补正') }">
															<!-- 当前登录人等于 申请人，并且流程状态 等于 驳回会补正 -->
															<thead class="textCenter">
																<tr>
																	<th width="50" class="text-nowrap">选择</th>
																	<th width="50" class="text-nowrap">序号</th>
																	<th width="100" class="text-nowrap">付款方式</th>
																	<th class="text-nowrap">收款人/单位名称</th>
																	<th class="text-nowrap">收款银行名称</th>
																	<th class="text-nowrap">收款银行账户</th>
																	<th width="100" class="text-nowrap">付款金额</th>
																	<th width="100" class="text-nowrap">出纳付款说明</th>
																	<th width="100" class="text-nowrap">备注</th>
																</tr>
															</thead>
															<tbody class="textCenter">
																<c:forEach items="${entity.bursementEntities }" var="b"
																	varStatus="index">
																	<tr>
																		<td><input type="checkbox"
																			class="i-checks pay_info_checkbox" name="account"></td>
																		<td>${index.index+1 }</td>
																		<td><select
																			name="bursementEntities[${index.index }].paymentMethod"
																			class="form-control input-sm pdl6 pdt4">
																				<option value="">请选择</option>
																				<c:set var="types" value="<%=PaymentEnum.values()%>" />
																				<c:forEach var="type" items="${types}">
																					<c:if test="${type.name()== b.paymentMethod }">
																						<option value="${type.name()}" selected="selected">${type.name()}</option>
																					</c:if>
																					<c:if test="${type.name()!= b.paymentMethod }">
																						<option value="${type.name()}">${type.name()}</option>
																					</c:if>
																				</c:forEach>
																		</select></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].payee"
																			class="form-control input-sm pdt4"
																			value="${b.payee }"></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].cashBank"
																			class="form-control input-sm pdt4"
																			value="${b.cashBank }"></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].bankAccount"
																			class="form-control input-sm pdt4"
																			value="${b.bankAccount }"></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].loanMoney"
																			class="form-control input-sm pdt4"
																			onblur="calculateToltalMoney(this)"
																			value="${b.loanMoney }"> <input type="hidden"
																			id="old_line_money${index.index }"
																			value="${b.loanMoney }"></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].paymentExplain"
																			class="form-control input-sm pdt4"
																			value="${b.paymentExplain }"></td>
																		<td><input type="text"
																			name="bursementEntities[${index.index }].remarks"
																			class="form-control input-sm pdt4"
																			value="${b.remarks }"></td>
																	</tr>
																</c:forEach>
															</tbody>
														</c:when>
														<c:otherwise>
															<thead class="textCenter">
																<tr>
																	<th width="50" class="text-nowrap">序号</th>
																	<th width="100" class="text-nowrap">付款方式</th>
																	<th class="text-nowrap">收款人/单位名称</th>
																	<th class="text-nowrap">收款银行名称</th>
																	<th class="text-nowrap">收款银行账户</th>
																	<th width="100" class="text-nowrap">付款金额</th>
																	<th width="100" class="text-nowrap">出纳付款说明</th>
																	<th width="100" class="text-nowrap">备注</th>
																</tr>
															</thead>
															<tbody class="textCenter">
																<c:forEach items="${bursementEntities }"
																	var="bursementEntitie" varStatus="index">
																	<tr>
																		<td>${index.index + 1 }</td>
																		<td>${bursementEntitie.paymentMethod }</td>
																		<td>${bursementEntitie.payee }</td>
																		<td>${bursementEntitie.cashBank }</td>
																		<td>${bursementEntitie.bankAccount }</td>
																		<td>${bursementEntitie.loanMoney }</td>
																		<td class="textwrap">${bursementEntitie.paymentExplain }</td>
																		<td>${bursementEntitie.remarks }</td>
																	</tr>
																</c:forEach>
															</tbody>
														</c:otherwise>
													</c:choose>
												</table>
											</div>
											<div class="textRight buttons buttons_display pdt10">
												<div class="del-auth">
													<!-- 此功能被取消，如需增加，打开注释即可 -->
													<%-- 
													<c:if
														test="${session_user.username == entity.person && (entity.procStatus == '驳回' || entity.procStatus == '补正') }">
														<button type="button"
															class="btn btn-sm btn-primary btn_add_line">
															<i class="fa fa-plus"></i> 增加一行
														</button>
														<button type="button"
															class="btn btn-sm btn-danger delete_draft btn_del_line">
															<i class="fa fa-trash-o"></i> 删除所选
														</button>
													</c:if> 
													--%>
													<span>合计金额：<span id="span_total_money">${entity.totalMoney }</span>
														(单位：元)
													</span> <input type="hidden" name="totalMoney" id="totalMoney">
													<input type="hidden" name="old_totalMoney"
														id="old_totalMoney" value="${entity.totalMoney }">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 从待办事宜 页面 跳转到详情 -->
							<c:if
								test="${flag == 'todo' && fn:contains(entity.currentApprover,session_user.username)}">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h5 class="font14">审批意见</h5>
									</div>
									<div class="panel-body bde7">
										<textarea rows="3" class="form-control" name="approveContent"
											id="approveContent"></textarea>
									</div>
									<!-- 转分公司会计 弹出框 -->
									<div id="filiale_div" style="display: none">
										<table class="table table-bordered" style="width:275px;">
											<tr>
												<th>所属分公司：</th>
												<td><select name="branch" id="branch"
													onchange="changeBranch(this.value)"
													class="form-control input-sm pdl6 pdt4">
														<option value="">请选择</option>
														<c:set var="branchs" value="<%=BranchEnum.values()%>" />
														<c:forEach var="type" items="${branchs}">
															<option value="${type.name()}">${type.name()}</option>
														</c:forEach>
												</select></td>
											</tr>
											<tr>
												<th>分公司会计：</th>
												<td><input type="text" class="form-control input-sm"
													name="branchAccounting" id="branchAccounting" /></td>
											</tr>
										</table>
									</div>
									<!-- 转平台部长 弹出框 -->
									<div id="terrace_div" style="display: none">
										<table class="table table-bordered" style="width:275px;">
											<tr>
												<th>平台部门名称：</th>
												<td><select name="platformName" id="platformName"
													onchange="changePlatform(this.value)"
													class="form-control input-sm pdl6 pdt4">
														<option value="">请选择</option>
														<c:forEach var="platform" items="${platforms}">
															<option value="${platform}">${platform}</option>
														</c:forEach>
												</select></td>
											</tr>
											<tr>
												<th>平台部长：</th>
												<td><input type="text" class="form-control input-sm"
													name="ministerName" id="ministerName" /></td>
											</tr>
										</table>
									</div>
									<div class="panel-body buttons textRight">
										<button type="button" class="btn btn-primary btn-sm back"
											onclick="javascript:history.back(-1)">
											<i class="fa fa-arrow-left"></i> 返回
										</button>
										<c:if test="${session_user.username == entity.person}">
											<button type="button" class="btn btn-primary btn-sm revoke">
												<i class="fa fa-reply"></i> 撤销
											</button>
											<button type="button" class="btn btn-primary btn-sm"
												onclick="javascript:window.open('upload.do?mainId=${entity.mainId }','_blank','width=1000,height=800,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
												<i class="fa fa-upload"></i> 上传附件
											</button>
										</c:if>
										<button type="button"
											class="btn btn-primary btn-bitbucket btn-sm"
											onclick="javascript:window.open('viewAttach.do?mainId=${entity.mainId }','_blank','width=1000,height=600,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
											<i class="fa fa-search"></i> 查看附件
										</button>
										<c:choose>
											<c:when
												test="${entity.currentApprover == entity.person && entity.procStatus == '驳回'}">
												<button type="button" class="btn btn-primary btn-sm submit">
													<i class="fa fa-check"></i> 提交驳回
												</button>
											</c:when>
											<c:when
												test="${entity.currentApprover == entity.person && entity.procStatus == '补正'}">
												<button type="button" class="btn btn-primary btn-sm submit">
													<i class="fa fa-check"></i> 提交补正
												</button>
											</c:when>
											<c:otherwise>
												<c:if
													test="${entity.currentApprover != entity.person || session_user.cashier == true || session_user.fnAccounting == true}">
													<!-- 只有申请人和出纳 能 上传附件 -->
													<c:if
														test="${session_user.cashier == true || session_user.username == entity.person}">
														<button type="button" class="btn btn-primary btn-sm"
															onclick="javascript:window.open('upload.do?mainId=${entity.mainId }','_blank','width=1000,height=800,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
															<i class="fa fa-upload"></i> 上传附件
														</button>
													</c:if>
													<!-- 只有出纳 能 转复核 -->
													<c:if test="${session_user.cashier == true }">
														<button type="button" class="btn btn-primary btn-sm check">
															<i class="fa fa-reply"></i> 转复核
														</button>
													</c:if>
													<!-- 只有 财务会计 能 转分公司会计、生成凭证号 -->
													<c:if test="${session_user.fnAccounting == true }">
														<!-- 信产分公司，转分公司会计审核 -->
														<c:if
															test="${entity.ou == '信产' && entity.loantype != '诉讼费'}">
															<button type="button"
																class="btn btn-primary btn-sm filiale">
																<i class="fa fa-reply"></i> 转分公司会计
															</button>
														</c:if>
														<c:if
															test="${entity.loantype == '研发' || entity.loantype == '小额工程采购' ||entity.loantype == '其他' }">
															<button type="button"
																class="btn btn-primary btn-sm terrace">
																<i class="fa fa-reply"></i> 转平台部长
															</button>
														</c:if>
														<button type="button"
															class="btn btn-primary btn-sm createPZ">
															<i class="fa fa-pencil-square-o"></i> 生成凭证号
														</button>
													</c:if>
													<button type="button" class="btn btn-danger btn-sm reject">
														<i class="fa fa-remove"></i> 驳回
													</button>
													<button type="button"
														class="btn btn-warning btn-sm corrections">
														<i class="fa fa-file-o"></i> 补正
													</button>
													<button type="button" class="btn btn-primary btn-sm agree">
														<i class="fa fa-check"></i> 同意
													</button>
												</c:if>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:if>
							<!-- 从审批中 页面 跳转到详情 -->
							<c:if
								test="${flag == 'progress' && fn:contains(entity.historyApprovers,session_user.username)}">
								<div class="panel-body buttons textRight">
									<c:if test="${session_user.username == entity.person}">
										<button type="button" class="btn btn-primary btn-sm revoke">
											<i class="fa fa-reply"></i> 撤销
										</button>
										<button type="button" class="btn btn-primary btn-sm"
											onclick="javascript:window.open('upload.do?mainId=${entity.mainId }','_blank','width=1000,height=800,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
											<i class="fa fa-upload"></i> 上传附件
										</button>
									</c:if>
									<button type="button"
										class="btn btn-primary btn-bitbucket btn-sm"
										onclick="javascript:window.open('viewAttach.do?mainId=${entity.mainId }','_blank','width=1000,height=600,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
										<i class="fa fa-search"></i> 查看附件
									</button>
									<button type="button" class="btn btn-primary btn-sm back"
										onclick="javascript:window.history.back(-1);">
										<i class="fa fa-arrow-left"></i> 返回
									</button>
									<button type="button" class="btn btn-primary btn-sm urge">
										<i class="fa fa-bolt"></i> 催办
									</button>
									<c:if
										test="${entity.currentWork == 'End' || entity.currentWork == '出纳' || entity.currentWork == '财务复核'}">
										<button type="button" class="btn btn-primary btn-sm"
											onClick="javascript:window.open('print.do?mainId=${entity.mainId }','_blank','width=1100,height=800,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
											<i class="fa fa-print"></i> 打印
										</button>
									</c:if>
								</div>
							</c:if>
							<!-- 从流程结束 页面 跳转到详情 -->
							<c:if
								test="${flag == 'end' && fn:contains(entity.historyApprovers,session_user.username)}">
								<div class="panel-body buttons textRight">
									<button type="button"
										class="btn btn-primary btn-bitbucket btn-sm"
										onclick="javascript:window.open('viewAttach.do?mainId=${entity.mainId }','_blank','width=1000,height=600,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
										<i class="fa fa-search"></i> 查看附件
									</button>
									<c:if
										test="${(empty entity.invoiceNum) && entity.currentWork == 'End'}">
										<c:if
											test="${session_user.cashier == true || session_user.fnAccounting == true || session_user.administrator == true}">
											<button type="button" class="btn btn-primary btn-sm invoice">
												<i class="fa fa-pencil-square-o"></i> 生成预付款发票
											</button>
										</c:if>
									</c:if>
									<button type="button" class="btn btn-primary btn-sm"
										onClick="javascript:window.open('print.do?mainId=${entity.mainId }','_blank','width=1100,height=800,status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,');">
										<i class="fa fa-print"></i> 打印
									</button>
									<button type="button" class="btn btn-primary btn-sm back"
										onclick="javascript:history.back(-1)">
										<i class="fa fa-arrow-left"></i> 返回
									</button>
								</div>
							</c:if>


							<div class="panel panel-primary">
								<div class="panel-heading">
									<h5 class="font14">流转历史</h5>
								</div>
								<div class="panel-body">
									<div class="mbt10">
										<%-- 当前状态：&nbsp;<span onMouseMove="$('.allLinks').show();"
										onMouseOut="$('.allLinks').hide();" class="label label-info">${entity.currentWork }</span> --%>
										当前状态：&nbsp;<span class="label label-info">${entity.currentWork }</span>
									</div>
									<div class="table-responsive">
										<table class="table table-bordered mbt0">
											<!--修改-->
											<thead class="textCenter">
												<tr>
													<th width="50" class="text-nowrap">序号</th>
													<th class="text-nowrap">处理人</th>
													<th class="text-nowrap">处理环节</th>
													<th class="text-nowrap">处理时间</th>
													<th class="text-nowrap">状态</th>
													<th width="300" class="text-nowrap">审批意见</th>
												</tr>
											</thead>
											<tbody class="textCenter">
												<c:forEach items="${approvalComments }" var="comment"
													varStatus="index">
													<tr>
														<td>${index.index +1 }</td>
														<td>${comment.approvor }</td>
														<td>${comment.taskName }</td>
														<td>${comment.approvorDate }</td>
														<td>${comment.status }</td>
														<td>${comment.desc }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4 allLinks dspn" style="z-index: 999">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<!--修改-->
							<h5 class="font14">查看全部环节</h5>
							<!--修改-->
						</div>

						<div class="panel-body timeline">
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date text-navy">
										<i class="fa fa-map-marker"></i>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>流程结束</strong>
										</p>
										<p></p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>财务复核</strong>
										</p>
										<p>王丽萍</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>出纳</strong>
										</p>
										<p>王丽萍</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>财务总监</strong>
										</p>
										<p>王丽萍</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row text-navy">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>财务会计</strong>
										</p>
										<p>李宁审批中</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i> <strong>01-31</strong><br>
										<small>11:20:45</small>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>分管高管</strong>
										</p>
										<p>苏慧清</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i> <strong>01-31</strong><br>
										<small>11:20:45</small>
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>事业部总/平台部长</strong>
										</p>
										<p>王丽萍</p>
									</div>
								</div>
							</div>
							<div class="timeline-item">
								<div class="row">
									<div class="col-xs-3 date">
										<i class="fa fa-chevron-circle-up"></i> <strong>01-31</strong><br>
										<small>11:20:45</small>
										<!--<small class="text-navy">讨论</small>-->
									</div>
									<div class="col-xs-7 content">
										<p class="m-b-xs">
											<strong>申请单填写</strong>
										</p>
										<p>填写申请单</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script src="local-js/personalWork.js"></script>
	<script src="local-js/three_cascade.js"></script>
	<script src="local-js/processDetail.js"></script>
	<script type="text/javascript">
		// 回显 借款所属OU
		$("#ou option[value='${entity.ou}']").prop("selected", true);

		// 回显 总金额
		$("#span_total_money").text('${entity.totalMoney}');
		$("#totalMoney").val('${entity.totalMoney}');

		// 回显 “是否有合同或预算” 按钮
		$("input[type=radio][name=contractOrBudget]").each(function() {
			if ($(this).val() == '${entity.contractOrBudget}') {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});

		// 回显 “本部门借款” 按钮
		var departmentLoan = '${entity.departmentLoan}';
		$("input[type=radio][name=departmentLoan]").each(function() {
			if ($(this).val() == departmentLoan) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});
		// 当前状态
		var procStatus = '${entity.procStatus}';
		if (departmentLoan == "否") {
			$("#self").show();

			if (procStatus == "驳回") {
				// 事业部、业务部、产品线 三级级联对象 参数
				var cascade_option = {
					"id1" : "replaceDept", // 第一级 select 元素ID
					"id2" : "replaceBusiness", // 第二级 select 元素ID
					"id3" : "replaceLine", // 第三级 select 元素ID
					"dataUrl1" : "getShiYeBuMap.do", // 第一级 获取数据源 URL地址
					"dataUrl2" : "getReplaceBusiness.do", // 第二级 获取数据源 URL地址
					"dataUrl3" : "getReplaceLine.do",// 第三级 获取数据源 URL地址
				};

				// 回显 事业部、业务部、产品线
				new $.three_cascade(cascade_option).init(
						'${entity.replaceDept}', '${entity.replaceBusiness}',
						'${entity.replaceLine}', {});
			}
		} else {
			$("#self").hide();
		}
	</script>
</body>
</html>