<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>
<script src="${ctx}/resources/nicescroll/jquery.nicescroll.min.js" type="text/javascript"></script>
<div class="easyui-layout" data-options="fit:true" style="width:100%;">
	<form method="post" action="${ctx_admin}/organization/staff" id="addForm">
		<div data-options="region:'north'">
             <span class="messager-icon messager-error"></span>
			 <font color="red" style="font-weight: bold;"> 错误编号：
			   <c:choose>
				 <c:when test="${fn:contains(exception, 'com.cattsoft.framework.exception.SysException')}">
				     <font color="blue">${exception.errId}</font><br/>
				 </c:when>
				  <c:otherwise>
				  	 <font color="blue">运行时异常</font><br/>
				  </c:otherwise>
				</c:choose>
			 </font>
             <font color="red" style="font-weight: bold;"> 错误提示信息: 
			    <font color="blue">${exception}</font>
			 </font>
	   </div>
       <div style="overflow:auto;" data-options="region:'center'" id="showStackId">
              <font color="red" style="font-weight: bold;cursor:hand;">错误堆栈详细信息：</font>
			  <font color="blue" style="font-weight: bold;cursor:hand;" id="showDetailMessage">查看详细：</font>
			  <div id="errorMessage" style="display:none;">${exceptionStack}<br/></div>
	   </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA[
$(function(){
	 $("#showDetailMessage").toggle(function(event){
		    $("#errorMessage").show();
			$("#showStackId").niceScroll({
							touchbehavior:false,
							cursorcolor:"#16628E",//光标颜色
							cursoropacitymin:1.0,//没有触发时透明度
							cursoropacitymax:1.0,//触发时颜色透明度
							cursorwidth:5,//光标的宽度
							cursorborderradius:"4px",//边框半径
							background:"#fff",
							horizrailenabled:false,
							autohidemode:"scroll"
		   }); 
	    },function(event){
	        $("#errorMessage").hide();
			$("#showStackId").attr("overflow","hidden");
	  });
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>