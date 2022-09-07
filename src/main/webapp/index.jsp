<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="AddQuizJSPs/QuizCreation.jsp">Create Quiz</a>
<a href="AddQuizJSPs/AddQuestions.jsp">Add Questions</a>
<a href="SearchJSPs/Search.jsp">Search</a>
<form method="POST" action="ShowQuizJSPs/ShowQuiz.jsp">
    <input type="hidden" id="quiz_id" name="quiz_id" value="10">
    <input type="submit">
</form>

</body>
</html>