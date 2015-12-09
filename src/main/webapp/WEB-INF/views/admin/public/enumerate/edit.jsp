<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
		<form method="post" id="editForm">
			<fieldset>
                <legend>修改数据字典</legend>
                <table class="table-info">
					<tr><th>
					<input name="createDate" value="${model.createDate}" type="hidden"/>
					<input name="creator" value="${model.creator}" type="hidden"/>
					<input name="version" value="${model.version}" type="hidden"/>
						<label class="required">表名称</label></th><td>
						<input type="text" class="easyui-validatebox" readOnly="readonly" maxlength="30" name="tableName" value="${model.tableName}" required="true" ></input>
					</td></tr>
					<tr><th>
						<label class="required">字段名称</label></th><td>
						<input class="easyui-validatebox" type="text" readOnly="readonly" maxlength="30" name="columnName" value="${model.columnName}" required="true" ></input>
					</td></tr>
					<tr><th>
						<label class="required">字段编号</label></th><td>
						<input class="easyui-validatebox" type="text" readOnly="readonly" maxlength="128"  name="stsId"  value="${model.stsId}" required="true" ></input>
					</td></tr>
					<tr><th>
						<label class="required">字段描述</label></th><td>
						<input class="easyui-validatebox" type="text"  name="stsWords" value="${model.stsWords}"  maxlength="512"  required="true" ></input>
					</td></tr>
					<tr><th>
						<label class="required">排列序号</label></th><td>
						<input class="easyui-numberbox" precision="0" type="text"  name="orderId" maxlength="3" value="${model.orderId}" required="true" ></input>
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
	 var key="${model.tableName}"+"-"+"${model.columnName}"+"-"+"${model.stsId}";
	 $('#editForm').ajaxForm({
	    url:"${ctx_admin}/public/enumerate/"+key+".json?_method=PUT",  
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
		$("#editForm").submit();
	 });
	//关闭页面
	 $("#btnCancel").click(function() {
		top.closeDialog();
	 });	 
	
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>