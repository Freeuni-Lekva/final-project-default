import java.util.ArrayList;

public interface Question {
    String getTag();
    int getId();
    int getQuizId();
    String getDescription();
    void setDescription(String description);
    ArrayList<Answer> getAnswers();
    boolean addAnswer(Answer answer);
    String getType();
    boolean equals(Object o);
}
