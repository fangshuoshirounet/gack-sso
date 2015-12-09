<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<script type="text/javascript" src="${ctx}/resources/themes/${THEME}/js/json2.js"></script>
<script>
var catalog = ${catalog};
var menu = ${menu};
var shortcutMenu = ${shortcutMenu};
var nodeList = [];
for(var i=0;i<catalog.length;i++){
	catalog[i].id = catalog[i].menuCatalogId;
	catalog[i].pid = catalog[i].parentCatalogId;
	catalog[i].text = catalog[i].sts;
	catalog[i].flag = 'c';
	nodeList.push(catalog[i]);
}
for(var i=0;i<menu.length;i++){
	for(var j=0;j<shortcutMenu.length;j++){
		if(menu[i].menuId == shortcutMenu[j].menuId){
			menu[i].checked=true;
			break;
		}
	}
	menu[i].id = menu[i].menuId;
	menu[i].pid = menu[i].menuCatalogId;
	menu[i].text = menu[i].sts;
	menu[i].flag = 'm';
	nodeList.push(menu[i]);
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
	if(node.flag == "m"){
		return;
	}
	jsonStr +='{"id":\''+node.id+'\',"text":"'+node.text+'","flag":"'+node.flag+'",';
	var child = getChildList(node.id);
	if(hasChild(node)){
		jsonStr +='"children":[';
		for(var i = 0;i<child.length;i++){
			var item = child[i];
			item = recursion(child[i]);
		}
	}
	for(var i = 0;i<child.length;i++){
		if(child[i].flag == 'm'){
			jsonStr +='{"id":\''+child[i].id+'\',"text":"'+child[i].text+'","flag":"'+child[i].flag+'"';
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
$(function(){
	
	$('#systemAreaTree').tree({
		data:treedata,
		animate:true,
		checkbox:true,
		onlyLeafCheck:true,
		onCheck:function(node,checked){
			if(checked){
				var li = $("#shortcutMenuList li[id='"+node.id+"']");
				if(li.length > 0){
					li.show();
				}else{
					li = '<li id="'+node.id+'">'+node.text+'</td></tr>';
					li = $(li);
					$("#shortcutMenuList").append(li);
				}
			}else{
				var li = $("#shortcutMenuList li[id='"+node.id+"']");
				li.hide();
			}
			setCss();
		}
	});
	
	$('#btn_ok').click(function(){
		var menulist = new Array();
		$("#shortcutMenuList li[shortcutMenuId]:hidden").each(function(){
			var item = {};
			item.menuId = $(this).attr("id");
			item.shortcutMenuId = $(this).attr("shortcutMenuId");
			item.sts = "P";
			menulist.push(item);
		});
		
		$("#shortcutMenuList li:not([shortcutMenuId]):not(:hidden)").each(function(){
			var item = {};
			item.menuId = $(this).attr("id");
			item.shortcutMenuId = $(this).attr("shortcutMenuId");
			item.sysUserId = $("#sysUserId").val();
			item.sts = "A";
			menulist.push(item);
		});
		
		//提交到后台
		$.ajax({
			type:'POST',
			url:'${ctx_admin}/security/shortcutmenu/1.json?_method=PUT',
			data:'json='+JSON.stringify(menulist),
			dataType:'json',
			success:function(data){
				var shortcutMenu = data.message;
				shortcutMenu = eval('('+shortcutMenu+')');
				var liDom = '';
				for(var i = 0;i<shortcutMenu.length;i++){
					liDom +='<li><a onclick="createTabs(\'${ctx}'+shortcutMenu[i].menuUrl+'\',\''+shortcutMenu[i].sts+'\');" href="javascript:void(0)">';
					liDom +=shortcutMenu[i].sts+'</a></li>';
				}
				var ul = $("#shortcut_menu",parent.document.body);
				ul.empty();
				ul.html(liDom);
				location.reload();
			}
		});
		
	});
	/***********样式加载*************/
	function setCss(){
		$('#shortcutMenuList').css({"list-style":"none","width":"100%","margin-top":"5px"});
		$('#shortcutMenuList li').css({"padding":"2px 10px","margin-left":"10px","min-width":"300px","float":"left","border-bottom":"1px dashed #366DA5","height":"24px","line-height":"24px","color":"#366DA5","font-size":"12px"});
		//$('#shortcutMenuList li:last').css({"border-bottom":"0"});
	}
	setCss();
	$('#flexgrid ul').height($('#flexgrid').height()-28);
});
</script>
<div class="easyui-layout" data-options="fit:true">
    <!-- 区域左侧树 -->
    <div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="目录菜单" fit="true" class="tree-center">
                <ul id="systemAreaTree" style=""></ul>
            </div>
            <div data-options="region:'south'" class="tree-buttons">
            </div>
        </div>
    </div>
    <!-- 中间区域 -->
    <div data-options="region:'center'" class="no-border">
        <div class="easyui-layout" data-options="fit:true">
            <!-- 中间:显示列表-->
			<div id="flexgrid" data-options="region:'center'" class="content">
				<fieldset>
					<legend>快捷菜单列表</legend>
					<ul id="shortcutMenuList" style="overflow:auto">
						<input type="hidden" id="sysUserId" value="${sysUserId}" >
						<c:forEach var="item" items="${shortcutMenulist}" varStatus="status">
							<li class="li" id="${item.menuId}" shortcutMenuId="${item.shortcutMenuId}">${item.sts}</li>
						</c:forEach>
					</ul>
					<div style="clear:both"></div>
				</fieldset>
			</div>
            <div data-options="region:'south'" class="buttons">
            	<input type="button" class="button" id="btn_ok" value="确 定"/>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>