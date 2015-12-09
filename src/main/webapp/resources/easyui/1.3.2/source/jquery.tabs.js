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
	function getMaxScrollWidth(jq) {
		var header = $(jq).children("div.tabs-header");
		var tabsWidth = 0;
		$("ul.tabs li", header).each(function() {
			tabsWidth += $(this).outerWidth(true);
		});
		var wrapWidth = header.children("div.tabs-wrap").width();
		var padding = parseInt(header.find("ul.tabs").css("padding-left"));
		return tabsWidth - wrapWidth + padding;
	};
	function setScrollers(jq) {
		var opts = $.data(jq, "tabs").options;
		var header = $(jq).children("div.tabs-header");
		var tool = header.children("div.tabs-tool");
		var leftScroller = header.children("div.tabs-scroller-left");
		var rightScroller = header.children("div.tabs-scroller-right");
		var wrap = header.children("div.tabs-wrap");
		var height = ($.boxModel == true ? (header.outerHeight() - (tool.outerHeight() - tool
				.height()))
				: header.outerHeight());
		if (opts.plain) {
			height -= 2;
		}
		tool.height(height);
		var fullWidth = 0;
		$("ul.tabs li", header).each(function() {
			fullWidth += $(this).outerWidth(true);
		});
		var realWidth = header.width() - tool.outerWidth();
		if (fullWidth > realWidth) {
			leftScroller.show();
			rightScroller.show();
			tool.css("right", rightScroller.outerWidth());
			wrap.css( {
				marginLeft : leftScroller.outerWidth(),
				marginRight : rightScroller.outerWidth() + tool.outerWidth(),
				left : 0,
				width : realWidth - leftScroller.outerWidth() - rightScroller.outerWidth()
			});
		} else {
			leftScroller.hide();
			rightScroller.hide();
			tool.css("right", 0);
			wrap.css( {
				marginLeft : 0,
				marginRight : tool.outerWidth(),
				left : 0,
				width : realWidth
			});
			wrap.scrollLeft(0);
		}
	};
	function buildButton(jq) {
		var opts = $.data(jq, "tabs").options;
		var header = $(jq).children("div.tabs-header");
		if (opts.tools) {
			if (typeof opts.tools == "string") {
				$(opts.tools).addClass("tabs-tool").appendTo(header);
				$(opts.tools).show();
			} else {
				header.children("div.tabs-tool").remove();
				var tool = $("<div class=\"tabs-tool\"></div>").appendTo(header);
				for ( var i = 0; i < opts.tools.length; i++) {
					var button = $("<a href=\"javascript:void(0);\"></a>")
							.appendTo(tool);
					button[0].onclick = eval(opts.tools[i].handler || function() {
					});
					button.linkbutton($.extend( {}, opts.tools[i], {
						plain : true
					}));
				}
			}
		} else {
			header.children("div.tabs-tool").remove();
		}
	};
	function setSize(jq) {
		var opts = $.data(jq, "tabs").options;
		var cc = $(jq);
		if (opts.fit == true) {
			var p = cc.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		cc.width(opts.width).height(opts.height);
		var header = $(jq).children("div.tabs-header");
		if ($.boxModel == true) {
			header.width(opts.width - (header.outerWidth() - header.width()));
		} else {
			header.width(opts.width);
		}
		setScrollers(jq);
		var panels = $(jq).children("div.tabs-panels");
		var height = opts.height;
		if (!isNaN(height)) {
			if ($.boxModel == true) {
				var height = panels.outerHeight() - panels.height();
				panels.css("height", (height - header.outerHeight() - height) || "auto");
			} else {
				panels.css("height", height - header.outerHeight());
			}
		} else {
			panels.height("auto");
		}
		var width = opts.width;
		if (!isNaN(width)) {
			if ($.boxModel == true) {
				panels.width(width - (panels.outerWidth() - panels.width()));
			} else {
				panels.width(width);
			}
		} else {
			panels.width("auto");
		}
	};
	function fitContent(jq) {
		var opts = $.data(jq, "tabs").options;
		var tab = getSelected(jq);
		if (tab) {
			var panels = $(jq).children("div.tabs-panels");
			var width = opts.width == "auto" ? "auto" : panels.width();
			var height = opts.height == "auto" ? "auto" : panels.height();
			tab.panel("resize", {
				width : width,
				height : height
			});
		}
	};
	function wrapTabs(jq) {
		var cc = $(jq);
		cc.addClass("tabs-container");
		cc.wrapInner("<div class=\"tabs-panels\"/>");
		$("<div class=\"tabs-header\">"
				+ "<div class=\"tabs-scroller-left\"></div>"
				+ "<div class=\"tabs-scroller-right\"></div>"
				+ "<div class=\"tabs-wrap\">"
				+ "<ul class=\"tabs\"></ul>" + "</div>" + "</div>")
				.prependTo(jq);
		var tabs = [];
		var tp = cc.children("div.tabs-panels");
		tp.children("div[selected]").attr("toselect", "true");
		tp.children("div").each(function() {
			var pp = $(this);
			tabs.push(pp);
			createTab(jq, pp);
		});
		cc.children("div.tabs-header").find(
				".tabs-scroller-left, .tabs-scroller-right").hover(function() {
			$(this).addClass("tabs-scroller-over");
		}, function() {
			$(this).removeClass("tabs-scroller-over");
		});
		cc.bind("_resize", function(e, param) {
			var opts = $.data(jq, "tabs").options;
			if (opts.fit == true || param) {
				setSize(jq);
				fitContent(jq);
			}
			return false;
		});
		return tabs;
	};
	function setProperties(jq) {
		var opts = $.data(jq, "tabs").options;
		var header = $(jq).children("div.tabs-header");
		var panel = $(jq).children("div.tabs-panels");
		if (opts.plain == true) {
			header.addClass("tabs-header-plain");
		} else {
			header.removeClass("tabs-header-plain");
		}
		if (opts.border == true) {
			header.removeClass("tabs-header-noborder");
			panel.removeClass("tabs-panels-noborder");
		} else {
			header.addClass("tabs-header-noborder");
			panel.addClass("tabs-panels-noborder");
		}
		$(".tabs-scroller-left", header).unbind(".tabs").bind("click.tabs",
				function() {
					var header = $(".tabs-wrap", header);
					var pos = header.scrollLeft() - opts.scrollIncrement;
					header.animate( {
						scrollLeft : pos
					}, opts.scrollDuration);
				});
		$(".tabs-scroller-right", header).unbind(".tabs").bind(
				"click.tabs",
				function() {
					var header = $(".tabs-wrap", header);
					var pos = Math.min(header.scrollLeft() + opts.scrollIncrement,
							getMaxScrollWidth(jq));
					header.animate( {
						scrollLeft : pos
					}, opts.scrollDuration);
				});
		var tabs = $.data(jq, "tabs").tabs;
		for ( var i = 0, len = tabs.length; i < len; i++) {
			var tab = tabs[i];
			var tab = tab.panel("options").tab;
			tab.unbind(".tabs").bind("click.tabs", {
				p : tab
			}, function(e) {
				selectTab(jq, getTabIndex(jq, e.data.p));
			}).bind("contextmenu.tabs",{
						p : tab
					},
					function(e) {
						opts.onContextMenu.call(jq, e, e.data.p.panel("options").title);
					});
			tab.find("a.tabs-close").unbind(".tabs").bind("click.tabs", {
				p : tab
			}, function(e) {
				closeTab(jq, getTabIndex(jq, e.data.p));
				return false;
			});
		}
	};
	function createTab(container, pp, options) {
		options = options || {};
		pp.panel($.extend( {}, options, {
			border : false,
			noheader : true,
			closed : true,
			doSize : false,
			iconCls : (options.icon ? options.icon : undefined),
			onLoad : function() {
				if (options.onLoad) {
					options.onLoad.call(this, arguments);
				}
				$.data(container, "tabs").options.onLoad.call(container, pp);
			}
		}));
		var opts = pp.panel("options");
		var header = $(container).children("div.tabs-header");
		var tabs = $("ul.tabs", header);
		var tab = $("<li></li>").appendTo(tabs);
		var tabInner = $("<a href=\"javascript:void(0)\" class=\"tabs-inner\"></a>")
				.appendTo(tab);
		var tabTitle = $("<span class=\"tabs-title\"></span>").html(opts.title)
				.appendTo(tabInner);
		var tabIcon = $("<span class=\"tabs-icon\"></span>").appendTo(tabInner);
		if (opts.closable) {
			tabTitle.addClass("tabs-closable");
			$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>")
					.appendTo(tab);
		}
		if (opts.iconCls) {
			tabTitle.addClass("tabs-with-icon");
			tabIcon.addClass(opts.iconCls);
		}
		if (opts.tools) {
			var tool = $("<span class=\"tabs-p-tool\"></span>").insertAfter(tabInner);
			if (typeof opts.tools == "string") {
				$(opts.tools).children().appendTo(tool);
			} else {
				for ( var i = 0; i < opts.tools.length; i++) {
					var t = $("<a href=\"javascript:void(0)\"></a>").appendTo(tool);
					t.addClass(opts.tools[i].iconCls);
					if (opts.tools[i].handler) {
						t.bind("click", eval(opts.tools[i].handler));
					}
				}
			}
			var pr = tool.children().length * 12;
			if (opts.closable) {
				pr += 8;
			} else {
				pr -= 3;
				tool.css("right", "5px");
			}
			tabTitle.css("padding-right", pr + "px");
		}
		opts.tab = tab;
	};
	function addTab(jq, options) {
		var opts = $.data(jq, "tabs").options;
		var tabs = $.data(jq, "tabs").tabs;
		var pp = $("<div></div>").appendTo($(jq).children("div.tabs-panels"));
		tabs.push(pp);
		createTab(jq, pp, options);
		opts.onAdd.call(jq, options.title);
		setScrollers(jq);
		setProperties(jq);
		selectTab(jq, tabs.length - 1);
	};
	function update(jq, param) {
		var selectHis = $.data(jq, "tabs").selectHis;
		var pp = param.tab;
		var title = pp.panel("options").title;
		pp.panel($.extend( {}, param.options, {
			iconCls : (param.options.icon ? param.options.icon : undefined)
		}));
		var opts = pp.panel("options");
		var tab = opts.tab;
		tab.find("span.tabs-icon").attr("class", "tabs-icon");
		tab.find("a.tabs-close").remove();
		tab.find("span.tabs-title").html(opts.title);
		if (opts.closable) {
			tab.find("span.tabs-title").addClass("tabs-closable");
			$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>").appendTo(tab);
		} else {
			tab.find("span.tabs-title").removeClass("tabs-closable");
		}
		if (opts.iconCls) {
			tab.find("span.tabs-title").addClass("tabs-with-icon");
			tab.find("span.tabs-icon").addClass(opts.iconCls);
		} else {
			tab.find("span.tabs-title").removeClass("tabs-with-icon");
		}
		if (title != opts.title) {
			for ( var i = 0; i < selectHis.length; i++) {
				if (selectHis[i] == title) {
					selectHis[i] = opts.title;
				}
			}
		}
		setProperties(jq);
		$.data(jq, "tabs").options.onUpdate.call(jq, opts.title);
	};
	function closeTab(container, title) {
		var opts = $.data(container, "tabs").options;
		var tabs = $.data(container, "tabs").tabs;
		var selectHis = $.data(container, "tabs").selectHis;
		if (!exists(container, title)) {
			return;
		}
		var tab = getTab(container, title);
		var title = tab.panel("options").title;
		if (opts.onBeforeClose.call(container, title) == false) {
			return;
		}
		var tab = getTab(container, title, true);
		tab.panel("options").tab.remove();
		tab.panel("destroy");
		opts.onClose.call(container, title);
		setScrollers(container);
		for ( var i = 0; i < selectHis.length; i++) {
			if (selectHis[i] == title) {
				selectHis.splice(i, 1);
				i--;
			}
		}
		var lastTab = selectHis.pop();
		if (lastTab) {
			selectTab(container, lastTab);
		} else {
			if (tabs.length) {
				selectTab(container, 0);
			}
		}
	};
	function getTab(container, title, close) {
		var tabs = $.data(container, "tabs").tabs;
		if (typeof title == "number") {
			if (title < 0 || title >= tabs.length) {
				return null;
			} else {
				var tab = tabs[title];
				if (close) {
					tabs.splice(title, 1);
				}
				return tab;
			}
		}
		for ( var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			if (tab.panel("options").title == title) {
				if (close) {
					tabs.splice(i, 1);
				}
				return tab;
			}
		}
		return null;
	};
	function getTabIndex(jq, tab) {
		var tabs = $.data(jq, "tabs").tabs;
		for ( var i = 0; i < tabs.length; i++) {
			if (tabs[i][0] == $(tab)[0]) {
				return i;
			}
		}
		return -1;
	};
	function getSelected(jq) {
		var tabs = $.data(jq, "tabs").tabs;
		for ( var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			if (tab.panel("options").closed == false) {
				return tab;
			}
		}
		return null;
	};
	function initSelectTab(jq) {
		var tabs = $.data(jq, "tabs").tabs;
		for ( var i = 0; i < tabs.length; i++) {
			if (tabs[i].attr("toselect") == "true") {
				selectTab(jq, i);
				return;
			}
		}
		if (tabs.length) {
			selectTab(jq, 0);
		}
	};
	function selectTab(jq, title) {
		var opts = $.data(jq, "tabs").options;
		var tabs = $.data(jq, "tabs").tabs;
		var selectHis = $.data(jq, "tabs").selectHis;
		if (tabs.length == 0) {
			return;
		}
		var tab = getTab(jq, title);
		if (!tab) {
			return;
		}
		var selected = getSelected(jq);
		if (selected) {
			selected.panel("close");
			selected.panel("options").tab.removeClass("tabs-selected");
		}
		tab.panel("open");
		var title = tab.panel("options").title;
		selectHis.push(title);
		var tab = tab.panel("options").tab;
		tab.addClass("tabs-selected");
		var wrap = $(jq).find(">div.tabs-header div.tabs-wrap");
		var leftPos = tab.position().left + wrap.scrollLeft();
		var left = leftPos - wrap.scrollLeft();
		var right = left + tab.outerWidth();
		if (left < 0 || right > wrap.innerWidth()) {
			var pos = Math.min(leftPos - (wrap.width() - tab.width()) / 2, getMaxScrollWidth(jq));
			wrap.animate( {
				scrollLeft : pos
			}, opts.scrollDuration);
		} else {
			var pos = Math.min(wrap.scrollLeft(), getMaxScrollWidth(jq));
			wrap.animate( {
				scrollLeft : pos
			}, opts.scrollDuration);
		}
		fitContent(jq);
		opts.onSelect.call(jq, title);
	};
	function exists(container, title) {
		return getTab(container, title) != null;
	};
	$.fn.tabs = function(options, param) {
		if (typeof options == "string") {
			return $.fn.tabs.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "tabs");
			var opts;
			if (state) {
				opts = $.extend(state.options, options);
				state.options = opts;
			} else {
				$.data(this, "tabs", {
					options : $.extend( {}, $.fn.tabs.defaults, $.fn.tabs
							.parseOptions(this), options),
					tabs : wrapTabs(this),
					selectHis : []
				});
			}
			buildButton(this);
			setProperties(this);
			setSize(this);
			initSelectTab(this);
		});
	};
	$.fn.tabs.methods = {
		options : function(jq) {
			return $.data(jq[0], "tabs").options;
		},
		tabs : function(jq) {
			return $.data(jq[0], "tabs").tabs;
		},
		resize : function(jq) {
			return jq.each(function() {
				setSize(this);
				fitContent(this);
			});
		},
		add : function(jq, options) {
			return jq.each(function() {
				addTab(this, options);
			});
		},
		close : function(jq, title) {
			return jq.each(function() {
				closeTab(this, title);
			});
		},
		getTab : function(jq, title) {
			return getTab(jq[0], title);
		},
		getTabIndex : function(jq, tab) {
			return getTabIndex(jq[0], tab);
		},
		getSelected : function(jq) {
			return getSelected(jq[0]);
		},
		select : function(jq, title) {
			return jq.each(function() {
				selectTab(this, title);
			});
		},
		exists : function(jq, title) {
			return exists(jq[0], title);
		},
		update : function(jq, param) {
			return jq.each(function() {
				update(this, param);
			});
		}
	};
	$.fn.tabs.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr("fit") ? t.attr("fit") == "true" : undefined),
			border : (t.attr("border") ? t.attr("border") == "true" : undefined),
			plain : (t.attr("plain") ? t.attr("plain") == "true" : undefined),
			tools : t.attr("tools")
		};
	};
	$.fn.tabs.defaults = {
		width : "auto",
		height : "auto",
		plain : false,
		fit : false,
		border : true,
		tools : null,
		scrollIncrement : 100,
		scrollDuration : 400,
		onLoad : function(panel) {
		},
		onSelect : function(title) {
		},
		onBeforeClose : function(title) {
		},
		onClose : function(title) {
		},
		onAdd : function(title) {
		},
		onUpdate : function(title) {
		},
		onContextMenu : function(e, title) {
		}
	};
})(jQuery);
