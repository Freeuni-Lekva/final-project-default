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
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("./Homepage.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("LoginJSPs/LoginDenied.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}