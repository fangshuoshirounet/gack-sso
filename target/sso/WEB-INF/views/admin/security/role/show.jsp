<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
    <form method="post" id="editForm"> 
		    <fieldset>
                <legend>角色信息</legend>
				<table class="table-info">
					<tr><th>
						<label class="required">角色名称</label></th><td>
						<input class="easyui-validatebox" type="text"  name="roleName" value="${model.roleName}" maxlength="20" required="true" />
					</td></tr>
					<tr><th>
						<label class="required">角色编码</label></th><td>
						<input class="easyui-validatebox" type="text"  name="roleCode" value="${model.roleCode}" maxlength="20" required="true" />
					</td></tr>
					<tr class="row2"><th>
						<label class="required"  >角色描述</label></th><td>
						<textarea class="easyui-validatebox"   name="roleDesc" required="true" >${model.roleDesc}</textarea>
					</td></tr>
				</table>
			</fieldset>
	</form>
	</div>
	<div class="buttons" data-options="region:'south'">
	    <input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
	</div> 
</div>
<script type="text/javascript">
//<![CDATA[
$(function(){
	 $("#closeSubmitBtn").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>