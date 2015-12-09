<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		<div class="info-form" data-options="region:'center'">
			<fieldset>
				<legend>查看数据字典</legend>
				<table class="table-info">
					<tr>
						<th><label>表名称</label></th>
						<td>${model.tableName}</td>
					</tr>
					<tr>
						<th><label>字段名称</label>
						</th>
						<td>${model.columnName}</td>
					</tr>
					<tr>
						<th><label>系统状态</label></th>
						<td>${model.stsId}</td>
					</tr>
					<tr>
						<th><label>字段描述</label></th>
						<td>${model.stsWords}</td>
					</tr>
					<tr>
						<th><label>排列序号</label></th>
						<td>${model.orderId}</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<div class="buttons" data-options="region:'south'">
			<input type="button" class="button" id="closeSubmitBtn"
				value="关闭" />
		</div>
	</form>
</div>
<script type="text/javascript">
	//<![CDATA[
	$(function() {
		//关闭页面
		$("#closeSubmitBtn").click(function() {
			top.closeDialog();
		});
	});
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>