<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
            <fieldset>
                <legend>角色信息</legend>
				<table class="table-info">
					<tr><th>
						<label class="required">角色名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="roleName" maxlength="20" required="required"/>
					</td></tr>
					<tr><th>
						<label class="required">角色编码</label></th><td>
					    <input class="easyui-validatebox" type="text" name="roleCode"  maxlength="20" required="required" />
					</td></tr>
					<tr class="row2"><th>
						<label class="required"  >角色描述</label></th><td>
						<textarea class="easyui-validatebox" name="roleDesc" required="required" ></textarea>
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
	 $('#addForm').ajaxForm({
	    url:"${ctx_admin}/security/applyrole", 
	    beforeSubmit:function(formData, jqForm){
            return jqForm.form('validate');
        },
		success:function(data){
			getDtt().refresh();
		 	  top.closeDialog();
		  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 1000,showType : 'slide'});
		}
	 });
	 //表单提交
     $("#btnSubmit").click(function(){
		$("#addForm").submit();
	 });
	//重置页面
	 $("#btnCancel").click(function(){
		 top.closeDialog();
	 });
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>