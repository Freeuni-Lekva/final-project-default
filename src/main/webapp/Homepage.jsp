<%@ page import="Quizs.QuizDao"%>
<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/11/2022
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%QuizDao qd = (QuizDao) application.getAttribute("QuizDao");%>
    <title>Homepage</title>
</head>
<body>
    <h1> <%= "Welcome " + request.getParameter("username") %></h1>
</body>
</html>
