<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_admin" value="${pageContext.request.contextPath}/admin/"/>
<c:set var="theme" value="${sessionScope.theme}"/>

<c:set var="placeholderUsername" value="请输入用户名"/>
<c:set var="placeholderPassword" value="请输入密码"/>
<c:set var="placeholderCaptcha" value="请输入验证码"/>
  
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>催收系统</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/resources/themes/${THEME}/images/favicon.ico" rel="shortcut icon" />
<link rel="stylesheet" href="${ctx}/resources/themes/${THEME}/css/login.css" />

<script src="${ctx}/resources/jquery/1.8.3/jquery.min.js"></script>
<script src="${ctx}/resources/themes/${THEME}/js/func.md5.js"></script>
</head>
<body>
 <form action="${ctx_admin}/doLogin" method="post" id="loginForm">
	<div class="login_warp" id="login_box">
		<div class="login">
			<ul>
				<li><input type="text" name="username" id="username" class="name" placeholder="${placeholderUsername}"></li>
				<li><input type="password" name="password" id="password" class="pass"  placeholder="${placeholderPassword}"></li>
				<li class="yzm">
					<p>
						<input type="text" name="captcha" id="captcha"  maxlength="4"   placeholder="${placeholderCaptcha}">
					</p>
					<p>
						<img alt="看不清，换张图片" style="cursor: hand" id="verifycode" src="${ctx_admin}/verifycode" draggable="false" > 
					</p>
					<p>
						<a  id="change_captcha" href="＃" alt="看不清，换一张">看不清，换一张</a>
					</p>
				</li>  
				 <li class="mm">
            		 <span id="error" ></span>
                 </li>
				<li class="login_btn"><input type="button" id="login_button" value="登录" onkeydown="LoginSubmit()"/></li>
				<li class="log_bottom">北京恒昌利通投资管理有限公司&nbsp;©&nbsp;版权所有</li>
			</ul>
		</div>
	</div>

 </form> 
	<script type="text/javascript">
		//<![CDATA[ 
		var err="${requestScope['SECURITY.LOGIN.FAILED.MESSAGE']}"; 
		function Show_Hidden(t) {
			if ($(t).next('ul').is(':visible')) {
				$(t).find('i').removeClass('v01').addClass('v02');
				$(t).next('ul').hide();
				$(t)[0].style.background = '#fff';
			} else {
				$(t).find('i').removeClass('v02').addClass('v01');
				$(t).next('ul').show();
				$(t)[0].style.background = '#f6f6f6';
			}
		}

		$(function() {

			function login_box() {
				//获取DIV为‘box’的盒子
				var oBox = document.getElementById('login_box');
				//获取元素自身的宽度
				var L1 = oBox.offsetWidth;
				//获取元素自身的高度
				var H1 = oBox.offsetHeight;
				//获取实际页面的left值。（页面宽度减去元素自身宽度/2）
				var Left = (document.documentElement.clientWidth - L1) / 2;
				//获取实际页面的top值。（页面宽度减去元素自身高度/2）
				var top = (document.documentElement.clientHeight - H1) / 2;
				oBox.style.left = Left + 'px';
				oBox.style.top = top + 'px';
			}
			login_box();
			//当浏览器页面发生改变时，DIV随着页面的改变居中。
			window.onresize = function() {
				login_box();
			}
 		if(err){
    		 $('#error').text(err).show('normal'); 
    	}
			$("input[name='username']").bind({
				focus : function() {
					$('#error').text('').hide('normal');
				}
			});
			$("input[name='password']").bind({
				focus : function() {
					$('#error').text('').hide('normal');
				}
			});
			$("input[name='captcha']").bind({
				focus : function() {
					$('#error').text('').hide('normal');
				}
			}); 
			$("#username").bind('keydown', function(event) {
				if (event.keyCode == 13) {
					$("#password").focus();
				}
			});
			$("#password").bind('keydown', function(event) {
				if (event.keyCode == 13) {
					$("#captcha").focus();
				}
			}).bind('change', function() {
				$('#passwordChanged').val("Y");
			});
			$("#captcha").bind('keydown', function(event) {
				if (event.keyCode == 13) {
					$('#login_button').click(

					);
				}
			});
			$('#change_captcha').click(
					function() {
						$('#verifycode').attr('src',
								'${ctx_admin}/verifycode?' + Math.random());
					});
			$('#verifycode').click(
					function() {
						$('#verifycode').attr('src',
								'${ctx_admin}/verifycode?' + Math.random());
					});
			$('#login_button')
					.mouseover(
							function() {
								this.src = '${ctx}/resources/themes/${THEME}/images/login_submit_over.png';
							})
					.mouseout(
							function() {
								this.src = '${ctx}/resources/themes/${THEME}/images/login_submit.png';
							}).click(
							function() {
								if ($("#username").val() == "") {
									alert("请输入账号！");
									return false;
								}
								if ($("#password").val() == "") {
									alert("请输入密码！");
									return false;
								}
								if ($("#captcha").val() == "") {
									alert("请输入验证码！");
									return false;
								}

								$("#password").val(
										new $.Md5().hex_md5($("#password")
												.val()));

								$("#loginForm")[0].submit();
							});
		}); 
		function LoginSubmit() {
			if (event.keyCode == 13) {
				$("#loginForm")[0].submit();
			}
		}

		//]]>
	</script>
	<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>