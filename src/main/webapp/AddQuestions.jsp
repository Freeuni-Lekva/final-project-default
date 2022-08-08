<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/7/2022
  Time: 12:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Questions To The Quiz</title>
</head>
<body>
<h1>Add Questions To The Quiz</h1>
<h3>Please Choose What Type Of Question You Want To Add:</h3>
<ul>
    <li><a href="AddMultipleChoiceQuestion.jsp">Multiple Choice Question</a></li>
    <li><a href="AddPictureResponseQuestion.jsp">Picture Response Question</a></li>
    <li><a href="AddFillQuestion.jsp">Fill Question</a></li>
</ul>
<form action="index.jsp" id = "BackForm" method = "get">
    <input type="submit" id = "BackButt" name = "BackButt" value="Finish Adding Quiz">
</form>
</body>
</html>
