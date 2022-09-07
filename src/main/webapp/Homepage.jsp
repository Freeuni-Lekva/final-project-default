<%@ page import="Quizs.QuizDao"%>
<%@ page import="History.HistoryDao" %>
<%@ page import="Mails.MailDao" %>
<%@ page import="Achievement.AchievementDAO" %>
<%@ page import="Users.UserService" %>
<%@ page import="Quizs.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Users.User" %>
<%@ page import="Achievement.AchievementHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="Mails.Mail" %>
<%@ page import="java.util.HashMap" %>

<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 8/11/2022
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        QuizDao qd = (QuizDao) application.getAttribute("QuizDao");
        HistoryDao hd = (HistoryDao) application.getAttribute("HistoryDao");
        MailDao md = (MailDao) application.getAttribute("MailDao");
        AchievementDAO ad = (AchievementDAO) application.getAttribute("AchievementDao");
        UserService us = (UserService) application.getAttribute("UserService");
        try {
            ArrayList<Quiz> popularQuizzes = hd.getPopularQuizzes(5);
            ArrayList<Quiz> recentQuizzes = qd.getRecentQuizzes(5);
            User user = us.getUser(request.getParameter("username"));
            ArrayList<Quiz> recetQuizTakeActivity = hd.getRecentQuizzesTakenBy(user.getId(), 5);
            ArrayList<Quiz> recentQuizCreationActivities = qd.getRecentQuizzes(user, 5);
            List<AchievementHistory> userAchievements = ad.getUserAchievements(user.getId());
            List<Mail> userIncomingRecentMails = md.getUsersRecentIncomingMails(user.getId());

            //friends activities
            List<User> friends = us.getFriends(user);
            HashMap<User, Quiz> friendRecentQuizzesTaken = new HashMap<>();
            HashMap<User, Quiz> friendRecentQuizCreatingActivities = new HashMap<>();
            HashMap<User, AchievementHistory> friendsAchievements = new HashMap<>();
            //until this code is all you need

            for(int i = 0; i < friends.size(); i++){
                ArrayList<Quiz> recentQuizTakingActivity = hd.getRecentQuizzesTakenBy(friends.get(i).getId(), 1);
                if(recentQuizTakingActivity.size() > 0 && recentQuizTakingActivity.get(0) != null) {
                    friendRecentQuizzesTaken.put(friends.get(i), recentQuizTakingActivity.get(0));
                }
                ArrayList<Quiz> recentQuizCreatingActivity = qd.getRecentQuizzes(friends.get(i), 1);
                if(recentQuizCreatingActivity.size() > 0 && recentQuizCreatingActivity.get(0) != null) {
                    friendRecentQuizCreatingActivities.put(friends.get(i), recentQuizCreatingActivity.get(0));
                }
                List<AchievementHistory> friendsAchievement = ad.getUserAchievements(friends.get(i).getId());
                if(friendsAchievement.size() > 0 && friendsAchievement.get(0) != null){
                    friendsAchievements.put(friends.get(i), friendsAchievement.get(0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    %>
    <title>Homepage</title>
</head>
<body>
    <h1><%= "Welcome " + request.getParameter("username") %></h1>

</body>
</html>
