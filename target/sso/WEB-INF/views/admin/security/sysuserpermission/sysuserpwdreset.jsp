<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<script src="${ctx}/resources/themes/${THEME}/js/func.md5.js" type="text/javascript"></script>
<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
	<form id="editForm">
            <fieldset>
                <legend>用户密码信息</legend>
                <table class="table-info">
					<tr><th> 
						<label class="required">重置密码</label></th><td>
						<input type="text" style="width: 182px;" id="password" class="easyui-validatebox" name="sysUser.password" class="easyui-validatebox" required="true"/>
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
$(".info-group input:not([type='radio']):not([type='checkbox'])").css({"width": "190px"});
	 var key="${model.sysUserId}";
	 $('#editForm').ajaxForm({
	    	url: "${ctx_admin}/security/sysuser",
	    	   beforeSubmit:function(formData, jqForm){
	               return jqForm.form('validate');
	           }
    	});
   //表单提交
     $("#btnSubmit").click(function(){
    	var pwd = $("#password").val();
    	if(pwd==null||pwd.length==0){
    		$("#editForm").submit();
    	}else{
    		$("#password").val(new $.Md5().hex_md5($("#password").val()));
    		var password = $("#password").val();
			var random = Math.floor(Math.random()*1000+1);
			var url = '${ctx_admin}/security/sysUserpermission/savePwdReset?sysUser.sysUserId='+key+"&random="+random+"&sysUser.password="+password;
			$.getJSON(url, function (data) {
				  if(data.message!=null){
					  $.messager.show({title : '温馨提示:',msg : data.message,timeout : 222000,showType : 'slide'});
				  } else{
		    		  top.closeDialog();
		    		  $.messager.show({title : '温馨提示:',msg : '重置密码成功！',timeout : 222000,showType : 'slide'});
				  }
			});
    	}
     });	 
	//重置页面
	 $("#btnCancel").click(function(){
		 top.closeDialog();
	 }); 
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>