package Quizs;
public class FillQuestion extends Question{
    public FillQuestion(int id, int quizId, String description){
        super(id, quizId, description);
    }

    public String getTag(){
        return "";
    }

    public String getType(){
        return "FILL_QUESTION";
    }
}
