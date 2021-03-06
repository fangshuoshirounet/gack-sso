<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
		<form method="post" id="addForm">
            <fieldset>
                <legend>新建数据字典</legend>
                <table class="table-info">
					<tr><th>
						<label class="required">表名称</label></th><td>
						<input type="text" class="easyui-validatebox"  name="tableName"   maxlength="30" required="true" placeholder="请输入表名称"></input>
					</td></tr>
					<tr><th>
						<label class="required">字段名称</label></th><td>
						<input class="easyui-validatebox" type="text"   name="columnName"  maxlength="30" required="true" placeholder="请输入字段名称"></input>
					</td></tr>
					<tr><th>
						<label class="required">字段编号</label></th><td>
						<input class="easyui-validatebox" type="text"   name="stsId" maxlength="128" required="true" placeholder="请输入字段编号"></input>
					</td></tr>
					<tr><th>
						<label class="required">字段描述</label></th><td>
						<input class="easyui-validatebox" type="text"  name="stsWords"  maxlength="512" required="true" placeholder="请输入字段描述"></input>
					</td></tr>
					<tr><th>
						<label class="required">排列序号</label></th><td>
						<input  class="easyui-numberbox" precision="0" type="text"  name="orderId" maxlength="3"  required="true" placeholder="请输入排序编号"></input>
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
	 //表单提交
     $("#btnSubmit").click(function(){
	    if($("#addForm").form('validate')){//验证
			 $.ajax({
				  type: "post",
				  url: "${ctx_admin}/public/enumerate",
				  data: $("#addForm").serialize(),
				  success: function(data){
					  getDtt().refresh();
					  top.closeDialog();
					  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
				  }
			});
	    }
	 });
	 
	//关闭页面
	 $("#btnCancel").click(function() {
   		top.closeDialog();
   	 });
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>