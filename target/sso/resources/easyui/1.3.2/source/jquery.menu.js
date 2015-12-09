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
	function init(target) {
		$(target).appendTo("body");
		$(target).addClass("menu-top");
		var menus = [];
		adjust($(target));
		var timer = null;
		for ( var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			wrapMenu(menu);
			menu.children("div.menu-item").each(function() {
				bindMenuItemEvent(target, $(this));
			});
			menu.bind("mouseenter", function() {
				if (timer) {
					clearTimeout(timer);
					timer = null;
				}
			}).bind("mouseleave", function() {
				timer = setTimeout(function() {
					hideAll(target);
				}, 100);
			});
		}
		function adjust(menu) {
			menus.push(menu);
			menu.find(">div").each(function() {
				var item = $(this);
				var submenu = item.find(">div");
				if (submenu.length) {
					submenu.insertAfter(target);
					item[0].submenu = submenu;
					adjust(submenu);
				}
			});
		};
		function wrapMenu(menu) {
			menu.addClass("menu").find(">div").each(function() {
				var item = $(this);
				if (item.hasClass("menu-sep")) {
					item.html("&nbsp;");
				} else {
					var text = item.addClass("menu-item").html();
					item.empty().append($("<div class=\"menu-text\"></div>").html(text));
					var icon = item.attr("iconCls") || item.attr("icon");
					if (icon) {
						$("<div class=\"menu-icon\"></div>").addClass(icon).appendTo(item);
					}
					if (item[0].submenu) {
						$("<div class=\"menu-rightarrow\"></div>").appendTo(item);
					}
					if ($.boxModel == true) {
						var height = item.height();
						item.height(height - (item.outerHeight() - item.height()));
					}
				}
			});
			menu.hide();
		};
	};
	function bindMenuItemEvent(target, item) {
		item.unbind(".menu");
		item.bind("mousedown.menu", function() {
			return false;
		}).bind("click.menu", function() {
			if ($(this).hasClass("menu-item-disabled")) {
				return;
			}
			if (!this.submenu) {
				hideAll(target);
				var href = $(this).attr("href");
				if (href) {
					location.href = href;
				}
			}
			var item = $(target).menu("getItem", this);
			$.data(target, "menu").options.onClick.call(target, item);
		}).bind("mouseenter.menu",function(e) {
			item.siblings().each(function() {
				if (this.submenu) {
					hideMenu(this.submenu);
				}
				$(this).removeClass("menu-active");
			});
			item.addClass("menu-active");
			if ($(this).hasClass("menu-item-disabled")) {
				item.addClass("menu-active-disabled");
				return;
			}
			var submenu = item[0].submenu;
			if (submenu) {
				var left = item.offset().left + item.outerWidth() - 2;
				if (left + submenu.outerWidth() + 5 > $(window).width()
						+ $(document).scrollLeft()) {
					left = item.offset().left - submenu.outerWidth() + 2;
				}
				var top = item.offset().top - 3;
				if (top + submenu.outerHeight() > $(window).height()
						+ $(document).scrollTop()) {
					top = $(window).height() + $(document).scrollTop()
							- submenu.outerHeight() - 5;
				}
				showMenu(submenu, {
					left : left,
					top : top
				});
			}
		}).bind("mouseleave.menu", function(e) {
			item.removeClass("menu-active menu-active-disabled");
			var submenu = item[0].submenu;
			if (submenu) {
				if (e.pageX >= parseInt(submenu.css("left"))) {
					item.addClass("menu-active");
				} else {
					hideMenu(submenu);
				}
			} else {
				item.removeClass("menu-active");
			}
		});
	};
	function hideAll(target) {
		var opts = $.data(target, "menu").options;
		hideMenu($(target));
		$(document).unbind(".menu");
		opts.onHide.call(target);
		return false;
	};
	function showTopMenu(target, pos) {
		var opts = $.data(target, "menu").options;
		if (pos) {
			opts.left = pos.left;
			opts.top = pos.top;
			if (opts.left + $(target).outerWidth() > $(window).width()
					+ $(document).scrollLeft()) {
				opts.left = $(window).width() + $(document).scrollLeft()
						- $(target).outerWidth() - 5;
			}
			if (opts.top + $(target).outerHeight() > $(window).height()
					+ $(document).scrollTop()) {
				opts.top -= $(target).outerHeight();
			}
		}
		showMenu($(target), {
			left : opts.left,
			top : opts.top
		}, function() {
			$(document).unbind(".menu").bind("mousedown.menu", function() {
				hideAll(target);
				$(document).unbind(".menu");
				return false;
			});
			opts.onShow.call(target);
		});
	};
	function showMenu(menu, pos, callback) {
		if (!menu) {
			return;
		}
		if (pos) {
			menu.css(pos);
		}
		menu.show(0, function() {
			if (!menu[0].shadow) {
				menu[0].shadow = $("<div class=\"menu-shadow\"></div>").insertAfter(menu);
			}
			menu[0].shadow.css( {
				display : "block",
				zIndex : $.fn.menu.defaults.zIndex++,
				left : menu.css("left"),
				top : menu.css("top"),
				width : menu.outerWidth(),
				height : menu.outerHeight()
			});
			menu.css("z-index", $.fn.menu.defaults.zIndex++);
			if (callback) {
				callback();
			}
		});
	};
	function hideMenu(menu) {
		if (!menu) {
			return;
		}
		hideit(menu);
		menu.find("div.menu-item").each(function() {
			if (this.submenu) {
				hideMenu(this.submenu);
			}
			$(this).removeClass("menu-active");
		});
		function hideit(m) {
			m.stop(true, true);
			if (m[0].shadow) {
				m[0].shadow.hide();
			}
			m.hide();
		};
	};
	function findItem(target, text) {
		var item = null;
		var tmp = $("<div></div>");
		function find(parentMenu) {
			parentMenu.children("div.menu-item").each(function() {
				var item = $(target).menu("getItem", this);
				var s = tmp.empty().html(item.text).text();
				if (text == $.trim(s)) {
					item = item;
				} else {
					if (this.submenu && !item) {
						find(this.submenu);
					}
				}
			});
		};
		find($(target));
		tmp.remove();
		return item;
	};
	function setDisabled(target, itemEl, disabled) {
		var t = $(itemEl);
		if (disabled) {
			t.addClass("menu-item-disabled");
			if (itemEl.onclick) {
				itemEl.onclick1 = itemEl.onclick;
				itemEl.onclick = null;
			}
		} else {
			t.removeClass("menu-item-disabled");
			if (itemEl.onclick1) {
				itemEl.onclick = itemEl.onclick1;
				itemEl.onclick1 = null;
			}
		}
	};
	function appendItem(target, param) {
		var param = $(target);
		if (param.parent) {
			param = param.parent.submenu;
		}
		var menu = $("<div class=\"menu-item\"></div>").appendTo(param);
		$("<div class=\"menu-text\"></div>").html(param.text).appendTo(menu);
		if (param.iconCls) {
			$("<div class=\"menu-icon\"></div>").addClass(param.iconCls).appendTo(menu);
		}
		if (param.id) {
			menu.attr("id", param.id);
		}
		if (param.href) {
			menu.attr("href", param.href);
		}
		if (param.onclick) {
			if (typeof param.onclick == "string") {
				menu.attr("onclick", param.onclick);
			} else {
				menu[0].onclick = eval(param.onclick);
			}
		}
		if (param.handler) {
			menu[0].onclick = eval(param.handler);
		}
		bindMenuItemEvent(target, menu);
	};
	function removeItem(target, itemEl) {
		function remove(el) {
			if (el.submenu) {
				el.submenu.children("div.menu-item").each(function() {
					remove(this);
				});
				var shadow = el.submenu[0].shadow;
				if (shadow) {
					shadow.remove();
				}
				el.submenu.remove();
			}
			$(el).remove();
		};
		remove(itemEl);
	};
	function destroy(target) {
		$(target).children("div.menu-item").each(function() {
			removeItem(target, this);
		});
		if (target.shadow) {
			target.shadow.remove();
		}
		$(target).remove();
	};
	$.fn.menu = function(target, param) {
		if (typeof target == "string") {
			return $.fn.menu.methods[target](this, param);
		}
		target = target || {};
		return this.each(function() {
			var menu = $.data(this, "menu");
			if (menu) {
				$.extend(menu.options, target);
			} else {
				menu = $.data(this, "menu", {
					options : $.extend( {}, $.fn.menu.defaults, target)
				});
				init(this);
			}
			$(this).css( {
				left : menu.options.left,
				top : menu.options.top
			});
		});
	};
	$.fn.menu.methods = {
		show : function(jq, pos) {
			return jq.each(function() {
				showTopMenu(this, pos);
			});
		},
		hide : function(jq) {
			return jq.each(function() {
				hideAll(this);
			});
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroy(this);
			});
		},
		setText : function(jq, param) {
			return jq.each(function() {
				$(param.target).children("div.menu-text").html(param.text);
			});
		},
		setIcon : function(jq, param) {
			return jq.each(function() {
				var item = $(this).menu("getItem", param.target);
				if (item.iconCls) {
					$(item.target).children("div.menu-icon").removeClass(
							item.iconCls).addClass(param.iconCls);
				} else {
					$("<div class=\"menu-icon\"></div>").addClass(param.iconCls)
							.appendTo(param.target);
				}
			});
		},
		getItem : function(jq, itemEl) {
			var item = {
				target : itemEl,
				id : $(itemEl).attr("id"),
				text : $.trim($(itemEl).children("div.menu-text").html()),
				disabled : $(itemEl).hasClass("menu-item-disabled"),
				href : $(itemEl).attr("href"),
				onclick : itemEl.onclick
			};
			var submenu = $(itemEl).children("div.menu-icon");
			if (submenu.length) {
				var cc = [];
				var aa = submenu.attr("class").split(" ");
				for ( var i = 0; i < aa.length; i++) {
					if (aa[i] != "menu-icon") {
						cc.push(aa[i]);
					}
				}
				item.iconCls = cc.join(" ");
			}
			return item;
		},
		findItem : function(jq, text) {
			return findItem(jq[0], text);
		},
		appendItem : function(jq, param) {
			return jq.each(function() {
				appendItem(this, param);
			});
		},
		removeItem : function(jq, itemEl) {
			return jq.each(function() {
				removeItem(this, itemEl);
			});
		},
		enableItem : function(jq, itemEl) {
			return jq.each(function() {
				setDisabled(this, itemEl, false);
			});
		},
		disableItem : function(jq, itemEl) {
			return jq.each(function() {
				setDisabled(this, itemEl, true);
			});
		}
	};
	$.fn.menu.defaults = {
		zIndex : 110000,
		left : 0,
		top : 0,
		onShow : function() {
		},
		onHide : function() {
		},
		onClick : function(item) {
		}
	};
})(jQuery);
