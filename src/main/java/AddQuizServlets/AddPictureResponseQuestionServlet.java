package AddQuizServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import Quizs.*;


@WebServlet(name = "AddPictureResponseQuestionServlet", value = "/AddPictureResponseQuestionServlet")
public class AddPictureResponseQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IQuestionDao questionDao = (IQuestionDao) request.getServletContext().getAttribute("QuestionDao");
        String question = request.getParameter("questionDescription");
        question = question + " " + request.getParameter("imgQuestion");
        try {
            questionDao.addQuestion(4 , "PICTURE_RESPONSE" , "tempo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Question newQuest = questionDao.addQuestion(9 , "PICTURE_RESPONSE" , question);
            String answer = request.getParameter("answer");
            questionDao.addAnswer(newQuest , answer , true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/AddQuizJSPs/AddQuestions.jsp").forward(request, response);
    }
}
