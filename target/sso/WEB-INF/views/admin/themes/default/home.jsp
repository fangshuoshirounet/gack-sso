<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctx_admin" value="${pageContext.request.contextPath}/admin/"/>
<c:set var="theme" value="${sessionScope.theme}"/>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>恒昌贷后业务催收管理系统</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link type="image/x-icon"
	href="${ctx}/resources/themes/${THEME}/images/favicon.ico"
	rel="shortcut icon" />

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/icon.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/application.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/flexigrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/jquery-tab.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/themes/${THEME}/css/jquery-toggle.css" />
<script src="${ctx}/resources/jquery/1.8.3/jquery.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jquery/1.8.3/jquery.json.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/easyui/1.3.2/easyui.customize.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/easyui/1.3.2/easyui-lang.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jquery-form/3.25.0/jquery.form.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/nicescroll/jquery.nicescroll.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/themes/${THEME}/js/main.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/my97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/flexigrid/1.1/js/flexigrid.customize.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/themes/${THEME}/js/application.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jquery-ext/jquery.tab.v1.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jquery-ext/jquery.toggle.v1.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/jquery-ext/jquery.ajaxfileupload.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/funtionCharts/Charts/FusionCharts.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/funtionCharts/data/3DChart_Col_3.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/funtionCharts/assets/prettify/prettify.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/funtionCharts/assets/ui/js/json2.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/funtionCharts/assets/ui/js/lib.js"
	type="text/javascript"></script>


<script type="text/javascript">
//<![CDATA[
	var menu = ${menu};
	var catalog = ${catalog};
	var imgPath = "${ctx}/resources/themes/${THEME}/images";
	var path = "${ctx}";
	var currentPageSize = null;
	var naviArray = new Array();
	var inactiveDaysMsg = '${inactiveDaysMsg}';
	$(function(){
		
		 $.ajax({  
	           type: "POST", 
	           dataType: "json", 
	           url: "${ctx}/statistics/case/queryCaseStatistics", 
	           success: function(data){ 
	        		 var chart1 = new FusionCharts("${ctx}/resources/funtionCharts/Charts/Pie3D.swf", "ChartId1", "560", "350", "0", "0");
	  	      		 chart1.setJSONData(data.chartsLow);		   
	  	      		 chart1.render("chartdiv1");
	        		 var chart = new FusionCharts("${ctx}/resources/funtionCharts/Charts/Pareto3D.swf",
	 						"ChartId", "560", "400", "0", "0");
	  	      		 chart.setJSONData(data.chartsHigh);		   
	  	      		 chart.render("chartdiv");
	           }  
		    });  
	           

	
	   /*  var chart1 = new FusionCharts("${ctx}/resources/funtionCharts/Charts/Pie3D.swf", "ChartId", "560", "350", "0", "0");
		 chart1.setXMLData( dataString1 );		   
		 chart1.render("chartdiv1"); */
		
		 	$(".tabs-header").bind('contextmenu',function(e){
		        e.preventDefault();
		        $('#rcmenu').menu('show', {
		            left: e.pageX,
		            top: e.pageY
		        });
		    });
		     
		    //关闭所有标签页
		    $("#closeall").bind("click",function(){
		    	closeAll();
		    });
		  
		
		
		  function show(){
			    var mydate = new Date();
			    var today = new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六');  
			    var week = today[mydate.getDay()]; 
			    var str = mydate.getFullYear() + "年";
			    str += (mydate.getMonth()+1) + "月";
			    str += mydate.getDate() + "日&nbsp;&nbsp;";
			    str += week;
			    return str;
			   }
		  $("#showdate").html(show());
		   
		
		
		for(var i=0;i<catalog.length;i++){
			if(catalog[i].parentCatalogId == "")
				naviArray.push(catalog[i]);
		}
		currentPageSize = 
		//添加导航栏 
		//添加导航栏click事件
		openMenuCatalogLevel(1);
	  
	 
		//功能菜单上的折叠按钮
		$('.panel-tool').css({'left':'80px'});
		$('#menuDiv .panel-tool:eq(0)').before('<div id="collapse_div" title=\'折叠面板\'></div>');
		$('#collapse_div').css({'position':'absolute','left':'200px','top':'10px','height':'20px','width':'20px','background':'url('+imgPath+'/collapse.png) no-repeat'});
		$('#collapse_div').click(function(){
			$('#wrapper').layout('collapse','west'); 
			return false;
		});
		//快捷菜单上的编辑按钮
		$('#menuDiv .panel-tool:eq(1)').before('<div id="edit_shortcut" title=\'编辑快捷菜单\'></div>');
		$('#edit_shortcut').css({'position':'absolute','left':'200px','top':'10px','height':'20px','width':'20px','background':'url('+imgPath+'/update2.png) no-repeat'});
		$('#edit_shortcut').click(function(){
			createTabs('${ctx_admin}/security/shortcutmenu','快捷菜单维护');
			return false;
		});
		$("#funcDiv,#shortcut").niceScroll({
				touchbehavior:false,
				cursorcolor:"#16628E",//光标颜色
				cursoropacitymin:1.0,//没有触发时透明度
				cursoropacitymax:1.0,//触发时颜色透明度
				cursorwidth:6,//光标的宽度
				cursorborderradius:"24px",//边框半径
				background:"#cdcdcd",
				horizrailenabled:false,
				autohidemode:"scroll"});
		 
		 
	    if(inactiveDaysMsg != '') {
		    $.messager.show({title: '温馨提示:', msg : inactiveDaysMsg, timeout: 5000, showType: 'slide'});
	    }
		
	});
	//关闭预览图片
	function closePic(){
		$('#divTest').hide();
		$('#showImg').attr('src','');
	}
	//打开预览图片
	function showPic(url){
		  $('#showImg').attr('src',url+"?radom="+Math.random());
		 $('#divTest').show();
	}
	function updateUserpwd(){
		var url="${ctx_admin}/organization/sysuser/updateuserpwd";
		 top.openDialog({href:url,resizable : false,height: 200,width: 380,title: "修改密码",modal : true});
	}
	

