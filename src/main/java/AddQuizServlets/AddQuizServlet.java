package AddQuizServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import Achievement.Achievement;
import Achievement.AchievementService;
import History.HistoryService;
import Mails.MailService;
import Quizs.*;
import Users.User;


@WebServlet(name = "AddQuizServlet", value = "/AddQuizServlet")
public class AddQuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IQuizDao dao = (IQuizDao) request.getServletContext().getAttribute("QuizDao");
        String title = (String) request.getParameter("quizTitle");
        String description = (String) request.getParameter("quizDescription");
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        HistoryService hd = (HistoryService) request.getServletContext().getAttribute("HistoryService");
        AchievementService ac;
        MailService mailService;
        try {
            ac = new AchievementService();
            mailService = new MailService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int quizTime = Integer.parseInt(request.getParameter("quizTime"));
        boolean isRandom = request.getParameter("isRandom") != null;
        boolean isOnePage = request.getParameter("isOnePage") != null;
        boolean imediateCorrection = request.getParameter("imediateCorrection") != null;
        boolean canBePracticed = request.getParameter("canBePracticed") != null;
        //System.out.println(title + " " + description + 1  + " " +  quizTime  + " " +  isRandom + " " +  isOnePage  + " " +  imediateCorrection  + " " +  canBePracticed);
        try {
            Quiz addQuiz = dao.addQuiz(title , description , currentUser.getId() , quizTime , isRandom , isOnePage , imediateCorrection , canBePracticed);
            if(dao.getQuizzes(currentUser).size() == 1){
                Achievement achievement = ac.getAllAchievements().stream().filter(x -> x.getId() == 2).findFirst().get();
                ac.giveUserAchievement(currentUser.getId() , achievement , new Date());
            }
            request.getSession().setAttribute("NewQuiz" , addQuiz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("AddQuizJSPs/AddQuestions.jsp").forward(request , response);
    }
}
