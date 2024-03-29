package ProfileServlets;


import Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "settingsServlet", value = "/settings")
public class SettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            // TODO: HE IS NOT LOGINED! REDIRECT TO LOGGINING PAGEs
        }
        req.getRequestDispatcher("WEB-INF/settings.jsp").forward(req, resp);
    }
}