<%@ page import="java.sql.SQLException" %>
<%@ page import="Quizs.*" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Ilia
  Date: 9/1/2022
  Time: 6:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        IQuizDao quizDao = (QuizDao) request.getServletContext().getAttribute("QuizDao");
        IQuestionDao questionDao = (QuestionDao) request.getServletContext().getAttribute("QuestionDao");
        ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");
        Integer curID = (Integer) request.getSession().getAttribute("CurrentQuestion");
        PictureResponseQuestion question = (PictureResponseQuestion) questions.get(curID);
        Quiz quiz;
        try {
            quiz = quizDao.getQuiz(question.getQuizId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <title><%=quiz.getTitle()%>
    </title>
</head>
<body>
<h1>
    <img src="<%=question.getDescription()%>" alt="<%=question.getDescription()%>">
</h1>
<form action="./MultiPageServlet" method="post">
    <% out.print("<input type=\"text\" name=\"question" + curID + "\"" + ">");%>
    <input type="submit" value="Next Question">
</form>
</body>
</html>
