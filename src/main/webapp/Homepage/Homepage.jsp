<%@ page import="Quizs.QuizDao" %>
<%@ page import="History.HistoryDao" %>
<%@ page import="Mails.MailDao" %>
<%@ page import="Achievement.AchievementDAO" %>
<%@ page import="Users.UserService" %>
<%@ page import="Quizs.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Users.User" %>
<%@ page import="Achievement.AchievementHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="Mails.Mail" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="javax.jws.soap.SOAPBinding" %>
<%@ page import="History.HistoryService" %>

<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/11/2022
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        QuizDao qd = (QuizDao) application.getAttribute("QuizDao");
        HistoryService hd = (HistoryService) application.getAttribute("HistoryService");
        MailDao md = (MailDao) application.getAttribute("MailDao");
        AchievementDAO ad = (AchievementDAO) application.getAttribute("AchievementDao");
        UserService us = (UserService) application.getAttribute("UserService");
        User user = (User) request.getSession().getAttribute("currentUser");
        ArrayList<Quiz> popularQuizzes;
        ArrayList<Quiz> recentQuizzes;
        ArrayList<Quiz> recentQuizCreationActivities = null;
        List<AchievementHistory> userAchievements = null;
        List<Mail> userIncomingRecentMails;
        ArrayList<Quiz> recetQuizTakeActivity = null;

        //friends activities
        List<User> friends;
        HashMap<User, Quiz> friendRecentQuizzesTaken = null;
        HashMap<User, Quiz> friendRecentQuizCreatingActivities = null;
        HashMap<User, AchievementHistory> friendsAchievements;
        try {
            popularQuizzes = hd.getPopularQuizzes(5);
            recentQuizzes = qd.getRecentQuizzes(5);
            if (user != null) {
                recentQuizCreationActivities = qd.getRecentQuizzes(user, 5);
                userAchievements = ad.getUserAchievements(user.getId());
                recetQuizTakeActivity = hd.getRecentQuizzesTakenBy(user.getId(), 5);
                userIncomingRecentMails = md.getUsersRecentIncomingMails(user.getId());

                //friends activities
                friends = us.getFriends(user);
                friendRecentQuizzesTaken = new HashMap<>();
                friendRecentQuizCreatingActivities = new HashMap<>();
                friendsAchievements = new HashMap<>();
                //until this code is all you need

                for (int i = 0; i < friends.size(); i++) {
                    ArrayList<Quiz> recentQuizTakingActivity = hd.getRecentQuizzesTakenBy(friends.get(i).getId(), 1);
                    if (recentQuizTakingActivity.size() > 0 && recentQuizTakingActivity.get(0) != null) {
                        friendRecentQuizzesTaken.put(friends.get(i), recentQuizTakingActivity.get(0));
                    }
                    ArrayList<Quiz> recentQuizCreatingActivity = qd.getRecentQuizzes(friends.get(i), 1);
                    if (recentQuizCreatingActivity.size() > 0 && recentQuizCreatingActivity.get(0) != null) {
                        friendRecentQuizCreatingActivities.put(friends.get(i), recentQuizCreatingActivity.get(0));
                    }
                    List<AchievementHistory> friendsAchievement = ad.getUserAchievements(friends.get(i).getId());
                    if (friendsAchievement.size() > 0 && friendsAchievement.get(0) != null) {
                        friendsAchievements.put(friends.get(i), friendsAchievement.get(0));
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    %>
    <title>Homepage</title>
    <div class="topnav">
        <a href="./Homepage.jsp">Home</a>
        <% if (request.getSession().getAttribute("currentUser") == null) { %>
        <a class="logout" href="../LoginJSPs/CreateAccount.jsp">Create Account</a>
        <a class="logout" href="../LoginJSPs/LoginJSP.jsp">Log In</a>
        <%}%>
        <a href="../SearchJSPs/Search.jsp">Search</a>
        <% if (request.getSession().getAttribute("currentUser") != null) { %>
        <a href="../profile?user=<%=user.getUsername()%>">Profile</a>
        <a href="Mails.jsp">Mails</a>
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
    <h1><%= "Welcome To The Quiz Site"%>
    </h1>
    <input class="tabs" type="button" name="Showdiv1" value="Popular Quizzes" onclick="showDiv('1')"/>
    <input class="tabs" type="button" name="Showdiv2" value="Recently Added Quizzes" onclick="showDiv('2')"/>
    <%if(user != null){ %>
    <input class="tabs" type="button" name="Showdiv3" value="My Recently Taken Quizzes" onclick="showDiv('3')"/>
    <input class="tabs" type="button" name="Showdiv4" value="My Quizzes" onclick="showDiv('4')"/>
    <input class="tabs" type="button" name="Showdiv5" value="My Achievements" onclick="showDiv('5')"/>
    <input class="tabs" type="button" name="Showdiv6" value="Quizzes Taken By Friends" onclick="showDiv('6')"/>
    <input class="tabs" type="button" name="Showdiv6" value="Quizzes Created By Friends" onclick="showDiv('7')"/>
    <% } %>
    <div id="div1">
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                for (Quiz quiz : popularQuizzes) {
                    out.println("<tr><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div2>
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                for (Quiz quiz : recentQuizzes) {
                    out.println("<tr><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <%if(user != null){ %>
    <div id=div3>
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                for (Quiz quiz : recetQuizTakeActivity) {
                    out.println("<tr><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div4>
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                for (Quiz quiz : recentQuizCreationActivities) {
                    out.println("<tr><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div5>
        <table class="topscorers">
            <tr>
                <th>Achievement Name</th>
                <th>Description</th>
                <th>Date Acquired</th>
            </tr>
            <%
                for (AchievementHistory ahist : userAchievements) {
                    out.println("<tr><td>" + ahist.getName() + "</td><td>" + ahist.getDescription() + "</td><td>" + ahist.getDate() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div6>
        <table class="topscorers">
            <tr>
                <th>Username</th>
                <th>Quiz</th>
            </tr>
            <%
                for (User friendsTaken : friendRecentQuizzesTaken.keySet()) {
                    out.println("<tr><td>" + friendsTaken.getUsername() + "</td><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + friendRecentQuizzesTaken.get(friendsTaken).getId() + "\">" + friendRecentQuizzesTaken.get(friendsTaken).getTitle() + "</a>" + "</td></tr>");
                }%>
        </table>
    </div>

    <div id=div7>
        <table class="topscorers">
            <tr>
                <th>Username</th>
                <th>Quiz</th>
            </tr>
            <%
                for (User friendsTaken : friendRecentQuizCreatingActivities.keySet()) {
                    out.println("<tr><td>" + friendsTaken.getUsername() + "</td><td>" + "<a href=\"../ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + friendRecentQuizzesTaken.get(friendsTaken).getId() + "\">" + friendRecentQuizzesTaken.get(friendsTaken).getTitle() + "</a>" + "</td></tr>");
                }%>
        </table>
    </div>
    <%}%>
    <script type="text/javascript">
        function showDiv(num) {
            document.getElementById('div1').style.display = 'none';
            document.getElementById('div2').style.display = 'none';
            <%if(user != null){ %>
            document.getElementById('div3').style.display = 'none';
            document.getElementById('div4').style.display = 'none';
            document.getElementById('div5').style.display = 'none';
            document.getElementById('div6').style.display = 'none';
            document.getElementById('div7').style.display = 'none';
            <% } %>
            document.getElementById('div' + num).style.display = 'block'
        }
    </script>
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

    #div4 {
        display: none;
    }

    #div5 {
        display: none;
    }

    #div6 {
        display: none;
    }

    #div7 {
        display: none;
    }

</style>



