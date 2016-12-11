<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Login</title>
</head>
<body>

<h1>
	Login!
</h1>
<br>
<form action="/login" method="POST">
 <table>
    <tbody><tr><td>User:</td><td><input type="text" name="username"></td></tr>
    <tr><td>Password:</td><td><input type="password" name="password"></td></tr>
    <tr><td colspan="2"><input name="submit" type="submit" value="Login"></td></tr>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </tbody></table>
</form>

</body>
</html>
