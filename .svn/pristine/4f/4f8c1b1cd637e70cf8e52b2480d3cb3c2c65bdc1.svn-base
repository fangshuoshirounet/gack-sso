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
		$(target).addClass("progressbar");
		$(target).html("<div class=\"progressbar-text\"></div><div class=\"progressbar-value\">&nbsp;</div>");
		return $(target);
	};
	function setWidth(target, width) {
		var opts = $.data(target, "progressbar").options;
		var bar = $.data(target, "progressbar").bar;
		if (width) {
			opts.width = width;
		}
		if ($.boxModel == true) {
			bar.width(opts.width - (bar.outerWidth() - bar.width()));
		} else {
			bar.width(opts.width);
		}
		bar.find("div.progressbar-text").width(bar.width());
	};
	$.fn.progressbar = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.progressbar.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "progressbar");
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, "progressbar", {
					options : $.extend( {}, $.fn.progressbar.defaults,
							$.fn.progressbar.parseOptions(this), options),
					bar : init(this)
				});
			}
			$(this).progressbar("setValue", state.options.value);
			setWidth(this);
		});
	};
	$.fn.progressbar.methods = {
		options : function(jq) {
			return $.data(jq[0], "progressbar").options;
		},
		resize : function(jq, width) {
			return jq.each(function() {
				setWidth(this, width);
			});
		},
		getValue : function(jq) {
			return $.data(jq[0], "progressbar").options.value;
		},
		setValue : function(jq, value) {
			if (value < 0) {
				value = 0;
			}
			if (value > 100) {
				value = 100;
			}
			return jq.each(function() {
				var opts = $.data(this, "progressbar").options;
				var text = opts.text.replace(/{value}/, value);
				var oldVal = opts.value;
				opts.value = value;
				$(this).find("div.progressbar-value").width(value + "%");
				$(this).find("div.progressbar-text").html(text);
				if (oldVal != value) {
					opts.onChange.call(this, value, oldVal);
				}
			});
		}
	};
	$.fn.progressbar.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			value : (t.attr("value") ? parseInt(t.attr("value")) : undefined),
			text : t.attr("text")
		};
	};
	$.fn.progressbar.defaults = {
		width : "auto",
		value : 0,
		text : "{value}%",
		onChange : function(newVal, oldVal) {
		}
	};
})(jQuery);
