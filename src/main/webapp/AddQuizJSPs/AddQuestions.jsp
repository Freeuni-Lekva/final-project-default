<%@ page import="Quizs.Quiz" %>
<%@ page import="Quizs.IQuizDao" %>
<%@ page import="Quizs.QuizDao" %>
<%@ page import="Users.User" %><%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/7/2022
  Time: 12:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Questions To The Quiz</title>
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
    <h1>Add Questions To The <%= ((Quiz) request.getSession().getAttribute("NewQuiz")).getTitle()%>
    </h1>
    <h3>Please Choose What Type Of Question You Want To Add:</h3>

    <button onclick="location.href='${pageContext.request.contextPath}/AddQuizJSPs/AddMultipleChoiceQuestion.jsp'"
            type="button" class="button1">
        Multiple Choice Question
    </button>
    <button onclick="location.href='${pageContext.request.contextPath}/AddQuizJSPs/AddPictureResponseQuestion.jsp'"
            type="button" class="button2">
        Picture Response Question
    </button>
    <button onclick="location.href='${pageContext.request.contextPath}/AddQuizJSPs/AddQuestionResponseQuestion.jsp'"
            type="button" class="button3">
        Question Response Question
    </button>
    <button onclick="location.href='${pageContext.request.contextPath}/AddQuizJSPs/AddFillQuestion.jsp'" type="button"
            class="button4">
        Fill Question
    </button>
    <br><br>
    <form action="./ShowQuizJSPs/ShowQuiz.jsp" id="BackForm" method="get">
        <input type="submit" id="BackButt" name="finishButt" class="finishButt" value="Finish Adding Quiz">
        <input type="hidden"  name="quiz_id" class="finishButt" value="<%=((Quiz) request.getSession().getAttribute("NewQuiz")).getId()%>">
    </form>
</div>
</body>
</html>

<style>

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

    .finishButt {
        background-color: deepskyblue; /* Green */
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        border-radius: 8px;
        transition-duration: 0.4s;
    }

    .finishButt:hover {
        box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
    }

    button {
        background-color: #4CAF50; /* Green */
        border: none;
        color: white;
        padding: 16px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        transition-duration: 0.4s;
        cursor: pointer;
    }

    .button1 {
        background-color: white;
        color: black;
        border: 2px solid #4CAF50;
    }

    .button1:hover {
        background-color: #4CAF50;
        color: white;
    }

    .button2 {
        background-color: white;
        color: black;
        border: 2px solid #008CBA;
    }

    .button2:hover {
        background-color: #008CBA;
        color: white;
    }

    .button3 {
        background-color: white;
        color: black;
        border: 2px solid #f44336;
    }

    .button3:hover {
        background-color: #f44336;
        color: white;
    }

    .button4 {
        background-color: white;
        color: black;
        border: 2px solid #555555;
    }

    .button4:hover {
        background-color: #555555;
        color: white;
    }

</style>
