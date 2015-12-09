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
	var resizable = false;
	$.fn.resizable = function(options, param) {
		if (typeof options == "string") {
			return $.fn.resizable.methods[options](this, param);
		}
		function resize(e) {
			var resizeData = e.data;
			var opts = $.data(resizeData.target, "resizable").options;
			if (resizeData.dir.indexOf("e") != -1) {
				var width = resizeData.startWidth + e.pageX - resizeData.startX;
				width = Math.min(Math.max(width, opts.minWidth), opts.maxWidth);
				resizeData.width = width;
			}
			if (resizeData.dir.indexOf("s") != -1) {
				var height = resizeData.startHeight + e.pageY - resizeData.startY;
				height = Math.min(Math.max(height, opts.minHeight), opts.maxHeight);
				resizeData.height = height;
			}
			if (resizeData.dir.indexOf("w") != -1) {
				resizeData.width = resizeData.startWidth - e.pageX + resizeData.startX;
				if (resizeData.width >= opts.minWidth && resizeData.width <= opts.maxWidth) {
					resizeData.left = resizeData.startLeft + e.pageX - resizeData.startX;
				}
			}
			if (resizeData.dir.indexOf("n") != -1) {
				resizeData.height = resizeData.startHeight - e.pageY + resizeData.startY;
				if (resizeData.height >= opts.minHeight && resizeData.height <= opts.maxHeight) {
					resizeData.top = resizeData.startTop + e.pageY - resizeData.startY;
				}
			}
		};
		function applySize(e) {
			var resizeData = e.data;
			var target = resizeData.target;
			if ($.boxModel == true) {
				$(target).css( {
					width : resizeData.width - resizeData.deltaWidth,
					height : resizeData.height - resizeData.deltaHeight,
					left : resizeData.left,
					top : resizeData.top
				});
			} else {
				$(target).css( {
					width : resizeData.width,
					height : resizeData.height,
					left : resizeData.left,
					top : resizeData.top
				});
			}
		};
		function doDown(e) {
			resizable = true;
			$.data(e.data.target, "resizable").options.onStartResize.call(
					e.data.target, e);
			return false;
		};
		function doMove(e) {
			resize(e);
			if ($.data(e.data.target, "resizable").options.onResize.call(
					e.data.target, e) != false) {
				applySize(e);
			}
			return false;
		};
		function doUp(e) {
			resizable = false;
			resize(e, true);
			applySize(e);
			$.data(e.data.target, "resizable").options.onStopResize.call(
					e.data.target, e);
			$(document).unbind(".resizable");
			$("body").css("cursor", "auto");
			return false;
		};
		return this.each(function() {
			var opts = null;
			var state = $.data(this, "resizable");
			if (state) {
				$(this).unbind(".resizable");
				opts = $.extend(state.options, options || {});
			} else {
				opts = $.extend( {}, $.fn.resizable.defaults, options || {});
				$.data(this, "resizable", {
					options : opts
				});
			}
			if (opts.disabled == true) {
				return;
			}
			$(this).bind("mousemove.resizable", {
				target : this
			}, function(e) {
				if (resizable) {
					return;
				}
				var dir = getDirection(e);
				if (dir == "") {
					$(e.data.target).css("cursor", "");
				} else {
					$(e.data.target).css("cursor", dir + "-resize");
				}
			}).bind("mousedown.resizable",{
				target : this
			},
			function(e) {
				var dir = getDirection(e);
				if (dir == "") {
					return;
				}
				function getCssValue(css) {
					var val = parseInt($(e.data.target).css(css));
					if (isNaN(val)) {
						return 0;
					} else {
						return val;
					}
				};
				var data = {
					target : e.data.target,
					dir : dir,
					startLeft : getCssValue("left"),
					startTop : getCssValue("top"),
					left : getCssValue("left"),
					top : getCssValue("top"),
					startX : e.pageX,
					startY : e.pageY,
					startWidth : $(e.data.target).outerWidth(),
					startHeight : $(e.data.target).outerHeight(),
					width : $(e.data.target).outerWidth(),
					height : $(e.data.target).outerHeight(),
					deltaWidth : $(e.data.target).outerWidth()
							- $(e.data.target).width(),
					deltaHeight : $(e.data.target).outerHeight()
							- $(e.data.target).height()
				};
				$(document).bind("mousedown.resizable", data, doDown);
				$(document).bind("mousemove.resizable", data, doMove);
				$(document).bind("mouseup.resizable", data, doUp);
				$("body").css("cursor", dir + "-resize");
			}).bind("mouseleave.resizable", {
				target : this
			}, function(e) {
				$(e.data.target).css("cursor", "");
			});
			function getDirection(e) {
				var tt = $(e.data.target);
				var dir = "";
				var offset = tt.offset();
				var width = tt.outerWidth();
				var height = tt.outerHeight();
				var edge = opts.edge;
				if (e.pageY > offset.top && e.pageY < offset.top + edge) {
					dir += "n";
				} else {
					if (e.pageY < offset.top + height
							&& e.pageY > offset.top + height - edge) {
						dir += "s";
					}
				}
				if (e.pageX > offset.left && e.pageX < offset.left + edge) {
					dir += "w";
				} else {
					if (e.pageX < offset.left + width
							&& e.pageX > offset.left + width - edge) {
						dir += "e";
					}
				}
				var handles = opts.handles.split(",");
				for ( var i = 0; i < handles.length; i++) {
					var handle = handles[i].replace(/(^\s*)|(\s*$)/g, "");
					if (handle == "all" || handle == dir) {
						return dir;
					}
				}
				return "";
			};
		});
	};
	$.fn.resizable.methods = {
		options : function(jq) {
			return $.data(jq[0], "resizable").options;
		},
		enable : function(jq) {
			return jq.each(function() {
				$(this).resizable( {
					disabled : false
				});
			});
		},
		disable : function(jq) {
			return jq.each(function() {
				$(this).resizable( {
					disabled : true
				});
			});
		}
	};
	$.fn.resizable.defaults = {
		disabled : false,
		handles : "n, e, s, w, ne, se, sw, nw, all",
		minWidth : 10,
		minHeight : 10,
		maxWidth : 10000,
		maxHeight : 10000,
		edge : 5,
		onStartResize : function(e) {
		},
		onResize : function(e) {
		},
		onStopResize : function(e) {
		}
	};
})(jQuery);
