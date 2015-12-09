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
	function init(jq) {
		var opts = $.data(jq, "propertygrid").options;
		$(jq).datagrid($.extend( {}, opts, {
			view : (opts.showGroup ? view : undefined),
			onClickRow : function(rowIndex, rowData) {
				if (opts.editIndex != rowIndex) {
					var valueOpts = $(this).datagrid("getColumnOption", "value");
					valueOpts.editor = rowData.editor;
					stopEdit(opts.editIndex);
					$(this).datagrid("beginEdit", rowIndex);
					$(this).datagrid("getEditors", rowIndex)[0].target.focus();
					opts.editIndex = rowIndex;
				}
				opts.onClickRow.call(jq, rowIndex, rowData);
			}
		}));
		$(jq).datagrid("getPanel").panel("panel").addClass("propertygrid");
		$(jq).datagrid("getPanel").find("div.datagrid-body").unbind(
				".propertygrid").bind("mousedown.propertygrid", function(e) {
			e.stopPropagation();
		});
		$(document).unbind(".propertygrid").bind("mousedown.propertygrid",function() {
			stopEdit(opts.editIndex);
			opts.editIndex = undefined;
		});
		function stopEdit(rowIndex) {
			if (rowIndex == undefined) {
				return;
			}
			var t = $(jq);
			if (t.datagrid("validateRow", rowIndex)) {
				t.datagrid("endEdit", rowIndex);
			} else {
				t.datagrid("cancelEdit", rowIndex);
			}
		};
	};
	$.fn.propertygrid = function(options, param) {
		if (typeof options == "string") {
			var method = $.fn.propertygrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "propertygrid");
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, "propertygrid", {
					options : $.extend( {}, $.fn.propertygrid.defaults,
							$.fn.propertygrid.parseOptions(this), options)
				});
			}
			init(this);
		});
	};
	$.fn.propertygrid.methods = {};
	$.fn.propertygrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend( {}, $.fn.datagrid.parseOptions(target), {
			showGroup : (t.attr("showGroup") ? t.attr("showGroup") == "true"
					: undefined)
		});
	};
	var view = $.extend({},$.fn.datagrid.defaults.view,{
		render : function(jq, container, frozen) {
			var opts = $.data(jq, "datagrid").options;
			var rows = $.data(jq, "datagrid").data.rows;
			var fields = $(jq).datagrid("getColumnFields", frozen);
			var html = [];
			var rowIndex = 0;
			var groups = this.groups;
			for ( var i = 0; i < groups.length; i++) {
				var group = groups[i];
				html.push("<div class=\"datagrid-group\" group-index=" + i + ">");
				html.push("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"height:100%\"><tbody>");
				html.push("<tr>");
				html.push("<td style=\"border:0;\">");
				if (!frozen) {
					html.push("<span>");
					html.push(opts.groupFormatter.call(jq,group.fvalue, group.rows));
					html.push("</span>");
				}
				html.push("</td>");
				html.push("</tr>");
				html.push("</tbody></table>");
				html.push("</div>");
				html.push("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>");
				for ( var j = 0; j < group.rows.length; j++) {
					var cls = (rowIndex % 2 && opts.striped) ? "class=\"datagrid-row-alt\""
							: "";
					var style = opts.rowStyler ? opts.rowStyler
							.call(jq, rowIndex, group.rows[j]) : "";
					var style = style ? "style=\"" + style + "\""
							: "";
					html.push("<tr datagrid-row-index=\"" + rowIndex
							+ "\" " + cls + " " + style + ">");
					html.push(this.renderRow.call(this, jq, fields,
							frozen, rowIndex, group.rows[j]));
					html.push("</tr>");
					rowIndex++;
				}
				html.push("</tbody></table>");
			}
			$(container).html(html.join(""));
		},
		onAfterRender : function(jq) {
			var opts = $.data(jq, "datagrid").options;
			var gridView = $(jq).datagrid("getPanel").find(
					"div.datagrid-view");
			var gridView1 = gridView.children("div.datagrid-view1");
			var gridView2 = gridView.children("div.datagrid-view2");
			$.fn.datagrid.defaults.view.onAfterRender.call(this, jq);
			if (opts.rownumbers || opts.frozenColumns.length) {
				var gridGroup = gridView1.find("div.datagrid-group");
			} else {
				var gridGroup = gridView2.find("div.datagrid-group");
			}
			$("<td style=\"border:0\"><div class=\"datagrid-row-expander datagrid-row-collapse\" style=\"width:25px;height:16px;cursor:pointer\"></div></td>")
					.insertBefore(gridGroup.find("td"));
			gridView.find("div.datagrid-group").each(function() {
				var groupIndex = $(this).attr("group-index");
				$(this).find("div.datagrid-row-expander")
					.bind("click",{
						groupIndex : groupIndex
					},
					function(e) {
						var group = gridView.find("div.datagrid-group[group-index=" + e.data.groupIndex + "]");
						if ($(this).hasClass("datagrid-row-collapse")) {
							$(this).removeClass("datagrid-row-collapse").addClass("datagrid-row-expand");
							group.next("table").hide();
						} else {
							$(this).removeClass("datagrid-row-expand").addClass("datagrid-row-collapse");
							group.next("table").show();
						}
						$(jq).datagrid("fixRowHeight");
					});
			});
		},
		onBeforeRender : function(jq, rows) {
			var opts = $.data(jq, "datagrid").options;
			var groups = [];
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var group = findGroup(row[opts.groupField]);
				if (!group) {
					group = {
						fvalue : row[opts.groupField],
						rows : [ row ],
						startRow : i
					};
					groups.push(group);
				} else {
					group.rows.push(row);
				}
			}
			function findGroup(value) {
				for ( var i = 0; i < groups.length; i++) {
					var group = groups[i];
					if (group.fvalue == value) {
						return group;
					}
				}
				return null;
			};
			this.groups = groups;
			var newRows = [];
			for ( var i = 0; i < groups.length; i++) {
				var group = groups[i];
				for ( var j = 0; j < group.rows.length; j++) {
					newRows.push(group.rows[j]);
				}
			}
			$.data(jq, "datagrid").data.rows = newRows;
		}
	});
	$.fn.propertygrid.defaults = $.extend( {}, $.fn.datagrid.defaults, {
		singleSelect : true,
		remoteSort : false,
		fitColumns : true,
		loadMsg : "",
		frozenColumns : [ [ {
			field : "f",
			width : 16,
			resizable : false
		} ] ],
		columns : [ [ {
			field : "name",
			title : "Name",
			width : 100,
			sortable : true
		}, {
			field : "value",
			title : "Value",
			width : 100,
			resizable : false
		} ] ],
		showGroup : false,
		groupField : "group",
		groupFormatter : function(data) {
			return data;
		}
	});
})(jQuery);
