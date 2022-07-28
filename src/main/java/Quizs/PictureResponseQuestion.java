
public class PictureResponseQuestion extends Question{

    public PictureResponseQuestion(int id, int quizId, String description){
        super(id, quizId, description);
    }
    public String getType(){
        return "PICTURE_RESPONSE";
    }

    public String getTag(){
        return "";
    }
}
