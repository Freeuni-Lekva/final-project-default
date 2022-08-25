package History;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryService {
    private IHistoryDao dao;

    public HistoryService() throws SQLException {
        dao = new HistoryDao("jdbc:mysql://localhost:3306/quiz","root","alpine");
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
    public void addHistoryEntry(int user_id, int quiz_id, int score, Date start_date, Date end_date) throws SQLException {
        History history = new History(1,user_id,quiz_id,score,start_date,end_date);
        dao.addHistoryEntry(history);
    }
}
