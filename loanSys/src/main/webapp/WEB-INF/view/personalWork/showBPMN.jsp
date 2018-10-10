<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--查看流程图</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading" style="display: none">
				<div class="col-lg-10">
					<h2>
						其他流程图 &nbsp;&nbsp; <select id="bpmn">
							<c:forEach items="${bpmn }" var="map">
								<option value="img/bpmn/${map.value }.png">${map.key }</option>
							</c:forEach>
						</select>
					</h2>
				</div>
			</div>

			<div class="wrapper wrapper-content animated fadeInRight"
				style="overflow: auto;display: none;">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<img src="img/bpmn/loan_type_01.png" id="bpmnImg">
						</div>
					</div>
				</div>
			</div>
			
			<div class="wrapper wrapper-content animated fadeInRight"
				style="height:570px;overflow: auto;">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<img src="img/bpmn/old.png" id="bpmnImg">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="local-js/personalWork.js"></script>
	<script type="text/javascript">
		new $.personalWork("showBPMN", {}, {});
		$("#bpmn").change(function() {
			$("#bpmnImg").attr("src", $(this).val());
		});
	</script>
</body>
</html>