/**
 * 登陆页面 JS
 */

$(function() {
	// 按enter键登录
	document.onkeydown = function(event) {

		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			login();
		}
	};

});

function login() {
	var username = $("#username").val().toUpperCase().replace(/\s/g, "");
	var password = $("#password").val().replace(/\s/g, "");
	if (username == "" || password == "") {
		layer.msg("用户名和密码不能为空！");
		return false;
	}
	$("button").unbind();
	layer.load("努力登陆中...");
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			'username' : username,
			'password' : password
		},
		url : "login.do",
		success : function(data) {
			if (data.code == 'failure') {
				layer.msg("用户名或密码不正确！");
				$("#password").val("");
			} else if (data.code == 'success') {
				$.ajax({
					type : "post",
					url : "todoCount.do",
					dataType : "json",
					success : function(count) {
						if(count > 0){
							// 有待办，则进入 待办事宜
							location.href = "/loanSys/todo.do";
						} else {
							// 没有待办，则进入 创建申请
							location.href = "/loanSys/create.do";
						}
					}
				});
				
			}
		},
		error : function(textStatus) {
			console.error(textStatus);
		},
		complete : function(XMLHttpRequest, status) {
			XMLHttpRequest = null;
		}
	});
}
