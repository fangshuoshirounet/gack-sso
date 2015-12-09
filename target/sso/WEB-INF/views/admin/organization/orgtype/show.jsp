<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

		<div class="easyui-layout" data-options="fit:true" >
			<form method="post" id="viewForm">
			 <div class="info-form" data-options="region:'center'" >
			  <fieldset >
		 		 <legend>组别类型信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label>组别类型名称</label></th><td>&nbsp;${model.name}
					</td></tr>
					<tr><th>
						<label>首字母缩写</label></th><td>&nbsp;${model.acronym}
					</td></tr> 
					<tr><th>
						<label >状态</label></th><td>
						<tags:showlabel-enumerate columnName="STS" tableName="ORG_TYPE" value="${model.sts}"></tags:showlabel-enumerate>&nbsp;
					</td></tr>
					<tr><th>
						<label >操作时间</label></th><td>&nbsp;${model.operateDate}
					</td></tr>
					<tr><th>
						<label >创建时间</label></th><td>&nbsp;${model.createDate}
					</td></tr>
					<tr class="row2"><th> 
					<label  >备注</label></th><td>
						<textarea   name="remarks" readonly>${model.remarks}</textarea>
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
//<![CDATA
$(function(){
	//关闭页面
	 $("#closeSubmitBtn").click(function(){
		  top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>