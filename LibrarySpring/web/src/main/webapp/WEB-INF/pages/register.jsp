<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register page</title>
</head>
<body>
<%--<c:if test="${not empty msg}"><h3>${msg}</h3></c:if>--%>
<c:if test="${not empty msg}">
    <h3><spring:message code="${msg}" text="Unknown message"/></h3>
</c:if>
    <form:form action="../register" method="POST" commandName="newAccount">
        <form:errors path="*" cssClass="errorblock" element="div" />
        <table>
            <tr>
                <td><spring:message code="register.name"/>: </td>
                <td><form:input path="user.name"/></td>
                <td><form:errors path="user.name" cssClass="error"/></td>
            </tr>
            <tr>
                <td><spring:message code="register.email"/>: </td>
                <td><form:input path="user.email"/></td>
                <td><form:errors path="user.email" cssClass="error"/></td>
            </tr>
            <tr>
                <td><spring:message code="register.pass"/>: </td>
                <td><form:input path="pass"/></td>
                <td><form:errors path="pass" cssClass="error"/></td>
            </tr>
            <tr>
                <td><spring:message code="register.pass"/>: </td>
                <td><form:input path="confirmPass"/></td>
                <td><form:errors path="confirmPass" cssClass="error"/></td>
            </tr>
            <tr>
                <input value="<spring:message code="register.submit"/>" type="submit">
            </tr>
        </table>
    </form:form>
</body>
</html>