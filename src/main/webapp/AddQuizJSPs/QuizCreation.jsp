<%@ page import="Users.User" %><%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/3/2022
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Quiz</title>
    <div class="topnav">
        <a href="">Home</a>
        <a href="#news">Profile</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
        <% if(request.getSession().getAttribute("CurrentUser") == null){
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
<div class="form">
    <h1>Quiz Creation</h1>
    <form action="../AddQuizServlet" id="addQuizForm" method="post">
        <label for="quizTitle">Quiz Title</label>
        <input form="addQuizForm" type="text" id="quizTitle" name="quizTitle"><br><br>
        <label for="quizTime">Length Of Quiz In Seconds</label>
        <input form="addQuizForm" type="number" id="quizTime" name="quizTime"><br><br>
        <label for="quizDescription">Quiz Description</label>
        <textarea form="addQuizForm" maxlength="1500" name="quizDescription" id="quizDescription"
                  cols="35"></textarea><br><br>
        <label>Random Quiz Order<input form="addQuizForm" name="isRandom" type="checkbox" id="isRandom"
                                       value="isRandom"><span class="slider"></span>
        </label>
        <label>Multi Question Page<input form="addQuizForm" name="isOnePage" type="checkbox" id="isOnePage"
                                         value="isOnePage"><span class="slider"></span>
        </label>

        <label>Immediate Correction<input form="addQuizForm" name="imediateCorrection" type="checkbox"
                                          id="imediateCorrection" value="imediateCorrection"><span
                class="slider"></span>
        </label>
        <label>Can Be Taken In Practice Mode<input name="canBePracticed" form="addQuizForm" type="checkbox"
                                                   id="canBePracticed" value="canBePracticed"><span
                class="slider"></span>
        </label>
        <input class="button1" type="submit" id="addQuizButton" name="addQuizButton">
    </form>
</div>
</body>
</html>
<style>

    h1{
        font-family: Arial, Helvetica, sans-serif;
    }
    .button1 {
        margin-top: 50px;
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

    label {
        font-family: Arial, Helvetica, sans-serif;
        display: inline-block;
        float: left;
        clear: left;
        width: 250px;
        text-align: left; /*Change to right here if you want it close to the inputs*/
    }

    input {
        font-family: Arial, Helvetica, sans-serif;
        border: 2px solid cornflowerblue;
        border-radius: 5px ;
        display: inline-block;
        float: left;
    }

    textarea {
        border: 2px solid cornflowerblue;
        font-family: Arial, Helvetica, sans-serif;
        border-radius: 5px;
        resize: none;
        width: 300px;
        height: 150px;
        display: inline-block;
        float: left;
    }

    div.form {
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

