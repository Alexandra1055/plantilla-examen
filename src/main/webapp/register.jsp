<%--
  Created by IntelliJ IDEA.
  User: alexandra
  Date: 09/12/2025
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Registrar-se</h1>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/register">
    <label>Usuari:
        <input type="text" name="username" value="${username}" required>
    </label>
    <br>
    <label>Contrasenya:
        <input type="password" name="password" required>
    </label>
    <br>
    <button type="submit">Registrar</button>
</form>

<p><a href="${pageContext.request.contextPath}/login">Ja tens compte? Inicia sessió</a></p>
</body>
</html>