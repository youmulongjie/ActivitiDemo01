<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<title>上传附件</title>

<link href="css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=2.2.0" rel="stylesheet">
<link href="css/newStyles.css" rel="stylesheet">
</head>

<body>
	<form id="upload_form" action="upload.do" method="post"
		enctype="multipart/form-data">
		<div id="wrapper">
			<div
				class="wrapper wrapper-content animated fadeInRight gray-bg dashbard-1"
				style="min-height:800px;">
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h5 class="font14">上传附件</h5>

								</div>
								<div class="panel-body">
									<div class="alert alert-warning">
										<p>
											<strong>附件不得超过10M，可同时上传多个文件（选择文件时，按下Ctrl键）</strong>
										</p>
									</div>
									<div class="form-horizontal">
										<div class="form-group">
											<label class="col-sm-1 control-label"></label>
											<div class="col-md-4 control-label">
												<input name="uploadFile" type="file" id="uploadFile" multiple="multiple">
											</div>
											<label class="col-sm-2 control-label">
												<button type="button" id="upload"
													class="btn btn-primary btn-bitbucket btn-sm upload">
													<i class="fa fa-upload"></i> 上传
												</button>
											</label>
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
							<jsp:include page="viewAttach.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
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
	<script src="js/check_all.js"></script>

	<!-- 自定义函数 分割线 -->
	<script src="js/common.js"></script>
</body>

</html>
