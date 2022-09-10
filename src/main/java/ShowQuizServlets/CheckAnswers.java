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
        int maxScore = 0;
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
                    if (curans.get(k).isCorrect()){
                        if (s != null && s.equals(curans.get(k).getDescription()))
                            score++;
                        maxScore++;
                    }
                }
            }else
            {
                if(s != null){
                    String[] answers = s.split(",");
                    Set<String> os = new HashSet<>();
                    for (int j = 0; j < answers.length; j++)
                        os.add(answers[j]);
                    for (int j = 0; j < curans.size(); j++)
                        if (curans.get(j).isCorrect()) {
                            if (os.contains(curans.get(j).getDescription())) score++;
                            maxScore++;
                        }
                } else {
                    for (int j = 0; j < curans.size(); j++)
                        if (curans.get(j).isCorrect()) {
                            maxScore++;
                        }
                }
            }
        }

        Integer quiz_id=Integer.parseInt((String) request.getSession().getAttribute("quiz_id"));

        String ispracticed =(String) request.getSession().getAttribute("IsPracticed");
        if(ispracticed==null || !ispracticed.equals("YES"))
        {
            HistoryService hs;
            try {
                hs=new HistoryService();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                hs.addHistoryEntry( ((User)request.getSession().getAttribute("currentUser")).getId() ,quiz_id,score,
                        (  java.sql.Date ) request.getSession().getAttribute("st_time"),end_time);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


        request.getSession().setAttribute("FinalScore",score);
        request.getSession().setAttribute("MaxScore",maxScore);
        request.getRequestDispatcher("/ShowQuizJSPs/ResultsPage.jsp").forward(request,response);
    }
}
