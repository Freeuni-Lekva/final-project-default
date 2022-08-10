package Quizs;
public class MultipleChoiceQuestion extends Question{

    public MultipleChoiceQuestion(int id, int quizId, String description){
        super(id, quizId, description);
    }
    public String getType(){
        return "MULTIPLE_CHOICE";
    }

    public String getTag(){
        return "";
    }
}
