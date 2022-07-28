package History;

import java.sql.Date;

public class History {
    private int h_id;
    private int user_id;
    private int quiz_id;
    private int score;
    private Date start_date;
    private Date end_date;

    public History(){

    }
    public History(int h_id, int user_id, int quiz_id, int score, Date start_date, Date end_date) {
        this.h_id = h_id;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.score = score;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getH_id() {
        return h_id;
    }

    public void setH_id(int h_id) {
        this.h_id = h_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
