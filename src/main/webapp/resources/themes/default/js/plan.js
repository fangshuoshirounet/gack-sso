/****************************
 ****定义flexgrid默认高度
 *****************************/ 
var tableNum = 1; 

var flexgirdHeight=400;
function reSetFG2H(){  
	  flexgirdHeight = $("div#flexgrid_two").height();  
	    if ($("div#top.nav").length <= 0) {
	        flexgirdHeight = flexgirdHeight - 123;
	    }
	    else {
	        flexgirdHeight = flexgirdHeight - 90;
	    } 
}
 
$(function(){  
    if ($("div#flexgrid_two")) {
      	reSetFG2H();    
      }
});

/**------------------------**
 **Flexigrid tool Functions**
 **------------------------**/

/**验证是否选中
 * options:
 * @param {Object} isMultiSelect是否支持选中多行默认值false:只选中单行
 * @param {Object} table 表格名称
 * @param {Object} tableName 表格名称
 */
function isSelectedBytable(options){
	var table = options.table;
	var tableName = options.tableName;
	if(typeof(tableName) == "undefined"||tableName==''||tableName==null){
		tableName ="";
	}else{
		tableName =tableName + "表";
	}
	
    var isMultiSelect = false;
	if(options.isMultiSelect){
		isMultiSelect=options;
	}
	
	var len = $("#" + table + " .trSelected").length;
    if (typeof(isMultiSelect) == "undefined") {
        Multiline = false;//默认选中一行
    }
    
    if (!isMultiSelect) {//只能选中单行
        if (len != 1) {
            jQuery.messager.alert('提示:', tableName+'请必须选择一行!', 'info');
            return;
        }
    }
    else {
        if (len == 0) {
            jQuery.messager.alert('提示:', tableName+'请至少选中一行!', 'info');
            return;
        }
    }
    
    return true;
}
 
 /**
  * 根据元素的name获取选中行的数据
  * @param {Object} nameGroup 筛选元素Name，以逗号隔开
  * @param {Object} table 表格
  */
 function getSelectRowByTableAndName(options){
	 var table = options.table;
	 var nameGroup = options.nameGroup;
     var selectValues = "";
     
     $("#" + table + " .trSelected").each(function(index, element){//循环选中行
         var nameList = nameGroup.split(",");
         var keys = "";
         for(var i = 0; i < nameList.length; i++) {
             var name = nameList[i];
             var temp = $(this).find("[name=" + name + "]").text();
             if (temp == "" || typeof(temp) == "undefined") {
                 temp = $(this).find("[name=" + name + "]").val();
             }
             keys += temp + ",";
         }
         keys = keys.substr(0, keys.length - 1);
         selectValues += keys + ",";
     });
     selectValues = selectValues.substr(0, selectValues.length - 1);
     return selectValues;
 }
 
 /**
  * 根据等级刷新table
  * @param {Object} type 表格类型 p:方案 r:规则 c:条件 a:分配
  * @param {Object} self 是否刷新自己
  * @param {Object} plan 方案名
  */
 function refresh(options) {
	 var type = options.type;
	 var self = options.self;
	 
	 if (typeof(type) == "undefined") { type = "";}
	 if (typeof(self) == "undefined") { self = true;}
	 
	 switch (type) {
		case 'p':
			if(self){
				$("#flexTable_"+type).flexReload();
			} 
			if($("#flexTable_r").length>0){
				$("#flexTable_r").find("tbody").children().remove();
			}
			if($("#flexTable_c").length>0){
				$("#flexTable_c").find("tbody").children().remove();
			}
			if($("#flexTable_a").length>0){
				$("#flexTable_a").find("tbody").children().remove();
			}
			break;
		case 'r':
			if(self){
				$("#flexTable_"+type).flexReload();
			} 
			if($("#flexTable_c").length>0){
				$("#flexTable_c").find("tbody").children().remove();
			}
			if($("#flexTable_a").length>0){
				$("#flexTable_a").find("tbody").children().remove();
			}
			break;
		case 'c':
			if(self) $("#flexTable_"+type).flexReload();
			break;
		case 'a':
			if(self) $("#flexTable_"+type).flexReload();
			break;
		default:
			$("#flexTable_p").flexReload();
			$("#flexTable_r").find("tbody").children().remove();
			$("#flexTable_c").find("tbody").children().remove();
			break;
		}
}
