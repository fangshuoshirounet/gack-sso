<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<!-- 区域左侧树 -->
	<div class="tree" data-options="region:'west',split:true">
		<div class="easyui-layout" data-options="fit:true" title='选择用户'>
			<div data-options="region:'center'" title="组织机构" fit="true" class="tree-center">
				<table class="table-info" id="organizationTree"></table>
			</div>
			<div data-options="region:'south'" class="tree-buttons"></div>
		</div>
	</div>
	<!-- 中间区域 -->
	<div data-options="region:'center'" class="no-border">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部:查询表单 -->
			<div data-options="region:'north'" class="search" id="">
				<fieldset>
					<legend>筛选条件</legend>
					<form id="searchForm">
						<input type="hidden" name="orgId" id="orgId" />
						<table class="search-table">
							<tr>
								<th>用户名</th>
								<td><input type="text" name="name" maxlength="20" /></td>
								<td class="search-buttons"><shiro:hasPermission name="security:sysuserpermission:query">
										<input type="button" class="button" id="qryBtn" value="查询" />
									</shiro:hasPermission> <input type="button" class="button" id="clearBtn" value="清除" /></td>
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
				<shiro:hasPermission name="security:sysuserpermission:view">
					<input type="button" class="button" id="showBtn" value="查看" />
				</shiro:hasPermission>
				<shiro:hasPermission name="security:sysuserpermission:add">
					<input type="button" class="button" id="nextBtn" value="下一步" />
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//<![CDATA[
	var permission = '${permission}';
	function initTree() {
		$('#organizationTree').tree({
			url : "${ctx_admin}/security/sysUserpermission/orgtreejson",
			onClick : function(node) {
				$("#orgId").val(node.id);
				$("#flexTable").flexReload();
			}
		});
	}
	var selectsysUserId = '${model.sysUserId}';
	$(function() {
		initTree();
<%-- 区域右侧tab加载start--%>
	$("#flexTable")
				.flexigrid(
						{
							url : "${ctx_admin}/organization/sysuser?search=true",
							autoload : true,
							title : "用户列表",
							hiddenArea : [ {
								name : "sysUserId"
							} ],//隐藏标识 在页面不显示
							colModel : [
									{
										display : '工号',
										name : 'employeeNo',
										width : 130,
										sortable : false,
										align : 'center'
									},
									{
										display : '组织机构 ',
										name : 'orgName',
										width : 125,
										sortable : false,
										align : 'center'
									},
									{
										display : '姓名',
										name : 'name',
										width : 130,
										sortable : false,
										align : 'center'
									},
									{
										display : '性别',
										name : 'sexName',
										width : 50,
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
										display : '密码失效时间',
										name : 'pwdInactiveTime',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '手机',
										name : 'mobile',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '家庭电话',
										name : 'homePhone',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '电子邮件',
										name : 'email',
										width : 200,
										sortable : false,
										align : 'center'
									},
									{
										display : '操作',
										name : 'operate',
										width : 120,
										align : 'center',
										operateColumn : [ {
											html : '<shiro:hasPermission name="security:sysuserpermission:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'
										} ]
									} ],
							onSuccess : function() {
								if (selectsysUserId != null
										&& selectsysUserId.length > 0) {
									$("#flexTable tr ")
											.each(
													function(n, tr) {
														var roleId = $(tr)
																.find(
																		"input[name='staffId']")
																.val();
														if (selectsysUserId == roleId) {
															var input = $($(tr)
																	.children()
																	.eq(0));
															var checkbox = input
																	.find("input[type='checkbox']");
															$(checkbox).attr(
																	"checked",
																	"checked");
															if (n % 2 == 0) {
																$(tr)
																		.attr(
																				"class",
																				"erow trSelected");
															} else {
																$(tr)
																		.attr(
																				"class",
																				"trSelected");
															}
														}
													});
								}
							}
						}); 
$("#showBtn,[name='showLink']").live("click",function (event,data) {
	 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
	var key=getSelected({nameGroup:'sysUserId',currentObj:$(this)});
	 var url='${ctx_admin}/security/sysUserpermission/' + key;
	 top.openDialog({href:url,resizable : false,height: 575,width: 654,title: "查看用户信息",modal : true});
	}});



	//查询
		$("#qryBtn").click(function() {
			$("#flexTable").flexRefresh();
		});

		//重置
		$("#clearBtn").click(function() {
			$("#searchForm")[0].reset();
		});
	});
	$("#nextBtn")
			.click(
					function() {
						var len = $('.trSelected').length;
						if (len == 0) {
							jQuery.messager.alert('删除提示', '请先选择系统用户!', 'info');
							return;
						}
						if (len > 1) {
							jQuery.messager.alert('删除提示', '选择系统用户只能是一项!',
									'info');
							return;
						}
						var sysUserId = getSelectRowByName("sysUserId");
						var url = '${ctx_admin}/security/sysUserpermission/permission?sysUserId='
								+ sysUserId + '&permission=' + permission;
						if (permission == 'role') {
							url = '${ctx_admin}/security/sysUserpermission/searchRole?sysUserId='
									+ sysUserId + "&permission=" + permission;
						}
						window.location.href = url;
					});
	//刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
	$("#step2").click(function() {
		$("#nextBtn").click();
	});
	//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>