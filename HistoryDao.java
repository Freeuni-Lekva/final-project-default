package History;

import java.sql.*;
import java.util.ArrayList;

public class HistoryDao implements IHistoryDao{
    private Connection connection;

    public HistoryDao(String base, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(base,user,password);
    }

    @Override
    public ArrayList <History> getUsersFromHistory(int quiz_id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from history where quiz_id = " + quiz_id + " order by score desc , (end_time-start_time) asc limit 50");
        ArrayList <History> arr = getHistoryObjects(resultSet);
        statement.close();
        return arr;
    }

    @Override
    public ArrayList <History> getQuizzesFromHistory(int user_id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from history where user_id = " + user_id + " order by score desc , (end_time-start_time) asc limit 50");
        ArrayList <History> arr = getHistoryObjects(resultSet);
        statement.close();
        return arr;
    }

    @Override
    public ArrayList <History> getScore(int quiz_id, int user_id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from history where user_id = " + user_id +
                " and quiz_id = " + quiz_id + " order by score desc , (end_time-start_time) asc limit 50");
        ArrayList <History> arr = getHistoryObjects(resultSet);
        statement.close();
        return arr;
    }

    @Override
    public ArrayList<History> getAllHistory() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from history order by score desc , (end_time-start_time) asc limit 50"); // limit 50
        ArrayList <History> arr = getHistoryObjects(resultSet);
        statement.close();
        return arr;
    }

    public HistorySummary getHistorySummary(int quiz_id) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select avg(score), avg(timestampdiff(minute,start_time,end_time)) from history");
        statement.close();
        return new HistorySummary(resultSet.getInt(1), resultSet.getDouble(2));
    }
    public ArrayList <History> getRecentTestTakers(int quiz_id) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from history where quiz_id = " + quiz_id + " order by end_time desc limit 5");
        ArrayList <History> arr = getHistoryObjects(resultSet);
        statement.close();
        return arr;
    }
    public void addHistoryEntry(History history) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into history(user_id,quiz_id,score,start_time,end_time) values (?,?,?,?,?)");
        preparedStatement.setInt(1,history.getUser_id());
        preparedStatement.setInt(2,history.getQuiz_id());
        preparedStatement.setInt(3,history.getScore());
        preparedStatement.setDate(4,history.getStart_date());
        preparedStatement.setDate(5,history.getEnd_date());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private ArrayList <History> getHistoryObjects(ResultSet resultSet) throws SQLException {
        ArrayList <History> arr = new ArrayList<History>();
        while (resultSet.next())
        {
            arr.add(new History(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),
                    resultSet.getInt(4),resultSet.getDate(5),resultSet.getDate(6)));
        }
        return arr;
    }
}
