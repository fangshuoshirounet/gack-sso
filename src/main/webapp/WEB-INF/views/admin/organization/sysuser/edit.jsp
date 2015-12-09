<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
		<form method="post" id="editForm">
			  <input type="hidden"   name="sysUserId" value="${model.sysUserId}" ></input>
			  <input name="createDate" value="${model.createDate}" type="hidden"/>
		<input name="creator" value="${model.creator}" type="hidden"/>
		<input name="version" value="${model.version}" type="hidden"/> 
		<input name="loginName" value="${model.loginName}" type="hidden"/>
		<input name="password" value="${model.password}" type="hidden"/>
		<input name="version" value="${model.version}" type="hidden"/>
		<input name="sts" value="${model.sts}" type="hidden"/>
		<input name="initPwdFlag" value="${model.initPwdFlag}" type="hidden"/> 
		<input name="name" value="${model.name}" type="hidden"/> 
           	  <fieldset>
                <legend>用户信息</legend>
                <table class="table-info">
                <tr>
						<th><label class="required">组别</label></th>
						<td colspan="3"><input id="parentOrgTree" value="${model.orgId}" name="orgId" data-options="multiple:false,required:true" style="width: 182px"></td>
						 
					</tr>
					<tr>
						<th><label class="required">用户姓名</label></th>
						<td>${model.name}</td>
						<th><label>首字母缩写</label></th>
						<td><input data-options="required:false" type="text" maxlength="8" name="acronym" value="${model.acronym}"/></td>
					</tr>
					<tr><th><label class="required">用户编号</label></th>
						<td>${model.employeeNo}</td><th>
                        <label class="required">用户性别</label></th><td>
                        <tags:radio-enumerate required="false" columnName="SEX" tableName="SYS_USER" name="sex" value="${model.sex}"/>
                    </td></tr>
                    <tr><th><label class="required">身份证号</label></th>
						<td><input class="easyui-validatebox"  data-options="required:true" validType="cid" type="text"
							maxlength="18" name="idNumber"  value="${model.idNumber}"/></td><th>
                        <label class="required">职责</label></th><td>
                         <select  name="dutyId" required="true" class="easyui-combobox" style="width:182px;">
						    
						     <c:forEach items="${dutyList}" var="duty">
						    	<c:choose>
						    	    <c:when test="${duty.dutyId == model.dutyId}"> 
						                <option value="${duty.dutyId}" selected>${duty.name}</option>
						            </c:when>
						            <c:otherwise>
						               <option value="${duty.dutyId}">${duty.name}</option>
						            </c:otherwise>
					            </c:choose>
						    </c:forEach> 
						</select>
                    </td></tr> 
                     
					<tr>
						<th><label class="required">生日</label></th>
						<td><input type="text" name="birthday" class="easyui-validatebox" required="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${model.birthday}"/></td>
						<th><label>公司邮箱</label></th>
						<td><input class="easyui-validatebox" id="email" type="text" maxlength="32" validType="email" name="email" value="${model.email}"></input></td>
					</tr>
					<tr>
						<th><label>家庭电话</label></th>
						<td><input data-options="required:true" type="text" maxlength="12" name="homePhone" value="${model.homePhone}"/></td>
						<th><label>手机</label></th>
						<td><input data-options="required:true" type="text" maxlength="11" name="mobile" value="${model.mobile}"/></td>
					</tr>
					<tr>
						<th><label class="required">用户登录名</label></th>
						<td>${model.loginName}</td>
						<th><label class="required">密码失效时间</label></th>
						<td><input type="text" name="pwdInactiveTime" class="easyui-validatebox" required="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${model.pwdInactiveTime}" /></td>
					</tr> 
					<tr class="row2">
						<th><label>备注</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px" value="${model.remarks}"></textarea></td>
					</tr>
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
$(function(){
	 $('#email').validatebox({ 
		required:false 
	 }); 
	 $("#parentOrgTree").combotree({
		 url:'${ctx_admin}/organization/organization/treejson?isChild=true'
   	 });
	 //验证
	 var key="${model.sysUserId}";
	 $('#editForm').ajaxForm({ 
		url:"${ctx_admin}/organization/applyuser/"+key+"?_method=PUT", 
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
		$("#editForm").submit();
	 });
 	//关闭页面
	 $(function() {
		$("#btnCancel").click(function() {
			 top.closeDialog();
		});	 
	});
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>