import java.sql.SQLException;
import java.util.ArrayList;

public interface IQuizDao {
    ArrayList<Quiz> getQuizzes() throws SQLException;
    Quiz getQuiz(int id) throws SQLException;
    void addQuiz(String title, String description, int creatorId, int quizTime) throws SQLException;
    boolean removeQuiz(Quiz quiz) throws SQLException;
    boolean setQuizTitle(Quiz quiz, String title) throws SQLException;
    boolean setQuizTitle(int quizId, String title) throws SQLException;
    boolean setQuizDescription(Quiz quiz, String description) throws SQLException;
    boolean setQuizDescription(int quizId, String description) throws SQLException;
    boolean setQuizTime(Quiz quiz, int quizTime) throws SQLException;
    boolean setQuizTime(int quizId, int quizTime) throws SQLException;
    boolean updateQuiz(Quiz quiz) throws SQLException;
    ArrayList<Question> getQuestions(Quiz quiz) throws SQLException;
    ArrayList<Question> getQuestions(int quizId) throws SQLException;
    boolean removeQuizQuestions(Quiz quiz) throws SQLException;
    boolean removeQuizQuestions(int quizId) throws SQLException;
}
