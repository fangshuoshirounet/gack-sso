<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_admin" value="${pageContext.request.contextPath}/admin/"/>
<c:set var="theme" value="${sessionScope.theme}"/>
<!DOCTYPE html>
<html>
<head>
    <title>恒昌贷后业务催收管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
<link type="image/x-icon" href="${ctx}/resources/themes/${THEME}/images/favicon.ico" rel="shortcut icon" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/icon.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/flexigrid.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/application.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/jquery-tab.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/themes/${THEME}/css/jquery-toggle.css" />
    
    <script src="${ctx}/resources/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/jquery/1.8.3/jquery.json.min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/easyui/1.3.2/easyui.customize.min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/easyui/1.3.2/easyui-lang.js" type="text/javascript"></script>
    <script src="${ctx}/resources/flexigrid/1.1/js/flexigrid.customize.js" type="text/javascript"></script>
    <script src="${ctx}/resources/jquery-form/3.25.0/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resources/my97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/resources/themes/${THEME}/js/application.js" type="text/javascript"></script>
	<script src="${ctx}/resources/jquery-ext/jquery.tab.v1.js" type="text/javascript"></script>
	<script src="${ctx}/resources/jquery-ext/jquery.toggle.v1.js" type="text/javascript"></script>
	<script src="${ctx}/resources/jquery-ext/jquery.ajaxfileupload.js" type="text/javascript"></script>
</head>
<body scroll="no">