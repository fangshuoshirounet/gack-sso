<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="citic.gack.sso.base.cache.CacheManager" %>
<%@ tag import="citic.gack.sso.utils.ConstantsUtils" %>
<%@ attribute name="tableName" type="java.lang.String" required="true" %>
<%@ attribute name="columnName" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.String" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    CacheManager cacheManager = CacheManager.getInstance();
    String label = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", tableName + "." + columnName + "." + value);

    request.setAttribute("label", label);
%>
${label}