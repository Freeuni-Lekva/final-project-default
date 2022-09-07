package Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserDao implements IUserDao {
    Connection conn;

    public UserDao(String base , String user , String password) throws SQLException {
        conn = DriverManager.getConnection(base , user , password);
    }


    @Override
    public void getUsers(HashSet<User> resultSet) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("select * from users");
        while(res.next()){
            User newUser = new User(res.getInt(1) , res.getString(2) , res.getBoolean(4));
            resultSet.add(newUser);
        }
        stm.close();
    }

    @Override
    public User addUser(String userName , String password_hash) throws SQLException {
        Statement stm = conn.createStatement();
        String query = "insert into users(username , password_hash , is_admin) values ('"+ userName + "','" + password_hash + "', false)";
        stm.execute(query);
        return getUser(userName);
    }

    @Override
    public boolean updateUser(User user , String newpassword_hash) throws SQLException {
        Statement stm = conn.createStatement();
        int changed = stm.executeUpdate("update users set username = '" + user.getUsername() + "', password_hash = '" + newpassword_hash + "',is_admin = " + user.isAdmin() +
                " where id = " + user.getId());
        if(changed < 1) return false;
        return true;
    }

    public boolean updateUser(User user) throws SQLException {
        Statement stm = conn.createStatement();
        int changed = stm.executeUpdate("update users set username = '" + user.getUsername() + "' ,is_admin = " + user.isAdmin() +
                " where id = " + user.getId());
        if(changed < 1) return false;
        return true;
    }

    @Override
    public User getUser(String userName) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("select * from users where username = '" + userName + "'");
        if(!res.next()) return null;
        User newUser = new User(res.getInt(1) , res.getString(2) , res.getBoolean(4));
        return newUser;
    }

    @Override
    public User getUser(int id) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("select * from users where id = " + id);
        if(!res.next()) return null;
        User newUser = new User(res.getInt(1) , res.getString(2) , res.getBoolean(4));
        return newUser;
    }

    @Override
    public boolean addFriend(User sender , User receiver) throws SQLException {
        Statement stm = conn.createStatement();
        boolean changed = stm.execute("insert into friends(user_Id , friend_Id , accepted) values ('" + sender.getId() + "'," + receiver.getId() + "," + false +")");
        return changed;
    }

    @Override
    public boolean acceptFriend(User sender , User receiver) throws SQLException {
        Statement stm = conn.createStatement();
        int changed = stm.executeUpdate("update friends set accepted = true where user_Id = " + sender.getId() + " and friend_Id = " + receiver.getId());
        if(changed < 1) return false;
        return true;
    }

    @Override
    public boolean tryLogin(String username, String password_hash) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("select password_hash from users where username = '" + username + "'");
        if(!res.next()) return false;
        String hash = res.getString(1);
        if(hash.equals(password_hash)) return true;
        return false;
    }

    @Override
    public List<User> searchByUsername(String userName) throws SQLException {
        List<User> resultList = new ArrayList<>();
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery("select * from users where username like LOWER('%" + userName + "%')");
        while(res.next()){
            User newUser = new User(res.getInt(1) , res.getString(2) , res.getBoolean(4));
            resultList.add(newUser);
        }
        return resultList;
    }

    @Override
    public List<User> getFriends(User user) throws SQLException {
        List<User> resultList = new ArrayList<>();
        Statement stm = conn.createStatement();
        HashSet<Integer> ids = new HashSet<>();
        ResultSet res = stm.executeQuery("select * from friends where user_id = " + user.getId() +  " and accepted = true");
        while(res.next()){
            ids.add(res.getInt(3));
        }
        res = stm.executeQuery("select * from friends where friend_id = " + user.getId() +  " and accepted = true");
        while(res.next()){
            ids.add(res.getInt(2));
        }

        for(Integer i : ids){
            resultList.add(getUser(i));
        }
        return resultList;
    }

    @Override
    public List<User> getSentRequests(User user) throws SQLException {
        List<User> resultList = new ArrayList<>();
        Statement stm = conn.createStatement();
        HashSet<Integer> ids = new HashSet<>();
        ResultSet res = stm.executeQuery("select * from friends where user_id = " + user.getId() +  " and accepted = false");
        while(res.next()){
            ids.add(res.getInt(3));
        }
        for(Integer i : ids){
            resultList.add(getUser(i));
        }
        return resultList;
    }

    @Override
    public List<User> getReceivedRequests(User user) throws SQLException {
        List<User> resultList = new ArrayList<>();
        Statement stm = conn.createStatement();
        HashSet<Integer> ids = new HashSet<>();
        ResultSet res = stm.executeQuery("select * from friends where friend_id = " + user.getId() +  " and accepted = false");
        while(res.next()){
            ids.add(res.getInt(2));
        }

        for(Integer i : ids){
            resultList.add(getUser(i));
        }
        return resultList;
    }
}
