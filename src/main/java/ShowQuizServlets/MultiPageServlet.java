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


        ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");
        Integer curID = (Integer) request.getSession().getAttribute("CurrentQuestion");
        if (!(Boolean) request.getSession().getAttribute("feedback")) {
            String s = request.getParameter("question" + curID);
            request.getSession().setAttribute("question" + curID, s);
        }
        int timeLeft = (int) Integer.parseInt(request.getParameter("timeLeft"));
        request.getSession().setAttribute("timeLeft" , timeLeft);
        IQuizDao quizDao = (IQuizDao) request.getServletContext().getAttribute("QuizDao");
        int quiz_id = Integer.parseInt((String) request.getSession().getAttribute("quiz_id"));
        Quiz currentQuiz;
        try {
            currentQuiz = quizDao.getQuiz(quiz_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (currentQuiz.isImmediateCorrection() && !(Boolean) request.getSession().getAttribute("feedback")) {
            request.getRequestDispatcher("/ShowQuizJSPs/ImmediateFeedback.jsp").forward(request, response);
        } else {
            curID++;
            if (curID == questions.size() || timeLeft <= 2) {
                request.getRequestDispatcher("CheckAnswers").forward(request, response);
            } else {
                request.getSession().setAttribute("CurrentQuestion", curID);
                Question firstQuestion = questions.get(curID);
                if (currentQuiz.isImmediateCorrection()) request.getSession().setAttribute("feedback", false);
                request.getRequestDispatcher("/ShowQuizJSPs/Show" + firstQuestion.getType() + ".jsp").forward(request, response);
            }
        }
    }
}
