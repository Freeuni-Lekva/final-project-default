package ShowQuizServlets;

import History.HistoryDao;
import History.IHistoryDao;
import Quizs.IQuestionDao;
import Quizs.IQuizDao;
import Quizs.QuestionDao;
import Quizs.QuizDao;
import Users.User;
import Users.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebListener
public class ShowQuizListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public ShowQuizListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        try {
            UserService s = new UserService();
            User u = s.getUser(1);
            sce.getServletContext().setAttribute("currentUser" , u);
            IQuizDao qDao = new QuizDao();
            sce.getServletContext().setAttribute("QuizDao" , qDao);
            IQuestionDao questionDao = new QuestionDao();
            sce.getServletContext().setAttribute("QuestionDao" , questionDao);
            IHistoryDao hDao = new HistoryDao("quiz","root","password");
            sce.getServletContext().setAttribute("HistoryDao",hDao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

        User user;
        try {
            UserService usv = new UserService();
            user = usv.getUser("temp"); // needs to be changed to current logged in user
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = se.getSession();
        session.setAttribute("currentUser",user);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
