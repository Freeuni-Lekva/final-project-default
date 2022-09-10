<%@ page import="java.sql.SQLException" %>
<%@ page import="Quizs.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Users.User" %><%--
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
<div class="form">
    <h3>Question <%="#" + (curID + 1)%>
    </h3>
    <div class="timer" onload="timer(10)">
        <div>Section</div>
        <div class="time">
            <strong>Time left: <span id="time">Loading...</span></strong>
        </div>
    </div>
    <h1>
        <%String link = question.getDescription().substring(0, question.getDescription().indexOf(' '));%>
        <%String quest = question.getDescription().substring(question.getDescription().indexOf(' '));%>
        <img src="<%=link%>" alt="<%=link%>">
    </h1>
    <h1>
        <%=quest%>
    </h1>
    <form action="./MultiPageServlet" name="timeOut"  id="timeOut" method="post">
        <% out.print("<input type=\"text\" name=\"question" + curID + "\"" + ">");%><br>
        <input class="button1" type="hidden" id="timeLeft" name="timeLeft" value="">
        <input class="button1" type="submit" value="Next Question">
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
</style>

<script>
    let time = <%= (int) request.getSession().getAttribute("timeLeft")%>;
    setInterval(function() {
        let seconds = time % 60;
        let minutes = (time - seconds) / 60;
        if (seconds.toString().length == 1) {
            seconds = "0" + seconds;
        }
        if (minutes.toString().length == 1) {
            minutes = "0" + minutes;
        }
        document.getElementById("time").innerHTML = minutes + ":" + seconds;
        document.getElementById("timeLeft").setAttribute('value' , time);
        time--;
        if (time == 0) {
            document.timeOut.submit();
        }
    }, 1000);
</script>
