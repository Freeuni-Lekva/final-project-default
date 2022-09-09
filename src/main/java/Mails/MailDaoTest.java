package Mails;
import Quizs.DatabaseConnection;
import Quizs.TestHelper;
import Users.IUserDao;
import Users.UserDao;
import junit.framework.TestCase;

import java.sql.Date;
import java.sql.SQLException;

public class MailDaoTest extends TestCase{

    private IUserDao userdao;
    private IMailDao md;
    Date date;
    protected void setUp() throws Exception {
        super.setUp();
        DatabaseConnection dbcon=new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbcon);
        userdao=new UserDao(new DatabaseConnection().getConnection());
        userdao.addUser("1","123");
        userdao.addUser("2","223");
        userdao.addUser("3","323");

        md = new MailDao(new DatabaseConnection().getConnection());
        date=new Date(System.currentTimeMillis());
    }

    public void test1() throws SQLException {
        assertEquals(null,md.getMail(1));
        md.addMail(1,2,"NOTE","hello","hello",date);
        Note mail=new Note(0,1,2,"hello","hello",date);
        assertTrue(md.getMail(1).equals(mail));
        assertEquals("hello",md.getMail(1).getContent());
        assertEquals(0,md.getUsersIncomingMails(1).size());
        assertEquals(1,md.getUsersIncomingMails(2).size());
        assertEquals(mail,md.getUsersIncomingMails(2).get(0));
    }

    public void test2() throws SQLException {
        assertEquals(null,md.getMail(1));
        md.addMail(1,2,"NOTE","hello","hello",date);
        md.addMail(2,1,"FRT","FRT","addme",date);
        md.addMail(1,2,"NOTE","hello","hello",date);
        Note mail1=new Note(1,1,2,"hello","hello",date);
        FriendRequest mail2=new FriendRequest(2,2,1,date);
        assertTrue(md.getMail(1).equals(mail1));
        assertEquals("FRT",md.getMail(2).getType());
        assertEquals(2,md.getUsersOutgoingMails(1).size());
        assertEquals(1,md.getUsersOutgoingMails(2).size());
        assertEquals(2,md.getUsersIncomingMails(2).size());
    }

    public void testDeleteMail() throws SQLException {
        assertEquals(null,md.getMail(1));
        md.addMail(1,2,"NOTE","hello","hello",date);
        Note mail=new Note(0,1,2,"hello","hello",date);
        assertTrue(md.getMail(1).equals(mail));
        assertEquals(0,md.getUsersIncomingMails(1).size());
        assertEquals(1,md.getUsersIncomingMails(2).size());
        assertEquals(mail,md.getUsersIncomingMails(2).get(0));
        md.deleteMail(1);
        assertEquals(null,md.getMail(1));
        assertEquals(0,md.getUsersIncomingMails(1).size());
        assertEquals(0,md.getUsersIncomingMails(2).size());
    }

    public void test4() throws SQLException {
        md.addMail(1,2,"NOTE","hello","hello",date);
        md.addMail(2,1,"FRT","FRT","addme",date);
        md.addMail(3,2,"CH","hello","hello",date);
        Note mail1=new Note(1,1,2,"hello","hello",date);
        assertEquals("CH",md.getMail(3).getType());
        assertEquals("FRT",md.getMail(2).getType());
        assertEquals(1,md.getUsersOutgoingMails(3).size());
        md.deleteMail(3);
        assertEquals(0,md.getUsersOutgoingMails(3).size());
        assertEquals(null,md.getMail(3));
        assertEquals(null,md.getMail(123));


    }




}
