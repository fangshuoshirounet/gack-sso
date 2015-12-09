/*
 * Flexigrid for jQuery - New Wave Grid
 * 
 * Copyright (c) 2008 Paulo P. Marinas (webplicity.net/flexigrid) Dual licensed
 * under the MIT (MIT-LICENSE.txt) and GPL (GPL-LICENSE.txt) licenses.
 *  
 */

(function($) {
	$.addFlex = function(t, p){
		if (t.grid)
			return false; // return if already exist
		p = $.extend({
					height : flexgirdHeight, // default height
					width : "auto", // auto width
					striped : true, // 隔行不同样式
					novstripe : false,
					minwidth : 30, // 列的最小宽度
					minheight : 80, // 列的最小高度
					resizable : false, // 是否可伸缩
					url : false, // ajax方式对应的url地址
					method : 'POST', // 数据发送方式
					dataType : 'json', // 数据加载的类型
					checkbox : true,// 是否要多选框
					errormsg : '系统异常',// 错误提示信息
					usepager : true, // 是否分页
					nowrap : true, // 是否不换行
					page : 1, // 默认当前页
					total : 0, // 总条目数
					useRp : true, // 是否可以动态设置每页显示的结果数
					rp : 15, // 每页默认的结果数
					rpOptions : [10, 15, 20, 25, 40,55,60],// 可选择设定的每页结果数
					title:"查询列表",//默认显示
					showTableToggleBtn:true,//是否显示表格的收缩按钮
					titleAlign : 'center',// 控制标题的align
					pagestat : '显示 {from} 到 {to} 条，共 {total} 条记录',// 显示当前页和总页面的样式
					pagetext : '第',
					outof : '  页，共',
					findtext : '查找',
					procmsg : '正在查询数据，请稍等 ...',
					query : '',
					qtype : '',
					nomsg : '没有符合条件的记录存在！',
					minColToggle : 1, // minimum allowed column to be hidden
					showToggleBtn : true, // show or hide column toggle popup
					hideOnSubmit : true,// 隐藏提交
					autoload : false,// 自动加载
					blockOpacity : 0.5,// 透明度设置
					onDragCol : false,
					onToggleCol : false,// 当在行之间转换时
					onChangeSort : false,// 当改变排序时
					onFrontSort : false,// 前台排序
					onSuccess : false,// 成功后执行
					onError : false,
					onSubmit : false, // 调用自定义的计算函数
					onRowDblclick : false,
					print : false,// 是否显示打印按钮
					toExcel : false,// 是否显示导出按钮
					onrowchecked : false,// 是否选中行后添加事件
					singleSelect : false,// 是否单选
					showpager : true,
					buttonAlign : 'left',
					relGrid : false
				}, p);

		$(t).show() // show if hidden
				.attr({
							cellPadding : 0,
							cellSpacing : 0,
							border : 0
						}) // remove padding and spacing
				.removeAttr('width') // remove width properties
		;

		// create grid class
		var g = {
			hset : {},
			rePosDrag : function() {// 设置表头

				var cdleft = 0 - this.hDiv.scrollLeft;
				if (this.hDiv.scrollLeft > 0)
					cdleft -= Math.floor(p.cgwidth / 2);
				$(g.cDrag).css({
							top : g.hDiv.offsetTop + 1
						});
				var cdpad = this.cdpad;

				$('div', g.cDrag).hide();

				$('thead tr:first th:visible', this.hDiv).each(function() {

							var curTR = $(this);
							var display = curTR.css("display");
							if (display == "none") {
								return;
							}

							var n = $('thead tr:first th:visible', g.hDiv)
									.index(this);

							var cdpos = parseInt($('div', this).width());
							var ppos = cdpos;
							if (cdleft == 0)
								cdleft -= Math.floor(p.cgwidth / 2);

							cdpos = cdpos + cdleft + cdpad;

							if (p.checkbox) {
								// add checkbox width
								$('div:eq(' + n + ')', g.cDrag).css({
											'left' : cdpos + 22 + 'px'
										}).show();
							} else {
								$('div:eq(' + n + ')', g.cDrag).css({
											'left' : cdpos + 'px'
										}).show();
							}
							cdleft = cdpos;
						});
			},
			fixHeight : function(newH) {
				newH = false;
				if (!newH)
					newH = $(g.bDiv).height();
				var hdHeight = $(this.hDiv).height();
				$('div', this.cDrag).each(function() {
							$(this).height(newH + hdHeight);
						});

				var nd = parseInt($(g.nDiv).height());

				if (nd > newH)
					$(g.nDiv).height(newH).width(200);
				else
					$(g.nDiv).height('auto').width('auto');

				$(g.block).css({
							height : newH,
							marginBottom : (newH * -1)
						});

				var hrH = g.bDiv.offsetTop + newH;
				if (p.height != 'auto' && p.resizable)
					hrH = g.vDiv.offsetTop;
				$(g.rDiv).css({
							height : hrH
						});

			},
			dragStart : function(dragtype, e, obj) { // default drag function
				// start

				if (dragtype == 'colresize') // column resize
				{
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					var n = $('div', this.cDrag).index(obj);
					var ow = $('th:visible div:eq(' + n + ')', this.hDiv)
							.width();
					$(obj).addClass('dragging').siblings().hide();
					$(obj).prev().addClass('dragging').show();

					this.colresize = {
						startX : e.pageX,
						ol : parseInt(obj.style.left),
						ow : ow,
						n : n
					};
					$('body').css('cursor', 'col-resize');
				} else if (dragtype == 'vresize') // table resize
				{
					var hgo = false;
					$('body').css('cursor', 'row-resize');
					if (obj) {
						hgo = true;
						$('body').css('cursor', 'col-resize');
					}
					this.vresize = {
						h : p.height,
						sy : e.pageY,
						w : p.width,
						sx : e.pageX,
						hgo : hgo
					};

				}

				else if (dragtype == 'colMove') // column header drag
				{
					$(g.nDiv).hide();
					$(g.nBtn).hide();
					this.hset = $(this.hDiv).offset();
					this.hset.right = this.hset.left
							+ $('table', this.hDiv).width();
					this.hset.bottom = this.hset.top
							+ $('table', this.hDiv).height();
					this.dcol = obj;
					this.dcoln = $('th', this.hDiv).index(obj);

					this.colCopy = document.createElement("div");
					this.colCopy.className = "colCopy";
					this.colCopy.innerHTML = obj.innerHTML;
					if ($.browser.msie) {
						this.colCopy.className = "colCopy ie";
					}

					$(this.colCopy).css({
								position : 'absolute',
								float : 'left',
								display : 'none',
								textAlign : obj.align
							});
					$('body').append(this.colCopy);
					$(this.cDrag).hide();

				}

				$('body').noSelect();

			},
			dragMove : function(e) {

				if (this.colresize) // column resize
				{
					var n = this.colresize.n;
					var diff = e.pageX - this.colresize.startX;
					var nleft = this.colresize.ol + diff;
					var nw = this.colresize.ow + diff;
					if (nw > p.minwidth) {
						$('div:eq(' + n + ')', this.cDrag).css('left', nleft);
						this.colresize.nw = nw;
					}
				} else if (this.vresize) // table resize
				{
					var v = this.vresize;
					var y = e.pageY;
					var diff = y - v.sy;

					if (!p.defwidth)
						p.defwidth = p.width;

					if (p.width != 'auto' && !p.nohresize && v.hgo) {
						var x = e.pageX;
						var xdiff = x - v.sx;
						var newW = v.w + xdiff;
						if (newW > p.defwidth) {
							this.gDiv.style.width = newW + 'px';
							p.width = newW;
						}
					}

					var newH = v.h + diff;
					if ((newH > p.minheight || p.height < p.minheight)
							&& !v.hgo) {
						this.bDiv.style.height = newH + 'px';
						p.height = newH;
						this.fixHeight(newH);
					}
					v = null;
				} else if (this.colCopy) {
					$(this.dcol).addClass('thMove').removeClass('thOver');
					if (e.pageX > this.hset.right || e.pageX < this.hset.left
							|| e.pageY > this.hset.bottom
							|| e.pageY < this.hset.top) {
						// this.dragEnd();
						$('body').css('cursor', 'move');
					} else
						$('body').css('cursor', 'pointer');
					$(this.colCopy).css({
								top : e.pageY + 10,
								left : e.pageX + 20,
								display : 'block'
							});
				}

			},
			dragEnd : function() {

				if (this.colresize) {
					var n = this.colresize.n;
					var nw = this.colresize.nw;

					$('th:visible div:eq(' + n + ')', this.hDiv).css('width',
							nw);
					$('tr', this.bDiv).each(function() {
						$('td:visible div:eq(' + n + ')', this)
								.css('width', nw);
					});
					this.hDiv.scrollLeft = this.bDiv.scrollLeft;

					$('div:eq(' + n + ')', this.cDrag).siblings().show();
					$('.dragging', this.cDrag).removeClass('dragging');
					this.rePosDrag();
					this.fixHeight();
					this.colresize = false;
				} else if (this.vresize) {
					this.vresize = false;
				} else if (this.colCopy) {
					$(this.colCopy).remove();
					if (this.dcolt != null) {

						if (this.dcoln > this.dcolt)
							$('th:eq(' + this.dcolt + ')', this.hDiv)
									.before(this.dcol);
						else
							$('th:eq(' + this.dcolt + ')', this.hDiv)
									.after(this.dcol);

						this.switchCol(this.dcoln, this.dcolt);
						$(this.cdropleft).remove();
						$(this.cdropright).remove();
						this.rePosDrag();

						if (p.onDragCol)
							p.onDragCol(this.dcoln, this.dcolt);

					}

					this.dcol = null;
					this.hset = null;
					this.dcoln = null;
					this.dcolt = null;
					this.colCopy = null;

					$('.thMove', this.hDiv).removeClass('thMove');
					$(this.cDrag).show();
				}
				$('body').css('cursor', 'default');
				$('body').noSelect(false);
			},
			toggleCol : function(cid, visible) {

				var ncol = $("th[axis='col" + cid + "']", this.hDiv)[0];
				var n = $('thead th', g.hDiv).index(ncol);
				var cb = $('input[value=' + cid + ']', g.nDiv)[0];

				if (visible == null) {
					visible = ncol.hide;
				}

				if ($('input:checked', g.nDiv).length < p.minColToggle
						&& !visible)
					return false;

				if (visible) {
					ncol.hide = false;
					$(ncol).show();
					cb.checked = true;
				} else {
					ncol.hide = true;
					$(ncol).hide();
					cb.checked = false;
				}

				$('tbody tr', t).each(function() {
							if (visible)
								$('td:eq(' + n + ')', this).show();
							else
								$('td:eq(' + n + ')', this).hide();
						});

				this.rePosDrag();

				if (p.onToggleCol)
					p.onToggleCol(cid, visible);

				return visible;
			},
			switchCol : function(cdrag, cdrop) { // switch columns

				$('tbody tr', t).each(function() {
					if (cdrag > cdrop)
						$('td:eq(' + cdrop + ')', this).before($('td:eq('
										+ cdrag + ')', this));
					else
						$('td:eq(' + cdrop + ')', this).after($('td:eq('
										+ cdrag + ')', this));
				});

				// switch order in nDiv
				if (cdrag > cdrop)
					$('tr:eq(' + cdrop + ')', this.nDiv).before($('tr:eq('
									+ cdrag + ')', this.nDiv));
				else
					$('tr:eq(' + cdrop + ')', this.nDiv).after($('tr:eq('
									+ cdrag + ')', this.nDiv));

				if ($.browser.msie && $.browser.version < 7.0)
					$('tr:eq(' + cdrop + ') input', this.nDiv)[0].checked = true;

				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
			},
			scroll : function() {
				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				this.rePosDrag();
			},

			ieTable : function(htmlContent) {
				if (!g.detachedDiv) {
					g.detachedDiv = document.createElement('div');
				}
				var detachedDiv = g.detachedDiv;
				detachedDiv.innerHTML = ['<table><tbody><tr>', htmlContent,
						'</tr><tbody><table>'].join('');

				var i = -1, depth = 4, el = detachedDiv, ns;

				while (++i < depth) {
					el = el.firstChild;
				}
				ns = el.nextSibling;

				if (ns) {
					el = document.createDocumentFragment();
					while (ns) {
						el.appendChild(ns);
						ns = ns.nextSibling;
					}
				}
				return el;
			},

			insertData : function(data) {
				// parse data
				var me = this;

				if (!data) {
					return false;
				}

				// 获取tbody
				$tbody = $('tbody', t);
				if ($tbody == undefined || $tbody == null || $tbody.length < 1) {
					$tbody = $("<tbody></tbody>");
					$(t).append($tbody);
				}
				// 修改json格式
				if (p.dataType == 'json') {
					var thArr = $('tr th', thead), thArrLen = thArr.length;

					var widthArr = new Array();
					for (var index = 0; index < thArrLen; index++) {
						widthArr.push($(thArr[index]).find('div').css('width'));
					}

					var tdTpl = [
							'<td style="text-align:{textAlign};overflow:hidden;{display}">',
							'<div name="{name}" style="width:{width};text-align:{textAlign};text-overflow:ellipsis;overflow:hidden">{html}</div>',
							'</td>'];
					var tplRe = /\{([\w\-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g;

					var rowData = data;
					var row;
					for (var i = 0; i < rowData.length; i++) {
						row = rowData[i];
						var tr = document.createElement('tr');

						if (i % 2 && p.striped)
							tr.className = 'erow';

						if (row.id)
							tr.id = 'row' + row.id;

						// by anson
						var tdVal = [];

						// 给每行添加id
						if (p.rowId) {
							tr.setAttribute('id', row[p.rowId]);
						}

						var trConfig = {
							trid : row[p.rowId],
							trClassName : (i % 2 && p.striped)
									? 'erow'
									: 'nocls'
						}

						// 将为每一行tr各个td填充内容，按照colModel的声明顺序放入数组tdVal中。
						if (p.colModel) {
							var j = 0, seleceName, subName, value, columns = p.colModel, len = columns.length;
							for (; j < len; j++) {
								// 取列名
								seleceName = columns[j].name;
								// 取子列名
								subName = columns[j].subname;

								
                                if(columns[j].value){
									value=columns[j].value;
								}else{
								    value = row[seleceName];
								}
								if (!value || value == "null")
									value = "";
                                
								/**
								 * 判断y是不是对象，如果是对象就再次进行遍历，在colModel中加入
								 * 属性subname，表示需要获取的对象中的属性。
								 */
								if (value && typeof(value) == 'object') {
									var n = value[subName];
									if (!n || n == "null")
										n = "";
									tdVal.push(n);
								} else {
									tdVal.push(value);
								}
							}
						}

						// add cells
						for (var k = 0; k < thArrLen; k++) {
							var pth = thArr[k];

							var tdConfig = {
								textAlign : pth.align,
								width : widthArr[k] == 'number'
										? (widthArr[k] + 'px')
										: widthArr[k],
								name : pth.attributes.name.value,
								html : tdVal[k] == '' ? '&nbsp;' : tdVal[k],
								display : pth.hide ? 'display:none' : ''
							};
							var rpFun = function(m, name, format, args) {
								return tdConfig[name];
							}
							var tdhtml = tdTpl.join('');
							tdhtml = tdhtml.replace(tplRe, rpFun);
							tr.appendChild(me.ieTable(tdhtml));
							tdConfig = tdhtml = null;
						}

						// handle if grid has no headers
						if ($('thead', this.gDiv).length < 1) {
							for (var idx = 0; idx < cell.length; idx++) {
								var td = document.createElement('td');
								td.innerHTML = tdVal[idx];
								$(tr).append(td);
								td = null;
							}
						}

						// 添加复选框
						if (p.checkbox) {

							var cth = $('<th/>');
							var cthch = null;
							if (!p.singleSelect) {
								cthch = $('<input type="checkbox" value="'
										+ $(tr).attr('id') + '"/>');
							} else {
								cthch = $('<input type="radio" value="'
										+ $(tr).attr('id') + '"/>');
							}

							var pTr;
							cthch.addClass("noborder").click(function() {
								pTr = $(this).parent().parent();
								if (p.singleSelect) {
									selectedCount = 0;
									$('tbody tr', g.bDiv).each(function() {
										$(this).removeClass('trSelected')
												.find('input')[0].checked = false;
									});
									pTr.addClass('trSelected');
									$(this).attr('checked', true);
									
									if (p.onrowchecked) {
										if (p.relGrid) {
											ReloadFlex($(this).attr('value'),
													p.relGrid);
										} else {
											ReloadFlex($(this).attr('value'));
										}
									}
								} else {
									if (this.checked) {
										pTr.addClass('trSelected');
										if (p.onrowchecked) {
											if (p.relGrid) {
												ReloadFlex($(this)
																.attr('value'),
														p.relGrid);
											} else {
												ReloadFlex($(this)
														.attr('value'));
											}
										}
									} else {
										pTr.removeClass('trSelected');
									}
								}
							})

							cth.addClass("cth").append(cthch);
							$(tr).prepend(cth);
						}

						$tbody.append(tr);
						// 设置选中状态
						$(tr).dblclick(function(e) {
							// 双击时不改变行的选中状态
							var chb = $("input", this)[0];
							if (chb) {
								chb.checked = true;
							}
							$(this).addClass('trSelected');
							var rowData = new Object();
							$.each($(this).find('div'), function(i, item) {
										// $(rowData).data(p.colModel[i].name,$(this).text());
										$(rowData).data($(item).attr("name"),
												$(this).text());
									});
							if (p.onRowDblclick) {
								p.onRowDblclick($(rowData));
							}
						}).click(function(e) {
							var obj = (e.target || e.srcElement);
							if (obj.href || obj.type)
								return true;
							if (p.singleSelect) {
								selectedCount = 0;
								$('tbody tr', g.bDiv).each(function() {
									$(this).removeClass('trSelected')
											.find('input')[0].checked = false;
								})
							}
							$(this).toggleClass('trSelected');
							// 添加多选框
							if (p.checkbox) {
								if ($(this).hasClass('trSelected')) {
									// $(this).find('input')[0].checked = true;
									$(this).find('input')[0].click();
								} else {
									$(this).find('input')[0].checked = false;
									// 添加选择条目
									if (--selectedCount <= 0) {
									} else {
									}
								}
							}
						}).mousedown(function(e) {
									if (e.shiftKey) {
										$(this).toggleClass('trSelected');
										g.multisel = true;
										this.focus();
										$(g.gDiv).noSelect();
									}
								}).mouseup(function() {
									if (g.multisel) {
										g.multisel = false;
										$(g.gDiv).noSelect(false);
									}
								}).hover(function(e) {
									if (g.multisel) {
										$(this).toggleClass('trSelected');
									}
								}, function() {
								});
						if ($.browser.msie && $.browser.version < 7.0) {
							$(tr).hover(function() {
										$(this).addClass('trOver');
									}, function() {
										$(this).removeClass('trOver');
									});
						}
						tr = null;
					}
				} else if (p.dataType == 'xml') {

					i = 1;

					$("rows row", data).each(

					function() {

						i++;

						var tr = document.createElement('tr');
						if (i % 2 && p.striped)
							tr.className = 'erow';

						var nid = $(this).attr('id');
						if (nid)
							tr.id = 'row' + nid;

						nid = null;

						var robj = this;

						$('thead tr:first th', g.hDiv).each(function() {

							var td = document.createElement('td');
							var idx = $(this).attr('axis').substr(3);
							td.align = this.align;
							td.innerHTML = $("cell:eq(" + idx + ")", robj)
									.text();
							$(tr).append(td);
							td = null;
						});

						if ($('thead', this.gDiv).length < 1) // handle if
						// grid has no
						// headers
						{
							$('cell', this).each(function() {
										var td = document.createElement('td');
										td.innerHTML = $(this).text();
										$(tr).append(td);
										td = null;
									});
						}

						$(tbody).append(tr);

						tr = null;
						robj = null;
					});

				}

			},

			addData : function(data) { // parse data
			
				
				var me = this;
				if (p.preProcess)
					data = p.preProcess(data);

				$('.pReload', this.pDiv).removeClass('loading');
				this.loading = false;

                if (!data || data.message) {
                    $('.pPageStat', this.pDiv).html(p.errormsg + "：" + data.searchMsg);
                    return false;
                }

				if (p.dataType == 'xml')
					p.total = +$('rows total', data).text();
				else
					p.total = data.total;
				// 使用分页栏则设置total,否则无
				if (p.usepager) {
					if (p.total == 0) {
						$('tr, a, td, div', t).unbind();
						$(t).empty();
						p.pages = 1;
						p.page = 1;
						this.buildpager();
						$('.pPageStat', this.pDiv).html(p.nomsg);
						if (p.hideOnSubmit)
							$(g.block).remove();// $(t).show();
						return false;
					}
				}

				p.pages = Math.ceil(p.total / p.rp);

				if (p.dataType == 'xml')
					p.page = +$('rows page', data).text();
				else
					p.page = data.page;

				if (p.usepager)
					this.buildpager();

				// build new body
				var tbody = document.createElement('tbody');

				// 修改json格式
				if (p.dataType == 'json') {
					var thArr = $('tr th', thead), thArrLen = thArr.length;
					
				

					var widthArr = new Array();
					for (var index = 0; index < thArrLen; index++) {
						widthArr.push($(thArr[index]).find('div').css('width'));
					}

					var tdTpl = [
							'<td style="text-align:{textAlign};overflow:hidden;{display}">',
							'<div name="{name}" style="width:{width};text-align:{textAlign};text-overflow:ellipsis;overflow:hidden">{html}</div>',
							'</td>'];
					var tplRe = /\{([\w\-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g;
					var dataRows = data.rows;
					var row;
					for (var i = 0; i < dataRows.length; i++) {
						row = dataRows[i];
						var tr = document.createElement('tr');

						if (i % 2 && p.striped)
							tr.className = 'erow';

						if (row.id)
							tr.id = 'row' + row.id;

						// by anson
						var tdVal = [];

						// 给每行添加id
						if (p.rowId) {
							tr.setAttribute('id', row[p.rowId]);
						}

						var trConfig = {
							trid : row[p.rowId],
							trClassName : (i % 2 && p.striped)
									? 'erow'
									: 'nocls'
						}

						// 将为每一行tr各个td填充内容，按照colModel的声明顺序放入数组tdVal中。
						if (p.colModel) {
							var j = 0, seleceName, subName, value, columns = p.colModel, len = columns.length;
							for (; j < len; j++) {
								// 取列名
								seleceName = columns[j].name;
								
								
								// 取子列名
								subName = columns[j].subname;
                                if(columns[j].value){
									value=columns[j].value;
								}
								if(columns[j].operateColumn){
									$('<div style="display:none;" id="flexigrid_customize_temp_hidden">').appendTo($('body'));
									var operateList=columns[j].operateColumn;
                                	var operateHtml="";
									for(var operateIndex=0;operateIndex<operateList.length;operateIndex++){
										if(operateList[operateIndex].html){
										  var operateHtmlTemp=operateList[operateIndex].html;
										  var $temp=$(operateHtmlTemp).attr("lineIndex",i);
										  $temp.appendTo("#flexigrid_customize_temp_hidden");
										  operateHtml+=$("#flexigrid_customize_temp_hidden").html()+' | ';
										  $("#flexigrid_customize_temp_hidden").html("");//清空临时变量
									    }
									}
									operateHtml = operateHtml.substr(0, operateHtml.length - 2);
									value=operateHtml;
								}
								
								else{
								    value = row[seleceName];
								}
								if (!value || value == "null")
									value = "";
								/**
								 * 判断y是不是对象，如果是对象就再次进行遍历，在colModel中加入
								 * 属性subname，表示需要获取的对象中的属性。
								 */
				
								
								if (value && typeof(value) == 'object') {
									var n = value[subName];
									if (!n || n == "null")
										n = "";
									tdVal.push(n);
								} else {
									
									///添加formatter处理
									if(null!=p.colModel[j].formatter && typeof(p.colModel[j].formatter)=='function'){
										value=p.colModel[j].formatter(value);
									}
									
									tdVal.push(value);
								}
							}
						}

						// add cells
						for (var k = 0; k < thArrLen; k++) {
							var pth = thArr[k];
							
							var tdConfig = {
								textAlign : pth.align,
								width : widthArr[k] == 'number'
										? (widthArr[k] + 'px')
										: widthArr[k],
								name : pth.attributes.name.value,
								html : tdVal[k] == '' ? '&nbsp;' : tdVal[k],
								display : pth.hide ? 'display:none' : ''
							};
							var rpFun = function(m, name, format, args) {
								return tdConfig[name];
							};
							// if (p.sortname && (p.sortname ==
							// pth.getAttribute('abbr'))) {
							// td.className = 'sorted';
							// }
							// if (p.nowrap == false)
							// tdDivObj.css('white-space', 'normal');
							// if (pth.process && tr.id){
							// var pid = tr.id.substr(3);
							// pth.process(tdDiv, pid);
							// }
							var tdhtml = tdTpl.join('');
							tdhtml = tdhtml.replace(tplRe, rpFun);
							tr.appendChild(me.ieTable(tdhtml));
							tdConfig = tdhtml = null;
						}

						// handle if grid has no headers
						if ($('thead', this.gDiv).length < 1) {
							for (idx = 0; idx < cell.length; idx++) {
								var td = document.createElement('td');
								td.innerHTML = tdVal[idx];
								$(tr).append(td);
								td = null;
							}
						}

						// 添加复选框
						if (p.checkbox) {

							var cth = $('<th/>');
							var cthch = null;
							var hiddenHtml="";
							if (!p.singleSelect) {
								cthch = $('<input type="checkbox" value="'
										+ $(tr).attr('id') + '"/>');
								if(p.hiddenArea){//添加隐藏区域
									 for (var hiddenAreaIndex = 0; hiddenAreaIndex < p.hiddenArea.length; hiddenAreaIndex++) {
									 	var hiddenAreaHtml='<input type="hidden" ';
									 	var hiddenAreaName,hiddenAreaValue,hiddenAreaId;
										var hiddenAreaObject=p.hiddenArea[hiddenAreaIndex];
										if(hiddenAreaObject.name){
											hiddenAreaName=hiddenAreaObject.name;
											hiddenAreaHtml+=' name='+hiddenAreaName;
											if (hiddenAreaObject.value) {
											    hiddenAreaValue=hiddenAreaObject.value;
										    }else{
											    hiddenAreaValue = row[hiddenAreaName];
										    }
											hiddenAreaHtml+=' value='+hiddenAreaValue; 
										}if (hiddenAreaObject.id) {
										    hiddenAreaId=hiddenAreaObject.id;
											hiddenAreaHtml+=' id='+hiddenAreaId; 
										}
										hiddenAreaHtml+=" />"; 
										hiddenHtml+=hiddenAreaHtml;
									}
							  }
							} else {
								cthch = $('<input type="radio" value="'
										+ $(tr).attr('id') + '"/>');
								if(p.hiddenArea){//添加隐藏区域
									 for (var hiddenAreaIndex = 0; hiddenAreaIndex < p.hiddenArea.length; hiddenAreaIndex++) {
									 	var hiddenAreaHtml='<input type="hidden" ';
									 	var hiddenAreaName,hiddenAreaValue,hiddenAreaId;
										var hiddenAreaObject=p.hiddenArea[hiddenAreaIndex];
										if(hiddenAreaObject.name){
											hiddenAreaName=hiddenAreaObject.name;
											hiddenAreaHtml+=' name='+hiddenAreaName;
											if (hiddenAreaObject.value) {
											    hiddenAreaValue=hiddenAreaObject.value;
										    }else{
											    hiddenAreaValue = row[hiddenAreaName];
										    }
											hiddenAreaHtml+=' value='+hiddenAreaValue; 
										}if (hiddenAreaObject.id) {
										    hiddenAreaId=hiddenAreaObject.id;
											hiddenAreaHtml+=' id='+hiddenAreaId; 
										}
										hiddenAreaHtml+=" />"; 
										hiddenHtml+=hiddenAreaHtml;
									}
							  }
							}

							var pTr;
							cthch.addClass("noborder").click(function() {
								pTr = $(this).parent().parent();
								if (p.singleSelect) {
									if (p.singleSelect) {
										selectedCount = 0;
										$('tbody tr', g.bDiv).each(function() {
											$(this).removeClass('trSelected')
													.find('input')[0].checked = false;
										})
										pTr.addClass('trSelected');
										$(this).attr('checked', true);
									}
									if (p.onrowchecked) {
										if (p.relGrid) {
											ReloadFlex($(this).attr('value'),
													p.relGrid);
										} else {
											ReloadFlex($(this).attr('value'));
										}
									}
								} else {
									if (this.checked) {
										pTr.addClass('trSelected');
										if (p.onrowchecked) {
											if (p.relGrid) {
												ReloadFlex($(this)
																.attr('value'),
														p.relGrid);
											} else {
												ReloadFlex($(this)
														.attr('value'));
											}
										}
									} else {
										pTr.removeClass('trSelected');
									}
								}
							});

							cth.addClass("cth").append(cthch);
							if(hiddenHtml!=""){
								$(tr).prepend(hiddenHtml);
							}
							$(tr).prepend(cth);
						}

						$(tbody).append(tr);
						tr = null;
					}
				} else if (p.dataType == 'xml') {

					i = 1;

					$("rows row", data).each(

					function() {

						i++;

						var tr = document.createElement('tr');
						if (i % 2 && p.striped)
							tr.className = 'erow';

						var nid = $(this).attr('id');
						if (nid)
							tr.id = 'row' + nid;

						nid = null;

						var robj = this;

						$('thead tr:first th', g.hDiv).each(function() {

							var td = document.createElement('td');
							var idx = $(this).attr('axis').substr(3);
							td.align = this.align;
							td.innerHTML = $("cell:eq(" + idx + ")", robj)
									.text();
							$(tr).append(td);
							td = null;
						});

						if ($('thead', this.gDiv).length < 1) // handle if
						// grid has no
						// headers
						{
							$('cell', this).each(function() {
										var td = document.createElement('td');
										td.innerHTML = $(this).text();
										$(tr).append(td);
										td = null;
									});
						}

						$(tbody).append(tr);
						tr = null;
						robj = null;
					});

				}

				// 性能标记: 从此至函数结束, IE7 200行 1500ms
				$('tr', t).unbind();
				$(t).empty();

				$(t).append(tbody);

				this.addRowProp();
				// this.fixHeight($(this.bDiv).height());
				this.rePosDrag();

				tbody = null;
				data = null;
				i = null;

				if (p.onSuccess)
					p.onSuccess();
				if (p.hideOnSubmit)
					$(g.block).remove();// $(t).show();

				this.hDiv.scrollLeft = this.bDiv.scrollLeft;
				if ($.browser.opera)
					$(t).css('visibility', 'visible');
			},
			changeSort : function(th) { // change sortorder

				if (!p.pages)
					return false;

				if (this.loading)
					return true;

				$(g.nDiv).hide();
				$(g.nBtn).hide();

				if (p.sortname == $(th).attr('abbr')) {
					if (p.sortorder == 'asc')
						p.sortorder = 'desc';
					else
						p.sortorder = 'asc';
				}

				$(th).addClass('sorted').siblings().removeClass('sorted');
				$('.sdesc', this.hDiv).removeClass('sdesc');
				$('.sasc', this.hDiv).removeClass('sasc');
				$('div', th).addClass('s' + p.sortorder);
				p.sortname = $(th).attr('abbr');

				if (p.onChangeSort)
					p.onChangeSort(p.sortname, p.sortorder);
				else if (p.onFrontSort) {
					this.frontSort($(th).attr('axis').substr(3));
				} else
					this.populate();

			},
			buildpager : function() { // rebuild pager based on new properties

				$('.pcontrol input', this.pDiv).val(p.page);
				$('.pcontrol span', this.pDiv).html(p.pages);

				var r1 = (p.page - 1) * p.rp + 1;
				var r2 = r1 + p.rp - 1;

				if (p.total < r2)
					r2 = p.total;

				var stat = p.pagestat;

				stat = stat.replace(/{from}/, r1);
				stat = stat.replace(/{to}/, r2);
				stat = stat.replace(/{total}/, p.total);

				$('.pPageStat', this.pDiv).html(stat);

			},
			// fleigrid填充函数，将rows中的数据信息填入grid中。
			populate : function(page) { // get latest data
				if (!p.url)
					return false;

				if (this.loading)
					return true;

				if (p.onSubmit) {
					var gh = p.onSubmit();
					if (!gh)
						return false;
				}

				this.loading = true;
				// if (!p.url)
				// return false;
				if (p.usepager) {
					// if(!p.pages)return false;
					$('.pPageStat', this.pDiv).html(p.procmsg);
					$('.pReload', this.pDiv).addClass('loading');
				}

				$(g.block).css({
							top : g.bDiv.offsetTop
						});

				if (p.hideOnSubmit)
					$(this.gDiv).prepend(g.block); // $(t).hide();

				if ($.browser.opera)
					$(t).css('visibility', 'hidden');

				if (!p.newp)
					p.newp = 1;
				if (p.page > p.pages) 
					p.page = p.pages;
				// var param = {page:p.newp, rp: p.rp, sortname: p.sortname,
				// sortorder: p.sortorder, query: p.query, qtype: p.qtype};
				var param = [{
							name : 'page',
							value : p.newp
						}, {
							name : 'rp',
							value : p.rp
						}, {
							name : 'sortname',
							value : p.sortname
						}, {
							name : 'sortorder',
							value : p.sortorder
						}, {
							name : 'query',
							value : p.query
						}, {
							name : 'qtype',
							value : p.qtype
						}, {
							name : 'usePager',
							value : p.usepager
						},/*
							 * { name : 'formData',value
							 * :$("#actionForm").formSerialize() },
							 */
						{
							name : 'sortFlag',
							value : '1'
						}];

				if (p.params) {
					for (var pi = 0; pi < p.params.length; pi++){
						param[param.length] = p.params[pi];
					}	
				}

				var formData = '';
				if (p.dataForm) {
					formData = $("#" + p.dataForm).formSerialize();
				} else {
					formData = $('form').formSerialize();
				}
				
				var paramData = '';
				for (var pi = 0; pi < param.length; pi++){
					if(!param[pi].value){
						continue;
					}
					if(pi ==  param.length){
						paramData += param[pi].name+'='+ param[pi].value;
					}else{
						paramData += param[pi].name+'='+ param[pi].value+'&';
					}
				}	
				if(formData == ''){
					formData = paramData;
				}else{
					formData = formData+'&'+paramData;
				}
				formData = decodeURIComponent(formData);
				//var flag = false;
				//var key;
				//var value;
				//var formDataArray = new Array;
				//formDataArray = decodeURIComponent(formData).split("&");
				/*for (var i = 0; i < formDataArray.length; i++) {
					key = formDataArray[i].split("=")[0];
					value = formDataArray[i].split("=")[1];
					$.each(param, function(index, item) {
								if (item.name == key) {
									item.value = value;
									flag = true;
								}
							});
					if (!flag) {
						param[param.length] = {
							name : key,
							value : value
						};
					}
				}*/
				var requestUrl=p.url;
				if (page) {
					if(requestUrl.indexOf("?")!=-1){
						requestUrl+="&page="+page;
					}else{
						requestUrl+="?page="+page;
					}
				}
				$.ajax({
							type : p.method,
							url : requestUrl,
							data : formData,
							dataType : p.dataType,
							success : function(data) {
								g.addData(data);
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert(errorThrown);
								try {
									if (p.onError)
										p.onError(XMLHttpRequest, textStatus,
												errorThrown);
								} catch (e) {
								}
							}
						});
			},

			print : function() { // print data

				alert("print data");
			},
			excel : function() { // export data to excel

				$(g.hDiv).find("div").find("th");
				var headLen = $(g.hDiv).find("div").find("th").length;
				// alert($(g.hDiv).find("div").find("th:hidden").length);
				var header = $(g.hDiv).find("div").find("th");
				var columnLen = $(g.bDiv).find("tr:first").find("td").length;
				var rowLen = $(g.bDiv).find("tbody").find("tr").length;
				var oXL = new ActiveXObject("Excel.Application");
				// Get a new workbook.
				var oWB = oXL.Workbooks.Add();
				var oSheet = oWB.ActiveSheet;
				var $table = $(g.bDiv).find("table");
				var table = $table[0];
				var m = 1;
				// Add table headers going cell by cell.
				for (var j = 0, index = 0; j < headLen; j++, index++) {
					if ($(header[j]).css("display") == 'none') {
						index--;
						continue;
					} else {
						oSheet.Cells(1, index + 1).Value = $(header[j]).text();
					}
				}
				// Add table contents going cell by cell.
				for (var i = 0; i < rowLen; i++) {
					m++;
					for (var j = 0, k = 0; j < columnLen; j++, k++) {
						if ($(table.rows(i).cells(j + 1)).css("display") == 'none') {
							k--;
							continue;
						} else {
							oSheet.Cells(m, k + 1).Value = table.rows(i)
									.cells(j + 1).innerText;
						}
					}
				}
				oXL.Visible = true;
				oXL.UserControl = true;

				// alert("export data");
			},
			doSearch : function() {
				p.query = $('input[name=q]', g.sDiv).val();
				p.qtype = $('select[name=qtype]', g.sDiv).val();
				p.newp = 1;

				this.populate();
			},
			// 分页函数
			changePage : function(ctype) { // change page
			    $("#checkbox_selecall_unselectall").attr("checked",false);//分页之前先取消 默认选中
				if (this.loading)
					return true;

				switch (ctype) {
					case 'first' :
						p.newp = 1;
						break;
					case 'prev' :
						if (p.page > 1)
							p.newp = parseInt(p.page) - 1;
						break;
					case 'next' :
						if (p.page < p.pages)
							p.newp = parseInt(p.page) + 1;
						break;
					case 'last' :
						p.newp = p.pages;
						break;
					case 'input' :
						var nv = parseInt($('.pcontrol input', this.pDiv).val());
						if (isNaN(nv))
							nv = 1;
						if (nv < 1)
							nv = 1;
						else if (nv > p.pages)
							nv = p.pages;
						$('.pcontrol input', this.pDiv).val(nv);
						p.newp = nv;
						break;
				}

				if (!p.pages)
					return false;

				if (p.newp == p.page)
					return false;

				if (p.onChangePage)
					p.onChangePage(p.newp);
				else
					this.populate();

			},

			frontSort : function(tdIndex) {
				sortTable(t.id, parseInt(tdIndex) + 1, 's');
			},

			addCellProp : function() {
				var g = this;

				$('tbody tr td', g.bDiv).each(function() {
							g.addTdCellProp(this);
						});
			},

			addTdCellProp : function(td) {
				var tdDiv = document.createElement('div');
				var n = $('td', $(td).parent()).index(td);
				var pth = $('th:eq(' + n + ')', g.hDiv).get(0);
				if (pth != null) {
					if (p.sortname == $(pth).attr('abbr') && p.sortname) {
						td.className = 'sorted';
					}
					$(tdDiv).css({
								textAlign : pth.align,
								width : $('div', pth)[0].style.width
							});
					$(tdDiv).attr("name", $(pth).attr('name'));
					// $(tdDiv).attr("name", $(pth).attr('abbr'));

					if (pth.hide)
						$(td).css('display', 'none');

				}

				if (p.nowrap == false)
					$(tdDiv).css('white-space', 'normal');

				if (td.innerHTML == '')
					td.innerHTML = '&nbsp;';

				// tdDiv.value = this.innerHTML; //store preprocess value
				tdDiv.innerHTML = td.innerHTML;
				var prnt = $(td).parent()[0];
				var pid = false;
				if (prnt.id)
					pid = prnt.id.substr(3);

				if (pth != null) {
					if (pth.process)
						pth.process(tdDiv, pid);
				}

				$(td).empty().append(tdDiv).removeAttr('width'); // wrap
				// content
				// add editable event here 'dblclick'
			},
			getCellDim : function(obj) // get cell prop for editable event
			{
				var ht = parseInt($(obj).height());
				var pht = parseInt($(obj).parent().height());
				var wt = parseInt(obj.style.width);
				var pwt = parseInt($(obj).parent().width());
				var top = obj.offsetParent.offsetTop;
				var left = obj.offsetParent.offsetLeft;
				var pdl = parseInt($(obj).css('paddingLeft'));
				var pdt = parseInt($(obj).css('paddingTop'));
				return {
					ht : ht,
					wt : wt,
					top : top,
					left : left,
					pdl : pdl,
					pdt : pdt,
					pht : pht,
					pwt : pwt
				};
			},
			addRowProp : function() {
				$('tbody tr', g.bDiv).each(function() {
					$(this).dblclick(function(e) {

						// 双击时不改变行的选中状态
						var chb = $("input", this)[0];
						if (chb) {
							chb.checked = true;
						}
						$(this).addClass('trSelected');
						var rowData = new Object();
						$.each($(this).find('div'), function(i, item) {
									// $(rowData).data(p.colModel[i].name,$(this).text());
									$(rowData).data($(item).attr("name"),
											$(this).text());
								});
						if (p.onRowDblclick) {
							p.onRowDblclick($(rowData));
						}

					})

					.click(function(e) {
						var obj = (e.target || e.srcElement);
						if (obj.href || obj.type)
							return true;
						if (p.singleSelect) {
							selectedCount = 0;
							$('tbody tr', g.bDiv).each(function() {
								$(this).removeClass('trSelected').find('input')[0].checked = false;
							})
						}
						$(this).toggleClass('trSelected');
						// 添加多选框
						if (p.checkbox) {
							if ($(this).hasClass('trSelected')) {
								$(this).find('input')[0].checked = true;
								// $(this).find('input')[0].click();
								// 添加选择条目
								// $(".tDiv1
								// span",this.tDiv).html(++selectedCount+"条被选择");
							} else {
								$(this).find('input')[0].checked = false;
								// 添加选择条目
								if (--selectedCount <= 0) {
									// $(".tDiv1
									// span",this.tDiv).html("");
								} else {
									// $(".tDiv1
									// span",this.tDiv).html(selectedCount+"条被选择");
								}

							}
						}

						if (p.singleSelect) {

							if ($(this).hasClass('trSelected')) {
								// $(this).find('input')[0].checked = true;
								$(this).find('input')[0].click();
								// 添加选择条目
								// $(".tDiv1
								// span",this.tDiv).html(++selectedCount+"条被选择");
							} else {
								$(this).find('input')[0].checked = false;
								$(this).siblings().removeClass('trSelected');
								// 添加选择条目
								if (--selectedCount <= 0) {
									// $(".tDiv1
									// span",this.tDiv).html("");
								} else {
									// $(".tDiv1
									// span",this.tDiv).html(selectedCount+"条被选择");
								}

							}

							// $(this).siblings()
							// .removeClass('trSelected');

						}
					}).mousedown(function(e) {
								if (e.shiftKey) {
									$(this).toggleClass('trSelected');
									g.multisel = true;
									this.focus();
									$(g.gDiv).noSelect();
								}
							}).mouseup(function() {
								if (g.multisel) {
									g.multisel = false;
									$(g.gDiv).noSelect(false);
								}
							}).hover(function(e) {
								if (g.multisel) {
									$(this).toggleClass('trSelected');
								}
							}, function() {
							});

					if ($.browser.msie && $.browser.version < 7.0) {
						$(this).hover(function() {
									$(this).addClass('trOver');
								}, function() {
									$(this).removeClass('trOver');
								});
					}
				});

			},
			pager : 0
		};

		// create model if any
		if (p.colModel) {
			thead = document.createElement('thead');
			tr = document.createElement('tr');

			// var th = document.createElement('th');
			// th.innerHTML = '序号';
			// $(tr).append(th);

			for (var i = 0; i < p.colModel.length; i++) {
				var cm = p.colModel[i];
				var th = document.createElement('th');

				th.innerHTML = cm.display;

				if (cm.alias && cm.sortable) {

					$(th).attr('abbr', cm.alias);
				} else if (cm.subname && cm.sortable) {

					$(th).attr('abbr', cm.subname);
				} else if (cm.name && cm.sortable) {

					$(th).attr('abbr', cm.name);
				}

				if (cm.name) {
					if (cm.subname) {
						$(th).attr('name', cm.name + "_" + cm.subname);
					} else {
						$(th).attr('name', cm.name);
					}
				}
				// th.idx = i;
				$(th).attr('axis', 'col' + i);

				if (cm.align)
					th.align = cm.align;

				if (cm.width)
					$(th).attr('width', cm.width);

				if (cm.hide) {
					th.hide = true;
				}

				if (cm.process) {
					th.process = cm.process;
				}

				$(tr).append(th);
			}
			$(thead).append(tr);
			$(t).prepend(thead);
		} // end if p.colmodel

		var selectedCount = 0;
		// init divs
		g.gDiv = document.createElement('div'); // create global container
		g.mDiv = document.createElement('div'); // create title container
		g.hDiv = document.createElement('div'); // create header container
		g.bDiv = document.createElement('div'); // create body container
		g.vDiv = document.createElement('div'); // create grip
		g.rDiv = document.createElement('div'); // create horizontal resizer
		g.cDrag = document.createElement('div'); // create column drag
		g.block = document.createElement('div'); // creat blocker
		g.nDiv = document.createElement('div'); // create column show/hide popup
		g.nBtn = document.createElement('div'); // create column show/hide
		// button
		g.iDiv = document.createElement('div'); // create editable layer
		g.tDiv = document.createElement('div'); // create toolbar
		g.sDiv = document.createElement('div');

		if (p.usepager)
			g.pDiv = document.createElement('div'); // create pager container
		g.hTable = document.createElement('table');

		// set gDiv
		g.gDiv.className = 'flexigrid';
		if (p.width != 'auto') {
			if (p.width.toString().indexOf('%') > 0)
				g.gDiv.style.width = p.width;
			else
				g.gDiv.style.width = p.width + 'px';
		}
		// g.gDiv.style.width = p.width + 'px';

		// add conditional classes
		if ($.browser.msie)
			$(g.gDiv).addClass('ie');

		if (p.novstripe)
			$(g.gDiv).addClass('novstripe');

		$(t).before(g.gDiv);
		$(g.gDiv).append(t);

		// set toolbar
		if (p.buttons) {
			g.tDiv.className = 'tDiv';
			var tDiv2 = document.createElement('div');
			// var tDiv1 = document.createElement('div');
			tDiv2.className = 'tDiv2';
			// tDiv1.className = 'tDiv1';
			// tDiv1.innerHTML = '<div class="fbutton"><span></span></div>';

			for (i = 0; i < p.buttons.length; i++) {
				var btn = p.buttons[i];
				if (!btn.separator) {
					var btnDiv = document.createElement('div');
					btnDiv.className = 'fbutton';
					btnDiv.innerHTML = "<div><span>" + btn.name
							+ "</span></div>";
					if (btn.bclass)
						$('span', btnDiv).addClass(btn.bclass).css({
									paddingLeft : 20
								});
					btnDiv.onpress = btn.onpress;
					btnDiv.name = btn.name;
					if (btn.onpress) {
						$(btnDiv).click(function() {
									this.onpress(this.name, g.gDiv);
								});
					}
					$(tDiv2).append(btnDiv);
					if ($.browser.msie && $.browser.version < 7.0) {
						$(btnDiv).hover(function() {
									$(this).addClass('fbOver');
								}, function() {
									$(this).removeClass('fbOver');
								});
					}

				} else {
					$(tDiv2).append("<div class='btnseparator'></div>");
				}
			}
			// $(g.tDiv).append(tDiv1);
			$(g.tDiv).append(tDiv2);
//			$(g.tDiv).append("<div class=\"leftRound\"></div>");// add by wangqiang
//			$(g.tDiv).append("<h1 class=\"roundTitle\">搜索结果</h1>");// add by wangqiang
			$(g.tDiv).append("<div style='clear:both'></div>");
			$(g.gDiv).prepend(g.tDiv);
		}

		// set hDiv
		g.hDiv.className = 'hDiv';

		$(t).before(g.hDiv);

		// set hTable
		g.hTable.cellPadding = 0;
		g.hTable.cellSpacing = 0;
		$(g.hDiv).append('<div class="hDivBox"></div>');
		$('div', g.hDiv).append(g.hTable);
		var thead = $("thead:first", t).get(0);
		if (thead)
			$(g.hTable).append(thead);
		// thead = null;

		if (!p.colmodel)
			var ci = 0;

		// setup thead
		$('thead tr:first th', g.hDiv).each(function() {
			var thdiv = document.createElement('div');

			if ($(this).attr('abbr')) {
				$(this).click(function(e) {

							if (!$(this).hasClass('thOver'))
								return false;
							var obj = (e.target || e.srcElement);
							if (obj.href || obj.type)
								return true;
							g.changeSort(this);
						});

				if ($(this).attr('abbr') == p.sortname) {
					this.className = 'sorted';
					thdiv.className = 's' + p.sortorder;
				}
			}

			if (this.hide)
				$(this).hide();

			if (!p.colmodel) {
				$(this).attr('axis', 'col' + ci++);
			}
			$(thdiv).css({
						// textAlign : this.align,
						textAlign : p.titleAlign,
						width : this.width + 'px'
					});
			thdiv.innerHTML = this.innerHTML;

			$(this).empty().append(thdiv).removeAttr('width').mousedown(
					function(e) {
						g.dragStart('colMove', e, this);
					}).hover(function() {
				if (!g.colresize && !$(this).hasClass('thMove') && !g.colCopy)
					$(this).addClass('thOver');

				if ($(this).attr('abbr') != p.sortname && !g.colCopy
						&& !g.colresize && $(this).attr('abbr'))
					$('div', this).addClass('s' + p.sortorder);
				else if ($(this).attr('abbr') == p.sortname && !g.colCopy
						&& !g.colresize && $(this).attr('abbr')) {
					var no = '';
					if (p.sortorder == 'asc')
						no = 'desc';
					else
						no = 'asc';
					$('div', this).removeClass('s' + p.sortorder).addClass('s'
							+ no);
				}

				if (g.colCopy) {
					var n = $('th', g.hDiv).index(this);

					if (n == g.dcoln)
						return false;

					if (n < g.dcoln)
						$(this).append(g.cdropleft);
					else
						$(this).append(g.cdropright);

					g.dcolt = n;

				} else if (!g.colresize) {
					var nv = $('th:visible', g.hDiv).index(this);
					var onl = parseInt($('div:eq(' + nv + ')', g.cDrag)
							.css('left'));
					var nw = jQuery(g.nBtn).outerWidth();
					nl = onl - nw + Math.floor(p.cgwidth / 2);

					$(g.nDiv).hide();
					$(g.nBtn).hide();

					// 不显示 显示/隐藏 列 2012-04-06
					$(g.nBtn).css({
								'left' : nl,
								top : g.hDiv.offsetTop
							}).show();

					var ndw = parseInt($(g.nDiv).width());

					$(g.nDiv).css({
								top : g.bDiv.offsetTop
							});

					if ((nl + ndw) > $(g.gDiv).width())
						$(g.nDiv).css('left', onl - ndw + 1);
					else
						$(g.nDiv).css('left', nl);

					if ($(this).hasClass('sorted'))
						$(g.nBtn).addClass('srtd');
					else
						$(g.nBtn).removeClass('srtd');

				}

			}, function() {
				$(this).removeClass('thOver');
				if ($(this).attr('abbr') != p.sortname)
					$('div', this).removeClass('s' + p.sortorder);
				else if ($(this).attr('abbr') == p.sortname) {
					var no = '';
					if (p.sortorder == 'asc')
						no = 'desc';
					else
						no = 'asc';

					$('div', this).addClass('s' + p.sortorder).removeClass('s'
							+ no);
				}
				if (g.colCopy) {
					$(g.cdropleft).remove();
					$(g.cdropright).remove();
					g.dcolt = null;
				}
			}); // wrap content
		});

		// set bDiv
		g.bDiv.className = 'bDiv';
		$(t).before(g.bDiv);
		$(g.bDiv).css({
					height : (p.height == 'auto') ? 'auto' : p.height + "px"
				}).scroll(function(e) {
					g.scroll()
				}).append(t);

		if (p.height == 'auto') {
			$('table', g.bDiv).addClass('autoht');
		}

		// add td properties
		g.addCellProp();

		// add row properties
		g.addRowProp();

		// set cDrag

		var cdcol = $('thead tr:first th:first', g.hDiv).get(0);

		if (cdcol != null) {
			g.cDrag.className = 'cDrag';
			g.cdpad = 0;

			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderLeftWidth')))
					? 0
					: parseInt($('div', cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('borderRightWidth')))
					? 0
					: parseInt($('div', cdcol).css('borderRightWidth')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingLeft')))
					? 0
					: parseInt($('div', cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($('div', cdcol).css('paddingRight')))
					? 0
					: parseInt($('div', cdcol).css('paddingRight')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderLeftWidth')))
					? 0
					: parseInt($(cdcol).css('borderLeftWidth')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('borderRightWidth')))
					? 0
					: parseInt($(cdcol).css('borderRightWidth')));

			if ($.browser.msie && $.browser.version == 8.0) {
				$(cdcol).css('paddingLeft', 0).css('paddingRight', 0);
			}
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingLeft')))
					? 0
					: parseInt($(cdcol).css('paddingLeft')));
			g.cdpad += (isNaN(parseInt($(cdcol).css('paddingRight')))
					? 0
					: parseInt($(cdcol).css('paddingRight')));

			$(g.bDiv).before(g.cDrag);

			var cdheight = $(g.bDiv).height();
			var hdheight = $(g.hDiv).height();

			$(g.cDrag).css({
						top : -hdheight + 'px'
					});

			$('thead tr:first th', g.hDiv).each(function() {
						var cgDiv = document.createElement('div');
						$(g.cDrag).append(cgDiv);
						if (!p.cgwidth)
							p.cgwidth = $(cgDiv).width();
						$(cgDiv).css({
									height : cdheight + hdheight
								}).mousedown(function(e) {
									g.dragStart('colresize', e, this);
								});
						if ($.browser.msie && $.browser.version < 7.0) {
							g.fixHeight($(g.gDiv).height());
							$(cgDiv).hover(function() {
										g.fixHeight();
										$(this).addClass('dragging')
									}, function() {
										if (!g.colresize)
											$(this).removeClass('dragging')
									});
						}
					});

			// g.rePosDrag();

		}

		// add strip
		if (p.striped)
			$('tbody tr:odd', g.bDiv).addClass('erow');

		if (p.resizable && p.height != 'auto') {
			g.vDiv.className = 'vGrip';
			$(g.vDiv).mousedown(function(e) {
						g.dragStart('vresize', e)
					}).html('<span></span>');
			$(g.bDiv).after(g.vDiv);
		}

		if (p.resizable && p.width != 'auto' && !p.nohresize) {
			g.rDiv.className = 'hGrip';
			$(g.rDiv).mousedown(function(e) {
						g.dragStart('vresize', e, true);
					}).html('<span></span>').css('height', $(g.gDiv).height());
			if ($.browser.msie && $.browser.version < 7.0) {
				$(g.rDiv).hover(function() {
							$(this).addClass('hgOver');
						}, function() {
							$(this).removeClass('hgOver');
						});
			}
			$(g.gDiv).append(g.rDiv);
		}

		// add pager
		if (p.usepager) {
			g.pDiv.className = 'pDiv';
			g.pDiv.innerHTML = '<div id="pDiv2" class="pDiv2"></div>';
			$(g.bDiv).after(g.pDiv);
			var html = ''
			if (p.showpager) {
				html += ' <div class="pGroup"> <div class="pFirst pButton" title="第一页"><span></span></div><div class="pPrev pButton" title="上一页"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol">'
						+ p.pagetext
						+ ' <input type="text" size="2" value="1" /> '
						+ p.outof
						+ ' <span> 1 </span></span><span class="pcontrol"> 页 </span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton" title="下一页"><span></span></div><div class="pLast pButton" title="最后一页"><span></span></div> </div><div class="pGroup"><div ><span class="pPageStat"></span></div></div><div class="btnseparator"></div> ';
						//+ ' <span> 1 </span></span><span class="pcontrol"> 页 </span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton" title="下一页"><span></span></div><div class="pLast pButton" title="最后一页"><span></span></div> </div><div class="btnseparator"></div> ';
			}
			if (p.buttonAlign == 'right') {
				$("#pDiv2").removeClass("pDiv2");
				$("#pDiv2").css("float", "right");
				$("#pDiv2").css("margin", "3px");
			}
			html += ' ';
			if (p.toExcel) {
				html += '<div class="pGroup" title="导出为Excel" style="margin-rigth:5px;"> <div class="pExcel pButton"><span></span></div> </div><div class="btnseparator"></div>';
			}
			if (p.print) {
				html += '<div class="pGroup" title="打印" style="margin-right:5px;right:5px;"> <div class="pPrint pButton"><span></span></div> </div><div class="btnseparator"></div>';
			}

			$('div', g.pDiv).html(html);

			$('.pReload', g.pDiv).click(function() {
						g.populate()
					});
			$('.pPrint', g.pDiv).click(function() {
						g.print()
					});
			$('.pExcel', g.pDiv).click(function() {
						g.excel()
					});
			$('.pFirst', g.pDiv).click(function() {
						g.changePage('first')
					});
			$('.pPrev', g.pDiv).click(function() {
						g.changePage('prev')
					});
			$('.pNext', g.pDiv).click(function() {
						g.changePage('next')
					});
			$('.pLast', g.pDiv).click(function() {
						g.changePage('last')
					});
			$('.pcontrol input', g.pDiv).keydown(function(e) {
						if (e.keyCode == 13)
							g.changePage('input')
					});
			if ($.browser.msie && $.browser.version < 7)
				$('.pButton', g.pDiv).hover(function() {
							$(this).addClass('pBtnOver');
						}, function() {
							$(this).removeClass('pBtnOver');
						});

			if (p.useRp) {
				var opt = "";
				for (var nx = 0; nx < p.rpOptions.length; nx++) {
					if (p.rp == p.rpOptions[nx])
						sel = 'selected="selected"';
					else
						sel = '';
					opt += "<option value='" + p.rpOptions[nx] + "' " + sel
							+ " >" + p.rpOptions[nx] + "&nbsp;&nbsp;</option>";
				};
				$('#pDiv2', g.pDiv)
						.prepend("<div class='pGroup'><select name='rp' style='margin-top:5px;height:18px'>"
								+ opt
								+ "</select></div> <div class='btnseparator'></div>");
				$('select', g.pDiv).change(function() {
							if (p.onRpChange)
								p.onRpChange(+this.value);
							else {
								p.newp = 1;
								p.rp = +this.value;
								g.populate();
							}
						});
			}

			// add search button
			if (p.searchitems) {
				// $('.pDiv2', g.pDiv)
				// .prepend("<div class='pGroup'> <div class='pSearch
				// pButton'><span></span></div> </div> <div
				// class='btnseparator'></div>");
				$('.pSearch', g.pDiv).click(function() {
					$(g.sDiv).slideToggle('fast', function() {
						$('.sDiv:visible input:first', g.gDiv).trigger('focus');
					});
				});
				// add search box
				g.sDiv.className = 'sDiv';

				sitems = p.searchitems;

				var sopt = "";
				for (var s = 0; s < sitems.length; s++) {
					if (p.qtype == '' && sitems[s].isdefault == true) {
						p.qtype = sitems[s].name;
						sel = 'selected="selected"';
					} else
						sel = '';
					sopt += "<option value='" + sitems[s].name + "' " + sel
							+ " >" + sitems[s].display
							+ "&nbsp;&nbsp;</option>";
				}

				if (p.qtype == '')
					p.qtype = sitems[0].name;

				$(g.sDiv)
						.append("<div class='sDiv2'>"
								+ p.findtext
								+ " <input type='text' size='30' name='q' class='qsbox' /> <select name='qtype'>"
								+ sopt
								+ "</select> <!--input type='button' value='Clear' /--></div>");

				$('input[name=q],select[name=qtype]', g.sDiv).keydown(
						function(e) {
							if (e.keyCode == 13)
								g.doSearch()
						});
				$('input[value=Clear]', g.sDiv).click(function() {
							$('input[name=q]', g.sDiv).val('');
							p.query = '';
							g.doSearch();
						});
				$(g.bDiv).after(g.sDiv);

			}

		}
		$(g.pDiv, g.sDiv).append("<div style='clear:both'></div>");
		// $(g.tDiv).appendTo($(g.pDiv));
		// $(g.pDiv).append($(g.tDiv));

		// add title
		if (p.title) {
			g.mDiv.className = 'mDiv';
			g.mDiv.innerHTML = '<div class="ftitle">' + p.title + '</div>';
			$(g.gDiv).prepend(g.mDiv);
			if (p.showTableToggleBtn) {
				$(g.mDiv)
						.append('<div class="ptogtitle" title="收起/展开"><span></span></div>');
				$('div.ptogtitle', g.mDiv).click(function() {
							$(g.gDiv).toggleClass('hideBody');
							$(this).toggleClass('vsble');
						});
			}
			// g.rePosDrag();
		}

		// setup cdrops
		g.cdropleft = document.createElement('span');
		g.cdropleft.className = 'cdropleft';
		g.cdropright = document.createElement('span');
		g.cdropright.className = 'cdropright';

		// add block
		g.block.className = 'gBlock';
		var gh = $(g.bDiv).height();
		var gtop = g.bDiv.offsetTop;
		$(g.block).css({
					width : g.bDiv.style.width,
					height : gh,
					background : '#ccccff',
					position : 'relative',
					marginBottom : (gh * -1),
					zIndex : 99,
					top : gtop,
					left : '0px'
				});
		$(g.block).fadeTo(0, p.blockOpacity);

		// add column control
		if ($('th', g.hDiv).length) {

			g.nDiv.className = 'nDiv';
			g.nDiv.innerHTML = "<table cellpadding='0' cellspacing='0'><tbody></tbody></table>";
			$(g.nDiv).css({
						marginBottom : (gh * -1),
						display : 'none',
						top : gtop
					}).noSelect();

			var cn = 0;

			$('th div', g.hDiv).each(function() {
				var kcol = $("th[axis='col" + cn + "']", g.hDiv)[0];
				var chk = 'checked="checked"';
				if (kcol.style.display == 'none')
					chk = '';

				$('tbody', g.nDiv)
						.append('<tr><td class="ndcol1"><input type="checkbox" '
								+ chk
								+ ' class="togCol" value="'
								+ cn
								+ '" /></td><td class="ndcol2">'
								+ this.innerHTML + '</td></tr>');
				cn++;
			});

			// 添加多选框
			if (p.checkbox) {
				$('tr', g.hDiv).each(function() {
					var cth = $('<td/>');
					if (!p.singleSelect) {
						var cthch = $('<input type="checkbox" id="checkbox_selecall_unselectall"/>');
						cthch.click(function() {
									if (this.checked) {
										selectedCount = 0;
										$('tbody tr', g.bDiv).each(function() {
											$(this).addClass('trSelected')
													.find('input')[0].checked = true;
												// 显示选择条目
												// $('.tDiv1
												// span',g.tDiv).html(++selectedCount+"条被选择");
										})
									} else {
										// $('.tDiv1 span',g.tDiv).html("");
										$('tbody tr', g.bDiv).each(function() {
											$(this).removeClass('trSelected')
													.find('input')[0].checked = false;
										})
									}
								})

						cth.addClass("cth").append(cthch);
					} else {
						var cthch = $('<span style="width:20px;height:5px;display:inline-block"></span>');
						cth.addClass("cth").append(cthch);

					}
					$(this).prepend(cth);
				});
			};

			if ($.browser.msie && $.browser.version < 7.0)
				$('tr', g.nDiv).hover(function() {
							$(this).addClass('ndcolover');
						}, function() {
							$(this).removeClass('ndcolover');
						});

			$('td.ndcol2', g.nDiv).click(function() {
				if ($('input:checked', g.nDiv).length <= p.minColToggle
						&& $(this).prev().find('input')[0].checked)
					return false;
				return g.toggleCol($(this).prev().find('input').val());
			});

			$('input.togCol', g.nDiv).click(function() {

				if ($('input:checked', g.nDiv).length < p.minColToggle
						&& this.checked == false)
					return false;
				$(this).parent().next().trigger('click');
					// return false;
			});

			$(g.gDiv).prepend(g.nDiv);

			$(g.nBtn).addClass('nBtn').html('<div></div>').attr('title',
					'隐藏/显示 列').click(function() {
						$(g.nDiv).toggle();
						return true;
					});

			if (p.showToggleBtn)
				$(g.gDiv).prepend(g.nBtn);

		}

		// add date edit layer
		$(g.iDiv).addClass('iDiv').css({
					display : 'none'
				});
		$(g.bDiv).append(g.iDiv);

		// add flexigrid events
		$(g.bDiv).hover(function() {
					$(g.nDiv).hide();
					$(g.nBtn).hide();
				}, function() {
					if (g.multisel)
						g.multisel = false;
				});
		$(g.gDiv).hover(function() {
				}, function() {
					$(g.nDiv).hide();
					$(g.nBtn).hide();
				});

		// add document events
		$(document).mousemove(function(e) {
					g.dragMove(e)
				}).mouseup(function(e) {
					g.dragEnd()
				}).hover(function() {
				}, function() {
					g.dragEnd()
				});

		// browser adjustments
		if ($.browser.msie && $.browser.version < 7.0) {
			$('.hDiv,.bDiv,.mDiv,.pDiv,.vGrip,.tDiv, .sDiv', g.gDiv).css({
						width : '100%'
					});
			$(g.gDiv).addClass('ie6');
			if (p.width != 'auto')
				$(g.gDiv).addClass('ie6fullwidthbug');
		}

		g.rePosDrag();
		g.fixHeight();

		// make grid functions accessible
		t.p = p;
		t.grid = g;

		// load data
		if (p.url && p.autoload) {
			g.populate();
		}

		return t;

	};

	var docloaded = false;

	$(document).ready(function() {
				docloaded = true
			});

	$.fn.flexigrid = function(p) {
		return this.each(function() {
					if (!docloaded) {
						$(this).hide();
						var t = this;
						$(document).ready(function() {
									$.addFlex(t, p);
								});
					} else {
						$.addFlex(this, p);
					}
				});

	}; // end flexigrid

	$.fn.flexReload = function(p) { // function to reload grid

		return this.each(function() {
					if (this.grid && this.p.url)
						this.grid.populate();
				});

	}; // end flexReload

    $.fn.flexRefresh = function(p) { // function to reload grid
        $("#checkbox_selecall_unselectall").attr("checked",false);
		return this.each(function() {
					if (this.grid && this.p.url)
						this.grid.populate(1);//查询默认回到第一页
				});

	}; // end flexReload
	
	$.fn.flexOptions = function(p) { // function to update general options

		return this.each(function() {
					if (this.grid)
						$.extend(this.p, p);
				});

	}; // end flexOptions

	$.fn.flexToggleCol = function(cid, visible) { // function to reload grid

		return this.each(function() {
					if (this.grid)
						this.grid.toggleCol(cid, visible);
				});

	}; // end flexToggleCol

	$.fn.flexAddData = function(data) { // function to add data to grid

		return this.each(function() {
					if (this.grid)
						this.grid.addData(data);
				});

	};

	$.fn.noSelect = function(p) { // no select plugin by me :-)

		if (p == null)
			prevent = true;
		else
			prevent = p;

		if (prevent) {

			return this.each(function() {
						if ($.browser.msie || $.browser.safari)
							$(this).bind('selectstart', function() {
										return false;
									});
						else if ($.browser.mozilla) {
							$(this).css('MozUserSelect', 'none');
							$('body').trigger('focus');
						} else if ($.browser.opera)
							$(this).bind('mousedown', function() {
										return false;
									});
						else
							$(this).attr('unselectable', 'on');
					});

		} else {

			return this.each(function() {
						if ($.browser.msie || $.browser.safari)
							$(this).unbind('selectstart');
						else if ($.browser.mozilla)
							$(this).css('MozUserSelect', 'inherit');
						else if ($.browser.opera)
							$(this).unbind('mousedown');
						else
							$(this).removeAttr('unselectable', 'on');
					});

		}

	}; // end noSelect

	$.fn.flexInsertData = function(data) { // function to insert data to
											// grid,no refres
		return this.each(function() {
					if (this.grid)
						this.grid.insertData(data);
				});

	};

})(jQuery);
