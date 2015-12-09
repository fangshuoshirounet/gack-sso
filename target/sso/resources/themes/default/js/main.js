var $dtt = null;
var iframeName = null;
 
function getDtt() {
	 
	var index=$('#workTabs').tabs('getTabIndex',$('#workTabs').tabs('getSelected'));
    var frameName = "funcContentIframeName" + index;
    return window.frames[frameName];
}

//打开页签
function createTabs(url, title,node) {
	/*$('#firstPage').attr("src",url);*/
	var length = $('#workTabs').tabs("tabs").length;
	if ($('#workTabs').tabs('exists', title)) {
		$('#workTabs').tabs('select', title);
		var selectIndex = $('#workTabs').tabs('getTabIndex',
				$('#workTabs').tabs('getSelected'));
		iframeName = "funcContentIframeName" + selectIndex;
	} else {
		if (length > 9) {
			jQuery.messager.alert('提示:', '您创建的窗口数量已经超过10个，请先关闭部分窗口!', 'info');
			return;
		} else {
			iframeName = "funcContentIframeName" + length;
			var content = '<iframe scrolling=\"no\" id=\"' + iframeName
					+ '\" name=\"' + iframeName
					+ '\" frameborder=\"0\"  src=\"' + url
					+ '\" style=\"width:100%;height:100%;\"></iframe>';
			$('#workTabs').tabs('add', {
				title : title,
				closable : true,
				content : content
			});
		}
	}
	// 获取当前页面的DOM对象（兼容IE和火狐）
	if (document.all) {// IE
		$dtt = window.frames(iframeName);
	} else {// Firefox
		$dtt = $("#" + iframeName);
	}
	//添加obj参数,用来改变第二级菜单的样式
	if (!node){
		return;
	}
	var level = 2;
	if (node) {
		level = $(node).parent().parent().attr("class");
		level = level.substring(level.length - 1);
		level = parseInt(level) + 1;
	}
	if(level != 3){
		return;
	}
	//该接点设置为蓝色,其他的就设置为白色
	var first = $(node).parent().hasClass("level3_first_m");
	if(first){
		//$(node).css("background-color","#FFF");
		$(node).css("background-image","url(" + imgPath + "/first_blue.png)");
	}else{
		$(node).css("background-image","url(" + imgPath + "/center_blue.png)");
	}
	$(node).css("color", "#FFF");
	//处理和它同级的目录和菜单的样式
	$(node).parent().siblings("li").each(
		function() {
			if ($(this).hasClass("c")) {
				$(this).find("ul:first").slideUp('normal');
				$(this).find("a:first").css("background-image","url(" + imgPath+ "/center_w_plus.png)");
				$(this).find("a:first").css("color","#366DA5");
			}else if($(this).hasClass("m")){
				$(this).find("a:first").css("background-image","url(" + imgPath+ "/center_w.png)");
				$(this).find("a:first").css("color","#366DA5");
			}else{
				$(this).find("a:first").css("background-image","url(" + imgPath+ "/first_w.png)");
				$(this).find("a:first").css("color","#366DA5");
			}
		});
}

/**
 * 关闭当前tab页面
 */
function closeTabs(){
	var tab = $('#workTabs').tabs('getSelected');
	var index = $('#workTabs').tabs('getTabIndex',tab);
	$('#workTabs').tabs('close',index);
}
//打开弹出框
function openDialog(options) {
	if(!options.onClose) {
		options.onClose= closeDialog;
	}
	$("#model1").dialog(options);
}
function closeDialog() {
	$("#model1").dialog("destroy");
	$("#model_level1").append('<div id="model1"></div>');
}
/**
 * 二级窗体视图
 */
function openDialoglevel2(options) {
	if(!options.onClose) {
		options.onClose= closeDialoglevel2;
	}
	$("#model2").dialog(options);
}
function closeDialoglevel2() {
	$("#model2").dialog("destroy");
	$("#model_level2").append('<div id="model2"></div>');
}

/**
 * change first menu’s css
 */
function changeFirstBg(node){
	var level = 2;
	if (node) {
		level = $(node).parent().parent().attr("class");
		level = level.substring(level.length - 1);
		level = parseInt(level) + 1;
	}
	if(level > 3){
		return;
	}
	var fisrtNode = $("#funcDiv>ul>li>a:first");
	//var nodeIsFisrt = typeof($(node).attr("first")) != "undefined";
	var isCatalog = fisrtNode.parent().hasClass("c");
	
	var bgimg = $("#funcDiv>ul>li>a:first").css("background-image");
	if (bgimg.indexOf("center_blue_minus.png") > 0) {
		if(isCatalog){
			fisrtNode.css("background-image","url(" + imgPath + "/first_blue_minus.png)");
			fisrtNode.css("color", "#FFF");
		}else{
			fisrtNode.css("background-image","url(" + imgPath + "/first_blue.png)");
			fisrtNode.css("color", "#FFF");
		}
	} else {
		if(isCatalog){
			fisrtNode.css("background-image","url(" + imgPath + "/first_w_plus.png)");
			fisrtNode.css("color", "#366DA5");
		}else{
			fisrtNode.css("background-image","url(" + imgPath + "/first_w.png)");
			fisrtNode.css("color", "#366DA5");
		}
		
	}
	$("#funcDiv>ul>li>a:first").css("background-color","#EAEDF0");
}
/**
 * open menu or catalog event
 * 
 * @param menuCatalogId
 * @param node
 */
