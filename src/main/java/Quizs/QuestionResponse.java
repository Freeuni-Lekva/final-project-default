
public class QuestionResponse extends Question {
   public QuestionResponse(int id, int quizId, String description){
       super(id, quizId, description);
   }

   public String getTag(){
       return "";
   }

   public String getType(){
       return "QUESTION_RESPONSE";
   }
}
