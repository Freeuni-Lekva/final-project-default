package ProfileServlets;

import Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deactivateUserServlet", value = "/deactivateUser")
public class DeactivateUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return;
        }
        String username = req.getParameter("deactivate-user");
        // aq user serviceshi unda iyos
    }
}
