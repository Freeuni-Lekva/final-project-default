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
    public void createNewAchievement(String name, String description) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO achievments (name, description) VALUES (?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.executeUpdate();
    }

    @Override
    public List<Achievement> getAllAchievements() throws SQLException {
        List<Achievement> result = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM achievments");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(new Achievement(rs.getString(2), rs.getString(3)));
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
            String name = rs.getString(6);
            String description = rs.getString(7);
            Achievement currAchievement = new Achievement(name, description);
            Date achievementDate = rs.getDate(4);
            AchievementHistory curr = new AchievementHistory(currAchievement, achievementDate);
            result.add(curr);
        }
        return result;
    }

    private int getIdByAchievement(String achievementName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM achievments a WHERE a.name = ?");
        stmt.setString(1, achievementName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void giveUserAchievement(int user_id, Achievement achievement, Date acquiredDate) throws SQLException {
        int achievementId = getIdByAchievement(achievement.getName());
        System.out.println(achievementId);
        PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO achievment_history (user_Id, achievment_Id, acquired_date) " +
                            "VALUES (?, ?, ?)");
        stmt.setInt(1, user_id);
        stmt.setInt(2, achievementId);
        stmt.setDate(3, new java.sql.Date(acquiredDate.getTime()));
        stmt.executeUpdate();
    }
}
