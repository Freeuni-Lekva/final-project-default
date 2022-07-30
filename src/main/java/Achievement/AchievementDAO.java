package Achievement;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAO implements IAchievementDAO {

    private Connection connection;

    public AchievementDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createNewAchievement(String name, String description) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO achievments (name, description) VALUES (?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, description);
        int change = stmt.executeUpdate();
        return change > 0;
    }

    @Override
    public List<Achievement> getAllAchievements() throws SQLException {
        List<Achievement> result = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM achievments");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(new Achievement(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return result;
    }

    @Override
    public List<AchievementHistory> getUserAchievements(int user_id) throws SQLException {
        List<AchievementHistory> result = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM achievment_history as a " +
                        "JOIN achievments as b ON a.achievment_Id = b.id " +
                        "WHERE a.user_Id = ?");
        stmt.setInt(1, user_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int historyId = rs.getInt(1);
            Date achievementDate = rs.getDate(4);
            int achievementId = rs.getInt(5);
            String name = rs.getString(6);
            String description = rs.getString(7);
            Achievement currAchievement = new Achievement(achievementId, name, description);
            AchievementHistory curr = new AchievementHistory(historyId, currAchievement, achievementDate);
            result.add(curr);
        }
        return result;
    }

    public boolean giveUserAchievement(int user_id, Achievement achievement, Date acquiredDate) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO achievment_history (user_Id, achievment_Id, acquired_date) " +
                            "VALUES (?, ?, ?)");
        stmt.setInt(1, user_id);
        stmt.setInt(2, achievement.getId());
        stmt.setDate(3, new java.sql.Date(acquiredDate.getTime()));
        int change = stmt.executeUpdate();
        return change > 0;
    }
}
