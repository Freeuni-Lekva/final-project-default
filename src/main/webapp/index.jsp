<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<a href="ShowQuizJSPs/ShowQuiz.jsp?quiz_id=10">dsdssdd</a>
<a href="Homepage/Homepage.jsp">Homepage</a>
<form method="POST" action="ShowQuizJSPs/ShowQuiz.jsp">
    <input type="hidden" id="quiz_id" name="quiz_id" value="10">
    <input type="submit">
</form>

<form action="LoginServlet" method="post" name="loginForm">
    <label for="username">User Name: </label>
    <input type="text" id="username" name="username"/><br/>
    <label for="password">Password: </label>
    <input type="password" id="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>


<form action="profile" method="get" name="loginForm">
    <input type="hidden" value="Nika" name="user">
    <input type="submit" value="prof"/>

</form>


<a href="LoginJSPs/CreateAccount.jsp">Create Account</a>
<a href="AddQuestionResponseQuestion.jsp"> Add</a>
<a href="QuizCreation.jsp"> Add</a>

</body>
</html>