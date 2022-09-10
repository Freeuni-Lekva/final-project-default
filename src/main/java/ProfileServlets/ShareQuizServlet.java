package ProfileServlets;

import Mails.MailService;
import Quizs.Quiz;
import Quizs.QuizDao;
import Users.User;
import Users.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Inet4Address;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ShareQuizServlet", value = "/ShareQuizServlet")
public class ShareQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserService userSer = new UserService();
            User user = (User) req.getSession().getAttribute("currentUser");
            User friend = userSer.getUser(Integer.parseInt(req.getParameter("id")));
            int quizId = Integer.parseInt((String) req.getSession().getAttribute("sharedQuiz"));
            QuizDao qd = new QuizDao();
            Quiz quiz = qd.getQuiz(quizId);
            if (friend == null || friend.getId() == user.getId()) {
                return;
            }
            MailService mailSer = new MailService();
            mailSer.addMail(user.getId(), friend.getId(), "CH", quiz.getTitle(), Integer.toString(quiz.getId()), new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("./Homepage/Homepage.jsp");
    }
}