//]]>
</script>
</head>
<div id="wrapper" class="easyui-layout" border="false" fit="true">
	<div data-options="region:'north'" style="height: 58px">
		<div class="top-area">
			<!-- <div class="branding"></div> -->
			<div class="divtop">
				<img
					src="${ctx}/resources/themes/${THEME}/images/crm_cuishou_logo.png"
					height="58" alt="催收logo标识" class="Logo left">
				<div class="right">
					<span class="huanyingText"><i></i>您好：欢迎登录恒昌贷后业务催收管理系统！<b
						id="showdate"></b></span>

					<div>
						<span class="yonghu"><i></i>当前用户：<span
							title="<shiro:principal/>"><shiro:principal /></span></span> <span
							class="bumen"><i></i>所属部门：<span title="岑峰组">${orgName}</span></span>
						<a href="javascript:updateUserpwd()" class="revise_pass">修改密码</a>
						<a href="${ctx_admin}/logout" class="close_xt">退出</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'south'"
		style="height: 32px; overflow: hidden">
		<div class="bottom-area">
			<div class="bottom-right">2015 北京恒昌利通投资管理有限公司 版权所有</div>
		</div>
	</div>
	<div id="menuDiv" class="level-1" data-options="region:'west'"
		style="width: 230px; height: 100%;">
		<div class="easyui-accordion" fit="true">
			<div id="funcDiv" title='功能菜单'></div>
			<div title='快捷菜单' id="shortcut">
				<ul id="shortcut_menu">
					<c:forEach var="item" items="${shortcutMenuDTOs}">
						<li><a
							onclick="createTabs('${ctx}${item.menuUrl}','${item.sts}');"
							href="javascript:void(0)">${item.sts}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="workTabs" class="easyui-tabs" fit="true">
			<div title='首页' style="width: 100%; height: 100%; border: 0;">
                <div id="chartdiv" align="center"></div>
                 <div id="chartdiv1" align="center"></div>
			</div>
		</div>
	</div>
</div>
<!-- 一级弹出窗体 -->
<div id="model_level1">
	<div id="model1"></div>
</div>
<!-- 二级弹出窗体 -->
<div id="model_level2">
	<div id="model2"></div>
</div>
<div id="rcmenu" class="easyui-menu" style="">

	<div id="closeall">关闭全部</div>
</div>

<!-- 图片预览 -->
<div id="divTest"
	style="width: 100%; height: 100%; border: 1px solid #999; position: absolute; top: 0; left: 0; text-align: center; z-index: 10; display: none; overflow-y: auto;">
	<span><div style="float: right; margin-right: 20px;">
			<img src="${ctx}/resources/themes/${THEME}/images/close.png"
				onclick="closePic();">
		</div></span> <img src="" id='showImg'><br />
</div>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>