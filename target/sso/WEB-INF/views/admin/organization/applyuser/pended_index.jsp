<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
   
    <!-- 中间区域 -->
    <div data-options="region:'center'" class="no-border">
        <div class="easyui-layout" data-options="fit:true">
            <!-- 上部:查询表单 -->
            <div data-options="region:'north'" class="search">
                <fieldset>
                    <legend>用户信息</legend>
                    <form id="serachForm">
                    <input type="hidden" name="type" id="type" value="${type}" /> 
                        <table class="search-table">
                            <tr>
                                <th>用户姓名</th>
                              	<td><input type="text" name="name" maxlength="20"/></td>	
								<td class="search-buttons">
									<shiro:hasPermission name="organization:applyuser:pended:query">
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
         	    <shiro:hasPermission name="organization:applyuser:pended:view">
                <input type="button" class="button" id="showBtn" value="查看"/>
                 </shiro:hasPermission> 
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[ 
$(function() { 
	$("#flexTable").flexigrid({
			title:'用户列表',
			hiddenArea:[{name:"id"},{name:"version"}],//隐藏标识 在页面不显示
			url:"${ctx_admin}/organization/applyuser?search=true",
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
										display : '变更类型',
										name : 'changeTypeName',
										width : 50,
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
										display : '复核人',
										name : 'auditor',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '复核时间',
										name : 'auditDate',
										width : 120,
										sortable : false,
										align : 'center'
									},
									{
										display : '复核状态',
										name : 'auditStatusName',
										width : 50,
										sortable : false,
										align : 'center'
									},
									{
							display: '操作',
							name: 'operate',
							width: 120,
							align: 'center',
							operateColumn: [
								            {html: '<shiro:hasPermission name="organization:applyuser:pended:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'}
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
		<%--按钮绑定操作 end--%>	
		 
		//查看
			$("#showBtn,[name='showLink']").live("click",function (event,data) {
			 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key=getSelected({nameGroup:'id',currentObj:$(this)});
			 var url='${ctx_admin}/organization/applyuser/' + key;
			 top.openDialog({href:url,resizable : false,height: 430,width: 654,title: "查看用户",modal : true});
			}
		});
	});
 

 
//刷新flexgird 数据
function refresh() {
	$("#flexTable").flexReload();
}
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>