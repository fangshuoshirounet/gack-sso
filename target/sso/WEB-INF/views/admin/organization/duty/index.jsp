<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true" >
<!-- 上部:查询表单 -->
    <div data-options="region:'north'" class="search">
	      <fieldset>
            <legend>筛选条件</legend>
			<form id="serachForm" method="post" >
				<table class="search-table">
					<tr>
						<th>岗位名称</th>
						<td><input type="text" maxlength="20" name="name" /></td>
	                    <td class="search-buttons">
	                    	<shiro:hasPermission name="organization:duty:query">
	                        <input type="button" class="button" id="qryBtn" value="查询"/>
	                        <input type="button" class="button" id="resetBtn" value="清除"/>
	                    	</shiro:hasPermission>
	                    </td>
					</tr>
				</table>
			</form>
	</fieldset>
	</div>
<!-- 显示列表-->
	<div id="flexgrid" data-options="region:'center'" class="content">
        <table id="flexTable"></table>
    </div>
<!-- 底部:操作按钮-->
            <div class="buttons" data-options="region:'south'">
            	<shiro:hasPermission name="organization:duty:view">
                <input type="button" class="button" id="showBtn" value="查看"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="organization:duty:add">
                <input type="button" class="button" id="addBtn" value="新建"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="organization:duty:edit">
                <input type="button" class="button" id="editBtn" value="修改"/>
                </shiro:hasPermission>
                <shiro:hasPermission name="organization:duty:delete">
                <input type="button" class="button" id="delBtn" value="注销"/>
                </shiro:hasPermission>
           </div>
</div>
<script type="text/javascript">
//<![CDATA[
	$(function() { 
		$(".info-form input[type='text']").css({"width": "180px"});
		$("#flexTable").flexigrid({
			url : '${ctx_admin}/organization/duty?search=true',
			title :'岗位列表',
			hiddenArea:[{name:"dutyId",name:"version"}],//隐藏标识 在页面不显示
			colModel : [ 
			   {display : '岗位名称',name : 'name',width : 175,sortable : true,align : 'center',hide : false},
			   {display : '状态',name : 'stsName',width : 175,sortable : false,align : 'center',hide : false},
			   {
					display: '操作',
					name: 'operate',
					width: 175,
					align: 'center',
					operateColumn: [
						            {html: '<shiro:hasPermission name="organization:duty:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="organization:duty:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="organization:duty:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
								   ]
				}
			   ]
		});
//查询
		$("#qryBtn").live('click', function () {
       	 $("#flexTable").flexRefresh();
		 });
//重置
		$("#resetBtn").click(function() {
			$("#serachForm")[0].reset();
		});
//新建
		$("#addBtn").click(function () {
			var url = '${ctx_admin}/organization/duty/new';
			top.openDialog({href : url,resizable : false,height : 198,width : 380,title: "添加岗位",modal : true});
		});
//修改
		 $("#editBtn,[name='editLink']").live("click",function () {
	        	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
					var key = getSelected({nameGroup:'dutyId',currentObj:$(this)});
					var url = '${ctx_admin}/organization/duty/' + key + '/edit';
					top.openDialog({href : url,resizable : false,height : 198,width : 380,title : "修改岗位",modal : true});
				}
	        });
//查看
	    $("#showBtn,[name='showLink']").live("click",function (event,data) {
        	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
				var key = getSelected({nameGroup:'dutyId',nameSymbol:"-",currentObj:$(this)});
				var url = '${ctx_admin}/organization/duty/' + key;
				top.openDialog({href : url,resizable : false,height : 267,width : 380,title : "查看岗位",modal : true});
			}
        });
//删除
	    $("#delBtn,[name='delLink']").live("click",function (event,data) {
        	var keys = getSelected({nameGroup:'dutyId,version',currentObj:$(this)});
        	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证多行
				$.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r){
					if (r){
						var url = "${ctx_admin}/organization/duty/" + keys + "?_method=DELETE";
						$.ajax({
							type:'post',
							url:url,
							data:{id:keys},  
							dataType:'json',
							success:function(data){
								$.messager.show({title: '温馨提示:',
									msg : data.message,timeout: 1000,showType: 'slide'
								});
								$("#flexTable").flexReload();
							}
						});
					}
				});
			}
        });
	});
//刷新flexgird 数据
	function refresh() {
		$("#flexTable").flexReload();
	}
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>