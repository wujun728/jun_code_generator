<%@page import="org.coderfun.boot.core.BootSettings"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.coderfun.boot.core.BootSettings" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtm1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>coderfun-boot后台管理</title>
<link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/easyui/jquery-easyui-1.5.3/jquery.min.js"></script>

<script>

var loginSubmit='${pageContext.request.contextPath}/admin/action/boot/userAccess/login';
function refreshCaptcha(){  
    $("#img_captcha").attr("src","${pageContext.request.contextPath}/static/images/kaptcha.jpg?t=" + Math.random());  
}

//防止iframe内出现登录
if(window.parent !== window){
	getTopWinow().location.href = "${pageContext.request.contextPath}<%=BootSettings.getAdminPath()%>/login";
}

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

</script>

</head>

<body>
	<div class="login-main">
		<div class="login-banner">
			<b>coderfun-boot</b><small>v0.1.0</small>
		</div>
		<div class="login-content">
			<h2>用户登录</h2>
			<form method="post" id="login-form" name="login-form">
				<div class="login-info">
					<span class="user">&nbsp;</span>
					<input name="username" id="username" type="text" class="login-input" />
				</div>
				<div class="login-info">
					<span class="pwd">&nbsp;</span>
					<input name="password" id="password" type="password" class="login-input" />
				</div>
				<div class="login-info">
					<span class="captcha">&nbsp;</span>
					<input name="kaptcha" id="kaptcha" style="width: 179px" type="text" class="login-input" />
					<img alt="验证码" class="captcha-img" title="点击更换" id="img_captcha" 
						onclick="javascript:refreshCaptcha();"/>
				</div>
				<div class="login-oper">
					<div id="errorPlacement"></div>
				</div>
				<div class="login-oper">
					<input id="submit" type="button" value="登 录" class="login-btn button" />
					<input id="resetButton" type="button" value="重 置" class="login-reset button" />
				</div>
			</form>
		</div>
		<div class="login-copyright">
				© 2018 coderfun-boot - Powered By <a href="https://gitee.com/klguang" target="_blank">klguang</a>.
		</div>
	</div>

</body>
<script type="text/javascript">

	function validate(){
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();
        var errorPlacement = $("#errorPlacement");
        if(!username){
        	errorPlacement.html("请输入用户名！");
        	return false;
        }
        if(!password){
        	errorPlacement.html("请输入密码！");
        	return false;
        }
        return true;
	}

	$(function(){
		refreshCaptcha();
		// 为document绑定onkeydown事件监听是否按了回车键
		$(document).keydown(function(event) {
			if (event.keyCode === 13) { // 按了回车键
				$("#submit").trigger("click");
			}
		});
		
		$("#submit").click(function(){
			if(validate()){
				var data=$("#login-form").serializeArray();
				$.ajax({
					url : loginSubmit,
					data : data,
					type : "POST",
					dataType : "json",
					success : function(json){
						if(json.type == "success"){
							window.location.href = "${pageContext.request.contextPath}<%=BootSettings.getAdminPath()%>/";
						}
					},
					error:function(XMLHttpRequest,textStatus,error){
				        var json = JSON.parse(XMLHttpRequest.responseText);
				        $("#errorPlacement").html(json.message);
				        refreshCaptcha();
					}
				});
			}
		})
		$("#resetButton").click(function(){
			$("#login-form")[0].reset();
			$("#errorPlacement").html("");
		});
		
	});
	

</script>
</html>
