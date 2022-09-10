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
    <form id="form" action="../SearchServlet" method="get">
        <input type="text" name="searchTerm" id="searchTerm"><br><br>
        <button class="button1" style="vertical-align:middle" name="searchType" type="submit" value="quiz"><span>Search Quizes </span>
        </button>
        <button class="button2" style="vertical-align:middle" name="searchType" type="submit" value="user"><span>Search Users </span>
        </button>
    </form>
</div>
</body>
</html>
<style>

    .button2 {
        display: inline-block;
        border-radius: 4px;
        background-color: cornflowerblue;
        border: none;
        color: #FFFFFF;;
        text-align: center;
        font-size: 20px;
        padding: 20px;
        width: 200px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 5px;
    }

    .button1 {
        display: inline-block;
        border-radius: 4px;
        background-color: #f4511e;
        border: none;
        color: #FFFFFF;
        text-align: center;
        font-size: 20px;
        padding: 20px;
        width: 200px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 5px;
    }

    button span {
        cursor: pointer;
        display: inline-block;
        position: relative;
        transition: 0.5s;
    }

    button span:after {
        content: '\00bb';
        position: absolute;
        opacity: 0;
        top: 0;
        right: -20px;
        transition: 0.5s;
    }

    button:hover span {
        padding-right: 25px;
    }

    button:hover span:after {
        opacity: 1;
        right: 0;
    }


    input[type="text"] {
        width: 600px;
        padding: 10px;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 30px;
        border: 2px solid cornflowerblue;
        border-radius: 50px;
    }

    div.form {
        font-family: Arial, Helvetica, sans-serif;
        display: block;
        margin-top: 20%;
        text-align: center;
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
