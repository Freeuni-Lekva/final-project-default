package History;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IHistoryDao {
    ArrayList <History> getUsersFromHistory(int quiz_id) throws SQLException;
    ArrayList <History> getQuizzesFromHistory(int user_id) throws SQLException;
    ArrayList <History> getScore(int quiz_id, int user_id) throws SQLException;
    ArrayList <History> getAllHistory() throws SQLException;
    HistorySummary getHistorySummary(int quiz_id) throws SQLException;
    ArrayList <History> getRecentTestTakers(int quiz_id) throws SQLException;
    void addHistoryEntry(History history) throws SQLException;


}
