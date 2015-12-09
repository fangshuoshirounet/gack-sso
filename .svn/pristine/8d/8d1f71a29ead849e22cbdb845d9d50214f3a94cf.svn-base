<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
    <form method="post" id="editForm"> 
				<input type="hidden" name="roleId"  value="${model.roleId}"/>
				<input type="hidden" name="sts"  value="${model.sts}"/>
				<input name="version" value="${model.version}" type="hidden"/>
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
	    <button class="button" id="btnSubmit">确 定</button>
		<button class="button" id="btnCancel">取 消</button>
	</div> 
</div>
<script type="text/javascript">
//<![CDATA[
$(function(){
$(".info-form input[type='text']").css({"width": "180px"});
	 //验证
	 var key="${model.roleId}";
	 $('#editForm').ajaxForm({
	    url:"${ctx_admin}/security/applyrole/"+key+"?_method=PUT", 
	    beforeSubmit:function(formData, jqForm){
            return jqForm.form('validate');
        },
		success:function(data){
			
			  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 1000,showType : 'slide'});
			  top.closeDialog();
			  getDtt().refresh();
		}
	 });
	 //表单提交
     $("#btnSubmit").click(function(){
		$("#editForm").submit();
	 });
	 
	//重置页面
	 $("#btnCancel").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>