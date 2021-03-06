<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false" %>
<%@ attribute name="list" type="java.lang.String" required="true" %>
<%@ attribute name="valueProperty" type="java.lang.String" required="true" %>
<%@ attribute name="labelProperty" type="java.lang.String" required="true" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%@ attribute name="dataOptions" type="java.lang.String" required="false" %>
<%@ attribute name="choiceOption" type="java.lang.Boolean" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List comboboxList = (List)request.getAttribute(list);
    request.setAttribute("comboboxList", comboboxList);
%>
<select name="${name}" id="${id}" class="easyui-combobox" <c:if test="${(dataOptions != null) && !(empty dataOptions)}"> data-options="${dataOptions}"</c:if><c:if test="${(style != null) && !(empty style)}"> style="${style}"</c:if>>
    <c:if test="${choiceOption != null && choiceOption}">
        <option value="">请选择</option>
    </c:if>
    <c:forEach items="${comboboxList}" var="item">
        <c:choose>
            <c:when test="${item[valueProperty] == value}">
                <option value="${item[valueProperty]}" selected>${item[labelProperty]}</option>
            </c:when>
            <c:otherwise>
                <option value="${item[valueProperty]}">${item[labelProperty]}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</select>