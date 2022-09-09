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
        IQuestionDao questionDao;
        try {
            questionDao = new QuestionDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Boolean feedback = true;
        request.getSession().setAttribute("feedback", feedback);
        ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");
        Integer curID = (Integer) request.getSession().getAttribute("CurrentQuestion");
        out.println("<h3>Question #" + (curID + 1) + "</h3>");
        Question question = questions.get(curID);
        String s = (String) request.getSession().getAttribute("question" + curID);
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
        <input class="button1" type="submit" value="Next Question"/>
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

