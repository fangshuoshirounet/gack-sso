<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/common/top.jsp" %>

<!-- 查询列表页面-->
<div class="easyui-layout" data-options="fit:true">
    <!-- 中间:显示列表-->
    <div id="flexgrid" data-options="region:'center'" class="content">
        <table id="flexTable">
        </table>
    </div>
    <!-- 底部:操作按钮-->
    <div class="buttons" data-options="region:'south'">
		<input type="button" class="button" id="cacherefreshBtn" value="刷新"/>
        <input type="button" class="button" id="allcacherefreshBtn" value="全部刷新"/>
    </div>
</div>
<script type="text/javascript">
//<![CDATA[
    $(function () {
        $("#flexTable").flexigrid({
            url: "${ctx_admin}/public/cacherefresh?search=true",
			title:'缓存列表',
			autoload : true,
			usepager: false,
			height : flexgirdHeight+30,
            colModel: [
                {
                    display: '缓存名',
                    name: 'cacheName',
                    width: 500,
                    sortable: true,
                    align: 'center',
                    hide: false,
                    toggle: false
                }
            ]
        });
        //刷新选中缓存
        $("#cacherefreshBtn").click(function () {
        	if (validateSelected(true)) {
        		$.messager.confirm('温馨提示', '您是否确定刷新选中缓存?', function(r){
        			if(r)
        			{
	        			var cacheNames = getSelectRowByName("cacheName");
	        			var random = Math.floor(Math.random()*1000+1);
	        			var validateurl = "${ctx_admin}/public/cacherefresh/recachebyname?cacheNames="+cacheNames+"&random="+random;
	    			    $.getJSON(validateurl, function (data) {
	    			    	if(data.message!=null){
	    			    		$.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
	    			    	}
	    			    });
        			}
            	});
        	}
        });
        //刷新全部缓存
        $("#allcacherefreshBtn").click(function () {
        	$.messager.confirm('温馨提示', '您是否确定刷新全部缓存?', function(r){
	    		if(r)
	        	{	
	        		var random = Math.floor(Math.random()*1000+1);
	    			var validateurl = "${ctx_admin}/public/cacherefresh/reallcache?random="+random;
				    $.getJSON(validateurl, function (data) {
				    	if(data.message!=null){
				    		$.messager.show({title : '温馨提示:',msg : data.message,timeout : 2000,showType : 'slide'});
				    	}
				    });
	        	}
        	});
        });
    });
  //]]>    
 </script>
<%@ include file="/WEB-INF/views/admin/common/bottom.jsp" %>