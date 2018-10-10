<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<title>长虹佳华-登录</title>

<link href="css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=2.2.0" rel="stylesheet">
<link href="css/newStyles.css" rel="stylesheet">
</head>

<body class="gray-bg">

	<div class="middle-box text-center loginscreen  animated fadeInDown">
		<div>
			<div>
				<img src="img/sp-top-ph-2.png" style="margin-top:80px;" />

			</div>
			<h3 style="margin:0 0 10px 10px; text-align:left;">欢迎使用&nbsp;&nbsp;
				长虹佳华借款审批系统</h3>

			<div class="form-group">
				<input type="text" aria-invalid="true" aria-required="true"
					class="form-control" name="username" id="username"
					placeholder="用户名">
			</div>
			<div class="form-group">
				<input id="password" name="password" class="form-control"
					type="password" placeholder="密码">
			</div>
			<button class="btn btn-danger block full-width m-b" onclick="login()">登 录</button>
			<p>
				<i class="fa fa-unlock-alt"></i>&nbsp; 请使用Notes邮箱的用户名密码登录
			</p>
			<p>
				<i class="fa fa-phone"></i>&nbsp; 平台技术支持：010-58292288
			</p>
		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.min.js"></script>
	<script src="js/jquery-ui-1.10.4.min.js"></script>
	<script src="js/bootstrap.min.js?v=3.4.0"></script>
	<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

	<!-- Custom and plugin javascript -->
	<script src="js/hplus.js?v=2.2.0"></script>
	<script src="js/plugins/pace/pace.min.js"></script>

	<!-- layer javascript -->
	<script src="js/plugins/layer/layer.min.js"></script>
	<script src="js/buttons.js"></script>
	<script src="js/check_all.js"></script>
	<script src="local-js/login.js"></script>
</body>

</html>

