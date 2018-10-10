<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 上边 -->
<div class="row border-bottom">
	<nav class="navbar navbar-static-top mbt0" role="navigation">
		<div class="navbar-header">
			<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
				href="#"><i class="fa fa-bars"></i> </a>
		</div>
		<ul class="nav navbar-top-links navbar-right">
			<li><span class="m-r-sm text-muted welcome-message" 
				id="userName"> <a href="#" title="返回首页" 
					class="home"><i class="fa fa-home"></i> </a>欢迎
					${session_user.username }！
			</span></li>
			<li><a href="/loanSys/loginOut.do"> <i
					class="fa fa-sign-out"></i>退出
			</a></li>
		</ul>

	</nav>
</div>
