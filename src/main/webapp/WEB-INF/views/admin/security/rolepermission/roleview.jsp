<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<div class="info-form"  id="flexgrid" data-options="region:'center'">
    <form method="post" id="editForm">  
					<input type="hidden" name="roleId" id="roleId" value="${model.roleId }"/>
		 
		   <table id="flexTable"></table>
	</form>
	</div>
	<div class="buttons" data-options="region:'south'">
	    <input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
	</div> 
</div>
 
<script type="text/javascript">
//<![CDATA[
$(function(){ 
	var flexgirdHeight=900;
	$("#flexTable").flexigrid({
		title :"角色功能视图列表",
		dataType : 'json', 
		autoload : true,
		height : flexgirdHeight-30, 
		url:"${ctx_admin}/security/rolepermission/rolepermissionview",
		usepager: false,
		colModel : [{
			display : '菜单名称',
			name : 'menuName',
			sortable : true,
			align : 'center',
			width:"200"
		}, {
			display : '菜单地址',
			name : 'menuUrl',
			sortable : true,
			align : 'center',
			width:"300"
		},{
			display : '菜单权限',
			name : 'permissionHtml',
			sortable : true,
			align : 'left',
			width:"500"
		} 
		]
	}); 
	 $("#closeSubmitBtn").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script> 
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>