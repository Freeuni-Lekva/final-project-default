<%@ page import="Quizs.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: temo
  Date: 9/1/2022
  Time: 9:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%
    IQuizDao quizDao = (QuizDao) request.getServletContext().getAttribute("QuizDao");
    IQuestionDao questionDao = (QuestionDao) request.getServletContext().getAttribute("QuestionDao");
    Quiz quiz;
    try {
             quiz=quizDao.getQuiz(Integer.parseInt(request.getParameter("quiz_id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>


</head>


<body>
<form action = "CheckAnswers" method="post">
    <%
        ArrayList<Question> questions;
        try {
             questions=questionDao.getQuestions(quiz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i=0;i<questions.size();i++)
        {
            Question curquest=questions.get(i);
            String s=curquest.getType();
            if (s.equals("QUESTION_RESPONSE") || s.equals("FILL_QUESTION"))
            {
                out.println("<p>"+curquest.getDescription()+"</p>");
                out.println("<input type=\"text\" name=\"question" + i + "\"" + ">");
            }

            if (s.equals("MULTIPLE_CHOICE"))
            {
                out.println("<p>"+curquest.getDescription()+"</p>");
                ArrayList<Answer> ans;
                try {
                    ans = questionDao.getAnswers(curquest);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (int k=0;k<ans.size();k++)
                {
                    String answer = ans.get(k).getDescription();
                    out.println("<input type=\"radio\" name=\"question" + i + "\" " + "value=" +
                            answer + "\" " + "> " + answer + "</br>");
                }
            }
            if (s.equals("PICTURE_RESPONSE"))
            {

                out.println("<p>"+"<img src=\""+curquest.getDescription() + "\"" + "alt=\"" + curquest.getDescription() + "\"" +">"+"</p>");
                out.println("<input type=\"text\" name=\"question" + i + "\"" + ">");

            }

        }

    %>
    <input type = "submit" id = "SubmitQuizButton" name = "Submit Quiz">

</form>
</body>
</html>
