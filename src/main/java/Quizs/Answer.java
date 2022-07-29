import java.util.Objects;

public class Answer {
    private int id;
    private String description;
    private int questionId;
    private boolean isCorrect;


    public Answer(int id, String description, int questionId, boolean isCorrect) {
        this.id = id;
        this.description = description;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.getId();
    }

    public String toString(){
        return "ID: " + getId() + " Description: " + getDescription() + " questionId: " + getQuestionId() +
                " isCorrect: " + isCorrect();
    }
}
