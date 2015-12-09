<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="citic.gack.sso.base.cache.CacheManager" %>
<%@ tag import="citic.gack.sso.entity.SysEnumerate" %>
<%@ tag import="citic.gack.sso.utils.ConstantsUtils" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="tableName" type="java.lang.String" required="true" %>
<%@ attribute name="columnName" type="java.lang.String" required="true" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%@ attribute name="dataOptions" type="java.lang.String" required="false" %>
<%@ attribute name="choiceOption" type="java.lang.Boolean" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
    CacheManager cacheManager = CacheManager.getInstance();
    List<SysEnumerate> statusList = (List<SysEnumerate>) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idlist.enumerate", tableName + "." + columnName);

    request.setAttribute("statusList", statusList);
%>
<select  name="${name}" <c:if test="${(id != null) && !(empty id)}"> id="${id}"</c:if> class="easyui-validatebox" editable="false" panelHeight="auto" <c:if test="${(dataOptions != null) && !(empty dataOptions)}"> data-options="${dataOptions}"</c:if><c:if test="${(style != null) && !(empty style)}"> style="${style}"</c:if>>
    <c:if test="${choiceOption != null && choiceOption}">
        <option value="">请选择</option>
    </c:if>
    <c:forEach items="${statusList}" var="status">
        <c:choose>
            <c:when test="${status.stsId == value}">
                <option value="${status.stsId}" selected>${status.stsWords}</option>
            </c:when>
            <c:otherwise>
                <option value="${status.stsId}">${status.stsWords}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>