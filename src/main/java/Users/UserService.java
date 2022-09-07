package Users;

import javax.jws.soap.SOAPBinding;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private IUserDao userDao;

    MessageDigest md;
    private static final String PASSWORD_PATTERN_STRING =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    private static final Pattern pasword_pattern = Pattern.compile(PASSWORD_PATTERN_STRING);

    private static final String USERNAME_PATTERN_STRING =
            "^[a-zA-Z0-9._-]{3,}$";

    private static final Pattern username_pattern = Pattern.compile(USERNAME_PATTERN_STRING);

    public UserService() throws SQLException, NoSuchAlgorithmException {
        userDao = new UserDao("jdbc:mysql://localhost:3306/quiz" , "root" , "tagvi_400");
        md = MessageDigest.getInstance("SHA");
    }

    public boolean makeUserAdmin(User user) throws SQLException {
        user.setAdmin(true);
        return userDao.updateUser(user);
    }

    public boolean changeUsername(User user , String newUsername) throws SQLException {
        if(userDao.getUser(newUsername) != null) return false;
        user.setUsername(newUsername);
        return userDao.updateUser(user);
    }

    public boolean changePassword(User user , String newPassword) throws SQLException {
        Matcher passMatcher = pasword_pattern.matcher(newPassword);
        if(!passMatcher.matches()) return false;

        md.update(newPassword.getBytes());
        byte[] hash = md.digest();
        String hashString = hexToString(hash);

        return userDao.updateUser(user , hashString);
    }

    public boolean login(String username, String password) throws SQLException {
        md.update(password.getBytes());
        byte[] hash = md.digest();
        String hashString = hexToString(hash);
        return userDao.tryLogin(username , hashString);
    }

    public User getUser(String username) throws SQLException {
        return userDao.getUser(username);
    }

    public User getUser(int id) throws SQLException {
        return userDao.getUser(id);
    }

    public User addUser(String username , String password) throws SQLException {
        if(!match(username , password)) return null;

        md.update(password.getBytes());
        byte[] hash = md.digest();
        String hashString = hexToString(hash);

        return userDao.addUser(username , hashString);
    }

    private boolean match(String username , String password) {
        Matcher passMatcher = pasword_pattern.matcher(password);
        if(!passMatcher.matches()) return false;
        Matcher usMatcher = username_pattern.matcher(username);
        if(!usMatcher.matches()) return false;
        return true;
    }

    public boolean passwordMatch(String password){
        Matcher passMatcher = pasword_pattern.matcher(password);
        if(!passMatcher.matches()) return false;
        return true;
    }

    public boolean usernameMatch(String username){
        Matcher usMatcher = username_pattern.matcher(username);
        if(!usMatcher.matches()) return false;
        return true;
    }

    public boolean addFriend(User sender , User receiver) throws SQLException {

        if(userDao.getFriends(sender).stream().anyMatch(c -> c.getUsername().equals(receiver.getUsername()))) return false;

        if(userDao.getSentRequests(sender).stream().anyMatch(c -> c.getUsername().equals(receiver.getUsername()))) return false;

        if(userDao.getReceivedRequests(sender).stream().anyMatch(c -> c.getUsername().equals(receiver.getUsername()))){
            userDao.acceptFriend(receiver , sender);
            return true;
        }

        userDao.addFriend(sender , receiver);
        return true;

    }

    public boolean acceptFriend(User sender , User receiver) throws SQLException {
        return userDao.acceptFriend(sender , receiver);
    }

    public List<User> searchByUsername(String username) throws SQLException{
        return userDao.searchByUsername(username);
    }


    /*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    /*
     Given a string of hex byte values such as "24a26f", creates
     a byte[] array of those values, one byte value -128..127
     for each 2 chars.
     (provided code)
    */

    private static byte[] hexToArray(String hex) {
        byte[] result = new byte[hex.length()/2];
        for (int i=0; i<hex.length(); i+=2) {
            result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
        }
        return result;
    }
}
