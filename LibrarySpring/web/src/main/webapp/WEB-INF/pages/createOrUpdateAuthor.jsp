<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>create/update author page</title>
</head>
<body>
<%--<c:if test="${not empty msg}"><h3>${msg}</h3></c:if>--%>
<c:if test="${not empty msg}">
    <h3><spring:message code="${msg}" text="Unknown message"/></h3>
</c:if>
<form:form action="../${action}" method="POST" commandName="author">
    <form:errors path="*" cssClass="errorblock" element="div" />
    <table>
        <tr>
            <td><spring:message code="author.name"/>: </td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" cssClass="error"/></td>
        </tr>
        <tr>
            <td><spring:message code="author.name4table"/>: </td>
            <td><form:input path="name4table"/></td>
            <td><form:errors path="name4table" cssClass="error"/></td>
        </tr>
        <tr>
            <td><spring:message code="author.info"/>: </td>
            <td><form:input path="text.text"/></td>
            <td><form:errors path="text.text" cssClass="error"/></td>
        </tr>
        <tr>
            <input value="<spring:message code="author.submit"/>" type="submit">
        </tr>
    </table>
</form:form>
</body>
</html>