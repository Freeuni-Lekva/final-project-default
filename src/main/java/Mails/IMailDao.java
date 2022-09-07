package Mails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface IMailDao {

     void addMail(int fromId, int toId, String type, String title, String content, Date date) throws SQLException;
     void deleteMail(int id) throws SQLException;
     Mail getMail(int id) throws SQLException ;
     List<Mail> getUsersIncomingMails(int user_id) throws SQLException;
     List<Mail> getUsersRecentIncomingMails(int userId) throws SQLException;
     List<Mail> getUsersOutgoingMails(int user_id) throws SQLException;
}
