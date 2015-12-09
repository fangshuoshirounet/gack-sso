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
	 * 设置窗口大小和布局
	 * @param {Object} jq
	 * @param {Object} options={width:宽度,height:高度,left:左侧位置,top:顶部位置}
	 */
	function resize(jq, options) {
		var opt = $.data(jq, "window").options;
		if (options) {
			if (options.width) {
				opt.width = options.width;
			}
			if (options.height) {
				opt.height = options.height;
			}
			if (options.left != null) {
				opt.left = options.left;
			}
			if (options.top != null) {
				opt.top = options.top;
			}
		}
		$(jq).panel("resize", opt);
	};
	/**
	 * 移动窗口到新位置
	 * @param {Object} jq
	 * @param {Object} options={left:左侧位置,top:顶部位置}
	 */
	function move(jq, options) {
		var window = $.data(jq, "window");
		if (options) {
			if (options.left != null) {
				window.options.left = options.left;
			}
			if (options.top != null) {
				window.options.top = options.top;
			}
		}
		$(jq).panel("move", window.options);
		if (window.shadow) {
			window.shadow.css( {
				left : window.options.left,
				top : window.options.top
			});
		}
	};
	function init(jq) {
		var state = $.data(jq, "window");
		var win = $(jq).panel($.extend({},state.options,{
			border : false,
			doSize : true,
			closed : true,
			cls : "window",
			headerCls : "window-header",
			bodyCls : "window-body " + (state.options.noheader ? "window-body-noheader" : ""),
			onBeforeDestroy : function() {
				if (state.options.onBeforeDestroy.call(jq) == false) {
					return false;
				}
				if (state.shadow) {
					state.shadow.remove();
				}
				if (state.mask) {
					state.mask.remove();
				}
			},
			onClose : function() {
				if (state.shadow) {
					state.shadow.hide();
				}
				if (state.mask) {
					state.mask.hide();
				}
				state.options.onClose.call(jq);
			},
			onOpen : function() {
				if (state.mask) {
					state.mask.css( {
						display : "block",
						zIndex : $.fn.window.defaults.zIndex++
					});
				}
				if (state.shadow) {
					state.shadow.css( {
						display : "block",
						zIndex : $.fn.window.defaults.zIndex++,
						left : state.options.left,
						top : state.options.top,
						width : state.window.outerWidth(),
						height : state.window.outerHeight()
					});
				}
				state.window.css("z-index",$.fn.window.defaults.zIndex++);
				state.options.onOpen.call(jq);
			},
			onResize : function(width, height) {
				var opts = $(jq).panel("options");
				state.options.width = opts.width;
				state.options.height = opts.height;
				state.options.left = opts.left;
				state.options.top = opts.top;
				if (state.shadow) {
					state.shadow.css( {
						left : state.options.left,
						top : state.options.top,
						width : state.window.outerWidth(),
						height : state.window.outerHeight()
					});
				}
				state.options.onResize.call(jq,width, height);
			},
			onMinimize : function() {
				if (state.shadow) {
					state.shadow.hide();
				}
				if (state.mask) {
					state.mask.hide();
				}
				state.options.onMinimize.call(jq);
			},
			onBeforeCollapse : function() {
				if (state.options.onBeforeCollapse
						.call(jq) == false) {
					return false;
				}
				if (state.shadow) {
					state.shadow.hide();
				}
			},
			onExpand : function() {
				if (state.shadow) {
					state.shadow.show();
				}
				state.options.onExpand.call(jq);
			}
		}));
		state.window = win.panel("panel");
		if (state.mask) {
			state.mask.remove();
		}
		if (state.options.modal == true) {
			state.mask = $("<div class=\"window-mask\"></div>").insertAfter(state.window);
			state.mask.css( {
				width : (state.options.inline ? state.mask.parent().width() : compatMode().width),
				height : (state.options.inline ? state.mask.parent().height() : compatMode().height),
				display : "none"
			});
		}
		if (state.shadow) {
			state.shadow.remove();
		}
		if (state.options.shadow == true) {
			state.shadow = $("<div class=\"window-shadow\"></div>").insertAfter(state.window);
			state.shadow.css( {
				display : "none"
			});
		}
		if (state.options.left == null) {
			var width = state.options.width;
			if (isNaN(width)) {
				width = state.window.outerWidth();
			}
			if (state.options.inline) {
				var parent = state.window.parent();
				state.options.left = (parent.width() - width) / 2 + parent.scrollLeft();
			} else {
				state.options.left = ($(window).width() - width) / 2 + $(document).scrollLeft();
			}
		}
		if (state.options.top == null) {
			var height = state.window.height;
			if (isNaN(height)) {
				height = state.window.outerHeight();
			}
			if (state.options.inline) {
				var parent = state.window.parent();
				state.options.top = (parent.height() - height) / 2 + parent.scrollTop();
			} else {
				state.options.top = ($(window).height() - height) / 2 + $(document).scrollTop();
			}
		}
		move(jq);
		if (state.options.closed == false) {
			win.window("open");
		}
	};
	function setProperties(jq) {
		var win = $.data(jq, "window");
		win.window.draggable( {
			handle : ">div.panel-header>div.panel-title",
			disabled : win.options.draggable == false,
			onStartDrag : function(e) {
				if (win.mask) {
					win.mask.css("z-index",$.fn.window.defaults.zIndex++);
				}
				if (win.shadow) {
					win.shadow.css("z-index",$.fn.window.defaults.zIndex++);
				}
				win.window.css("z-index", $.fn.window.defaults.zIndex++);
				if (!win.proxy) {
					win.proxy = $("<div class=\"window-proxy\"></div>").insertAfter(win.window);
				}
				win.proxy.css( {
					display : "none",
					zIndex : $.fn.window.defaults.zIndex++,
					left : e.data.left,
					top : e.data.top,
					width : ($.boxModel == true ? (win.window
							.outerWidth() - (win.proxy
							.outerWidth() - win.proxy.width()))
							: win.window.outerWidth()),
					height : ($.boxModel == true ? (win.window
							.outerHeight() - (win.proxy
							.outerHeight() - win.proxy.height()))
							: win.window.outerHeight())
				});
				setTimeout(function() {
					if (win.proxy) {
						win.proxy.show();
					}
				}, 500);
			},
			onDrag : function(e) {
				win.proxy.css( {
					display : "block",
					left : e.data.left,
					top : e.data.top
				});
				return false;
			},
			onStopDrag : function(e) {
				win.options.left = e.data.left;
				win.options.top = e.data.top;
				$(jq).window("move");
				win.proxy.remove();
				win.proxy = null;
			}
		});
		win.window.resizable( {
			disabled : win.options.resizable == false,
			onStartResize : function(e) {
				win.pmask = $("<div class=\"window-proxy-mask\"></div>").insertAfter(win.window);
				win.pmask.css( {
					zIndex : $.fn.window.defaults.zIndex++,
					left : e.data.left,
					top : e.data.top,
					width : win.window.outerWidth(),
					height : win.window.outerHeight()
				});
				if (!win.proxy) {
					win.proxy = $("<div class=\"window-proxy\"></div>").insertAfter(win.window);
				}
				win.proxy.css( {
					zIndex : $.fn.window.defaults.zIndex++,
					left : e.data.left,
					top : e.data.top,
					width : ($.boxModel == true ? (e.data.width - (win.proxy
							.outerWidth() - win.proxy.width()))
							: e.data.width),
					height : ($.boxModel == true ? (e.data.height - (win.proxy
							.outerHeight() - win.proxy.height()))
							: e.data.height)
				});
			},
			onResize : function(e) {
				win.proxy.css( {
					left : e.data.left,
					top : e.data.top,
					width : ($.boxModel == true ? (e.data.width - (win.proxy
							.outerWidth() - win.proxy.width()))
							: e.data.width),
					height : ($.boxModel == true ? (e.data.height - (win.proxy
							.outerHeight() - win.proxy.height()))
							: e.data.height)
				});
				return false;
			},
			onStopResize : function(e) {
				win.options.left = e.data.left;
				win.options.top = e.data.top;
				win.options.width = e.data.width;
				win.options.height = e.data.height;
				resize(jq);
				win.pmask.remove();
				win.pmask = null;
				win.proxy.remove();
				win.proxy = null;
			}
		});
	};
	/**
	 * 判断当前浏览器采用的渲染方式(兼容目前流行的全部浏览器)
	 * @return {TypeName} 
	 */
	function compatMode() {
		//BackCompat：标准兼容模式关闭。
		//CSS1Compat：标准兼容模式开启。
		//当document.compatMode等于BackCompat时，浏览器客户区宽度是document.body.clientWidth；
		//当document.compatMode等于CSS1Compat时，浏览器客户区宽度是document.documentElement.clientWidth。
		//浏览器客户区高度、滚动条高度、滚动条的Left、滚动条的Top等等都是上面的情况。
		if (document.compatMode == "BackCompat") {
			return {
				width : Math.max(document.body.scrollWidth,
						document.body.clientWidth),
				height : Math.max(document.body.scrollHeight,
						document.body.clientHeight)
			};
		} else {
			return {
				width : Math.max(document.documentElement.scrollWidth,
						document.documentElement.clientWidth),
				height : Math.max(document.documentElement.scrollHeight,
						document.documentElement.clientHeight)
			};
		}
	};
	$(window).resize(function() {
		$("body>div.window-mask").css( {
			width : $(window).width(),
			height : $(window).height()
		});
		setTimeout(function() {
			$("body>div.window-mask").css( {
				width : compatMode().width,
				height : compatMode().height
			});
		}, 50);
	});
	/**
	 * 窗口实例化或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.window = function(options, param) {
		if (typeof options == "string") {
			var callMethod = $.fn.window.methods[options];
			if (callMethod) {
				return callMethod(this, param);
			} else {
				return this.panel(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var data = $.data(this, "window");
			if (data) {
				$.extend(data.options, options);
			} else {
				data = $.data(this, "window", {
					options : $.extend( {}, $.fn.window.defaults, $.fn.window
							.parseOptions(this), options)
				});
				if (!data.options.inline) {
					$(this).appendTo("body");
				}
			}
			init(this);
			setProperties(this);
		});
	};
	/**
	 * 方法注册
	 */
	$.fn.window.methods = {
		options : function(jq) {
			var option = jq.panel("options");
			var opt = $.data(jq[0], "window").options;
			return $.extend(opt, {
				closed : option.closed,
				collapsed : option.collapsed,
				minimized : option.minimized,
				maximized : option.maximized
			});
		},
		window : function(jq) {
			return $.data(jq[0], "window").window;
		},
		resize : function(jq, options) {
			return jq.each(function() {
				resize(this, options);
			});
		},
		move : function(jq, options) {
			return jq.each(function() {
				move(this, options);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.window.parseOptions = function(target) {
		var t = $(target);
		return $.extend({},$.fn.panel.parseOptions(target),{
			draggable : (t.attr("draggable") ? t.attr("draggable") == "true" : undefined),
			resizable : (t.attr("resizable") ? t.attr("resizable") == "true" : undefined),
			shadow : (t.attr("shadow") ? t.attr("shadow") == "true" : undefined),
			modal : (t.attr("modal") ? t.attr("modal") == "true" : undefined),
			inline : (t.attr("inline") ? t.attr("inline") == "true" : undefined)
		});
	};
	/**
	 * 默认属性
	 */
	$.fn.window.defaults = $.extend( {}, $.fn.panel.defaults, {
		zIndex : 9000,
		draggable : true,
		resizable : true,
		shadow : true,
		modal : false,
		inline : false,
		title : "New Window",
		collapsible : true,
		minimizable : true,
		maximizable : true,
		closable : true,
		closed : false
	});
})(jQuery);
