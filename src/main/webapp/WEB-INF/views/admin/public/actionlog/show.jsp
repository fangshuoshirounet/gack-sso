<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
	  <div class="info-form" data-options="region:'center'">
		<fieldset>
			<legend>操作历史</legend>
			<table class="table-info">
					<tr><th>
						<label>用户ID</label></th><td>
						${model.sysUserId}
					</td></tr>
					<tr><th>
						<label>菜单ID</label></th><td>
						${model.menuId}
					</td></tr>
					<tr><th>
						<label>操作类型</label></th><td>
						${model.actionType}
					</td></tr>
					<tr><th>
						<label>操作时间</label></th><td>
						${model.actionTime}
					</td></tr>
					<tr><th>
						<label>菜单URL</label></th><td>
						${model.menuUrl}
					</td></tr>
					<tr><th>
						<label>系统内容</label></th><td>
						${model.sysText}
					</td></tr>
					<tr><th>
						<label>操作内容</label></th><td>
						<div>${model.actionText}</div>
					</td></tr>
			</table>
		</fieldset>
		</div>
		 <div class="buttons" data-options="region:'south'">
	    <input type="button" class="button" id="closeSubmitBtns" value="关闭"/>
	</div> 
	</form>
</div>
	   
<script type="text/javascript">
//<!CDATA[
	$(function() {
		//关闭页面
		$("#closeSubmitBtns").click(function() {
			 top.closeDialoglevel2();
		});
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>