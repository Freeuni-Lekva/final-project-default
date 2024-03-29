package Mails;
import Users.User;
import Users.UserDao;
import Users.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Challenge extends Mail {

    private String link;
    private String title;

    public Challenge(int mailId, int fromId, int toId,String title , String link , Date sentDate) {
        super(mailId, fromId, toId, sentDate);
        this.link = link;
        this.title = title;
    }

    @Override
    public String getType() {
        return "CH";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return link;
    }
}
