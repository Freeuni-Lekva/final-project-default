package ProfileServlets;

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
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "sendMailServlet", value = "/sendmail")
public class SendMailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) return;
        String recipient = req.getParameter("recipient");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (recipient == null || title == null || content == null) return;
        if (title.isEmpty() || content.isEmpty()) return;
        try {
            UserService userSer = new UserService();
            User recipientUser = userSer.getUser(recipient);
            if (recipientUser == null || recipientUser.getId() == user.getId()) {
                return;
            }
            MailService mailSer = new MailService();
            mailSer.addMail(user.getId(), recipientUser.getId(), "NOTE", title, content, new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
