<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true"> 
    <!-- 上部:查询表单 --> 
    <div data-options="region:'north'" class="search" >
      <!-- 查询表单 -->
	  <fieldset>
               <legend>筛选条件</legend>
               <form id="searchForm">
                   <input type="hidden" name="parentId" id="parentId"/>
                   <table class="search-table">
                       <tr>
                           <th>系统配置标识</th><td><input type="text" name="sysConfigId" maxlength="20"/></td>
                           <th>名称</th><td><input type="text" class="inputbox-text" name="name"/></td>
			               <td colspan="4" class="search-buttons">
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
		<shiro:hasPermission name="public:sysconfig:view">
  				<input type="button" class="button" id="showBtn" value="查看"/>
		</shiro:hasPermission>
              <shiro:hasPermission name="public:sysconfig:add">
  				<input type="button" class="button" id="addBtn" value="新建"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="public:sysconfig:edit">
  				<input type="button" class="button" id="editBtn" value="修改"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="public:sysconfig:delete">
  				<input type="button" class="button" id="delBtn" value="注销"/>
		</shiro:hasPermission>
     </div>
</div>	
<script type="text/javascript">
//<![CDATA[
 $(function() {
	$("#flexTable").flexigrid({
			url:'${ctx_admin}/public/sysconfig?search=true',
			title: "系统配置查询列表",
			colModel : [{display : '系统配置标识',name : 'sysConfigId',width : 140,sortable : false,align : 'center'},
			            {display : '名称',name : 'name',width : 140,sortable : false,align : 'center'},
				        {display : '配置类型 ',name : 'type',width : 140,sortable : false,align : 'center'}, 
				        {display : '当前值',name : 'curValue',width : 135,sortable : false,align : 'center'},
				        {
							display: '操作',
							name: 'operate',
							width: 135,
							align: 'center',
							operateColumn: [
								            {html: '<shiro:hasPermission name="public:sysconfig:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
											{html: '<shiro:hasPermission name="public:sysconfig:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
											{html: '<shiro:hasPermission name="public:sysconfig:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
										   ]
						}
				       ]
	});
 });
    //查询
	$("#qryBtn").click(function(){
		$("#flexTable").flexRefresh();
	});
	//重置
	$("#clearBtn").live('click', function () {
        $("#searchForm")[0].reset();
	});
	//新建
	$("#addBtn").click(function () {
		var url='${ctx_admin}/public/sysconfig/new';
		top.openDialog({ href:url,resizable : false,height:310,width: 380,title: "新建系统配置信息",modal : true});
	});
	//修改
	 $("#editBtn,[name='editLink']").live("click",function () {
			  if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
			var key= getSelected({nameGroup:'sysConfigId',currentObj:$(this)});
			var url='${ctx_admin}/public/sysconfig/' + key + '/edit';
			top.openDialog({ href:url,resizable : false,height: 270, width: 380, title: "修改系统配置信息",modal : true}); 
		}
	});
	//查看
	$("#showBtn,[name='showLink']").live("click",function (event,data) {
		 if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证只能选中单行，如查看，修改
		var key=getSelected({nameGroup:'sysConfigId',currentObj:$(this)});
		    var url='${ctx_admin}/public/sysconfig/' + key;
		    top.openDialog({href:url,resizable : false,height: 263,width: 380,title: "查看系统配置信息",modal : true});
		}
	});
	//删除
	$("#delBtn,[name='delLink']").live("click",function (event,data) {
	    	var keys=getSelected({nameGroup:'sysConfigId',currentObj:$(this)});
	    	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {
			 
		        	 $.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r) {
		        		 if(!r){
		 					return;
		 				 }
		        		 var url = "${ctx_admin}/public/sysconfig/" + keys+ "?_method=DELETE";
		        		 
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