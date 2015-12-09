<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">
	<!-- 上部:查询表单 -->
	 <div data-options="region:'north'" class="search" >
		<fieldset>
			<legend>筛选条件</legend>
			<form id="serachForm">
				<table class="search-table">
					<tr>
						<th>组别类型名称</th>
						<td><input type="text"  name="name" /></td> 
						<td class="search-buttons">
							<shiro:hasPermission name="organization:orgtype:query">
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
	<!-- 底部:操作按钮-->
	<div data-options="region:'south'" class="buttons">
		<shiro:hasPermission name="organization:orgtype:view">
    				<input type="button" class="button" id="showBtn" value="查看"/>
				</shiro:hasPermission>
                <shiro:hasPermission name="organization:orgtype:add">
    				<input type="button" class="button" id="addBtn" value="新建"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="organization:orgtype:edit">
    				<input type="button" class="button" id="editBtn" value="修改"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="organization:orgtype:delete">
    				<input type="button" class="button" id="delBtn" value="注销"/>
				</shiro:hasPermission>
	</div>
</div>
<script type="text/javascript">
//<![CDATA[
	$(function() {
		$("#flexTable").flexigrid({
			title:"组别列表",
			url : '${ctx_admin}/organization/orgtype?search=true',
			hiddenArea:[{name:"orgTypeId"}],//隐藏标识 在页面不显示
			colModel : [
               {display : '组别类型名称',	name : 'name',width : 140,sortable : false,align : 'center'}, 
               {display : '首字母缩写',	name : 'acronym',width : 140,sortable : true,align : 'center'}, 
               {display : '状态',name : 'stsName',width : 135,sortable : false,align : 'center'} ,
               {
					display: '操作',
					name: 'operate',
					width: 135,
					align: 'center',
					operateColumn: [
						            {html: '<shiro:hasPermission name="organization:orgtype:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="organization:orgtype:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="organization:orgtype:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
								   ]
				}
               ]
		});
		//查询
		$("#qryBtn").live('click', function() {
			$("#flexTable").flexRefresh();
		});
		//重置
		$("#clearBtn").live('click', function() {
			$("#serachForm")[0].reset();
			$("#type").combobox('setValue','');
		});
		//新建
		$("#addBtn").click(function() {
			var url = '${ctx_admin}/organization/orgtype/new';
			top.openDialog({href : url,resizable : false,height : 255,width : 380,title : "新建组别类型",	modal : true
				});
		});
		//修改
		 $("#editBtn,[name='editLink']").live("click",function () {
			  if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key= getSelected({nameGroup:'orgTypeId',currentObj:$(this)});
					var url = '${ctx_admin}/organization/orgtype/' + key
							+ '/edit';
					top.openDialog({href : url,resizable : false,height : 255,width : 380,title : "修改组别类型",modal : true
					});
					}
				});
		//查看
		$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'orgTypeId',currentObj:$(this)});
			var url = '${ctx_admin}/organization/orgtype/' + key;
			top.openDialog({href : url,resizable : false,height :314,width : 380,title : "查看组别类型",modal : true
			});
			}
		});
		//删除
		 $("#delBtn,[name='delLink']").live("click",function (event,data) {
	    	var keys=getSelected({nameGroup:'orgTypeId',currentObj:$(this)});
	    	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
						$.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r){
						if (r) {
						    var url = "${ctx_admin}/organization/orgtype/"+ keys + "?_method=DELETE"; 
							$.ajax({
								type:'post',
								url:url,
								data:{id:keys},  
								dataType:'json',
								success:function(data){
									$.messager.show({title: '温馨提示:', msg : data.message, timeout: 10000, showType: 'slide'});
									$("#flexTable").flexReload();
								}
							});
						}
					});
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