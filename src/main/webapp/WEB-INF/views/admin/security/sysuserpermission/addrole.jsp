<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">

 <!-- 中间:显示列表-->
    <div id="flexgrid" data-options="region:'center'" class="content">
                <form id="saveRole" method="post">
                <table id="flexTable"></table>
                 <input type="hidden" name="sysUser.sysUserId" value="${model.sysUserId}" id="sysUserId"></input>	
				</form>
    </div>
    <!-- 底部:操作按钮-->
            <div data-options="region:'south'" class="buttons">
            	<input type="button" class="button" id="menuBackBtn" value="上一步"/>
				<input type="button" class="button" id="applyBtn" value="提交申请"/> 
            </div>
</div>
<script type="text/javascript">
//<![CDATA[
var permission = '${permission}';
var sysUserId="${model.sysUserId}";
	$(function() { 
		$("#flexTable").flexigrid({
			title:"角色信息",
			url:"${ctx_admin}/security/sysUserpermission/queryRoleList",
			autoload : true, 
			hiddenArea :[{
				name :'roleId'
			}],
			colModel : [ 
			             {display : '角色编码',name : 'roleCode',width : 200,sortable : false,align : 'center'},
			             {display : '角色名称',name : 'roleName',width : 200,sortable : true,align : 'center'}, 
			             {display : '状态',name : 'stsName',width : 200,sortable : false,align : 'center'}, 
						 {display : '创建时间',name : 'operateDate',width : 200,sortable : true,align : 'center'}
				       ],
		   onSuccess:function(){
			   var sysUserId = $("#sysUserId").val();
			   var random = Math.floor(Math.random()*1000+1);
			   var url = '${ctx_admin}/security/sysUserpermission/querySysUserRole?sysUserId='+sysUserId+"&random="+random;
			   $.getJSON(url, function (data) {
				  var ids = data.ids;
			   	  if(ids!=null&&ids.length>0){
			   	 		$("#flexTable tr ").each(function (n, tr) {
			   	 		   var roleId = $(tr).find("input[name='roleId']").val();
		                   if(ids.indexOf(roleId)>0){
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
			   });	
		   }
		});
		//上一步
		$("#menuBackBtn").click(function(){
			var sysUserId="${model.sysUserId}";
			window.location.href="${ctx_admin}/security/sysUserpermission?sysUserId="+sysUserId+"&permission="+permission;  
		});
		
		//表单提交
		$("#applyBtn").click(function(){
			if(validateSelected(true)){
				var roleIds=getSelectRowByName("roleId");
				var sysUserId = $("#sysUserId").val();
				var url = '${ctx_admin}/security/sysUserpermission/applySysUserRole?roleId='+roleIds+"&sysUserId="+sysUserId;
				$.getJSON(url, function (data) {
					$.messager.alert("温馨提示",  data.message,"info", function () {
				         top.closeTabs();
				        }); 
			    });
			}
		});  
    }); 
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>