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
	 * wrap树
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function wrapTree(jq) {
		var tree = $(jq);
		tree.addClass("tree");
		return tree;
	};
	/**
	 * 获取树数据
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 返回JSON
	 */
	function getTreeData(jq) {
		var data = [];
		collectTreeData(data, $(jq));
		/**
		 * 递归获取结点
		 * @param {Object} nodes 结点定义数组
		 * @param {Object} ul
		 * @memberOf {TypeName} 
		 */
		function collectTreeData(nodes, ul) {
			ul.children("li").each(function() {
				var li = $(this);
				var node = {};
				node.text = li.children("span").html();
				if (!node.text) {
					node.text = li.html();
				}
				node.id = li.attr("id");
				node.iconCls = li.attr("iconCls") || li.attr("icon");
				node.checked = li.attr("checked") == "true";
				node.state = li.attr("state") || "open";
				var uls = li.children("ul");
				if (uls.length) {
					node.children = [];
					collectTreeData(node.children, uls);
				}
				nodes.push(node);
			});
		};
		return data;
	};
	/**
	 * 事件绑定
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function bindEvent(jq) {
		var options = $.data(jq, "tree").options;
		var tree = $.data(jq, "tree").tree;
		//结点事件绑定
		$("div.tree-node", tree).unbind(".tree").bind("dblclick.tree",function() {//双击结点事件
			select(jq, this);
			options.onDblClick.call(jq, getSelected(jq));
		}).bind("click.tree", function() {//单击结点事件
			select(jq, this);
			options.onClick.call(jq, getSelected(jq));
		}).bind("mouseenter.tree", function() {//鼠标进入事件
			$(this).addClass("tree-node-hover");
			return false;
		}).bind("mouseleave.tree", function() {//鼠标离开事件
			$(this).removeClass("tree-node-hover");
			return false;
		}).bind("contextmenu.tree", function(e) {//右键菜单
			options.onContextMenu.call(jq, e, getNode(jq, this));
		});
		//文件加前展开/折叠图标事件绑定
		$("span.tree-hit", tree).unbind(".tree").bind("click.tree", function() {
			var parents = $(this).parent();
			toggle(jq, parents[0]);//切换展开/折叠节点的状态
			return false;
		}).bind("mouseenter.tree", function() {
			if ($(this).hasClass("tree-expanded")) {
				$(this).addClass("tree-expanded-hover");
			} else {
				$(this).addClass("tree-collapsed-hover");
			}
		}).bind("mouseleave.tree", function() {
			if ($(this).hasClass("tree-expanded")) {
				$(this).removeClass("tree-expanded-hover");
			} else {
				$(this).removeClass("tree-collapsed-hover");
			}
		}).bind("mousedown.tree", function() {
			return false;
		});
		//勾选框事件绑定
		$("span.tree-checkbox", tree).unbind(".tree").bind("click.tree",function() {
			var node = $(this).parent();
			setChecked(jq, node[0], !$(this).hasClass("tree-checkbox1"));
			return false;
		}).bind("mousedown.tree", function() {
			return false;
		});
	};
	/**
	 * 禁用拖放功能
	 * @param {Object} jq
	 */
	function disableDnd(jq) {
		var node = $(jq).find("div.tree-node");
		node.draggable("disable");
		node.css("cursor", "pointer");
	};
	/**
	 * 启用拖放功能
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function enableDnd(jq) {
		var options = $.data(jq, "tree").options;
		var tree = $.data(jq, "tree").tree;
		tree.find("div.tree-node").draggable({
			disabled : false,
			revert : true,
			cursor : "pointer",
			proxy : function(node) {
				var p = $("<div class=\"tree-node-proxy tree-dnd-no\"></div>").appendTo("body");
				p.html($(node).find(".tree-title").html());
				p.hide();
				return p;
			},
			deltaX : 15,
			deltaY : 15,
			onBeforeDrag : function(e) {
				if (e.which != 1) {
					return false;
				}
				$(this).next("ul").find("div.tree-node").droppable( {
					accept : "no-accept"
				});
			},
			onStartDrag : function() {
				$(this).draggable("proxy").css( {
					left : -10000,
					top : -10000
				});
			},
			onDrag : function(e) {
				var x1 = e.pageX, y1 = e.pageY, x2 = e.data.startX, y2 = e.data.startY;
				var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
				if (d > 3) {
					$(this).draggable("proxy").show();
				}
				this.pageY = e.pageY;
			},
			onStopDrag : function() {
				$(this).next("ul").find("div.tree-node").droppable( {
					accept : "div.tree-node" //接受结点拖动
				});
			}
		}).droppable({
			accept : "div.tree-node",
			onDragOver : function(e, source) {//source:可拖动的DOM对象
				var pageY = source.pageY;
				var top = $(this).offset().top;
				var height = top + $(this).outerHeight();
				$(source).draggable("proxy").removeClass("tree-dnd-no").addClass("tree-dnd-yes");
				$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
				if (pageY > top + (height - top) / 2) {
					if (height - pageY < 5) {
						$(this).addClass("tree-node-bottom");
					} else {
						$(this).addClass("tree-node-append");
					}
				} else {
					if (pageY - top < 5) {
						$(this).addClass("tree-node-top");
					} else {
						$(this).addClass("tree-node-append");
					}
				}
			},
			onDragLeave : function(e, source) {
				$(source).draggable("proxy").removeClass(
						"tree-dnd-yes").addClass("tree-dnd-no");
				$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
			},
			onDrop : function(e, source) {
				var target = this;
				var action, point;
				if ($(this).hasClass("tree-node-append")) {
					action = moveNode;
				} else {
					action = insertNode;
					point = $(this).hasClass("tree-node-top") ? "top" : "bottom";
				}
				setTimeout(function() {
					action(source, target, point);
				}, 0);
				$(this).removeClass("tree-node-append tree-node-top tree-node-bottom");
			}
		});
		function moveNode(nodeEl, parent) {
			if (getNode(jq, parent).state == "closed") {
				expand(jq, parent, function() {
					doMoveNode();
				});
			} else {
				doMoveNode();
			}
			function doMoveNode() {
				var nodeData = $(jq).tree("pop", nodeEl);
				$(jq).tree("append", {
					parent : parent,
					data : [ nodeData ]
				});
				options.onDrop.call(jq, parent, nodeData, "append");
			};
		};
		function insertNode(nodeEl, parent, point) {
			var param = {};
			if (point == "top") {
				param.before = parent;
			} else {
				param.after = parent;
			}
			var nodeData = $(jq).tree("pop", nodeEl);
			param.data = nodeData;
			$(jq).tree("insert", param);
			options.onDrop.call(jq, parent, nodeData, point);
		};
	};
	/**
	 * 将结点设置为勾选或不勾选
	 * @param {Object} jq
	 * @param {Object} target
	 * @param {Object} isCheck true:勾选,flase:不勾选
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function setChecked(jq, target, isCheck) {
		var options = $.data(jq, "tree").options;
		if (!options.checkbox) {
			return;
		}
		var node = $(target);
		var ck = node.find(".tree-checkbox");
		ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");//清空样式
		if (isCheck) {
			ck.addClass("tree-checkbox1");//勾选样式
		} else {
			ck.addClass("tree-checkbox0");//不勾选样式
		}
		//设置级联选中
		if (options.cascadeCheck) {
			setParentsChecked(node);
			setChildrenChecked(node);
		}
		var node = getNode(jq, target);
		options.onCheck.call(jq, node, isCheck);//触发onCheck事件
		/**
		 * 勾选子结点
		 * @param {Object} node 勾选结点的DOMd对象
		 */
		function setChildrenChecked(node) {
			var ck = node.next().find(".tree-checkbox");
			ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
			if (node.find(".tree-checkbox").hasClass("tree-checkbox1")) {
				ck.addClass("tree-checkbox1");//勾选样式（非级联）
			} else {
				ck.addClass("tree-checkbox0");//不勾选样式
			}
		};
		/**
		 * 级联勾选父结点
		 * @param {Object} node 勾选结点的DOMd对象
		 * @memberOf {TypeName} 
		 * @return {TypeName} 
		 */
		function setParentsChecked(node) {
			var parentNode = getParent(jq, node[0]);//获取父结点
			if (parentNode) {
				var ck = $(parentNode.target).find(".tree-checkbox");
				ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
				if (isAllSelected(node)) {
					ck.addClass("tree-checkbox1");//勾选样式
				} else {
					if (isAllNull(node)) {
						ck.addClass("tree-checkbox0");//不勾选样式
					} else {
						ck.addClass("tree-checkbox2");//级联勾选样式
					}
				}
				setParentsChecked($(parentNode.target));
			}
			/**
			 * 是否全选（非级联勾选）
			 * @param {Object} node
			 * @memberOf {TypeName} 
			 * @return {TypeName} true:勾选,false:未勾选或级联勾选
			 */
			function isAllSelected(node) {
				var ck = node.find(".tree-checkbox");
				//判断当前勾选框的样式状态
				if (ck.hasClass("tree-checkbox0")
						|| ck.hasClass("tree-checkbox2")) {
					return false;
				}
				var checked = true;
				node.parent().siblings().each(function() {
					if (!$(this).children("div.tree-node").children(
							".tree-checkbox")
							.hasClass("tree-checkbox1")) {
						checked = false;
					}
				});
				return checked;
			};
			/**
			 * 是否全未勾选
			 * @param {Object} node
			 * @memberOf {TypeName} 
			 * @return {TypeName} true:未勾选,false:勾选或级联勾选
			 */
			function isAllNull(node) {
				var ck = node.find(".tree-checkbox");
				if (ck.hasClass("tree-checkbox1")
						|| ck.hasClass("tree-checkbox2")) {
					return false;//若ck为勾选或级联勾选，则返回false
				}
				var unChecked = true;
				node.parent().siblings().each(function() {
					if (!$(this).children("div.tree-node").children(
							".tree-checkbox")
							.hasClass("tree-checkbox0")) {
						unChecked = false;
					}
				});
				return unChecked;
			};
		};
	};
	/**
	 * 设置勾选框的值
	 * @param {Object} jq
	 * @param {Object} target
	 */
	function setCheckBoxValue(jq, target) {
		var options = $.data(jq, "tree").options;
		var node = $(target);
		if (isLeaf(jq, target)) {//叶子结点
			var ck = node.find(".tree-checkbox");
			if (ck.length) {
				if (ck.hasClass("tree-checkbox1")) {
					setChecked(jq, target, true);
				} else {
					setChecked(jq, target, false);
				}
			} else {
				if (options.onlyLeafCheck) {//只显示叶子节点前的复选框
					$("<span class=\"tree-checkbox tree-checkbox0\"></span>").insertBefore(node.find(".tree-title"));
					bindEvent(jq);
				}
			}
		} else {//非叶子结点
			var ck = node.find(".tree-checkbox");
			if (options.onlyLeafCheck) {
				ck.remove();
			} else {
				if (ck.hasClass("tree-checkbox1")) {
					setChecked(jq, target, true);
				} else {
					if (ck.hasClass("tree-checkbox2")) {
						var checked = true;
						var unchecked = true;
						var children = getChildren(jq, target);
						for ( var i = 0; i < children.length; i++) {
							if (children[i].checked) {
								unchecked = false;
							} else {
								checked = false;
							}
						}
						if (checked) {
							setChecked(jq, target, true);
						}
						if (unchecked) {
							setChecked(jq, target, false);
						}
					}
				}
			}
		}
	};
	/**
	 * 加载树图数据
	 * @param {Object} jq
	 * @param {Object} ul
	 * @param {Object} data 数据
	 * @param {Object} isAppend
	 */
	function loadData(jq, ul, data, isAppend) {
		var options = $.data(jq, "tree").options;
		if (!isAppend) {
			$(ul).empty();
		}
		var checkedNodes = [];
		var depth = $(ul).prev("div.tree-node").find(
				"span.tree-indent, span.tree-hit").length;
		appendNodes(ul, data, depth);
		bindEvent(jq);
		if (options.dnd) {
			enableDnd(jq);
		} else {
			disableDnd(jq);
		}
		for ( var i = 0; i < checkedNodes.length; i++) {
			setChecked(jq, checkedNodes[i], true);
		}
		var node = null;
		if (jq != ul) {
			var _50 = $(ul).prev();
			node = getNode(jq, _50[0]);
		}
		options.onLoadSuccess.call(jq, node, data);
		function appendNodes(ul, children, depth) {
			for ( var i = 0; i < children.length; i++) {
				var li = $("<li></li>").appendTo(ul);
				var item = children[i];
				if (item.state != "open" && item.state != "closed") {
					item.state = "open";
				}
				var node = $("<div class=\"tree-node\"></div>").appendTo(li);
				node.attr("node-id", item.id);
				$.data(node[0], "tree-node", {
					id : item.id,
					text : item.text,
					iconCls : item.iconCls,
					attributes : item.attributes
				});
				$("<span class=\"tree-title\"></span>").html(item.text)
						.appendTo(node);
				if (options.checkbox) {
					if (options.onlyLeafCheck) {
						if (item.state == "open"
								&& (!item.children || !item.children.length)) {
							if (item.checked) {
								$("<span class=\"tree-checkbox tree-checkbox1\"></span>").prependTo(node);
							} else {
								$("<span class=\"tree-checkbox tree-checkbox0\"></span>").prependTo(node);
							}
						}
					} else {
						if (item.checked) {
							$("<span class=\"tree-checkbox tree-checkbox1\"></span>").prependTo(node);
							checkedNodes.push(node[0]);
						} else {
							$("<span class=\"tree-checkbox tree-checkbox0\"></span>").prependTo(node);
						}
					}
				}
				if (item.children && item.children.length) {
					var subul = $("<ul></ul>").appendTo(li);
					if (item.state == "open") {
						$("<span class=\"tree-icon tree-folder tree-folder-open\"></span>")
								.addClass(item.iconCls).prependTo(node);
						$("<span class=\"tree-hit tree-expanded\"></span>").prependTo(node);
					} else {
						$("<span class=\"tree-icon tree-folder\"></span>")
								.addClass(item.iconCls).prependTo(node);
						$("<span class=\"tree-hit tree-collapsed\"></span>").prependTo(node);
						subul.css("display", "none");
					}
					appendNodes(subul, item.children, depth + 1);
				} else {
					if (item.state == "closed") {
						$("<span class=\"tree-icon tree-folder\"></span>")
								.addClass(item.iconCls).prependTo(node);
						$("<span class=\"tree-hit tree-collapsed\"></span>").prependTo(node);
					} else {
						$("<span class=\"tree-icon tree-file\"></span>")
								.addClass(item.iconCls).prependTo(node);
						$("<span class=\"tree-indent\"></span>").prependTo(node);
					}
				}
				for ( var j = 0; j < depth; j++) {
					$("<span class=\"tree-indent\"></span>").prependTo(node);
				}
			}
		};
	};
	/**
	 * 请求UL
	 * @param {Object} jq
	 * @param {Object} ul
	 * @param {Object} data ajax请求的data参数
	 * @param {Object} callBack 回调函数
	 * @return {TypeName} 
	 */
	function request(jq, ul, data, callBack) {
		var options = $.data(jq, "tree").options;
		data = data || {};
		var node = null;
		if (jq != ul) {
			var prevNodes = $(ul).prev();//获取同一层的之前的结点
			node = getNode(jq, prevNodes[0]);
		}
		if (options.onBeforeLoad.call(jq, node, data) == false) {
			return;
		}
		if (!options.url) {
			return;
		}
		var folder = $(ul).prev().children("span.tree-folder");
		folder.addClass("tree-loading");//非叶子结点加载中样式
		$.ajax( {
			type : options.method,
			url : options.url,
			data : data,
			dataType : "json",
			success : function(json) {
				folder.removeClass("tree-loading");//去除非叶子结点的加载中样式
				loadData(jq, ul, json);
				if (callBack) {
					callBack();
				}
			},
			error : function() {
				folder.removeClass("tree-loading");
				options.onLoadError.apply(jq, arguments);
				if (callBack) {
					callBack();
				}
			}
		});
	};
	/**
	 * 展开某节点
	 * @param {Object} jq
	 * @param {Object} target 展开节点的DOM对象
	 * @param {Object} callBack 回调函数
	 * @return {TypeName} 
	 */
	function expand(jq, target, callBack) {
		var options = $.data(jq, "tree").options;
		var hit = $(target).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			return;
		}
		var node = getNode(jq, target);
		if (options.onBeforeExpand.call(jq, node) == false) {
			return;//若onBeforeExpand返回false则直接返回
		}
		//修改样式
		hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
		hit.next().addClass("tree-folder-open");
		var target = $(target).next();//获取同辈后面的结点
		if (target.length) {
			if (options.animate) {
				target.slideDown("normal", function() {
					options.onExpand.call(jq, node);
					if (callBack) {
						callBack();
					}
				});
			} else {
				target.css("display", "block");
				options.onExpand.call(jq, node);
				if (callBack) {
					callBack();
				}
			}
		} else {
			var ul = $("<ul style=\"display:none\"></ul>").insertAfter(target);
			request(jq, ul[0], {
				id : node.id
			}, function() {
				if (options.animate) {
					ul.slideDown("normal", function() {
						options.onExpand.call(jq, node);
						if (callBack) {
							callBack();
						}
					});
				} else {
					ul.css("display", "block");
					options.onExpand.call(jq, node);
					if (callBack) {
						callBack();
					}
				}
			});
		}
	};
	function collapse(jq, nodeEl) {
		var opts = $.data(jq, "tree").options;
		var hit = $(nodeEl).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-collapsed")) {
			return;
		}
		var node = getNode(jq, nodeEl);
		if (opts.onBeforeCollapse.call(jq, node) == false) {
			return;
		}
		hit.removeClass("tree-expanded tree-expanded-hover").addClass(
				"tree-collapsed");
		hit.next().removeClass("tree-folder-open");
		var target = $(nodeEl).next();
		if (opts.animate) {
			target.slideUp("normal", function() {
				opts.onCollapse.call(jq, node);
			});
		} else {
			target.css("display", "none");
			opts.onCollapse.call(jq, node);
		}
	};
	function toggle(jq, nodeEl) {
		var hit = $(nodeEl).children("span.tree-hit");
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass("tree-expanded")) {
			collapse(jq, nodeEl);
		} else {
			expand(jq, nodeEl);
		}
	};
	function expandAll(jq, nodeEl) {
		var children = getChildren(jq, nodeEl);
		if (nodeEl) {
			children.unshift(getNode(jq, nodeEl));
		}
		for ( var i = 0; i < children.length; i++) {
			expand(jq, children[i].target);
		}
	};
	function expandTo(jq, nodeEl) {
		var ancestors = [];
		var p = getParent(jq, nodeEl);
		while (p) {
			ancestors.unshift(p);
			p = getParent(jq, p.target);
		}
		for ( var i = 0; i < ancestors.length; i++) {
			expand(jq, ancestors[i].target);
		}
	};
	function collapseAll(jq, nodeEl) {
		var children = getChildren(jq, nodeEl);
		if (nodeEl) {
			children.unshift(getNode(jq, nodeEl));
		}
		for ( var i = 0; i < children.length; i++) {
			collapse(jq, children[i].target);
		}
	};
	/**
	 * 获取根结点
	 * @param {Object} jq
	 * @return {TypeName} node object
	 */
	function getRoot(jq) {
		var rootNodes = getRoots(jq);
		if (rootNodes.length) {
			return rootNodes[0];
		} else {
			return null;
		}
	};
	/**
	 * 获取所有根结点
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} node array
	 */
	function getRoots(jq) {
		var roots = [];
		$(jq).children("li").each(function() {
			var nodes = $(this).children("div.tree-node");
			roots.push(getNode(jq, nodes[0]));
		});
		return roots;
	};
	/**
	 * 获取所有子结点
	 * @param {Object} jq
	 * @param {Object} target
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function getChildren(jq, target) {
		var nodes = [];
		if (target) {
			findChildren($(target));
		} else {
			var rootNodes = getRoots(jq);
			for ( var i = 0; i < rootNodes.length; i++) {
				nodes.push(rootNodes[i]);
				findChildren($(rootNodes[i].target));
			}
		}
		function findChildren(target) {
			target.next().find("div.tree-node").each(function() {
				nodes.push(getNode(jq, this));
			});
		};
		return nodes;
	};
	/**
	 * 获取父结点
	 * @param {Object} jq
	 * @param {Object} target
	 * @return {TypeName} 
	 */
	function getParent(jq, target) {
		var ul = $(target).parent().parent();
		if (ul[0] == jq) {
			return null;
		} else {
			return getNode(jq, ul.prev()[0]);
		}
	};
	/**
	 * 获取所有被勾选的结点
	 * @param {Object} jq
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function getChecked(jq) {
		var nodes = [];
		$(jq).find(".tree-checkbox1").each(function() {
			var parentNodes = $(this).parent();
			nodes.push(getNode(jq, parentNodes[0]));
		});
		return nodes;
	};
	/**
	 * 获取被选中的结点
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	function getSelected(jq) {
		var nodes = $(jq).find("div.tree-node-selected");
		if (nodes.length) {
			return getNode(jq, nodes[0]);
		} else {
			return null;
		}
	};
	function append(jq, param) {
		var parent = $(param.parent);
		var ul;
		if (parent.length == 0) {
			ul = $(jq);
		} else {
			ul = parent.next();
			if (ul.length == 0) {
				ul = $("<ul></ul>").insertAfter(parent);
			}
		}
		if (param.data && param.data.length) {
			var icon = parent.find("span.tree-icon");
			if (icon.hasClass("tree-file")) {
				icon.removeClass("tree-file").addClass("tree-folder");
				var hit = $("<span class=\"tree-hit tree-expanded\"></span>")
						.insertBefore(icon);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		loadData(jq, ul[0], param.data, true);
		setCheckBoxValue(jq, ul.prev());
	};
	function insert(jq, param) {
		var ref = param.before || param.after;
		var pNode = getParent(jq, ref);
		var li;
		if (pNode) {
			append(jq, {
				parent : pNode.target,
				data : [ param.data ]
			});
			li = $(pNode.target).next().children("li:last");
		} else {
			append(jq, {
				parent : null,
				data : [ param.data ]
			});
			li = $(jq).children("li:last");
		}
		if (param.before) {
			li.insertBefore($(ref).parent());
		} else {
			li.insertAfter($(ref).parent());
		}
	};
	/**
	 * 删除一个节点和它的子节点
	 * @param {Object} jq
	 * @param {Object} target 需要删除的结点DOM对象
	 */
	function remove(jq, target) {
		var parentNode = getParent(jq, target);
		var node = $(target);
		var li = node.parent();//获取删除结点的li
		var ul = li.parent();//获取删除结点所在的ul
		li.remove();//删除结点下所有的子结点
		//判断删除结点是否有兄弟结点，若没有则将父结点有文件夹样式改成文件样式，比并将结点删除
		if (ul.children("li").length == 0) {
			var node = ul.prev();
			node.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
			node.find(".tree-hit").remove();
			$("<span class=\"tree-indent\"></span>").prependTo(node);
			if (ul[0] != jq) {
				ul.remove();
			}
		}
		if (parentNode) {
			setCheckBoxValue(jq, parentNode.target);
		}
	};
	/**
	 * 递归获取某结点数据（包括子结点）
	 * @param {Object} jq
	 * @param {Object} target
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	function getData(jq, target) {
		/**
		 * 获取下一结点的子结点
		 * @param {Object} children 子结点数组
		 * @param {Object} ul 下一结点
		 * @memberOf {TypeName} 
		 */
		function getChildrenOfNextNode(children, ul) {
			ul.children("li").each(function() {
				var nodes = $(this).children("div.tree-node");
				var node = getNode(jq, nodes[0]);
				var sub = $(this).children("ul");
				if (sub.length) {
					node.children = [];
					getData(node.children, sub);
				}
				children.push(node);
			});
		};
		if (target) {
			var node = getNode(jq, target);
			node.children = [];
			getChildrenOfNextNode(node.children, $(target).next());
			return node;
		} else {
			return null;
		}
	};
	/**
	 * 更新指定的节点
	 * @param {Object} jq
	 * @param {Object} param = {target:DOM对象,id:结点ID,text:结点TEXT,iconCls:结点图标,checked:结点勾选状态}
	 */
	function update(jq, param) {
		var target = $(param.target);
		var node = $.data(param.target, "tree-node");
		if (node.iconCls) {
			target.find(".tree-icon").removeClass(node.iconCls);
		}
		$.extend(node, param);
		$.data(param.target, "tree-node", node);
		target.attr("node-id", node.id);//设置ID
		target.find(".tree-title").html(node.text);//设置TEXT
		if (node.iconCls) {
			target.find(".tree-icon").addClass(node.iconCls);//设置图标
		}
		//设置勾选状态
		var ck = target.find(".tree-checkbox");
		ck.removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2");
		if (node.checked) {
			setChecked(jq, param.target, true);
		} else {
			setChecked(jq, param.target, false);
		}
	};
	/**
	 * 获取结点
	 * @param {Object} jq
	 * @param {Object} target
	 * @return {TypeName} 
	 */
	function getNode(jq, target) {
		var node = $.extend( {}, $.data(target, "tree-node"), {
			target : target,
			checked : $(target).find(".tree-checkbox").hasClass("tree-checkbox1")
		});
		if (!isLeaf(jq, target)) {
			node.state = $(target).find(".tree-hit").hasClass("tree-expanded") ? "open" : "closed";
		}
		return node;
	};
	/**
	 * 根据ID查找结点
	 * @param {Object} jq
	 * @param {Object} id
	 * @return {TypeName} 
	 */
	function find(jq, id) {
		var nodes = $(jq).find("div.tree-node[node-id=" + id + "]");
		if (nodes.length) {
			return getNode(jq, nodes[0]);
		} else {
			return null;
		}
	};
	/**
	 * 选择结点
	 * @param {Object} jq
	 * @param {Object} target
	 * @return {TypeName} 
	 */
	function select(jq, target) {
		var options = $.data(jq, "tree").options;
		var node = getNode(jq, target);
		if (options.onBeforeSelect.call(jq, node) == false) {
			return;//如onBeforeSelect返回false，则取消选中
		}
		$("div.tree-node-selected", jq).removeClass("tree-node-selected");
		$(target).addClass("tree-node-selected");
		options.onSelect.call(jq, node);//触发onSelect事件
	};
	/**
	 * 是否叶子结点
	 * @param {Object} jq
	 * @param {Object} target
	 * @return {TypeName} 
	 */
	function isLeaf(jq, target) {
		var node = $(target);
		var hit = node.children("span.tree-hit");
		return hit.length == 0;
	};
	/**
	 * 开始编辑结点
	 * @param {Object} jq
	 * @param {Object} nodeEl 结点DOM对象
	 * @return {TypeName} 
	 */
	function beginEdit(jq, nodeEl) {
		var options = $.data(jq, "tree").options;
		var node = getNode(jq, nodeEl);
		if (options.onBeforeEdit.call(jq, node) == false) {
			return;//如onBeforeEdit返回false，则取消编辑
		}
		$(nodeEl).css("position", "relative");
		var nt = $(nodeEl).find(".tree-title");
		var outerWidth = nt.outerWidth();
		nt.empty();
		var editor = $("<input class=\"tree-editor\">").appendTo(nt);//编辑器
		editor.val(node.text).focus();//编辑器赋值并获取焦点
		//设置编辑器大小
		editor.width(outerWidth + 20);
		editor.height(document.compatMode == "CSS1Compat" ? (18 - (editor
				.outerHeight() - editor.height())) : 18);
		//设置编辑器事件
		editor.bind("click", function(e) {
			return false;
		}).bind("mousedown", function(e) {
			e.stopPropagation();
		}).bind("mousemove", function(e) {
			e.stopPropagation();
		}).bind("keydown", function(e) {
			if (e.keyCode == 13) {//回车
				endEdit(jq, nodeEl);
				return false;
			} else {
				if (e.keyCode == 27) {//Esc
					cancelEdit(jq, nodeEl);
					return false;
				}
			}
		}).bind("blur", function(e) {
			e.stopPropagation();
			endEdit(jq, nodeEl);
		});
	};
	/**
	 * 结束编辑
	 * @param {Object} jq
	 * @param {Object} nodeEl 结点DOM对象
	 */
	function endEdit(jq, nodeEl) {
		var options = $.data(jq, "tree").options;
		$(nodeEl).css("position", "");
		var editor = $(nodeEl).find("input.tree-editor");
		var val = editor.val();//获取编辑的值
		editor.remove();
		var node = getNode(jq, nodeEl);
		node.text = val;//将编辑的值赋给结点
		update(jq, node);//更新结点
		options.onAfterEdit.call(jq, node);//响应onAfterEdit事件
	};
	/**
	 * 取消编辑
	 * @param {Object} jq
	 * @param {Object} nodeEl 结点DOM对象
	 */
	function cancelEdit(jq, nodeEl) {
		var options = $.data(jq, "tree").options;
		$(nodeEl).css("position", "");
		$(nodeEl).find("input.tree-editor").remove();
		var node = getNode(jq, nodeEl);
		update(jq, node);
		options.onCancelEdit.call(jq, node);
	};
	/**
	 * 实例化树图或方法调用
	 * @param {Object} options 若为string则是方法调用，否则实例化组件
	 * @param {Object} param 方法参数
	 * @memberOf {TypeName} 
	 * @return {TypeName} 
	 */
	$.fn.tree = function(options, param) {
		if (typeof options == "string") {
			return $.fn.tree.methods[options](this, param);
		}
		var options = options || {};
		return this.each(function() {
			var data = $.data(this, "tree");
			var options;
			if (data) {
				options = $.extend(data.options, options);
				data.options = options;
			} else {
				options = $.extend( {}, $.fn.tree.defaults, $.fn.tree.parseOptions(this), options);
				$.data(this, "tree", {
					options : options,
					tree : wrapTree(this)
				});
				var jsonData = getTreeData(this);
				if (jsonData.length && !options.data) {
					options.data = jsonData;
				}
			}
			if (options.data) {
				loadData(this, this, options.data);
			} else {
				if (options.dnd) {
					enableDnd(this);
				} else {
					disableDnd(this);
				}
			}
			if (options.url) {
				request(this, this);
			}
		});
	};
	/**
	 * 方法注册
	 * @param {Object} jq
	 * @return {TypeName} 
	 */
	$.fn.tree.methods = {
		options : function(jq) {
			return $.data(jq[0], "tree").options;
		},
		loadData : function(jq, data) {
			return jq.each(function() {
				loadData(this, this, data);
			});
		},
		getNode : function(jq, target) {
			return getNode(jq[0], target);
		},
		getData : function(jq, target) {
			return getData(jq[0], target);
		},
		reload : function(jq, target) {
			return jq.each(function() {
				if (target) {
					var node = $(target);
					var hit = node.children("span.tree-hit");//获取结点前的展开/折叠对象
					hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
					node.next().remove();//删除子结点
					expand(this, target);//调用展开方法，重新加载树
				} else {
					$(this).empty();
					request(this, this);
				}
			});
		},
		getRoot : function(jq) {
			return getRoot(jq[0]);
		},
		getRoots : function(jq) {
			return getRoots(jq[0]);
		},
		getParent : function(jq, target) {
			return getParent(jq[0], target);
		},
		getChildren : function(jq, target) {
			return getChildren(jq[0], target);
		},
		getChecked : function(jq) {
			return getChecked(jq[0]);
		},
		getSelected : function(jq) {
			return getSelected(jq[0]);
		},
		isLeaf : function(jq, target) {
			return isLeaf(jq[0], target);
		},
		find : function(jq, id) {
			return find(jq[0], id);
		},
		select : function(jq, target) {
			return jq.each(function() {
				select(this, target);
			});
		},
		check : function(jq, target) {
			return jq.each(function() {
				setChecked(this, target, true);
			});
		},
		uncheck : function(jq, target) {
			return jq.each(function() {
				setChecked(this, target, false);
			});
		},
		collapse : function(jq, target) {
			return jq.each(function() {
				collapse(this, target);
			});
		},
		expand : function(jq, target) {
			return jq.each(function() {
				expand(this, target);
			});
		},
		collapseAll : function(jq, target) {
			return jq.each(function() {
				collapseAll(this, target);
			});
		},
		expandAll : function(jq, target) {
			return jq.each(function() {
				expandAll(this, target);
			});
		},
		expandTo : function(jq, target) {
			return jq.each(function() {
				expandTo(this, target);
			});
		},
		toggle : function(jq, target) {
			return jq.each(function() {
				toggle(this, target);
			});
		},
		//param={parent: DOM object,data: nodes array}
		append : function(jq, param) {
			return jq.each(function() {
				append(this, param);
			});
		},
		//param={before: DOM object,after: DOM object,data : node data}
		insert : function(jq, param) {
			return jq.each(function() {
				insert(this, param);
			});
		},
		remove : function(jq, target) {
			return jq.each(function() {
				remove(this, target);
			});
		},
		pop : function(jq, target) {
			var data = jq.tree("getData", target);
			jq.tree("remove", target);
			return data;
		},
		//param={target,id,text,iconCls,checked,etc}
		update : function(jq, param) {
			return jq.each(function() {
				update(this, param);
			});
		},
		enableDnd : function(jq) {
			return jq.each(function() {
				enableDnd(this);
			});
		},
		disableDnd : function(jq) {
			return jq.each(function() {
				disableDnd(this);
			});
		},
		beginEdit : function(jq, nodeEl) {
			return jq.each(function() {
				beginEdit(this, nodeEl);
			});
		},
		endEdit : function(jq, nodeEl) {
			return jq.each(function() {
				endEdit(this, nodeEl);
			});
		},
		cancelEdit : function(jq, nodeEl) {
			return jq.each(function() {
				cancelEdit(this, nodeEl);
			});
		}
	};
	/**
	 * class声明式定义属性转化为options
	 * @param {Object} target DOM对象
	 * @return {TypeName} 
	 */
	$.fn.tree.parseOptions = function(target) {
		var t = $(target);
		return {
			url : t.attr("url"),
			method : (t.attr("method") ? t.attr("method") : undefined),
			checkbox : (t.attr("checkbox") ? t.attr("checkbox") == "true"
					: undefined),
			cascadeCheck : (t.attr("cascadeCheck") ? t.attr("cascadeCheck") == "true"
					: undefined),
			onlyLeafCheck : (t.attr("onlyLeafCheck") ? t.attr("onlyLeafCheck") == "true"
					: undefined),
			animate : (t.attr("animate") ? t.attr("animate") == "true"
					: undefined),
			dnd : (t.attr("dnd") ? t.attr("dnd") == "true" : undefined)
		};
	};
	/**
	 * 默认参数设置
	 * @param {Object} node
	 * @param {Object} param
	 */
	$.fn.tree.defaults = {
		url : null,
		method : "post",
		animate : false,
		checkbox : false,
		cascadeCheck : true,
		onlyLeafCheck : false,
		dnd : false,
		data : null,
		onBeforeLoad : function(node, param) {
		},
		onLoadSuccess : function(node, data) {
		},
		onLoadError : function() {
		},
		onClick : function(node) {
		},
		onDblClick : function(node) {
		},
		onBeforeExpand : function(node) {
		},
		onExpand : function(node) {
		},
		onBeforeCollapse : function(node) {
		},
		onCollapse : function(node) {
		},
		onCheck : function(node, checked) {
		},
		onBeforeSelect : function(node) {
		},
		onSelect : function(node) {
		},
		onContextMenu : function(e, node) {
		},
		onDrop : function(target, source, point) {
		},
		onBeforeEdit : function(node) {
		},
		onAfterEdit : function(node) {
		},
		onCancelEdit : function(node) {
		}
	};
})(jQuery);
