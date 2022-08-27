<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/7/2022
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Multiple Choice Question</title>
</head>
<body>
    <form action="AddQuestionResponseQuestionServlet" id = "AddQuestionResponse" method="post">
        <h1>Question:</h1><br>
        <textarea form="AddQuestionResponse" maxlength="500" id="questionDescription" name="questionDescription" cols="15"></textarea>
        <h4>Please, Write Answers. Separate Each Answer With Comma.</h4>
        <textarea form="AddQuestionResponse" maxlength="500" id="answer" name="answer" cols="15"></textarea><br>
        <input form ="AddQuestionResponse" type="submit" id = "addQuestionButton" name = "addQuestionButton" value="Add">
    </form>
    <form action="AddQuestions.jsp" id = "BackForm" method = "get">
        <input type="submit" id = "BackButt" name = "BackButt" value="Back">
    </form>

</body>
</html>
<style>


</style>


