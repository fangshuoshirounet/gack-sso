<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<script src="${ctx}/resources/themes/${THEME}/js/func.md5.js" type="text/javascript"></script>
<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
		<form method="post" id="addForm">
			<fieldset>
				<legend> 用户信息 </legend>
				<table class="table-info">
					<tr>
						<th><label class="required">组别</label></th>
						<td colspan="3"><input id="parentOrgTree" value="${model.orgId}" name="orgId" data-options="multiple:false,required:true" style="width: 182px"></td>
						 
					</tr>
					<tr>
						<th><label class="required">用户姓名</label></th>
						<td><input class="easyui-validatebox" type="text" maxlength="16" name="name" required="true" /></td>
						<th><label>首字母缩写</label></th>
						<td><input data-options="required:false" type="text" maxlength="8" name="acronym" /></td>
					</tr>
					<tr><th><label class="required">用户编号</label></th>
						<td><input data-options="required:true" type="text"
							maxlength="8" name="employeeNo"  value="${model.employeeNo}"/></td><th>
                        <label class="required">用户性别</label></th><td>
                        <tags:radio-enumerate required="false" columnName="SEX" tableName="SYS_USER" name="sex" value="M"/>
                    </td></tr>
                    	<tr><th><label class="required">身份证号</label></th>
						<td><input class="easyui-validatebox"  data-options="required:true" validType="cid" type="text"
							maxlength="18" name="idNumber"  value="${model.idNumber}"/></td><th>
                        <label class="required">职责</label></th><td>
                         <select  name="dutyId" required="true" class="easyui-combobox" style="width:182px;">
						    <c:forEach items="${dutyList}" var="duty">
				                <option value="${duty.dutyId}">${duty.name}</option>
						    </c:forEach>
						</select>
                    </td></tr> 
					<tr>
						<th><label class="required">生日</label></th>
						<td><input type="text" name="birthday" class="easyui-validatebox" required="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
						<th><label>公司邮箱</label></th>
						<td><input class="easyui-validatebox" id="email" type="text" maxlength="32" validType="email" name="email" value=""></input></td>
					</tr>
					<tr>
						<th><label class="required">家庭电话</label></th>
						<td><input class="easyui-validatebox"  data-options="required:true" type="text" validType="homePhone"  maxlength="13" name="homePhone" /></td>
						<th><label class="required">手机</label></th>
						<td><input class="easyui-validatebox" data-options="required:true" type="text" validType="mobile"  maxlength="11" name="mobile" /></td>
					</tr>
					<tr>
						<th><label class="required">用户登录名</label></th>
						<td><input class="easyui-validatebox" type="text" validType="checkUserName['${ctx}','请输入正确的用户登录名']" name="loginName" required="true" id="loginName"></input></td>
						<th><label class="required">用户密码</label></th>
						<td><input class="easyui-validatebox" type="password" style="width: 180px;" name="password" required="true" id="password"></input></td>
					</tr>
					<tr>
						<th><label class="required">密码失效时间</label></th>
						<td><input type="text" name="pwdInactiveTime" class="easyui-validatebox" required="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
						<th><label class="required">是否初始密码 </label></th>
						<td><tags:radio-enumerate required="true" columnName="INIT_PWD_FLAG" tableName="SYS_USER" name="initPwdFlag" /></td>
					</tr>
					<tr class="row2">
						<th><label>备注</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px"></textarea></td>
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
	$(".info-form input[type='text']").css({
		"width" : "180px"
	});
	$(function() { 

		$("input[name='initPwdFlag']").bind("click", function() {
			if ("N" == $(this).val()) {
				$("#initPwdFlag").css("display", "inline");
				$("#password").attr("required", "true");
				$("#password").attr("class", "easyui-validatebox");
			} else {
				$("#initPwdFlag").css("display", "none");
				$("#password").attr("required", "false");
				$("#password").attr("class", "");
			}
		});

		//表单提交
		$("#btnSubmit").click(function() {
			$("#addForm").submit();
		});
		$("#parentOrgTree").combotree({
			 url:'${ctx_admin}/organization/organization/treejson?isChild=true'
		});
		$('#addForm').ajaxForm(
				{
					url : "${ctx_admin}/organization/applyuser",
					beforeSubmit : function(formData, jqForm) {
						
						var tag = jqForm.form('validate'); 
						if (tag) {
							for (var i = 0; i < formData.length; i++) {
								if (formData[i].name == 'password') {
									formData[i].value = new $.Md5().hex_md5($(
											"#password").val());
								}
							}
						}
						return tag;
					},
					success : function(data) {
						getDtt().refresh();
						top.closeDialog();
						$.messager.show({
							title : '温馨提示:',
							msg : data.message,
							timeout : 1000,
							showType : 'slide'
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