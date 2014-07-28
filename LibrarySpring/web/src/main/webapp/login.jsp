<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Login page.</title>
</head>
<body>

<form method="POST" action="<c:url value="/j_spring_security_check" />">
<table>
	<tr>
		<td align="right"><spring:message code="login.email"/></td>
		<td><input type="text" name="j_username" /></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="login.pass"/></td>
		<td><input type="password" name="j_password" /></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="login.rememberMe"/></td>
		<td><input type="checkbox" name="_spring_security_remember_me" /></td>
	</tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="<spring:message code="login.login"/>" />
		<input type="reset" value="<spring:message code="login.reset"/>" /></td>
	</tr>
</table>
</form>
</body>
</html>