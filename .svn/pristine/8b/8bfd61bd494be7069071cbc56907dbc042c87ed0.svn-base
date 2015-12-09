<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		<div class="info-form" data-options="region:'center'">
			<fieldset>
				<legend>
					区域信息
				</legend>

				<table class="table-info">
					<tr>
						<th><label class="required">上级区域</label></th>
						<td>${model.parentName}&nbsp;</td>
					</tr>
					<tr>
						<th><label class="required">区域名称</label>
						</th>
						<td>${model.name}&nbsp;</td>
					</tr>

					<tr>
						<th><label >区域规格</label></th>
						<td>${model.areaSpecId}&nbsp;</td>
					</tr>
					<tr>
						<th><label >区域编码</label></th>
						<td>${model.code}&nbsp;</td>
					</tr>
					<tr>
						<th><label >名称缩写</label></th>
						<td>${model.abbr}&nbsp;</td>
					</tr>
					<tr>
						<th><label >全名</label></th>
						<td>${model.fullName}&nbsp;</td>
					</tr>
					<tr>
						<th><label >排序位置</label></th>
						<td>${model.sortPosition}&nbsp;</td>
					</tr>
					<tr>
						<th><label >是否中心位置</label></th>
						<td><tags:showlabel-enumerate tableName="AREA" columnName="IS_CENTER" value="${model.isCenter}"/></td>
					</tr>
					<tr>
						<th><label >经度</label></th>
						<td>${model.longitude}&nbsp;</td>
					</tr>
					<tr>
						<th><label >纬度</label></th>
						<td>${model.latitude}&nbsp;</td>
					</tr>
					<tr>
						<th><label >物理区域标识</label></th>
						<td>${model.geoAreaId}&nbsp;</td>
					</tr>
					<tr>
						<th><label >状态</label></th>
						<td><tags:showlabel-enumerate columnName="STS" tableName="AREA" value="${model.sts}"></tags:showlabel-enumerate>&nbsp;</td>
					</tr>
					<tr>
						<th><label >创建时间</label></th>
						<td>${model.createDate}&nbsp;</td>
					</tr>
					<tr>
						<th><label >操作时间</label></th>
						<td>${model.operateDate}&nbsp;</td>
					</tr>
					<tr class="row2">
						<th><label>备注</label></th>
						<td><textarea name="remarks" readOnly="readonly" >${model.remarks}</textarea></td>
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