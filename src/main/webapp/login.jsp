<%--
  Created by IntelliJ IDEA.
  User: alexandra
  Date: 09/12/2025
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Iniciar sesión</h1>

<c:if test="${not empty loginError}">
    <p style="color:red;">${loginError}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Usuario:
        <input type="text" name="username" required>
    </label>
    <br>
    <label>Contraseña:
        <input type="password" name="password" required>
    </label>
    <br>
    <button type="submit">Entrar</button>
</form>

</body>
</html>
