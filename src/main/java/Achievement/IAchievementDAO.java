package Achievement;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IAchievementDAO {

    boolean createNewAchievement(String name, String description) throws SQLException;
    List<Achievement> getAllAchievements() throws SQLException;
    List<AchievementHistory> getUserAchievements(int user_id) throws SQLException;
    boolean giveUserAchievement(int user_id, Achievement achievement, Date acquiredDate) throws SQLException;

}
