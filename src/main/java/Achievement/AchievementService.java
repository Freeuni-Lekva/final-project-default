package Achievement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AchievementService {

    AchievementDAO dao;

    public AchievementService() {
        // Somehow get connection
    }

    public void createNewAchievement(String name, String description) throws SQLException {
        dao.createNewAchievement(name, description);
    }

    public List<Achievement> getAllAchievements() throws SQLException {
        return dao.getAllAchievements();
    }

    public List<AchievementHistory> getUserAchievements(int user_id) throws SQLException {
        return dao.getUserAchievements(user_id);
    }

    public void giveUserAchievement(int user_id, Achievement achievement, Date acquiredDate) throws SQLException {
        dao.giveUserAchievement(user_id, achievement, acquiredDate);
    }
}
