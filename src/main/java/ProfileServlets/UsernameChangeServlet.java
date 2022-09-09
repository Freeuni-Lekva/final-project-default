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

@WebServlet(name = "usernameChangeServlet", value = "/usernamechange")
public class UsernameChangeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        User currentUser = (User) req.getSession().getAttribute("currentUser");
        String newUsername = req.getParameter("new-username");

        try {
            UserService service = new UserService();
            boolean exists = service.getUser(newUsername) != null;
            if (exists) {
                resp.getWriter().println("User with such username already Exists!");
            } else {
                boolean changed = service.changeUsername(currentUser, newUsername);
                if (changed) {
                    resp.getWriter().println("Password succesfully Changed");
                } else {
                    resp.getWriter().println("NO!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
