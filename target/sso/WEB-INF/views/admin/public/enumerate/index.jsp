<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">
    <!-- 上部:查询表单 -->
    <div data-options="region:'north'" class="search">
        <fieldset>
            <legend>筛选条件</legend>
            <form id="searchForm">
                <input type="hidden" name="parentId" id="parentId"/>
                <table class="search-table">
                    <tr>
                        <th>表名称</th><td><input type="text" maxlength="30" name="tableName"/></td>
                        <th>字段名称</th><td><input type="text" maxlength="30" name="columnName"/></td>
						 <td colspan="4" class="search-buttons">
						 	<shiro:hasPermission name="public:enumerate:query">
                            <input type="button" class="button" id="qryBtn" value="查询"/>
                            <input type="button" class="button" id="clearBtn" value="清除"/>
                        	</shiro:hasPermission>
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </div>
    <!-- 中间:显示列表-->
    <div id="flexgrid" data-options="region:'center'" class="content">
        <table id="flexTable"></table>
    </div>
    <!-- 底部:操作按钮-->
    <div class="buttons" data-options="region:'south'">
        <shiro:hasPermission name="public:enumerate:view">
		  <input type="button" class="button" name='showBtn' id="showBtn" value="查看"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="public:enumerate:add">
          <input type="button" class="button" id="addBtn" value="新建"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="public:enumerate:edit">
			<input type="button" class="button" id="editBtn" value="修改"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="public:enumerate:delete">
			<input type="button" class="button" id="delBtn" value="注销"/>
		</shiro:hasPermission>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
	//刷新flexgird 数据
    function refresh() {
        $("#flexTable").flexReload();
    }
    $(function () {
        $("#flexTable").flexigrid({
            url: "${ctx_admin}/public/enumerate.json?search=true",
            hiddenArea:[{name:"version"}],//隐藏标识 在页面不显示
			title:'数据字典列表',
            colModel: [
                {display: '表名称',name: 'tableName',width: 140,sortable: true,align: 'center',hide: false,toggle: false},
                {display: '字段名称',name: 'columnName',width: 140,sortable: false,align: 'center'},
                {display: '字段编号',name: 'stsId',width: 140,sortable: true,align: 'center'},
                {display: '字段描述',name: 'stsWords',width: 140,sortable: true,align: 'center'},
                {display: '排列序号',name: 'orderId',width: 140,sortable: true,align: 'center'},
				{
					display: '操作',
					name: 'operate',
					width: 130,
					align: 'center',
					operateColumn: [
						            {html: '<shiro:hasPermission name="public:enumerate:view"><a  name="showLink" href="#">查看</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="public:enumerate:edit"><a name="editLink" href="#">修改</a></shiro:hasPermission>'},
									{html: '<shiro:hasPermission name="public:enumerate:delete"><a name="delLink" href="#">注销</a></shiro:hasPermission>'}
								   ]
				}
              ]
        });
        //查询
        $("#qryBtn").click(function(){$("#flexTable").flexRefresh();});
        //重置
        $("#clearBtn").click(function(){$("#searchForm")[0].reset();});
        //新建
        $("#addBtn").click(function () {
            var url = '${ctx_admin}/public/enumerate/new';
			top.openDialog({href: url,resizable : false,height: 258,width: 380,title: "添加数据字典",modal : true});
        });
        //修改
        $("#editBtn,[name='editLink']").live("click",function () {
        	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
				var key = getSelected({nameGroup:'tableName,columnName,stsId',currentObj:$(this)});
				var url = '${ctx_admin}/public/enumerate/' + key + '/edit';
				top.openDialog({href: url,resizable : false,height: 258,width: 380,title: "修改数据字典",modal : true});
			}
        });
        //查看
        $("#showBtn,[name='showLink']").live("click",function (event,data) {
        	if (isSelected({isMultiSelect:false,currentObj:$(this)})) {//验证单行
				var key = getSelected({nameGroup:'tableName,columnName,stsId',currentObj:$(this)});
				var url = '${ctx_admin}/public/enumerate/' + key;
				top.openDialog({href: url,resizable : false,height: 228,width: 380,title: "查看数据字典",modal : true});
			}
        });
        //删除delBtn
        $("#delBtn,[name='delLink']").live("click",function (event,data) {
        	var keys = getSelected({nameGroup:'tableName,columnName,stsId,version',currentObj:$(this)});
        	if (isSelected({isMultiSelect:true,currentObj:$(this)})) {//验证多行
				$.messager.confirm('删除提示', '您是否确定永久删除选中数据?', function(r){
					if (r){
						
						var url = "${ctx_admin}/public/enumerate/" + keys+ "?_method=DELETE";
						$.ajax({
							type:'post',
							url:url,
							data:{id:keys},  
							dataType:'json',
							success:function(data){
								$.messager.show({title: '温馨提示:', msg : data.message, timeout: 10000, showType: 'slide'});
								$("#flexTable").flexReload();
							}
						}); 
					}
				});
			}
        });
    });
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp" %>