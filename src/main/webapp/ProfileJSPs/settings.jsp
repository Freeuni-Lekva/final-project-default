<%--
  Created by IntelliJ IDEA.
  User: zken7
  Date: 02.08.22
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
</head>
<body>
<h3>Enter current password:</h3>
<input type="password" class="curr-password"><br>
<h3>Enter new password:</h3>
<input type="password" class="new-password"><br>
<h3>Enter new password again:</h3>
<input type="password" class="new-password"><br>
<button class="password-btn">Change Password</button>
<h1 class="password-error-message" style="color: red;"></h1>

<h3>Enter new username</h3>
<input type="text" class="new-username"><br>
<button class="username-btn">Change username</button>
<h1 class="username-error-message" style="color: red;"></h1>
</body>
<script>
    document.querySelector('.password-btn').addEventListener('click', changePassword);
    function changePassword() {
        currentPsw = document.querySelector('.curr-password').value
        newPsw1 = document.querySelectorAll('.new-password')[0].value
        newPsw2 = document.querySelectorAll('.new-password')[1].value
        if (currentPsw === '' || newPsw1 === '' || newPsw2 === '') {
            document.querySelector('.password-error-message').innerHTML = "e biwo sheavse yvela"
        } else if (newPsw1 != newPsw2) {
            document.querySelector('.password-error-message').innerHTML = "new passwords don't match"
        } else {
            $.ajax({
                type: 'POST',
                url: '/quizWebsite_war_exploded/passwordchange',
                data: {
                    "current-password": currentPsw,
                    "new-password": newPsw1
                },
                success: function(resp) {
                    document.querySelector('.password-error-message').innerHTML = resp;
                }
            });
        }
    }

    document.querySelector('.username-btn').addEventListener('click', changeUsername);
    function changeUsername() {
        newUsername = document.querySelector('.new-username').value;
        if (newUsername === '') {
            document.querySelector('.username-error-message').innerHTML = "e biwo sheavse"
        } else {
            $.ajax({
                type: 'POST',
                url: '/quizWebsite_war_exploded/usernamechange',
                data: {
                    "new-username": newUsername
                },
                success: function(resp) {
                    document.querySelector('.username-error-message').innerHTML = resp;
                }
            });
        }
    }
</script>
</html>
