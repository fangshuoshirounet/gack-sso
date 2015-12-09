<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

<div class="easyui-layout" data-options="fit:true">
	<form method="post" id="viewForm">
		 <div class="info-form" data-options="region:'center'">
		 	<fieldset>
		 		 <legend>组别信息</legend>
		 		 <table class="table-info">
		 		    <tr><th>
						<label>上级组别</label></th><td>
						${model.parentName}&nbsp;
					</td></tr>
					<tr><th>
						<label>组别名称</label></th><td>
						${model.name}&nbsp;
					</td></tr>
					<tr><th>
						<label>首字母缩写</label></th><td>
						${model.acronym}&nbsp;
					</td></tr>
					<tr><th>
						<label>组别类型</label></th><td>
						${model.orgTypeName}&nbsp;
					</td></tr>   
						<tr><th>
					<label>排序方案</label></th><td>
					${model.planName}&nbsp;
					</td></tr>
					<tr><th>
						<label>是否跨组查看</label></th><td>  ${model.showFlagName}
					</td></tr>
					
					<tr><th>
						<label>排序位置</label></th><td>
						${model.orderNo}&nbsp;
					</td></tr> 
					<tr><th>
						<label>状态</label></th><td>
						${model.stsName}
						&nbsp;
					</td></tr>
					<tr><th>
						<label>创建时间</label></th><td>
						${model.createDate}&nbsp;
					</td></tr>
					<tr><th>
						<label>操作时间</label></th><td>
						${model.operateDate}&nbsp;
					</td></tr>
					<tr class="row2"><th> 
						<label>备注</label></th><td>
						<textarea name="remarks" readOnly="readonly" >${model.remarks}</textarea>
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
$(function(){
	$(".info-group input:not([type='radio']):not([type='checkbox'])").css({"width": "180px"});
	//关闭页面
	 $("#closeSubmitBtn").click(function(){
	 	  top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>