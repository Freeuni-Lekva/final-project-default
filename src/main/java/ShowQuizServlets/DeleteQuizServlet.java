package ShowQuizServlets;

import History.HistoryService;
import Quizs.*;
import Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "DeleteQuizServlet", value = "/DeleteQuizServlet")
public class DeleteQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute("QuizDao");
        int quizId = Integer.parseInt((String)request.getSession().getAttribute("quiz_id"));
        Quiz quiz = null;
        try {
            quiz = quizDao.getQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if(quiz.getCreatorId() != currentUser.getId() && !currentUser.isAdmin()){
            return;
        }

        try {
            quizDao.removeQuiz(quiz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("./Homepage/Homepage.jsp");

    }
}
