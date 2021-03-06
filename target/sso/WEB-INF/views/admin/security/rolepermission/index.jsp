<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

	<div class="easyui-layout" data-options="fit:true">  
	   <input type="hidden" name="menuCatalogId" id="menuCatalogId"/>
	  <!-- 中间:显示列表-->
	  <div id="flexgrid" data-options="region:'center'" class="content">
	        <table id="flexTable"></table>
	  </div>
	  <!-- 底部:操作按钮-->
	  <div class="buttons" data-options="region:'south'">
	  <shiro:hasPermission name="security:rolepermission:view">
					<input type="button" class="button" id="showBtn" value="查看权限" />
		 </shiro:hasPermission>
	 	<shiro:hasPermission name="security:rolepermission:add">
			 <input type="button" class="button" id="nextBtn" value="下一步"/>
		</shiro:hasPermission>
	       
	  </div>
</div>

<script type="text/javascript">
//<![CDATA[
    var selectRoleId = '${model.roleId}';
    var type = '${type}';
    $(function () {
	$("#flexTable").flexigrid({
		url:"${ctx_admin}/security/rolepermission/queryRoleList",
		autoload : true,
		height : flexgirdHeight-30, 
		title:"角色列表",
		//usepager: false,
		hiddenArea :[{
			name :'roleId'
		}],
		colModel: [
			  //  {display: '角色Id', name: 'roleId', sortable: true, align: 'center', hide: true, toggle: false},
				{display: '角色名称', name: 'roleName', width: 180, sortable: true, align: 'center'},
				{display: '角色编码', name: 'roleCode', width: 175, sortable: true, align: 'center'},
				{display: '状态', name: 'stsName', width: 170, sortable: false, align: 'center'},
				{display: '创建时间', name: 'operateDate', width: 170, sortable: true, align: 'center'},
				{
					display : '操作',
					name : 'operate',
					width : 120,
					align : 'center',
					operateColumn : [ {
						html : '<shiro:hasPermission name="security:rolepermission:view"><a  name="showLink" href="#">查看权限</a></shiro:hasPermission>'
					} ]
				} 
				],
		onSuccess:function(){
			if(selectRoleId!=null&&selectRoleId.length>0){
				$("#flexTable tr ").each(function (n, tr) {
		   	 		   var roleId = $(tr).find("input[name='roleId']").val();
	                   if(selectRoleId==roleId){
	                	   var input = $($(tr).children().eq(0));
	                	   var checkbox = input.find("input[type='checkbox']");
	                       $(checkbox).attr("checked", "checked");
	                       if (n % 2 == 0) {
	                           $(tr).attr("class", "erow trSelected");
	                       } else {
	                           $(tr).attr("class", "trSelected");
	                       }
	                   }
	               });
			}
		}
	});
	 function fixWidth(){  
         return document.body.clientWidth;  
     } 
	 function fixHeight(percent){  
         return document.body.clientHeight;  
     } 
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'roleId',currentObj:$(this)});
		var url='${ctx_admin}/security/rolepermission/roleView?roleId=' + key;
		 top.openDialog({href:url,resizable : false,width:fixWidth(),height:fixHeight(),title: "查看角菜单权限信息",modal : true});
		}});
    });
	$("#nextBtn").click(function() {
		if(validateSelected()){
			var key = getSelectRowByName("roleId");
			var keyname = getSelectRowByName("roleName");  
			var keycode = getSelectRowByName("roleCode");  
		  var url='${ctx_admin}/security/rolepermission/permission?roleId=' + key+'&roleName='+ encodeURI(encodeURI(keyname)) +'&roleCode='+keycode+'&permissionType='+type ;
			  window.location.href=url; 
		}
	});
	$("#step2").click(function(){
		$("#nextBtn").click();
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>