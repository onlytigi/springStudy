<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>needLogin</title>
</head>
<body>
<h1>
	need Login
</h1>

<P> user : ${pageContext.request.remoteUser} </P>
<br>
<c:if test="${not empty pageContext.request.remoteUser}">
<form action="/logout" method="post">
	<button type="submit" class="btn btn-default">Log out</button>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</c:if>
</body>
</html>
