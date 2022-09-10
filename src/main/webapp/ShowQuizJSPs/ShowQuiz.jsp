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
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
    <div class="topnav">
        <a href="../Homepage/Homepage.jsp">Home</a>
        <% if (request.getSession().getAttribute("currentUser") == null) { %>
        <a class="logout" href="../LoginJSPs/CreateAccount.jsp">Create Account</a>
        <a class="logout" href="../LoginJSPs/LoginJSP.jsp">Log In</a>
        <%}%>
        <a href="../SearchJSPs/Search.jsp">Search</a>
        <% if (request.getSession().getAttribute("currentUser") != null) { %>
        <a href="../profile?user=<%=((User)request.getSession().getAttribute("currentUser")).getUsername()%>">Profile</a>
        <a href="../Homepage/Mails.jsp">Mails</a>
        <a class="logout" href="../LogOutServlet">Log Out</a>
        <%}%>
    </div>
    <style>
        .topnav {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #333;
            overflow: hidden;
        }

        /* Style the links inside the navigation bar */
        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        /* Change the color of links on hover */
        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav .logout {
            float: right;
        }

    </style>
</head>
<body>
<div class="d-flex flex-column justify-content-center w-100 h-100"></div>
<div class="form">
    <%if(request.getSession().getAttribute("currentUser") != null && (((User)request.getSession().getAttribute("currentUser")).isAdmin() || ((User)request.getSession().getAttribute("currentUser")).getId() == q.getCreatorId())){ %>
    <form action="../DeleteQuizServlet" method="post">
        <input type="submit" value="Delete Quiz" class="button2">
    </form>
    <%}%>
    <form action="./ShareQuiz.jsp" method="post">
        <input type="submit" value="Challenge Friend" class="button2">
        <input type="hidden" name="sharedQuiz" value="<%= q.getId() %>">
    </form>
    <form action="../ShowQuizServlet" id="showQuizForm" method="post">
        <h1><%=q.getTitle()%><br>
        </h1>
        <div class="w3-tag w3-small w3-round w3-blue" style="padding:3px">
            <% if (q.isCanBePracticed()) {%>
            <div class="w3-tag w3-round w3-blue w3-border w3-border-white" style="margin-left:5px">
                Practicable
            </div>
            <% } %>
            <% if (q.isRandom()) {%>
            <div class="w3-tag w3-round w3-blue w3-border w3-border-white" style="margin-left:5px">
                Random
            </div>
            <% } %>
            <% if (q.isOnePage()) {%>
            <div class="w3-tag w3-round w3-blue w3-border w3-border-white" style="margin-left:5px">
                Random
            </div>
            <% } %>
            <% if (q.isImmediateCorrection()) {%>
            <div class="w3-tag w3-round w3-blue w3-border w3-border-white" style="margin-left:5px">
                Immediate Feedback
            </div>
            <% } %>
        </div>
        <%
            User creator = null;
            try {
                creator = usv.getUser(q.getCreatorId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        %>
        <h6>Created By : <%=creator.getUsername()%>
        </h6>
        <div class="w3-container">
            <span class="w3-tag w3-padding w3-round-large w3-orange w3-center ">Time: <%=q.getQuizTime()%> minutes</span>
        </div>
        <h4>Description:
        </h4>
        <p>
            <%=q.getDescription()%>
        </p>


        <input class="tabs" type="button" name="Showdiv1" value="Top Scorers" onclick="showDiv('1')"/>
        <input class="tabs" type="button" name="Showdiv2" value="My Scores" onclick="showDiv('2')"/>
        <input class="tabs" type="button" name="Showdiv3" value="Recent Scores" onclick="showDiv('3')"/>

        <div id="div1">
            <table class="topscorers">
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                    <th>Date</th>
                </tr>
                <%
                    for (int k = 0; k < ar.size(); k++) {
                        History cur = ar.get(k);
                        try {
                            String username = usv.getUser(cur.getUser_id()).getUsername();
                            int score = cur.getScore();
                            String date = cur.getEnd_date().toString();
                            out.println("<tr><td>" + username + "</td><td>" + score + "</td><td>" + date + "</td></tr>");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } %>
            </table>
        </div>
        <div id=div2>
            <table class="topscorers">
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                    <th>Date</th>
                </tr>
                <%

                    for (int k = 0; k < pastScores.size(); k++) {

                        History cur = pastScores.get(k);
                        try {
                            String username = usv.getUser(cur.getUser_id()).getUsername();
                            int score = cur.getScore();
                            String date = cur.getEnd_date().toString();
                            out.println("<tr><td>" + username + "</td><td>" + score + "</td><td>" + date + "</td></tr>");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                %>
            </table>
        </div>
        <div id=div3>
            <table class="topscorers">
                <tr>
                    <th>Username</th>
                    <th>Score</th>
                    <th>Date</th>
                </tr>
                <%

                    for (int k = 0; k < OthersScores.size(); k++) {

                        History cur = OthersScores.get(k);
                        try {
                            String username = usv.getUser(cur.getUser_id()).getUsername();
                            int score = cur.getScore();
                            String date = cur.getEnd_date().toString();
                            out.println("<tr><td>" + username + "</td><td>" + score + "</td><td>" + date + "</td></tr>");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                %>
            </table>
        </div>
        <script type="text/javascript">
            function showDiv(num) {
                document.getElementById('div1').style.display = 'none';
                document.getElementById('div2').style.display = 'none';
                document.getElementById('div3').style.display = 'none';
                document.getElementById('div' + num).style.display = 'block'
            }
        </script>
        <br>
        <div class="w3-container">
            <span class="w3-tag w3-padding w3-round-large w3-red w3-center">Average Score: <%=summary.getAverage_score()%></span>
            <span class="w3-tag w3-padding w3-round-large w3-red w3-center">Average Time Spent in Minutes: <%=summary.getAverage_time()%></span>
        </div>
        <input name="quiz_id" type="hidden" value="<%=q.getId()%>"/>
        <br>
        <%
            if (q.isCanBePracticed()){%>

            <label class="container">Practice Mode
            <input type="checkbox" value="YES" name="IsPracticed" >
            <span class="checkmark"></span>
            </label>

        <%}%>


        <input type="submit" class="button1" id="startQuizButton"  value="Start Quiz" name="Start Quiz">
    </form>
</div>
</body>
</html>
<style>

    input {
        font-family: Arial, Helvetica, sans-serif;
        border: 2px solid gray;
        border-radius: 5px;
        display: inline-block;
        float: left;
    }

    .button1 {
        background-color: #4CAF50; /* Green */
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        margin-top: 10px;
        display: inline-block;
        font-size: 16px;
        border-radius: 8px;
        transition-duration: 0.4s;
    }

    .button1:hover {
        box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
    }
    .button2 {
        background-color: darkred; /* Green */
        border: none;
        color: white;
        padding: 7px 16px;
        text-align: center;
        text-decoration: none;
        margin-top: 10px;
        display: inline-block;
        font-size: 12px;
        border-radius: 8px;
        transition-duration: 0.4s;
    }

    .button2:hover {
        box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.24), 0 9px 25px 0 rgba(0, 0, 0, 0.19);
    }

    .tabs {
        background-color: orange; /* Green */
        border: none;
        color: white;
        padding: 7px 16px;
        text-align: center;
        text-decoration: none;
        margin-top: 10px;
        margin-bottom: 15px;
        margin-left: 10px;
        display: inline-block;
        font-size: 15px;
        border-radius: 8px;
        transition-duration: 0.4s;
    }

    .tabs:hover {
        box-shadow: 0 6px 8px 0 rgba(0, 0, 0, 0.24), 0 9px 25px 0 rgba(0, 0, 0, 0.19);
    }

    div.form {
        font-family: Arial, Helvetica, sans-serif;
        display: block;
        text-align: center;
    }

    form {
        display: inline-block;
        margin-left: auto;
        margin-right: auto;
        text-align: left;
    }

    body {
        background: linear-gradient(-45deg, #fdb8a0, #f5a0bf, #7ab7d0, #88d2c0);
        background-size: 400% 400%;
        animation: gradient 15s ease infinite;
        height: 100vh;
    }

    @keyframes gradient {
        0% {
            background-position: 0% 50%;
        }

        50% {
            background-position: 100% 50%;
        }

        100% {
            background-position: 0% 50%;
        }
    }

    .topscorers {
        font-family: Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    .topscorers td, #topscorers th {
        border: 1px solid #ddd;
        padding: 8px;
    }

    .topscorers tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .topscorers tr:hover {
        background-color: #ddd;
    }

    .topscorers th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: left;
        background-color: #04AA6D;
        color: white;
    }

    .container {
        display: block;
        position: relative;
        padding-left: 35px;
        margin-bottom: 12px;
        cursor: pointer;
        font-size: 22px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }

    /* Hide the browser's default checkbox */
    .container input {
        position: absolute;
        opacity: 0;
        cursor: pointer;
        height: 0;
        width: 0;
    }

    /* Create a custom checkbox */
    .checkmark {
        position: absolute;
        top: 0;
        left: 0;
        height: 25px;
        width: 25px;
        background-color: #eee;
    }

    /* On mouse-over, add a grey background color */
    .container:hover input ~ .checkmark {
        background-color: #ccc;
    }

    /* When the checkbox is checked, add a blue background */
    .container input:checked ~ .checkmark {
        background-color: #2196F3;
    }

    /* Create the checkmark/indicator (hidden when not checked) */
    .checkmark:after {
        content: "";
        position: absolute;
        display: none;
    }

    /* Show the checkmark when checked */
    .container input:checked ~ .checkmark:after {
        display: block;
    }

    /* Style the checkmark/indicator */
    .container .checkmark:after {
        left: 9px;
        top: 5px;
        width: 5px;
        height: 10px;
        border: solid white;
        border-width: 0 3px 3px 0;
        -webkit-transform: rotate(45deg);
        -ms-transform: rotate(45deg);
        transform: rotate(45deg);
    }

    #div2 {
        display: none;
    }

    #div3 {
        display: none;
    }
</style>


