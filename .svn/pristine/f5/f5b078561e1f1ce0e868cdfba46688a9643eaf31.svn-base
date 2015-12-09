<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		 <div class="info-form" data-options="region:'center'">
		 	 <fieldset>
                <legend>角色申请信息</legend>
                  <table class="table-info">
               <tr><th>
						<label class="required">角色名称</label></th><td>
						${model.roleName}<c:if test="${model.roleName!=model.roleNameOld && model.changeType =='U'}"><span  style="color:#F00">(${model.roleNameOld})</span>
						</c:if>
					</td></tr>
					<tr><th>
						<label class="required">角色编码</label></th><td>
						${model.roleCode}<c:if test="${model.roleCode!=model.roleCodeOld && model.changeType =='U'}"><span  style="color:#F00">(${model.roleCodeOld})</span>
						</c:if>
					</td></tr>
					<tr class="row2"><th>
						<label class="required"  >角色描述</label></th><td>
						<textarea class="easyui-validatebox"  readonly="readonly" name="roleDesc" required="true" >${model.roleDesc}</textarea>
						<c:if test="${model.roleDesc!=model.roleDescOld && model.changeType =='U'}"><textarea class="easyui-validatebox"  style="color:#F00" readonly="readonly" name="roleDesc" required="true" >${model.roleDescOld}</textarea>
						</c:if>
					</td></tr>
					<c:if test="${model.auditStatus!='I'}">
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