<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>create/update book page</title>
</head>
<body>
<%--<c:if test="${not empty msg}"><h3>${msg}</h3></c:if>--%>
<c:if test="${not empty msg}">
    <h3><spring:message code="${msg}" text="Unknown message"/></h3>
</c:if>
<form:form action="../${action}" method="POST" commandName="book">
    <form:errors path="*" cssClass="errorblock" element="div" />
    <table>
        <tr>
            <td><spring:message code="book.title"/>: </td>
            <td><form:input path="title"/></td>
            <td><form:errors path="title" cssClass="error"/></td>
        </tr>
        <c:forEach begin="1" end="5" step="1" var="num">
            <tr>
                <td>
                    <c:if test="${num==1}"><spring:message code="book.authors"/>: </c:if>
                </td>
                <td>
                    <form:select path="author${num}.id">
                        <form:option value="0" label=""/>
                        <c:forEach items="${authorList}" var="author">
                            <form:option value="${author.id}" label="${author.name4table}"/>
                        </c:forEach>
                    </form:select>
                </td>
                <td>
                    <form:errors path="author${num}" cssClass="error"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td><spring:message code="book.text"/>: </td>
            <td><form:input path="text.text"/></td>
            <td><form:errors path="text.text" cssClass="error"/></td>
        </tr>
        <tr>
            <input value="<spring:message code="book.submit"/>" type="submit">
        </tr>
    </table>
</form:form>
</body>
</html>