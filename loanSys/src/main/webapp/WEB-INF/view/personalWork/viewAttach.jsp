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

<title>查看附件</title>

<link href="css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=2.2.0" rel="stylesheet">
<link href="css/newStyles.css" rel="stylesheet">
</head>

<body>
	<input type="hidden" name="mainId" id="mainId" value="${mainId }">
	<div id="wrapper">
		<div
			class="wrapper wrapper-content animated fadeInRight gray-bg dashbard-1"
			style="min-height:800px;">
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h5 class="font14">查看附件</h5>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered mbt0">
										<thead class="textCenter">
											<tr>
												<th width="50" class="text-nowrap">选择</th>
												<th width="50" class="text-nowrap">序号</th>
												<th class="text-nowrap">附件名称</th>
												<th class="text-nowrap">大小</th>
												<th class="text-nowrap">上传人</th>
												<th class="text-nowrap">上传时间</th>
												<th class="text-nowrap">备注</th>
											</tr>
										</thead>
										<tbody class="textCenter">
											<c:if test="${empty entities }">
												<tr>
													<td colspan="7" align="center">暂无审批记录！</td>
												</tr>
											</c:if>
											<c:if test="${! empty entities }">
												<c:forEach items="${entities}" var="e" varStatus="index">
													<tr class="data">
														<td><input type="checkbox" class="i-checks"
															value="${e.attachmentId }" name="" id="${e.id }"></td>
														<td>${index.index +1 }</td>
														<td>${e.attachmentName }</td>
														<td>${e.attachmentSize }</td>
														<td>${e.createBy }</td>
														<td>${e.createDate }</td>
														<td>${e.remake }</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
								<div class="textRight buttons buttons_display pdt10">
									<div class="del-auth">
										<button type="button" class="btn btn-sm btn-primary download">
											<i class="fa fa-download"></i> 下载
										</button>
										<button type="button"
											class="btn btn-sm btn-danger delete_draft">
											<i class="fa fa-trash-o"></i> 删除
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
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
	<script src="local-js/upload.js"></script>
</body>

</html>
