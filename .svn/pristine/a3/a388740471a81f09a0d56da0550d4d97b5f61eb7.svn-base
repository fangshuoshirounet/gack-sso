<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
		 	<fieldset>
		 		 <legend>组别信息</legend>
		 		 <table class="table-info">
		 		 	<tr><th>
						<label>上级组别</label></th><td>
						<input data-options="multiple:false,required:false" name="parentId" id="parentOrgTree" style="width:182px" value="${model.parentId}"/>
					</td></tr>
					<tr><th>
						<label class="required">组别名称</label></th><td>
						<input class="easyui-validatebox" type="text"   name="name" maxlength="20"  required="true" ></input>
					</td></tr>
					<tr><th>
						<label>首字母缩写</label></th><td>
						<input  type="text"  name="acronym" maxlength="10"   ></input>
					</td></tr>
					<tr><th>
						<label class="required">组别类型</label></th><td>
						<select  name="orgTypeId" required="true" class="easyui-combobox" style="width:182px;">
						    <c:forEach items="${orgTypeList}" var="orgType">
				                <option value="${orgType.orgTypeId}">${orgType.name}</option>
						    </c:forEach>
						</select>
					</td></tr> 
					
					<tr><th>
						<label>排序方案</label></th><td>
						<select  name="planId" required="true" class="easyui-combobox" style="width:182px;">
						    <c:forEach items="${orgTypeList}" var="orgType">
				                <option value="${orgType.orgTypeId}">${orgType.name}</option>
						    </c:forEach>
						</select> 
					</td></tr>
					<tr><th>
						<label>是否跨组查看</label></th><td>
						 <tags:radio-enumerate required="false" columnName="SHOW_FLAG" tableName="ORGANIZATION" name="showFlag" value="N"/>
					</td></tr>
					<tr><th>
						<label class="required">排序位置</label></th><td>
						<input class="easyui-numberbox" precision="0" required="true" type="text"  name="orderNo" maxlength="10" ></input>
					</td></tr>
					<tr class="row2"><th>
						<label>备注</label></th><td>
						<textarea name="remarks" ></textarea>
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
$(".info-form input[type='text']").css({"width": "180px"});   
// $(function () {
// 	$(".info-group input:not([type='radio']):not([type='checkbox']):not([class='easyui-combotree'])").css({"width": "180px"});
// });
$("#parentAreaTree").combotree({
	url:'${ctx_admin}/organization/organization/areatreejson'
});


$("#parentOrgTree").combotree({
	 url:'${ctx_admin}/organization/organization/treejson?isChild=true'
});
<%--
function cleanAreaBtn(){
	$("#parentAreaTree").combotree('clear');
}
function cleanOrgBtn(){
	$("#parentOrgTree").combotree('clear');
}
--%>
$(function(){
	 //验证
	 $('#addForm').ajaxForm({
	    url:"${ctx_admin}/organization/organization", 
	    beforeSubmit:function(formData, jqForm){
            return jqForm.form('validate');
        },
		success:function(data){
			getDtt().refresh();
			getDtt().initTree();
	 	  top.closeDialog();
		  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
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