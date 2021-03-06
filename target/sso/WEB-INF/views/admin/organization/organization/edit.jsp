<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
	<form method="post" id="editForm">
		<input type="hidden" name="orgId" value="${model.orgId }" />  
	    <input name="createDate" value="${model.createDate}" type="hidden"/>
		<input name="creator" value="${model.creator}" type="hidden"/>
		<input name="version" value="${model.version}" type="hidden"/>
		 	<fieldset>
		 		 <legend>组别信息</legend>
		 		 <table class="table-info">
		 		 	<tr><th>
						<label>上级组别</label></th><td>
						<input data-options="multiple:false,required:false" value="${model.parentId}" name="parentId" id="parentOrgTree" style="width:182px"/>
					</td></tr>
					<tr><th>
						<label class="required">组别名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="name" maxlength="20" value="${model.name}"   required="true" ></input>
					</td></tr>
					<tr><th>
						<label>首字母缩写</label></th><td>
						<input  type="text"  name="acronym" maxlength="10" value="${model.acronym}"  ></input>
					</td></tr>
					<tr><th>
						<label class="required">组别类型</label></th><td>
						<select name="orgTypeId" required="false" class="easyui-combobox" style="width:182px;">
						    <c:forEach items="${orgTypeList}" var="orgType">
						    	<c:choose>
						    	    <c:when test="${orgType.orgTypeId == model.orgTypeId}">
						                <option value="${orgType.orgTypeId}" selected>${orgType.name}</option>
						            </c:when>
						            <c:otherwise>
						                <option value="${orgType.orgTypeId}">${orgType.name}</option>
						            </c:otherwise>
					            </c:choose>
						    </c:forEach>
						</select>
					</td></tr>
						<tr><th>
						<label>排序方案</label></th><td>
						<select  name="planId" required="true" class="easyui-combobox" style="width:182px;">
						     <c:forEach items="${orgTypeList}" var="orgType">
						    	<c:choose>
						    	    <c:when test="${orgType.orgTypeId == model.planId}">
						                <option value="${orgType.orgTypeId}" selected>${orgType.name}</option>
						            </c:when>
						            <c:otherwise>
						                <option value="${orgType.orgTypeId}">${orgType.name}</option>
						            </c:otherwise>
					            </c:choose>
						    </c:forEach>
						</select> 
					</td></tr>
					<tr><th>
						<label>是否跨组查看</label></th><td>
						 <tags:radio-enumerate required="false" columnName="SHOW_FLAG" tableName="ORGANIZATION" name="showFlag" value="${model.showFlag}"/>
					</td></tr>
					<tr><th>
						<label class="required">排序位置</label></th><td>
						<input class="easyui-numberbox" precision="0" required="true" type="text"  name="orderNo" maxlength="10"   value="${model.orderNo}"></input>
					</td></tr>
					<tr class="row2"><th>
						<label>备注</label></th><td>
						<textarea name="remarks" value="${model.remarks}" ></textarea>
					</td></tr>  </table>
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
 
var key="${model.orgId}";
$("#parentAreaTree").combotree({
	 url:'${ctx_admin}/organization/organization/areatreejson?isChild=true'
});

$("#parentOrgTree").combotree({
	 url:'${ctx_admin}/organization/organization/treejson?isChild=true&id='+key
});
 
$('#editForm').ajaxForm({
  	url:"${ctx_admin}/organization/organization/"+key+"?_method=PUT", 
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
	$("#editForm").submit();
});
//重置页面
$("#btnCancel").click(function(){
	top.closeDialog();
}); 
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>