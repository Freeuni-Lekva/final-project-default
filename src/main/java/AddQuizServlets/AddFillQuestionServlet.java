package AddQuizServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import Quizs.*;

@WebServlet(name = "AddFillQuestionServlet", value = "/AddFillQuestionServlet")
public class AddFillQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IQuestionDao questionDao = (IQuestionDao) request.getServletContext().getAttribute("QuestionDao");
        String question = request.getParameter("questionDescription");
        Quiz newQuiz = (Quiz) request.getSession().getAttribute("NewQuiz");
        String answersText = request.getParameter("answer");
        String[] answers = answersText.split(",");
        List<String> trimmedAnswers = Arrays.stream(answers).map(String::trim).collect(Collectors.toList());
        try {
            Question newQuest = questionDao.addQuestion(newQuiz.getId(), "FILL_QUESTION" , question);
            trimmedAnswers.forEach(x -> {
                try {
                    questionDao.addAnswer(newQuest , x , true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/AddQuizJSPs/AddQuestions.jsp").forward(request, response);
    }
}
