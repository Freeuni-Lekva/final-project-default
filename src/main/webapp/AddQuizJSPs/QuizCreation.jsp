<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/3/2022
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>
<div style="display:inline-block;margin-right:20px;">
    <%! public boolean ok = true;%>
    <h1>Quiz Creation</h1>
    <form action="AddQuizServlet" id="addQuizForm" method="post">
        <label for="quizTitle">Quiz Title</label>
        <input form="addQuizForm" type="text" id="quizTitle" name="quizTitle"><br><br>
        <label for="quizTime">Length Of Quiz In Seconds</label>
        <input form="addQuizForm" type="number" id="quizTime" name="quizTime"><br><br>
        <label for="quizDescription">Quiz Description</label>
        <textarea form="addQuizForm" maxlength="1500" name="quizDescription" id="quizDescription" cols="35"></textarea>
        <label>Random Quiz Order<input form="addQuizForm" name="isRandom" type="checkbox" id="isRandom"
                                       value="isRandom">
        </label><br>
        <label>Multi Question Page<input form="addQuizForm" name="isOnePage" type="checkbox" id="isOnePage"
                                         value="isOnePage">
        </label><br>
        <label>Immediate Correction<input form="addQuizForm" name="imediateCorrection" type="checkbox"
                                          id="imediateCorrection" value="imediateCorrection">
        </label><br>
        <label>Can Be Taken In Practice Mode<input name="canBePracticed" form="addQuizForm" type="checkbox"
                                                   id="canBePracticed" value="canBePracticed">
        </label><br>
        <input type="submit" id="addQuizButton" name="addQuizButton"><br><br>
    </form>
</div>
</body>
</html>
<style>
    input, label {
        display: block;
    }

    textarea {
        resize: none;
        width: 300px;
        height: 150px;
    }


</style>

