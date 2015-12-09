<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		<!-- 中间区域 -->
		<div data-options="region:'center'" class="no-border">
			<div class="easyui-layout" data-options="fit:true">
				<input type="hidden" name="sysUserId" id="sysUserId" value="${model.sysUserId }" /> <input type="hidden" name="id" id="id" value="${model.id }" /> <input type="hidden" name="version" id="version" value="${model.version}" /> <input type="text" name="auditStatus" id="auditStatus" value="${model.auditStatus }" />
				<!-- 上部:查询表单 -->
				<div data-options="region:'north'">
					<fieldset>
						<legend>用户信息</legend>
						<table class="table-info">
							<tr>
								<th><label class="required">组别</label></th>
								<td>${model.orgName}</td>
								<th><label class="required">用户姓名</label></th>
								<td>${model.name}</td>
							</tr>
							<tr>
								<th><label class="required">用户编号</label></th>
								<td>${model.employeeNo}</td>
								<th><label>首字母缩写</label></th>
								<td>${model.acronym}</td>
							</tr>
							<tr>
								<th><label class="required">用户性别</label></th>
								<td>${model.sexName}</td>
								<th><label class="required">身份证号</label></th>
								<td>${model.idNumber}</td>
							</tr>
							<c:if test="${model.auditStatus!='I'}">
								<tr class="row2">
									<th><label>审核意见</label></th>
									<td colspan="3"><textarea data-options="required:false" maxlength="512" name="remarks" style="width: 500px" readonly="readonly" value="${model.auditOpintion}"></textarea></td>
								</tr>
							</c:if>
						</table>
					</fieldset>
				</div>
				<!-- 下部:显示列表-->
				<div id="flexgrid" data-options="region:'center'" class="content">
					<table id="roleTables"></table>
				</div>
				<div data-options="region:'south'" class="buttons">
					<shiro:hasPermission name="check:user:edit"><input type="button" class="button" id="btnAccessBtn" value="通 过" /> <input type="button" class="button" id="btnRefuseBtn" value="否 决" /></shiro:hasPermission><input type="button" class="button" id="cancelBtn" value="取 消" />
				</div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	//<![CDATA 
	var flexgirdHeight = 180;
	var aduitstatus;
	$("#roleTables").flexigrid({
		title : '用户角色列表',
		dataType : 'json',
		autoload : true,
		url : "${ctx_admin}/security/applyuserrole/applyUserRole",
		checkbox : false,
		usepager : false,
		colModel : [ {
			display : '角色名称',
			name : 'roleName',
			sortable : true,
			align : 'center',
			width : "400"
		}, {
			display : '变更类型',
			name : 'flagType',
			sortable : true,
			align : 'center',
			width : "220"
		} ]
	});

	var id = '${model.id}' + ',' + '${model.version}';

	$("#cancelBtn").click(function() {
		top.closeDialog();
	});

	$("#btnRefuseBtn").click(function() {
		aduitstatus = 'R';
		var reason = $("#reason").val();
		var url = "${ctx_admin}/security/applyuserrole/review/" + id;
		$.ajax({
			type : 'post',
			url : url,
			data : {
				id : id,
				auditOpintion : reason,
				auditStatus : aduitstatus
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
		aduitstatus = 'A';
		var reason = $("#reason").val(); 
		var url = "${ctx_admin}/security/applyuserrole/review/" + id;
		$.ajax({
			type : 'post',
			url : url,
			data : {
				id : id,
				auditOpintion : reason,
				auditStatus : aduitstatus
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
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>