<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%> 


<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">
  <!-- 上部:查询表单 -->
    <div data-options="region:'south'" class="search">
        <fieldset>
			<form id="searchForm">
				<table class="search-table">
					<tr>
						<th>菜单ID</th>
						<td><input id="menuId" type="text" name="menuId" maxlength="64" /></td>
						<td class="search-buttons">
						<shiro:hasPermission name="public:actionlog:query">
							<input type="button" class="button" id="qryBtn" value="查询" />
						</shiro:hasPermission>
						<shiro:hasPermission name="public:actionlog:view">
							<input type="button" class="button" id="showBtn" value="查看" />
						</shiro:hasPermission>
						<input type="button" class="button" id="closeSubmitBtn" value="关闭" />
						 
						</td>
					</tr>
				</table>
			</form>
		</fieldset>
    </div> 
 <!-- 中间:显示列表-->
    <div id="flexgrid" data-options="region:'center'" class="content">
        <table id="flexTable"></table>
    </div>
    <!-- 底部:操作按钮-->
</div>
<script type="text/javascript">
//<![CDATA[
    var menuId = '${menuId}';
    document.getElementById("menuId").value = menuId;
 $(function() {
	$("#flexTable").flexigrid({
		    title:'',
			url:"${ctx_admin}/public/actionlog.json?search=true",
			hiddenArea:[{name:"actionLogId"}],
			height:'465',
			width:'auto',
			striped:false,
			autoload: true,
			colModel : [
				{display : '用户ID',name : 'sysUserId',	width : 140,sortable : false,align : 'center'}, 
				{display : '菜单ID',name : 'menuId',width : 140,sortable : false,align : 'center'}, 
				{display : '操作类型',name : 'actionType',width : 140,sortable : false,align : 'center'}, 
				{display : '操作时间',name : 'actionTime',width : 140,sortable : false,align : 'center'},
				{
					display: '操作',
					name: 'operate',
					width: 130,
					align: 'center',
					operateColumn: [
						            {html: '<shiro:hasPermission name="public:actionlog:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'}
								   ]
				}
				]
		});
	    //查询
		$("#qryBtn").click(function(){
			$("#flexTable").flexRefresh();
		});
		//重置
		$("#clearBtn").click(function() {
			$("#searchForm")[0].reset();
		});
	//查看
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'actionLogId',currentObj:$(this)});
		var url='${ctx_admin}/public/actionlog/' + key;
		openDialoglevel2({href:url,height: 356,width: 660,title: "查看日志",modal:true
		});
		 }
	});
	});
    //刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
    
	$("#closeSubmitBtn").click(function() {
		 top.closeDialog();
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>