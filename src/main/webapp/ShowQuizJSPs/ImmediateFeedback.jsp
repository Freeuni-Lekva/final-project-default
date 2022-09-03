<%@ page import="Quizs.Question" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quizs.IQuestionDao" %>
<%@ page import="Quizs.QuestionDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Quizs.Answer" %><%--
  Created by IntelliJ IDEA.
  User: Ilia
  Date: 9/3/2022
  Time: 3:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feedback about last question</title>
</head>
<body>
<%
    IQuestionDao questionDao;
    try {
        questionDao = new QuestionDao();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    Boolean feedback = true;
    request.setAttribute("feedback",feedback);
    ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute("QuestionsList");
    Integer curID = (Integer)request.getAttribute("CurrentQuestion");
    Question question = questions.get(curID);
    String s=request.getParameter("question"+curID);
        ArrayList<Answer> answers;
        try {
            answers = questionDao.getAnswers(question);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (question.getType().equals("MULTIPLE_CHOICE")) {
            String correctAns = "";
            for (int k = 0; k < answers.size(); k++) {
                if (answers.get(k).isCorrect())
                    correctAns = answers.get(k).getDescription();
            }
            out.println("<h2>Your Answer: " + s + "</h2> <br>");
            out.println("<h2>Correct Answer: " + correctAns + "</h2> <br>");
        } else {
            String[] ansS = s.split(",");
            String temp = "";
            for (int k = 0; k < ansS.length; k++)
                temp += ansS[k] + " ";
            out.println("<h2>Your Answer: " + temp + "</h2> <br>");
            String correctAns = "";
            for (int k = 0; k < answers.size(); k++) {
                if (answers.get(k).isCorrect())
                    correctAns += answers.get(k).getDescription() + " ";
            }
            out.println("<h2>Correct Answer: " + correctAns + "</h2> <br>");
        }
%>
<form action="MultiPageServlet" method="post">
    <input type="submit" value="Next Question" />
</form>
</body>
</html>
