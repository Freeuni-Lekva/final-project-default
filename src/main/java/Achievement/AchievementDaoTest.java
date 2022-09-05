//package Achievement;
//
//import Users.User;
//import Users.UserDao;
//import org.junit.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class AchievementDaoTest {
//
//    private static Connection conn;
//
//    private String achievementsNames[] = {"Amateur Author", "Prolific Author", "Quiz Machine"};
//    private String achievementsDescriptions[] = {"Created a quiz!", "Created 5 quizzes!", "Took 10 quizzes"};
//
//    @BeforeClass
//    public static void initConnection() throws ClassNotFoundException, SQLException {
//        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testquiz", "root", "root");
//    }
//
//    private static void clearTables() throws SQLException {
//        PreparedStatement st1 = conn.prepareStatement("DELETE FROM achievment_history");
//        st1.executeUpdate();
//        PreparedStatement st2 = conn.prepareStatement("DELETE FROM users");
//        st2.executeUpdate();
//        PreparedStatement st3 = conn.prepareStatement("DELETE FROM achievments");
//        st3.executeUpdate();
//    }
//
//    @AfterClass
//    public static void closeConnection() throws SQLException {
//        conn.close();
//    }
//
//    @Test
//    public void AddingNewAchievementTest() throws SQLException {
//        clearTables();
//        AchievementDAO dao = new AchievementDAO(conn);
//        for (int i = 0; i < achievementsNames.length; ++i) {
//            boolean added = dao.createNewAchievement(achievementsNames[i], achievementsDescriptions[i]);
//            assertTrue(added);
//        }
//
//        // Test if they exist in database
//        List<Achievement> achs = dao.getAllAchievements();
//        assertEquals(achievementsNames.length, achs.size());
//        for (int i = 0; i < achs.size(); ++i) {
//            assertEquals(achievementsNames[i], achs.get(i).getName());
//            assertEquals(achievementsDescriptions[i], achs.get(i).getDescription());
//        }
//    }
//
//    @Test
//    public void GivingUserAchievementTest() throws SQLException {
//        clearTables();
//
//        // User for testing
//        UserDao userDao = new UserDao("jdbc:mysql://127.0.0.1:3306/testquiz", "root", "root");
//        User testuser = userDao.addUser("testuser", "random_password");
//
//        AchievementDAO dao = new AchievementDAO(conn);
//        // Add Achievements to dao
//        for (int i = 0; i < achievementsNames.length; ++i) {
//            dao.createNewAchievement(achievementsNames[i], achievementsDescriptions[i]);
//        }
//        // Get Achievements
//        List<Achievement> achs = dao.getAllAchievements();
//        // Give all achievements to user
//        for (Achievement ach : achs) {
//            boolean changed = dao.giveUserAchievement(testuser.getId(), ach, new Date());
//            assertTrue(changed);
//        }
//        // Check
//        List<AchievementHistory> histories = dao.getUserAchievements(testuser.getId());
//        assertEquals(achs.size(), histories.size());
//        for (int i = 0; i < histories.size(); ++i) {
//            assertEquals(achs.get(i).getName(), histories.get(i).getName());
//            assertEquals(achs.get(i).getDescription(), histories.get(i).getDescription());
//        }
//    }
//}
