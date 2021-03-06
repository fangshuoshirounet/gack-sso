<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
<form method="post" id="viewForm">
<input type="hidden" name="roleId" id="roleId" value="${model.roleId}"/>
<input type="hidden" name="roleName" id="roleName" value="${model.roleName}"/>
		<input type="hidden" name="applyRoleId" id="applyRoleId" value="${model.id }"/>
		<input type="text" name="auditStatus" id="auditStatus" value="${model.auditStatus }"/>
<!-- 上部:查询表单 -->
			<div data-options="region:'north'" >
				<fieldset>
                <legend>角色申请信息</legend>
                  <table class="table-info">
               <tr><th>
						<label class="required">角色名称</label></th><td>
						${model.roleName} 
					</td></tr>
					<tr><th>
						<label class="required">角色编码</label></th><td>
						${model.roleCode} 
					</td></tr>
					<tr class="row2"><th>
						<label class="required"  >角色描述</label></th><td>
						<textarea class="easyui-validatebox"  readonly="readonly" name="roleDesc" required="true" >${model.roleDesc}</textarea>
						 
					</td></tr>
					<c:if test="${model.auditStatus!='I'}">
					<tr class="row2">
						<th><label>审核意见</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px" readonly="readonly" value="${model.auditOpintion}"></textarea></td>
					</tr>
					</c:if>
				</table>
            </fieldset>
			</div>
			<!-- 下部:显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="roleTables"></table>
			</div>
			<div data-options="region:'south'" class="buttons">
				<input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
			</div> 
	</form>
</div>
<script type="text/javascript">
//<![CDATA
	$(function() { 
	     var flexgirdHeight=470; 
	 		$("#roleTables").flexigrid({
	 			title :"角色功能视图列表",
	 			dataType : 'json', 
	 			autoload : true,
	 			height : flexgirdHeight-30, 
	 			url:"${ctx_admin}/security/applyrolepermission/applyRolePermission",
	 			usepager: false,
	 			colModel : [{
	 				display : '菜单名称',
	 				name : 'menuName',
	 				sortable : true,
	 				align : 'center',
	 				width:"200"
	 			},  {
	 				display : '菜单权限',
	 				name : 'permissionHtml',
	 				sortable : true,
	 				align : 'left',
	 				width:"400"
	 			} 
	 			]
	 		});  
		$("#closeSubmitBtn").click(function() {
			 top.closeDialog();
		});	 
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>