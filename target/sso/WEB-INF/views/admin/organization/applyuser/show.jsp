<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		 <div class="info-form" data-options="region:'center'">
		 	 <fieldset>
                <legend>用户信息</legend>
                  <table class="table-info">
                <tr>
						<th><label class="required">组别</label></th>
						<td colspan="3">${model.orgName}<c:if test="${model.orgName!=model.orgNameOld && model.changeType =='U'}"><span  style="color:#F00">(${model.orgNameOld})</span>
						</c:if></td>
						 
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
                       ${model.sexName}
                    </td></tr>
                    <tr><th><label class="required">身份证号</label></th>
						<td>${model.idNumber}<c:if test="${model.idNumber!=model.idNumberOld && model.changeType =='U'}"><span  style="color:#F00">(${model.idNumberOld})</span>
						</c:if></td><th>
                        <label class="required">职责</label></th><td>
                      ${dutyName}<c:if test="${model.dutyName!=model.dutyNameOld && model.changeType =='U'}"><span  style="color:#F00">(${model.dutyName})</span>
						</c:if>
                    </td></tr> 
                     
					<tr>
						<th><label class="required">生日</label></th>
						<td>${model.birthday}<c:if test="${model.birthday!=model.birthdayOld && model.changeType =='U'}"><span  style="color:#F00">(${model.birthdayOld})</span>
						</c:if></td>
						<th><label>公司邮箱</label></th>
						<td>${model.email}<c:if test="${model.email!=model.emailOld && model.changeType =='U'}"><span  style="color:#F00">(${model.emailOld})</span>
						</c:if></td>
					</tr>
					<tr>
						<th><label>家庭电话</label></th>
						<td>${model.homePhone}<c:if test="${model.homePhone!=model.homePhoneOld && model.changeType =='U'}"><span  style="color:#F00">(${model.homePhoneOld})</span>
						</c:if></td>
						<th><label>手机</label></th>
						<td>${model.mobile}<c:if test="${model.mobile!=model.mobileOld && model.changeType =='U'}"><span  style="color:#F00">(${model.mobileOld})</span>
						</c:if></td>
					</tr>
					<tr>
						<th><label class="required">用户登录名</label></th>
						<td>${model.loginName}</td>
						<th><label class="required">密码失效时间</label></th>
						<td>${model.pwdInactiveTime}</td>
					</tr> 
					<tr class="row2">
						<th><label>备注</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px" readonly="readonly" value="${model.remarks}"></textarea></td>
					</tr>
					<c:if test="${model.auditStatus!='I'}"><span  style="color:#F00">(${model.homePhoneOld})</span>
					<tr class="row2">
						<th><label>审核意见</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px" readonly="readonly" value="${model.auditOpintion}"></textarea></td>
					</tr>
					</c:if>
				</table>
            </fieldset>
		 </div>
		 <div class="buttons" data-options="region:'south'">
		 	<input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA
	$(function() {
		$("#closeSubmitBtn").click(function() {
			 top.closeDialog();
		});	 
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>