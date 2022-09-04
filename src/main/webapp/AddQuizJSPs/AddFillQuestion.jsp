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
    <form action="../AddFillQuestionServlet" id = "addFillQuestion" method="post">
        <h4>Please, Write Question. Type "[blank]" For Unfilled Words.</h4>
        <h10>Example: "The First President Of USA Was [blank]"</h10><br>
        <textarea form="addFillQuestion" maxlength="500" id="questionDescription" name="questionDescription" cols="15"></textarea>
        <h4>Please, Write Answers In Order. Separate Each Answer With Comma.</h4>
        <textarea form="addFillQuestion" maxlength="500" id="answer" name="answer" cols="15"></textarea><br>
        <input form ="addFillQuestion" type="submit" id = "addQuestionButton" name = "addQuestionButton" value="Add">
    </form>
    <form action="./AddQuestions.jsp" id = "BackForm" method = "get">
        <input type="submit" id = "BackButt" name = "BackButt" value="Back">
    </form>


</body>
</html>
<style>
    textarea {
        resize: none;
        width: 250px;
        height: 100px;
    }
</style>


