package Mails;
import Users.User;
import Users.UserDao;
import Users.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Challenge extends Mail {

    public Challenge(int mailId, int fromId, int toId, Date sentDate) {
        super(mailId, fromId, toId, sentDate);
    }

    @Override
    public String getType() {
        return "CH";
    }

    @Override
    String getTitle() {
        String name;
        try {
            UserService us = new UserService();
            User user = us.getUser(getId());
            name = user.getUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return name + " has challenged you to take a quiz!";
    }

    @Override
    String getContent() {
        int usersBestScore = 0;
        String url = "";
        try {
            UserService us=new UserService();

            //User user = us.getUser(getId());
            // get users highest score
            // get url
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Hey, can you beat my high score: " + usersBestScore + "?\n"
                + "quiz link: " + url;
    }
}
