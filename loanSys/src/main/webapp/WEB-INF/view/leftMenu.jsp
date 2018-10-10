<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page import="com.changhongit.loan.enums.LoanTypeEnum"%>
<link href="css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css?v=4.3.0" rel="stylesheet">
<link href="css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=2.2.0" rel="stylesheet">
<link href="css/newStyles.css" rel="stylesheet">

<!-- 左边 菜单栏 -->
<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">

				<div class="dropdown profile-element logo">
					<h4 class="font-bold">ChanghongIT</h4>
					<h2 class="font-bold">借款审批系统</h2>
				</div>
				<div class="logo-element">CHIT</div>

			</li>
			<li id="personalWork" class=""><a href=""><i
					class="fa fa-user fa-lg"></i> <span class="nav-label">个人工作区</span><span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level" id="personalWorkOpen">
					<li id="processCreate"><a href="/loanSys/create.do">创建申请</a></li>
					<li id="draft"><a href="/loanSys/draft.do">草稿箱</a></li>
					<li id="todo"><a href="/loanSys/todo.do"><span
							class="nav-label">待办事宜</span><span
							class="label label-warning pull-right" id="todoCount">0</span></a></li>
					<li id="processProgress"><a href="/loanSys/progress.do">审批中</a></li>
					<li id="processEnd"><a href="/loanSys/end.do">流程结束</a></li>
					<c:if
						test="${session_user.administrator == true}">
						<li id="processManage"><a href="/loanSys/manage.do">管理员查看申请单</a></li>
					</c:if>
					<c:if
						test="${session_user.administrator == true || session_user.fnAccounting == true || session_user.cashier == true}">
						<li id="historyQuery"><a href="http://chitoa1.changhongit.com/book/EmployeeLoanSystem.nsf" target="_black">历史数据查询</a></li>
					</c:if>
					<li id="showBPMN"><a href="/loanSys/showBPMN.do">查看流程图</a></li>
				</ul></li>
			<c:if test="${session_user.administrator == true }">
				<li id="managerWork" class=""><a href=""><i
						class="fa fa-cog fa-lg"></i> <span class="nav-label">业务管理工作区</span><span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level" id="managerWorkOpen">
						<li id="fixed_approver" class=""><a
							href="/loanSys/fixed_approver.do">固定审批人配置</a></li>
						<li id="loan_special_OU" class=""><a
							href="/loanSys/loan_special_OU.do">借款专用OU配置</a></li>
						<li id="loan_MaxDuration_Config" class=""><a
							href="/loanSys/loan_maxduration_config.do">最长清欠期限配置</a></li>
						<li id="loan_LoanToInvoice" class=""><a
							href="/loanSys/loanToInvoice.do">借款单对应发票类型</a></li>
						<li id="loan_LoanToAccounting" class=""><a
							href="/loanSys/loanToAccounting.do">借款类型对应会计</a></li>
						<li id="branchAccounting" class=""><a
							href="/loanSys/branchAccounting.do">分公司会计配置</a></li>
						<li id="loan_BranchCashier" class=""><a
							href="/loanSys/loan_BranchCashier.do">分公司出纳配置</a></li>
						<li id="platformMinister" class=""><a
							href="/loanSys/platformMinister.do">平台部长配置</a></li>
						<li id="loan_sys_admin" class=""><a
							href="/loanSys/loan_sys_admin.do">借款系统管理员</a></li>
					</ul></li>
			</c:if>
		</ul>

	</div>
</nav>

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
<script src="js/bootstrap-paginator.min.js"></script>

<!-- 自定义函数 分割线 -->
<script src="js/common.js"></script>
<script src="js/page.js"></script>

<!-- Data picker -->
<script src="js/plugins/datapicker/bootstrap-datepicker.js"></script>
