<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page trimDirectiveWhitespaces="true" %>
<html>
<head><title>Main page</title></head>
<body>
<%--<c:if test="${not empty msg}"><h3>${msg}</h3></c:if>--%>
<c:if test="${not empty msg}">
    <h3><spring:message code="${msg}" text="Unknown message"/></h3>
</c:if>

<%--<spring:message code="welcome" text="default text"/>--%>

<spring:message code="index.hello"/>, ${roles.userName}. <spring:message code="index.roles"/>:
<c:if test="${roles.isGuest}"><spring:message code="users.guest"/> </c:if>
<c:if test="${roles.isUser}"><spring:message code="users.user"/> </c:if>
<c:if test="${roles.isModerator}"><spring:message code="users.moderator"/> </c:if>
<c:if test="${roles.isAdmin}"><spring:message code="users.admin"/> </c:if><br><br>

<c:choose>
    <c:when test="${not empty author}">
        <h2 align="center">${author.name}</h2>
        <p align="justify">${author.text.text}</p>
        <c:set var="coreUrl" value="../author/${author.id}?"/>
    </c:when>
    <c:when test="${not empty user}">
        <h2 align="center">${user.name}</h2>
        <p align="center">${user.role}</p>
        <c:set var="coreUrl" value="../user/${user.id}?"/>
    </c:when>
    <c:otherwise>
        <c:set var="coreUrl" value="../index?"/>
    </c:otherwise>
</c:choose>

<%--<h3>Order=${order}</h3>--%>

<table border="0">
    <tr>
        <td>
            <spring:message code="index.id"/>
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
            <spring:message code="index.title"/>
            <c:choose>
                <c:when test="${order == 'title_asc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=title_asc">O</a></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${order == 'title_desc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=title_desc">O</a></c:otherwise>
            </c:choose>
        </td>
        <td>
            <spring:message code="index.date"/>
            <c:choose>
                <c:when test="${order == 'date_asc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=date_asc">O</a></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${order == 'date_desc'}">X</c:when>
                <c:otherwise><a href="${coreUrl}order=date_desc">O</a></c:otherwise>
            </c:choose>
        </td>
        <td><spring:message code="index.authors"/></td>
        <td><spring:message code="index.user"/></td>
    </tr>
    <c:forEach items="${bookList}" var="book">
        <tr>
            <td>${book.id}</td>
            <td><a href="../book/${book.id}">${book.title}</a></td>
            <fmt:formatDate value="${book.lastUpdate.time}" pattern="kk:mm dd/MM/YYYY" var="date"/>
            <td>${date}</td>
            <td><c:forEach items="${book.authors}" var="a">
                <a href="../author/${a.id}">${a.name4table}</a>
            </c:forEach></td>
            <td><a href="../user/${book.user.id}">${book.user.name}</a></td>

            <c:if test="${roles.isModerator || (roles.isUser && roles.userName==book.user.name)}">
                <td>
                    <a href="../book/${book.id}/edit">[<spring:message code="index.edit"/>]</a>
                    <a href="../book/${book.id}/delete">[<spring:message code="index.delete"/>]</a>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>

<c:if test="${not empty order}">
    <c:set var="coreUrl" value="${coreUrl}order=${order}&"></c:set>
</c:if>
<c:if test="${not empty lastPage && lastPage>1}">
    <c:forEach begin="1" end="${lastPage}" var="page">
        <a href="${coreUrl}from=${5*(page-1)}&number=5"> ${page} </a>
    </c:forEach>
</c:if>
<br><br>
<a href="?lang=en">en</a>|<a href="?lang=ru">ru</a><br>
<c:choose>
    <c:when test="${roles.isGuest}">
        <a href="../login"><spring:message code="menu.login"/></a><br>
        <a href="../register"><spring:message code="menu.register"/></a>
    </c:when>
    <c:otherwise>
        <a href="../author/new"><spring:message code="menu.addAuthor"/></a><br>
        <a href="../book/new"><spring:message code="menu.addBook"/></a><br>
        <a href="../logout"><spring:message code="menu.logout"/></a><br>
    </c:otherwise>
</c:choose>
<c:if test="${roles.isAdmin}">
    <a href="../users"><spring:message code="menu.users"/></a>
</c:if>
</body>
</html>