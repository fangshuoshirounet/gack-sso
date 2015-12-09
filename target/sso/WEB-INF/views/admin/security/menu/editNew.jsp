<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<div class="easyui-layout" data-options="fit:true">
    <div class="info-form" data-options="region:'center'">
    <form method="post" id="addForm">
            <fieldset>
                <legend>菜单信息</legend>
                <table class="table-info">
                    <tr><th>
                        <label class="required">菜单目录</label></th><td>
                        <input name="menuCatalogId" id="menuCatalogIdOfTree" data-options="multiple:false,required:true"/  style="width:182px">
                    </td></tr>
                    <tr><th>
                        <label class="required">菜单名称</label></th><td>
                        <input class="easyui-validatebox" type="text" name="menuName" required="true"/>
                    </td></tr>
                    <tr><th>
                        <label class="required">菜单编码</label></th><td>
                        <input class="easyui-validatebox" type="text" name="menuCode" required="true"/>
                    </td></tr>
                    <tr><th>
                        <label>菜单描述</label></th><td>
                        <input  type="text" data-options="required:false" name="menuDesc"/>
                    </td></tr>
                    <tr><th>
                        <label class="required">功能链接</label></th><td>
                        <input class="easyui-validatebox" type="text" name="menuUrl" required="true"/>
                    </td></tr>
                     <tr><th>
                        <label class="required">排序</label></th><td>
                        <input class="easyui-numberbox" type="text" name="orderNo" required="true"/>
                    </td></tr>
                   <tr class="row2"><th>
                        <label >菜单操作：</label></th><td>
						<span style="display:inline-block;width:210px;">
							<c:forEach items="${operationList}" var="operation" varStatus="status">
								<c:choose>
									<c:when test="${operation.operationId == '1'}">
										<span style="display:inline-block;width:93px"><input type="checkbox" style="width:20px"  name="operationIds" readonly="readonly" onclick='this.checked=!this.checked' checked="checked" value="${ operation.operationId}">${operation.operationName}</input></span>
									</c:when>
									<c:otherwise >
										<span style="display:inline-block;width:93px"><input type="checkbox" style="width:20px"  name="operationIds"  value="${ operation.operationId}">${operation.operationName}</input></span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</span>
                    </td></tr>
                </table>
            </fieldset>
    </form>
    </div>
	<div class="buttons" data-options="region:'south'">
	    <button class="button" id="btnSubmit">确 定</button>
		<button class="button" id="btnCancel">取 消</button>
	</div>
</div>
<script type="text/javascript">
//<![CDATA[
$(".info-form input[type='text']").css({"width": "180px"});
    $(function () {
        //验证
        $('#addForm').ajaxForm({
            url: "${ctx_admin}/security/menu",
            beforeSubmit:function(formData, jqForm){
                return jqForm.form('validate');
            },
            success: function (data) {
            	top.closeDialog();
            	getDtt().refresh();
                $.messager.show({title: '温馨提示:', msg: data.message, timeout: 1000, showType: 'slide'});
            }
        });
        //构建树
        $("#menuCatalogIdOfTree").combotree({
            url: "${ctx_admin}/security/menu/treejson"
        });
        //表单提交
        $("#btnSubmit").click(function () {
            $("#addForm").submit();
        });
        //重置页面
        $("#btnCancel").click(function () {
            top.closeDialog();
        });
    });
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp" %>