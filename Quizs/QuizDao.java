import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizDao implements IQuizDao{
    private DatabaseConnection dbConn;

    public QuizDao() throws SQLException, ClassNotFoundException {
        dbConn = new DatabaseConnection();
    }

    public ArrayList<Quiz> getQuizzes() throws SQLException {
        ArrayList<Quiz> result = new ArrayList<>();

        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM quizes;");

        while(rs.next()){
            Quiz quiz = new Quiz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
            result.add(quiz);
        }

        st.close();

        return result;
    }

    public Quiz getQuiz(int id) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM quizes WHERE Id = " + id + ";");

        if(!rs.next()) return null;

        Quiz quiz = new Quiz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
        st.close();
        return quiz;
    }

    public void addQuiz(String title, String description, int creatorId, int quizTime) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("INSERT INTO quizes(Title, description, creator_id, quizTime) VALUES('" + title + "', '"
                + description + "', " + creatorId + ", " + quizTime + ");");
        st.close();
    }

    public boolean removeQuiz(Quiz quiz) throws SQLException {
        Quiz gottenQuiz = getQuiz(quiz.getId());
        if(gottenQuiz == null){
            //throw new RuntimeException("Quiz with such an id is not present in our database");
            System.out.println("Quiz with such an id is not present in our database");
            return false;
        }
        removeQuizHistory(quiz);
        removeQuizQuestions(quiz);

        Statement st = dbConn.getConnection().createStatement();
        st.executeUpdate("DELETE FROM quizes WHERE Id = " + quiz.getId() + ";");
        st.close();

        return true;
    }

    private boolean removeQuizHistory(Quiz quiz) throws SQLException {
        return removeQuizHistory(quiz.getId());
    }

    private boolean removeQuizHistory(int quizId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("DELETE FROM history WHERE quiz_id = " + quizId + ";");
        if(diff < 1) return false;
        return true;
    }

    public boolean setQuizTitle(Quiz quiz, String title) throws SQLException {
        return setQuizTitle(quiz.getId(), title);
    }

    public boolean setQuizTitle(int quizId, String title) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE quizes SET Title = '" + title + "' + WHERE Id = " + quizId + ";");
        st.close();

        if(diff < 1) return false;
        return true;
    }

    public boolean setQuizDescription(Quiz quiz, String description) throws SQLException {
        return setQuizDescription(quiz.getId(), description);
    }

    public boolean setQuizDescription(int quizId, String description) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE quizes SET description = '" + description + "' + WHERE Id = " + quizId + ";");
        st.close();

        if(diff < 1) return false;
        return true;
    }

    public boolean setQuizTime(Quiz quiz, int quizTime) throws SQLException {
        return setQuizTime(quiz.getId(), quizTime);
    }

    public boolean setQuizTime(int quizId, int quizTime) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE quizes SET quizTime = " + quizTime + " + WHERE Id = " + quizId + ";");
        st.close();

        if(diff < 1) return false;
        return true;
    }

    public boolean updateQuiz(Quiz quiz) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("UPDATE quizes SET quizTime = " + quiz.getQuizTime() + ", description = '"
                + quiz.getDescription() + "', Title = '" + quiz.getTitle() + "'" + " + WHERE Id = " + quiz.getId() + ";");
        st.close();

        if(diff < 1) return false;
        return true;
    }

    public ArrayList<Question> getQuestions(Quiz quiz) throws SQLException {
        return getQuestions(quiz.getId());
    }

    public ArrayList<Question> getQuestions(int quizId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM questions WHERE quiz_id = " + quizId + ";");
        ArrayList<Question> result = new ArrayList<>();

        while(rs.next()){
            int id = rs.getInt(1);
            String description = rs.getString(4);
            String questionType = rs.getString(3);
            QuestionDao.questionGetter(result, id, quizId, description, questionType);
        }

        return result;
    }

    public boolean removeQuizQuestions(Quiz quiz) throws SQLException {
        return removeQuizQuestions(quiz.getId());
    }

    public boolean removeQuizQuestions(int quizId) throws SQLException {
        Statement statement = dbConn.getConnection().createStatement();
        //questions related to given quiz (foreign key)
        ResultSet rs = statement.executeQuery("SELECT Id FROM questions WHERE quiz_id = " + quizId + ";");
        while(rs.next()){
            int id = rs.getInt(1);
            removeQuestionAnswers(id);
        }

        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("DELETE FROM questions WHERE quiz_id = " + quizId + ";");
        st.close();
        if(diff < 1) return false;
        return true;
    }

    private boolean removeQuestionAnswers(int questionId) throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        int diff = st.executeUpdate("DELETE FROM answers WHERE Question_id = " + questionId + ";");

        if(diff < 1) return false;
        return true;
    }
}
