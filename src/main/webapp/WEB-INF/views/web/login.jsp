<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>统一用户中心</title>
	 <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
</head>

<body>

	<h1>统一用户中心登录</h1>

	<div id="content">
		<c:if test="${not empty param.authentication_error}">
			<h1>错误</h1>
			<p class="error">登录失败.</p>
		</c:if>
		<c:if test="${not empty param.authorization_error}">
			<h1>错误</h1>
			<p class="error">无权访问.</p>
		</c:if>

		<h2>登录</h2>

		<form id="loginForm" name="loginForm" action="doLogin" method="post">
			<p>
				<label>用户名: 
					<input type='text' name='j_username' value="ddewaele@gmail.com" />
				</label>
			</p>
			<p>
				<label>密码: 
					<input type='text' name='j_password' value="adminadmin" />
				</label>
			</p>
			<p>
				<input name="login" value="登录" type="submit"/>
			</p>
		</form>
	</div>
</body>
</html>