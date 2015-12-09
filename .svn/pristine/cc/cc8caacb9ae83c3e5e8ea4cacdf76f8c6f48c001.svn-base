<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
		 	<fieldset>
		 		 <legend>菜单目录信息</legend>
		 		 <table class="table-info">
		 		 	<tr><th>
						<label class="norequired">上级菜单目录</label></th><td>
						<input data-options="multiple:false,required:false" value="${model.parentCatalogId}" name="parentCatalogId" id="parentCatalogTree" />
					</td></tr>
					<tr><th>
						<label class="required">菜单目录名称</label></th><td>
						<input class="easyui-validatebox" type="text" name="catalogName" maxlength="20" required="required" placeholder="请输入菜单目录名称"></input>
					</td></tr>
					<tr><th>
                        <label class="required">排序</label></th><td>
                        <input class="easyui-numberbox" type="text" name="orderNo"   required="true"/>
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
$("#parentCatalogTree").combotree({
	 url:"${ctx_admin}/security/menucatalog/treejson?isChild=true"
});
//验证
$(function(){
	 
	 $('#addForm').ajaxForm({
	    url:"${ctx_admin}/security/menucatalog", 
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