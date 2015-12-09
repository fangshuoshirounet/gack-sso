<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<div class="easyui-layout" data-options="fit:true">
    <!--tree-->
    <div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="区域信息" fit="true" class="tree-center">
               <table class="table-info"  id="systemAreaTree" style=""></table>
            </div>
            <div data-options="region:'south'" class="tree-buttons">
            	 
            </div>
        </div>
    </div>
    <!--content-->
    <div class="no-border" data-options="region:'center'">
        <div class="easyui-layout" data-options="fit:true">
            <!--search-->
            <div data-options="region:'north'" class="search">
                <fieldset>
                    <legend>筛选条件</legend>
                    <form id="serachForm">
                        <input type="hidden" name="parentId" id="parentId"/>
                        <table class="search-table">
                            <tr>
                                <th>区域名称</th>
                                <td><input type="text" name="name" maxlength="20"/></td>
                                <th>区域编码</th>
                                <td><input type="text" name="code" maxlength="10"/></td>
                                <td class="search-buttons">
                                	<shiro:hasPermission name="public:area:query">
	                                  	<input type="button" class="button" id="qryBtn" value="查询"/>
	                                	<input type="button" class="button" id="clearBtn" value="清除"/>
                                	</shiro:hasPermission>
                                </td>
                            </tr>
                        </table>
                    </form>
                </fieldset>
            </div>
            <!-- 下部:显示列表-->
            <div id="flexgrid" data-options="region:'center'" class="content">
                <table id="flexTable"></table>
            </div>
            <div data-options="region:'south'" class="buttons">
            	<shiro:hasPermission name="public:area:view">
                	<input type="button" class="button" id="showBtn" value="查看"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="public:area:add">
                	<input type="button" class="button" id="addBtn" value="新建"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="public:area:edit">
                	<input type="button" class="button" id="editBtn" value="修改"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="public:area:delete">
                	<input type="button" class="button" id="delBtn" value="注销"/>
                </shiro:hasPermission>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
