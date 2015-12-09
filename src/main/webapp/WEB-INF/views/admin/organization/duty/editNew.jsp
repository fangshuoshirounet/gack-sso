<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
   <div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
		 	<fieldset>
		 	 	<legend>岗位信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label class="required">岗位名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="name" required="required" maxlength="20"  ></input>
					</td></tr>
					<tr class="row2"><th>
					<label>岗位说明</label></th><td>
						<textarea data-options="required:false" name="remarks" maxlength="512"  ></textarea>
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
	 $('#addForm').ajaxForm({
	    url:"${ctx_admin}/organization/duty", 
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
//关闭页面
	 $("#btnCancel").click(function(){
		  top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>