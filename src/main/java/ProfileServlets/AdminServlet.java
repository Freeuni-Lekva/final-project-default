package ProfileServlets;

import Users.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "adminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            // gadaiyvane loginis pageze jer sxvebs rogor uweriat eg aris gasarkvevi
            return;
        }

        if (!user.isAdmin()) {
            req.getRequestDispatcher("WEB-INF/notAdmin.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("WEB-INF/admin.jsp").forward(req, resp);
    }

}