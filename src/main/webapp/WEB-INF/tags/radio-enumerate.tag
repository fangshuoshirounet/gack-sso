<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="citic.gack.sso.base.cache.CacheManager" %>
<%@ tag import="citic.gack.sso.entity.SysEnumerate" %>
<%@ tag import="citic.gack.sso.utils.ConstantsUtils" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="removevalue" type="java.lang.String" required="false" %>
<%@ attribute name="tableName" type="java.lang.String" required="true" %>
<%@ attribute name="columnName" type="java.lang.String" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    CacheManager cacheManager = CacheManager.getInstance();
    List<SysEnumerate> statusList = (List<SysEnumerate>) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idlist.enumerate", tableName + "." + columnName);

    request.setAttribute("statusList", statusList);
%>
 <c:forEach items="${statusList}" var="status">
 
 <c:choose>
 	<c:when test="${removevalue!= null && !(empty removevalue)}"> 
 		<c:choose>
 		<c:when test="${status.stsId == removevalue }">
	    </c:when>
	  	<c:when test="${status.stsId == value }">
	    	<input class="easyui-validatebox" type="radio"   name="${name}"  value="${status.stsId}" checked="checked" style="width:10px;"/>${status.stsWords}
	    </c:when>
	    <c:when test="${required }">
	    	<input class="easyui-validatebox" type="radio"   name="${name}"  value="${status.stsId}" checked="checked"  style="width:10px;"/>${status.stsWords}
	    </c:when>
	    <c:otherwise>
	    	<input class="easyui-validatebox" type="radio" name="${name}"  value="${status.stsId}"  style="width:10px;"/>${status.stsWords}
	    </c:otherwise>
	  	</c:choose> 
 	</c:when>
 	<c:otherwise>
	  	<c:choose>
	  	<c:when test="${status.stsId == value }">
	    	<input class="easyui-validatebox" type="radio"   name="${name}"  value="${status.stsId}" checked="checked" style="width:10px;"/>${status.stsWords}
	    </c:when>
	    <c:when test="${required }">
	    	<input class="easyui-validatebox" type="radio"   name="${name}"  value="${status.stsId}" checked="checked"  style="width:10px;"/>${status.stsWords}
	    </c:when>
	    <c:otherwise>
	    	<input class="easyui-validatebox" type="radio" name="${name}"  value="${status.stsId}"  style="width:10px;"/>${status.stsWords}
	    </c:otherwise>
	  	</c:choose>
  </c:otherwise></c:choose>
 </c:forEach>



