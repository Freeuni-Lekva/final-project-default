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
<form action="../AddPictureResponseQuestionServlet" id="addPictureResponse" method="post">
    <h1>Picture:</h1><br>
    <input type="file" accept="image/*" name="questionDescription" onchange="loadFile(event)">
    <p><img id="output" width="200"/></p>
    <script>
        var loadFile = function (event) {
            var image = document.getElementById('output');
            image.src = URL.createObjectURL(event.target.files[0]);
        };
    </script>
    <input form="addPictureResponse" type="text" name="answer"/><br>
    <input form="addPictureResponse" type="submit" id="addQuestionButton" name="addQuestionButton" value="Add">
</form>
<form action="AddQuestions.jsp" id="BackForm" method="get">
    <input type="submit" id="BackButt" name="BackButt" value="Back">
</form>

</body>
</html>
<style>


</style>


