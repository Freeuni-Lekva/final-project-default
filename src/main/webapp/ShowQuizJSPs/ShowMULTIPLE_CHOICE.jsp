<%@ page import="java.sql.SQLException" %>
<%@ page import="Quizs.*" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Ilia
  Date: 9/1/2022
  Time: 6:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%
    IQuizDao quizDao = (QuizDao) request.getServletContext().getAttribute("QuizDao");
    IQuestionDao questionDao = (QuestionDao) request.getServletContext().getAttribute("QuestionDao");
    ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute("QuestionsList");
    Integer curID = (Integer)request.getAttribute("CurrentQuestion");
    MultipleChoiceQuestion question = (MultipleChoiceQuestion) questions.get(curID);
    Quiz quiz;
    try {
      quiz = quizDao.getQuiz(question.getQuizId());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  %>
  <title><%=quiz.getTitle()%></title>
</head>
<body>
  <h1><%=question.getDescription()%></h1>
  <form action="MultiPageServlet" method="post">
    <%
      ArrayList<Answer> answers;
      try {
        answers = questionDao.getAnswers(question);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      for (int k = 0; k < answers.size(); k++)
      {
        String ans = answers.get(k).getDescription();
        out.println("<input type=\"radio\" name=\"question" + curID + "\" " + "value=" +
                ans + "\" " + "> " + ans + "</br>");
      }
    %>
    <input type="submit" value="Next Question">
  </form>
</body>
</html>
