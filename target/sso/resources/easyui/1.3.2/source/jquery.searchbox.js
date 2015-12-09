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
	 * 初始化搜索框
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function initSearchbox(jq) {
		$(jq).hide();
		var sb = $("<span class=\"searchbox\"></span>").insertAfter(jq);
		var textBox = $("<input type=\"text\" class=\"searchbox-text\">").appendTo(sb);
		$("<span><span class=\"searchbox-button\"></span></span>").appendTo(sb);
		var name = $(jq).attr("name");
		if (name) {
			textBox.attr("name", name);
			$(jq).removeAttr("name").attr("searchboxName", name);
		}
		return sb;
	};
	/**
	 * 重设组件的宽度
	 * @param {Object} jq
	 * @param {Object} width
	 */
	function reSize(jq,width) {
		var opt = $.data(jq, "searchbox").options;
		var sb = $.data(jq, "searchbox").searchbox;
		if (width) {
			opt.width = width;
		}
		sb.appendTo("body");
		if (isNaN(opt.width)) {
			opt.width = sb.outerWidth();
		}
		var width = opt.width - sb.find("a.searchbox-menu").outerWidth()
				- sb.find("span.searchbox-button").outerWidth();
		if ($.boxModel == true) {
			width -= sb.outerWidth() - sb.width();
		}
		sb.find("input.searchbox-text").width(width);
		sb.insertAfter(jq);
	};
	/**
	 * 初始化菜单
	 * @param {Object} jq
	 */
	function initMenu(jq) {
		var sb = $.data(jq, "searchbox");
		var opt = sb.options;
		if (opt.menu) {
			sb.menu = $(opt.menu).menu( {
				onClick : function(item) {
					clickMenu(item);
				}
			});
			//设置默认选中
			var selecteds = sb.menu.children("div.menu-item:first[selected]");
			if (!selecteds.length) {
				selecteds = sb.menu.children("div.menu-item:first");
			}
			selecteds.triggerHandler("click");
		} else {
			sb.searchbox.find("a.searchbox-menu").remove();
			sb.menu = null;
		}
		/**
		 * 点击菜单按钮的事件处理方法
		 * @param {Object} item
		 */
		function clickMenu(item) {
			sb.searchbox.find("a.searchbox-menu").remove();
			var mb = $("<a class=\"searchbox-menu\" href=\"javascript:void(0)\"></a>").html(item.text);
			mb.prependTo(sb.searchbox).menubutton( {
				menu : sb.menu,
				iconCls : item.iconCls
			});
			sb.searchbox.find("input.searchbox-text").attr("name",$(item.target).attr("name") || item.text);
			reSize(jq);
		};
	};
	/**
	 * 事件绑定
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function bindEvent(jq) {
		var sb = $.data(jq, "searchbox");
		var opt = sb.options;
		var textBox = sb.searchbox.find("input.searchbox-text");
		var bt = sb.searchbox.find(".searchbox-button");
		textBox.unbind(".searchbox").bind("blur.searchbox", function(e) {
			opt.value = $(this).val();
			if (opt.value == "") {
				$(this).val(opt.prompt);
				$(this).addClass("searchbox-prompt");
			} else {
				$(this).removeClass("searchbox-prompt");
			}
		}).bind("focus.searchbox", function(e) {
			if ($(this).val() != opt.value) {
				$(this).val(opt.value);
			}
			$(this).removeClass("searchbox-prompt");
		}).bind("keydown.searchbox", function(e) {
			//按回车键
			if (e.keyCode == 13) {
				e.preventDefault();
				var name = $.fn.prop ? textBox.prop("name") : textBox.attr("name");
				opt.value = $(this).val();
				opt.searcher.call(jq, opt.value, name);
				return false;
			}
		});
		bt.unbind(".searchbox").bind("click.searchbox", function() {
			var name = $.fn.prop ? textBox.prop("name") : textBox.attr("name");
			opt.searcher.call(jq, opt.value, name);
		}).bind("mouseenter.searchbox", function() {
			$(this).addClass("searchbox-button-hover");
		}).bind("mouseleave.searchbox", function() {
			$(this).removeClass("searchbox-button-hover");
		});
	};
	/**
	 * 初始化组件文本框
	 * @param {Object} jq
	 */
	function initTextBox(jq) {
		var sb = $.data(jq, "searchbox");
		var opt = sb.options;
		var textBox = sb.searchbox.find("input.searchbox-text");
		if (opt.value == "") {//提示信息
			textBox.val(opt.prompt);
			textBox.addClass("searchbox-prompt");
		} else {//设置值
			textBox.val(opt.value);
			textBox.removeClass("searchbox-prompt");
		}
	};
	/**
	 * 组件实例化或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.searchbox = function(options, param) {
		if (typeof options == "string") {
			return $.fn.searchbox.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var sb = $.data(this, "searchbox");
			if (sb) {
				$.extend(sb.options, options);
			} else {
				sb = $.data(this, "searchbox", {
					options : $.extend( {}, $.fn.searchbox.defaults,
							$.fn.searchbox.parseOptions(this), options),
					searchbox : initSearchbox(this)
				});
			}
			initMenu(this);
			initTextBox(this);
			bindEvent(this);
			reSize(this);
		});
	};
	/**
	 * 方法注册
	 */
	$.fn.searchbox.methods = {
		options : function(jq) {
			return $.data(jq[0], "searchbox").options;
		},
		menu : function(jq) {
			return $.data(jq[0], "searchbox").menu;
		},
		textbox : function(jq) {
			return $.data(jq[0], "searchbox").searchbox
					.find("input.searchbox-text");
		},
		getValue : function(jq) {
			return $.data(jq[0], "searchbox").options.value;
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				$(this).searchbox("options").value = value;
				$(this).searchbox("textbox").val(value);
				$(this).searchbox("textbox").blur();
			});
		},
		getName : function(jq) {
			return $.data(jq[0], "searchbox").searchbox.find(
					"input.searchbox-text").attr("name");
		},
		selectName : function(jq, name) {
			return jq.each(function() {
				var menu = $.data(this, "searchbox").menu;
				if (menu) {
					menu.children("div.menu-item[name=\"" + name + "\"]").triggerHandler("click");
				}
			});
		},
		destroy : function(jq) {
			return jq.each(function() {
				var menu = $(this).searchbox("menu");
				if (menu) {
					menu.menu("destroy");
				}
				$.data(this, "searchbox").searchbox.remove();
				$(this).remove();
			});
		},
		resize : function(jq, width) {
			return jq.each(function() {
				reSize(this, width);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.searchbox.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			prompt : t.attr("prompt"),
			value : t.val(),
			menu : t.attr("menu"),
			searcher : (t.attr("searcher") ? eval(t.attr("searcher")) : undefined)
		};
	};
	/**
	 * 默认属性
	 * @param {Object} title
	 */
	$.fn.searchbox.defaults = {
		width : "auto",
		prompt : "",//显示在输入框里的提示信息
		value : "",
		menu : null,//搜索类型的菜单.每个菜单项可以有下面属性:name:搜索类型的名称,selected:当前选择的搜索类型的名字
		searcher : function(value, name) {//当用户按搜索按钮或者按ENTER键时调用这个方法
		}
	};
})(jQuery);
