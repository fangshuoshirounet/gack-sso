<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
    <!-- 目录左侧树 -->
            <div class="tree" data-options="region:'west',split:true">
			<div class="easyui-layout" data-options="fit:true">
			<div id="p" data-options="region:'center'" title="菜单目录" fit="true" class="tree-center">
				<table id="menuCatalogTree">
				</table>   
            </div>
            <div id="menuCatalogMenuAll" data-options="region:'south'" class="tree-buttons">
            	 
            </div>
        </div>
    </div>
    <!-- 中间区域 -->
    <div class="no-border" data-options="region:'center'">
        <div class="easyui-layout" data-options="fit:true">
     	  <!-- 上部:查询表单 -->
          	<div data-options="region:'north'" class="search">
				<fieldset>
					<legend>筛选条件</legend>
					<form id="serachForm">
						<input type="hidden" name="parentCatalogId" id="parentCatalogId" />
						<table class="search-table">
							<tr>
								<th>菜单目录名称</th>
								<td><input type="text" name="catalogName" maxlength="20" /></td>
								<td class="search-buttons">
								<shiro:hasPermission name="security:menucatalog:query">
								<input type="button" class="button" id="qryBtn" value="查询" />
								<input type="button" class="button" id="resetBtn" value="清除" />
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
				<shiro:hasPermission name="security:menucatalog:view">
				<input type="button" class="button" id="showBtn" value="查看" /> 
				</shiro:hasPermission>
				<shiro:hasPermission name="security:menucatalog:add">
				<input type="button" class="button" id="addBtn" value="新建" /> 
				</shiro:hasPermission>
				<shiro:hasPermission name="security:menucatalog:edit">
				<input type="button" class="button" id="editBtn" value="修改" /> 
				</shiro:hasPermission>
				<shiro:hasPermission name="security:menucatalog:delete">
				<input type="button" class="button" id="delBtn" value="注销" />
				</shiro:hasPermission>
			</div>
        </div>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
