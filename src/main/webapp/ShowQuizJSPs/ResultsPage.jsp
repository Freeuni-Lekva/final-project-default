<%@ page import="Users.UserService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="Users.User" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quizs.*" %><%--
  Created by IntelliJ IDEA.
  User: Ilia
  Date: 9/3/2022
  Time: 3:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Result Page</title>
</head>
<body>
    <%
        User user = (User) request.getSession().getAttribute("currentUser");
        Integer score = (Integer)request.getAttribute("FinalScore");
        java.sql.Date st_time = (Date) request.getAttribute("st_time");
        java.sql.Date end_time = (Date) request.getAttribute("end_time");
        Integer quizID = Integer.parseInt(request.getParameter("quiz_id"));
        IQuizDao quizDao;
        try {
            quizDao = new QuizDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        IQuestionDao questionDao;
        try {
            questionDao = new QuestionDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Quiz quiz;
        try {
            quiz = quizDao.getQuiz(quizID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
    <h1>User <%=user.getUsername()%> got <%=score%> points in this quiz!</h1>
    <h2>Start Time : <%=st_time%> </br> End Time: <%=end_time%></h2>
    <%
        ArrayList<Question> quests= (ArrayList<Question>) request.getAttribute("QuestionsList");
        for (int i=0;i<quests.size();i++)
        {
            String s = request.getParameter("question" + i);
            Question question = quests.get(i);
            out.println("<h2>Question Number " + i + 1 + "</h2>");
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
                out.println("<h3>Your Answer: " + s + "</h3> <br>");
                out.println("<h3>Correct Answer: " + correctAns + "</h3> <br>");
            } else {
                String[] ansS = s.split(",");
                String temp = "";
                for (int k = 0; k < ansS.length; k++)
                    temp += ansS[k] + " ";
                out.println("<h3>Your Answer: " + temp + "</h3> <br>");
                String correctAns = "";
                for (int k = 0; k < answers.size(); k++) {
                    if (answers.get(k).isCorrect())
                        correctAns += answers.get(k).getDescription() + " ";
                }
                out.println("<h3>Correct Answer: " + correctAns + "</h3> <br>");
            }
        }
    %>
    <form action="HomePage.jsp">
        <input type="submit" value="Return to home page" />
    </form>
</body>
</html>