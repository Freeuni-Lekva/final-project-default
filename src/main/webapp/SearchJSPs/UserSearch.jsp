<%@ page import="java.util.ArrayList" %>
<%@ page import="Quizs.Quiz" %>
<%@ page import="Users.UserService" %>
<%@ page import="Users.User" %><%--
  Created by IntelliJ IDEA.
  User: nikag
  Date: 9/7/2022
  Time: 12:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
    <div class="topnav">
        <a href="./Homepage/Homepage.jsp">Home</a>
        <% if (request.getSession().getAttribute("currentUser") == null) { %>
        <a class="logout" href="./LoginJSPs/CreateAccount.jsp">Create Account</a>
        <a class="logout" href="./LoginJSPs/LoginJSP.jsp">Log In</a>
        <%}%>
        <a href="./SearchJSPs/Search.jsp">Search</a>
        <% if (request.getSession().getAttribute("currentUser") != null) { %>
        <a href="./profile?user=<%=((User)request.getSession().getAttribute("currentUser")).getUsername()%>">Profile</a>
        <a href="./Homepage/Mails.jsp">Mails</a>
        <a class="logout" href="./LogOutServlet">Log Out</a>
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
        <% ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
                UserService us = (UserService) application.getAttribute("UserService");
        %>
</head>
<body>
<div class="d-flex flex-column justify-content-center w-100 h-100"></div>
<div class="form">
    <h1>Search Results: </h1>
    <table class="topscorers">
        <tr>
            <th>Username</th>
        </tr>
        <%
            for (User user : users) {
                out.println("<tr><td>" + "<a href=\"./profile?user=" + user.getUsername() + "\">" + user.getUsername() + "</a>" + "</td></tr>");
            }
        %>
    </table>
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
