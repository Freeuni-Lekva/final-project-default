package AddQuizServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import Quizs.*;


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
        int quizTime = Integer.parseInt(request.getParameter("quizTime"));
        boolean isRandom = request.getParameter("isRandom") != null;
        boolean isOnePage = request.getParameter("isOnePage") != null;
        boolean imediateCorrection = request.getParameter("imediateCorrection") != null;
        boolean canBePracticed = request.getParameter("canBePracticed") != null;
        //System.out.println(title + " " + description + 1  + " " +  quizTime  + " " +  isRandom + " " +  isOnePage  + " " +  imediateCorrection  + " " +  canBePracticed);
        try {
             dao.addQuiz(title , description , 1 , quizTime , isRandom , isOnePage , imediateCorrection , canBePracticed);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("AddQuizJSPs/AddQuestions.jsp").forward(request , response);
    }
}
