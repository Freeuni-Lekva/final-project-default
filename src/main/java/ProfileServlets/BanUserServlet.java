package ProfileServlets;

import Users.User;
import Users.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "banUserServlet", value = "/banUser")
public class BanUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return;
        }
        String username = req.getParameter("ban-user");
        int days = Integer.valueOf(req.getParameter("time"));
        try {
            UserService ser = new UserService();
            ser.banUser(username, days);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
