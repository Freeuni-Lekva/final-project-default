<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome To The BEST QUIZ Webpage Of All Time</h1>
<p>Please log in.</p>

<form action="LoginServlet" method="post" name="loginForm">
    <label for="username">User Name: </label>
    <input type="text" id="username" name="username"/><br/>
    <label for="password">Password: </label>
    <input type="password" id="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>

<a href="LoginJSPs/CreateAccount.jsp">Create Account</a>

</body>
</html>