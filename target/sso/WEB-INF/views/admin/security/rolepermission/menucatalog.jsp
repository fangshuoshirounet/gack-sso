<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<script type="text/javascript">
//<![CDATA[
var catalog = ${catalog}; 
var roleMenuCatalogJson = ${roleMenuCatalogJson};

var nodeList = [];
for(var i=0;i<catalog.length;i++){
	for(var j=0;j<roleMenuCatalogJson.length;j++){
		if(catalog[i].menuCatalogId == roleMenuCatalogJson[j].menuCatalogId){
			catalog[i].checked=true;
			break;
		}
	}
	catalog[i].id = catalog[i].menuCatalogId;
	catalog[i].pid = catalog[i].parentCatalogId;
	catalog[i].text = catalog[i].catalogName;
	catalog[i].flag = 'c';
	nodeList.push(catalog[i]);
}

//得到子节点列表
function getChildList(id){
	var child = [];
	for(var i=0;i<nodeList.length;i++){
		if(nodeList[i].pid == id){
			child.push(nodeList[i]);
		}
	}
	return child;
}
//判断是否有子节点
function hasChild(node) {
	return node.flag == 'c'? true : false;
}
//递归
function recursion(node){
	jsonStr +='{"id":"'+node.id+'",';
	if(node.checked){
		jsonStr+='"checked":'+node.checked+',';
	}
	jsonStr += '"text":"'+node.text+'","flag":"'+node.flag+'",';
	var child = getChildList(node.id);
	if(hasChild(node)){
		jsonStr +='"children":[';
		for(var i = 0;i<child.length;i++){
			var item = child[i];
			item = recursion(child[i]);
		}
	}
	if(jsonStr.charAt(jsonStr.length - 1) == ','){
		jsonStr = jsonStr.substring(0,jsonStr.length-1);
	}
	jsonStr +=']},';
}
var treeData = getChildList("");
var jsonStr = '[';
for(var i = 0;i<treeData.length;i++){
	treeData[i] = recursion(treeData[i]);
}
if(jsonStr.charAt(jsonStr.length - 1) == ','){
	jsonStr = jsonStr.substring(0,jsonStr.length-1);
}
jsonStr += ']';
var treedata = eval('('+jsonStr+')');
$(function(){
	$('#menuCatalogTree').tree({
		data:treedata,
		animate:true,
		checkbox:true,
		cascadeCheck:false,
		onCheck:function(node,checked){
			if(checked){
				var roleId = $("#roleId").val();
				var random = Math.floor(Math.random()*1000+1);
				var loadurl= '${ctx_admin}/security/rolepermission/searchmenucatalog?menuCatalogId='+node.id+"&roleId="+roleId+"&random="+random;
				$.getJSON(loadurl, function (data) {
					var tr = $("<tr  id='"+node.id+"'></tr>");
					var td1=$("<td style=\"TEXT-ALIGN: center; OVERFLOW: hidden\"></td>");
					var div1=$("<div style=\"TEXT-ALIGN: center; WIDTH: 280px; TEXT-OVERFLOW: ellipsis; OVERFLOW: hidden\" name=\"menuName\"></div>");
					var a1html="<a href='###' onclick=\"removerows("+node.id+")\">"+node.text+"</a>";
					var a1 = $(a1html);
					div1.append(a1);
					td1.append(div1);
					var td2=$("<td style=\"TEXT-ALIGN: center; OVERFLOW: hidden\"></td>");
					var div2=$("<div style=\"TEXT-ALIGN: center; WIDTH: 550px; TEXT-OVERFLOW: ellipsis; OVERFLOW: hidden\" name=\"permissionHtml\">"+data[0].operations+"</div>");
					td2.append(div2);
					tr.append(td1);
					tr.append(td2);
					$("#flexTable").append(tr);
				});
			}else{
				var rowId = $("#"+node.id);
				rowId.remove();
			}
		}
	});
});
//]]>
</script>
<div class="easyui-layout" data-options="fit:true">

    <!-- 区域左侧树 -->
    <div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="菜单目录" fit="true" class="tree-center">
                 <table class="table-info" id="menuCatalogTree"></table>
            </div>
            <div data-options="region:'south'" class="tree-buttons"></div>
        </div>
    </div>
    <!-- 中间区域 -->
    <div  data-options="region:'center'" class="no-border">
        <div class="easyui-layout" data-options="fit:true">
            <!-- 下部:显示列表-->
            <div id="flexgrid" data-options="region:'center'" class="content">
                <form id="menuCatalogPermission" method="post">
                <table id="flexTable"></table>
				<input type="hidden" name="roleId" id="roleId" value="${model.roleId }"/>
		    	<input type="hidden" name="menuCatalogId" id="menuCatalogId"/>
				</form>
            </div>
            <div data-options="region:'south'" class="buttons">
            	<input type="button" class="button" id="menuBackBtn" value="上一步"/>
				<input type="button" class="button" id="menuPermissionBtn" value="申请"/>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
$("#menuBackBtn").click(function(){
	var url='${ctx_admin}/security/rolepermission/permission?type=menucatalog' ;
	window.location.href=url;
});
$("#flexTable").flexigrid({
	dataType : 'json',
	title:'菜单目录操作',
	colModel : [ {
		display : '菜单目录名称',
		name : 'catalogName',
		sortable : true,
		align : 'center',
		width:"280px"
	},{
		display : '菜单目录权限',
		name : 'permissionHtml',
		sortable : true,
		align : 'center',
		width:"550px"
	}],
	checkbox : false,
	sortorder : "desc",
	autoload : true,
	usepager: false
});

$('#menuCatalogPermission').ajaxForm({
    url:"${ctx_admin}/security/rolepermission/saveMenuCatalogOperatePermission",
    onSubmit:function(){
		return $(this).form('validate');
	},
	success:function(data){
		$.messager.alert("温馨提示",  data.message,"info", function () {
	         top.closeTabs();
	        }); 
	}
 });
function removerows(id){
	 $("#"+id).remove();//得到父tr对象;   
	 var node = $('#menuCatalogTree').tree('find',id);
	 if(node){
		 $('#menuCatalogTree').tree('uncheck',node.target);
	 }
}
//表单提交
$("#menuPermissionBtn").click(function(){
	if($("#roleId").val()==null||$("#roleId").val().length==0){
		$.messager.alert('系统角色授权操作','请先选择系统角色！');
		return;
	}
	$("#menuCatalogPermission").submit();
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp" %>