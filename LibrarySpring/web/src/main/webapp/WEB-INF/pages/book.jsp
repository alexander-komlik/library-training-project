<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page trimDirectiveWhitespaces="true" %>
<html>
<head><title>${title}</title></head>
<body>
    <c:if test="${not empty msg}">
        <h3><spring:message code="${msg}" text="Unknown message"/></h3>
    </c:if>
    <h2 align="center">${authors}</h2>
    <h1 align="center">${book.title}</h1>
    <p align="justify">${book.text.text}</p>
</body>
</html>