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
	function createButton(target) {
		var opts = $.data(target, "linkbutton").options;
		$(target).empty();
		$(target).addClass("l-btn");
		if (opts.id) {
			$(target).attr("id", opts.id);
		} else {
			$.fn.removeProp ? $(target).removeProp("id") : $(target).removeAttr("id");
		}
		if (opts.plain) {
			$(target).addClass("l-btn-plain");
		} else {
			$(target).removeClass("l-btn-plain");
		}
		if (opts.text) {
			$(target).html(opts.text).wrapInner(
					"<span class=\"l-btn-left\">"
							+ "<span class=\"l-btn-text\">" + "</span>"
							+ "</span>");
			if (opts.iconCls) {
				$(target).find(".l-btn-text").addClass(opts.iconCls).css(
						"padding-left", "20px");
			}
		} else {
			$(target).html("&nbsp;").wrapInner(
					"<span class=\"l-btn-left\">"
							+ "<span class=\"l-btn-text\">"
							+ "<span class=\"l-btn-empty\"></span>" + "</span>"
							+ "</span>");
			if (opts.iconCls) {
				$(target).find(".l-btn-empty").addClass(opts.iconCls);
			}
		}
		$(target).unbind(".linkbutton").bind("focus.linkbutton", function() {
			if (!opts.disabled) {
				$(this).find("span.l-btn-text").addClass("l-btn-focus");
			}
		}).bind("blur.linkbutton", function() {
			$(this).find("span.l-btn-text").removeClass("l-btn-focus");
		});
		setDisabled(target, opts.disabled);
	};
	function setDisabled(target, disabled) {
		var lb = $.data(target, "linkbutton");
		if (disabled) {
			lb.options.disabled = true;
			var href = $(target).attr("href");
			if (href) {
				lb.href = href;
				$(target).attr("href", "javascript:void(0)");
			}
			if (target.onclick) {
				lb.onclick = target.onclick;
				target.onclick = null;
			}
			$(target).addClass("l-btn-disabled");
		} else {
			lb.options.disabled = false;
			if (lb.href) {
				$(target).attr("href", lb.href);
			}
			if (lb.onclick) {
				target.onclick = lb.onclick;
			}
			$(target).removeClass("l-btn-disabled");
		}
	};
	$.fn.linkbutton = function(options, param) {
		if (typeof options == "string") {
			return $.fn.linkbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var lb = $.data(this, "linkbutton");
			if (lb) {
				$.extend(lb.options, options);
			} else {
				$.data(this, "linkbutton", {
					options : $.extend( {}, $.fn.linkbutton.defaults,
							$.fn.linkbutton.parseOptions(this), options)
				});
				$(this).removeAttr("disabled");
			}
			createButton(this);
		});
	};
	$.fn.linkbutton.methods = {
		options : function(jq) {
			return $.data(jq[0], "linkbutton").options;
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
	$.fn.linkbutton.parseOptions = function(target) {
		var t = $(target);
		return {
			id : t.attr("id"),
			disabled : (t.attr("disabled") ? true : undefined),
			plain : (t.attr("plain") ? t.attr("plain") == "true" : undefined),
			text : $.trim(t.html()),
			iconCls : (t.attr("icon") || t.attr("iconCls"))
		};
	};
	$.fn.linkbutton.defaults = {
		id : null,
		disabled : false,
		plain : false,
		text : "",
		iconCls : null
	};
})(jQuery);
