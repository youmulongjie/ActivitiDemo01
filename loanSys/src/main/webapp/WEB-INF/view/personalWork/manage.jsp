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
<title>借款审批系统--管理员查看申请单</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>管理员浏览</h2>
					<ol class="breadcrumb">
						<li>管理员查看所有的申请单</li>
					</ol>
				</div>
			</div>
			<jsp:include page="processList.jsp">
				<jsp:param value="manage" name="flag" />
			</jsp:include>
		</div>
	</div>
</body>
</html>