import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private String title;
    private String description;
    private int creatorId;
    private int quizTimeSeconds;

    public Quiz(int id, String title, String description, int creatorId, int quizTimeSeconds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.quizTimeSeconds = quizTimeSeconds;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuizTime(){
        return quizTimeSeconds;
    }

    public void setQuizTime(int quizTimeSeconds){
        this.quizTimeSeconds = quizTimeSeconds;
    }

    public String toString(){
        return "ID: " + getId() + " TITLE: " + getTitle() + " DESCRIPTION: " + getDescription() + " CREATOR_ID: "
                + getCreatorId() + " QUIZTIME: " + getQuizTime();
    }
}
