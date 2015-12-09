<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		 <input name="_method" value="put" type="hidden"/>
		 <div class="info-form" data-options="region:'center'">
		 	<fieldset>
		 		 <legend>菜单目录信息</legend>
		 		 <table class="table-info">
		 		 	<tr><th>
						<label class="norequired">上级菜单目录</label></th><td>
						${model.parentCatalogName}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired">菜单目录名称</label></th><td>
						${model.catalogName}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired">状态</label></th><td>
						<tags:showlabel-enumerate columnName="STS" tableName="MENU_CATALOG" value="${model.sts}"></tags:showlabel-enumerate>&nbsp;
					</td></tr>
					<tr><th>
                        <label class="required">排序</label></th><td>
                         ${model.orderNo } 
                    </td></tr>
					<tr><th>
						<label class="norequired">操作时间</label></th><td>
						${model.operateDate}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired">创建时间</label></th><td>
						${model.createDate}&nbsp;
					</td></tr>
		 		 </table>
		 	</fieldset>
		 </div>
		 <div class="buttons" data-options="region:'south'">
		 	<input type="button" class="button" id="closeSubmitBtn" value="关闭"/>
         </div>
	</form>
</div>
<script type="text/javascript">
//<![CDATA[
//关闭页面
$(function(){
	 $("#closeSubmitBtn").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>