package ProfileServlets;

import Mails.Mail;
import Mails.MailService;
import Users.User;
import Users.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "sendFriendRequestServlet", value = "/sendfriendrequest")
public class SendFriendRequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        User otherUser;
        try {
            UserService us = new UserService();
            otherUser = us.getUser(req.getParameter("other-user"));
            us.addFriend(currentUser , otherUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        MailService service = null;
        try {
            service = new MailService();
            service.addMail(currentUser.getId(), otherUser.getId(), "FRT", "", "", new java.sql.Date(new Date().getTime()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("Homepage/Homepage.jsp");

    }
}
