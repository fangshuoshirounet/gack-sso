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
	function hiddenField(target) {
		var v = $("<input type=\"hidden\">").insertAfter(target);
		var name = $(target).attr("name");
		if (name) {
			v.attr("name", name);
			$(target).removeAttr("name").attr("numberboxName", name);
		}
		return v;
	};
	function initValue(target) {
		var opts = $.data(target, "numberbox").options;
		var fn = opts.onChange;
		opts.onChange = function() {
		};
		fixValue(target, opts.parser.call(target, opts.value));
		opts.onChange = fn;
	};
	function getValue(target) {
		return $.data(target, "numberbox").field.val();
	};
	function fixValue(target, val) {
		var nb = $.data(target, "numberbox");
		var opts = nb.options;
		var value = getValue(target);
		val = opts.parser.call(target, val);
		opts.value = val;
		nb.field.val(val);
		$(target).val(opts.formatter.call(target, val));
		if (value != val) {
			opts.onChange.call(target, val, value);
		}
	};
	function bindEvents(target) {
		var opts = $.data(target, "numberbox").options;
		$(target).unbind(".numberbox").bind("keypress.numberbox",function(e) {
			if (e.which == 45) {
				return true;
			}
			if (e.which == 46) {
				return true;
			} else {
				if ((e.which >= 48 && e.which <= 57
						&& e.ctrlKey == false && e.shiftKey == false)
						|| e.which == 0 || e.which == 8) {
					return true;
				} else {
					if (e.ctrlKey == true
							&& (e.which == 99 || e.which == 118)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}).bind("paste.numberbox", function() {
			if (window.clipboardData) {
				var s = clipboardData.getData("text");
				if (!/\D/.test(s)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}).bind("dragenter.numberbox", function() {
			return false;
		}).bind("blur.numberbox", function() {
			fixValue(target, $(this).val());
			$(this).val(opts.formatter.call(target, getValue(target)));
		}).bind("focus.numberbox", function() {
			var vv = getValue(target);
			if ($(this).val() != vv) {
				$(this).val(vv);
			}
		});
	};
	function validate(target) {
		if ($.fn.validatebox) {
			var opts = $.data(target, "numberbox").options;
			$(target).validatebox(opts);
		}
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, "numberbox").options;
		if (disabled) {
			opts.disabled = true;
			$(target).attr("disabled", true);
		} else {
			opts.disabled = false;
			$(target).removeAttr("disabled");
		}
	};
	$.fn.numberbox = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.numberbox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.validatebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var nb = $.data(this, "numberbox");
			if (nb) {
				$.extend(nb.options, options);
			} else {
				nb = $.data(this, "numberbox", {
					options : $.extend( {}, $.fn.numberbox.defaults,
							$.fn.numberbox.parseOptions(this), options),
					field : hiddenField(this)
				});
				$(this).removeAttr("disabled");
				$(this).css( {
					imeMode : "disabled"
				});
			}
			setDisabled(this, nb.options.disabled);
			bindEvents(this);
			validate(this);
			initValue(this);
		});
	};
	$.fn.numberbox.methods = {
		options : function(jq) {
			return $.data(jq[0], "numberbox").options;
		},
		destroy : function(jq) {
			return jq.each(function() {
				$.data(this, "numberbox").field.remove();
				$(this).validatebox("destroy");
				$(this).remove();
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				setDisabled(this, true);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
			});
		},
		fix : function(jq) {
			return jq.each(function() {
				fixValue(this, $(this).val());
			});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				fixValue(this, value);
			});
		},
		getValue : function(jq) {
			return getValue(jq[0]);
		},
		clear : function(jq) {
			return jq.each(function() {
				var nb = $.data(this, "numberbox");
				nb.field.val("");
				$(this).val("");
			});
		}
	};
	$.fn.numberbox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.validatebox.parseOptions(target), {
			disabled : (t.attr("disabled") ? true : undefined),
			value : (t.val() || undefined),
			min : (t.attr("min") == "0" ? 0
					: parseFloat(t.attr("min")) || undefined),
			max : (t.attr("max") == "0" ? 0
					: parseFloat(t.attr("max")) || undefined),
			precision : (parseInt(t.attr("precision")) || undefined),
			decimalSeparator : (t.attr("decimalSeparator") ? t
					.attr("decimalSeparator") : undefined),
			groupSeparator : (t.attr("groupSeparator") ? t
					.attr("groupSeparator") : undefined),
			prefix : (t.attr("prefix") ? t.attr("prefix") : undefined),
			suffix : (t.attr("suffix") ? t.attr("suffix") : undefined)
		});
	};
	$.fn.numberbox.defaults = $.extend( {}, $.fn.validatebox.defaults,{
		disabled : false,
		value : "",
		min : null,
		max : null,
		precision : 0,
		decimalSeparator : ".",
		groupSeparator : "",
		prefix : "",
		suffix : "",
		formatter : function(param) {
			if (!param) {
				return param;
			}
			param = param + "";
			var opts = $(this).numberbox("options");
			var intNum = param, decNum = "";
			var pointIndex = param.indexOf(".");
			if (pointIndex >= 0) {
				intNum = param.substring(0, pointIndex);
				decNum = param.substring(pointIndex + 1, param.length);
			}
			if (opts.groupSeparator) {
				var p = /(\d+)(\d{3})/;
				while (p.test(intNum)) {
					intNum = intNum.replace(p, "$1" + opts.groupSeparator + "$2");
				}
			}
			if (decNum) {
				return opts.prefix + intNum + opts.decimalSeparator + decNum + opts.suffix;
			} else {
				return opts.prefix + intNum + opts.suffix;
			}
		},
		parser : function(s) {
			s = s + "";
			var opts = $(this).numberbox("options");
			if (opts.groupSeparator) {
				s = s.replace(new RegExp("\\"
						+ opts.groupSeparator, "g"), "");
			}
			if (opts.decimalSeparator) {
				s = s.replace(new RegExp("\\"
						+ opts.decimalSeparator, "g"), ".");
			}
			if (opts.prefix) {
				s = s.replace(new RegExp("\\"
						+ $.trim(opts.prefix), "g"), "");
			}
			if (opts.suffix) {
				s = s.replace(new RegExp("\\"
						+ $.trim(opts.suffix), "g"), "");
			}
			s = s.replace(/\s/g, "");
			var val = parseFloat(s).toFixed(opts.precision);
			if (isNaN(val)) {
				val = "";
			} else {
				if (typeof (opts.min) == "number"
						&& val < opts.min) {
					val = opts.min.toFixed(opts.precision);
				} else {
					if (typeof (opts.max) == "number"
							&& val > opts.max) {
						val = opts.max.toFixed(opts.precision);
					}
				}
			}
			return val;
		},
		onChange : function(newValue, oldValue) {
		}
	});
})(jQuery);
