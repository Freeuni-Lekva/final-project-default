package Quizs;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IQuestionDao {
    ArrayList<Question> getQuestions() throws SQLException;
    ArrayList<Question> getQuestions(Quiz quiz) throws SQLException;
    ArrayList<Question> getQuestions(int quizId) throws SQLException;
    Question getQuestion(int id) throws SQLException;
    Question addQuestion(int quizId, String type, String description) throws SQLException;
    boolean removeQuestion(Question question) throws SQLException;
    boolean removeQuestion(int questionId) throws SQLException;
    boolean setQuestionDescription(Question question, String description) throws SQLException;
    boolean setQuestionDescription(int questionId, String description) throws SQLException;
    boolean updateQuestion(Question question) throws SQLException;
    ArrayList<Answer> getAnswers(Question question) throws SQLException;
    ArrayList<Answer> getAnswers(int questionId) throws SQLException;
    Answer addAnswer(Question question, String description, boolean isCorrect) throws SQLException;
    Answer addAnswer(int questionId, String description, boolean isCorrect) throws SQLException;
    boolean removeAnswer(Answer answer) throws SQLException;
    boolean removeAnswer(int answerId) throws SQLException;
    boolean removeQuestionAnswers(Question question) throws SQLException;
    boolean removeQuestionAnswers(int questionId) throws SQLException;
    Answer getAnswer(int answerId) throws SQLException;
    boolean setAnswerDescription(Answer answer, String description) throws SQLException;
    boolean setAnswerDescription(int answerId, String description) throws SQLException;
    boolean setAnswerCorrectness(Answer answer, boolean isCorrect) throws SQLException;
    boolean setAnswerCorrectness(int answerId, boolean isCorrect) throws SQLException;
    boolean updateAnswer(Answer answer) throws SQLException;
}
