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
	function submitForm(target, options) {
		options = options || {};
		if (options.onSubmit) {
			if (options.onSubmit.call(target) == false) {
				return;
			}
		}
		var form = $(target);
		if (options.url) {
			form.attr("action", options.url);
		}
		var frameId = "easyui_frame_" + (new Date().getTime());
		var frame = $("<iframe id=" + frameId + " name=" + frameId + "></iframe>").attr(
				"src",
				window.ActiveXObject ? "javascript:false" : "about:blank").css(
				{
					position : "absolute",
					top : -1000,
					left : -1000
				});
		var t = form.attr("target"), a = form.attr("action");
		form.attr("target", frameId);
		try {
			frame.appendTo("body");
			frame.bind("load", cb);
			form[0].submit();
		} finally {
			form.attr("action", a);
			t ? form.attr("target", t) : form.removeAttr("target");
		}
		var checkCount = 10;
		function cb() {
			frame.unbind();
			var body = $("#" + frameId).contents().find("body");
			var data = body.html();
			if (data == "") {
				if (--checkCount) {
					setTimeout(cb, 100);
					return;
				}
				return;
			}
			var ta = body.find(">textarea");
			if (ta.length) {
				data = ta.val();
			} else {
				var pre = body.find(">pre");
				if (pre.length) {
					data = pre.html();
				}
			}
			if (options.success) {
				options.success(data);
			}
			setTimeout(function() {
				frame.unbind();
				frame.remove();
			}, 100);
		};
	};
	function load(target, data) {
		if (!$.data(target, "form")) {
			$.data(target, "form", {
				options : $.extend( {}, $.fn.form.defaults)
			});
		}
		var opts = $.data(target, "form").options;
		if (typeof data == "string") {
			var param = {};
			if (opts.onBeforeLoad.call(target, param) == false) {
				return;
			}
			$.ajax( {
				url : data,
				data : param,
				dataType : "json",
				success : function(data) {
					_load(data);
				},
				error : function() {
					opts.onLoadError.apply(target, arguments);
				}
			});
		} else {
			_load(data);
		}
		function _load(data) {
			var form = $(target);
			for ( var name in data) {
				var val = data[name];
				var rr = setChecked(name, val);
				if (!rr.length) {
					var f = form.find("input[numberboxName=\"" + name + "\"]");
					if (f.length) {
						f.numberbox("setValue", val);
					} else {
						$("input[name=\"" + name + "\"]", form).val(val);
						$("textarea[name=\"" + name + "\"]", form).val(val);
						$("select[name=\"" + name + "\"]", form).val(val);
					}
				}
				setValue(name, val);
			}
			opts.onLoadSuccess.call(target, data);
			validate(target);
		};
		function setChecked(name, val) {
			var form = $(target);
			var rr = $("input[name=\"" + name + "\"][type=radio], input[name=\""
					+ name + "\"][type=checkbox]", form);
			$.fn.prop ? rr.prop("checked", false) : rr.attr("checked", false);
			rr.each(function() {
				var f = $(this);
				if (f.val() == val) {
					$.fn.prop ? f.prop("checked", true) : f.attr("checked",true);
				}
			});
			return rr;
		};
		function setValue(name, val) {
			var form = $(target);
			var cc = [ "combobox", "combotree", "combogrid", "datetimebox",
					"datebox", "combo" ];
			var c = form.find("[comboName=\"" + name + "\"]");
			if (c.length) {
				for ( var i = 0; i < cc.length; i++) {
					var type = cc[i];
					if (c.hasClass(type + "-f")) {
						if (c[type]("options").multiple) {
							c[type]("setValues", val);
						} else {
							c[type]("setValue", val);
						}
						return;
					}
				}
			}
		};
	};
	function clear(target) {
		$("input,select,textarea", target).each(function() {
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == "text" || t == "hidden" || t == "password"
					|| tag == "textarea") {
				this.value = "";
			} else {
				if (t == "file") {
					var node = $(this);
					node.after(node.clone().val(""));
					node.remove();
				} else {
					if (t == "checkbox" || t == "radio") {
						this.checked = false;
					} else {
						if (tag == "select") {
							this.selectedIndex = -1;
						}
					}
				}
			}
		});
		if ($.fn.combo) {
			$(".combo-f", target).combo("clear");
		}
		if ($.fn.combobox) {
			$(".combobox-f", target).combobox("clear");
		}
		if ($.fn.combotree) {
			$(".combotree-f", target).combotree("clear");
		}
		if ($.fn.combogrid) {
			$(".combogrid-f", target).combogrid("clear");
		}
		validate(target);
	};
	function setForm(target) {
		var opts = $.data(target, "form").options;
		var form = $(target);
		form.unbind(".form").bind("submit.form", function() {
			setTimeout(function() {
				submitForm(target, opts);
			}, 0);
			return false;
		});
	};
	function validate(target) {
		if ($.fn.validatebox) {
			var box = $(".validatebox-text", target);
			if (box.length) {
				box.validatebox("validate");
				box.trigger("focus");
				box.trigger("blur");
				var valid = $(".validatebox-invalid:first", target).focus();
				return valid.length == 0;
			}
		}
		return true;
	};
	$.fn.form = function(options, param) {
		if (typeof options == "string") {
			return $.fn.form.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			if (!$.data(this, "form")) {
				$.data(this, "form", {
					options : $.extend( {}, $.fn.form.defaults, options)
				});
			}
			setForm(this);
		});
	};
	$.fn.form.methods = {
		submit : function(jq, param) {
			return jq.each(function() {
				submitForm(this, $.extend( {}, $.fn.form.defaults, param || {}));
			});
		},
		load : function(jq, data) {
			return jq.each(function() {
				load(this, data);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				clear(this);
			});
		},
		validate : function(jq) {
			return validate(jq[0]);
		}
	};
	$.fn.form.defaults = {
		url : null,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(data) {
		},
		onBeforeLoad : function(param) {
		},
		onLoadSuccess : function(data) {
		},
		onLoadError : function() {
		}
	};
})(jQuery);
