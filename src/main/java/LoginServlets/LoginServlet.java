package LoginServlets;

import Users.User;
import Users.UserDao;
import Users.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService us = (UserService) getServletContext().getAttribute("UserService");

        try {
            if(us.login(username, password)){

                User user = us.getUser(username);

                Date banExp = us.getBanExpiration(user);
                if(banExp != null && !banExp.before(new java.util.Date())) {
                    request.getRequestDispatcher("./LoginJSPs/LoginDenied.jsp").forward(request, response);
                    return;
                } else if(banExp != null && banExp.before(new java.util.Date())) {
                    us.unBanUser(username);
                }
                request.getSession().setAttribute("currentUser", user);
                response.sendRedirect("./Homepage/Homepage.jsp");
            }else{
                request.getRequestDispatcher("./LoginJSPs/LoginDenied.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}