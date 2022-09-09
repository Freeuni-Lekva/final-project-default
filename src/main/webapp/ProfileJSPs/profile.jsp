<%@ page import="java.util.ArrayList" %>
<%@ page import="Achievement.AchievementHistory" %>
<%@ page import="History.History" %>
<%@ page import="Quizs.Quiz" %>
<%@ page import="Users.User" %>
<%@ page import="Mails.Mail" %>
<%@ page import="java.util.List" %>
<%@ page import="Users.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% User visitedUser = (User) request.getAttribute("visitedUser"); %>
    <% User currUser = (User) request.getAttribute("user"); %>
    <% UserService us = (UserService) request.getServletContext().getAttribute("UserService");%>
    <title>
        <%=visitedUser.getUsername()%>
    </title>
    <div class="topnav">
        <a href="">Home</a>
        <a href="#news">Profile</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
        <% if (request.getSession().getAttribute("currentUser") != null) {
            out.println("<a class = \"logout\" href=\"\">Log Out</a>");
        }%>
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
<% if (currUser != null && visitedUser.getUsername().equals(currUser.getUsername())) {%>
<%-- TDOO: change action --%>
<form action="ProfileJSPs/settings.jsp" method="get">
    <input type="submit" class="button1" value="Settings">
</form>
<% } else if (currUser != null) {%>
<% String friendship = (String) request.getAttribute("friendship"); %>
<% String friendBtnvalue = null; %>
<% String url = null; %>
<% if (friendship.equals("none")) {
    friendBtnvalue = "Send-Friend-Request";
    url = "sendfriendrequest";
} else if (friendship.equals("incoming")) {
    friendBtnvalue = "Accept-Friend-Request";
    url = "acceptfriendrequest";
} else if (friendship.equals("outgoing")) {
    friendBtnvalue = "Friend-Request-Sent";
    url = "removefriendrequest";
}%>
<form action=<%=url%> method="post">
    <input type="hidden" name="other-user" value=<%=visitedUser.getUsername()%>>
    <input type="submit" class="button1" value=<%=friendBtnvalue%>>
</form>
<% } %>

<div class="form">
    <h1>Welcome to <%=visitedUser.getUsername()%>'s profile</h1>
    <% if (currUser != null && !visitedUser.getUsername().equals(currUser.getUsername())) {%>
    <form action=sendmail method="post">
        <h3>Send Message</h3>
        <h5>Title</h5>
        <input type="text" name="title"><br>
        <input type="hidden" name="recipient" value="<%=visitedUser.getUsername()%>">
        <h5>Message</h5>
        <textarea maxlength="500" id="content" name="content"
                  cols="15"></textarea>
        <br>
        <input type="submit" class="button1" value="Send Message">
    </form>
    <% } %>
<br>
    <input class="tabs" type="button" name="Showdiv1" value="Taken Quizzes" onclick="showDiv('1')"/>
    <input class="tabs" type="button" name="Showdiv3" value="Added Quizzes" onclick="showDiv('3')"/>
    <input class="tabs" type="button" name="Showdiv2" value="Achievements" onclick="showDiv('2')"/>

    <div id="div1">
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                ArrayList<Quiz> arr = (ArrayList<Quiz>) request.getAttribute("takenQuizzes");
                for (Quiz quiz : arr) {
                    out.println("<tr><td>" + "<a href=\"ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div3>
        <table class="topscorers">
            <tr>
                <th>Quiz Title</th>
                <th>Times Taken</th>
                <th>Creator</th>
            </tr>
            <%
                ArrayList<Quiz> arr1 = (ArrayList<Quiz>) request.getAttribute("madeQuizzes");
                for (Quiz quiz : arr1) {
                    out.println("<tr><td>" + "<a href=\"ShowQuizJSPs/ShowQuiz.jsp?quiz_id=" + quiz.getId() + "\">" + quiz.getTitle() + "</a>" + "</td><td>" + quiz.getCount() + "</td><td>" + us.getUser(quiz.getCreatorId()).getUsername() + "</td></tr>");
                }
            %>
        </table>
    </div>
    <div id=div2>
        <table class="topscorers">
            <tr>
                <th>Achievement Name</th>
                <th>Description</th>
                <th>Date Acquired</th>
            </tr>
            <% List<AchievementHistory> userAchievements = (List<AchievementHistory>) request.getAttribute("achievements");
                for (AchievementHistory ahist : userAchievements) {
                    out.println("<tr><td>" + ahist.getName() + "</td><td>" + ahist.getDescription() + "</td><td>" + ahist.getDate() + "</td></tr>");
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

    textarea {
        border: 2px solid gray;
        font-family: Arial, Helvetica, sans-serif;
        border-radius: 5px;
        resize: none;
        width: 300px;
        height: 100px;
        display: inline-block;
        float: left;
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