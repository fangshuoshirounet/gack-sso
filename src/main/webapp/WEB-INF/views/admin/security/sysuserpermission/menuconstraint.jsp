<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>
	
<script type="text/javascript">
//<![CDATA[
var catalog = ${catalog};
var menu = ${menu};
var shortcutMenu = ${roleMenuJson};

var nodeList = [];
var catelogList = [];
for(var i=0;i<catalog.length;i++){
	catalog[i].id = catalog[i].menuCatalogId;
	catalog[i].pid = catalog[i].parentCatalogId;
	catalog[i].text = catalog[i].catalogName;
	catalog[i].flag = 'c';
	nodeList.push(catalog[i]);
	catelogList.push(catalog[i]);
}
var menuList = [];
for(var i=0;i<menu.length;i++){
	for(var j=0;j<shortcutMenu.length;j++){
		if(menu[i].menuId == shortcutMenu[j].menuId){
			menu[i].checked=true;
			break;
		}
	}
	menu[i].id = menu[i].menuId;
	menu[i].pid = menu[i].menuCatalogId;
	menu[i].text = menu[i].menuName;
	menu[i].flag = 'm';
	nodeList.push(menu[i]);
	menuList.push(menu[i]);
}

var catalogIds=[];
function eachCatalogs(pid){
	if(pid==null||pid.length==0){
		return;
	}
	catalogIds.push(pid);
	for(var i=0;i<catelogList.length;i++){
		if(catelogList[i].id==pid){
			eachCatalogs(catelogList[i].pid);
		}
	}
}

