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
					<tr class="row2">
						<th><label>复核意见</label></th>
						<td colspan="3"><textarea data-options="required:false" maxlength="512" name="reason" style="width: 500px"   id="reason"></textarea></td>
					</tr>
				</table>
            </fieldset>
		 </div>
		 <div class="buttons" data-options="region:'south'">
		 <shiro:hasPermission name="check:user:edit">
		 <input type="button" class="button" id="btnAccessBtn" value="通 过"/>
		 <input type="button" class="button" id="btnRefuseBtn" value="否 决"/>
		 </shiro:hasPermission>
		 <input type="button" class="button" id="cancelBtn" value="取 消"/> 
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA
     var aduitstatus; 
     var id='${model.id}'+','+'${model.version}';
	$(function() { 
	 
		$("#cancelBtn").click(function() {
			 top.closeDialog();
		});	 
		$("#btnRefuseBtn").click(function() {
			aduitstatus='R';
			var reason=$("#reason").val(); 
			var url = "${ctx_admin}/organization/applyuser/review/" + id;
		 	$.ajax({
				type : 'post',
				url : url,
				data : {
					id : id,
					auditOpintion : reason,
					auditStatus:aduitstatus
				},
				dataType : 'json',
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
		});
		//通过
		$("#btnAccessBtn").click(function() {
			aduitstatus='A';
			var reason=$("#reason").val(); 
			var url = "${ctx_admin}/organization/applyuser/review/" + id;
		 	$.ajax({
				type : 'post',
				url : url,
				data : {
					id : id,
					auditOpintion : reason,
					auditStatus:aduitstatus
				},
				dataType : 'json',
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
		});
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>