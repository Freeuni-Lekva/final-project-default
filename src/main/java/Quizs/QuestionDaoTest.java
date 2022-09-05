package Quizs;

import Users.UserDao;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.sql.Statement;

public class QuestionDaoTest extends TestCase {

    public void test1() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuestionDao qdd = new QuestionDao();
        assertTrue(qdd.getQuestions().isEmpty());

        QuizDao qd = new QuizDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("walter", "skyler");
        qd.addQuiz("jesse", "pinkman", 1, 200 , false ,false ,false ,false);
        qdd.addQuestion(1, "FILL_QUESTION", "description");
        assertTrue(qdd.getQuestion(1).getDescription().equals("description") && qdd.getQuestion(1).getType().
                equals("FILL_QUESTION") && qdd.getQuestion(1).getQuizId() == 1);
        assertEquals(1, qdd.getQuestions().size());
        assertTrue(qdd.getAnswers(1).isEmpty());
    }

    public void test2() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");

        ud.addUser("user", "password");
        qd.addQuiz("title", "description", 1, 100 , false , false , false ,false);
        qdd.addQuestion(1, "QUESTION_RESPONSE", "desc");
        qdd.addQuestion(1, "MULTIPLE_CHOICE", "desc");
        qdd.addQuestion(1, "PICTURE_RESPONSE", "desc");
        qdd.addQuestion(1, "FILL_QUESTION", "desc");
        assertTrue(qdd.getAnswers(1).isEmpty());

        assertEquals(4, qdd.getQuestions().size());
        for(int i = 0; i < 4; i++){
            qdd.removeQuestion(qdd.getQuestion(i + 1));
            assertEquals(4 - i - 1, qdd.getQuestions().size());
        }

        //removing non-existent question
        assertFalse(qdd.removeQuestion(new QuestionResponse(7, 1, "desc")));
    }

    public void test3() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");

        ud.addUser("user", "password");
        qd.addQuiz("title", "description", 1, 100, false ,false ,false , false);
        qdd.addQuestion(1, "QUESTION_RESPONSE", "desc");
        assertEquals("desc", qdd.getQuestion(1).getDescription());
        qdd.setQuestionDescription(qdd.getQuestion(1), "description");
        assertEquals("description", qdd.getQuestion(1).getDescription());
        qdd.updateQuestion(new FillQuestion(1, 1, "loti iraklai"));
        assertTrue(qdd.getQuestion(1).getDescription().equals("loti iraklai") && qdd.getQuestion(1).getType().
                equals("FILL_QUESTION"));
        assertTrue(qdd.getAnswers(1).isEmpty());
    }

    public void test4() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("user", "password");
        qd.addQuiz("title", "description", 1, 100, false ,false ,false , false);
        qd.addQuiz("TITLE", "DESCRIPTION", 1, 100, false ,false ,false , false);
        qd.addQuiz("TITle", "DESCription", 1, 100, false ,false ,false , false);

        int count = 1;
        for(int i = 0; i < 15; i++){
            if(i % 3 == 0 && i != 0) count++;
            qdd.addQuestion((i % 3) + 1, "QUESTION_RESPONSE", "desc");
            assertEquals(count, qdd.getQuestions(qd.getQuiz((i % 3) + 1)).size());
        }

        for(int i = 0; i < 15; i++){
            qdd.addAnswer(qdd.getQuestion(1), "desc", false);
            assertEquals(i + 1, qdd.getAnswers(qdd.getQuestion(1)).size());
        }

        for(int i = 0; i < 15; i++){
            qdd.removeAnswer(qdd.getAnswer(i + 1));
            assertEquals(15 - i - 1, qdd.getAnswers(qdd.getQuestion(1)).size());
        }

        //removing non-existent answer
        assertFalse(qdd.removeAnswer(new Answer(5, "desc", 1, false)));
    }

    public void test5() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("user", "password");
        qd.addQuiz("title", "description", 1, 100 , false ,false ,false , false);
        qdd.addQuestion(1, "QUESTION_RESPONSE", "desc");

        for(int i = 0; i < 15; i++){
            qdd.addAnswer(qdd.getQuestion(1), "desc", false);
            assertEquals(i + 1, qdd.getAnswers(qdd.getQuestion(1)).size());
        }

        qdd.removeQuestionAnswers(qdd.getQuestion(1));
        assertTrue(qdd.getAnswers(qdd.getQuestion(1)).isEmpty());
    }

    public void test6() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);

        QuizDao qd = new QuizDao();
        QuestionDao qdd = new QuestionDao();
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "");
        ud.addUser("user", "password");
        qd.addQuiz("title", "description", 1, 100, false ,false ,false , false);
        qdd.addQuestion(1, "QUESTION_RESPONSE", "desc");

        //testing setter methods
        qdd.addAnswer(qdd.getQuestion(1), "desc", false);
        qdd.setAnswerCorrectness(qdd.getAnswer(1), true);
        assertTrue(qdd.getAnswer(1).isCorrect());
        qdd.setAnswerDescription(qdd.getAnswer(1), "DESC");
        assertEquals("DESC", qdd.getAnswer(1).getDescription());
        qdd.updateAnswer(new Answer(1, "desc", 1, false));
        assertTrue(qdd.getAnswer(1).getDescription().equals("desc") && !qdd.getAnswer(1).isCorrect());
    }
}
