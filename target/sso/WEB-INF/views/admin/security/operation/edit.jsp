<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
	<form method="post" id="editForm">
		<input type="hidden"   name="operationId" value="${model.operationId}"></input>
		<input type="hidden"   name="sts" value="${model.sts}"></input>
		<input name="version" value="${model.version}" type="hidden"/>
		 	<fieldset>
		 		 <legend>操作基本信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label class="required">操作名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="operationName" maxlength="4" value="${model.operationName}"  maxlength="20" required="required" placeholder="请输入操作名称"></input>
					</td></tr>
					<tr><th>
						<label class="required">操作编码</label></th><td>
						<input class="easyui-validatebox" type="text" name="operationCode" maxlength="20" value="${model.operationCode}"  maxlength="10"  required="required" placeholder="请输入操作代码"></input>
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
//验证
	 $(".info-form input[type='text']").css({"width": "180px"});
	 var key="${model.operationId}";
	 $('#editForm').ajaxForm({
	    url:"${ctx_admin}/security/operation/"+key+"?_method=PUT", 
	    beforeSubmit:function(formData, jqForm){
            return jqForm.form('validate');
        },
		success:function(data){
			getDtt().refresh();
		 	top.closeDialog();	
			$.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
		}
	 });
//表单提交
     $("#btnSubmit").click(function(){
		$("#editForm").submit();
	 });
//关闭页面
     $(function(){
    		 $("#btnCancel").click(function(){
    			 top.closeDialog();
    		 }); 
    	});
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>