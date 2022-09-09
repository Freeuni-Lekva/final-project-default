package Mails;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailDao implements  IMailDao{

    private Connection connection;



    public MailDao(Connection conn) throws SQLException {
        connection = conn;
    }


    public void addMail(int fromId, int toId, String type, String title, String content, Date date) throws SQLException {
       // connection.createStatement().executeUpdate("USE quiz");
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mails (from_Id, to_Id, message_type, message_title, message_content, send_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, fromId);
        statement.setInt(2, toId);
        statement.setString(3, type);
        statement.setString(4, title);
        statement.setString(5, content);
        statement.setDate(6, date);
        statement.executeUpdate();
    }

    public void deleteMail(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM mails WHERE id = ?");
        statement.setInt(1, id);
        statement.execute();
    }




    private Mail mailParser(ResultSet res) throws SQLException {
       // connection.createStatement().executeUpdate("USE quiz");
        int id = res.getInt(1);
        int fromId = res.getInt(2);
        int toId = res.getInt(3);
        String type = res.getString(4);
        if (type.equals("NOTE")) {
            return new Note(id, fromId, toId, res.getString(5), res.getString(6), res.getDate(7));
        } else if (type.equals("FRT")) {
            return new FriendRequest(id, fromId, toId, res.getDate(7));
        } else if (type.equals("CH")) {
            return new Challenge(id, fromId, toId, res.getString(5), res.getString(6),res.getDate(7));
        }
        return null;
    }

    public Mail getMail(int id) throws SQLException {
     //   connection.createStatement().executeUpdate("USE quiz");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM mails WHERE id = ?");
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        Mail resultMail = null;
        if (res.next()) {
            resultMail = mailParser(res);
        }
        return resultMail;
    }

    public List<Mail> getUsersIncomingMails(int user_id) throws SQLException {
     //   connection.createStatement().executeUpdate("USE quiz");
        List<Mail> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM mails WHERE to_Id = ?");
        statement.setInt(1, user_id);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            result.add(mailParser(res));
        }
        return result;
    }

    public List<Mail> getUsersRecentIncomingMails(int userId) throws SQLException {
        List<Mail> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM mails WHERE to_Id = ? ORDER BY send_date DESC;");
        statement.setInt(1, userId);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            result.add(mailParser(res));
        }
        return result;
    }

    public List<Mail> getUsersOutgoingMails(int user_id) throws SQLException {
      //  connection.createStatement().executeUpdate("USE quiz");
        List<Mail> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM mails WHERE from_Id = ?");
        statement.setInt(1, user_id);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            result.add(mailParser(res));
        }
        return result;
    }
}
