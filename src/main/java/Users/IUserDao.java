package Users;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public interface IUserDao {

    void getUsers(HashSet<User> resultSet) throws SQLException;
    User addUser(String userName , String password) throws SQLException;
    boolean updateUser(User user , String newPasswordHash) throws SQLException;
    boolean updateUser(User user) throws SQLException;
    User getUser(String userName) throws SQLException;
    User getUser(int id) throws SQLException;
    boolean addFriend(User sender , User receiver) throws SQLException;
    boolean acceptFriend(User sender , User receiver) throws SQLException;
    boolean tryLogin(String username , String password) throws SQLException;
    List<User> searchByUsername(String username) throws SQLException;
    List<User> getFriends(User user) throws SQLException;
    List<User> getSentRequests(User user) throws SQLException;
    List<User> getReceivedRequests(User user) throws SQLException;
    boolean banUser(String username, Date ban_expiration) throws SQLException;
    Date getBanExpiration(String username) throws SQLException;
    boolean unBanUser(String username) throws SQLException;
}
