<%@page import="com.changhongit.loan.enums.MostChangqingPeriodEnum"%>
<%@page import="com.changhongit.loan.enums.RepaymentEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--最长清欠期限配置</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>最长清欠期限配置</h2>
					<ol class="breadcrumb">
						<li>提供最长清欠期限配置功能</li>
					</ol>
				</div>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<input type="hidden" id="isShowPage" value="false">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<!--增加-->
							<div class="panel-heading">
								<!--修改-->
								<h5 class="font14">新建配置表</h5>
								<!--修改-->
							</div>
							<form id="saveForm">
								<c:if test="${!empty entity}">
									<input type="hidden" id="entityId" value="${entity.id }">
								</c:if>
								<c:if test="${empty entity}">
									<input type="hidden" id="entityId" value="">
								</c:if>
								<div class="panel-body">
									<!--修改-->
									<div class="form-group single row">
										<label class="col-sm-3 control-label textRight">借款类型：</label>
										<div class="col-md-3">
											<select name="loanType"
												class="form-control input-sm pdl6 pdt4" id="loanType">
												<option value="">请选择</option>
												<c:set var="loanTypes" value="<%=LoanTypeEnum.values()%>" />
												<c:forEach var="type" items="${loanTypes}">
													<c:if test="${!empty entity}">
														<c:choose>
															<c:when test="${type.getType() == entity.loanType}">
																<option value="${type.getType()}" selected="selected">${type.getType()}</option>
															</c:when>
															<c:otherwise>
																<option value="${type.getType()}">${type.getType()}</option>
															</c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${empty entity }">
														<option value="${type.getType()}">${type.getType()}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group single row">
										<label class="col-sm-3 control-label textRight">还款类型：</label>
										<div class="col-md-3">
											<select name="repaymentType"
												class="form-control input-sm pdl6 pdt4" id="repaymentType">
												<option value="">请选择</option>
												<c:set var="repayments" value="<%=RepaymentEnum.values()%>" />
												<c:forEach var="type" items="${repayments}">
													<c:if test="${!empty entity}">
														<c:choose>
															<c:when test="${type.getType() == entity.repaymentType}">
																<option value="${type.getType()}" selected="selected">${type.getType()}</option>
															</c:when>
															<c:otherwise>
																<option value="${type.getType()}">${type.getType()}</option>
															</c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${empty entity }">
														<option value="${type.getType()}">${type.getType()}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group single row">
										<label class="col-sm-3 control-label textRight">最长清欠期限：</label>
										<div class="col-md-3">
											<select name="maxDuration"
												class="form-control input-sm pdl6 pdt4" id="maxDuration">
												<option value="">请选择</option>
												<c:set var="repayments"
													value="<%=MostChangqingPeriodEnum.values()%>" />
												<c:forEach var="type" items="${repayments}">
													<c:if test="${!empty entity}">
														<c:choose>
															<c:when test="${type.getPeriod() == entity.maxDuration}">
																<option value="${type.getPeriod()}" selected="selected">${type.getPeriod()}</option>
															</c:when>
															<c:otherwise>
																<option value="${type.getPeriod()}">${type.getPeriod()}</option>
															</c:otherwise>
														</c:choose>
													</c:if>
													<c:if test="${empty entity }">
														<option value="${type.getPeriod()}">${type.getPeriod()}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-md-3 lht30"></div>
										<div class="col-md-3 buttons">
											<button type="button" class="btn btn-primary btn-sm save2">保存</button>
											&nbsp;
											<button type="button" class="btn btn-danger btn-sm back"
												onclick="window.history.back()">退出</button>
										</div>
									</div>
								</div>
							</form>
						</div>
						<!--增加-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<script src="local-js/managerWork.js"></script>
	<script src="local-js/loan_maxduration_config.js"></script>
</body>
</html>