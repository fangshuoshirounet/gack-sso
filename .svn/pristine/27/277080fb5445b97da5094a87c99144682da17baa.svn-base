// JavaScript Document
/**
Jquery tab ���
**/
(function($){
		$.fn.jqueryTab = function(options){
       	 	var defaults = {
           	 	tarTab:"",
           	 	clickback:null	
        	},_this=this;
			this._options = $.extend(defaults, options);
			
			 this._resetTabs=function resetTabs(){
					$(_this._options.tarTab+" > div").hide(); //Hide all content
					var obj=$(this).attr("id");
					$("#"+obj+" a").attr("id",""); //Reset id's      
    		 }
		
			this._init=function(){
				 var contentId=_this._options.tarTab;
				 $(contentId+"> div").hide();
				 $(this).find("li>a").each(function(){	
					var current=$(this).attr("current");
					if(current=="true"){
						$(this).attr("id","current");
						var tabName=$(this).attr("name");
						$(_this._options.tarTab).find(tabName).fadeIn();
					}
				 }).on("click",function(){
					 _this._resetTabs();
					 $(this).attr("id","current");
					 var tabName=$(this).attr("name");
					 $(_this._options.tarTab).find(tabName).fadeIn();
					 
					 //add call back event function
					 if(_this._options.clickback!=null && typeof(_this._options.clickback)=='function'){
						 _this._options.clickback(tabName);
					 }
					 
				});
			}
			_this._init();
		return this;
	} 
		  
})(jQuery);