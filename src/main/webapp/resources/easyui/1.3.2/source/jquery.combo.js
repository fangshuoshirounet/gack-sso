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
	 * @param {Object} width
	 */
	function reSize(jq, width) {
		var options = $.data(jq, "combo").options;
		var combo = $.data(jq, "combo").combo;
		var panel = $.data(jq, "combo").panel;
		if (width) {
			options.width = width;
		}
		combo.appendTo("body");
		if (isNaN(options.width)) {
			options.width = combo.find("input.combo-text").outerWidth();
		}
		var arrowWidth = 0;//下拉箭头宽度
		if (options.hasDownArrow) {
			arrowWidth = combo.find(".combo-arrow").outerWidth();
		}
		var width = options.width - arrowWidth;//text的宽度
		if ($.boxModel == true) {
			width -= combo.outerWidth() - combo.width();
		}
		combo.find("input.combo-text").width(width);
		panel.panel("resize", {
			width : (options.panelWidth ? options.panelWidth : combo.outerWidth()),
			height : options.panelHeight
		});
		combo.insertAfter(jq);
	};
	/**
	 * 是否显示下拉箭头
	 * @param {Object} jq
	 */
	function setDownArrow(jq) {
		var options = $.data(jq, "combo").options;
		var combo = $.data(jq, "combo").combo;
		if (options.hasDownArrow) {
			combo.find(".combo-arrow").show();
		} else {
			combo.find(".combo-arrow").hide();
		}
	};
	/**
	 * 渲染组件
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function renderCombo(jq) {
		$(jq).addClass("combo-f").hide();
		var combo = $("<span class=\"combo\"></span>").insertAfter(jq);
		var textBox = $("<input type=\"text\" class=\"combo-text\">").appendTo(combo);//将text框添加到combo
		$("<span><span class=\"combo-arrow\"></span></span>").appendTo(combo);//将下来箭头添加到combo
		$("<input type=\"hidden\" class=\"combo-value\">").appendTo(combo);//将隐藏域添加到combo以存放combo的value
		var panel = $("<div class=\"combo-panel\"></div>").appendTo("body");//将下来面板添加到body
		//设置下拉面板
		panel.panel( {
			doSize : false,
			closed : true,
			style : {
				position : "absolute",
				zIndex : 10
			},
			onOpen : function() {
				$(this).panel("resize");//打开时重置大小
			}
		});
		var name = $(jq).attr("name");
		if (name) {
			combo.find("input.combo-value").attr("name", name);
			$(jq).removeAttr("name").attr("comboName", name);
		}
		textBox.attr("autocomplete", "off");//关闭自动完成
		return {
			combo : combo,
			panel : panel
		};
	};
	/**
	 * 销毁Combo
	 * @param {Object} jq
	 */
	function destroyCombo(jq) {
		var textBox = $.data(jq, "combo").combo.find("input.combo-text");
		textBox.validatebox("destroy");
		$.data(jq, "combo").panel.panel("destroy");
		$.data(jq, "combo").combo.remove();
		$(jq).remove();
	};
	/**
	 * 绑定事件
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function bindEvents(jq) {
		var data = $.data(jq, "combo");
		var options = data.options;
		var combo = $.data(jq, "combo").combo;
		var panel = $.data(jq, "combo").panel;
		var textBox = combo.find(".combo-text");
		var arrowBox = combo.find(".combo-arrow");//下拉箭头
		$(document).unbind(".combo").bind("mousedown.combo", function(e) {
			$("div.combo-panel").panel("close");//鼠标点击combo外，关闭选择面板
		});
		//移除事件处理器
		combo.unbind(".combo");
		panel.unbind(".combo");
		textBox.unbind(".combo");
		arrowBox.unbind(".combo");
		//若组件未禁用，添加以下事件处理器
		if (!options.disabled) {
			panel.bind("mousedown.combo", function(e) {
				return false;
			});
			textBox.bind("mousedown.combo", function(e) {
				e.stopPropagation();//该方法将停止事件的传播，阻止它被分派到其他 Document节点，详情参考http://www.w3school.com.cn/xmldom/met_event_stoppropagation.asp
			}).bind("keydown.combo", function(e) {
				switch (e.keyCode) {
				case 38://小键盘上箭头
					options.keyHandler.up.call(jq);
					break;
				case 40://小键盘下箭头
					options.keyHandler.down.call(jq);
					break;
				case 13://Enter键
					e.preventDefault();
					options.keyHandler.enter.call(jq);
					return false;
				case 9://Tab键
				case 27://Esc键
					hidePanel(jq);
					break;
				default:
					if (options.editable) {
						if (data.timer) {
							clearTimeout(data.timer);
						}
						data.timer = setTimeout(function() {
							var q = textBox.val();
							if (data.previousValue != q) {
								data.previousValue = q;
								showPanel(jq);
								options.keyHandler.query.call(jq, textBox.val());
								validateValue(jq, true);
							}
						}, options.delay);
					}
				}
			});
			arrowBox.bind("click.combo", function() {
				if (panel.is(":visible")) {
					hidePanel(jq);
				} else {
					$("div.combo-panel").panel("close");
					showPanel(jq);
				}
				textBox.focus();
			}).bind("mouseenter.combo", function() {
				$(this).addClass("combo-arrow-hover");
			}).bind("mouseleave.combo", function() {
				$(this).removeClass("combo-arrow-hover");
			}).bind("mousedown.combo", function() {
				return false;
			});
		}
	};
	/**
	 * 显示下拉选择面板
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function showPanel(jq) {
		var options = $.data(jq, "combo").options;
		var combo = $.data(jq, "combo").combo;
		var panel = $.data(jq, "combo").panel;
		if ($.fn.window) {
			panel.panel("panel").css("z-index", $.fn.window.defaults.zIndex++);//若放在窗口里面，则显示在窗口之上
		}
		panel.panel("move", {
			left : combo.offset().left,
			top : getOffsetTop()
		});
		panel.panel("open");
		options.onShowPanel.call(jq);
		(function() {
			if (panel.is(":visible")) {
				panel.panel("move", {
					left : getOffsetLeft(),
					top : getOffsetTop()
				});
				setTimeout(arguments.callee, 200);
			}
		})();
		/**
		 * 获取Left位置
		 * @return {TypeName} 
		 */
		function getOffsetLeft() {
			var left = combo.offset().left;
			if (left + panel.outerWidth() > $(window).width()
					+ $(document).scrollLeft()) {
				left = $(window).width() + $(document).scrollLeft()
						- panel.outerWidth();
			}
			if (left < 0) {
				left = 0;
			}
			return left;
		};
		/**
		 * 获取TOP位置
		 * @return {TypeName} 
		 */
		function getOffsetTop() {
			var top = combo.offset().top + combo.outerHeight();
			if (top + panel.outerHeight() > $(window).height()
					+ $(document).scrollTop()) {
				top = combo.offset().top - panel.outerHeight();
			}
			if (top < $(document).scrollTop()) {
				top = combo.offset().top + combo.outerHeight();
			}
			return top;
		};
	};
	/**
	 * 隐藏下拉选择面板
	 * @param {Object} jq
	 */
	function hidePanel(jq) {
		var options = $.data(jq, "combo").options;
		var panel = $.data(jq, "combo").panel;
		panel.panel("close");
		options.onHidePanel.call(jq);
	};
	/**
	 * 校验值
	 * @param {Object} jq
	 * @param {Object} tag 是否校验（true:是,flase:否）
	 */
	function validateValue(jq, tag) {
		var options = $.data(jq, "combo").options;
		var textBox = $.data(jq, "combo").combo.find("input.combo-text");
		textBox.validatebox(options);
		if (tag) {
			textBox.validatebox("validate");
			textBox.trigger("mouseleave");//触发鼠标离开事件
		}
	};
	/**
	 * 设置禁用/启用组件样式
	 * @param {Object} jq
	 * @param {Object} disabled (true:禁用;flase:启用)
	 */
	function setDisabled(jq, disabled) {
		var options = $.data(jq, "combo").options;
		var combo = $.data(jq, "combo").combo;
		if (disabled) {
			options.disabled = true;
			$(jq).attr("disabled", true);
			combo.find(".combo-value").attr("disabled", true);
			combo.find(".combo-text").attr("disabled", true);
		} else {
			options.disabled = false;
			$(jq).removeAttr("disabled");
			combo.find(".combo-value").removeAttr("disabled");
			combo.find(".combo-text").removeAttr("disabled");
		}
	};
	/**
	 * 清空combo值
	 * @param {Object} jq
	 */
	function clearValue(jq) {
		var options = $.data(jq, "combo").options;
		var combo = $.data(jq, "combo").combo;
		if (options.multiple) {
			combo.find("input.combo-value").remove();
		} else {
			combo.find("input.combo-value").val("");
		}
		combo.find("input.combo-text").val("");
	};
	/**
	 * 获取text值
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function getText(jq) {
		var combo = $.data(jq, "combo").combo;
		return combo.find("input.combo-text").val();
	};
	/**
	 * 设置text值
	 * @param {Object} jq
	 * @param {Object} text
	 */
	function setText(jq, text) {
		var combo = $.data(jq, "combo").combo;
		combo.find("input.combo-text").val(text);
		validateValue(jq, true);
		$.data(jq, "combo").previousValue = text;
	};
	/**
	 * 获取值（多选）
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function getValues(jq) {
		var values = [];
		var combo = $.data(jq, "combo").combo;
		combo.find("input.combo-value").each(function() {
			values.push($(this).val());
		});
		return values;
	};
	/**
	 * 设置值（多选）
	 * @param {Object} jq
	 * @param {Object} values
	 */
	function setValues(jq, values) {
		var options = $.data(jq, "combo").options;
		var nowValues = getValues(jq);//获取当前值数组
		var combo = $.data(jq, "combo").combo;
		combo.find("input.combo-value").remove();//清空原来的值
		var name = $(jq).attr("comboName");
		for ( var i = 0; i < values.length; i++) {
			var v = $("<input type=\"hidden\" class=\"combo-value\">").appendTo(combo);
			if (name) {
				v.attr("name", name);
			}
			v.val(values[i]);
		}
		var tmp = [];
		for ( var i = 0; i < nowValues.length; i++) {
			tmp[i] = nowValues[i];
		}
		var aa = [];
		for ( var i = 0; i < values.length; i++) {
			for ( var j = 0; j < tmp.length; j++) {
				if (values[i] == tmp[j]) {
					aa.push(values[i]);
					tmp.splice(j, 1);//splice()方法用于插入、删除或替换数组的元素，详细参考http://www.w3school.com.cn/js/jsref_splice.asp
					break;
				}
			}
		}
		//若设置值数组与原值数组不相等，则将设置值数组、原值数组返回给onChange事件作为参数，并响应事件
		if (aa.length != values.length || values.length != nowValues.length) {
			if (options.multiple) {
				options.onChange.call(jq, values, nowValues);
			} else {
				options.onChange.call(jq, values[0], nowValues[0]);
			}
		}
	};
	/**
	 * 获取值（单选）
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function getValue(jq) {
		var values = getValues(jq);
		return values[0];
	};
	/**
	 * 设置值（单选）
	 * @param {Object} jq
	 * @param {Object} value
	 */
	function setValue(jq, value) {
		setValues(jq, [ value ]);
	};
	/**
	 * 初始化combo值
	 * @param {Object} jq
	 */
	function initValue(jq) {
		var options = $.data(jq, "combo").options;
		var fn = options.onChange;
		options.onChange = function() {
		};
		if (options.multiple) {
			if (options.value) {
				if (typeof options.value == "object") {
					setValues(jq, options.value);
				} else {
					setValue(jq, options.value);
				}
			} else {
				setValues(jq, []);
			}
		} else {
			setValue(jq, options.value);
		}
		options.onChange = fn;
	};
	/**
	 * 实例化组件
	 * @param {Object} options
	 * @param {Object} method
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.combo = function(options, method) {
		if (typeof options == "string") {
			return $.fn.combo.methods[options](this, method);
		}
		options = options || {};
		return this.each(function() {
			var data = $.data(this, "combo");
			if (data) {
				$.extend(data.options, options);
			} else {
				var r = renderCombo(this);
				data = $.data(this, "combo", {
					options : $.extend( {}, $.fn.combo.defaults, $.fn.combo.parseOptions(this), options),
					combo : r.combo,
					panel : r.panel,
					previousValue : null
				});
				$(this).removeAttr("disabled");
			}
			$("input.combo-text", data.combo).attr("readonly",!data.options.editable);//设置是否只读
			setDownArrow(this);//设置是否显示下拉箭头
			setDisabled(this, data.options.disabled);//设置是否禁用
			reSize(this);
			bindEvents(this);
			validateValue(this);
			initValue(this);//初始化combo值
		});
	};
	/**
	 * 方法注册
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	$.fn.combo.methods = {
		options : function(jq) {
			return $.data(jq[0], "combo").options;
		},
		panel : function(jq) {
			return $.data(jq[0], "combo").panel;
		},
		textbox : function(jq) {
			return $.data(jq[0], "combo").combo.find("input.combo-text");
		},
		destroy : function(jq) {
			return jq.each(function() {
				destroyCombo(this);
			});
		},
		resize : function(jq, width) {
			return jq.each(function() {
				reSize(this, width);
			});
		},
		showPanel : function(jq) {
			return jq.each(function() {
				showPanel(this);
			});
		},
		hidePanel : function(jq) {
			return jq.each(function() {
				hidePanel(this);
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				setDisabled(this, true);
				bindEvents(this);
			});
		},
		enable : function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
				bindEvents(this);
			});
		},
		validate : function(jq) {
			return jq.each(function() {
				validateValue(this, true);
			});
		},
		isValid : function(jq) {
			var textBox = $.data(jq[0], "combo").combo.find("input.combo-text");
			return textBox.validatebox("isValid");//是否有效
		},
		clear : function(jq) {
			return jq.each(function() {
				clearValue(this);
			});
		},
		getText : function(jq) {
			return getText(jq[0]);
		},
		setText : function(jq, text) {
			return jq.each(function() {
				setText(this, text);
			});
		},
		getValues : function(jq) {
			return getValues(jq[0]);
		},
		setValues : function(jq, values) {
			return jq.each(function() {
				setValues(this, values);
			});
		},
		getValue : function(jq) {
			return getValue(jq[0]);
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				setValue(this, value);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.combo.parseOptions = function(target) {
		var t = $(target);
		return $.extend({},$.fn.validatebox.parseOptions(target),{
				width : (parseInt(target.style.width) || undefined),
				panelWidth : (parseInt(t.attr("panelWidth")) || undefined),
				panelHeight : (t.attr("panelHeight") == "auto" ? "auto" : parseInt(t.attr("panelHeight")) || undefined),
				separator : (t.attr("separator") || undefined),
				multiple : (t.attr("multiple") ? (t.attr("multiple") == "true" || t.attr("multiple") == true || t.attr("multiple") == "multiple") : undefined),
				editable : (t.attr("editable") ? t.attr("editable") == "true" : undefined),
				disabled : (t.attr("disabled") ? true : undefined),
				hasDownArrow : (t.attr("hasDownArrow") ? t.attr("hasDownArrow") == "true" : undefined),
				value : (t.val() || undefined),
				delay : (t.attr("delay") ? parseInt(t.attr("delay")) : undefined)
			});
	};
	/**
	 * 默认参数
	 */
	$.fn.combo.defaults = $.extend( {}, $.fn.validatebox.defaults, {
		width : "auto",
		panelWidth : null,
		panelHeight : 200,
		multiple : false,//多选
		separator : ",",//多选值的分隔符号
		editable : true,//可编辑
		disabled : false,//是否禁用
		hasDownArrow : true,//是否显示下拉箭头
		value : "",
		delay : 200,
		keyHandler : {//支持小键盘上下方向键选择
			up : function() {
			},
			down : function() {
			},
			enter : function() {
			},
			query : function(q) {
			}
		},
		onShowPanel : function() {
		},
		onHidePanel : function() {
		},
		onChange : function(newValue, oldValue) {
		}
	});
})(jQuery);
