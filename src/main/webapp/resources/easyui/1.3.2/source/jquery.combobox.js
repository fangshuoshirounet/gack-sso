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
	 * 设置下拉面板滚动条位置
	 * @param {Object} jq
	 * @param {Object} value
	 */
	function scrollTo(jq, value) {
		var panel = $(jq).combo("panel");
		var item = panel.find("div.combobox-item[value=" + value + "]");
		if (item.length) {
			if (item.position().top <= 0) {
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else {
				if (item.position().top + item.outerHeight() > panel.height()) {
					var h = panel.scrollTop() + item.position().top
							+ item.outerHeight() - panel.height();
					panel.scrollTop(h);
				}
			}
		}
	};
	/**
	 * 小键盘向上选择操作
	 * @param {Object} jq
	 */
	function selectPrev(jq) {
		var panel = $(jq).combo("panel");
		var values = $(jq).combo("getValues");//获取combo值
		//获取到combo值在下拉面板panel里面对应的item项的前一选项
		var item = panel.find("div.combobox-item[value=" + values.pop() + "]");
		if (item.length) {
			var prevItem = item.prev(":visible");//获取前一项
			if (prevItem.length) {
				item = prevItem;//若有前一项，则item指向前一个选项
			}
		} else {
			item = panel.find("div.combobox-item:visible:last");//若不存在前一项，则直接指向最后一个选项
		}
		var value = item.attr("value");//获取上一选项的值
		selectByValue(jq, value);
		scrollTo(jq, value);
	};
	/**
	 * 小键盘向下选择操作
	 * @param {Object} jq
	 */
	function selectNext(jq) {
		var panel = $(jq).combo("panel");
		var values = $(jq).combo("getValues");
		//获取到combo值在下拉面板panel里面对应的item项的下一选项
		var item = panel.find("div.combobox-item[value=" + values.pop() + "]");
		if (item.length) {
			var nextItem = item.next(":visible");//获取下一项
			if (nextItem.length) {
				item = nextItem;//若有下一项，则item指向下一个选项
			}
		} else {
			item = panel.find("div.combobox-item:visible:first");//若不存在下一项，则直接指向第一个选项
		}
		var value = item.attr("value");
		selectByValue(jq, value);
		scrollTo(jq, value);
	};
	/**
	 * 选中指定值对应的选项
	 * @param {Object} jq
	 * @param {Object} value
	 * @return {TypeName} 
	 */
	function selectByValue(jq, value) {
		var options = $.data(jq, "combobox").options;
		var data = $.data(jq, "combobox").data;
		//重新设置combobox值（单选/多选）
		if (options.multiple) {
			var values = $(jq).combo("getValues");//获取当前值
			for ( var i = 0; i < values.length; i++) {
				if (values[i] == value) {
					return;//若指定值已经在当前值中，则不作任何处理
				}
			}
			values.push(value);//若指定值不在当前值中，则将指定值添加到combobox值数组中
			setValues(jq, values);
		} else {
			setValues(jq, [ value ]);
		}
		for ( var i = 0; i < data.length; i++) {
			if (data[i][options.valueField] == value) {
				options.onSelect.call(jq, data[i]);
				return;
			}
		}
	};
	/**
	 * 取消选择指定值的选项
	 * @param {Object} jq
	 * @param {Object} value
	 * @return {TypeName} 
	 */
	function unselectByValue(jq, value) {
		var options = $.data(jq, "combobox").options;
		var data = $.data(jq, "combobox").data;
		var values = $(jq).combo("getValues");
		for ( var i = 0; i < values.length; i++) {
			if (values[i] == value) {
				values.splice(i, 1);
				setValues(jq, values);
				break;
			}
		}
		for ( var i = 0; i < data.length; i++) {
			if (data[i][options.valueField] == value) {
				options.onUnselect.call(jq, data[i]);
				return;
			}
		}
	};
	/**
	 * 设置combobox的值（数组）
	 * @param {Object} jq
	 * @param {Object} values
	 * @param {Object} single 是否单选（这个参数感觉没完全吃透）
	 */
	function setValues(jq, values, single) {
		var options = $.data(jq, "combobox").options;
		var data = $.data(jq, "combobox").data;//获取combobox的数据
		var panel = $(jq).combo("panel");
		panel.find("div.combobox-item-selected").removeClass(
				"combobox-item-selected");//去掉当前值选项的选中样式
		var vv = [], ss = [];
		for ( var i = 0; i < values.length; i++) {
			var v = values[i];
			var s = v;
			for ( var j = 0; j < data.length; j++) {
				if (data[j][options.valueField] == v) {
					s = data[j][options.textField];
					break;
				}
			}
			vv.push(v);
			ss.push(s);
			panel.find("div.combobox-item[value=" + v + "]").addClass(
					"combobox-item-selected");
		}
		$(jq).combo("setValues", vv);
		if (!single) {
			$(jq).combo("setText", ss.join(options.separator));//多选，使用分隔符
		}
	};
	function transformData(jq) {
		var options = $.data(jq, "combobox").options;
		var values = [];
		$(">option", jq).each(
			function() {
				var value = {};
				value[options.valueField] = $(this).attr("value") != undefined ? $(this).attr("value") : $(this).html();
				value[options.textField] = $(this).html();
				value["selected"] = $(this).attr("selected");
				//value["attributes"] = $(this).attr("attributes");//扩展
				values.push(value);
			});
		return values;
	};
	/**
	 * 加载数据
	 * @param {Object} jq
	 * @param {Object} data
	 * @param {Object} single 是否单选（这个参数感觉没完全吃透）
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function loadData(jq, data, single) {
		var options = $.data(jq, "combobox").options;
		var panel = $(jq).combo("panel");
		$.data(jq, "combobox").data = data;//将数据赋给combobox
		var values = $(jq).combobox("getValues");
		panel.empty();//清空下拉面板所有选项
		//循环数据，给下拉面板添加选项
		for ( var i = 0; i < data.length; i++) {
			var v = data[i][options.valueField];
			var s = data[i][options.textField];
			//var attributes = data[i]["attributes"];//扩展
			var item = $("<div class=\"combobox-item\"></div>").appendTo(panel);//添加选项
			item.attr("value", v);//选项赋值
			if (options.formatter) {
				item.html(options.formatter.call(jq, data[i]));//formatter处理
			} else {
				item.html(s);
			}
			//若选项定义为默认选中
			if (data[i]["selected"]) {
				(function() {
					for ( var i = 0; i < values.length; i++) {
						if (v == values[i]) {
							return;
						}
					}
					values.push(v);//讲默认选中的值加入到combobox值数组values中
				})();
			}
		}
		//设置默认选中值数组values（单选/多选）
		if (options.multiple) {
			setValues(jq, values, single);
		} else {
			if (values.length) {
				setValues(jq, [ values[values.length - 1] ], single);
			} else {
				setValues(jq, [], single);
			}
		}
		options.onLoadSuccess.call(jq, data);//触发onLoadSuccess事件
		//给下拉面板选项注册hover、click事件
		$(".combobox-item", panel).hover(function() {
			$(this).addClass("combobox-item-hover");
		}, function() {
			$(this).removeClass("combobox-item-hover");
		}).click(function() {
			var selectItem = $(this);//单击选中的选项
			if (options.multiple) {
				if (selectItem.hasClass("combobox-item-selected")) {
					unselectByValue(jq, selectItem.attr("value"));
				} else {
					selectByValue(jq, selectItem.attr("value"));
				}
			} else {
				selectByValue(jq, selectItem.attr("value"));
				$(jq).combo("hidePanel");//单选时，选中一次就隐藏下拉面板
			}
		});
	};
	/**
	 * 重新加载数据
	 * @param {Object} jq
	 * @param {Object} url
	 * @param {Object} paramData 参数
	 * @param {Object} single 是否单选（这个参数感觉没完全吃透）
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function reloadData(jq, url, paramData, single) {
		var options = $.data(jq, "combobox").options;
		if (url) {
			options.url = url;
		}
		if (!options.url) {
			return;
		}
		paramData = paramData || {};
		$.ajax( {
			type : options.method,
			url : options.url,
			dataType : "json",
			data : paramData,
			success : function(json) {
				loadData(jq, json, single);
			},
			error : function() {
				options.onLoadError.apply(this, arguments);
			}
		});
	};
	/**
	 * 输入值匹配
	 * @param {Object} jq
	 * @param {Object} q 用户输入的值
	 */
	function doQuery(jq, q) {
		var options = $.data(jq, "combobox").options;
		if (options.multiple && !q) {
			setValues(jq, [], true);
		} else {
			setValues(jq, [ q ], true);
		}
		if (options.mode == "remote") {
			reloadData(jq, null, {
				q : q
			}, true);
		} else {
			var panel = $(jq).combo("panel");
			panel.find("div.combobox-item").hide();
			var data = $.data(jq, "combobox").data;//获取combobox的数据
			for ( var i = 0; i < data.length; i++) {
				if (options.filter.call(jq, q, data[i])) {
					var v = data[i][options.valueField];
					var s = data[i][options.textField];
					var item = panel.find("div.combobox-item[value=" + v + "]");
					item.show();
					if (s == q) {
						setValues(jq, [ v ], true);
						item.addClass("combobox-item-selected");//选项设置为选中样式
					}
				}
			}
		}
	};
	function create(jq) {
		var options = $.data(jq, "combobox").options;
		$(jq).addClass("combobox-f");
		$(jq).combo($.extend( {}, options, {
			onShowPanel : function() {
				$(jq).combo("panel").find("div.combobox-item").show();
				scrollTo(jq, $(jq).combobox("getValue"));
				options.onShowPanel.call(jq);//响应onShowPanel事件
			}
		}));
	};
	/**
	 * 实例化combobox或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.combobox = function(options, param) {
		if (typeof options == "string") {
			var fn = $.fn.combobox.methods[options];
			if (fn) {
				return fn(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var data = $.data(this, "combobox");
			if (data) {
				$.extend(data.options, options);
				create(this);
			} else {
				data = $.data(this, "combobox", {
					options : $.extend( {}, $.fn.combobox.defaults,
							$.fn.combobox.parseOptions(this), options)
				});
				create(this);
				loadData(this, transformData(this));
			}
			if (data.options.data) {
				loadData(this, data.options.data);
			}
			reloadData(this);
		});
	};
	/**
	 * 方法注册
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	$.fn.combobox.methods = {
		options : function(jq) {
			return $.data(jq[0], "combobox").options;
		},
		getData : function(jq) {
			return $.data(jq[0], "combobox").data;
		},
		setValues : function(jq, values) {
			return jq.each(function() {
				setValues(this, values);
			});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
				setValues(this, [ value ]);
			});
		},
		clear : function(jq) {
			return jq.each(function() {
				$(this).combo("clear");//清除值
				var panel = $(this).combo("panel");
				panel.find("div.combobox-item-selected").removeClass(
						"combobox-item-selected");//去掉选中样式
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				loadData(this, data);
			});
		},
		reload : function(jq, url) {
			return jq.each(function() {
				reloadData(this, url);
			});
		},
		select : function(jq, value) {
			return jq.each(function() {
				selectByValue(this, value);
			});
		},
		unselect : function(jq, value) {
			return jq.each(function() {
				unselectByValue(this, value);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.combobox.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.combo.parseOptions(target), {
			valueField : t.attr("valueField"),
			textField : t.attr("textField"),
			mode : t.attr("mode"),
			method : (t.attr("method") ? t.attr("method") : undefined),
			url : t.attr("url")
		});
	};
	/**
	 * 默认参数设置
	 * @memberOf {TypeName} 
	 */
	$.fn.combobox.defaults = $.extend( {}, $.fn.combo.defaults, {
		valueField : "value",
		textField : "text",
		mode : "local",//加载值的方式（local:本地;remote:服务）
		method : "post",
		url : null,
		data : null,
		keyHandler : {
			up : function() {
				selectPrev(this);//小键盘向上选择
			},
			down : function() {
				selectNext(this);//小键盘向下选择
			},
			enter : function() {
				var values = $(this).combobox("getValues");
				$(this).combobox("setValues", values);
				$(this).combobox("hidePanel");
			},
			query : function(q) {
				doQuery(this, q);//输入值匹配
			}
		},
		//过滤器
		filter : function(q, row) {//q:用户输入的值
			var options = $(this).combobox("options");
			return row[options.textField].indexOf(q) == 0;
		},
		formatter : function(row) {
			var options = $(this).combobox("options");
			return row[options.textField];
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onSelect : function(record) {
		},
		onUnselect : function(record) {
		}
	});
})(jQuery);
