package ShowQuizServlets;

import Quizs.IQuestionDao;
import Quizs.IQuizDao;
import Quizs.Quiz;
import Users.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ShowQuizServlet", value = "/ShowQuizServlet")
public class ShowQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IQuestionDao questionDao = (IQuestionDao) request.getServletContext().getAttribute("QuestionDao");
        IQuizDao quizDao = (IQuizDao) request.getServletContext().getAttribute("QuizDao");
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
        Quiz currentQuiz;
        try {
            currentQuiz = quizDao.getQuiz(quiz_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
}
