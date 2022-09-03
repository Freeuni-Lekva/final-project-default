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

@WebServlet(name = "MultiPageServlet", value = "/MultiPageServlet")
public class MultiPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute("QuestionsList");
        Integer curID = (Integer)request.getAttribute("CurrentQuestion");
        IQuizDao quizDao = (IQuizDao) request.getServletContext().getAttribute("QuizDao");
        int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
        Quiz currentQuiz;
        try {
            currentQuiz = quizDao.getQuiz(quiz_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (currentQuiz.isImmediateCorrection())
        {
            if (!(Boolean) request.getAttribute("feedback"))
            {
                request.getRequestDispatcher("ImmediateFeedback.jsp").forward(request,response);
            }
        }

        if (curID+1 == questions.size())
        {
            request.getRequestDispatcher("CheckAnswers").forward(request,response);
        }
        curID++;
        request.setAttribute("CurrentQuestion",curID);
        Question firstQuestion = questions.get(curID);
        request.getRequestDispatcher("Show" + firstQuestion.getType() + ".jsp").forward(request,response);
    }
}
