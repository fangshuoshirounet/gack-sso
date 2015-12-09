<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<script src="${ctx}/resources/themes/${THEME}/js/func.md5.js" type="text/javascript"></script>
<style>
<!--
.info-form ul li label {display: inline-block;width: 103px; text-align: left;padding-left: 12px;}
-->
</style>
<div class="easyui-layout" data-options="fit:true">
 	<div class="info-form" data-options="region:'center'">
	<form method="post" id="editForm">
		 	<fieldset>
		 	 	<legend>用户信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label class="required">旧密码</label></th><td>
						<input class="easyui-validatebox" id="pwd" validType="checkUserPwd['#pwd1','${ctx}']"  invalidMessage="您输入的密码与原密码不匹配！" type="password" maxlength="32" name="oldpwd" required="required" />
					</td></tr>
					<tr><th>
						<label class="required">新密码</label></th><td>
						<input class="easyui-validatebox" id="pwd1"  type="password" maxlength="32" name="newpwd" required="required"/>
					</td></tr>
					<tr><th>
						<label class="required">重复密码</label></th><td>
						<input class="easyui-validatebox" id="pwd2" validType="equalTo['#pwd1']" invalidMessage="两次输入密码不匹配"  type="password" maxlength="32" name="renewpwd" required="required" />
					</td></tr>
		 		 </table>
		 	</fieldset>
	</form>
	 </div>
	<div class="buttons" data-options="region:'south'">
	    <input type="button" class="button" id="btnSubmit"  value="确 定">
		<button class="button" id="btnCancel">取 消</button>
	</div>
</div> 



<script type="text/javascript">
//<![CDATA[
$(function(){
	 //验证
	 $(".info-group input:not([type='radio']):not([type='checkbox'])").css({"width": "180px"});
	 

	 $('#editForm').ajaxForm({
	    url:"${ctx}/saveuserpwd", 
	    beforeSubmit:function(formData, jqForm){ 
	    	var tag = jqForm.form('validate');
    		if(tag){  
    			for(var i =0;i<formData.length;i++){
    				if(formData[i].name=='oldpwd'){
    					formData[i].value=new $.Md5().hex_md5($("#pwd").val());
    				}
    				if(formData[i].name=='newpwd'){
    					formData[i].value=new $.Md5().hex_md5($("#pwd1").val());
    				}
    				if(formData[i].name=='renewpwd'){
    					formData[i].value=new $.Md5().hex_md5($("#pwd2").val());
    				}
    			}
    		}
    		return tag;
        },
		success:function(data){
			 
		 	  	  top.closeDialog();
				  $.messager.show({title : '温馨提示:',msg :"修改成功" ,timeout : 1000,showType : 'slide'});
		 
			  
		}
	 });
}); 
$("#btnSubmit").click(function() {
	$("#editForm").submit();
}); 
//关闭页面
$("#btnCancel").click(function(){
	  top.closeDialog();
});  
//]]>    
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>