<%@ page import="Quizs.Quiz" %>
<%@ page import="Quizs.QuizDao" %>
<%@ page import="Quizs.IQuizDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="History.IHistoryDao" %>
<%@ page import="History.HistoryDao" %>
<%@ page import="History.History" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Users.UserService" %>
<%@ page import="java.security.NoSuchAlgorithmException" %><%--
  Created by IntelliJ IDEA.
  User: Ilia
  Date: 8/11/2022
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        IQuizDao quizDao;
        try {
            quizDao = new QuizDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Quiz q;
        try {
            q = quizDao.getQuiz(Integer.parseInt(request.getParameter("quiz_id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        IHistoryDao historyDao;
        try {
            historyDao = new HistoryDao("base","user","pass");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList <History> ar;
        try {
            ar = historyDao.getUsersFromHistory(q.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserService usv;
        try {
            usv = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    %>
    <title><%=q.getTitle()%></title>

</head>
<body>
    <form action = "ShowQuizServlet" id = "showQuizForm" method="post">
        <h1>Quiz Description: <%=q.getDescription()%></h1>
        <h2>Quiz Time: <%=q.getQuizTime()%></h2>
        <h3>isRandom : <%=q.isRandom()%></h3>
        <h3>isOnePage : <%=q.isOnePage()%></h3>
        <h3>immediateCorrection : <%=q.isImmediateCorrection()%></h3>
        <h3>canBePracticed : <%=q.isCanBePracticed()%></h3>
        <p> Top scorers on this quiz:
            <%
                for (int k = 0; k < ar.size(); k++)
                {
                    try {
                        out.println(usv.getUser(ar.get(k).getUser_id()).getUsername() + " scored " + ar.get(k).getScore());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            %>
        </p>
        <input name="quiz_id" type="hidden" value="<%=q.getId()%>"/>
        <input type = "submit" id = "startQuizButton" name = "Start Quiz">
    </form>
</body>
</html>
