<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
 	<div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
		 	<fieldset>
		 		 <legend>操作基本信息</legend>
		 		 <table class="table-info">
		 		 	<tr><th>
						<label class="required">操作名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="operationName" id="parentTree" maxlength="4" required="required" placeholder="请输入操作名称"></input>
					</td></tr>
					<tr><th>
						<label class="required">操作编码</label></th><td>
						<input type="text" class="easyui-validatebox" name="operationCode" maxlength="20" required="required" placeholder="请输入操作代码"></input>
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
	    url:"${ctx_admin}/security/operation", 
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
		$("#addForm").submit();
	 });
//重置页面
	 $("#addRestBtn").click(function(){
		 $("#addForm")[0].reset();
	 }); 
	
});
//关闭页面
$(function(){
	 $("#btnCancel").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>