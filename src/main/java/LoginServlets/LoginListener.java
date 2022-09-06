package LoginServlets;

import Quizs.QuizDao;
import Users.UserDao;
import Users.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebListener
public class LoginListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    //private AccountManager manager;
    private UserService us;
    private String message;
    private QuizDao qd;

    public LoginListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            this.us = new UserService();
            this.qd = new QuizDao();
            sce.getServletContext().setAttribute("UserService", us);
            sce.getServletContext().setAttribute("QuizDao", qd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        this.message = "";
        se.getSession().setAttribute("Message", message);
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