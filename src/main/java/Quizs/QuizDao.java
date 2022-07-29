import java.sql.PreparedStatement;
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
        PreparedStatement st = dbConn.getConnection().prepareStatement("SELECT * FROM quizes WHERE Id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if(!rs.next()) return null;

        Quiz quiz = new Quiz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
        st.close();
        return quiz;
    }

    public Quiz addQuiz(String title, String description, int creatorId, int quizTime) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("INSERT INTO quizes (Title, description, creator_id, quizTime)" +
                "VALUES (?, ?, ?, ?);");
        st.setString(1, title);
        st.setString(2, description);
        st.setInt(3, creatorId);
        st.setInt(4, quizTime);
        st.executeUpdate();
        st.close();
        return getQuiz(getLastInsertedQuizId());
    }

    private int getLastInsertedQuizId() throws SQLException {
        Statement st = dbConn.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM QUIZES ORDER BY id DESC;");
        if(!rs.next()) return -1;
        int result = rs.getInt(1);
        st.close();
        return result;
    }

    public boolean removeQuiz(Quiz quiz) throws SQLException {
        Quiz gottenQuiz = getQuiz(quiz.getId());
        if(gottenQuiz == null){
            System.out.println("Quiz with such an id is not present in our database");
            return false;
        }
        removeQuizHistory(quiz);
        removeQuizQuestions(quiz);

        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM quizes WHERE Id = ?;");
        st.setInt(1, quiz.getId());
        st.executeUpdate();
        st.close();

        return true;
    }

    private boolean removeQuizHistory(Quiz quiz) throws SQLException {
        return removeQuizHistory(quiz.getId());
    }

    private boolean removeQuizHistory(int quizId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM history WHERE quiz_id = ?;");
        st.setInt(1, quizId);
        int diff = st.executeUpdate();
        st.close();
        return diff >= 1;
    }

    public boolean setQuizTitle(Quiz quiz, String title) throws SQLException {
        return setQuizTitle(quiz.getId(), title);
    }

    public boolean setQuizTitle(int quizId, String title) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE quizes SET Title = ? WHERE Id = ?;");
        st.setString(1, title);
        st.setInt(2, quizId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean setQuizDescription(Quiz quiz, String description) throws SQLException {
        return setQuizDescription(quiz.getId(), description);
    }

    public boolean setQuizDescription(int quizId, String description) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE quizes SET description = ? WHERE Id = ?;");
        st.setString(1, description);
        st.setInt(2, quizId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean setQuizTime(Quiz quiz, int quizTime) throws SQLException {
        return setQuizTime(quiz.getId(), quizTime);
    }

    public boolean setQuizTime(int quizId, int quizTime) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE quizes SET quizTime = ? WHERE Id = ?");
        st.setInt(1, quizTime);
        st.setInt(2, quizId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public boolean updateQuiz(Quiz quiz) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("UPDATE quizes SET quizTime = ?, description = ?, Title = ? WHERE Id = ?;");
        st.setInt(1, quiz.getQuizTime());
        st.setString(2, quiz.getDescription());
        st.setString(3, quiz.getTitle());
        st.setInt(4, quiz.getId());
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }

    public ArrayList<Question> getQuestions(Quiz quiz) throws SQLException {
        return getQuestions(quiz.getId());
    }

    public ArrayList<Question> getQuestions(int quizId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("SELECT * FROM questions WHERE quiz_id = ?;");
        st.setInt(1, quizId);
        ResultSet rs = st.executeQuery();
        ArrayList<Question> result = new ArrayList<>();

        while(rs.next()){
            int id = rs.getInt(1);
            String description = rs.getString(4);
            String questionType = rs.getString(3);
            QuestionDao.questionGetter(result, id, quizId, description, questionType);
        }
        st.close();

        return result;
    }

    public boolean removeQuizQuestions(Quiz quiz) throws SQLException {
        return removeQuizQuestions(quiz.getId());
    }

    public boolean removeQuizQuestions(int quizId) throws SQLException {
        PreparedStatement statement = dbConn.getConnection().prepareStatement("SELECT Id FROM questions WHERE quiz_id = ?;");
        statement.setInt(1, quizId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            removeQuestionAnswers(id);
        }
        statement.close();

        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM questions WHERE quiz_id = ?;");
        st.setInt(1, quizId);
        int diff = st.executeUpdate();
        st.close();
        return diff >= 1;
    }

    private boolean removeQuestionAnswers(int questionId) throws SQLException {
        PreparedStatement st = dbConn.getConnection().prepareStatement("DELETE FROM answers WHERE Question_id = ?;");
        st.setInt(1, questionId);
        int diff = st.executeUpdate();
        st.close();

        return diff >= 1;
    }
}
