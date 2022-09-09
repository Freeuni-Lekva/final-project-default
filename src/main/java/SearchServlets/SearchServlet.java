package SearchServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Quizs.*;
import Users.User;
import Users.UserService;


@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        String type = request.getParameter("searchType");
        if (type.equals("quiz")) {
            try {
                IQuizDao quizDao = new QuizDao();
                ArrayList<Quiz> quizzs = quizDao.getQuizzes(searchTerm);

                request.setAttribute("quizzes", quizzs);
                request.getRequestDispatcher("./SearchJSPs/QuizSearch.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                UserService userService = new UserService();
                List<User> users = userService.searchByUsername(searchTerm);
                request.setAttribute("users", users);
                request.getRequestDispatcher("./SearchJSPs/UserSearch.jsp").forward(request, response);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
