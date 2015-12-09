<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<div class="info-form" data-options="region:'center'">
		<form method="post" id="addForm">
			<fieldset>
				<legend>
					区域信息
				</legend>
				<table class="table-info">
					<tr>
						<th><label>上级区域</label></th>
						<td><input data-options="multiple:false,required:false"
							value="${model.parentId}" style="height: 20px; width: 182px"
							name="parentId" id="parentTree" /></td>
					</tr>

					<tr>
						<th>
				<label class="required">区域名称</label>
					</th>
					<td><input class="easyui-validatebox" type="text" name="name"
						maxlength="20" required="true"></input></td>
					</tr>

					<tr>
						<th>
					<label class="required">区域规格</label></th>
					<td>
					<input type="text" class="easyui-numberbox" precision="0"
						name="areaSpecId" maxlength="12" required="true"></input>
					</td>
					</tr>

					<tr>
						<th>
					<label class="required">区域编码</label>
					</th>
					<td><input class="easyui-validatebox" type="text" name="code"
						maxlength="20" required="true"></input></td>
					</tr>

					<tr>
						<th>
					<label>名称缩写</label></th>
					<td>
					<input type="text" name="abbr" maxlength="10"></input>
					</td>
					</tr>

					<tr>
						<th>
					<label>全名</label></th>
					<td>
					<input type="text" name="fullName" maxlength="20"></input>
					</td>
					</tr>

					<tr>
						<th>
					<label class="required">排序位置</label>
					</th>
					<td><input class="easyui-numberbox" precision="0" type="text"
						name="sortPosition" maxlength="4" required="true"></input></td>
					</tr>

					<tr>
						<th>
					<label class="required">是否中心位置</label>
					</th>
					<td><tags:radio-enumerate required="true" columnName="IS_CENTER"
							tableName="AREA" name="isCenter" /></td>
					</tr>

					<tr>
						<th>
					<label>经度</label></th>
					<td>
					<input class="easyui-numberbox" data-options="required:false"
						type="text" precision="2" name="longitude" maxlength="10"></input>
					</td>
					</tr>

					<tr>
						<th>
					<label>纬度</label></th>
					<td>
					<input class="easyui-numberbox" data-options="required:false"
						type="text" precision="2" name="latitude" maxlength="10"></input>
					</td>
					</tr>

					<tr>
						<th>
					<label>物理区域标识</label></th>
					<td>
					<input class="easyui-numberbox" data-options="required:false"
						type="text" name="geoAreaId" maxlength="12"></input>
					</td>
					</tr>
					<tr  class="row2">
					<th><label>备注</label></th>
					<td> <textarea
							name="remarks"></textarea></td>
					</tr>
					</table>
			</fieldset>
		</form>
	</div>
	<div class="buttons" data-options="region:'south'">
		<button class="button" id="btnSubmit">
			确 定
		</button>
		<button class="button" id="btnCancel">
			取 消
		</button>
	</div>
</div>
<script type="text/javascript">
	//<![CDATA[
	$(function() {
		$(".info-form input[type='text']").css({
			"width" : "180px"
		});
		$("#parentTree").combotree({
			url:'${ctx_admin}/public/area/treejson?isChild=true'
		});
 
	//验证
		$('#addForm')
				.ajaxForm(
						{
							url : "${ctx_admin}/public/area",
							beforeSubmit : function(formData, jqForm) {
								return jqForm.form('validate');
							},
							success : function(data) {
								getDtt().refresh();
								getDtt().initTree();
								top.closeDialog();
								$.messager
										.show({
											title : '温馨提示:',
											msg : data.message,
											timeout : 2000,
											showType : 'slide'
										});
							}
						});

		//表单提交
		$("#btnSubmit").click(function() {
			if ($("#addForm").form('validate')) {
				$("#addForm").submit();
			}
		});

		//重置页面
		$("#btnCancel").click(function() {
			top.closeDialog();
		});

	});
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>