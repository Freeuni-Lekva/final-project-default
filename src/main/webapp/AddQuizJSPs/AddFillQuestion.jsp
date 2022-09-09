<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/7/2022
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Multiple Choice Question</title>
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
<div class="d-flex flex-column justify-content-center w-100 h-100"></div>

<button onclick="location.href='${pageContext.request.contextPath}/AddQuizJSPs/AddQuestions.jsp'" type="button"
        class="button4">
    Back
</button>
<div class="form">
    <form action="../AddFillQuestionServlet" id="addFillQuestion" method="post">
        <h4>Please, Write Question. Type "[blank]" For Unfilled Words.</h4>
        <h5>Example: "The First President Of USA Was [blank]"</h5>
        <textarea form="addFillQuestion" maxlength="500" id="questionDescription" name="questionDescription"
                  cols="15"></textarea>
        <br><br><br><br><br><h4>Please, Write Answers In Order. Separate Each Answer With Comma.</h4>
        <textarea form="addFillQuestion" maxlength="500" id="answer" name="answer" cols="15"></textarea><br>
        <br><br><br><br><br><input class="button1" form="addFillQuestion" type="submit" id="addQuestionButton"
                                   name="addQuestionButton" value="Add">
    </form>
</div>
</body>
</html>
<style>


    button {
        margin-top: 10px;
        background-color: red; /* Green */
        border: none;
        color: white;
        padding: 7px 16px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        border-radius: 8px;
        transition-duration: 0.4s;
    }

    button:hover {
        box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
    }

    div.form {
        font-family: Arial, Helvetica, sans-serif;
        display: block;
        text-align: center;
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

    input {
        font-family: Arial, Helvetica, sans-serif;
        border: 2px solid gray;
        border-radius: 5px;
        display: inline-block;
        float: left;
    }

    label {
        font-family: Arial, Helvetica, sans-serif;
        display: inline-block;
        float: left;
        clear: left;
        width: 250px;
        text-align: left; /*Change to right here if you want it close to the inputs*/
    }

    form {
        display: inline-block;
        margin-left: auto;
        margin-right: auto;
        text-align: left;
    }

    .button1 {
        background-color: #4CAF50; /* Green */
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

    .button1:hover {
        box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
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