<%-- 菜单目录树 --%>
	function initTree(){
		$('#menuCatalogTree').tree({  
		   	url:"${ctx_admin}/security/menucatalog/treejson",
		   	onClick: function(node){
		   		$("#parentCatalogId").val(node.id);
		   		$("#flexTable").flexRefresh();
		   		$("#parentCatalogId").val(node.id);
		   		if(node.attributes=="true"){
					$("#remove").css("display","none"); 
			 	}else{
					$("#remove").css("display",""); 
			 	}
			}
		});  
	}
	$(function() {
		initTree();
<%-- 右侧主面板区域 --%>
		$("#flexTable").flexigrid({
			title:'菜单目录列表',
			url : '${ctx_admin}/security/menucatalog?search=true',
			hiddenArea:[{name:"menuCatalogId"}],
			colModel : [ 
			   {display : '菜单目录名称',name : 'catalogName',width : 130,sortable : true,align : 'center',hide : false},
			   {display : '上级菜单目录',name : 'parentCatalogName',width : 125,sortable : true,align : 'center',hide : false},
			   {display : '状态',name : 'stsName',width : 120,sortable : false,align : 'center',hide : false},
			   {display : '排序',name : 'orderNo',hide : false,sortable : false,width : 120,align : 'center'},
			   {
					display: '操作',
					name: 'operate',
					width: 120,
					align: 'center',
					operateColumn: [
						            {html: '<shiro:hasPermission name="security:menucatalog:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="security:menucatalog:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="security:menucatalog:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
								   ]
				}
			   ],
				onSuccess :function(){
		   			var parentCatalogId = $("#parentCatalogId").val();
					if(parentCatalogId!=null&&parentCatalogId.length>0){
						$("#flexTable tr ").each(function (n,tr){
				   			var menuCatalogId = $(tr).find("input[name='menuCatalogId']").val();
				   			if(parentCatalogId==menuCatalogId){
				   				var input = $($(tr).children().eq(0));
				   				var checkbox = input.find("input[type='checkbox']");
				   				$(checkbox).attr("checked","checked");
				   				if(n%2==0){
				   					$(tr).attr("class","erow trSelected");
				   				}else{
				   					$(tr).attr("class","trSelected");
				   				}
				   				return false;
				   			}
			             });
		            }
		        }
	    });
		//查询
		$("#qryBtn").live('click', function() {
			$("#flexTable").flexRefresh();
		});
		//重置
		$("#resetBtn").click(function() {
			var  parentCatalogId = $("#parentCatalogId").val();
			$("#serachForm")[0].reset();
			$("#parentCatalogId").val(parentCatalogId);
		});
	});
 
<%--按钮绑定操作 end--%>		
//新建
	$("#addBtn").click(function() {
		var len = $('.trSelected').length;
		if(len==0){
			var parentCatalogId = $("#parentCatalogId").val();	
			if (parentCatalogId != null && parentCatalogId.length > 0) {
				var url = '${ctx_admin}/security/menucatalog/new?parentCatalogId=' + parentCatalogId;
				top.openDialog({href:url,hrefMode:"iframe",resizable: false,height: 194,width: 380,title: "菜单目录添加",modal: true});
			} else {
				var url = '${ctx_admin}/security/menucatalog/new';
				top.openDialog({href:url,hrefMode:"iframe",resizable: false,height: 194,width: 380,title: "菜单目录添加",modal: true});
				top.load();			
			}
		}else if(len==1){
			var parentCatalogId = getSelectRowByName("menuCatalogId");
			var url = '${ctx_admin}/security/menucatalog/new?parentCatalogId=' + parentCatalogId;
				top.openDialog({href:url,hrefMode:"iframe",resizable: false,height: 194,width: 380,title: "菜单目录添加",modal: true });
		}else{
			$.messager.alert('菜单目录添加','只能选择一项菜单目录！');
		}
	});
//修改
    $("#editBtn,[name='editLink']").live("click",function () {
    	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
			var key = getSelected({nameGroup:'menuCatalogId',currentObj:$(this)});
			var url='${ctx_admin}/security/menucatalog/' + key + '/edit';
			top.openDialog({href:url, hrefMode:"iframe", resizable: false,height: 194, width: 380,title: "修改菜单目录",modal: true });
		}
    });
//查看
    $("#showBtn,[name='showLink']").live("click",function (event,data) {
     	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
			var key = getSelected({nameGroup:'menuCatalogId',currentObj:$(this)});
			var url='${ctx_admin}/security/menucatalog/' + key;
			top.openDialog({href:url,hrefMode:"iframe",resizable: false,height: 278, width: 380,title: "菜单目录查看",modal: true});
		}
     });
//删除
	$("#delBtn,[name='delLink']").live("click",function (event,data) {
	    	var keys=getSelected({nameGroup:'menuCatalogId',currentObj:$(this)});
	    	if (isSelected({isMultiSelect:true,currentObj:$(this)})) { 
		    var delurl = "${ctx_admin}/security/menucatalog/"+keys+"/"+"validatedelete";
		    $.getJSON(delurl, function (data) {
		    	 if (data.message != null) {//返回异常信息
		             $.messager.alert('删除提示', data.message, '您是否确定永久删除选中数据?');
		         }else{
		        	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function (r) {
		        	        if (r) {
		        	            var url = "${ctx_admin}/security/menucatalog/" + keys + "?_method=DELETE";
		        			    $.ajax({
		    						type:'post',
		    						url:url,
		    						data:{id:keys},  
		    						dataType:'json',
		    						success:function(data){
		    							$.messager.show({title: '温馨提示:', msg : data.message, timeout: 2000, showType: 'slide'});
			        			        $("#flexTable").flexReload();
			        		            initTree();
		    						}
		    					});
		        	        }
		        	 });
		         }
		    });
		}
     });
// 刷新flexgird 数据
	function refresh() {
	    $("#flexTable").flexReload();
	}
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>