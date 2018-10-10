<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--系统管理员配置</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>系统管理员配置</h2>
					<ol class="breadcrumb">
						<li>提供系统管理员配置功能</li>
					</ol>
				</div>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<input type="hidden" id="isShowPage" value="false">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h5 class="font14">新建配置表</h5>
							</div>
							<form id="saveForm">
								<c:if test="${!empty entity}">
									<input type="hidden" id="entityId" value="${entity.id }">
								</c:if>
								<c:if test="${empty entity}">
									<input type="hidden" id="entityId" value="">
								</c:if>
								<div class="panel-body">
									<div class="form-group single row">
										<label class="col-sm-3 control-label textRight">系统管理员名称：</label>
										<div class="col-md-3">
											<c:if test="${!empty entity}">
												<input type="text" name="username" id="username"
													class="form-control input-sm pdl6 pdt4"
													value="${entity.username}">
											</c:if>
											<c:if test="${empty entity }">
												<input type="text" name="username" id="username"
													class="form-control input-sm pdl6 pdt4">
											</c:if>
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
					</div>
				</div>
			</div>

		</div>
	</div>
	<script src="local-js/managerWork.js"></script>
	<script src="local-js/loan_sys_admin.js"></script>
</body>
</html>