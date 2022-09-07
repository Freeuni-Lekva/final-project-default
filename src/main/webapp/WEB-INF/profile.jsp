<%@ page import="java.util.ArrayList" %>
<%@ page import="Achievement.AchievementHistory" %>
<%@ page import="History.History" %>
<%@ page import="Quizs.Quiz" %>
<%@ page import="Users.User" %>
<%@ page import="Mails.Mail" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Quiz Profile</title>
</head>
<body>

<% User visitedUser = (User) request.getAttribute("visitedUser"); %>
<% User currUser = (User) request.getAttribute("user"); %>

<% if (currUser != null && visitedUser.getUsername().equals(currUser.getUsername())) {%>
<%-- TDOO: change action --%>
<form action="/quizWebsite_war_exploded/settings" method="get">
        <input type="submit" value="Settings">
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
    <form action=<%="/quizWebsite_war_exploded/" + url%> method="post">
        <input type="hidden" name="other-user" value=<%=visitedUser.getUsername()%>>
        <input type="submit" value=<%=friendBtnvalue%>>
    </form>
<% } %>

<%--    Achievements--%>
<h1>Achievements</h1>
<% ArrayList<AchievementHistory> arr = (ArrayList<AchievementHistory>) request.getAttribute("achievements"); %>
<% if (!arr.isEmpty()){%>
<% for(int i = 0; i < arr.size(); i+=1) { %>
<tr>
    <td><%=arr.get(i).getName() %></td>
    <td><%=arr.get(i).getDescription()%></td>
    <td><%=arr.get(i).getDate()%></td>
    <br>
</tr>
<% }%>
<% } else {%>
User Has No Achievements
<% } %>

<% ArrayList<Quiz> takenQuizzes = (ArrayList<Quiz>) request.getAttribute("takenQuizzes"); %>
<h1> Recently Taken Quizzes</h1>
<% if (!takenQuizzes.isEmpty()){%>
<% for(int i = 0; i < takenQuizzes.size(); i+=1) { %>
<tr>
    <td><%=takenQuizzes.get(i).getTitle()%></td>
    <br>
</tr>
<% }%>
<% } else {%>
<%=visitedUser.getUsername()%> hasn't taken any quizzes yet
<% } %>

<%--Made Quizes--%>
<h1>Quizzes made by <%=visitedUser.getUsername()%></h1>
<% ArrayList<Quiz> madeQuizzes = (ArrayList<Quiz>) request.getAttribute("madeQuizzes"); %>
<% if (!madeQuizzes.isEmpty()) { %>
    <% for(int i = 0; i < madeQuizzes.size(); i+=1) { %>
    <tr>
     <td><%=madeQuizzes.get(i).getTitle()%></td>
    <% } %>
<% } else {%>
<%=visitedUser.getUsername()%> hasn't made any quizzes
<% } %>

<% if (currUser != null && visitedUser.getUsername().equals(currUser.getUsername())) {%>
    <h1>Incoming Mails:</h1>
    <%List<Mail> mailList = (List<Mail>) request.getAttribute("mailList");%>
            <% for(int i = 0; i < madeQuizzes.size(); i+=1) { %>
            <tr>
                <td><%=mailList.get(i).getFromId()%></td>
                <br>
                <td><%=mailList.get(i).getTitle()%></td>
                <br>
                <td><%=mailList.get(i).getContent()%></td>
                <br>
                <td><%=mailList.get(i).getDate()%></td>
                <br>
                <br>
            </tr>
    <%}%>
<% }%>

</body>
</html>
