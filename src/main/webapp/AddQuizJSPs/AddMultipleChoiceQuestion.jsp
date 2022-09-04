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
    <form action="../AddMultipleAnswerQuestionServlet" id = "addMultipleChoice" method="post">
        <h1>Question:</h1><br>
        <textarea form="addMultipleChoice" maxlength="500" id="questionDescription" name="questionDescription" cols="15"></textarea>
        <br><br>
        <h4>Answers:</h4>
        <ul id="answList">
                <li id = "answer1"><input form ="addMultipleChoice" type="text" name = "answer1"/>
                    <label><input type="radio" id="answer1Radio" name="correctAnswer" value="1"/>Correct Answer</label></li>
                <li id = "answer2"><input form ="addMultipleChoice" type="text" name = "answer2">
                    <label><input type="radio" id="answer2Radio" name="correctAnswer" value="2">Correct Answer</label></li>
                <li id = "answer3"><input form ="addMultipleChoice" type="text" name = "answer3">
                    <label><input type="radio" id="answer3Radio" name="correctAnswer" value="3">Correct Answer</label></li>
                <li id = "answer4"><input form ="addMultipleChoice" type="text" name = "answer4">
                    <label><input type="radio" id="answer4Radio" name="correctAnswer" value="4">Correct Answer</label></li>
        </ul>
        <input type="submit" id = "addQuestionButton" name = "addQuestionButton" value="Add">
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

    #answList > li{
        list-style: none;
    }


</style>


