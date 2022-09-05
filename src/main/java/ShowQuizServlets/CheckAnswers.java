package ShowQuizServlets;

import History.HistoryService;
import Quizs.*;
import Users.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import java.util.stream.Collectors;

@WebServlet(name = "CheckAnswers", value = "/CheckAnswers")
public class CheckAnswers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        java.sql.Date end_time = new java.sql.Date(System.currentTimeMillis());
        request.getSession().setAttribute("end_time",end_time);
        Integer score=0;
        ArrayList<Question> quests= (ArrayList<Question>) request.getSession().getAttribute("QuestionsList");

        IQuestionDao  questdao= (IQuestionDao) request.getServletContext().getAttribute("QuestionDao");

        for (int i=0;i<quests.size();i++)
        {
            String s= (String) request.getSession().getAttribute("question"+i);
            ArrayList<Answer>curans ;
            try {
               curans=questdao.getAnswers(quests.get(i));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (quests.get(i).getType().equals("MULTIPLE_CHOICE"))
            {
                for (int k=0;k<curans.size();k++)
                {
                    if (curans.get(k).isCorrect())
                        if (s.equals(curans.get(k).getDescription()))
                            score++;
                }
            }else
            {
                String[] answers = s.split(",");
                Set<String>os = new HashSet<>();
                for (int j=0;j<answers.length;j++)
                    os.add(answers[j]);
                for (int j=0;j<curans.size();j++)
                    if (curans.get(j).isCorrect() && os.contains(curans.get(j).getDescription()))
                        score++;
            }
        }

        Integer quiz_id=Integer.parseInt((String) request.getSession().getAttribute("quiz_id"));

        String ispracticed =request.getParameter("IsPracticed");
        if(ispracticed==null || ispracticed.equals("NO"))
        {
            HistoryService hs;
            try {
                hs=new HistoryService();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                hs.addHistoryEntry( 1 ,quiz_id,score,
                        (  java.sql.Date ) request.getSession().getAttribute("st_time"),end_time);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


        request.setAttribute("FinalScore",score);
        request.getRequestDispatcher("/ShowQuizJSPs/ResultsPage.jsp").forward(request,response);
    }
}