for(var i=0;i<menuList.length;i++){
	var pid = menu[i].pid;
	eachCatalogs(pid);
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
//判断是否有菜单
function hashMenu(id){
	var isTag = false;
	for(var i=0;i<catalogIds.length;i++){
		if(catalogIds[i]==id){
			isTag= true;
			break;
		}
	}
	return isTag;
}
//递归
function recursion(node){
	if(node.flag == "m"){
		return;
	}
	var child = getChildList(node.id);
	if(child==null||child.length==0||!hashMenu(node.id)){
		return;
	}
	jsonStr +='{"id":"'+node.flag+node.id+'","text":"'+node.text+'","flag":"'+node.flag+'",';
	if(hasChild(node)){
		jsonStr +='"children":[';
		for(var i = 0;i<child.length;i++){
			var item = child[i];
			item = recursion(child[i]);
		}
	}
	for(var i = 0;i<child.length;i++){
		if(child[i].flag == 'm'){
			jsonStr +='{"id":"'+child[i].id+'","text":"'+child[i].text+'","flag":"'+child[i].flag+'"';
			if(child[i].checked){
				jsonStr +=',"checked":'+child[i].checked;
			}
			jsonStr +='},';
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
function loadTableHtml(loadurl,node){
	$.getJSON(loadurl, function (data) {
		if(data[0].operations==null||data[0].operations.length==0){
			return;
		}
		var row = $("#"+node.id);
		if(row){
			row.remove();
		}
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
}
$(function(){
	$('#menuCatalogTree').tree({
		data:treedata,
		animate:true,
		checkbox:true,
		onlyLeafCheck:true,
		cascadeCheck:false,
		onCheck:function(node,checked){
			if(checked){
				var sysUserId = $("#sysUserId").val();
				var random = Math.floor(Math.random()*1000+1);
				var loadurl= '${ctx_admin}/security/sysUserpermission/searchMenuConstraint?menuId='+node.id+"&sysUserId="+sysUserId+"&random="+random;
				loadTableHtml(loadurl,node);
			}else{
				var rowId = $("#"+node.id);
				rowId.remove();
			}
		},
		onLoadSuccess:function(){
			var nodeChecked = $('#menuCatalogTree').tree('getChecked');
			if(nodeChecked&&nodeChecked.length>0){
				for(var i=0;i<nodeChecked.length;i++){
					var node = nodeChecked[i];
					var sysUserId = $("#sysUserId").val();
					var random = Math.floor(Math.random()*1000+1);
					var loadurl= '${ctx_admin}/security/sysUserpermission/searchMenuConstraint?menuId='+node.id+"&sysUserId="+sysUserId+"&random="+random;
					loadTableHtml(loadurl,node);
				}
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
            <div data-options="region:'center'" title="菜单" fit="true" class="tree-center" >
               <table class="table-info" id="menuCatalogTree" style=""></table>
            </div>
        	<div data-options="region:'south'" class="tree-buttons"></div>
        </div>
    </div>
    <!-- 中间区域 -->
    <div  data-options="region:'center'" class="no-border">
        <div class="easyui-layout" data-options="fit:true">
            <!-- 下部:显示列表-->
            <div id="flexgrid" data-options="region:'center'" class="content">
                 <form id="menuConstraintForm" method="post">
                	<table id="flexTable"></table>
					<input type="hidden" name="sysUserId" id="sysUserId" value="${model.sysUserId }"/>
					<input type="hidden" name="menuCatalogId" id="menuCatalogId"/>
				</form>
            </div>
           
            <div data-options="region:'south'" class="buttons">
            	<input type="button" class="button" id="menuBackBtn" value="上一步"/>
				<input type="button" class="button" id="menuConstraintBtn" value="保 存"/> 
            </div>
        </div>
    </div>
</div>  
<script type="text/javascript">
var permission = '${permission}';
var sysUserId="${model.sysUserId}";
//<![CDATA[
$(function () {
	for(var j=0;j<shortcutMenu.length;j++){
			var tr = $("<tr  id='"+shortcutMenu[j].menuId+"'></tr>");
			var td1=$("<td style=\"TEXT-ALIGN: center; OVERFLOW: hidden\"></td>");
			var div1=$("<div style=\"TEXT-ALIGN: center; WIDTH: 280px; TEXT-OVERFLOW: ellipsis; OVERFLOW: hidden\" name=\"menuName\"></div>");
			var a1html="<a href='###' onclick=\"removerows("+shortcutMenu[j].menuId+")\">"+shortcutMenu[j].menuName+"</a>";
			var a1 = $(a1html);
			div1.append(a1);
			td1.append(div1);
			var td2=$("<td style=\"TEXT-ALIGN: center; OVERFLOW: hidden\"></td>");
			var div2=$("<div style=\"TEXT-ALIGN: center; WIDTH: 550px; TEXT-OVERFLOW: ellipsis; OVERFLOW: hidden\" name=\"permissionHtml\">"+shortcutMenu[j].operations+"</div>");
			td2.append(div2);
			tr.append(td1);
			tr.append(td2);
			$("#flexTable").append(tr);
	}
});
$("#menuBackBtn").click(function(){
	window.location.href="${ctx_admin}/security/sysUserpermission?sysUserId="+sysUserId+"&permission="+permission;  
});
 
$("#flexTable").flexigrid({
	title :'菜单约束操作',
	dataType : 'json',
	colModel : [  {
		display : '菜单名称',
		name : 'menuName',
		sortable : true,
		align : 'center',
		width:"280px"
	},{
		display : '菜单权限',
		name : 'peOrConstraintHtml',
		sortable : true,
		align : 'center',
		width:"550px"
	}],
	checkbox : false,
	sortorder : "desc",
	autoload : true,
	usepager: false
});

$('#menuConstraintForm').ajaxForm({
    url:"${ctx_admin}/security/sysUserpermission/saveMenuConstraint.json",
    beforeSubmit:function(formData, jqForm){
        return jqForm.form('validate');
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
$("#menuConstraintBtn").click(function(){
	if($("#sysUserId").val()==null||$("#sysUserId").val().length==0){
		$.messager.alert('系统角色授权操作','请先选择系统角色！');
		return;
	}
	$("#menuConstraintForm").submit();
}); 
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>