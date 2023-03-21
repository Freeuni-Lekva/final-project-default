import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionDao implements IQuestionDao{
    private static DatabaseConnection dbConn;

    public QuestionDao() throws SQLException, ClassNotFoundException {
        dbConn = new DatabaseConnection();
    }

    public ArrayList<Question> getQuestions() throws SQLException {
        ArrayList<Question> result = new ArrayList<>();

        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM questions;");

        while(rs.next()){
            int id = rs.getInt(1);
            int quizId = rs.getInt(2);
            String description = rs.getString(4);
            String questionType = rs.getString(3);
            questionGetter(result, id, quizId, description, questionType);
        }

        st.close();

        return result;
    }

    static void questionGetter(ArrayList<Question> result, int id, int quizId, String description, String questionType) {
        if(questionType.equals("QUESTION_RESPONSE")){
            result.add(new QuestionResponse(id, quizId, description));
        }else if(questionType.equals("MULTIPLE_CHOICE")){
            result.add(new MultipleChoiceQuestion(id, quizId, description));
        }else if(questionType.equals("PICTURE_RESPONSE")){
            result.add(new PictureResponseQuestion(id, quizId, description));
        }else if(questionType.equals("FILL_QUESTION")){
            result.add(new FillQuestion(id, quizId, description));
        }
    }

    public Question getQuestion(int id) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM questions WHERE Id = " + id + ";");

        if(!rs.next()) return null;

        int quizId = rs.getInt(2);
        String description = rs.getString(4);
        String questionType = rs.getString(3);
        if(questionType.equals("QUESTION_RESPONSE")){
            return new QuestionResponse(id, quizId, description);
        }else if(questionType.equals("MULTIPLE_CHOICE")){
            return new MultipleChoiceQuestion(id, quizId, description);
        }else if(questionType.equals("PICTURE_RESPONSE")){
            return new PictureResponseQuestion(id, quizId, description);
        }else if(questionType.equals("FILL_QUESTION")){
            return new FillQuestion(id, quizId, description);
        }

        throw new RuntimeException("There is no class with such a type of a question");
    }

    public void addQuestion(int quizId, String type, String description) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("INSERT INTO questions(quiz_id, Question_type, Question_Description) " +
                "VALUES(" + quizId + ", '" + type + "', '" + description + "');");
        st.close();
    }

    public boolean removeQuestion(Question question) throws SQLException {
        return removeQuestion(question.getId());
    }

    public boolean removeQuestion(int questionId) throws SQLException {
        Question gottenQuestion = getQuestion(questionId);
        if(gottenQuestion == null){
            System.out.println("Such question doesn't exist in our database");
            return false;
        }

        removeQuestionAnswers(questionId);
        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("DELETE FROM questions WHERE Id = " + questionId + ";");
        st.close();
        return true;
    }

    public boolean setQuestionDescription(Question question, String description) throws SQLException {
        return setQuestionDescription(question.getId(), description);
    }

    public boolean setQuestionDescription(int questionId, String description) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE questions SET Question_Description = '"
                + description + "' WHERE Id = " + questionId + ";");
        st.close();

        if(diff < 1) return false;
        return true;
    }

    public boolean updateQuestion(Question question) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        //შეიძლება type შეუცვალოს დავუშვათ Picture Response ზე მაგრამ კითხვაში ფოტო არ ჩააგდოს და ასეთი
        //ქეისები გასათვალისწინებელია რომელსაც მერე ვიზამ.
        int diff = st.executeUpdate("UPDATE questions SET Question_type = '" + question.getType() +
                "', Question_Description = '" + question.getDescription() + "' WHERE Id = " + question.getId() + ";");

        st.close();

        if(diff < 1) return false;
        return true;
    }

    public ArrayList<Answer> getAnswers(Question question) throws SQLException {
        return getAnswers(question.getId());
    }

    public ArrayList<Answer> getAnswers(int questionId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT FROM answers WHERE Question_id = " + questionId + ";");

        ArrayList<Answer> result = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt(1);
            String description = rs.getString(3);
            boolean isCorrect = rs.getBoolean(4);

            Answer answer = new Answer(id, description, questionId, isCorrect);
            result.add(answer);
        }

        return result;
    }

    public void addAnswer(Question question, String description, boolean isCorrect) throws SQLException {
        addAnswer(question.getId(), description, isCorrect);
    }

    public void addAnswer(int questionId, String description, boolean isCorrect) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("INSERT INTO answers(Question_id, answer_Description, is_correct) " +
                "VALUES(" + questionId + ", '" + description + "', " + isCorrect + ");");
        st.close();
    }

    public boolean removeAnswer(Answer answer) throws SQLException {
        return removeAnswer(answer.getId());
    }

    public boolean removeAnswer(int answerId) throws SQLException {
        Answer gottenAnswer = getAnswer(answerId);

        if(gottenAnswer == null){
            System.out.println("Such an answer is not in our database");
            return false;
        }

        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("DELETE FROM answers WHERE Id = " + answerId + ";");
        st.close();

        return true;
    }

    public boolean removeQuestionAnswers(Question question) throws SQLException {
        return removeQuestionAnswers(question.getId());
    }

    public boolean removeQuestionAnswers(int questionId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("DELETE FROM answers WHERE Question_id = " + questionId + ";");

        if(diff < 1) return false;
        return true;
    }

    public Answer getAnswer(int answerId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT FROM answers WHERE Id = " + answerId + ";");
        st.close();

        if(!rs.next()) return null;
        return new Answer(answerId, rs.getString(3), rs.getInt(2), rs.getBoolean(4));
    }

    public boolean setAnswerDescription(Answer answer, String description) throws SQLException {
        return setAnswerDescription(answer.getId(), description);
    }

    public boolean setAnswerDescription(int answerId, String description) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE answers SET answer_Description = '"
                + description + "' WHERE Id = " + answerId + ";");

        if(diff < 1) return false;
        return true;
    }

    public boolean setAnswerCorrectness(Answer answer, boolean isCorrect) throws SQLException{
        return setAnswerCorrectness(answer.getId(), isCorrect);
    }

    public boolean setAnswerCorrectness(int answerId, boolean isCorrect) throws SQLException{
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE answers SET is_correct = '"
                + isCorrect + "' WHERE Id = " + answerId + ";");

        if(diff < 1) return false;
        return true;
    }

    public boolean updateAnswer(Answer answer) throws SQLException {
        boolean differentCorrectness = setAnswerCorrectness(answer, answer.isCorrect());
        boolean differentDescription = setAnswerDescription(answer, answer.getDescription());
        return differentCorrectness || differentDescription;
    }
}
