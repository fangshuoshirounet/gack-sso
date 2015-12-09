/****************************
 ****定义flexgrid默认高度
 *****************************/
var flexgirdHeight = 400;
function reSetFGH(){
    flexgirdHeight = $("div#flexgrid").height();
    if ($("div#top.nav").length <= 0) {
        flexgirdHeight = flexgirdHeight - 83;
    }
    else {
        flexgirdHeight = flexgirdHeight - 53;
    }
} 
$(function(){ 
    if ($("div#flexgrid")) {
        reSetFGH();
    } 
});

/**------------------------**
 **Flexigrid tool Functions**
 **------------------------**/

/**
 * 根据元素的name获取选中行的数据
 * @param {Object} nameGroup 筛选元素Name，以逗号隔开
 * @param {Object} nameSymbol name的连接分割符
 * @param {Object} lineSymbol 选中行的连接分割符
 * @param {Object} selectObj 选中行
 */
function getSelectRowByName(nameGroup, nameSymbol, lineSymbol){
    var selectValues = "";
    if (typeof(nameSymbol) == "undefined") { nameSymbol = ",";}
    if (typeof(lineSymbol) == "undefined") {lineSymbol = ",";}
    
    $('.trSelected').each(function(index, element){//循环选中行
        var nameList = nameGroup.split(",");
        var keys = "";
        for(var i = 0; i < nameList.length; i++) {
            var name = nameList[i];
            var temp = $(this).find("[name=" + name + "]").text();
            if (temp == "" || typeof(temp) == "undefined") {
                temp = $(this).find("[name=" + name + "]").val();
            }
            keys += temp + nameSymbol;
        }
        keys = keys.substr(0, keys.length - 1);
        selectValues += keys + lineSymbol;
    });
    selectValues = selectValues.substr(0, selectValues.length - 1);
    return selectValues;
}

/**
 * 根据元素的name获取选中行的数据
 * options选项：
 * @param {Object} nameGroup 筛选元素Name，以逗号隔开
 * @param {Object} nameSymbol name的连接分割符
 * @param {Object} lineSymbol 选中行的连接分割符
 * @param {Object} elementObj 选中行
 */
function getSelected(options){
    var nameGroup = options.nameGroup, nameSymbol = ",", lineSymbol = "-", selectLine = "", lineIndex = "";
    var selectValues = "";
    var selectRow = null;
    if (options.currentObj.attr("lineIndex")) {
        lineIndex = options.currentObj.attr("lineIndex");
        selectRow = $('.flexigrid .bDiv table tr:eq(' + lineIndex + ')');
    }
    else {
        selectRow = $('.trSelected');
    }
    if (options.nameSymbol) {
        nameSymbol = options.nameSymbol;
    }
    if (options.lineSymbol) {
        lineSymbol = options.lineSymbol;
    }
    selectRow.each(function(index, element){//循环选中行
        var nameList = nameGroup.split(",");
        var keys = "";
        for (i = 0; i < nameList.length; i++) {
            var name = nameList[i];
            var temp = $(this).find("[name=" + name + "]").text();
            if (temp == "" || typeof(temp) == "undefined") {
                temp = $(this).find("[name=" + name + "]").val();
            }
            keys += temp + nameSymbol;
        }
        keys = keys.substr(0, keys.length - 1);
        selectValues += keys + lineSymbol;
    });
    selectValues = selectValues.substr(0, selectValues.length - 1);
    return selectValues;
}

/**验证是否选中
 * options:
 * @param {Object} isMultiSelect是否支持选中多行默认值false:只选中单行
 * @param {Object} currentObj 当前对象:传:$(this)即可
 */
function isSelected(options){
    var isMultiSelect = false;
	if(options.isMultiSelect){
		isMultiSelect=options;
	}
    var isSelect = false;
    if (options.currentObj&&options.currentObj.attr("lineIndex")) {
        isSelect = true;
    }
    if (!isSelect) {
		var len = $('.trSelected').length;
        if (typeof(isMultiSelect) == "undefined") {
            Multiline = false;//默认选中一行
        }
        if (!isMultiSelect) {//只能选中单行
            if (len != 1) {
                jQuery.messager.alert('提示:', '请必须选择一行!', 'info');
                return;
            }
        }
        else {
            if (len == 0) {
                jQuery.messager.alert('提示:', '请至少选中一行!', 'info');
                return;
            }
        }
    }
    return true;
}


/**
 * 验证是否按照要求选中行
 * @param {Object} Multiline是否支持选中多行
 * @param {Object} 默认值false:只选中单行
 */
function validateSelected(isMultiSelect/*是否支持选中多行,默认值false:只选中单行 */){
    var len = $('.trSelected').length;
    if (typeof(isMultiSelect) == "undefined") {
        Multiline = false;//默认选中一行
    }
    if (!isMultiSelect) {//只能选中单行
        if (len != 1) {
            jQuery.messager.alert('提示:', '请必须选择一行!', 'info');
            return;
        }
    }
    else {
        if (len == 0) {
            jQuery.messager.alert('提示:', '请至少选中一行!', 'info');
            return;
        }
    }
    return true;
}
