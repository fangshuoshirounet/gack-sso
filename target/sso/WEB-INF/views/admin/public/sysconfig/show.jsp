<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/common/top.jsp"%>

	<div class="easyui-layout" data-options="fit:true" >
	<form method="post" id="viewForm">
	  <div class="info-form" data-options="region:'center'">
	  	<fieldset>
	  		<legend>系统配置信息</legend>
				 <table class="table-info">
				 <tr><th> 
						<label>系统配置标识</label></th><td>
						${model.sysConfigId}&nbsp;
					</td></tr>
					<tr><th> 
						<label>名称</label></th><td>
						${model.name}&nbsp;
					</td></tr>
					<tr><th> 
						<label>配置类型</label></th><td>
						<tags:showlabel-enumerate tableName="SYS_CONFIG" columnName="CONFIG_TYPE" value="${model.configType}"/>
					</td></tr>
					<tr><th> 
						<label>当前值</label></th><td>
						${model.curValue}&nbsp;
					/td></tr>
					<tr class="row2"><th>  
						<label>字段描述</label></th><td>
						<textarea name="valueDesc" readOnly="readonly" >${model.valueDesc}</textarea>
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
	$(function() {
		//关闭页面
		$("#closeSubmitBtn").click(function() {
			 top.closeDialog();
		});
	});
//]]>
</script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp"%>