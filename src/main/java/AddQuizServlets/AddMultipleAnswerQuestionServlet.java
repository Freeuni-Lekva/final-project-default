package AddQuizServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import Quizs.*;


@WebServlet(name = "AddMultipleAnswerQuestionServlet", value = "/AddMultipleAnswerQuestionServlet")
public class AddMultipleAnswerQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IQuestionDao questionDao = (IQuestionDao) request.getServletContext().getAttribute("QuestionDao");
        String question = request.getParameter("questionDescription");
        Quiz newQuiz = (Quiz) request.getSession().getAttribute("NewQuiz");

        try {
            Question newQuest = questionDao.addQuestion( newQuiz.getId(), "MULTIPLE_CHOICE" , question);
            for(int index = 1 ; index < 5 ; index++){
                String answer = request.getParameter("answer" + index);
                boolean correct = request.getParameter("correctAnswer").equals("" + index);
                questionDao.addAnswer(newQuest , answer , correct);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/AddQuizJSPs/AddQuestions.jsp").forward(request, response);
    }
}
