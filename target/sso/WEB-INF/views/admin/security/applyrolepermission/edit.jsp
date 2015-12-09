<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
<div class="easyui-layout" data-options="fit:true">
<form method="post" id="viewForm">
<input type="hidden" name="roleId" id="roleId" value="${model.roleId}"/>
<input type="hidden" name="roleName" id="roleName" value="${model.roleName}"/>
		<input type="hidden" name="applyRoleId" id="applyRoleId" value="${model.id }"/>
		<input type="text" name="auditStatus" id="auditStatus" value="${model.auditStatus }"/>
<!-- 上部:查询表单 -->
			<div data-options="region:'north'" >
				<fieldset>
                <legend>角色申请信息</legend>
                  <table class="table-info">
               <tr><th>
						<label class="required">角色名称</label></th><td>
						${model.roleName} 
					</td></tr>
					<tr><th>
						<label class="required">角色编码</label></th><td>
						${model.roleCode} 
					</td></tr>
					<tr class="row2"><th>
						<label class="required"  >角色描述</label></th><td>
						<textarea class="easyui-validatebox"  readonly="readonly" name="roleDesc" required="true" >${model.roleDesc}</textarea> 
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
			<!-- 下部:显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="roleTables"></table>
			</div>
			 <div class="buttons" data-options="region:'south'">
			 <shiro:hasPermission name="check:role:edit">
		 <input type="button" class="button" id="btnAccessBtn" value="通 过"/>
		 <input type="button" class="button" id="btnRefuseBtn" value="否 决"/></shiro:hasPermission>
		 <input type="button" class="button" id="cancelBtn" value="取 消"/> 
         </div>
	</form>
</div>
 
<script type="text/javascript">
//<![CDATA
     var aduitstatus; 
     var id='${model.id}'+','+'${model.version}';
	$(function() { 
		var flexgirdHeight=470; 
 		$("#roleTables").flexigrid({
 			title :"角色功能视图列表",
 			dataType : 'json', 
 			autoload : true,
 			height : flexgirdHeight-30, 
 			url:"${ctx_admin}/security/applyrolepermission/applyRolePermission",
 			usepager: false,
 			colModel : [{
 				display : '菜单名称',
 				name : 'menuName',
 				sortable : true,
 				align : 'center',
 				width:"200"
 			},  {
 				display : '菜单权限',
 				name : 'permissionHtml',
 				sortable : true,
 				align : 'left',
 				width:"400"
 			} 
 			]
 		});  
		$("#cancelBtn").click(function() {
			 top.closeDialog();
		});	 
		$("#btnRefuseBtn").click(function() {
			aduitstatus='R';
			var reason=$("#reason").val(); 
			var url = "${ctx_admin}/security/applyrole/review/" + id;
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
			var url = "${ctx_admin}/security/applyrole/review/" + id;
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