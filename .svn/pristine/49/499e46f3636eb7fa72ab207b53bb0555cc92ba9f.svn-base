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
		$(jq).addClass("droppable");
		$(jq).bind("_dragenter", function(e, source) {
			$.data(jq, "droppable").options.onDragEnter.apply(jq, [ e, source ]);
		});
		$(jq).bind("_dragleave", function(e, source) {
			$.data(jq, "droppable").options.onDragLeave.apply(jq, [ e, source ]);
		});
		$(jq).bind("_dragover", function(e, source) {
			$.data(jq, "droppable").options.onDragOver.apply(jq, [ e, source ]);
		});
		$(jq).bind("_drop", function(e, source) {
			$.data(jq, "droppable").options.onDrop.apply(jq, [ e, source ]);
		});
	};
	$.fn.droppable = function(options, param) {
		if (typeof options == "string") {
			return $.fn.droppable.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "droppable");
			if (state) {
				$.extend(state.options, options);
			} else {
				init(this);
				$.data(this, "droppable", {
					options : $.extend( {}, $.fn.droppable.defaults, options)
				});
			}
		});
	};
	$.fn.droppable.methods = {};
	$.fn.droppable.defaults = {
		accept : null,
		onDragEnter : function(e, source) {
		},
		onDragOver : function(e, source) {
		},
		onDragLeave : function(e, source) {
		},
		onDrop : function(e, source) {
		}
	};
})(jQuery);
