import Users.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IQuizDao {
    ArrayList<Quiz> getQuizzes() throws SQLException;
    ArrayList<Quiz> getQuizzes(User user) throws SQLException;
    ArrayList<Quiz> getQuizzes(int userId) throws SQLException;
    ArrayList<Quiz> getRecentQuizzes(int limit) throws SQLException;
    ArrayList<Quiz> getRecentQuizzes(User user, int limit) throws SQLException;
    ArrayList<Quiz> getRecentQuizzes(int userId, int limit) throws SQLException;
    Quiz getQuiz(int id) throws SQLException;
    Quiz addQuiz(String title, String description, int creatorId, int quizTime,boolean isRandom,boolean isOnePage,boolean immediateCorrection,boolean canBePracticed) throws SQLException;
    boolean removeQuiz(Quiz quiz) throws SQLException;
    boolean setQuizTitle(Quiz quiz, String title) throws SQLException;
    boolean setQuizTitle(int quizId, String title) throws SQLException;
    boolean setQuizDescription(Quiz quiz, String description) throws SQLException;
    boolean setQuizDescription(int quizId, String description) throws SQLException;
    boolean setQuizTime(Quiz quiz, int quizTime) throws SQLException;
    boolean setQuizTime(int quizId, int quizTime) throws SQLException;
    boolean setQuizRandomness(Quiz quiz, boolean isRandom) throws SQLException;
    boolean setQuizRandomness(int quizId, boolean isRandom) throws SQLException;
    boolean setQuizToOnePage(Quiz quiz, boolean isOnePage) throws SQLException;
    boolean setQuizToOnePage(int quizId, boolean isOnePage) throws SQLException;
    boolean setQuizImmediateCorrection(Quiz quiz, boolean isImmediateCorrection) throws SQLException;
    boolean setQuizImmediateCorrection(int quizId, boolean isImmediateCorrection) throws SQLException;
    boolean setQuizCanBePracticed(Quiz quiz, boolean canBePracticed) throws SQLException;
    boolean setQuizCanBePracticed(int quizId, boolean canBePracticed) throws SQLException;
    boolean updateQuiz(Quiz quiz) throws SQLException;
    ArrayList<Question> getQuestions(Quiz quiz) throws SQLException;
    ArrayList<Question> getQuestions(int quizId) throws SQLException;
    boolean removeQuizQuestions(Quiz quiz) throws SQLException;
    boolean removeQuizQuestions(int quizId) throws SQLException;
}
