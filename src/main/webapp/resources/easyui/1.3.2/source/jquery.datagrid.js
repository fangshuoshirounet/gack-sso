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
	function getObjectIndex(rows, o) {
		for ( var i = 0, count = rows.length; i < count; i++) {
			if (rows[i] == o) {
				return i;
			}
		}
		return -1;
	};
	function unSelectedRowsById(rows, idField, id) {
		if (typeof o == "string") {
			for ( var i = 0, count = rows.length; i < count; i++) {
				if (rows[i][o] == id) {
					rows.splice(i, 1);
					return;
				}
			}
		} else {
			var index = getObjectIndex(rows, o);
			if (index != -1) {
				rows.splice(index, 1);
			}
		}
	};
	function setSize(jq, param) {
		var opts = $.data(jq, "datagrid").options;
		var panel = $.data(jq, "datagrid").panel;
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
		}
		if (opts.fit == true) {
			var p = panel.panel("panel").parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.panel("resize", {
			width : opts.width,
			height : opts.height
		});
	};
	function fitGridSize(jq) {
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		var panel = $.data(jq, "datagrid").panel;
		var width = panel.width();
		var height = panel.height();
		var gridView = dc.view;
		var gridView1 = dc.view1;
		var gridView2 = dc.view2;
		var gridHeader1 = gridView1.children("div.datagrid-header");
		var gridHeader2 = gridView2.children("div.datagrid-header");
		var gridTable1 = gridHeader1.find("table");
		var gridTable2 = gridHeader2.find("table");
		gridView.width(width);
		var innerHeader = gridHeader1.children("div.datagrid-header-inner").show();
		gridView1.width(innerHeader.find("table").width());
		if (!opts.showHeader) {
			innerHeader.hide();
		}
		gridView2.width(width - gridView1.outerWidth());
		gridView1.children(
				"div.datagrid-header,div.datagrid-body,div.datagrid-footer")
				.width(gridView1.width());
		gridView2.children(
				"div.datagrid-header,div.datagrid-body,div.datagrid-footer")
				.width(gridView2.width());
		var hh;
		gridHeader1.css("height", "");
		gridHeader2.css("height", "");
		gridTable1.css("height", "");
		gridTable2.css("height", "");
		hh = Math.max(gridTable1.height(), gridTable2.height());
		gridTable1.height(hh);
		gridTable2.height(hh);
		if ($.boxModel == true) {
			gridHeader1.height(hh - (gridHeader1.outerHeight() - gridHeader1.height()));
			gridHeader2.height(hh - (gridHeader2.outerHeight() - gridHeader2.height()));
		} else {
			gridHeader1.height(hh);
			gridHeader2.height(hh);
		}
		if (opts.height != "auto") {
			var fixedHeight = height
					- gridView2.children("div.datagrid-header").outerHeight(true)
					- gridView2.children("div.datagrid-footer").outerHeight(true)
					- panel.children("div.datagrid-toolbar").outerHeight(true)
					- panel.children("div.datagrid-pager").outerHeight(true);
			gridView1.children("div.datagrid-body").height(fixedHeight);
			gridView2.children("div.datagrid-body").height(fixedHeight);
		}
		gridView.height(gridView2.height());
		gridView2.css("left", gridView1.outerWidth());
	};
	function setMsgSize(jq) {
		var panel = $(jq).datagrid("getPanel");
		var mask = panel.children("div.datagrid-mask");
		if (mask.length) {
			mask.css( {
				width : panel.width(),
				height : panel.height()
			});
			var msg = panel.children("div.datagrid-mask-msg");
			msg.css( {
				left : (panel.width() - msg.outerWidth()) / 2,
				top : (panel.height() - msg.outerHeight()) / 2
			});
		}
	};
	function fixRowHeight(jq, rowIndex) {
		var rows = $.data(jq, "datagrid").data.rows;
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		if (!dc.body1.is(":empty")) {
			if (rowIndex >= 0) {
				alignRowHeight(rowIndex);
			} else {
				for ( var i = 0; i < rows.length; i++) {
					alignRowHeight(i);
				}
				if (opts.showFooter) {
					var footerRows = $(jq).datagrid("getFooterRows") || [];
					for ( var i = 0; i < footerRows.length; i++) {
						alignRowHeight(i, "footer");
					}
					fitGridSize(jq);
				}
			}
		}
		if (opts.height == "auto") {
			var gridBody1 = dc.body1.parent();
			var gridBody2 = dc.body2;
			var fullHeight = 0;
			var width = 0;
			gridBody2.children().each(function() {
				var c = $(this);
				if (c.is(":visible")) {
					fullHeight += c.outerHeight();
					if (width < c.outerWidth()) {
						width = c.outerWidth();
					}
				}
			});
			if (width > gridBody2.width()) {
				fullHeight += 18;
			}
			gridBody1.height(fullHeight);
			gridBody2.height(fullHeight);
			dc.view.height(dc.view2.height());
		}
		dc.body2.triggerHandler("scroll");
		function alignRowHeight(rowIndex, content) {
			content = content || "body";
			var tr1 = opts.finder.getTr(jq, rowIndex, content, 1);
			var tr2 = opts.finder.getTr(jq, rowIndex, content, 2);
			tr1.css("height", "");
			tr2.css("height", "");
			var height = Math.max(tr1.height(), tr2.height());
			tr1.css("height", height);
			tr2.css("height", height);
		};
	};
	function wrapGrid(jq, rownumbers) {
		function getColumns(thead) {
			var columns = [];
			$("tr", thead).each(function() {
				var cols = [];
				$("th", this).each(function() {
					var th = $(this);
					var col = {
						title : th.html(),
						align : th.attr("align") || "left",
						sortable : th.attr("sortable") == "true" || false,
						checkbox : th.attr("checkbox") == "true" || false
					};
					if (th.attr("field")) {
						col.field = th.attr("field");
					}
					if (th.attr("formatter")) {
						col.formatter = eval(th.attr("formatter"));
					}
					if (th.attr("styler")) {
						col.styler = eval(th.attr("styler"));
					}
					if (th.attr("editor")) {
						var s = $.trim(th.attr("editor"));
						if (s.substr(0, 1) == "{") {
							col.editor = eval("(" + s + ")");
						} else {
							col.editor = s;
						}
					}
					if (th.attr("rowspan")) {
						col.rowspan = parseInt(th.attr("rowspan"));
					}
					if (th.attr("colspan")) {
						col.colspan = parseInt(th.attr("colspan"));
					}
					if (th.attr("width")) {
						col.width = parseInt(th.attr("width")) || 100;
					}
					if (th.attr("hidden")) {
						col.hidden = true;
					}
					if (th.attr("resizable")) {
						col.resizable = th.attr("resizable") == "true";
					}
					cols.push(col);
				});
				columns.push(cols);
			});
			return columns;
		};
		var wrap = $(
				"<div class=\"datagrid-wrap\">"
						+ "<div class=\"datagrid-view\">"
						+ "<div class=\"datagrid-view1\">"
						+ "<div class=\"datagrid-header\">"
						+ "<div class=\"datagrid-header-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-body\">"
						+ "<div class=\"datagrid-body-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-footer\">"
						+ "<div class=\"datagrid-footer-inner\"></div>"
						+ "</div>" + "</div>"
						+ "<div class=\"datagrid-view2\">"
						+ "<div class=\"datagrid-header\">"
						+ "<div class=\"datagrid-header-inner\"></div>"
						+ "</div>" + "<div class=\"datagrid-body\"></div>"
						+ "<div class=\"datagrid-footer\">"
						+ "<div class=\"datagrid-footer-inner\"></div>"
						+ "</div>" + "</div>"
						+ "<div class=\"datagrid-resize-proxy\"></div>"
						+ "</div>" + "</div>").insertAfter(jq);
		wrap.panel( {
			doSize : false
		});
		wrap.panel("panel").addClass("datagrid").bind("_resize",
				function(e, param) {
					var opts = $.data(jq, "datagrid").options;
					if (opts.fit == true || param) {
						setSize(jq);
						setTimeout(function() {
							if ($.data(jq, "datagrid")) {
								fixColumnSize(jq);
							}
						}, 0);
					}
					return false;
				});
		$(jq).hide().appendTo(wrap.children("div.datagrid-view"));
		var frozenColumns = getColumns($("thead[frozen=true]", jq));
		var columns = getColumns($("thead[frozen!=true]", jq));
		var gridView = wrap.children("div.datagrid-view");
		var gridView1 = gridView.children("div.datagrid-view1");
		var gridView2 = gridView.children("div.datagrid-view2");
		return {
			panel : wrap,
			frozenColumns : frozenColumns,
			columns : columns,
			dc : {
				view : gridView,
				view1 : gridView1,
				view2 : gridView2,
				body1 : gridView1.children("div.datagrid-body").children(
						"div.datagrid-body-inner"),
				body2 : gridView2.children("div.datagrid-body"),
				footer1 : gridView1.children("div.datagrid-footer").children(
						"div.datagrid-footer-inner"),
				footer2 : gridView2.children("div.datagrid-footer").children(
						"div.datagrid-footer-inner")
			}
		};
	};
	function getGridData(jq) {
		var data = {
			total : 0,
			rows : []
		};
		var fields = getColumnFields(jq, true).concat(getColumnFields(jq, false));
		$(jq).find("tbody tr").each(function() {
			data.total++;
			var col = {};
			for ( var i = 0; i < fields.length; i++) {
				col[fields[i]] = $("td:eq(" + i + ")", this).html();
			}
			data.rows.push(col);
		});
		return data;
	};
	function init(jq) {
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		var panel = $.data(jq, "datagrid").panel;
		panel.panel($.extend( {}, opts, {
			doSize : false,
			onResize : function(width, height) {
				setMsgSize(jq);
				setTimeout(function() {
					if ($.data(jq, "datagrid")) {
						fitGridSize(jq);
						fitColumns(jq);
						opts.onResize.call(panel, width, height);
					}
				}, 0);
			},
			onExpand : function() {
				fitGridSize(jq);
				fixRowHeight(jq);
				opts.onExpand.call(panel);
			}
		}));
		var gridView1 = dc.view1;
		var gridView2 = dc.view2;
		var innerHeader1 = gridView1.children("div.datagrid-header").children(
				"div.datagrid-header-inner");
		var innerHeader2 = gridView2.children("div.datagrid-header").children(
				"div.datagrid-header-inner");
		buildGridHeader(innerHeader1, opts.frozenColumns, true);
		buildGridHeader(innerHeader2, opts.columns, false);
		innerHeader1.css("display", opts.showHeader ? "block" : "none");
		innerHeader2.css("display", opts.showHeader ? "block" : "none");
		gridView1.find("div.datagrid-footer-inner").css("display",
				opts.showFooter ? "block" : "none");
		gridView2.find("div.datagrid-footer-inner").css("display",
				opts.showFooter ? "block" : "none");
		if (opts.toolbar) {
			if (typeof opts.toolbar == "string") {
				$(opts.toolbar).addClass("datagrid-toolbar").prependTo(panel);
				$(opts.toolbar).show();
			} else {
				$("div.datagrid-toolbar", panel).remove();
				var tb = $("<div class=\"datagrid-toolbar\"></div>").prependTo(
						panel);
				for ( var i = 0; i < opts.toolbar.length; i++) {
					var btn = opts.toolbar[i];
					if (btn == "-") {
						$("<div class=\"datagrid-btn-separator\"></div>")
								.appendTo(tb);
					} else {
						var tool = $("<a href=\"javascript:void(0)\"></a>");
						tool[0].onclick = eval(btn.handler || function() {
						});
						tool.css("float", "left").appendTo(tb).linkbutton(
								$.extend( {}, btn, {
									plain : true
								}));
					}
				}
			}
		} else {
			$("div.datagrid-toolbar", panel).remove();
		}
		$("div.datagrid-pager", panel).remove();
		if (opts.pagination) {
			var pager = $("<div class=\"datagrid-pager\"></div>").appendTo(panel);
			pager.pagination( {
				pageNumber : opts.pageNumber,
				pageSize : opts.pageSize,
				pageList : opts.pageList,
				onSelectPage : function(pageNumber, pageSize) {
					opts.pageNumber = pageNumber;
					opts.pageSize = pageSize;
					request(jq);
				}
			});
			opts.pageSize = pager.pagination("options").pageSize;
		}
		function buildGridHeader(header, columns, frozen) {
			if (!columns) {
				return;
			}
			$(header).show();
			$(header).empty();
			var t = $("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tbody></tbody></table>").appendTo(header);
			for ( var i = 0; i < columns.length; i++) {
				var tr = $("<tr></tr>").appendTo($("tbody", t));
				var column = columns[i];
				for ( var j = 0; j < column.length; j++) {
					var col = column[j];
					var tdHTML = "";
					if (col.rowspan) {
						tdHTML += "rowspan=\"" + col.rowspan + "\" ";
					}
					if (col.colspan) {
						tdHTML += "colspan=\"" + col.colspan + "\" ";
					}
					var td = $("<td " + tdHTML + "></td>").appendTo(tr);
					if (col.checkbox) {
						td.attr("field", col.field);
						$("<div class=\"datagrid-header-check\"></div>").html(
								"<input type=\"checkbox\"/>").appendTo(td);
					} else {
						if (col.field) {
							td.attr("field", col.field);
							td.append("<div class=\"datagrid-cell\"><span></span><span class=\"datagrid-sort-icon\"></span></div>");
							$("span", td).html(col.title);
							$("span.datagrid-sort-icon", td).html("&nbsp;");
							var cell = td.find("div.datagrid-cell");
							if (col.resizable == false) {
								cell.attr("resizable", "false");
							}
							col.boxWidth = $.boxModel ? (col.width - (cell
									.outerWidth() - cell.width())) : col.width;
							cell.width(col.boxWidth);
							cell.css("text-align", (col.align || "left"));
						} else {
							$("<div class=\"datagrid-cell-group\"></div>")
									.html(col.title).appendTo(td);
						}
					}
					if (col.hidden) {
						td.hide();
					}
				}
			}
			if (frozen && opts.rownumbers) {
				var td = $("<td rowspan=\""
						+ opts.frozenColumns.length
						+ "\"><div class=\"datagrid-header-rownumber\"></div></td>");
				if ($("tr", t).length == 0) {
					td.wrap("<tr></tr>").parent().appendTo($("tbody", t));
				} else {
					td.prependTo($("tr:first", t));
				}
			}
		};
	};
	function resetGridEvent(jq) {
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		var tr = opts.finder.getTr(jq, "", "allbody");
		tr.unbind(".datagrid").bind("mouseenter.datagrid", function() {
			var rowIndex = $(this).attr("datagrid-row-index");
			opts.finder.getTr(jq, rowIndex).addClass("datagrid-row-over");
		}).bind("mouseleave.datagrid", function() {
			var rowIndex = $(this).attr("datagrid-row-index");
			opts.finder.getTr(jq, rowIndex).removeClass("datagrid-row-over");
		}).bind("click.datagrid", function() {
			var rowIndex = $(this).attr("datagrid-row-index");
			if (opts.singleSelect == true) {
				clearSelections(jq);
				selectRow(jq, rowIndex);
			} else {
				if ($(this).hasClass("datagrid-row-selected")) {
					unselectRow(jq, rowIndex);
				} else {
					selectRow(jq, rowIndex);
				}
			}
			if (opts.onClickRow) {
				opts.onClickRow.call(jq, rowIndex, data.rows[rowIndex]);
			}
		}).bind("dblclick.datagrid", function() {
			var rowIndex = $(this).attr("datagrid-row-index");
			if (opts.onDblClickRow) {
				opts.onDblClickRow.call(jq, rowIndex, data.rows[rowIndex]);
			}
		}).bind("contextmenu.datagrid", function(e) {
			var rowIndex = $(this).attr("datagrid-row-index");
			if (opts.onRowContextMenu) {
				opts.onRowContextMenu.call(jq, e, rowIndex, data.rows[rowIndex]);
			}
		});
		tr.find("td[field]").unbind(".datagrid").bind("click.datagrid",
				function() {
					var rowIndex = $(this).parent().attr("datagrid-row-index");
					var field = $(this).attr("field");
					var value = data.rows[rowIndex][field];
					opts.onClickCell.call(jq, rowIndex, field, value);
				}).bind("dblclick.datagrid", function() {
			var rowIndex = $(this).parent().attr("datagrid-row-index");
			var field = $(this).attr("field");
			var value = data.rows[rowIndex][field];
			opts.onDblClickCell.call(jq, rowIndex, field, value);
		});
		tr.find("div.datagrid-cell-check input[type=checkbox]").unbind(
				".datagrid").bind(
				"click.datagrid",
				function(e) {
					var rowIndex = $(this).parent().parent().parent().attr(
							"datagrid-row-index");
					if (opts.singleSelect) {
						clearSelections(jq);
						selectRow(jq, rowIndex);
					} else {
						if ($(this).is(":checked")) {
							selectRow(jq, rowIndex);
						} else {
							unselectRow(jq, rowIndex);
						}
					}
					e.stopPropagation();
				});
	};
	function setProperties(jq) {
		var panel = $.data(jq, "datagrid").panel;
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		var header = dc.view.find("div.datagrid-header");
		header.find("td:has(div.datagrid-cell)").unbind(".datagrid").bind(
				"mouseenter.datagrid", function() {
					$(this).addClass("datagrid-header-over");
				}).bind("mouseleave.datagrid", function() {
			$(this).removeClass("datagrid-header-over");
		}).bind("contextmenu.datagrid", function(e) {
			var field = $(this).attr("field");
			opts.onHeaderContextMenu.call(jq, e, field);
		});
		header.find("input[type=checkbox]").unbind(".datagrid").bind(
				"click.datagrid", function() {
					if (opts.singleSelect) {
						return false;
					}
					if ($(this).is(":checked")) {
						selectAll(jq);
					} else {
						unselectAll(jq);
					}
				});
		dc.body2.unbind(".datagrid").bind(
				"scroll.datagrid",
				function() {
					dc.view1.children("div.datagrid-body").scrollTop(
							$(this).scrollTop());
					dc.view2.children("div.datagrid-header").scrollLeft(
							$(this).scrollLeft());
					dc.view2.children("div.datagrid-footer").scrollLeft(
							$(this).scrollLeft());
				});
		function doSort(cell, sortable) {
			cell.unbind(".datagrid");
			if (!sortable) {
				return;
			}
			cell.bind("click.datagrid", function(e) {
				var field = $(this).parent().attr("field");
				var opt = getColumnOption(jq, field);
				if (!opt.sortable) {
					return;
				}
				opts.sortName = field;
				opts.sortOrder = "asc";
				var c = "datagrid-sort-asc";
				if ($(this).hasClass("datagrid-sort-asc")) {
					c = "datagrid-sort-desc";
					opts.sortOrder = "desc";
				}
				header.find("div.datagrid-cell").removeClass(
						"datagrid-sort-asc datagrid-sort-desc");
				$(this).addClass(c);
				if (opts.remoteSort) {
					request(jq);
				} else {
					var data = $.data(jq, "datagrid").data;
					renderGrid(jq, data);
				}
				if (opts.onSortColumn) {
					opts.onSortColumn.call(jq, opts.sortName, opts.sortOrder);
				}
			});
		};
		doSort(header.find("div.datagrid-cell"), true);
		header.find("div.datagrid-cell").each(function() {
			$(this).resizable({
				handles : "e",
				disabled : ($(this).attr("resizable") ? $(this).attr("resizable") == "false" : false),
				minWidth : 25,
				onStartResize : function(e) {
					header.css("cursor", "e-resize");
					dc.view.children("div.datagrid-resize-proxy").css({
						left : e.pageX - $(panel).offset().left - 1,
						display : "block"
					});
					doSort($(this), false);
				},
				onResize : function(e) {
					dc.view.children("div.datagrid-resize-proxy").css({
						display : "block",
						left : e.pageX - $(panel).offset().left - 1
					});
					return false;
				},
				onStopResize : function(e) {
					header.css("cursor", "");
					var field = $(this).parent().attr("field");
					var col = getColumnOption(jq, field);
					col.width = $(this).outerWidth();
					col.boxWidth = $.boxModel == true ? $(this)
							.width() : $(this).outerWidth();
					fixColumnSize(jq, field);
					fitColumns(jq);
					setTimeout(function() {
						doSort($(e.data.target), true);
					}, 0);
					dc.view2.children("div.datagrid-header")
							.scrollLeft(dc.body2.scrollLeft());
					dc.view.children(
							"div.datagrid-resize-proxy").css(
							"display", "none");
					opts.onResizeColumn
							.call(jq, field, col.width);
				}
			});
		});
		dc.view1.children("div.datagrid-header").find("div.datagrid-cell").resizable({
			onStopResize : function(e) {
				header.css("cursor", "");
				var field = $(this).parent().attr("field");
				var col = getColumnOption(jq, field);
				col.width = $(this).outerWidth();
				col.boxWidth = $.boxModel == true ? $(this)
						.width() : $(this).outerWidth();
				fixColumnSize(jq, field);
				dc.view2.children("div.datagrid-header")
						.scrollLeft(dc.body2.scrollLeft());
				dc.view.children("div.datagrid-resize-proxy")
						.css("display", "none");
				fitGridSize(jq);
				fitColumns(jq);
				setTimeout(function() {
					doSort($(e.data.target), true);
				}, 0);
				opts.onResizeColumn.call(jq, field, col.width);
			}
		});
	};
	function fitColumns(jq) {
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		if (!opts.fitColumns) {
			return;
		}
		var header = dc.view2.children("div.datagrid-header");
		var visableWidth = 0;
		var visableColumn;
		var fields = getColumnFields(jq, false);
		for ( var i = 0; i < fields.length; i++) {
			var col = getColumnOption(jq, fields[i]);
			if (!col.hidden && !col.checkbox) {
				visableWidth += col.width;
				visableColumn = col;
			}
		}
		var innerHeader = header.children("div.datagrid-header-inner").show();
		var fullWidth = header.width() - header.find("table").width() - opts.scrollbarSize;
		var rate = fullWidth / visableWidth;
		if (!opts.showHeader) {
			innerHeader.hide();
		}
		for ( var i = 0; i < fields.length; i++) {
			var col = getColumnOption(jq, fields[i]);
			if (!col.hidden && !col.checkbox) {
				var width = Math.floor(col.width * rate);
				fitColumnWidth(col, width);
				fullWidth -= width;
			}
		}
		fixColumnSize(jq);
		if (fullWidth) {
			fitColumnWidth(visableColumn, fullWidth);
			fixColumnSize(jq, visableColumn.field);
		}
		function fitColumnWidth(col, width) {
			col.width += width;
			col.boxWidth += width;
			header.find("td[field=\"" + col.field + "\"] div.datagrid-cell").width(col.boxWidth);
		};
	};
	function fixColumnSize(jq, cell) {
		var panel = $.data(jq, "datagrid").panel;
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		if (cell) {
			fix(cell);
		} else {
			var header = dc.view1.children("div.datagrid-header").add(
					dc.view2.children("div.datagrid-header"));
			header.find("td[field]").each(function() {
				fix($(this).attr("field"));
			});
		}
		fixMergedCellsSize(jq);
		setTimeout(function() {
			fixRowHeight(jq);
			fixEditorSize(jq);
		}, 0);
		function fix(cell) {
			var col = getColumnOption(jq, cell);
			var bf = opts.finder.getTr(jq, "", "allbody").add(
					opts.finder.getTr(jq, "", "allfooter"));
			bf.find("td[field=\"" + cell + "\"]").each(function() {
				var td = $(this);
				var colspan = td.attr("colspan") || 1;
				if (colspan == 1) {
					td.find("div.datagrid-cell").width(col.boxWidth);
					td.find("div.datagrid-editable").width(col.width);
				}
			});
		};
	};
	function fixMergedCellsSize(jq) {
		var panel = $.data(jq, "datagrid").panel;
		var dc = $.data(jq, "datagrid").dc;
		var header = dc.view1.children("div.datagrid-header").add(
				dc.view2.children("div.datagrid-header"));
		panel.find("div.datagrid-body td.datagrid-td-merged").each(function() {
			var td = $(this);
			var colspan = td.attr("colspan") || 1;
			var field = td.attr("field");
			var tdEl = header.find("td[field=\"" + field + "\"]");
			var width = tdEl.width();
			for ( var i = 1; i < colspan; i++) {
				tdEl = tdEl.next();
				width += tdEl.outerWidth();
			}
			var cell = td.children("div.datagrid-cell");
			if ($.boxModel == true) {
				cell.width(width - (cell.outerWidth() - cell.width()));
			} else {
				cell.width(width);
			}
		});
	};
	function fixEditorSize(jq) {
		var panel = $.data(jq, "datagrid").panel;
		panel.find("div.datagrid-editable").each(function() {
			var ed = $.data(this, "datagrid.editor");
			if (ed.actions.resize) {
				ed.actions.resize(ed.target, $(this).width());
			}
		});
	};
	function getColumnOption(jq, field) {
		var opts = $.data(jq, "datagrid").options;
		if (opts.columns) {
			for ( var i = 0; i < opts.columns.length; i++) {
				var column = opts.columns[i];
				for ( var j = 0; j < column.length; j++) {
					var col = column[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		if (opts.frozenColumns) {
			for ( var i = 0; i < opts.frozenColumns.length; i++) {
				var column = opts.frozenColumns[i];
				for ( var j = 0; j < column.length; j++) {
					var col = column[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		return null;
	};
	function getColumnFields(jq, frozen) {
		var opts = $.data(jq, "datagrid").options;
		var columns = (frozen == true) ? (opts.frozenColumns || [ [] ]) : opts.columns;
		if (columns.length == 0) {
			return [];
		}
		var fields = [];
		function getFixedColspan(index) {
			var c = 0;
			var i = 0;
			while (true) {
				if (fields[i] == undefined) {
					if (c == index) {
						return i;
					}
					c++;
				}
				i++;
			}
		};
		function findColumnFields(r) {
			var ff = [];
			var c = 0;
			for ( var i = 0; i < columns[r].length; i++) {
				var col = columns[r][i];
				if (col.field) {
					ff.push( [ c, col.field ]);
				}
				c += parseInt(col.colspan || "1");
			}
			for ( var i = 0; i < ff.length; i++) {
				ff[i][0] = getFixedColspan(ff[i][0]);
			}
			for ( var i = 0; i < ff.length; i++) {
				var f = ff[i];
				fields[f[0]] = f[1];
			}
		};
		for ( var i = 0; i < columns.length; i++) {
			findColumnFields(i);
		}
		return fields;
	};
	function renderGrid(jq, data) {
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		var panel = $.data(jq, "datagrid").panel;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		data = opts.loadFilter.call(jq, data);
		var rows = data.rows;
		$.data(jq, "datagrid").data = data;
		if (data.footer) {
			$.data(jq, "datagrid").footer = data.footer;
		}
		if (!opts.remoteSort) {
			var opt = getColumnOption(jq, opts.sortName);
			if (opt) {
				var sorter = opt.sorter || function(a, b) {
					return (a > b ? 1 : -1);
				};
				data.rows.sort(function(r1, r2) {
					return sorter(r1[opts.sortName], r2[opts.sortName])
							* (opts.sortOrder == "asc" ? 1 : -1);
				});
			}
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, jq, rows);
		}
		opts.view.render.call(opts.view, jq, dc.body2, false);
		opts.view.render.call(opts.view, jq, dc.body1, true);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, jq, dc.footer2, false);
			opts.view.renderFooter.call(opts.view, jq, dc.footer1, true);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, jq);
		}
		opts.onLoadSuccess.call(jq, data);
		var pager = panel.children("div.datagrid-pager");
		if (pager.length) {
			if (pager.pagination("options").total != data.total) {
				pager.pagination( {
					total : data.total
				});
			}
		}
		fixRowHeight(jq);
		resetGridEvent(jq);
		dc.body2.triggerHandler("scroll");
		if (opts.idField) {
			for ( var i = 0; i < rows.length; i++) {
				if (isSelected(rows[i])) {
					selectRecord(jq, rows[i][opts.idField]);
				}
			}
		}
		function isSelected(row) {
			for ( var i = 0; i < selectedRows.length; i++) {
				if (selectedRows[i][opts.idField] == row[opts.idField]) {
					selectedRows[i] = row;
					return true;
				}
			}
			return false;
		};
	};
	function getRowIndex(jq, row) {
		var opts = $.data(jq, "datagrid").options;
		var rows = $.data(jq, "datagrid").data.rows;
		if (typeof row == "object") {
			return getObjectIndex(rows, row);
		} else {
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i][opts.idField] == row) {
					return i;
				}
			}
			return -1;
		}
	};
	function getSelected(jq) {
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		if (opts.idField) {
			return $.data(jq, "datagrid").selectedRows;
		} else {
			var rowIndexs = [];
			opts.finder.getTr(jq, "", "selected", 2).each(function() {
				var rowIndex = parseInt($(this).attr("datagrid-row-index"));
				rowIndexs.push(data.rows[rowIndex]);
			});
			return rowIndexs;
		}
	};
	function clearSelections(jq) {
		unselectAll(jq);
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		selectedRows.splice(0, selectedRows.length);
	};
	function selectAll(jq) {
		var opts = $.data(jq, "datagrid").options;
		var rows = $.data(jq, "datagrid").data.rows;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		var tr = opts.finder.getTr(jq, "", "allbody").addClass(
				"datagrid-row-selected");
		var checkbox = tr.find("div.datagrid-cell-check input[type=checkbox]");
		$.fn.prop ? checkbox.prop("checked", true) : checkbox.attr("checked", true);
		for ( var rowIndex = 0; rowIndex < rows.length; rowIndex++) {
			if (opts.idField) {
				(function() {
					var row = rows[rowIndex];
					for ( var i = 0; i < selectedRows.length; i++) {
						if (selectedRows[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					selectedRows.push(row);
				})();
			}
		}
		opts.onSelectAll.call(jq, rows);
	};
	function unselectAll(jq) {
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		var tr = opts.finder.getTr(jq, "", "selected").removeClass("datagrid-row-selected");
		var checkbox = tr.find("div.datagrid-cell-check input[type=checkbox]");
		$.fn.prop ? checkbox.prop("checked", false) : checkbox.attr("checked", false);
		if (opts.idField) {
			for ( var rowIndex = 0; rowIndex < data.rows.length; rowIndex++) {
				unSelectedRowsById(selectedRows, opts.idField, data.rows[rowIndex][opts.idField]);
			}
		}
		opts.onUnselectAll.call(jq, data.rows);
	};
	function selectRow(jq, rowIndex) {
		var dc = $.data(jq, "datagrid").dc;
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		if (rowIndex < 0 || rowIndex >= data.rows.length) {
			return;
		}
		if (opts.singleSelect == true) {
			clearSelections(jq);
		}
		var tr = opts.finder.getTr(jq, rowIndex);
		if (!tr.hasClass("datagrid-row-selected")) {
			tr.addClass("datagrid-row-selected");
			var ck = $("div.datagrid-cell-check input[type=checkbox]", tr);
			$.fn.prop ? ck.prop("checked", true) : ck.attr("checked", true);
			if (opts.idField) {
				var row = data.rows[rowIndex];
				(function() {
					for ( var i = 0; i < selectedRows.length; i++) {
						if (selectedRows[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					selectedRows.push(row);
				})();
			}
		}
		opts.onSelect.call(jq, rowIndex, data.rows[rowIndex]);
		var height = dc.view2.children("div.datagrid-header").outerHeight();
		var gridBody = dc.body2;
		var top = tr.position().top - height;
		if (top <= 0) {
			gridBody.scrollTop(gridBody.scrollTop() + top);
		} else {
			if (top + tr.outerHeight() > gridBody.height() - 18) {
				gridBody.scrollTop(gridBody.scrollTop() + top + tr.outerHeight()
						- gridBody.height() + 18);
			}
		}
	};
	function selectRecord(jq, id) {
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		if (opts.idField) {
			var index = -1;
			for ( var i = 0; i < data.rows.length; i++) {
				if (data.rows[i][opts.idField] == id) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				selectRow(jq, index);
			}
		}
	};
	function unselectRow(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var dc = $.data(jq, "datagrid").dc;
		var data = $.data(jq, "datagrid").data;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		if (rowIndex < 0 || rowIndex >= data.rows.length) {
			return;
		}
		var tr = opts.finder.getTr(jq, rowIndex);
		var ck = tr.find("div.datagrid-cell-check input[type=checkbox]");
		tr.removeClass("datagrid-row-selected");
		$.fn.prop ? ck.prop("checked", false) : ck.attr("checked", false);
		var row = data.rows[rowIndex];
		if (opts.idField) {
			unSelectedRowsById(selectedRows, opts.idField, row[opts.idField]);
		}
		opts.onUnselect.call(jq, rowIndex, row);
	};
	function beginEdit(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var tr = opts.finder.getTr(jq, rowIndex);
		var row = opts.finder.getRow(jq, rowIndex);
		if (tr.hasClass("datagrid-row-editing")) {
			return;
		}
		if (opts.onBeforeEdit.call(jq, rowIndex, row) == false) {
			return;
		}
		tr.addClass("datagrid-row-editing");
		createEditor(jq, rowIndex);
		fixEditorSize(jq);
		tr.find("div.datagrid-editable").each(function() {
			var field = $(this).parent().attr("field");
			var ed = $.data(this, "datagrid.editor");
			ed.actions.setValue(ed.target, row[field]);
		});
		validateRow(jq, rowIndex);
	};
	function stopEdit(jq, rowIndex, revert) {
		var opts = $.data(jq, "datagrid").options;
		var updatedRows = $.data(jq, "datagrid").updatedRows;
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		var tr = opts.finder.getTr(jq, rowIndex);
		var row = opts.finder.getRow(jq, rowIndex);
		if (!tr.hasClass("datagrid-row-editing")) {
			return;
		}
		if (!revert) {
			if (!validateRow(jq, rowIndex)) {
				return;
			}
			var changed = false;
			var newValues = {};
			tr.find("div.datagrid-editable").each(function() {
				var field = $(this).parent().attr("field");
				var ed = $.data(this, "datagrid.editor");
				var value = ed.actions.getValue(ed.target);
				if (row[field] != value) {
					row[field] = value;
					changed = true;
					newValues[field] = value;
				}
			});
			if (changed) {
				if (getObjectIndex(insertedRows, row) == -1) {
					if (getObjectIndex(updatedRows, row) == -1) {
						updatedRows.push(row);
					}
				}
			}
		}
		tr.removeClass("datagrid-row-editing");
		destroyEditor(jq, rowIndex);
		$(jq).datagrid("refreshRow", rowIndex);
		if (!revert) {
			opts.onAfterEdit.call(jq, rowIndex, row, newValues);
		} else {
			opts.onCancelEdit.call(jq, rowIndex, row);
		}
	};
	function getEditors(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var tr = opts.finder.getTr(jq, rowIndex);
		var editors = [];
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				editors.push(ed);
			}
		});
		return editors;
	};
	function getEditor(jq, options) {
		var editors = getEditors(jq, options.index);
		for ( var i = 0; i < editors.length; i++) {
			if (editors[i].field == options.field) {
				return editors[i];
			}
		}
		return null;
	};
	function createEditor(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var tr = opts.finder.getTr(jq, rowIndex);
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-cell");
			var field = $(this).attr("field");
			var col = getColumnOption(jq, field);
			if (col && col.editor) {
				var type, editorOpts;
				if (typeof col.editor == "string") {
					type = col.editor;
				} else {
					type = col.editor.type;
					editorOpts = col.editor.options;
				}
				var editor = opts.editors[type];
				if (editor) {
					var html = cell.html();
					var width = cell.outerWidth();
					cell.addClass("datagrid-editable");
					if ($.boxModel == true) {
						cell.width(width - (cell.outerWidth() - cell.width()));
					}
					cell.html("<table border=\"0\" cellspacing=\"0\" cellpadding=\"1\"><tr><td></td></tr></table>");
					cell.children("table").attr("align",col.align);
					cell.children("table").bind("click dblclick contextmenu",function(e) {
						e.stopPropagation();
					});
					$.data(cell[0], "datagrid.editor", {
						actions : editor,
						target : editor.init(cell.find("td"), editorOpts),
						field : field,
						type : type,
						oldHtml : html
					});
				}
			}
		});
		fixRowHeight(jq, rowIndex);
	};
	function destroyEditor(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var tr = opts.finder.getTr(jq, rowIndex);
		tr.children("td").each(function() {
			var cell = $(this).find("div.datagrid-editable");
			if (cell.length) {
				var ed = $.data(cell[0], "datagrid.editor");
				if (ed.actions.destroy) {
					ed.actions.destroy(ed.target);
				}
				cell.html(ed.oldHtml);
				$.removeData(cell[0], "datagrid.editor");
				var width = cell.outerWidth();
				cell.removeClass("datagrid-editable");
				if ($.boxModel == true) {
					cell.width(width - (cell.outerWidth() - cell.width()));
				}
			}
		});
	};
	function validateRow(jq, rowIndex) {
		var tr = $.data(jq, "datagrid").options.finder.getTr(jq, rowIndex);
		if (!tr.hasClass("datagrid-row-editing")) {
			return true;
		}
		var vbox = tr.find(".validatebox-text");
		vbox.validatebox("validate");
		vbox.trigger("mouseleave");
		var invalid = tr.find(".validatebox-invalid");
		return invalid.length == 0;
	};
	function getChanges(jq, type) {
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		var deletedRows = $.data(jq, "datagrid").deletedRows;
		var updatedRows = $.data(jq, "datagrid").updatedRows;
		if (!type) {
			var rows = [];
			rows = rows.concat(insertedRows);
			rows = rows.concat(deletedRows);
			rows = rows.concat(updatedRows);
			return rows;
		} else {
			if (type == "inserted") {
				return insertedRows;
			} else {
				if (type == "deleted") {
					return deletedRows;
				} else {
					if (type == "updated") {
						return updatedRows;
					}
				}
			}
		}
		return [];
	};
	function deleteRow(jq, rowIndex) {
		var opts = $.data(jq, "datagrid").options;
		var data = $.data(jq, "datagrid").data;
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		var deletedRows = $.data(jq, "datagrid").deletedRows;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		$(jq).datagrid("cancelEdit", rowIndex);
		var row = data.rows[rowIndex];
		if (getObjectIndex(insertedRows, row) >= 0) {
			unSelectedRowsById(insertedRows, row);
		} else {
			deletedRows.push(row);
		}
		unSelectedRowsById(selectedRows, opts.idField, data.rows[rowIndex][opts.idField]);
		opts.view.deleteRow.call(opts.view, jq, rowIndex);
		if (opts.height == "auto") {
			fixRowHeight(jq);
		}
	};
	function insertRow(jq, param) {
		var view = $.data(jq, "datagrid").options.view;
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		view.insertRow.call(view, jq, param.index, param.row);
		resetGridEvent(jq);
		insertedRows.push(param.row);
	};
	function appendRow(jq, row) {
		var view = $.data(jq, "datagrid").options.view;
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		view.insertRow.call(view, jq, null, row);
		resetGridEvent(jq);
		insertedRows.push(row);
	};
	function resetOperation(jq) {
		var data = $.data(jq, "datagrid").data;
		var rows = data.rows;
		var originalRows = [];
		for ( var i = 0; i < rows.length; i++) {
			originalRows.push($.extend( {}, rows[i]));
		}
		$.data(jq, "datagrid").originalRows = originalRows;
		$.data(jq, "datagrid").updatedRows = [];
		$.data(jq, "datagrid").insertedRows = [];
		$.data(jq, "datagrid").deletedRows = [];
	};
	function acceptChanges(jq) {
		var data = $.data(jq, "datagrid").data;
		var ok = true;
		for ( var i = 0, len = data.rows.length; i < len; i++) {
			if (validateRow(jq, i)) {
				stopEdit(jq, i, false);
			} else {
				ok = false;
			}
		}
		if (ok) {
			resetOperation(jq);
		}
	};
	function rejectChanges(jq) {
		var opts = $.data(jq, "datagrid").options;
		var originalRows = $.data(jq, "datagrid").originalRows;
		var insertedRows = $.data(jq, "datagrid").insertedRows;
		var deletedRows = $.data(jq, "datagrid").deletedRows;
		var selectedRows = $.data(jq, "datagrid").selectedRows;
		var data = $.data(jq, "datagrid").data;
		for ( var i = 0; i < data.rows.length; i++) {
			stopEdit(jq, i, true);
		}
		var records = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			records.push(selectedRows[i][opts.idField]);
		}
		selectedRows.splice(0, selectedRows.length);
		data.total += deletedRows.length - insertedRows.length;
		data.rows = originalRows;
		renderGrid(jq, data);
		for ( var i = 0; i < records.length; i++) {
			selectRecord(jq, records[i]);
		}
		resetOperation(jq);
	};
	function request(jq, param) {
		var opts = $.data(jq, "datagrid").options;
		if (param) {
			opts.queryParams = param;
		}
		if (!opts.url) {
			return;
		}
		var param = $.extend( {}, opts.queryParams);
		if (opts.pagination) {
			$.extend(param, {
				page : opts.pageNumber,
				rows : opts.pageSize
			});
		}
		if (opts.sortName) {
			$.extend(param, {
				sort : opts.sortName,
				order : opts.sortOrder
			});
		}
		if (opts.onBeforeLoad.call(jq, param) == false) {
			return;
		}
		$(jq).datagrid("loading");
		setTimeout(function() {
			doRequest();
		}, 0);
		function doRequest() {
			$.ajax( {
				type : opts.method,
				url : opts.url,
				data : param,
				dataType : "json",
				success : function(data) {
					setTimeout(function() {
						$(jq).datagrid("loaded");
					}, 0);
					renderGrid(jq, data);
					setTimeout(function() {
						resetOperation(jq);
					}, 0);
				},
				error : function() {
					setTimeout(function() {
						$(jq).datagrid("loaded");
					}, 0);
					if (opts.onLoadError) {
						opts.onLoadError.apply(jq, arguments);
					}
				}
			});
		};
	};
	function mergeCells(jq, options) {
		var opts = $.data(jq, "datagrid").options;
		var rows = $.data(jq, "datagrid").data.rows;
		options.rowspan = options.rowspan || 1;
		options.colspan = options.colspan || 1;
		if (options.index < 0 || options.index >= rows.length) {
			return;
		}
		if (options.rowspan == 1 && options.colspan == 1) {
			return;
		}
		var field = rows[options.index][options.field];
		var tr = opts.finder.getTr(jq, options.index);
		var td = tr.find("td[field=\"" + options.field + "\"]");
		td.attr("rowspan", options.rowspan).attr("colspan", options.colspan);
		td.addClass("datagrid-td-merged");
		for ( var i = 1; i < options.colspan; i++) {
			td = td.next();
			td.hide();
			rows[options.index][td.attr("field")] = field;
		}
		for ( var i = 1; i < options.rowspan; i++) {
			tr = tr.next();
			var td = tr.find("td[field=\"" + options.field + "\"]").hide();
			rows[options.index + i][td.attr("field")] = field;
			for ( var j = 1; j < options.colspan; j++) {
				td = td.next();
				td.hide();
				rows[options.index + i][td.attr("field")] = field;
			}
		}
		setTimeout(function() {
			fixMergedCellsSize(jq);
		}, 0);
	};
	$.fn.datagrid = function(options, param) {
		if (typeof options == "string") {
			return $.fn.datagrid.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "datagrid");
			var opts;
			if (state) {
				opts = $.extend(state.options, options);
				state.options = opts;
			} else {
				opts = $.extend( {}, $.extend( {}, $.fn.datagrid.defaults, {
					queryParams : {}
				}), $.fn.datagrid.parseOptions(this), options);
				$(this).css("width", "").css("height", "");
				var gridWrap = wrapGrid(this, opts.rownumbers);
				if (!opts.columns) {
					opts.columns = gridWrap.columns;
				}
				if (!opts.frozenColumns) {
					opts.frozenColumns = gridWrap.frozenColumns;
				}
				$.data(this, "datagrid", {
					options : opts,
					panel : gridWrap.panel,
					dc : gridWrap.dc,
					selectedRows : [],
					data : {
						total : 0,
						rows : []
					},
					originalRows : [],
					updatedRows : [],
					insertedRows : [],
					deletedRows : []
				});
			}
			init(this);
			if (!state) {
				var data = getGridData(this);
				if (data.total > 0) {
					renderGrid(this, data);
					resetOperation(this);
				}
			}
			setSize(this);
			if (opts.url) {
				request(this);
			}
			setProperties(this);
		});
	};
	var editors = {
		text : {
			init : function(container, options) {
				var editor = $("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(container);
				return editor;
			},
			getValue : function(jq) {
				return $(jq).val();
			},
			setValue : function(jq, value) {
				$(jq).val(value);
			},
			resize : function(jq, width) {
				var editor = $(jq);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		textarea : {
			init : function(container, options) {
				var editor = $("<textarea class=\"datagrid-editable-input\"></textarea>").appendTo(container);
				return editor;
			},
			getValue : function(jq) {
				return $(jq).val();
			},
			setValue : function(jq, value) {
				$(jq).val(value);
			},
			resize : function(jq, width) {
				var editor = $(jq);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		checkbox : {
			init : function(container, options) {
				var editor = $("<input type=\"checkbox\">").appendTo(container);
				editor.val(options.on);
				editor.attr("offval", options.off);
				return editor;
			},
			getValue : function(jq) {
				if ($(jq).is(":checked")) {
					return $(jq).val();
				} else {
					return $(jq).attr("offval");
				}
			},
			setValue : function(jq, value) {
				var checked = false;
				if ($(jq).val() == value) {
					checked = true;
				}
				$.fn.prop ? $(jq).prop("checked", checked) : $(jq).attr(
						"checked", checked);
			}
		},
		numberbox : {
			init : function(container, options) {
				var editor = $("<input type=\"text\" class=\"datagrid-editable-input\">").appendTo(container);
				editor.numberbox(options);
				return editor;
			},
			destroy : function(jq) {
				$(jq).numberbox("destroy");
			},
			getValue : function(jq) {
				return $(jq).numberbox("getValue");
			},
			setValue : function(jq, value) {
				$(jq).numberbox("setValue", value);
			},
			resize : function(jq, width) {
				var editor = $(jq);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		validatebox : {
			init : function(container, options) {
				var editor = $(
						"<input type=\"text\" class=\"datagrid-editable-input\">")
						.appendTo(container);
				editor.validatebox(options);
				return editor;
			},
			destroy : function(jq) {
				$(jq).validatebox("destroy");
			},
			getValue : function(jq) {
				return $(jq).val();
			},
			setValue : function(jq, value) {
				$(jq).val(value);
			},
			resize : function(jq, width) {
				var editor = $(jq);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		datebox : {
			init : function(container, options) {
				var editor = $("<input type=\"text\">").appendTo(container);
				editor.datebox(options);
				return editor;
			},
			destroy : function(jq) {
				$(jq).datebox("destroy");
			},
			getValue : function(jq) {
				return $(jq).datebox("getValue");
			},
			setValue : function(jq, value) {
				$(jq).datebox("setValue", value);
			},
			resize : function(jq, width) {
				$(jq).datebox("resize", width);
			}
		},
		combobox : {
			init : function(container, options) {
				var editor = $("<input type=\"text\">").appendTo(container);
				editor.combobox(options || {});
				return editor;
			},
			destroy : function(jq) {
				$(jq).combobox("destroy");
			},
			getValue : function(jq) {
				return $(jq).combobox("getValue");
			},
			setValue : function(jq, value) {
				$(jq).combobox("setValue", value);
			},
			resize : function(jq, width) {
				$(jq).combobox("resize", width);
			}
		},
		combotree : {
			init : function(container, options) {
				var editor = $("<input type=\"text\">").appendTo(container);
				editor.combotree(options);
				return editor;
			},
			destroy : function(jq) {
				$(jq).combotree("destroy");
			},
			getValue : function(jq) {
				return $(jq).combotree("getValue");
			},
			setValue : function(jq, value) {
				$(jq).combotree("setValue", value);
			},
			resize : function(jq, width) {
				$(jq).combotree("resize", width);
			}
		}
	};
	$.fn.datagrid.methods = {
		options : function(jq) {
			var gridOpts = $.data(jq[0], "datagrid").options;
			var panelOpts = $.data(jq[0], "datagrid").panel.panel("options");
			var opts = $.extend(gridOpts, {
				width : panelOpts.width,
				height : panelOpts.height,
				closed : panelOpts.closed,
				collapsed : panelOpts.collapsed,
				minimized : panelOpts.minimized,
				maximized : panelOpts.maximized
			});
			var pager = jq.datagrid("getPager");
			if (pager.length) {
				var pagerOpts = pager.pagination("options");
				$.extend(opts, {
					pageNumber : pagerOpts.pageNumber,
					pageSize : pagerOpts.pageSize
				});
			}
			return opts;
		},
		getPanel : function(jq) {
			return $.data(jq[0], "datagrid").panel;
		},
		getPager : function(jq) {
			return $.data(jq[0], "datagrid").panel.find("div.datagrid-pager");
		},
		getColumnFields : function(jq, frozen) {
			return getColumnFields(jq[0], frozen);
		},
		getColumnOption : function(jq, field) {
			return getColumnOption(jq[0], field);
		},
		resize : function(jq, param) {
			return jq.each(function() {
				setSize(this, param);
			});
		},
		load : function(jq, param) {
			return jq.each(function() {
				var opts = $(this).datagrid("options");
				opts.pageNumber = 1;
				var pager = $(this).datagrid("getPager");
				pager.pagination( {
					pageNumber : 1
				});
				request(this, param);
			});
		},
		reload : function(jq, param) {
			return jq.each(function() {
				request(this, param);
			});
		},
		reloadFooter : function(jq, footer) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				var view = $(this).datagrid("getPanel").children(
						"div.datagrid-view");
				var gridView1 = view.children("div.datagrid-view1");
				var gridView2 = view.children("div.datagrid-view2");
				if (footer) {
					$.data(this, "datagrid").footer = footer;
				}
				if (opts.showFooter) {
					opts.view.renderFooter.call(opts.view, this, gridView2
							.find("div.datagrid-footer-inner"), false);
					opts.view.renderFooter.call(opts.view, this, gridView1
							.find("div.datagrid-footer-inner"), true);
					if (opts.view.onAfterRender) {
						opts.view.onAfterRender.call(opts.view, this);
					}
					$(this).datagrid("fixRowHeight");
				}
			});
		},
		loading : function(jq) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				$(this).datagrid("getPager").pagination("loading");
				if (opts.loadMsg) {
					var panel = $(this).datagrid("getPanel");
					$("<div class=\"datagrid-mask\" style=\"display:block\"></div>").appendTo(panel);
					$("<div class=\"datagrid-mask-msg\" style=\"display:block\"></div>").html(opts.loadMsg).appendTo(panel);
					setMsgSize(this);
				}
			});
		},
		loaded : function(jq) {
			return jq.each(function() {
				$(this).datagrid("getPager").pagination("loaded");
				var panel = $(this).datagrid("getPanel");
				panel.children("div.datagrid-mask-msg").remove();
				panel.children("div.datagrid-mask").remove();
			});
		},
		fitColumns : function(jq) {
			return jq.each(function() {
				fitColumns(this);
			});
		},
		fixColumnSize : function(jq) {
			return jq.each(function() {
				fixColumnSize(this);
			});
		},
		fixRowHeight : function(jq, index) {
			return jq.each(function() {
				fixRowHeight(this, index);
			});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				renderGrid(this, data);
				resetOperation(this);
			});
		},
		getData : function(jq) {
			return $.data(jq[0], "datagrid").data;
		},
		getRows : function(jq) {
			return $.data(jq[0], "datagrid").data.rows;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], "datagrid").footer;
		},
		getRowIndex : function(jq, id) {
			return getRowIndex(jq[0], id);
		},
		getSelected : function(jq) {
			var rows = getSelected(jq[0]);
			return rows.length > 0 ? rows[0] : null;
		},
		getSelections : function(jq) {
			return getSelected(jq[0]);
		},
		clearSelections : function(jq) {
			return jq.each(function() {
				clearSelections(this);
			});
		},
		selectAll : function(jq) {
			return jq.each(function() {
				selectAll(this);
			});
		},
		unselectAll : function(jq) {
			return jq.each(function() {
				unselectAll(this);
			});
		},
		selectRow : function(jq, index) {
			return jq.each(function() {
				selectRow(this, index);
			});
		},
		selectRecord : function(jq, id) {
			return jq.each(function() {
				selectRecord(this, id);
			});
		},
		unselectRow : function(jq, index) {
			return jq.each(function() {
				unselectRow(this, index);
			});
		},
		beginEdit : function(jq, index) {
			return jq.each(function() {
				beginEdit(this, index);
			});
		},
		endEdit : function(jq, index) {
			return jq.each(function() {
				stopEdit(this, index, false);
			});
		},
		cancelEdit : function(jq, index) {
			return jq.each(function() {
				stopEdit(this, index, true);
			});
		},
		getEditors : function(jq, index) {
			return getEditors(jq[0], index);
		},
		getEditor : function(jq, options) {
			return getEditor(jq[0], options);
		},
		refreshRow : function(jq, index) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.refreshRow.call(opts.view, this, index);
			});
		},
		validateRow : function(jq, index) {
			return validateRow(jq[0], index);
		},
		updateRow : function(jq, param) {
			return jq.each(function() {
				var opts = $.data(this, "datagrid").options;
				opts.view.updateRow.call(opts.view, this, param.index,param.row);
			});
		},
		appendRow : function(jq, row) {
			return jq.each(function() {
				appendRow(this, row);
			});
		},
		insertRow : function(jq, param) {
			return jq.each(function() {
				insertRow(this, param);
			});
		},
		deleteRow : function(jq, index) {
			return jq.each(function() {
				deleteRow(this, index);
			});
		},
		getChanges : function(jq, type) {
			return getChanges(jq[0], type);
		},
		acceptChanges : function(jq) {
			return jq.each(function() {
				acceptChanges(this);
			});
		},
		rejectChanges : function(jq) {
			return jq.each(function() {
				rejectChanges(this);
			});
		},
		mergeCells : function(jq, options) {
			return jq.each(function() {
				mergeCells(this, options);
			});
		},
		showColumn : function(jq, field) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + field + "\"]").show();
				$(this).datagrid("getColumnOption", field).hidden = false;
				$(this).datagrid("fitColumns");
			});
		},
		hideColumn : function(jq, field) {
			return jq.each(function() {
				var panel = $(this).datagrid("getPanel");
				panel.find("td[field=\"" + field + "\"]").hide();
				$(this).datagrid("getColumnOption", field).hidden = true;
				$(this).datagrid("fitColumns");
			});
		}
	};
	$.fn.datagrid.parseOptions = function(jq) {
		var t = $(jq);
		return $.extend({},$.fn.panel.parseOptions(jq),{
			fitColumns : (t.attr("fitColumns") ? t
					.attr("fitColumns") == "true" : undefined),
			striped : (t.attr("striped") ? t.attr("striped") == "true"
					: undefined),
			nowrap : (t.attr("nowrap") ? t.attr("nowrap") == "true"
					: undefined),
			rownumbers : (t.attr("rownumbers") ? t
					.attr("rownumbers") == "true" : undefined),
			singleSelect : (t.attr("singleSelect") ? t
					.attr("singleSelect") == "true" : undefined),
			pagination : (t.attr("pagination") ? t
					.attr("pagination") == "true" : undefined),
			pageSize : (t.attr("pageSize") ? parseInt(t
					.attr("pageSize")) : undefined),
			pageNumber : (t.attr("pageNumber") ? parseInt(t
					.attr("pageNumber")) : undefined),
			pageList : (t.attr("pageList") ? eval(t
					.attr("pageList")) : undefined),
			remoteSort : (t.attr("remoteSort") ? t
					.attr("remoteSort") == "true" : undefined),
			sortName : t.attr("sortName"),
			sortOrder : t.attr("sortOrder"),
			showHeader : (t.attr("showHeader") ? t
					.attr("showHeader") == "true" : undefined),
			showFooter : (t.attr("showFooter") ? t
					.attr("showFooter") == "true" : undefined),
			scrollbarSize : (t.attr("scrollbarSize") ? parseInt(t
					.attr("scrollbarSize"))
					: undefined),
			loadMsg : (t.attr("loadMsg") != undefined ? t
					.attr("loadMsg") : undefined),
			idField : t.attr("idField"),
			toolbar : t.attr("toolbar"),
			url : t.attr("url"),
			rowStyler : (t.attr("rowStyler") ? eval(t
					.attr("rowStyler")) : undefined)
		});
	};
	var view = {
		render : function(jq, container, frozen) {
			var opts = $.data(jq, "datagrid").options;
			var rows = $.data(jq, "datagrid").data.rows;
			var fields = $(jq).datagrid("getColumnFields", frozen);
			if (frozen) {
				if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
					return;
				}
			}
			var html = [ "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for ( var i = 0; i < rows.length; i++) {
				var cls = (i % 2 && opts.striped) ? "class=\"datagrid-row-alt\""
						: "";
				var style = opts.rowStyler ? opts.rowStyler.call(jq, i,rows[i]) : "";
				var style = style ? "style=\"" + style + "\"" : "";
				html.push("<tr datagrid-row-index=\"" + i + "\" " + cls + " "+ style + ">");
				html.push(this.renderRow.call(this, jq, fields, frozen, i,rows[i]));
				html.push("</tr>");
			}
			html.push("</tbody></table>");
			$(container).html(html.join(""));
		},
		renderFooter : function(jq, container, frozen) {
			var opts = $.data(jq, "datagrid").options;
			var rows = $.data(jq, "datagrid").footer || [];
			var fields = $(jq).datagrid("getColumnFields", frozen);
			var html = [ "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" ];
			for ( var i = 0; i < rows.length; i++) {
				html.push("<tr datagrid-row-index=\"" + i + "\">");
				html.push(this.renderRow.call(this, jq, fields, frozen, i,
						rows[i]));
				html.push("</tr>");
			}
			html.push("</tbody></table>");
			$(container).html(html.join(""));
		},
		renderRow : function(jq, fields, frozen, rowIndex, rowData) {
			var opts = $.data(jq, "datagrid").options;
			var cc = [];
			if (frozen && opts.rownumbers) {
				var rowNumber = rowIndex + 1;
				if (opts.pagination) {
					rowNumber += (opts.pageNumber - 1) * opts.pageSize;
				}
				cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rowNumber + "</div></td>");
			}
			for ( var i = 0; i < fields.length; i++) {
				var field = fields[i];
				var col = $(jq).datagrid("getColumnOption", field);
				if (col) {
					var style = col.styler ? (col.styler(rowData[field], rowData, rowIndex) || "") : "";
					var style = col.hidden ? "style=\"display:none;" + style + "\"" : (style ? "style=\"" + style + "\"" : "");
					cc.push("<td field=\"" + field + "\" " + style + ">");
					var style = "width:" + (col.boxWidth) + "px;";
					style += "text-align:" + (col.align || "left") + ";";
					style += opts.nowrap == false ? "white-space:normal;" : "";
					cc.push("<div style=\"" + style + "\" ");
					if (col.checkbox) {
						cc.push("class=\"datagrid-cell-check ");
					} else {
						cc.push("class=\"datagrid-cell ");
					}
					cc.push("\">");
					if (col.checkbox) {
						cc.push("<input type=\"checkbox\"/>");
					} else {
						if (col.formatter) {
							cc.push(col.formatter(rowData[field], rowData, rowIndex));
						} else {
							cc.push(rowData[field]);
						}
					}
					cc.push("</div>");
					cc.push("</td>");
				}
			}
			return cc.join("");
		},
		refreshRow : function(jq, rowIndex) {
			var row = {};
			var fields = $(jq).datagrid("getColumnFields", true).concat(
					$(jq).datagrid("getColumnFields", false));
			for ( var i = 0; i < fields.length; i++) {
				row[fields[i]] = undefined;
			}
			var rows = $(jq).datagrid("getRows");
			$.extend(row, rows[rowIndex]);
			this.updateRow.call(this, jq, rowIndex, row);
		},
		updateRow : function(jq, rowIndex, row) {
			var opts = $.data(jq, "datagrid").options;
			var rows = $(jq).datagrid("getRows");
			var tr = opts.finder.getTr(jq, rowIndex);
			for ( var field in row) {
				rows[rowIndex][field] = row[field];
				var td = tr.children("td[field=\"" + field + "\"]");
				var cell = td.find("div.datagrid-cell");
				var col = $(jq).datagrid("getColumnOption", field);
				if (col) {
					var style = col.styler ? col.styler(rows[rowIndex][field],rows[rowIndex], rowIndex) : "";
					td.attr("style", style || "");
					if (col.hidden) {
						td.hide();
					}
					if (col.formatter) {
						cell.html(col.formatter(rows[rowIndex][field], rows[rowIndex],rowIndex));
					} else {
						cell.html(rows[rowIndex][field]);
					}
				}
			}
			var style = opts.rowStyler ? opts.rowStyler.call(jq, rowIndex,rows[rowIndex]) : "";
			tr.attr("style", style || "");
			$(jq).datagrid("fixRowHeight", rowIndex);
		},
		insertRow : function(jq, rowIndex, row) {
			var opts = $.data(jq, "datagrid").options;
			var dc = $.data(jq, "datagrid").dc;
			var data = $.data(jq, "datagrid").data;
			if (rowIndex == undefined || rowIndex == null) {
				rowIndex = data.rows.length;
			}
			if (rowIndex > data.rows.length) {
				rowIndex = data.rows.length;
			}
			for ( var i = data.rows.length - 1; i >= rowIndex; i--) {
				opts.finder.getTr(jq, i, "body", 2).attr(
						"datagrid-row-index", i + 1);
				var tr = opts.finder.getTr(jq, i, "body", 1).attr(
						"datagrid-row-index", i + 1);
				if (opts.rownumbers) {
					tr.find("div.datagrid-cell-rownumber").html(i + 2);
				}
			}
			var frozenFields = $(jq).datagrid("getColumnFields", true);
			var fields = $(jq).datagrid("getColumnFields", false);
			var tr1 = "<tr datagrid-row-index=\"" + rowIndex + "\">"
					+ this.renderRow.call(this, jq, frozenFields, true, rowIndex, row)
					+ "</tr>";
			var tr2 = "<tr datagrid-row-index=\"" + rowIndex + "\">"
					+ this.renderRow.call(this, jq, fields, false, rowIndex, row)
					+ "</tr>";
			if (rowIndex >= data.rows.length) {
				if (data.rows.length) {
					opts.finder.getTr(jq, "", "last", 1).after(tr1);
					opts.finder.getTr(jq, "", "last", 2).after(tr2);
				} else {
					dc.body1.html("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr1 + "</tbody></table>");
					dc.body2.html("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>" + tr2 + "</tbody></table>");
				}
			} else {
				opts.finder.getTr(jq, rowIndex + 1, "body", 1).before(tr1);
				opts.finder.getTr(jq, rowIndex + 1, "body", 2).before(tr2);
			}
			data.total += 1;
			data.rows.splice(rowIndex, 0, row);
			this.refreshRow.call(this, jq, rowIndex);
		},
		deleteRow : function(jq, rowIndex) {
			var opts = $.data(jq, "datagrid").options;
			var data = $.data(jq, "datagrid").data;
			opts.finder.getTr(jq, rowIndex).remove();
			for ( var i = rowIndex + 1; i < data.rows.length; i++) {
				opts.finder.getTr(jq, i, "body", 2).attr(
						"datagrid-row-index", i - 1);
				var tr1 = opts.finder.getTr(jq, i, "body", 1).attr(
						"datagrid-row-index", i - 1);
				if (opts.rownumbers) {
					tr1.find("div.datagrid-cell-rownumber").html(i);
				}
			}
			data.total -= 1;
			data.rows.splice(rowIndex, 1);
		},
		onBeforeRender : function(jq, rows) {
		},
		onAfterRender : function(jq) {
			var opts = $.data(jq, "datagrid").options;
			if (opts.showFooter) {
				var footer = $(jq).datagrid("getPanel").find("div.datagrid-footer");
				footer.find("div.datagrid-cell-rownumber,div.datagrid-cell-check").css("visibility", "hidden");
			}
		}
	};
	$.fn.datagrid.defaults = $.extend({},$.fn.panel.defaults,{
		frozenColumns : null,
		columns : null,
		fitColumns : false,
		toolbar : null,
		striped : false,
		method : "post",
		nowrap : true,
		idField : null,
		url : null,
		loadMsg : "Processing, please wait ...",
		rownumbers : false,
		singleSelect : false,
		pagination : false,
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		queryParams : {},
		sortName : null,
		sortOrder : "asc",
		remoteSort : true,
		showHeader : true,
		showFooter : false,
		scrollbarSize : 18,
		rowStyler : function(rowIndex, rowData) {
		},
		loadFilter : function(data) {
			if (typeof data.length == "number"
					&& typeof data.splice == "function") {
				return {
					total : data.length,
					rows : data
				};
			} else {
				return data;
			}
		},
		editors : editors,
		finder : {
			getTr : function(jq, rowIndex, type, step) {
				type = type || "body";
				step = step || 0;
				var dc = $.data(jq, "datagrid").dc;
				var opts = $.data(jq, "datagrid").options;
				if (step == 0) {
					var tr1 = opts.finder.getTr(jq, rowIndex,type, 1);
					var tr2 = opts.finder.getTr(jq, rowIndex,type, 2);
					return tr1.add(tr2);
				} else {
					if (type == "body") {
						return (step == 1 ? dc.body1 : dc.body2)
								.find(">table>tbody>tr[datagrid-row-index=" + rowIndex + "]");
					} else {
						if (type == "footer") {
							return (step == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index=" + rowIndex + "]");
						} else {
							if (type == "selected") {
								return (step == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr.datagrid-row-selected");
							} else {
								if (type == "last") {
									return (step == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr:last[datagrid-row-index]");
								} else {
									if (type == "allbody") {
										return (step == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index]");
									} else {
										if (type == "allfooter") {
											return (step == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index]");
										}
									}
								}
							}
						}
					}
				}
			},
			getRow : function(jq, rowIndex) {
				return $.data(jq, "datagrid").data.rows[rowIndex];
			}
		},
		view : view,
		onBeforeLoad : function(param) {
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onClickRow : function(rowIndex, rowData) {
		},
		onDblClickRow : function(rowIndex, rowData) {
		},
		onClickCell : function(rowIndex, field, value) {
		},
		onDblClickCell : function(rowIndex, field, value) {
		},
		onSortColumn : function(sort, order) {
		},
		onResizeColumn : function(field, width) {
		},
		onSelect : function(rowIndex, rowData) {
		},
		onUnselect : function(rowIndex, rowData) {
		},
		onSelectAll : function(rows) {
		},
		onUnselectAll : function(rows) {
		},
		onBeforeEdit : function(rowIndex, rowData) {
		},
		onAfterEdit : function(rowIndex, rowData, changes) {
		},
		onCancelEdit : function(rowIndex, rowData) {
		},
		onHeaderContextMenu : function(e, field) {
		},
		onRowContextMenu : function(e, owIndex, rowData) {
		}
	});
})(jQuery);
