import Users.UserDao;
import junit.framework.TestCase;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizDaoTest extends TestCase {

    public void test1() throws SQLException, ClassNotFoundException {
        //Tests for simple cases
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        //Testing on empty quizzes table
        QuizDao qd = new QuizDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("pingvina", "salam");
        assertTrue(qd.getQuizzes().isEmpty());

        qd.addQuiz("Cxeli dzagli", "blabla", 1, 400);
        assertEquals(1, qd.getQuizzes().size());
        assertTrue(qd.getQuiz(1).getTitle().equals("Cxeli dzagli") && qd.getQuiz(1).getDescription().
                equals("blabla") && qd.getQuiz(1).getQuizTime() == 400 && qd.getQuiz(1).getCreatorId() == 1);
    }

    public void test2() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("xuso", "2");
        ud.addUser("koshka", "12");
        ud.addUser("hasan", "13");

        int count = 1;
        for(int i = 0; i < 15; i++){
            if(i % 3 == 0 && i != 0) count++;
            char title = (char)('a' + i);
            qd.addQuiz("" + title, "", (i % 3) + 1, 250);
            assertEquals(i + 1, qd.getQuizzes().size());
            assertTrue(qd.getQuiz(i + 1).getTitle().equals("" + title) && qd.getQuiz(i + 1).getDescription().
                    equals("") && qd.getQuiz(i + 1).getQuizTime() == 250 && qd.getQuiz(i + 1).getCreatorId() == (i % 3) + 1);
            qdd.addQuestion(i + 1, "FILL_QUESTION", "sadaa kaci patroni a?");
            qdd.addAnswer(i + 1, "me ravi", false);
            assertEquals(count, qd.getQuizzes(ud.getUser((i % 3) + 1)).size());
        }

        //Testing getRecentQuizzes
        ArrayList<Quiz> recentQuizzes = qd.getRecentQuizzes(10);
        for(int i = 0; i < recentQuizzes.size(); i++){
            Quiz quiz = recentQuizzes.get(i);
            assertEquals(15 - i, quiz.getId());
        }

        //Testing getRecentQuizzes on specific user
        ArrayList<Quiz> recentUserQuizzes = qd.getRecentQuizzes(ud.getUser(2), 5);
        int firstId = 14;
        for(int i = 0; i < recentUserQuizzes.size(); i++){
            Quiz quiz = recentUserQuizzes.get(i);
            assertEquals(firstId, quiz.getId());
            firstId -= 3;
        }

        //removeQuiz test
        for(int i = 0; i < 15; i++){
            qd.removeQuiz(qd.getQuiz(i + 1));
            assertEquals(15 - i - 1, qd.getQuizzes().size());
        }

        //testing removeQuiz on non-existent quiz
        assertFalse(qd.removeQuiz(new Quiz(12, "", "", 2, 330)));
    }

    public void test3() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("kalduna", "");
        ud.addUser("sklinta", "");
        qd.addQuiz("vano", "gejadze", 1, 200);
        qd.addQuiz("guram", "jinoria", 2, 111);

        assertTrue(qd.getQuiz(1).getTitle().equals("vano") && qd.getQuiz(1).getDescription().equals("gejadze") &&
                qd.getQuiz(1).getCreatorId() == 1 && qd.getQuiz(1).getQuizTime() == 200);
        assertTrue(qd.getQuiz(2).getTitle().equals("guram") && qd.getQuiz(2).getDescription().equals("jinoria") &&
                qd.getQuiz(2).getCreatorId() == 2 && qd.getQuiz(2).getQuizTime() == 111);

        //testing setter methods
        qd.setQuizTitle(qd.getQuiz(1), "vaniko");
        assertTrue(qd.getQuiz(1).getTitle().equals("vaniko") && qd.getQuiz(1).getDescription().equals("gejadze") &&
                qd.getQuiz(1).getCreatorId() == 1 && qd.getQuiz(1).getQuizTime() == 200);
        qd.setQuizDescription(qd.getQuiz(2), "jinoriaaa");
        assertTrue(qd.getQuiz(2).getTitle().equals("guram") && qd.getQuiz(2).getDescription().equals("jinoriaaa") &&
                qd.getQuiz(2).getCreatorId() == 2 && qd.getQuiz(2).getQuizTime() == 111);
        qd.setQuizTime(qd.getQuiz(1), 111);
        qd.setQuizTime(qd.getQuiz(2), 200);
        assertTrue(qd.getQuiz(1).getTitle().equals("vaniko") && qd.getQuiz(1).getDescription().equals("gejadze") &&
                qd.getQuiz(1).getCreatorId() == 1 && qd.getQuiz(1).getQuizTime() == 111);
        assertTrue(qd.getQuiz(2).getTitle().equals("guram") && qd.getQuiz(2).getDescription().equals("jinoriaaa") &&
                qd.getQuiz(2).getCreatorId() == 2 && qd.getQuiz(2).getQuizTime() == 200);
        qd.updateQuiz(new Quiz(1, "vano", "gejadze", 1, 200));
        qd.updateQuiz(new Quiz(2, "guram", "jinoria", 2, 111));
        assertTrue(qd.getQuiz(1).getTitle().equals("vano") && qd.getQuiz(1).getDescription().equals("gejadze") &&
                qd.getQuiz(1).getCreatorId() == 1 && qd.getQuiz(1).getQuizTime() == 200);
        assertTrue(qd.getQuiz(2).getTitle().equals("guram") && qd.getQuiz(2).getDescription().equals("jinoria") &&
                qd.getQuiz(2).getCreatorId() == 2 && qd.getQuiz(2).getQuizTime() == 111);
    }

    public void test4() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");

        ud.addUser("zaxarichi", "amas ramdeniaq gavlili");
        qd.addQuiz("title", "description", 1, 200);
        for(int i = 0; i < 15; i++){
            qdd.addQuestion(1, "FILL_QUESTION", "desc");
            assertEquals(i + 1, qd.getQuestions(qd.getQuiz(1)).size());
        }
    }
}