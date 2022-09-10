package ShowQuizServlets;

import History.HistoryService;
import Quizs.Answer;
import Quizs.IQuestionDao;
import Quizs.Question;

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

@WebServlet(name = "SinglePageCheckAnswersForwarder", value = "/SinglePageCheckAnswersForwarder")
public class SinglePageCheckAnswersForwarder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        ArrayList<Question> quests= (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");
        for (int i=0;i<quests.size();i++) {
            request.getSession().setAttribute("question" + i, request.getParameter("question" + i) );
        }
        System.out.println(request.getParameter("timeLeft"));
        request.getRequestDispatcher("CheckAnswers").forward(request,response);
    }
}
