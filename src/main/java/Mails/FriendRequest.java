package Mails;
import Users.User;
import Users.UserDao;
import Users.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

public class FriendRequest extends Mail {

    public FriendRequest(int mailId, int fromId, int toId, Date sentDate) {
        super(mailId, fromId, toId, sentDate);
    }

    @Override
    public String getType() {
        return "FRIEND_REQUEST";
    }

    @Override
    String getTitle() {
        String name;
        try {
            UserService us=new UserService();

            User user = us.getUser(getId());
            name = user.getUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return name + " sent you a friend request!";
    }

    @Override
    String getContent() {
      return null;
      //return yes or no
    }



}
