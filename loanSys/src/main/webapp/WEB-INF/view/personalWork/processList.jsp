<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.changhongit.loan.enums.*"%>

<script src="local-js/personalWork.js"></script>
<script src="local-js/processList.js"></script>

<input type="hidden" name="isShowPage" id="isShowPage" value="true" />
<!-- 判断是哪个页面跳进来的 -->
<input type="hidden" name="flag" id="flag" value="${param.flag }" />
<div class="wrapper wrapper-content animated fadeInRight">
	<form action="" id="form">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h5 class="font14">查询条件</h5>
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group tar row">
								<label class="col-sm-2 control-label">标题：</label>
								<div class="col-md-2">
									<input type="text" name="title" id="title"
										class="form-control input-sm" />
								</div>
								<label class="col-sm-2 control-label">申请人：</label>
								<div class="col-md-2">
									<input type="text" name="person" id="person"
										class="form-control input-sm" />
								</div>
								<label class="col-sm-2 control-label">当前处理环节：</label>
								<div class="col-md-2">
									<select name="currentWork" id="currentWork"
										class="form-control input-sm pdl6 pdt4">
										<option value="">请选择</option>
										<c:forEach var="workName" items="${handleWorkSet}">
											<option value="${workName}">${workName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group tar row">
								<label class="col-sm-2 control-label">当前处理人：</label>
								<div class="col-md-2">
									<input type="text" name="currentApprover" id="currentApprover"
										class="form-control input-sm" />
								</div>
								<label class="col-sm-2 control-label">申请日期：</label>
								<div class="col-md-4" id="data_5">
									<div
										class="input-group input-medium date-picker input-daterange"
										id="datepicker" data-date-format="yyyy-mm-dd">
										<input placeholder="--/--/--" type="text" readonly="readonly"
											class="form-control input-sm textLeft date" name="beginDate"
											id="beginDate" /> <span class="input-group-addon">到</span> <input
											placeholder="--/--/--" type="text" readonly="readonly"
											class="form-control input-sm textLeft date" name="endDate"
											id="endDate" />
									</div>
								</div>
							</div>
							<div class="form-group tar row">
								<label class="col-sm-2 control-label">借款类型：</label>
								<div class="col-md-2">
									<select name="loantype" id="loantype"
										class="form-control input-sm pdl6 pdt4">
										<option value="">请选择</option>
										<c:set var="loanTypes" value="<%=LoanTypeEnum.values()%>" />
										<c:forEach var="type" items="${loanTypes}">
											<option value="${type.getType()}">${type.getType()}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">状态：</label>
								<div class="col-md-2">
									<select name="procStatus" id="procStatus"
										class="form-control input-sm pdl6 pdt4">
										<option value="">请选择</option>
										<c:set var="status" value="<%=ApprovalStatus.values()%>" />
										<c:forEach var="type" items="${status}">
											<option value="${type.name()}">${type.name()}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">申请单编号：</label>
								<div class="col-md-2">
									<input type="text" name="loanNumber" id="loanNumber"
										class="form-control input-sm" />
								</div>
							</div>
							<div class="form-group tar row">
								<label class="col-sm-2 control-label">凭证号：</label>
								<div class="col-md-2">
									<input type="text" name="pzNumber" id="pzNumber"
										class="form-control input-sm" />
								</div>
								<label class="col-sm-2 control-label">发票创建状态：</label>
								<div class="col-md-2">
									<select name="invoiceStatus" id="invoiceStatus"
										class="form-control input-sm pdl6 pdt4">
										<option value="">请选择</option>
										<c:set var="status" value="<%=InvoiceStatusEnum.values()%>" />
										<c:forEach var="type" items="${status}">
											<option value="${type.getValue()}">${type.name()}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group tar row">
								<div class="col-md-12 textRight">
									<button type="button" class="btn btn-w-m btn-primary btn-sm"
										id="queryBtn">查询</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h5 class="font14">
							<c:if test="${param.flag == 'todo' }">待办事宜 清单</c:if>
							<c:if test="${param.flag == 'draft' }">草稿箱 清单</c:if>
							<c:if test="${param.flag == 'progress' }">审批中 清单</c:if>
							<c:if test="${param.flag == 'end' }">流程结束 清单</c:if>
							<c:if test="${param.flag == 'manage' }">管理员查看 清单</c:if>
						</h5>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-striped table-hover hovers thin">
								<thead>
									<tr class="textCenter">
										<th width="25" class="text-nowrap">序号</th>
										<th width="100" class="text-nowrap">申请人</th>
										<th width="100" class="text-nowrap">申请日期</th>
										<th width="300" class="text-nowrap">标题</th>
										<th width="100" class="text-nowrap">借款类型</th>
										<th width="100" class="text-nowrap">当前处理人</th>
										<th width="100" class="text-nowrap">当前处理环节</th>
										<th width="100" class="text-nowrap">状态</th>
									</tr>
								</thead>
								<c:if test="${param.flag == 'draft'}">
									<script type="template" id="tableTemplate">
                                 <tr class="data" onclick="javascript:window.location.href='create.do?id={{mainId}}';">
                                    <td width="25">{{mainId}}</td>
                                    <td width="100">{{person}}</td>
                                    <td width="100">{{applyDate}}</td>
                                    <td width="300">{{title}}</td>
                                    <td width="100">{{loantype}}</td>
                                    <td width="100">{{currentApprover}}</td>
                                    <td width="100">{{currentWork}}</td>
                                    <td width="100">{{procStatus}}</td>
                                  </tr>
                             </script>
								</c:if>
								<c:if test="${param.flag != 'draft'}">
									<script type="template" id="tableTemplate">
                                 <tr class="data" onclick="javascript:window.location.href='processDetail.do?id={{mainId}}&flag=${param.flag}';">
                                    <td width="25">{{mainId}}</td>
                                    <td width="100">{{person}}</td>
                                    <td width="100">{{applyDate}}</td>
                                    <td width="300">{{title}}</td>
                                    <td width="100">{{loantype}}</td>
                                    <td width="100">{{currentApprover}}</td>
                                    <td width="100">{{currentWork}}</td>
									<td width="100">{{procStatus}}</td>
                                  </tr>
                             	</script>
								</c:if>
								<tbody align="center"></tbody>
							</table>
						</div>
						<div class="textRight buttons">
							<div class="del-auth"></div>
							<div class="btn-group">
								<span id="totals" class="btntest"
									style="line-height: 30px;float: left;padding: 20px 10px 0 10px;"></span>
								<ul id="paginator"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
