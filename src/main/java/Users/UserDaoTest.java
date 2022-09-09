package Users;

import Quizs.DatabaseConnection;
import Quizs.TestHelper;
import junit.framework.TestCase;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;

public class UserDaoTest extends TestCase {

    public void test1() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "tagvi_400");

        HashSet<User> users = new HashSet<>();
        ud.getUsers(users);
        assertEquals(0, users.size());

        User user = ud.addUser("gela", "b914f46bc4061f533454fbc28a2697892f3ffdb36d904ecfd7940d8ad4e271c5");
        assertEquals("gela", user.getUsername());
        User newUser = new User(1, "badri", false);
        boolean flag = ud.updateUser(newUser, "8782804f99bee768bc47a6eb1dfba9f71a932030ec561c7aec5c1cfeed67e963");
        assertTrue(flag);
        assertEquals("badri", ud.getUser(1).getUsername());
        assertFalse(ud.getUser("badri").isAdmin());
        boolean flag_ = ud.updateUser(newUser);
        //assertFalse(flag_);
        assertFalse(ud.getUser(1).isAdmin());
        assertEquals("badri", ud.getUser(1).getUsername());
    }

    public void test2() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "tagvi_400");

        for(int i = 0; i < 5; i++){
            ud.addUser("a" + i, "" + i);
        }

        HashSet<User> users = new HashSet<>();
        ud.getUsers(users);
        assertEquals(5, users.size());

        User sender = new User(1, "kokakola", false);
        User receiver = new User(2, "pepsi", false);
        User receiver2 = new User(3, "zandukeli", false);
        User receiver3 = new User(4, "xashuris limonati", false);

        ud.addFriend(sender, receiver);
        ud.addFriend(sender, receiver2);
        ud.addFriend(receiver3, sender);
        ud.addFriend(receiver, receiver2);

        assertEquals(2, ud.getSentRequests(sender).size());
        assertEquals(2, ud.getReceivedRequests(receiver2).size());

        ud.acceptFriend(sender, receiver);
        ud.acceptFriend(sender, receiver2);
        ud.acceptFriend(receiver3, sender);

        assertEquals(3, ud.getFriends(sender).size());
    }

    public void test3() throws SQLException, ClassNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);
        UserDao ud = new UserDao("jdbc:mysql://localhost:3306/quiz", "root", "tagvi_400");

        ud.addUser("gulnazi", "3c216a4cbbfaba7367be060175c81359e722b588e7ca26ec35036af5bf13576e");
        ud.addUser("nazi", "8137e9e6a7b969a0144c6de82cf8cf5589c20857fb15e3b5eaa37eadeb0ba449");
        ud.addUser("balakanazi", "10");
        ud.banUser("gulnazi", new Date(2022, 10, 10));
        ud.addUser("tengiza", "ska");

        assertEquals(3, ud.searchByUsername("nazi").size());
        assertEquals(4, ud.searchByUsername("z").size());
        assertTrue(ud.tryLogin("nazi", "8137e9e6a7b969a0144c6de82cf8cf5589c20857fb15e3b5eaa37eadeb0ba449"));
        assertFalse(ud.tryLogin("tengiza", "skabadoni"));
    }
}
