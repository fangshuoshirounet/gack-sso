<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

       <div class="easyui-layout" data-options="fit:true">
	     <form method="post" id="viewForm">
			<div class="info-form" data-options="region:'center'">
			  <fieldset>
		 		 <legend>菜单信息</legend>
		 		 <table class="table-info">
					<tr><th>
						<label class="norequired" >菜单名称</label></th><td>${model.menuName}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired" >菜单编码</label></th><td>${model.menuCode}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired" >菜单描述</label></th><td>${model.menuDesc}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired" >功能链接</label></th><td>${model.menuUrl}
					</td></tr>
					<tr><th>
						<label class="norequired" >状态</label></th><td>
						<tags:showlabel-enumerate columnName="STS" tableName="MENU" value="${model.sts}"></tags:showlabel-enumerate>&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired" >操作时间</label></th><td>${model.operateDate}&nbsp;
					</td></tr>
					<tr><th>
						<label class="norequired" >创建时间</label></th><td>${model.createDate}&nbsp;
					</td></tr>
					<tr><th>
                        <label class="required">排序</label></th><td>
                        ${model.orderNo }
                    </td></tr>
					<tr class="row2"><th>
                        <label >菜单操作：</label></th><td>
						<span style="display:inline-block;width:210px;">
							<c:forEach items="${operationList}" var="operation" varStatus="status">
								<c:choose>
									<c:when test="${operation.operationId == '1'}">
										<span style="display:inline-block;width:93px"><input  style="width:20px" type="checkbox" onclick='this.checked=!this.checked' name="operationIds" readonly="readonly" checked="checked" value="${ operation.operationId}">${operation.operationName}</input></span>
									</c:when>
									<c:when test="${operation.checked}">
										<span style="display:inline-block;width:93px"><input style="width:20px" type="checkbox" onclick='this.checked=!this.checked' name="operationIds" checked="checked"  value="${ operation.operationId}">${operation.operationName}</input></span>
									</c:when>
									<c:otherwise  >
										<span style="display:inline-block;width:93px"><input   style="width:20px" type="checkbox" onclick='this.checked=!this.checked' name="operationIds" value="${ operation.operationId}">${operation.operationName}</input></span>
									</c:otherwise>
								</c:choose>
								<c:if test="${status.count % 2 == 0}">
									<br/>
								</c:if>
							</c:forEach>
						</span>
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
		//关闭页面
	 $("#closeSubmitBtn").click(function(){
		 top.closeDialog();
	 }); 
});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>