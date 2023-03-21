import java.util.ArrayList;

public class PictureResponseQuestion implements Question{
    private int id;
    private int quizId;
    private String description;

    public PictureResponseQuestion(int id, int quizId, String description){
        this.id = id;
        this.quizId = quizId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getQuizId() {
        return quizId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Answer> getAnswers() {
        //QuestionDao - getAnswers
        return new ArrayList<Answer>();
    }

    public boolean addAnswer(Answer answer){
        ArrayList<Answer> answers = getAnswers();
        if(answers.contains(answer)) return false;

        //QuestionDao - addAnswer(answer)
        return true;
    }

    public String getType(){
        return "PICTURE_RESPONSE";
    }

    public String getTag(){
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.getId();
    }
}