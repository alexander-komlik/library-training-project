<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<h3><spring:message code="${msg}" text="HTTP Status 500 - :-("/></h3>
<br><br>
<a href="../index"><spring:message code="menu.index" text="Main page"/></a>
</body>
</html>