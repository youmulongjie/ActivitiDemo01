<%@page import="com.changhongit.loan.enums.PaymentEnum"%>
<%@page import="com.changhongit.loan.enums.OuMapEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--创建申请</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>创建申请</h2>
					<ol class="breadcrumb">
						<li><strong>借款填写</strong></li>
					</ol>
				</div>
			</div>

			<form action="createForm.do" method="post" id="createForm"
				enctype="multipart/form-data">
				<input type="hidden" name="mainId" id="mainId"
					value="${loanMainEntity.mainId }"> <input type="hidden"
					name="currentDate" id="currentDate" value="${currentDate }">
				<!-- 保存草稿箱、提交 标识（1：提交；0：保存草稿） -->
				<input type="hidden" name="draftsFlag" id="draftsFlag"
					value="${loanMainEntity.draftsFlag }">
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
											<span class="font18">${session_user.username }的借款申请单</span>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h5 class="font14">个人基本信息</h5>
										</div>
										<div class="panel-body">
											<div class="form-horizontal">
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">申请人：</label>
													<div class="col-md-2 lht30">
														${session_user.username } <input type="hidden"
															name="person" id="person"
															value="${session_user.username }"> <input
															type="hidden" name="erpNum" id="erpNum"
															value="${session_user.erpNum }"> <input
															type="hidden" name="erpName" id="erpName"
															value="${session_user.erpName }"> <input
															type="hidden" name="personOu" id="personOu"
															value="${session_user.ou }"> <input type="hidden"
															name="assignNum" id="assignNum" value="${assignNum }">
													</div>
													<label class="col-sm-2 control-label">借款人职位：</label>
													<div class="col-md-2 lht30">
														<select name="position" id="position"
															class="form-control input-sm pdl6 pdt4">
															<c:forEach items="${positionRecs }" var="rec">
																<c:if test="${rec.primaryFlag == 'Y' }">
																	<option value="${rec.positionName }"
																		selected="selected">${rec.positionName }</option>
																</c:if>
																<c:if test="${rec.primaryFlag !='Y' }">
																	<option value="${rec.positionName }">${rec.positionName }</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
													<label class="col-sm-2 control-label">所属部门：</label>
													<div class="col-md-2 lht30">
														<span id="span_upDept">${upDept }</span>\<span
															id="span_dept">${dept }</span> <input type="hidden"
															name="upDept" id="upDept" value="${upDept }"> <input
															type="hidden" name="dept" id="dept" value="${dept }">
													</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">借款类型：</label>
													<div class="col-md-2 lht30">
														<select name="loantype"
															class="form-control input-sm pdl6 pdt4" id="loantype">
															<option value="">请选择</option>
															<c:set var="loanTypes" value="<%=LoanTypeEnum.values()%>" />
															<c:forEach var="type" items="${loanTypes}">
																<option value="${type.getType()}">${type.getType()}</option>
															</c:forEach>
														</select>
													</div>
													<label class="col-sm-2 control-label">申请日期：</label>
													<div class="col-md-2 lht30">${applyDate }
														<input type="hidden" name="applyDate" id="applyDate"
															value="${applyDate }">
													</div>
													<label class="col-sm-2 control-label">所属分支机构：</label>
													<div class="col-md-2 lht30">${session_user.worksite }
														<input type="hidden" name="branch" id="branch"
															value="${session_user.worksite }">
													</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">本部门借款：</label>
													<div class="col-md-2 lht30">
														<input id="departmentLoan_yes" name="departmentLoan"
															type="radio" value="是" checked="checked" />是 <input
															name="departmentLoan" id="departmentLoan_no" type="radio"
															value="否" />否
													</div>
													<label class="col-sm-2 control-label">借款所属OU：</label>
													<div class="col-md-2 lht30">
														<select name="ou" id="ou"
															class="form-control input-sm pdl6 pdt4">
															<option value="">请选择</option>
															<c:set var="ous" value="<%=OuMapEnum.values()%>" />
															<c:forEach var="type" items="${ous}">
																<option value="${type.name()}">${type.name()}</option>
															</c:forEach>
														</select>
													</div>
													<label class="col-sm-2 control-label">币种：</label>
													<div class="col-md-2 lht30">
														人民币 <input type="hidden" name="cny" id="cny" value="人民币">
													</div>
												</div>
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
												<div class="form-group tar row" style="display: none"
													id="div_plan_date">
													<label class="col-sm-2 control-label">预计还款日：</label>
													<div class="col-md-2 lht30">
														<div id="data_5">
															<div id="datepicker" class="input-daterange">
																<input name="planDate" type="text" id="planDate"
																	readonly="readonly"
																	class="form-control textLeft input-sm pdt4" />
															</div>
														</div>
													</div>
												</div>
												<div class="form-group tar row" style="display: none"
													id="div_contractOrBudget">
													<label class="col-sm-2 control-label">是否有合同或预算：</label>
													<div class="col-md-2 lht30">
														<input name="contractOrBudget" type="radio" value="是" />是
														<input name="contractOrBudget" type="radio" value="否"
															checked="checked" />否
													</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label">借款用途及说明：<br>（100字以内）<br>
														<span id="span_length" class="c-red"></span>
													</label>
													<div class="col-md-10 lht30">
														<textarea rows="4" class="form-control" placeholder=""
															name="loanExplain" id="loanExplain" maxlength="100"></textarea>
													</div>
												</div>
												<div class="form-group tar row">
													<label class="col-sm-2 control-label"></label>
													<div class="col-md-10">
														代替别部门借款：如果替其他部门借款，请在【本部门借款】单选中选择"否"。<br>
														借款所属OU说明：在哪个公司付款请选择对应的OU，如在分公司付款请选择信产。<br> <span
															class="c-red">最长清欠期限提示：请按照【员工借款规定】中最长欠期内，清理次借款。</span><br>
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
													<!-- 从草稿箱进入，但没有保存过记录；或者从 创建进入 -->
													<c:if test="${empty bursementEntities }">
														<tbody class="textCenter">
															<tr>
																<td><input type="checkbox"
																	class="i-checks pay_info_checkbox" name="account"></td>
																<td>1</td>
																<td><select
																	name="bursementEntities[0].paymentMethod"
																	class="form-control input-sm pdl6 pdt4">
																		<option value="">请选择</option>
																		<c:set var="types" value="<%=PaymentEnum.values()%>" />
																		<c:forEach var="type" items="${types}">
																			<option value="${type.name()}">${type.name()}</option>
																		</c:forEach>
																</select></td>
																<td><input type="text"
																	name="bursementEntities[0].payee"
																	class="form-control input-sm pdt4"></td>
																<td><input type="text"
																	name="bursementEntities[0].cashBank"
																	class="form-control input-sm pdt4"></td>
																<td><input type="text"
																	name="bursementEntities[0].bankAccount"
																	class="form-control input-sm pdt4"></td>
																<td><input type="text"
																	name="bursementEntities[0].loanMoney"
																	class="form-control input-sm pdt4"
																	onblur="calculateToltalMoney()"></td>
																<td><input type="text"
																	name="bursementEntities[0].paymentExplain"
																	class="form-control input-sm pdt4"></td>
																<td><input type="text"
																	name="bursementEntities[0].remarks"
																	class="form-control input-sm pdt4"></td>
															</tr>
														</tbody>
													</c:if>
													<!-- 从草稿箱进入，且已经保存过记录 -->
													<c:if test="${not empty bursementEntities }">
														<tbody class="textCenter">
															<c:forEach items="${bursementEntities }" var="b"
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
																		class="form-control input-sm pdt4" value="${b.payee }"></td>
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
																		onblur="calculateToltalMoney()"
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
													</c:if>
												</table>
											</div>
											<div class="textRight buttons buttons_display pdt10">
												<div class="del-auth">
													<button type="button"
														class="btn btn-sm btn-primary btn_add_line">
														<i class="fa fa-plus"></i> 增加一行
													</button>
													<button type="button"
														class="btn btn-sm btn-danger delete_draft btn_del_line">
														<i class="fa fa-trash-o"></i> 删除所选
													</button>
													<span>合计金额：<span id="span_total_money"></span>
														(单位：元)
													</span> <input type="hidden" name="totalMoney" id="totalMoney">
												</div>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<h5 class="font14">上传附件</h5>
										</div>
										<div class="panel-body bde7">
											<div class="alert alert-warning">
												<p>
													<strong>附件不得超过10M，可同时上传多个文件（选择文件时，按下Ctrl键）</strong>
												</p>
											</div>
											<div class="form-horizontal">
												<div class="form-group">
													<label class="col-sm-1 control-label"></label>
													<div class="col-md-6 control-label">
														<input name="uploadFile" type="file" id="uploadFile" multiple="multiple">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-1 control-label"></label>
													<div class="col-md-6 control-label">
														<textarea rows="4" class="form-control" placeholder="填写备注"
															name="remake" id="remake"></textarea>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="panel-body bde7 buttons textRight">
									<c:if test="${loanMainEntity.draftsFlag == 0 }">
										<button type="button"
											class="btn btn-primary btn-bitbucket btn-sm deleteDraft">
											<i class="fa fa-save">删除草稿箱</i>
										</button>
									</c:if>
									<button type="button"
										class="btn btn-primary btn-bitbucket btn-sm save">
										<i class="fa fa-save"></i> 保存退出
									</button>
									&nbsp;
									<button type="button" class="btn btn-primary btn-sm back"
										onclick="javascript:history.back(-1)">
										<i class="fa fa-arrow-left"></i> 返回
									</button>
									&nbsp;
									<button type="button" class="btn btn-primary btn-sm submits">
										<i class="fa fa-check"></i> 提交
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="local-js/personalWork.js"></script>
	<script src="local-js/three_cascade.js"></script>
	<script src="local-js/create.js"></script>
	<script type="text/javascript">
		//是否草稿标识（1：非草稿箱；0：草稿箱），从草稿箱进入则需要回显值
		var draftsFlag = $("#draftsFlag").val();
		if (draftsFlag == 0) {
			// 回显 借款人职位
			$("#position option[value='${loanMainEntity.position}']").prop(
					"selected", true);
			// 回显 所属部门
			$("#span_upDept").html('${loanMainEntity.upDept}');
			$("#span_dept").html('${loanMainEntity.dept}');
			// 回显 借款类型
			$("#loantype option[value='${loanMainEntity.loantype}']").prop(
					"selected", true);
			// 回显 借款所属OU
			$("#ou option[value='${loanMainEntity.ou}']")
					.prop("selected", true);
			// 回显 借款用途及说明
			$("#loanExplain").val('${loanMainEntity.loanExplain}');

			// 回显 “本部门借款” 按钮
			var departmentLoan = '${loanMainEntity.departmentLoan}';
			$("input[type=radio][name=departmentLoan]").each(function() {
				if ($(this).val() == departmentLoan) {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
			});
			if (departmentLoan == "否") {
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

				// 回显 事业部、业务部、产品线
				new $.three_cascade(cascade_option).init(
						'${loanMainEntity.replaceDept}',
						'${loanMainEntity.replaceBusiness}',
						'${loanMainEntity.replaceLine}', {});
			} else {
				$("#self").hide();
			}

			// 回显 “是否有合同或预算” 按钮
			$("input[type=radio][name=contractOrBudget]").each(function() {
				if ($(this).val() == '${loanMainEntity.departmentLoan}') {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
			});

			// 回显 总金额
			$("#span_total_money").text('${loanMainEntity.totalMoney}');
			$("#totalMoney").val('${loanMainEntity.totalMoney}');
		}
	</script>
</body>
</html>