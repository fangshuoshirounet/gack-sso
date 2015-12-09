<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		 <div class="info-form" data-options="region:'center'">
		 	<fieldset>
		 		 <legend>操作基本信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label class="norequired">操作名称</label></th><td>
						${model.operationName}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired">操作编码</label></th><td>
						${model.operationCode}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired">状态</label></th><td>
						<tags:showlabel-enumerate columnName="STS" tableName="OPERATION" value="${model.sts}"></tags:showlabel-enumerate>&nbsp;
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