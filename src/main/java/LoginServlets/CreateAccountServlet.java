package LoginServlets;

import Users.User;
import Users.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreateAccountServlet", value = "/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService us = (UserService) getServletContext().getAttribute("UserService");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = us.getUser(username);
            if(user != null){//account exists with implied username
                request.getSession().setAttribute("Message", "The Username " + username + " Is Already In Use");
                request.getRequestDispatcher("LoginJSPs/AccountCreationDenied.jsp").forward(request, response);
            }else{
                User user_ = us.addUser(username, password);
                if(user_ == null){
                    if(!us.usernameMatch(username)){
                        request.getSession().setAttribute("Message", "Username should be at least 3 characters and it should" +
                                " contain at least one a-z, A-Z, 0-9 characters. Username can also contain - and _");
                        request.getRequestDispatcher("LoginJSPs/AccountCreationDenied.jsp").forward(request, response);
                    }else if(!us.passwordMatch(password)){
                        request.getSession().setAttribute("Message", "Password should be between 8-20 character length and"
                        + " it should contain at least one a-z, A-Z, 0-9 characters");
                        request.getRequestDispatcher("LoginJSPs/AccountCreationDenied.jsp").forward(request, response);
                    }
                }else{
                    User useR = us.getUser(username);
                    request.getSession().setAttribute("user", useR);
                    request.getRequestDispatcher("./Homepage.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}