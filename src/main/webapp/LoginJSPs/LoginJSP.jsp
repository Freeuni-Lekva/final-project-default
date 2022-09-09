<%--
  Created by IntelliJ IDEA.
  User: nikag
  Date: 9/9/2022
  Time: 7:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form action="../LoginServlet" method="post" name="loginForm">
    <label for="username">User Name: </label>
    <input type="text" id="username" name="username"/><br/>
    <label for="password">Password: </label>
    <input type="password" id="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>

</body>
</html>
