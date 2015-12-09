<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <!-- 区域左侧树 -->
    <div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="组别" fit="true" class="tree-center">
                <table id="organizationTree" style=""></table>
            </div>
            <div data-options="region:'south'" class="tree-buttons"> 
            </div>
        </div>
    </div>
    <!-- 中间区域 -->
    <div data-options="region:'center'" class="no-border">
        <div class="easyui-layout" data-options="fit:true">
            <!-- 上部:查询表单 -->
            <div data-options="region:'north'" class="search">
                <fieldset>
                    <legend>筛选条件</legend>
                    <form id="serachForm">
                    <input type="hidden" name="orgId" id="orgId"/>
                        <table class="search-table">
                            <tr>
                                <th>用户姓名</th>
                              	<td><input type="text" name="name" maxlength="20"/></td>	
								<td class="search-buttons">
									<shiro:hasPermission name="organization:sysuser:query">
									<input type="button" class="button" id="qryBtn" value="查询"/>
                                	<input type="button" class="button" id="clearBtn" value="清除"/>
                              		</shiro:hasPermission>
                                </td>
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
         	    <shiro:hasPermission name="organization:sysuser:view">
                <input type="button" class="button" id="showBtn" value="查看"/>
                 </shiro:hasPermission>
                <shiro:hasPermission name="organization:sysuser:add">
                <input type="button" class="button" id="addBtn" value="新建"/>
                 </shiro:hasPermission>
                <shiro:hasPermission name="organization:sysuser:edit">
                <input type="button" class="button" id="editBtn" value="修改"/>
                 </shiro:hasPermission>
                 <shiro:hasPermission name="organization:sysuser:delete">
                <input type="button" class="button" id="delBtn" value="删除"/>
                 </shiro:hasPermission>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
function initTree(){
	$('#organizationTree').tree({  
	   	url:"${ctx_admin}/organization/sysuser/treejson",
	   	onClick: function(node){
	   		$("#orgId").val(node.id);
	   		$("#flexTable").flexReload();
		}
	});  
};
$(function() {
	initTree();
	$("#flexTable").flexigrid({
			title:'用户列表',
			hiddenArea:[{name:"sysUserId"}],//隐藏标识 在页面不显示
			url:"${ctx_admin}/organization/sysuser?search=true",
			colModel : [
						{display : '姓名',name : 'name',width : 130,sortable : false,align : 'center'}, 
						{display : '组别 ',name : 'orgName',width : 125,sortable : false,align : 'center'}, 
						{display : '用户名',name : 'loginName',width : 120,sortable : false,align : 'center'}, 
						{display : '密码设置时间',name : 'pwdSetTime',width : 120,sortable : false,align : 'center'}, 
						{display : '密码失效时间',name : 'pwdInactiveTime',width : 120,sortable : false,align : 'center'}, 
						{display : '手机',name : 'mobile',width : 120,sortable : false,align : 'center'}, 
						{display : '家庭电话',name : 'homePhone',width : 120,sortable : false,align : 'center'},  
						{
							display: '操作',
							name: 'operate',
							width: 120,
							align: 'center',
							operateColumn: [
								            {html: '<shiro:hasPermission name="organization:sysuser:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
											{html: '<shiro:hasPermission name="organization:sysuser:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
											{html: '<shiro:hasPermission name="organization:sysuser:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
										   ]
						}]
		});
	    //查询
		$("#qryBtn").click(function(){
			$("#flexTable").flexRefresh();
		});
		$("#clearBtn").click(function() {
			var  orgId = $("#orgId").val();
			$("#serachForm")[0].reset();
			$("#orgId").val(orgId);
		});
		
	});
 
<%--按钮绑定操作 end--%>	
 $("#addBtn").click(function(){
	 var url='${ctx_admin}/organization/sysuser/new';
		top.openDialog({href:url,resizable : false,height: 410,width: 654,title: "新建用户申请",modal : true});
 });
 //修改
 $("#editBtn,[name='editLink']").live("click",function () {
	  if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
	    	var key= getSelected({nameGroup:'sysUserId',currentObj:$(this)});
			var url='${ctx_admin}/organization/sysuser/' + key + '/edit';
			top.openDialog({href:url,resizable : false,height: 410,width: 654,title: "修改用户信息申请",modal : true});
		}
 });
//查看
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
	 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
	var key=getSelected({nameGroup:'sysUserId',currentObj:$(this)});
	 var url='${ctx_admin}/organization/sysuser/' + key;
	 top.openDialog({href:url,resizable : false,height: 410,width: 654,title: "查看用户信息",modal : true});
	}
});
	//删除
	 $("#delBtn,[name='delLink']").live("click",function (event,data) {
   	var keys=getSelected({nameGroup:'sysUserId',currentObj:$(this)});
   	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r) {
		if (r) {
			var url = "${ctx_admin}/organization/sysuser/" + keys + ".json?_method=DELETE";
			$.ajax({
				type:'post',
				url:url,
				data:{id:keys},  
				dataType:'json',
				success:function(data){
					$.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
					$("#flexTable").flexReload();
				}
			});
		}
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