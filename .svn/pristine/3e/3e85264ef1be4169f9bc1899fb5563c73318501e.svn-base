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
		var opts = $.data(target, "menubutton").options;
		var btn = $(target);
		btn.removeClass("m-btn-active m-btn-plain-active");
		btn.linkbutton($.extend( {}, opts, {
			text : opts.text + "<span class=\"m-btn-downarrow\">&nbsp;</span>"
		}));
		if (opts.menu) {
			$(opts.menu).menu({
				onShow : function() {
					btn.addClass((opts.plain == true) ? "m-btn-plain-active" : "m-btn-active");
				},
				onHide : function() {
					btn.removeClass((opts.plain == true) ? "m-btn-plain-active" : "m-btn-active");
				}
			});
		}
		setDisabled(target, opts.disabled);
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, "menubutton").options;
		opts.disabled = disabled;
		var btn = $(target);
		if (disabled) {
			btn.linkbutton("disable");
			btn.unbind(".menubutton");
		} else {
			btn.linkbutton("enable");
			btn.unbind(".menubutton");
			btn.bind("click.menubutton", function() {
				showMenu();
				return false;
			});
			var timer = null;
			btn.bind("mouseenter.menubutton", function() {
				timer = setTimeout(function() {
					showMenu();
				}, opts.duration);
				return false;
			}).bind("mouseleave.menubutton", function() {
				if (timer) {
					clearTimeout(timer);
				}
			});
		}
		function showMenu() {
			if (!opts.menu) {
				return;
			}
			var left = btn.offset().left;
			if (left + $(opts.menu).outerWidth() + 5 > $(window).width()) {
				left = $(window).width() - $(opts.menu).outerWidth() - 5;
			}
			$("body>div.menu-top").menu("hide");
			$(opts.menu).menu("show", {
				left : left,
				top : btn.offset().top + btn.outerHeight()
			});
			btn.blur();
		};
	};
	$.fn.menubutton = function(options, param) {
		if (typeof options == "string") {
			return $.fn.menubutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var mb = $.data(this, "menubutton");
			if (mb) {
				$.extend(mb.options, options);
			} else {
				$.data(this, "menubutton", {
					options : $.extend( {}, $.fn.menubutton.defaults,
							$.fn.menubutton.parseOptions(this), options)
				});
				$(this).removeAttr("disabled");
			}
			init(this);
		});
	};
	$.fn.menubutton.methods = {
		options : function(jq) {
			return $.data(jq[0], "menubutton").options;
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				setDisabled(this, true);
			});
		}
	};
	$.fn.menubutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.linkbutton.parseOptions(target), {
			menu : t.attr("menu"),
			duration : t.attr("duration")
		});
	};
	$.fn.menubutton.defaults = $.extend( {}, $.fn.linkbutton.defaults, {
		plain : true,
		menu : null,
		duration : 100
	});
})(jQuery);
