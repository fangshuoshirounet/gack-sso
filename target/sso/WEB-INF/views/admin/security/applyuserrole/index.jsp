<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<!-- 中间区域 -->
	<div data-options="region:'center'" class="no-border">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部:查询表单 -->
			<div data-options="region:'north'" class="search">
				<fieldset>
					<legend>筛选条件</legend>
					<form id="serachForm">
					    <input type="hidden" name="type" id="type" value="${type}" /> 
						<table class="search-table">
							<tr>
								<th>员工姓名</th>
								<td><input type="text" name="name" maxlength="20" /></td>
								<td class="search-buttons"><shiro:hasPermission name="check:user:query">
										<input type="button" class="button" id="qryBtn" value="查询" />
										<input type="button" class="button" id="clearBtn" value="清除" />
									</shiro:hasPermission></td>
							</tr>
						</table>
					</form>
				</fieldset>
			</div>
			<!-- 下部:显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="flexTable"></table>
			</div>
			<div data-options="region:'south'" class="buttons"> 
				<shiro:hasPermission name="check:user:view">
						    <input type="button" class="button" id="showBtn" value="查看" />
				</shiro:hasPermission>
				<shiro:hasPermission name="check:user:edit">
		
					<input type="button" class="button" id="accessBtn" value="通过" />
					<input type="button" class="button" id="refuseBtn" value="否决" />
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>

<div id="win">
	<div class="easyui-layout" data-options="fit:true">
		<form method="post" id="refuseForm">
			<div class="info-form" data-options="region:'center'"> 
					<table class="table-info">
						<tr class="row3">
							<td  ><textarea data-options="required:true" maxlength="512" name="auditOpintion" id='auditOpintion' style="width: 520px"></textarea></td>
						</tr>
					</table> 
			</div>
			<div class="buttons" data-options="region:'south'">
				 <input type="button" class="button" id="okBtn" value="确定" /><input type="button" class="button" id="btnCancel" value="取消" />
			</div>
		</form>
	</div>

</div>

<script type="text/javascript">
	//<![CDATA[ 
	var auditStatus; 
	$(function() {
		$("#flexTable")
				.flexigrid(
						{
							title : '用户列表',
							hiddenArea : [ {
								name : "id"
							}, {
								name : "version"
							} ],//隐藏标识 在页面不显示
							url : "${ctx_admin}/security/applyuserrole?search=true",
							colModel : [

									{
											display : '工号',
											name : 'employeeNo',
											width : 120,
											sortable : false,
											align : 'center'
										},
									{
										display : '组别 ',
										name : 'orgName',
										width : 125,
										sortable : false,
										align : 'center'
									},
									{
										display : '姓名',
										name : 'name',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '用户名',
										name : 'loginName',
										width : 120,
										sortable : false,
										align : 'center'
									},
									 
									{
										display : '申请人',
										name : 'applyor',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '申请时间',
										name : 'applyDate',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '操作',
										name : 'operate',
										width : 120,
										align : 'center',
										operateColumn : [
												{
													html : '<shiro:hasPermission name="check:user:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'
												},
												{
													html : '<shiro:hasPermission name="check:user:edit"><a name="accessLink" href="#">通过</a></shiro:hasPermission>'
												},
												{
													html : '<shiro:hasPermission name="check:user:edit"><a name="refuseLink" href="#">否决</a></shiro:hasPermission>'
												} ]
									} ]
						});
		//查询
		$("#qryBtn").click(function() {
			$("#flexTable").flexRefresh();
		});
		$("#clearBtn").click(function() {
			var orgId = $("#orgId").val();
			$("#serachForm")[0].reset();
			$("#orgId").val(orgId);
		});

	});
	var keys;
<%--按钮绑定操作 end--%>
	$("#addBtn").click(function() {
		var url = '${ctx_admin}/security/applyuserrole/new';
		top.openDialog({
			href : url,
			resizable : false,
			height : 430,
			width : 654,
			title : "新建用户申请",
			modal : true
		});
	}); 
	//查看
	$("#showBtn,[name='showLink']").live("click", function(event, data) {
		if (isSelected({
			isMultiSelect : false,
			currentObj : $(this)
		})) {//验证只能选中单行，如查看，修改
			var key = getSelected({
				nameGroup : 'id',
				currentObj : $(this)
			});
			var url = '${ctx_admin}/security/applyuserrole/' + key + '/edit';
			top.openDialog({
				href : url,
				resizable : false,
				height : 430,
				width : 654,
				title : "用户角色申请复核",
				modal : true
			});
		}
	});

	$("#okBtn").click(function() {
		var reason=$("#auditOpintion").val(); 
		var url = "${ctx_admin}/security/applyuserrole/review/" + keys;
	 	$.ajax({
			type : 'post',
			url : url,
			data : {
				id : keys,
				auditOpintion : reason,
				auditStatus:auditStatus
			},
			dataType : 'json',
			success : function(data) {
				$.messager.show({
					title : '温馨提示:',
					msg : data.message,
					timeout : 2000,
					showType : 'slide'
				});
				$('#win').window('close'); 
				$("#flexTable").flexReload();
			}
		}); 
		 
	});
	$("#btnCancel").click(function() {
		$("#auditOpintion").val(''); 
		auditStatus='';
		$('#win').window('close'); 
	});
	//拒绝
	$("#refuseBtn,[name='refuseLink']").live("click", function(event, data) {
		 keys = getSelected({
			nameGroup : 'id,version',
			currentObj : $(this)
		}); 
		$("#auditOpintion").val(''); 
		auditStatus='R';
		if (isSelected({
			isMultiSelect : true,
			currentObj : $(this)
		})) { 
			$('#win').window({
				width : 545,
				height : 200,
				resizable : false,
				collapsible	 : false,
				minimizable	 : false,
				maximizable : false,
				title : "填写复核理由",
				modal : true
			});

		}
	});
	
	
	//通过
	$("#accessBtn,[name='accessLink']").live("click", function(event, data) {
		 keys = getSelected({
			nameGroup : 'id,version',
			currentObj : $(this)
		}); 
		$("#auditOpintion").val(''); 
		auditStatus='A';
		if (isSelected({
			isMultiSelect : true,
			currentObj : $(this)
		})) {//验证只能选中单行，如查看，修改
			$('#win').window({
				width : 545,
				height : 200,
				resizable : false,
				collapsible	 : false,
				minimizable	 : false,
				maximizable : false,
				title : "填写复核理由",
				modal : true
			});

		}
	});
	
	
	//刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>