function openMenuCatalogLevel(menuCatalogId, node) {
	// 获取当前级别下的所有目录和菜单
	var curMenu = new Array();
	var curCatalog = new Array(); 
	for ( var i = 0; i < menu.length; i++) { 
		if (menu[i].menuCatalogId == menuCatalogId)
			curMenu.push(menu[i]);
	}
	for ( var i = 0; i < catalog.length; i++) {
		if (catalog[i].parentCatalogId == menuCatalogId)
			curCatalog.push(catalog[i]);
	}
	// 添加目录和菜单--如果是点击a标签
	var level = 'level2';
	if (node) {
		level = $(node).parent().parent().attr("class");
		level = level.substring(level.length - 1);
		level = parseInt(level) + 1;
		level = "level" + level;
	}
	var catalogHtml = '<ul class="' + level + '" style="display:none">';
	//添加该级别所有菜单项,点击的时候绑定createTabs方法,打开页签
	for ( var i = 0; i < curMenu.length; i++) {
		if(i==0 && level == 'level2'){
			catalogHtml += '<li class="level3_first_m"><a href="#" onclick="createTabs(\''+ path + curMenu[i].menuUrl + '\',\'' + curMenu[i].sts+ '\',this)">' + curMenu[i].sts + '</a></li>';
		}else{
			catalogHtml += '<li class="m"><a href="#" onclick="createTabs(\''+ path + curMenu[i].menuUrl + '\',\'' + curMenu[i].sts+ '\',this)">' + curMenu[i].sts + '</a></li>';
		}
	}
	//添加该级别目录,点击的时候继续绑定openMenuCatalogLevel方法,展开该目录
	for ( var i = 0; i < curCatalog.length; i++) {
			catalogHtml += '<li class="c"><a href="#" onclick="openMenuCatalogLevel(\''+ curCatalog[i].menuCatalogId+ '\',this)">'+ curCatalog[i].sts + '</a></li>';
	}
	
	catalogHtml += '</ul>';
	//node是真正的树上的接点,而树节点应该属于第3级
	if (node) {
		var ulNode = $(node).next();
		if (ulNode.is('ul')) {
			if (ulNode.is(':visible')) {
				ulNode.slideUp('normal');
				if (level == 'level3') {
					isMenu = $(node).parent().hasClass("m");
					if(isMenu){
						$(node).css("background-image","url(" + imgPath + "/center_w.png)");
					}else{
						$(node).css("background-image","url(" + imgPath + "/center_w_plus.png)");
					}
					$(node).css("color", "#366DA5");
				} else {
					$(node).css("background-image","url(" + imgPath + "/plus_blue.png)");
				}
			} else {
				ulNode.slideDown('normal');
				if (level == 'level3') {
					$(node).css("background-image","url(" + imgPath + "/center_blue_minus.png)");
					$(node).css('color', '#FFF');
				} else {
					$(node).css("background-image","url(" + imgPath + "/minus_blue.png)");
				}
				$(node).parent().siblings("li").each(
						function() {
							if ($(this).hasClass("c")) {
								$(this).children("ul").slideUp('normal');
								if (level == 'level3') {
									$(this).children("a").css("background-image","url(" + imgPath+ "/center_w_plus.png)");
									$(this).children("a").css("color","#366DA5");
								} else {
									$(this).children("a").css("background-image","url(" + imgPath+ "/plus_blue.png)");
								}
							}else if($(this).hasClass("m")){
								if (level == 'level3'){
									$(this).find("a:first").css("background-image","url(" + imgPath+ "/center_w.png)");
									$(this).find("a:first").css("color","#366DA5");
								}
							}
						});
			}
		} else {
			$(node).parent().append(catalogHtml);
			$(node).next().slideDown('normal');
			if (level == 'level3') {
				$(node).css("background-image","url(" + imgPath + "/center_blue_minus.png)");
				$(node).css('color', '#FFF');
			} else {
				$(node).css("background-image","url(" + imgPath + "/minus_blue.png)");
			}
			$(node).parent().siblings("li").each(
					function() {
						if ($(this).hasClass("c")) {
							$(this).children("ul").slideUp('normal');
							if (level == 'level3') {
								$(this).children("a").css("background-image","url(" + imgPath+ "/center_w_plus.png)");
								$(this).children("a").css("color", "#366DA5");
							} else {
								$(this).children("a").css("background-image","url(" + imgPath + "/plus_blue.png)");
							}
						}else if($(this).hasClass("m")){
							if (level == 'level3'){
								$(this).find("a:first").css("background-image","url(" + imgPath+ "/center_w.png)");
								$(this).find("a:first").css("color","#366DA5");
							}
						}
					});
		}
	} else {
		$("#funcDiv").html(catalogHtml);
		$("#funcDiv").children('ul').slideDown('normal');
	}
	changeFirstBg(node);
}