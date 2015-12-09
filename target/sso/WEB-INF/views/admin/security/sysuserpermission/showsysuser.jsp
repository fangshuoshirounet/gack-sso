<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		<input type="hidden" name="staffId" value="${model.staffId}"/>
        <div class="info-form" data-options="region:'center'">
            <fieldset>
                <legend>系统用户信息</legend>
                <table class="table-info">
					<tr><th>
						<label>用户登录名</label></th><td>
						${model.sysUser.loginName}&nbsp;
					</td></tr>
					<tr><th>
						<label>密码失效时间</label></th><td>
						${model.sysUser.pwdInactiveTime}&nbsp;
					</td></tr>
					<tr><th>
						<label >初始密码 y/n</label></th><td>
							<tags:showlabel-enumerate tableName="SYS_USER" columnName="INIT_PWD_FLAG" value="${model.sysUser.initPwdFlag}"/>
					</td></tr>
					<tr class="row2"><th>
						<label>备注</label></th><td>
						<textarea name="remarks" readonly="readonly">${model.sysUser.remarks}</textarea>
					</td></tr>
                </table>
            </fieldset>
        </div>
        <div class="buttons" data-options="region:'south'">
            <input type="button" class="button" id="closeBtn" value="关闭"/>
        </div>
    </form>
</div>
<script type="text/javascript">
//<![CDATA[
$(function(){
	
 });
//重置页面
$("#closeBtn").click(function(){
	 top.closeDialog();
}); 
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>