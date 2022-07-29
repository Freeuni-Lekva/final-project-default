import java.sql.PreparedStatement;
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
        PreparedStatement st = dbConn.getConnection().prepareStatement("SELECT * FROM questions WHERE Id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if(!rs.next()) return null;

        int quizId = rs.getInt(2);
        String description = rs.getString(4);
        String questionType = rs.getString(3);
        st.close();
        switch (questionType) {
            case "QUESTION_RESPONSE":
                return new QuestionResponse(id, quizId, description);
            case "MULTIPLE_CHOICE":
                return new MultipleChoiceQuestion(id, quizId, description);
            case "PICTURE_RESPONSE":
                return new PictureResponseQuestion(id, quizId, description);
            case "FILL_QUESTION":
                return new FillQuestion(id, quizId, description);
        }

        throw new RuntimeException("There is no class with such a type of a question");
    }

    public Question addQuestion(int quizId, String type, String description) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("INSERT INTO questions (quiz_id, Question_type, Question_Description)" +
                "VALUES (?, ?, ?);");
        st.setInt(1, quizId);
        st.setString(2, type);
        st.setString(3, description);
        st.executeUpdate();
        st.close();
        return getQuestion(getLastInsertElement("questions"));
    }

    private int getLastInsertElement(String tableName) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + " ORDER BY id DESC;");
        if(!rs.next()) return -1;
        int result = rs.getInt(1);
        st.close();
        return result;
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
        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM questions WHERE Id = ?;");
        st.setInt(1, questionId);
        st.executeUpdate();
        st.close();
        return true;
    }

    public boolean setQuestionDescription(Question question, String description) throws SQLException {
        return setQuestionDescription(question.getId(), description);
    }

    public boolean setQuestionDescription(int questionId, String description) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE questions SET Question_Description = ? WHERE Id = ?;");
        st.setString(1, description);
        st.setInt(2, questionId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean updateQuestion(Question question) throws SQLException {
//        //შეიძლება type შეუცვალოს დავუშვათ Picture Response ზე მაგრამ კითხვაში ფოტო არ ჩააგდოს და ასეთი
//        //ქეისები გასათვალისწინებელია რომელსაც მერე ვიზამ.

        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE questions SET Question_type = ?, Question_Description = ? WHERE Id = ?;");
        st.setString(1, question.getType());
        st.setString(2, question.getDescription());
        st.setInt(3, question.getId());
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public ArrayList<Answer> getAnswers(Question question) throws SQLException {
        return getAnswers(question.getId());
    }

    public ArrayList<Answer> getAnswers(int questionId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("SELECT * FROM answers WHERE Question_id = ?;");
        st.setInt(1, questionId);
        ResultSet rs = st.executeQuery();

        ArrayList<Answer> result = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt(1);
            String description = rs.getString(3);
            boolean isCorrect = rs.getBoolean(4);

            Answer answer = new Answer(id, description, questionId, isCorrect);
            result.add(answer);
        }
        st.close();

        return result;
    }

    public Answer addAnswer(Question question, String description, boolean isCorrect) throws SQLException {
        return addAnswer(question.getId(), description, isCorrect);
    }

    public Answer addAnswer(int questionId, String description, boolean isCorrect) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("INSERT INTO answers (Question_id, answer_Description, is_correct)" +
                "VALUES (?, ?, ?);");
        st.setInt(1, questionId);
        st.setString(2, description);
        st.setBoolean(3, isCorrect);
        st.executeUpdate();
        st.close();
        return getAnswer(getLastInsertElement("answers"));
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

        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM answers WHERE Id = ?;");
        st.setInt(1, answerId);
        st.executeUpdate();
        st.close();

        return true;
    }

    public boolean removeQuestionAnswers(Question question) throws SQLException {
        return removeQuestionAnswers(question.getId());
    }

    public boolean removeQuestionAnswers(int questionId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM answers WHERE Question_id = ?;");
        st.setInt(1, questionId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public Answer getAnswer(int answerId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("SELECT * FROM answers WHERE Id = ?;");
        st.setInt(1, answerId);
        ResultSet rs = st.executeQuery();

        if(!rs.next()) return null;
        Answer result = new Answer(answerId, rs.getString(3), rs.getInt(2), rs.getBoolean(4));
        st.close();
        return result;
    }

    public boolean setAnswerDescription(Answer answer, String description) throws SQLException {
        return setAnswerDescription(answer.getId(), description);
    }

    public boolean setAnswerDescription(int answerId, String description) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE answers SET answer_Description = ? WHERE Id = ?;");
        st.setString(1, description);
        st.setInt(2, answerId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean setAnswerCorrectness(Answer answer, boolean isCorrect) throws SQLException{
        return setAnswerCorrectness(answer.getId(), isCorrect);
    }

    public boolean setAnswerCorrectness(int answerId, boolean isCorrect) throws SQLException{
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE answers SET is_correct = ? WHERE Id = ?;");
        st.setBoolean(1, isCorrect);
        st.setInt(2, answerId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean updateAnswer(Answer answer) throws SQLException {
        boolean differentCorrectness = setAnswerCorrectness(answer, answer.isCorrect());
        boolean differentDescription = setAnswerDescription(answer, answer.getDescription());
        return differentCorrectness || differentDescription;
    }
}
