/**
 * jQuery EasyUI 1.2.5
 * 
 * Licensed under the GPL terms
 * To use it on other terms please contact us
 *
 * Copyright(c) 2009-2011 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($) {
	/**
	 * 调整大小
	 * @param {Object} jq
	 */
	function reSize(jq) {
		var options = $.data(jq, "accordion").options;
		var panels = $.data(jq, "accordion").panels;
		var cc = $(jq);
		//自适应
		if (options.fit == true) {
			var p = cc.parent();
			options.width = p.width();
			options.height = p.height();
		}
		//宽度设置
		if (options.width > 0) {
			cc.width($.boxModel == true ? (options.width - (cc.outerWidth() - cc
					.width())) : options.width);
		}
		//高度设置
		var height = "auto";
		if (options.height > 0) {
			cc.height($.boxModel == true ? (options.height - (cc.outerHeight() - cc
					.height())) : options.height);
			var outerHeight = panels.length ? panels[0].panel("header").css("height", null)
					.outerHeight() : "auto";
			var height = cc.height() - (panels.length - 1) * outerHeight;
		}
		//循环各panel
		for ( var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			var header = panel.panel("header");
			header.height($.boxModel == true ? (outerHeight - (header.outerHeight() - header
					.height())) : outerHeight);
			panel.panel("resize", {
				width : cc.width(),
				height : height
			});
		}
	};
	/**
	 * 获取被选中的Panel
	 */
	function getSelectedPanel(jq) {
		var panels = $.data(jq, "accordion").panels;
		for ( var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			if (panel.panel("options").collapsed == false) {
				return panel;
			}
		}
		return null;
	};
	/**
	 * 根据Title获取Panel
	 * @param {Object} jq
	 * @param {Object} title
	 * @param {Object} isDelete 获取完后是否删除掉该panel
	 * @return {TypeName} panel
	 */
	function getPanelByTitle(jq, title, isDelete) {
		var panels = $.data(jq, "accordion").panels;
		for ( var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			if (panel.panel("options").title == title) {
				if (isDelete) {
					//删除第i个panel
					panels.splice(i, 1);//splice()方法用于插入、删除或替换数组的元素，详细参考http://www.w3school.com.cn/js/jsref_splice.asp
				}
				return panel;
			}
		}
		return null;
	};
	/**
	 * 渲染折叠面板
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function renderAccordion(jq) {
		var cc = $(jq);//
		cc.addClass("accordion");//添加折叠面板样式
		//设置边框
		if (cc.attr("border") == "false") {
			cc.addClass("accordion-noborder");
		} else {
			cc.removeClass("accordion-noborder");
		}
		var selectedPanels = cc.children("div[selected]");//获取设置了selected的面板
		cc.children("div").not(selectedPanels).attr("collapsed", "true");//将未设置了selected的面板的collapsed设置为true
		if (selectedPanels.length == 0) {
			cc.children("div:first").attr("collapsed", "false");//展开第一个面板
		}
		var panels = [];
		//循环所有面板
		cc.children("div").each(function() {
			var pp = $(this);
			panels.push(pp);
			createNewPanel(jq, pp, {});//创建面板
		});
		//自适应
		cc.bind("_resize", function(e, fit) {
			var options = $.data(jq, "accordion").options;
			if (options.fit == true || fit) {
				reSize(jq);
			}
			return false;
		});
		return {
			accordion : cc,
			panels : panels
		};
	};
	/**
	 * 创建新增的Panel
	 * @param {Object} jq
	 * @param {Object} pp 新增Panel的div元素对象
	 * @param {Object} options
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function createNewPanel(jq, pp, options) {
		//初始化panel
		pp.panel($.extend( {}, options,{
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false,
			doSize : false,
			tools : [ {
				iconCls : "accordion-collapse",
				handler : function() {
					var animate = $.data(jq, "accordion").options.animate;
					if (pp.panel("options").collapsed) {
						stopAll(jq);
						pp.panel("expand", animate);//调用panel的expand方法
					} else {
						stopAll(jq);
						pp.panel("collapse", animate);//调用panel的collapse方法
					}
					return false;
				}
			} ],
			onBeforeExpand : function() {
				var selectedPanel = getSelectedPanel(jq);//获取展开前被选中的panel
				if (selectedPanel) {
					var header = $(selectedPanel).panel("header");
					header.removeClass("accordion-header-selected");//把展开前被选中的panel的选中样式设置为未选中
					header.find(".accordion-collapse").triggerHandler("click");//把展开前被选中的panel合上
				}
				var header = pp.panel("header");
				header.addClass("accordion-header-selected");//样式设置为已选中
				header.find(".accordion-collapse").removeClass("accordion-expand");//样式设置为已展开
			},
			onExpand : function() {
				var options = $.data(jq, "accordion").options;
				options.onSelect.call(jq, pp.panel("options").title);//返回选择的panle的title给onSelect事件作为参数，并响应事件
			},
			onBeforeCollapse : function() {
				var header = pp.panel("header");
				header.removeClass("accordion-header-selected");//去掉选中样式
				header.find(".accordion-collapse").addClass("accordion-expand");//样式设置为未展开
			}
		}));
		pp.panel("body").addClass("accordion-body");//添加新增panel的body样式
		//单击选中新增panel并展开
		pp.panel("header").addClass("accordion-header").click(function() {
			$(this).find(".accordion-collapse").triggerHandler("click");
			return false;
		});
	};
	/**
	 * 根据Title选中Panel
	 * @param {Object} jq
	 * @param {Object} title
	 * @return {TypeName} void 
	 */
	function selectByTitle(jq, title) {
		var options = $.data(jq, "accordion").options;
//		var panels = $.data(jq, "accordion").panels;//方法内未用到，估计多写了
		var selectedPanel = getSelectedPanel(jq);//获取当前选中的panel
		if (selectedPanel && selectedPanel.panel("options").title == title) {
			return;//若title就是当前选中panel，则不作任何操作
		}
		var panel = getPanelByTitle(jq, title);//根据Title获取panel
		if (panel) {
			panel.panel("header").triggerHandler("click");//triggerHandler()方法触发被选元素的指定事件类型,详情参考http://www.w3school.com.cn/jquery/event_triggerhandler.asp
		} else {//若title对应的panel不存在，则仍为之前选择的panel（虽selectedPanel本已经选中，但确保onSelect事件的执行，这里仍重新选择）
			if (selectedPanel) {
				selectedPanel.panel("header").addClass("accordion-header-selected");
				//call()方法调用一个对象的一个方法，以另一个对象替换当前对象，详情参考http://www.cnblogs.com/sweting/archive/2009/12/21/1629204.html
				options.onSelect.call(jq, selectedPanel.panel("options").title);//返回选择的panel的title给onSelect事件，并响应事件
			}
		}
	};
	/**
	 * 停止折叠面板所有正在运行的动画
	 * @param {Object} jq
	 */
	function stopAll(jq) {
		var panels = $.data(jq, "accordion").panels;
		for ( var i = 0; i < panels.length; i++) {
			panels[i].stop(true, true);
		}
	};
	/**
	 * 新增Panel
	 * @param {Object} jq
	 * @param {Object} options
	 */
	function add(jq, options) {
		var options = $.data(jq, "accordion").options;
		var panels = $.data(jq, "accordion").panels;
		stopAll(jq);
		options.collapsed = options.selected == undefined ? true : options.selected;
		var pp = $("<div></div>").appendTo(jq);//新增Panel的div元素对象
		panels.push(pp);
		createNewPanel(jq, pp, options);
		reSize(jq);
		options.onAdd.call(jq, options.title);//返回添加的panel的title给onAdd事件作为参数，并响应事件
		selectByTitle(jq, options.title);
	};
	/**
	 * 删除指定Title的panel
	 * @param {Object} jq
	 * @param {Object} title
	 * @return {TypeName} void
	 */
	function removeByTile(jq, title) {
		var options = $.data(jq, "accordion").options;
		var panels = $.data(jq, "accordion").panels;
		stopAll(jq);
		//删除前判断onBeforeRemove的返回结果
		if (options.onBeforeRemove.call(jq, title) == false) {
			return;//若onBeforeRemove返回false则取消删除
		}
		var panel = getPanelByTitle(jq, title, true);
		if (panel) {
			panel.panel("destroy");//销毁panel
			if (panels.length) {
				reSize(jq);
				var selectedPanel = getSelectedPanel(jq);
				if (!selectedPanel) {
					//若当前没有panel被选中，则选中第一个panel
					selectByTitle(jq, panels[0].panel("options").title);
				}
			}
		}
		options.onRemove.call(jq, title);//返回要删除的title给onRemove事件作为参数，并响应事件
	};
	/**
	 * 折叠面板实例化或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.accordion = function(options, param) {
		if (typeof options == "string") {
			return $.fn.accordion.methods[options](this, param);//调用相应的方法
		}
		options = options || {};
		return this.each(function() {
			var data = $.data(this, "accordion");
			var opts;
			if (data) {
				opts = $.extend(data.options, options);
				data.opts = opts;
			} else {
				opts = $.extend( {}, $.fn.accordion.defaults, $.fn.accordion
						.parseOptions(this), options);
				var r = renderAccordion(this);//渲染折叠面板
				$.data(this, "accordion", {
					options : opts,
					accordion : r.accordion,
					panels : r.panels
				});
			}
			reSize(this);
			selectByTitle(this);
		});
	};
	/**
	 * 方法注册
	 */
	$.fn.accordion.methods = {
		options : function(jq) {
			return $.data(jq[0], "accordion").options;
		},
		panels : function(jq) {
			return $.data(jq[0], "accordion").panels;
		},
		resize : function(jq) {
			return jq.each(function() {
				reSize(this);
			});
		},
		getSelected : function(jq) {
			return getSelectedPanel(jq[0]);
		},
		getPanel : function(jq, title) {
			return getPanelByTitle(jq[0], title);
		},
		select : function(jq, title) {
			return jq.each(function() {
				selectByTitle(this, title);
			});
		},
		add : function(jq, options) {
			return jq.each(function() {
				add(this, options);
			});
		},
		remove : function(jq, title) {
			return jq.each(function() {
				removeByTile(this, title);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.accordion.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr("fit") ? t.attr("fit") == "true" : undefined),
			border : (t.attr("border") ? t.attr("border") == "true" : undefined),
			animate : (t.attr("animate") ? t.attr("animate") == "true" : undefined)
		};
	};
	/**
	 * 默认属性
	 * @param {Object} title
	 */
	$.fn.accordion.defaults = {
		width : "auto",
		height : "auto",
		fit : false,
		border : true,
		animate : true,
		onSelect : function(title) {
		},
		onAdd : function(title) {
		},
		onBeforeRemove : function(title) {
		},
		onRemove : function(title) {
		}
	};
})(jQuery);
