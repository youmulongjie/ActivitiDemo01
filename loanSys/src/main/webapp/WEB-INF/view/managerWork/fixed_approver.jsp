<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--固定审批人配置</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>固定审批人配置</h2>
					<ol class="breadcrumb">
						<li>提供固定审批人配置功能</li>
					</ol>
				</div>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h5 class="font14">固定审批人配置</h5>
							</div>
							<div class="panel-body">
								<c:forEach var="approver" items="${approverMap }"
									varStatus="index">
									<div class="form-group single row">
										<label class="col-sm-3 control-label textRight">${approver.key }：</label>
										<div class="col-md-3">
											<input type="text" title="${approver.key }"
												class="form-control input-sm"
												value="${approver.value.name }" />
										</div>
										<c:if test="${index.index == 5}">
											<div class="col-md-6">
												(默认本公司出纳，若转财务则系统将会去“业务系统配置库”中获取分公司出纳)</div>
										</c:if>
									</div>
								</c:forEach>

								<div class="form-group">
									<div class="col-md-3"></div>
									<div class="col-md-3 buttons">
										<button type="button" class="btn btn-primary btn-sm save2">保存</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script src="local-js/managerWork.js"></script>
	<script src="local-js/fixed_approver.js"></script>
</body>
</html>