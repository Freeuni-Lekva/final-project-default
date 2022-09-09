package Mails;

import Quizs.DatabaseConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailService {
    IMailDao md;

    public MailService() throws SQLException, ClassNotFoundException {
        md = new MailDao(new DatabaseConnection().getConnection());
    }


    public void addMail(int fromId, int toId, String type, String title, String content, Date date) throws SQLException {
         md.addMail(fromId,toId,type,title,content,date);
    }

    public void deleteMail(int id) throws SQLException {
        md.deleteMail(id);
    }

    public Mail getMail(int id) throws SQLException {
        return md.getMail(id);
    }

    public List<Mail> getUsersIncomingMails(int user_id) throws SQLException {
        return md.getUsersIncomingMails(user_id);
    }

    public List<Mail> getUsersOutgoingMails(int user_id) throws SQLException {
        return md.getUsersOutgoingMails(user_id);
    }

}
