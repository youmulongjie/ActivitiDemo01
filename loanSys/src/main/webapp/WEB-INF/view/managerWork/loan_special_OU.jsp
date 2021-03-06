<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>借款审批系统--借款专用OU配置</title>

</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/view/leftMenu.jsp"%>
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%@ include file="/WEB-INF/view/top.jsp"%>
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>借款专用OU配置</h2>
					<ol class="breadcrumb">
						<li>提供借款专用OU配置功能</li>
					</ol>
				</div>
			</div>
			<div class="wrapper wrapper-content animated fadeInRight">
				<input type="hidden" id="isShowPage" value="true">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h5 class="font14 fltl">借款专用OU配置</h5>
								<div class="textRight cor333">
									<a href="/loanSys/loan_special_ou_new.do"
										class="btn btn-sm btn-white ">新建配置表单</a>
								</div>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered hovers thin">
										<thead class="textCenter">
											<tr>
												<th class="text-nowrap"><input type="checkbox"
													class="i-checks" id="all" onClick="check_all()"></th>
												<th class="text-nowrap">序号</th>
												<th class="text-nowrap">OU简称</th>
												<th class="text-nowrap">OU_ID</th>
												<th class="text-nowrap">映射OU</th>
												<th class="text-nowrap">创建人</th>
												<th class="text-nowrap">创建时间</th>
											</tr>
										</thead>
										<script type="template" id="tableTemplate">
                                    	<tr class="data">
                                    		<td><input type="checkbox" class="i-checks"
														value="{{id}}" name="account"></td>
                                    		<td>{{id}}</td>
                                    		<td>{{ou}}</td>
                                    		<td>{{ouId}}</td>
                                    		<td>{{ouMap}}</td>
                                    		<td>{{createBy}}</td>
                                    		<td>{{createDate}}</td>
                                    	</tr>
                                		</script>
										<tbody align="center"></tbody>
									</table>
								</div>
								<div class="textRight buttons">
									<div class="del-auth">
										<button type="button"
											class="btn btn-sm btn-danger delete_draft">
											<i class="fa fa-trash-o"></i> 删除所选
										</button>
									</div>
									<div class="btn-group">
										<span id="totals" class="btntest" style="line-height: 30px;float: left;padding: 20px 10px 0 10px;"></span>
										<ul id="paginator"></ul>
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
	<script src="local-js/loan_special_OU.js"></script>
</body>
</html>