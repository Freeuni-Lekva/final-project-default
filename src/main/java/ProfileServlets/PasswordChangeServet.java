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

@WebServlet(name = "passwordChangeServlet", value = "/passwordchange")
public class PasswordChangeServet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        String oldPassword = req.getParameter("current-password");
        String newPassword = req.getParameter("new-password");
        if (oldPassword.equals(newPassword)) {
            resp.getWriter().write("current and new passwords shouldn't match!");
            return;
        }

        resp.setContentType("text/plain");
        System.out.println("HO");
        try {
            UserService service = new UserService();
            System.out.println("HO");

            boolean validatePassword = service.login(currentUser.getUsername(), oldPassword);
            req.getRequestDispatcher("WEB-INF/passwordchanged.jsp").forward(req, resp);
            if (validatePassword) {
                service.changePassword(currentUser, newPassword);
                resp.getWriter().write("Password Changed Successfully!");
            } else {
                resp.getWriter().write("Wrong current password!");
            }
        } catch (SQLException e) {
            resp.getWriter().write("Couldn't change password for internal errors, try again later");
        } catch (NoSuchAlgorithmException e) {
            resp.getWriter().write("Couldn't change password for internal errors, try again later");
        }
    }
}
