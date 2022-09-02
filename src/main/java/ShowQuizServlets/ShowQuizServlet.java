package ShowQuizServlets;

import Quizs.IQuestionDao;
import Quizs.IQuizDao;
import Quizs.Question;
import Quizs.Quiz;
import Users.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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

        Date st_time = new Date(System.currentTimeMillis());
        request.setAttribute("st_time",st_time);
        if (currentQuiz.isOnePage())
        {
            request.getRequestDispatcher("SinglePage.jsp").forward(request,response);
        }
        else
        {
            ArrayList<Question>curquests;
            try {
              curquests=quizDao.getQuestions(currentQuiz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (currentQuiz.isRandom())
                Collections.shuffle(curquests);
            request.setAttribute("QuestionsList",curquests);
            request.setAttribute("CurrentQuestion",0);
            Question firstQuestion = curquests.get(0);
            request.getRequestDispatcher("Show" + firstQuestion.getType() + ".jsp").forward(request,response);
        }
    }
}
