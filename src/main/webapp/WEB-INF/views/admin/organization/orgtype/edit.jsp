<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
		<div class="info-form" data-options="region:'center'">
			<form method="post" id="editForm">
				<input   type="hidden"  name="sts" value="${model.sts}"/>
						<input name="createDate" value="${model.createDate}" type="hidden"/>
						<input name="creator" value="${model.creator}" type="hidden"/>
						<input name="version" value="${model.version}" type="hidden"/>
						<input type="hidden"  name="orgTypeId"  value="${model.orgTypeId}" ></input>
		 	      <fieldset>
		 		    <legend>组别类型信息</legend>
		 		    <table class="table-info">
				       
					<tr><th>
						<label class="required">组别类型名称</label></th><td>
						<input type="text" class="easyui-validatebox"  name="name"   maxlength="20" required="true" value="${model.name}" ></input>
					</td></tr>
					<tr><th>
						<label  class="norequired">首字母缩写</label></th><td>
						<input type="text"   name="acronym" data-options="required:false" value="${model.acronym}"   ></input>
					</td></tr> 
					
					
					<tr class="row2"><th>
						<label>备注</label></th><td>
						<textarea name="remarks" >${model.remarks}</textarea>
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
//验证
var key="${model.orgTypeId}";
$('#editForm').ajaxForm({
  	url:"${ctx_admin}/organization/orgtype/"+key+".json?_method=PUT", 
  	beforeSubmit:function(formData, jqForm){
        return jqForm.form('validate');
    },
	success:function(data){
	   top.closeDialog();
	   getDtt().refresh();
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