<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
<!-- 菜单左侧树 -->
<div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="菜单目录" fit="true" class="tree-center" >
				<table id="menuCatalogTree"></table>
			</div>
			<div data-options="region:'south'" class="tree-buttons">
             
            </div>
		</div>
	</div>
	<!-- 中间区域 -->
	<div  class="no-border" data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 查询项 -->
			<div data-options="region:'north'" class="search">
				<fieldset>
					<legend>筛选条件</legend>
					<form id="serachForm">
						<input type="hidden" name="menuCatalogId" id="menuCatalogId" />
						<table class="search-table">
							<tr>
								<th>菜单名称</th>
								<td><input type="text" name="menuName" maxlength="20" /></td>
								<th>菜单编码</th>
								<td><input type="text" name="menuCode" maxlength="40" /></td>
								<td class="search-buttons">
								  <shiro:hasPermission name="security:menu:query">
								     <input type="button" class="button" id="qryBtn" value="查询" />
								     <input type="button" class="button" id="clearBtn" value="清除" />
								  </shiro:hasPermission>   
								</td>
							</tr>
						</table>
					</form>
				</fieldset>
			</div>
			<!-- 显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<table id="flexTable"></table>
			</div>
			<div data-options="region:'south'" class="buttons">
				<shiro:hasPermission name="security:menu:view">
                	<input type="button" class="button" id="showBtn" value="查看"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="security:menu:add">
                	<input type="button" class="button" id="addBtn" value="新建"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="security:menu:edit">
                	<input type="button" class="button" id="editBtn" value="修改"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="security:menu:delete">
                	<input type="button" class="button" id="delBtn" value="注销"/>
                </shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//<![CDATA[
<%-- 菜单目录树新建--%>
$("#insert").bind("click", function () {
	var url = '${ctx_admin}/security/menu/new';
	top.openDialog({href : url,	resizable : true,height : 358,width : 400,title : "新建菜单",modal : true
	});
});
	function initTree() {
		$('#menuCatalogTree').tree({
			url : "${ctx_admin}/security/menu/treejson",
			onClick : function(node) {
				$("#menuCatalogId").val(node.id);
				$("#flexTable").flexReload();
			}
		});
	}
	$(function() {
		initTree();
		<%-- 右侧主面板区域 --%>
		$("#flexTable").flexigrid({
			title:"菜单列表",
			url : '${ctx_admin}/security/menu?search=true',
			hiddenArea:[{name:"menuId"}],
				colModel : [ 
				 {display : '菜单名称',name : 'menuName',sortable : true,width : 130,align : 'center'}, 
				 {display : '菜单编码',name : 'menuCode',sortable : true,width : 125,align : 'center'}, 
				 {display : '状态',name : 'stsName',hide : false,sortable : false,width : 120,align : 'center'},
				 {display : '排序',name : 'orderNo',hide : false,sortable : false,width : 120,align : 'center'},
				 {
						display: '操作',
						name: 'operate',
						width: 120,
						align: 'center',
						operateColumn: [
							            {html: '<shiro:hasPermission name="security:menu:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
										{html: '<shiro:hasPermission name="security:menu:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
										{html: '<shiro:hasPermission name="security:menu:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
									   ]
					}
		]});
	});
		//查询
		$("#qryBtn").live('click', function() {
			$("#flexTable").flexRefresh();
		});
		//重置
		$("#clearBtn").live('click', function() {
			var  menuCatalogId = $("#menuCatalogId").val();
			$("#serachForm")[0].reset();
			$("#menuCatalogId").val(menuCatalogId);
		});
	//刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
	//新建
	$("#addBtn").click(function() {
		var url = '${ctx_admin}/security/menu/new';
		top.openDialog({href : url,resizable : false,height : 378,width : 380,title : "新建菜单",modal : true
		});
	});
	//修改
	 $("#editBtn,[name='editLink']").live("click",function () {
			  if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key= getSelected({nameGroup:'menuId',currentObj:$(this)});
		var url = '${ctx_admin}/security/menu/' + key + '/edit';
		top.openDialog({href : url,resizable : false,height : 378,width : 380,title : "修改菜单",modal : true
		});
		}
	});
	//查看
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'menuId',currentObj:$(this)});
		var url = '${ctx_admin}/security/menu/' + key;
		top.openDialog({href : url,resizable : false,height : 394,width : 380,title : "查看菜单",modal : true
			});
		}
	});
	//删除
	 $("#delBtn,[name='delLink']").live("click",function (event,data) {
	    	var keys=getSelected({nameGroup:'menuId',currentObj:$(this)});
	    	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			$.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r) {
					if (r) {
						var url = "${ctx_admin}/security/menu/" + keys
								+ "?_method=DELETE";
						$.ajax({async : false,cache : false,type : 'POST',dataType : "json",url : url,//请求的action路径
							success : function(data) {
								if (data != null) {//返回异常信息
									$.messager.alert('删除提示', data.message,
											'您是否确定永久删除选中数据?');
								}
							}
						});
						$("#flexTable").flexReload();
						initTree();
					}
				});
				}
			});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>