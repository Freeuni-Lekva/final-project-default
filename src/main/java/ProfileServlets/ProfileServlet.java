package ProfileServlets;

import Achievement.AchievementDAO;
import Achievement.AchievementHistory;
import Achievement.AchievementService;
import History.History;
import History.HistoryService;
import Mails.FriendRequest;
import Mails.Mail;
import Mails.MailService;
import Quizs.DatabaseConnection;
import Quizs.Quiz;
import Quizs.QuizDao;
import Users.User;
import Users.UserDao;
import Users.UserService;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "profileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {

    private void getAchievements() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Sanam loginis/parolis shesayvani ar daemateba ase iyos
//        try {
//            req.getSession().setAttribute("user", new UserService().getUser(1));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }

        User currUser = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", currUser);

        String username = req.getParameter("user");
        User visitingUser;
        // Get User
        try {
            visitingUser = new UserService().getUser(username);
            req.setAttribute("visitedUser", visitingUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // User not found
        if (visitingUser == null) {
            req.getRequestDispatcher("WEB-INF/userNotFound.jsp").forward(req, resp);
            return;
        }

        friendRequestButton(req, currUser, visitingUser);

        // Get Achievement
        try {
            AchievementService service = new AchievementService();
            List<AchievementHistory> achs = service.getUserAchievements(visitingUser.getId());
            req.setAttribute("achievements", achs);
        } catch (Exception e) {
            // Create empty list
            req.setAttribute("achievements", new ArrayList<AchievementHistory>());
        }

        // List of recently taken quizzes
        try {
            QuizDao quizDao = new QuizDao();
            ArrayList<Quiz> arr = quizDao.getRecentQuizzes(visitingUser, 10);
            req.setAttribute("takenQuizzes", arr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // List Of Made Quizzes
        try {
            QuizDao quizDao = new QuizDao();
            ArrayList<Quiz> madeQuizzes = quizDao.getQuizzes(visitingUser);
            req.setAttribute("madeQuizzes", madeQuizzes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (currUser != null && currUser.getUsername().equals(visitingUser.getUsername())) {
            try {
                MailService service = new MailService();
                List<Mail> mailList = service.getUsersIncomingMails(currUser.getId());
                req.setAttribute("mailList", mailList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        req.getRequestDispatcher("WEB-INF/profile.jsp").forward(req, resp);
    }

    private void friendRequestButton(HttpServletRequest req, User currUser, User visitingUser) {
        if (currUser != null && !currUser.getUsername().equals(visitingUser.getUsername())) {
            try {
                MailService mailService = new MailService();
                List<Mail> incomingMails = mailService.getUsersIncomingMails(currUser.getId());
                for (int i = 0; i < incomingMails.size(); ++i) {
                    if (incomingMails.get(i) instanceof FriendRequest) {
                        FriendRequest currFriendRequest = (FriendRequest) incomingMails.get(i);
                        if (currFriendRequest.getFromId() == visitingUser.getId()) {
                            req.setAttribute("friendship", "incoming");
                            return;
                        }
                    }
                }

                List<Mail> outgoing = mailService.getUsersOutgoingMails(currUser.getId());
                for (int i = 0; i < outgoing.size(); ++i) {
                    if (outgoing.get(i) instanceof FriendRequest) {
                        FriendRequest currFriendRequest = (FriendRequest) outgoing.get(i);
                        if (currFriendRequest.getToId() == visitingUser.getId()) {
                            req.setAttribute("friendship", "outgoing");
                            return;
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("friendship", "none");
        }
    }
}