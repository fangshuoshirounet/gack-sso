<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
	<form method="post" id="addForm">
            <fieldset >
                <legend>系统配置信息</legend>
                <table class="table-info">
                 <tr><th> 
                        <label class="required">系统标识</label></th><td>
                        <input class="easyui-validatebox" type="text" name="sysConfigId" maxlength="20" required="true" />
                  </td></tr>
                   <tr><th> 
                        <label class="required">名称</label></th><td>
                        <input class="easyui-validatebox" type="text" name="name" maxlength="20" required="true" />
                  </td></tr>
                   <tr><th>  
						<label class="required">配置类型</label></th><td>
					    <tags:combobox-enumerate tableName="SYS_CONFIG" columnName="CONFIG_TYPE" value="123" style='width:182px;' name="configType" choiceOption="false"></tags:combobox-enumerate>
					</td></tr>
                   <tr><th> 
						<label class="required">当前值</label></th><td>
						<input class="easyui-validatebox" type="text"  name="curValue"  required="true" maxlength="32"></input>
					</td></tr>
                   <tr class="row2"><th>  
						<label>字段描述</label></th><td>
						<textarea name="valueDesc"  ></textarea>
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
				  url: "${ctx_admin}/public/sysconfig",
				  data: $("#addForm").serialize(),
				  success: function(data){
					  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 3000,showType : 'slide'});
					  top.closeDialog();
					  getDtt().refresh();					 
				  }
			});
	    }
	 });
	 
	//关闭页面
	 $("#btnCancel").click(function(){
		  top.closeDialog();
	 }); 
 });
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>