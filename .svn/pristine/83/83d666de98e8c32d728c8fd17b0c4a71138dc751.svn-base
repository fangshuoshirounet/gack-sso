;(function($) {
	$.fn.logDebug=function(message, isDebug) {
			return $.logger.debug(message, isDebug);
	};

	$.extend({
		logger : {
			isDebug : true,
			debug : function(message, isDebug) {
				if (isDebug == false) {
					return;
				}
				var container = $("#x-debug")[0];
				if (container == null) {
					var debugZone = document.createElement("TEXTAREA");
					document.body.appendChild(debugZone);
					debugZone.setAttribute("id", "x-debug");
					container = debugZone;
				}
				$(container).css("width", "100%");
				$(container).css("height", "80px");
				container.innerText += "\n[" + new Date().toLocaleTimeString()
						+ "]: " + message;
				container.scrollTop += 10000;
			}
		}
	})
})(jQuery);