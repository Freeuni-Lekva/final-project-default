<%--
  Created by IntelliJ IDEA.
  User: zken7
  Date: 07.09.22
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <br>
    <form action="../banUser" method="post">
        <label for="ban">Ban user account:</label><br>
        <input type="text" id="ban" name="ban-user"><br><br>
        <label for="time">Time in days</label><br>
        <input type="text" id="time" name="time"><br><br>
        <input type="submit" value="Ban">
    </form>
    <br>
    <form action="../unBanUser" method="post">
        <label for="ban">UnBan user account:</label><br>
        <input type="text" id="unban" name="unban-user"><br><br>
        <input type="submit" value="UnBan">
    </form>
    <form action="../makeAdmin" method="post">
        <label for="new-admin">Make user admin:</label><br>
        <input type="text" id="new-admin" name="new-admin"><br><br>
        <input type="submit" value="Make Admin">
    </form>
</body>
</html>
