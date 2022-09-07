package History;

import Quizs.Quiz;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IHistoryDao {
    ArrayList <History> getUsersFromHistory(int quiz_id) throws SQLException;
    ArrayList <History> getQuizzesFromHistory(int user_id) throws SQLException;
    ArrayList <History> getScore(int quiz_id, int user_id) throws SQLException;
    ArrayList <History> getAllHistory() throws SQLException;
    HistorySummary getHistorySummary(int quiz_id) throws SQLException;
    ArrayList <History> getRecentTestTakers(int quiz_id) throws SQLException;
    ArrayList<Quiz> getPopularQuizzes(int limit) throws SQLException, ClassNotFoundException;
    ArrayList<Quiz> getRecentQuizzesTakenBy(int userId, int limit) throws SQLException, ClassNotFoundException;
    void addHistoryEntry(History history) throws SQLException;


}
