<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page trimDirectiveWhitespaces="true" %>
<html>
<head><title>Roles Management</title></head>
<body>

<c:set var="coreUrl" value="../users?"/>

<%--<h3>Order=${order}</h3>--%>

<%--<c:if test="${not empty msg}"><h3>${msg}</h3></c:if>--%>
<c:if test="${not empty msg}">
    <h3><spring:message code="${msg}" text="Unknown message"/></h3>
</c:if>
<table>
    <tr>
        <td>
            <spring:message code="users.id"/>
            <c:choose>
                <c:when test="${order == 'id_asc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=id_asc">O</a></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${order == 'id_desc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=id_desc">O</a></c:otherwise>
            </c:choose>
        </td>
        <td>
            <spring:message code="users.name"/>
            <c:choose>
                <c:when test="${order == 'name_asc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=name_asc">O</a></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${order == 'name_desc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=name_desc">O</a></c:otherwise>
            </c:choose>
        </td>
        <td><spring:message code="users.role"/></td>
        <td><spring:message code="users.newRole"/></td>
    </tr>
<c:forEach items="${userList}" var="user">
    <tr>
        <td>${user.id}</td>
        <td><a href="../user/${user.id}">${user.name} </a></td>
        <td>${user.role}</td>
        <c:if test="${user.role != 'admin'}">
            <td>
                <c:if test="${user.role!='guest'}">
                    <a href="../user/${user.id}/guest">[<spring:message code="users.guest"/>]</a>
                </c:if>
                <c:if test="${user.role!='user'}">
                    <a href="../user/${user.id}/user">[<spring:message code="users.user"/>]</a>
                </c:if>
                <c:if test="${user.role!='moderator'}">
                    <a href="../user/${user.id}/moderator">[<spring:message code="users.moderator"/>]</a>
                </c:if>
            </td>
        </c:if>
    </tr>
</c:forEach>
</table>

<c:if test="${not empty order}">
    <c:set var="coreUrl" value="${coreUrl}order=${order}&"/>
</c:if>

<c:if test="${not empty lastPage && lastPage>1}">
    <c:forEach begin="1" end="${lastPage}" var="page">
        <a href="${coreUrl}from=${5*(page-1)}&number=5"> ${page} </a>
    </c:forEach>
</c:if>
<br><br>
<a href="../index"><spring:message code="menu.index"/></a>
</body>
</html>