//alert(0);
function initTree(){
    $('#systemAreaTree').tree({
        url: "${ctx_admin}/public/area/treejson?isChild=false",
        onClick: function (node) {
            $("#parentId").val(node.id);
            $("#flexTable").flexRefresh();
            if(node.attributes=="true"){
				$("#remove").hide(); 
			}else{
				$("#remove").show(); 
			}
        }
    });
}
$(function(){
    initTree();
    <%-- 区域右侧tab加载start--%>
    $("#flexTable").flexigrid({
    	title:"区域列表",
        url: '${ctx_admin}/public/area?search=true',
        hiddenArea:[{name:"areaId"}],
        colModel: [
            {display: '区域名称', name: 'name', width: 145, sortable: true, align: 'center', hide: false, toggle: false},
            {display: '区域编码', name: 'code', width: 115, sortable: true, align: 'center'},
            {display: '是否中心位置', name: 'iscenterName', width: 100, sortable: false, align: 'center'},
            {
				display: '操作',
				name: 'operate',
				width: 135,
				align: 'center',
				operateColumn: [
					            {html: '<shiro:hasPermission name="public:area:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
								{html: '<shiro:hasPermission name="public:area:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
								{html: '<shiro:hasPermission name="public:area:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
							   ]
			}
        ],
        onSuccess: function () {
            var parentId = $("#parentId").val();
            if (parentId != null && parentId.length > 0) {
                $("#flexTable tr ").each(function (n, tr) {
                    var areaId = $(tr).find("input[name='areaId']").val();
                    if (parentId == areaId) {
                        var input = $($(tr).children().eq(0));
                        var checkbox = input.find("input[type='checkbox']");
                        $(checkbox).attr("checked", "checked");
                        if (n % 2 == 0) {
                            $(tr).attr("class", "erow trSelected");
                        } else {
                            $(tr).attr("class", "trSelected");
                        }
                        return false;
                    }
                });
            }
        }
    });
    <%-- 区域右侧tab加载end--%>
    //查询
    $("#qryBtn").live('click', function () {
        $("#flexTable").flexRefresh();
    });
    //重置
    $("#clearBtn").live('click', function () {
        var parentId = $("#parentId").val();
        $("#serachForm")[0].reset();
        $("#parentId").val(parentId);
    });
});
<%-- 按钮绑定操作 start--%>
$("#insert").bind("click", function () {
    var parentId = $("#parentId").val();
    var url = '${ctx_admin}/public/area/new?parentId=' + parentId;
    top.openDialog({href: url,resizable : false,height : 500,width : 380,title: "新建系统区域",modal : true});
});

$("#edit").bind("click", function () {
    var parentId = $("#parentId").val();
    if(parentId==null||parentId.length==0){
    	 $.messager.alert('系统区域操作', '请先选择区域参数！');
    	 return;
    }
    var url = '${ctx_admin}/public/area/' + parentId + '/edit';
    top.openDialog({href: url,resizable : false,height : 505,width : 380,title: "修改系统区域",modal : true});
});
$("#remove").bind("click", function () {
    var parentId = $("#parentId").val();
    if(parentId==null||parentId.length==0){
	   	 $.messager.alert('系统区域操作', '请先选择区域参数！');
	   	 return;
    }
    var delurl = "${ctx_admin}/public/area/"+parentId+"/"+"validatedelete.json";
    
    $.getJSON(delurl, function (data){
    	 if (data.message != null) {//返回异常信息
             $.messager.alert('删除提示', data.message, '您是否确定永久删除选中数据?');
         }else{
         	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function (r) {
				if (r) {
					var url = "${ctx_admin}/public/area/" + parentId + "?_method=DELETE";
					 $.ajax({
					       type:'post',
					       url: url,
					      data:{id:parentId},  
						  dataType:'json',
					       success: function (data) { 
	        			        $.messager.show({title: '温馨提示:', msg : data.message, timeout: 1000, showType: 'slide'});
	        			        $("#flexTable").flexReload();
	        		            initTree();
	        			    }
					   });
					 
				}
         	 });
         }
    });
});
$("#view").bind("click", function () {
    var parentId = $("#parentId").val();
    if(parentId==null||parentId.length==0){
    	$.messager.alert('查看系统区域', '请先选择区域参数！');
    }else{
    	var url = '${ctx_admin}/public/area/' + parentId;
    	top.openDialog({href: url,resizable : false,height : 518,width : 380,title: "查看系统区域",modal : true});
    }
});
<%--parentId是左侧区域树传递过来的。在点击table时候，是不能改变parentId的值--%>
<%--按钮绑定操作 end--%>
//新建（如果自己选中了，则按照自己的来，如果没有选中，则按照左侧的parentId来做父级）
$("#addBtn").click(function () {
    //判断是否选择table
    var len = $('.trSelected').length;
    if (len == 0) {
        //说明没有选择table
        var parentId = $("#parentId").val();
        if (parentId != null && parentId.length > 0) {
            var url = '${ctx_admin}/public/area/new?parentId=' + parentId;
            top.openDialog({href: url,resizable : false,height : 500,width : 380,title: "新建系统区域",modal : true});
        } else {
            var url = '${ctx_admin}/public/area/new';
            top.openDialog({href: url,resizable : false,height : 500,width : 380,title: "新建系统区域",modal : true});
        }
    } else if (len == 1) {
        //说明选择了table
        var parentId = getSelectRowByName("areaId");
        var url = '${ctx_admin}/public/area/new?parentId=' + parentId;
        top.openDialog({href: url,resizable : false,height : 500,width : 380,title: "新建系统区域",modal : true});
    } else {
        //说明选择多个table的checkbox
        $.messager.alert('新建系统区域', '请先选择区域参数！');
    }
});

//修改
$("#editBtn,[name='editLink']").live("click",function () {
		if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key= getSelected({nameGroup:'areaId',currentObj:$(this)});
		var url = '${ctx_admin}/public/area/' + key + '/edit';
	    top.openDialog({href: url,resizable : false,height : 505,width : 380,title: "修改系统区域",modal : true});
	}
});
//查看
$("#showBtn,[name='showLink']").live("click",function (event,data) {
		if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'areaId',currentObj:$(this)});
	    var url = '${ctx_admin}/public/area/' + key;
	    top.openDialog({href: url,resizable : false,height : 518,width : 380,title: "查看系统区域",modal : true});
	}
});
//删除
 $("#delBtn,[name='delLink']").live("click",function (event,data) {
	    var keys=getSelected({nameGroup:'areaId',currentObj:$(this)});
	    if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
	    var delurl = "${ctx_admin}/public/area/"+keys+"/"+"validatedelete";
	    $.getJSON(delurl, function (data) {
	    	 if (data.message != null) {//返回异常信息
	             $.messager.alert('删除提示', data.message, '您是否确定永久删除选中数据?');
	         }else{
	        	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function (r) {
	        	        if (r) {
	        	            var url = "${ctx_admin}/public/area/" + keys + "?_method=DELETE";
	        	            $.ajax({
	 					       type:'post',
	 					       url: url,
	 					      data:{id:keys},  
							  dataType:'json',
	 					       success: function (data) { 
		        			        $.messager.show({title: '温馨提示:', msg : data.message, timeout: 1000, showType: 'slide'});
		        			        $("#flexTable").flexReload();
		        		            initTree();
		        			    }
	 					   });
	        	        }
	        	 });
	         }
	    });
	}
});
//刷新flexgird 数据
function refresh() {
    $("#flexTable").flexReload();
}
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp" %>