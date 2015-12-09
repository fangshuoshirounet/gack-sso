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
	 */
	function reSize(jq) {
		var options = $.data(jq, "calendar").options;
		var t = $(jq);
		//自适应
		if (options.fit == true) {
			var p = t.parent();
			options.width = p.width();
			options.height = p.height();
		}
		var header = t.find(".calendar-header");
		if ($.boxModel == true) {
			t.width(options.width - (t.outerWidth() - t.width()));
			t.height(options.height - (t.outerHeight() - t.height()));
		} else {
			t.width(options.width);
			t.height(options.height);
		}
		var body = t.find(".calendar-body");
		//设置高度
		var height = t.height() - header.outerHeight();
		if ($.boxModel == true) {
			body.height(height - (body.outerHeight() - body.height()));
		} else {
			body.height(height);
		}
	};
	/**
	 * 包裹日历
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function wrapCalendar(jq) {
		//wrapInner()方法使用指定的 HTML 内容或元素，来包裹每个被选元素中的所有内容 (inner HTML)。
		//详情参考http://www.w3school.com.cn/jquery/manipulation_wrapinner.asp
		$(jq).addClass("calendar").wrapInner(
				"<div class=\"calendar-header\">"
						+ "<div class=\"calendar-prevmonth\"></div>"
						+ "<div class=\"calendar-nextmonth\"></div>"
						+ "<div class=\"calendar-prevyear\"></div>"
						+ "<div class=\"calendar-nextyear\"></div>"
						+ "<div class=\"calendar-title\">"
						+ "<span>Aprial 2010</span>"
						+ "</div>"
						+ "</div>"
						+ "<div class=\"calendar-body\">"
						+ "<div class=\"calendar-menu\">"
						+ "<div class=\"calendar-menu-year-inner\">"
						+ "<span class=\"calendar-menu-prev\"></span>"
						+ "<span><input class=\"calendar-menu-year\" type=\"text\"></input></span>"
						+ "<span class=\"calendar-menu-next\"></span>"
						+ "</div>"
						+ "<div class=\"calendar-menu-month-inner\">"
						+ "</div>" + "</div>" + "</div>");
		//日历头部“年月”hover、click事件
		$(jq).find(".calendar-title span").hover(function() {
			$(this).addClass("calendar-menu-hover");
		}, function() {
			$(this).removeClass("calendar-menu-hover");
		}).click(function() {
			var menu = $(jq).find(".calendar-menu");
			//is()根据选择器、元素或 jQuery 对象来检测匹配元素集合，如果这些元素中至少有一个元素匹配给定的参数，则返回 true
			//详情参考http://www.w3school.com.cn/jquery/traversing_is.asp
			if (menu.is(":visible")) {
				menu.hide();
			} else {
				showMenu(jq);//显示年份和月份选择的菜单
			}
		});
		//日历头部四个按钮hover、click事件
		$(".calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear",jq).hover(function() {
			$(this).addClass("calendar-nav-hover");
		}, function() {
			$(this).removeClass("calendar-nav-hover");
		});
		$(jq).find(".calendar-nextmonth").click(function() {
			toMonth(jq, 1);//下月
		});
		$(jq).find(".calendar-prevmonth").click(function() {
			toMonth(jq, -1);//上月
		});
		$(jq).find(".calendar-nextyear").click(function() {
			toYear(jq, 1);//下年
		});
		$(jq).find(".calendar-prevyear").click(function() {
			toYear(jq, -1);//上年
		});
		$(jq).bind("_resize", function() {
			var options = $.data(jq, "calendar").options;
			if (options.fit == true) {
				reSize(jq);//调整大小
			}
			return false;
		});
	};
	/**
	 * 上一月、下一月选择按钮处理方法
	 * @param {Object} jq
	 * @param {Object} tag (1：表示下月；-1：表示上月)
	 */
	function toMonth(jq, tag) {
		var options = $.data(jq, "calendar").options;
		options.month += tag;//月份+1或-1
		if (options.month > 12) {
			options.year++;//大于12月，年份+1
			options.month = 1;//月份归1
		} else {
			if (options.month < 1) {
				options.year--;//小于1月，年份-1
				options.month = 12;//月份归12
			}
		}
		renderCalendar(jq);
		//日历头部月份更改
		var inner = $(jq).find(".calendar-menu-month-inner");
		inner.find("td.calendar-selected").removeClass("calendar-selected");
		inner.find("td:eq(" + (options.month - 1) + ")").addClass("calendar-selected");
	};
	/**
	 * 上一年、下一年选择按钮处理方法
	 * @param {Object} jq
	 * @param {Object} tag (1：表示下年；-1：表示上年)
	 */
	function toYear(jq, tag) {
		var options = $.data(jq, "calendar").options;
		options.year += tag;//年份+1或-1
		renderCalendar(jq);
		//日历头部年份更改
		var year = $(jq).find(".calendar-menu-year");
		year.val(options.year);
	};
	/**
	 * 显示年份和月份选择的菜单
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 */
	function showMenu(jq) {
		var options = $.data(jq, "calendar").options;
		$(jq).find(".calendar-menu").show();
		if ($(jq).find(".calendar-menu-month-inner").is(":empty")) {
			$(jq).find(".calendar-menu-month-inner").empty();
			//表格布局月份选择菜单
			var t = $("<table></table>").appendTo(
					$(jq).find(".calendar-menu-month-inner"));
			var idx = 0;
			for ( var i = 0; i < 3; i++) {
				var tr = $("<tr></tr>").appendTo(t);
				for ( var j = 0; j < 4; j++) {
					$("<td class=\"calendar-menu-month\"></td>").html(
							options.months[idx++]).attr("abbr", idx).appendTo(tr);
				}
			}
			//菜单内的上年、下年按钮hover事件
			$(jq).find(".calendar-menu-prev,.calendar-menu-next").hover(
					function() {
						$(this).addClass("calendar-menu-hover");
					}, function() {
						$(this).removeClass("calendar-menu-hover");
					});
			//下年按钮click事件
			$(jq).find(".calendar-menu-next").click(function() {
				var y = $(jq).find(".calendar-menu-year");
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val()) + 1);
				}
			});
			//上年按钮click事件
			$(jq).find(".calendar-menu-prev").click(function() {
				var y = $(jq).find(".calendar-menu-year");
				if (!isNaN(y.val())) {
					y.val(parseInt(y.val() - 1));
				}
			});
			//菜单年份输入框keypress事件
			$(jq).find(".calendar-menu-year").keypress(function(e) {
				//Enter键
				if (e.keyCode == 13) {
					hideMenu();
				}
			});
			//菜单月份hover、click事件
			$(jq).find(".calendar-menu-month").hover(function() {
				$(this).addClass("calendar-menu-hover");
			}, function() {
				$(this).removeClass("calendar-menu-hover");
			}).click(
					function() {
						var menu = $(jq).find(".calendar-menu");
						menu.find(".calendar-selected").removeClass(
								"calendar-selected");
						$(this).addClass("calendar-selected");
						hideMenu();
					});
		}
		/**
		 * 隐藏菜单
		 */
		function hideMenu() {
			var menu = $(jq).find(".calendar-menu");
			var year = menu.find(".calendar-menu-year").val();//获取菜单年份输入框值
			var month = menu.find(".calendar-selected").attr("abbr");//获取菜单选择的月份值
			if (!isNaN(year)) {
				options.year = parseInt(year);//更新年
				options.month = parseInt(month);//更新月
				renderCalendar(jq);
			}
			menu.hide();
		};
		var body = $(jq).find(".calendar-body");
		var menu = $(jq).find(".calendar-menu");
		var yearInner = menu.find(".calendar-menu-year-inner");
		var monthInner = menu.find(".calendar-menu-month-inner");
		yearInner.find("input").val(options.year).focus();//默认年份输入框获得焦点
		monthInner.find("td.calendar-selected").removeClass("calendar-selected");
		monthInner.find("td:eq(" + (options.month - 1) + ")")
				.addClass("calendar-selected");
		//设置菜单大小
		//当前页面中浏览器是否使用标准盒模型渲染页面,盒子模型是CSS的一个布局概念，详情参考http://www.w3school.com.cn/css/css_boxmodel.asp
		if ($.boxModel == true) {
			menu.width(body.outerWidth() - (menu.outerWidth() - menu.width()));
			menu.height(body.outerHeight() - (menu.outerHeight() - menu.height()));
			monthInner.height(menu.height() - (monthInner.outerHeight() - monthInner.height())
					- yearInner.outerHeight());
		} else {
			menu.width(body.outerWidth());
			menu.height(body.outerHeight());
			monthInner.height(menu.height() - yearInner.outerHeight());
		}
	};
	/**
	 * 根据年月获取日历所要显示的日期数组
	 * @param {Object} year
	 * @param {Object} month
	 * @return {TypeName} result
	 */
	function getDateArray(year, month) {
		var dateArray = [];//存放年、月、日的二维数组，格式：[[year,month,day],...,[year,month,day]]
		var days = new Date(year, month, 0).getDate();//获取月份的天数
		for ( var i = 1; i <= days; i++) {
			dateArray.push( [ year, month, i ]);
		}
		//dates是存放星期六-星期天的日期的二维数组
		//result是存放dates的数组
		//result格式：[[[year,month,day],...,[]],...,[]]
		var result = [], dates = [];
		while (dateArray.length > 0) {
			//shift()方法用于把数组的第一个元素从其中删除，并返回第一个元素的值。
			//详情参考http://www.w3school.com.cn/js/jsref_shift.asp
			var date = dateArray.shift();
			dates.push(date);
			if (new Date(date[0], date[1] - 1, date[2]).getDay() == 6) {
				result.push(dates);//若是星期天，则把dates放进result
				dates = [];//清空dates（注意：若最好一周不够7天，则dates不会被清空）
			}
		}
		//若最后一周不足7天，仍把dates放进result
		if (dates.length) {
			result.push(dates);
		}
		var firstWeek = result[0];//获取第一个星期
		//若不足7天，取前面日期补足
		if (firstWeek.length < 7) {
			while (firstWeek.length < 7) {
				var firstDate = firstWeek[0];//第一天
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - 1);
				firstWeek.unshift( [ date.getFullYear(), date.getMonth() + 1,
						date.getDate() ]);
			}
		} else {
			var firstDate = firstWeek[0];
			var dates = [];
			for ( var i = 1; i <= 7; i++) {
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - i);
				dates.unshift( [ date.getFullYear(), date.getMonth() + 1,
						date.getDate() ]);
			}
			//unshift()方法可向数组的开头添加一个或更多元素，并返回新的长度。
			//详情参考http://www.w3school.com.cn/js/jsref_unshift.asp
			result.unshift(dates);
		}
		var lastWeek = result[result.length - 1];//获取最后一个星期
		//若不足7天，取后面日期补足
		while (lastWeek.length < 7) {
			var lastDate = lastWeek[lastWeek.length - 1];//最后一天
			var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + 1);
			lastWeek.push( [ date.getFullYear(), date.getMonth() + 1, date.getDate() ]);
		}
		if (result.length < 6) {
			var lastDate = lastWeek[lastWeek.length - 1];
			var dates = [];
			for ( var i = 1; i <= 7; i++) {
				var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + i);
				dates.push( [ date.getFullYear(), date.getMonth() + 1,
						date.getDate() ]);
			}
			result.push(dates);
		}
		return result;
	};
	/**
	 * 渲染日历
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 */
	function renderCalendar(jq) {
		var options = $.data(jq, "calendar").options;
		$(jq).find(".calendar-title span").html(options.months[options.month - 1] + " " + options.year);//设置日历头部年月值
		var body = $(jq).find("div.calendar-body");
		body.find(">table").remove();//清空日历body
		var t = $("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><thead></thead><tbody></tbody></table>").prependTo(body);//table布局日历body
		var tr = $("<tr></tr>").appendTo(t.find("thead"));
		//创建“周”头部
		for ( var i = 0; i < options.weeks.length; i++) {
			tr.append("<th>" + options.weeks[i] + "</th>");
		}
		var dateArray = getDateArray(options.year, options.month);//根据年月获取需要显示的日期数组
		//循环日期数组，逐行逐列渲染日期
		for ( var i = 0; i < dateArray.length; i++) {
			var week = dateArray[i];
			var tr = $("<tr></tr>").appendTo(t.find("tbody"));
			for ( var j = 0; j < week.length; j++) {
				var date = week[j];
				$("<td class=\"calendar-day calendar-other-month\"></td>")
						.attr("abbr", date[0] + "," + date[1] + "," + date[2])
						.html(date[2]).appendTo(tr);
			}
		}
		//当月日期和非当月日期样式设置
		t.find("td[abbr^=\"" + options.year + "," + options.month + "\"]").removeClass(
				"calendar-other-month");
		var now = new Date();
		var dateStr = now.getFullYear() + "," + (now.getMonth() + 1) + ","
				+ now.getDate();
		t.find("td[abbr=\"" + dateStr + "\"]").addClass("calendar-today");
		if (options.current) {
			t.find(".calendar-selected").removeClass("calendar-selected");
			var currentDateStr = options.current.getFullYear() + ","
					+ (options.current.getMonth() + 1) + ","
					+ options.current.getDate();
			t.find("td[abbr=\"" + currentDateStr + "\"]").addClass("calendar-selected");
		}
		t.find("tr").find("td:first").addClass("calendar-sunday");//周日样式
		t.find("tr").find("td:last").addClass("calendar-saturday");//周六样式
		//日期单元格hover、click事件
		t.find("td").hover(function() {
			$(this).addClass("calendar-hover");
		}, function() {
			$(this).removeClass("calendar-hover");
		}).click(function() {
			t.find(".calendar-selected").removeClass("calendar-selected");
			$(this).addClass("calendar-selected");
			var selectDate = $(this).attr("abbr").split(",");
			options.current = new Date(selectDate[0], parseInt(selectDate[1]) - 1, selectDate[2]);
			options.onSelect.call(jq, options.current);//返回选择的日期给onSelect事件作为参数，并响应事件
		});
	};
	/**
	 * 日历实例化或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.calendar = function(options, menthod) {
		if (typeof options == "string") {
			return $.fn.calendar.methods[options](this, menthod);
		}
		options = options || {};
		return this.each(function() {
			var data = $.data(this, "calendar");
			if (data) {
				$.extend(data.options, options);
			} else {
				data = $.data(this, "calendar", {
					options : $.extend( {}, $.fn.calendar.defaults,
							$.fn.calendar.parseOptions(this), options)
				});
				wrapCalendar(this);
			}
			if (data.options.border == false) {
				$(this).addClass("calendar-noborder");
			}
			reSize(this);
			renderCalendar(this);
			$(this).find("div.calendar-menu").hide();
		});
	};
	/**
	 * 方法注册
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	$.fn.calendar.methods = {
		options : function(jq) {
			return $.data(jq[0], "calendar").options;
		},
		resize : function(jq) {
			return jq.each(function() {
				reSize(this);
			});
		},
		moveTo : function(jq, date) {
			return jq.each(function() {
				$(this).calendar( {
					year : date.getFullYear(),
					month : date.getMonth() + 1,
					current : date
				});
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.calendar.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr("fit") ? t.attr("fit") == "true" : undefined),
			border : (t.attr("border") ? t.attr("border") == "true" : undefined)
		};
	};
	/**
	 * 默认参数
	 */
	$.fn.calendar.defaults = {
		width : 180,
		height : 180,
		fit : false,
		border : true,
		weeks : [ "S", "M", "T", "W", "T", "F", "S" ],
		months : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug","Sep", "Oct", "Nov", "Dec" ],
		year : new Date().getFullYear(),
		month : new Date().getMonth() + 1,
		current : new Date(),
		onSelect : function(date) {
		}
	};
})(jQuery);
