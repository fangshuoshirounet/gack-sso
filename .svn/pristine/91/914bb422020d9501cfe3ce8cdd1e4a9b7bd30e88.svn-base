// JavaScript Document
(function($){
		$.fn.jqueryToggle = function(options){
       	 	var defaults = {
           	 	openClass:"t-tool-toggle-open",
				closeClass:"t-tool-toggle-close"
        	},_this=this;
			this._options = $.extend(defaults, options);
			
			this._ctrlElement=function(__this,showFlag){
				$(__this).nextAll().each(function(){
					if(!$(this).is('legend')){
						if(showFlag){
							$(this).fadeIn();
						}else{
							$(this).fadeOut();
						}
					}
				})
			}
		
			this._init=function(){
				
				var thisE=$(this);
				thisE.children("legend").on("click",function(){
						var emE=$(this).find("em").first();
						if(emE.is('.'+_this._options.openClass)){
							emE.addClass(_this._options.closeClass);
							emE.removeClass(_this._options.openClass);
							_this._ctrlElement(this,false);
						}else if(emE.is('.'+_this._options.closeClass)){
							emE.addClass(_this._options.openClass);
							emE.removeClass(_this._options.closeClass);
							_this._ctrlElement(this,true);
						}
				});	
			}
			_this._init();
		return this;
	} 
		  
})(jQuery);