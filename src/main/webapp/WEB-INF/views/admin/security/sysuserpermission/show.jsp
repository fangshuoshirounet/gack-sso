<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
<form method="post" id="viewForm">
	<!-- 中间区域 -->
	<div data-options="region:'center'" class="no-border">
		<div class="easyui-layout" data-options="fit:true">
		<input type="hidden" name="sysUserId" id="sysUserId" value="${model.sysUserId }"/>
			<!-- 上部:查询表单 -->
			<div data-options="region:'north'" >
				<fieldset>
                <legend>用户信息</legend>
                  <table class="table-info">
                <tr>
						<th><label class="required">组别</label></th>
						<td colspan="3">${model.orgName}</td>
						 
					</tr>
					<tr>
						<th><label class="required">用户姓名</label></th>
						<td>${model.name}</td>
						<th><label>首字母缩写</label></th>
						<td>${model.acronym}</td>
					</tr>
					<tr><th><label class="required">用户编号</label></th>
						<td>${model.employeeNo}</td><th>
                        <label class="required">用户性别</label></th><td>
                        <tags:radio-enumerate required="false" columnName="SEX" tableName="SYS_USER" name="sex" value="${model.sex}"/>
                    </td></tr>
                    <tr><th><label class="required">身份证号</label></th>
						<td>${model.idNumber}</td><th>
                        <label class="required">职责</label></th><td>
                         ${duty.name}
                    </td></tr> 
                     
					<tr>
						<th><label class="required">生日</label></th>
						<td>${model.birthday}</td>
						<th><label>公司邮箱</label></th>
						<td>${model.email}</td>
					</tr>
					<tr>
						<th><label>家庭电话</label></th>
						<td>${model.homePhone}</td>
						<th><label>手机</label></th>
						<td>${model.mobile}</td>
					</tr>
					<tr>
						<th><label class="required">用户登录名</label></th>
						<td>${model.loginName}</td>
						<th><label class="required">密码失效时间</label></th>
						<td>${model.pwdInactiveTime}</td>
					</tr> 
					<tr class="row2">
						<th><label>备注</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" readonly="readonly" style="width: 500px" value="${model.remarks}"></textarea></td>
					</tr>
				</table> 
            </fieldset> 
			</div>
			<!-- 下部:显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="roleTables"></table>
			</div>
			<div data-options="region:'south'" class="buttons">
				<input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
			</div>
		</div>
	</div>
	</form>
</div>
 
<script type="text/javascript">
//<![CDATA
     var flexgirdHeight=180;
	$(function() {  
		$("#roleTables").flexigrid({
			title :'角色列表',
			dataType : 'json', 
			autoload : true,
			url:"${ctx_admin}/public/sysuserrole/sysUserRole",
			checkbox:false,
			usepager: false,
			colModel : [{
				display : '角色名称',
				name : 'roleName',
				sortable : true,
				align : 'center',
				width:"625"
			}]
		});
	});
	$("#closeSubmitBtn").click(function(){
		 top.closeDialog();
	}); 
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>