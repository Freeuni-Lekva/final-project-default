package History;

import Quizs.DatabaseConnection;
import Quizs.Quiz;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryService {
    private IHistoryDao dao;

    public HistoryService() throws SQLException, ClassNotFoundException {
        dao = new HistoryDao(new DatabaseConnection().getConnection());
    }
    public ArrayList<History> getUsersFromHistory(int quiz_id) throws SQLException {
        return dao.getUsersFromHistory(quiz_id);
    }
    public ArrayList <History> getQuizzesFromHistory(int user_id) throws SQLException {
        return dao.getQuizzesFromHistory(user_id);
    }
    public ArrayList <History> getScore(int quiz_id, int user_id) throws SQLException {
        return dao.getScore(quiz_id,user_id);
    }
    public HistorySummary getHistorySummary(int quiz_id) throws SQLException{
        return dao.getHistorySummary(quiz_id);
    }
    public ArrayList<History> getAllHistory() throws SQLException {
        return dao.getAllHistory();
    }
    public ArrayList <History> getRecentTestTakers(int quiz_id) throws SQLException{
        return dao.getRecentTestTakers(quiz_id);
    }
    public ArrayList<Quiz> getPopularQuizzes(int limit) throws SQLException, ClassNotFoundException{
        return dao.getPopularQuizzes(limit);
    }
    public ArrayList<Quiz> getRecentQuizzesTakenBy(int userId, int limit) throws SQLException, ClassNotFoundException{
        return dao.getRecentQuizzesTakenBy(userId,limit);
    }
    public void addHistoryEntry(int user_id, int quiz_id, int score, Date start_date, Date end_date) throws SQLException {
        History history = new History(1,user_id,quiz_id,score,start_date,end_date);
        dao.addHistoryEntry(history);
    }
}
