<%@ page import="Quizs.Quiz" %>
<%@ page import="Quizs.QuizDao" %>
<%@ page import="Quizs.IQuizDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="History.IHistoryDao" %>
<%@ page import="History.HistoryDao" %>
<%@ page import="History.HistoryService" %>
<%@ page import="History.History" %>
<%@ page import="History.HistorySummary" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Users.UserService" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="Users.User" %><%--
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
            request.getSession().setAttribute("quiz_id", request.getParameter("quiz_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HistoryService historyservice;
        try {
            historyservice = new HistoryService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<History> ar;
        try {
            ar = historyservice.getUsersFromHistory(q.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<History> pastScores;
        try {
            //pastScores = historyservice.getScore(q.getId(), ((User) request.getSession().getAttribute("user_id")).getId());
            pastScores = historyservice.getScore(q.getId(), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<History> OthersScores;
        try {
            OthersScores = historyservice.getRecentTestTakers(q.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UserService usv;
        try {
            usv = new UserService();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        HistorySummary summary = null;
        try {
            summary = historyservice.getHistorySummary(q.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    %>
    <title><%=q.getTitle()%>
    </title>

</head>
<body>
<form action="../ShowQuizServlet" id="showQuizForm" method="post">
    <h1>Quiz Description: <%=q.getDescription()%>
    </h1>
    <%
        User creator = null;
        try {
            creator = usv.getUser(q.getCreatorId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>

    <h2>Quiz Creator : <%=creator.getUsername()%>
    </h2>
    <br>
    <hr>
    <p> Past Performances on this quiz
        <%


            for (int k = 0; k < pastScores.size(); k++) {

                History cur = pastScores.get(k);
                try {
                    out.println(" scored " + cur.getScore() + "started on" + cur.getStart_date() + "ended on " + cur.getEnd_date());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        %>

    </p>

    <h2>Quiz Time: <%=q.getQuizTime()%>
    </h2>
    <h3>isRandom : <%=q.isRandom()%>
    </h3>
    <h3>isOnePage : <%=q.isOnePage()%>
    </h3>
    <h3>immediateCorrection : <%=q.isImmediateCorrection()%>
    </h3>
    <h3>canBePracticed : <%=q.isCanBePracticed()%>
    </h3>


    <p> Top scorers on this quiz:
        <%
            for (int k = 0; k < ar.size(); k++) {
                History cur = ar.get(k);
                try {
                    out.println(usv.getUser(cur.getUser_id()).getUsername() + " scored " + cur.getScore());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        %>
    </p>


    <p>Scores of Recent Test Takers :

        <%
            for (int k = 0; k < OthersScores.size(); k++) {
                History cur = OthersScores.get(k);
                try {
                    out.println(usv.getUser(cur.getUser_id()).getUsername() + " scored " + cur.getScore());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        %>
    </p>

    <p>Quiz Summary:
    <h1>
        Average Score:
        <%=summary.getAverage_score()%>
    </h1>

    <h1>
        Average Time Spent in Minutes
        <%=summary.getAverage_time()%>
    </h1>


    </p>


    <input name="quiz_id" type="hidden" value="<%=q.getId()%>"/>
    <%
        if (q.isCanBePracticed())

            out.println("<input type=\"radio\" name=\"IsPracticed" + "\" " + "value=" +
                    "YES" + "\" " + "> " + "YES" + "</br>");
        out.println("<input type=\"radio\" name=\"IsPracticed" + "\" " + "value=" +
                "NO" + "\" " + "> " + "NO" + "</br>");

    %>


    <input type="submit" id="startQuizButton" name="Start Quiz">
</form>
</body>
</html>
