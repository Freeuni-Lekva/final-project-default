package Mails;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        MailService ms=new MailService();
       // ms.addMail(1,2,"NOTE","a","b",null);
       // System.out.println(ms.getMail(1).getTitle());
       for (Mail cur : ms.getUsersOutgoingMails(2))
        System.out.println(cur.getType());


    }

}
