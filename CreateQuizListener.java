
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;

@WebListener
public class CreateQuizListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public CreateQuizListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            IQuizDao qDao = new QuizDao();
            sce.getServletContext().setAttribute("QuizDao" , qDao);
            IQuestionDao questionDao = new QuestionDao();
            sce.getServletContext().setAttribute("QuestionDao" , questionDao);
        } catch (SQLException e) {
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
        /* Session is created. */
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
