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
    <div class="topnav">
        <a href="">Home</a>
        <a href="#news">Profile</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
        <% if (request.getSession().getAttribute("CurrentUser") != null) {
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
<div class="form">
    <%
        User user = (User) request.getSession().getAttribute("currentUser");
        Integer score = (Integer) request.getAttribute("FinalScore");
        java.sql.Date st_time = (Date) request.getSession().getAttribute("st_time");
        java.sql.Date end_time = (Date) request.getSession().getAttribute("end_time");
        Integer quizID = Integer.parseInt((String) request.getSession().getAttribute("quiz_id"));
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
    <h2>Start Time : <%=st_time%> </br> End Time: <%=end_time%>
    </h2>
    <%
        ArrayList<Question> quests = (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");
        for (int i = 0; i < quests.size(); i++) {
            String s = (String) request.getSession().getAttribute("question" + i);
            Question question = quests.get(i);
            out.println("<h2>Question Number " + (i + 1) + "</h2>");
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
                out.println("<h3>Your Answer: " + s + "</h3>");
                out.println("<h3>Correct Answer: " + correctAns + "</h3>");
            } else {
                String[] ansS = s.split(",");
                String temp = "";
                for (int k = 0; k < ansS.length; k++)
                    temp += ansS[k] + " ";
                out.println("<h3>Your Answer: " + temp + "</h3>");
                String correctAns = "";
                for (int k = 0; k < answers.size(); k++) {
                    if (answers.get(k).isCorrect())
                        correctAns += answers.get(k).getDescription() + " ";
                }
                out.println("<h3>Correct Answer: " + correctAns + "</h3>");
            }
        }
    %>
    <form action="./Homepage/Homepage.jsp">
        <input type="submit" class="button1" value="Return to home page"/>
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
