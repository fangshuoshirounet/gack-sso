<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">  
    <!-- 区域左侧树 -->
    <div class="tree" data-options="region:'west',split:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" title="组别" fit="true" class="tree-center">
                <table class="table-info" id="organizationTree"></table> 
            </div>
            <div class="tree-buttons" data-options="region:'south'">
          
            </div>
        </div>
    </div> 
    <!-- 中间区域 --> 	
   <div data-options="region:'center'" class="no-border">
    	<div class="easyui-layout" data-options="fit:true"> 
    		<div data-options="region:'north'" class="search" >
    		<!-- 查询表单 -->
    		<fieldset>
                    <legend>筛选条件</legend>
                    <form id="serachForm">
                        <input type="hidden" name="parentId" id="parentId"/>
                        <table class="search-table">
                            <tr>
                                <th>组别名称</th>
                                <td><input type="text" name="name" maxlength="20"/></td>  
                                <td class="search-buttons">
                                	<shiro:hasPermission name="organization:organization:query">
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
			<div class="buttons" data-options="region:'south'">
				<shiro:hasPermission name="organization:organization:view">
    				<input type="button" class="button" id="showBtn" value="查看"/>
				</shiro:hasPermission>
                <shiro:hasPermission name="organization:organization:add">
    				<input type="button" class="button" id="addBtn" value="新建"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="organization:organization:edit">
    				<input type="button" class="button" id="editBtn" value="修改"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="organization:organization:delete">
    				<input type="button" class="button" id="delBtn" value="注销"/>
				</shiro:hasPermission>
            </div>
	  </div>	
    </div>  
</div>  

<script type="text/javascript">
//<![CDATA[
function initTree(){
	$('#organizationTree').tree({  
	   	url:"${ctx_admin}/organization/organization/treejson",
	   	onClick: function(node){
	   		$("#parentId").val(node.id);
	   		$("#flexTable").flexRefresh(); 
		 	if(node.attributes=="true"){
				$("#remove").css("display","none"); 
		 	}else{
				$("#remove").css("display",""); 
		 	}
		}
	});  
}
$(function() { 
	<%-- 区域左侧树 加载start--%>
	initTree();
	<%-- 区域左侧树 加载end--%>
	<%-- 区域右侧tab加载start--%>
	$("#flexTable").flexigrid({
		title:"组别列表",
		url : '${ctx_admin}/organization/organization?search=true',
		hiddenArea:[{name:"orgId"}],
		colModel: [
            {display: '组别名称', name: 'name', width: 130, sortable: true, align: 'center', hide: false, toggle: false},
            {display: '首字母缩写', name: 'acronym', width: 125, sortable: false, align: 'center'},
            {display: '组别类型', name: 'orgTypeName', width: 125, sortable: false, align: 'center'},
            {display: '排序方案', name: 'planId', width: 125, sortable: false, align: 'center'},
            {display: '是否跨组查看', name: 'showFlagName', width: 125, sortable: false, align: 'center'}, 
            {display: '备注', name: 'remarks', width: 125, sortable: false, align: 'center'}, 
            {
				display: '操作',
				name: 'operate',
				width: 120,
				align: 'center',
				operateColumn: [
					            {html: '<shiro:hasPermission name="organization:organization:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
								{html: '<shiro:hasPermission name="organization:organization:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
								{html: '<shiro:hasPermission name="organization:organization:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
							   ]
			}
        ],
		onSuccess :function(){
   			var parentId = $("#parentId").val();
			if(parentId!=null&&parentId.length>0){
				$("#flexTable tr ").each(function (n,tr){
					 var orgId = $(tr).find("input[name='orgId']").val();
		   			 if(parentId==orgId){
		   				var input = $($(tr).children().eq(0));
		   				var checkbox = input.find("input[type='checkbox']");
		   				$(checkbox).attr("checked","checked");
		   				if(n%2==0){
		   					$(tr).attr("class","erow trSelected");
		   				}else{
		   					$(tr).attr("class","trSelected");
		   				}
		   				return false;
		   			}
	   	 		});
			}
		}
	});
	
	<%-- 区域右侧tab加载end--%>
	
});
	//查询
	$("#qryBtn").click(function() {
		$("#flexTable").flexRefresh();
	});

	//重置
	$("#clearBtn").click(function() {
		var  parentId = $("#parentId").val();
		$("#serachForm")[0].reset();
		$("#parentId").val(parentId);
		$("#orgtype").combobox('setValue','');
	});
	  
	$("#addBtn").click(function () {
		var url='${ctx_admin}/organization/organization/new';
		top.openDialog({ href:url,resizable : false,height : 410,width : 380, title: "新建组别",modal : true});
	});

	//修改
	 $("#editBtn,[name='editLink']").live("click",function () {
			  if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key= getSelected({nameGroup:'orgId',currentObj:$(this)});
			var url='${ctx_admin}/organization/organization/' + key + '/edit';
			top.openDialog({ href:url,resizable : false,height : 410,width : 380, title: "修改组别",modal : true});
		}
	});
	//查看
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key=getSelected({nameGroup:'orgId',currentObj:$(this)});
			var url='${ctx_admin}/organization/organization/' + key;
			top.openDialog({ href:url,resizable : false,height : 445,width : 380, title: "查看组别",modal : true});
		}
	});
	//删除
	 $("#delBtn,[name='delLink']").live("click",function (event,data) {
	    	var keys=getSelected({nameGroup:'orgId',currentObj:$(this)});
	    	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {
	    	var delurl = "${ctx_admin}/organization/organization/"+keys+"/"+"validatedelete"; 
		     $.getJSON(delurl, function (data) {
		    	 if (data.message != null) {//返回异常信息
		             $.messager.alert('删除提示', data.message, '您是否确定永久删除选中数据?');
		         }else{
		        	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function (r) {
		        	        if (r) {
		        	            var url = "${ctx_admin}/organization/organization/" + keys + "?_method=DELETE";
		        			 
		        	            $.ajax({
									type:'post',
									url:url,
									data:{id:keys},  
									dataType:'json',
									success:function(data){
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
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>