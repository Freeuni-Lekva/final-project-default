package Mails;
import java.util.Date;

public abstract class Mail {

    private int mailId;
    private int fromId;
    private int toId;
    private Date sentDate;

    public Mail(int mailId, int fromId, int toId, Date sentDate) {
        this.mailId = mailId;
        this.fromId = fromId;
        this.toId = toId;
        this.sentDate = sentDate;
    }

    public int getId() {
        return mailId;
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }

    public Date getDate() {
        return sentDate;
    }

    abstract String getType();
    abstract String getTitle();
    abstract String getContent();
}
