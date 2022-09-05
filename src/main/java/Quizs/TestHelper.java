package Quizs;

import java.sql.SQLException;
import java.sql.Statement;

public class TestHelper {
    private static final String CREATE_USERS = "create table users(Id serial primary key,username varchar(50) unique," +
            "password_hash varchar(200),is_admin boolean);";
    private static final String CREATE_QUIZZES = "create table quizes(Id serial primary key,Title varchar(200)," +
            "description varchar(1500),creator_id bigint unsigned,foreign key (creator_id) references users(id),quizTime bigint);";
    private static final String CREATE_QUESTIONS = "create table questions(Id serial primary key,quiz_id bigint unsigned," +
            "Question_type varchar(30),Question_Description varchar(2000),foreign key (quiz_id) references QUIZES(id));";
    private static final String CREATE_ANSWERS = "create table answers(Id serial primary key,Question_id bigint unsigned," +
            "answer_Description varchar(1000),is_correct boolean,foreign key (Question_id) references Questions(id));";
    private static final String CREATE_HISTORY = "create table history(Id serial primary key,user_Id bigint unsigned," +
            "quiz_id bigint unsigned,score integer,start_time date,end_time date,foreign key (user_id) references users(id)," +
            "foreign key (quiz_id) references quizes(id));";
    private static final String CREATE_FRIENDS = "create table friends(Id serial primary key,user_Id bigint unsigned," +
            "friend_Id bigint unsigned,accepted boolean,foreign key (user_id) references users(id)," +
            "foreign key (friend_Id) references users(id));";
    private static final String CREATE_MAILS = "create table mails(Id serial primary key,from_Id bigint unsigned,to_Id bigint unsigned," +
            "message_type varchar(5),message_title varchar(200),message_content varchar(1000),send_date date," +
            "foreign key (from_Id) references users(id),foreign key (to_Id) references users(id));";
    private static final String CREATE_ACHIEVEMENTS = "create table achievments(Id serial primary key,name varchar(100)," +
            "description varchar(500));";
    private static final String CREATE_ACHIEVEMENTS_HISTORY = "create table achievment_history(Id serial primary key," +
            "user_Id bigint unsigned,achievment_Id bigint unsigned,acquired_date date,foreign key (user_Id) references users(id)," +
            "foreign key (achievment_Id) references achievments(id));";

    public static void deleteAndCreateDatabase(DatabaseConnection db) throws SQLException {
        Statement st = db.getConnection().createStatement();
        dropTables(st);
        createTables(st);
    }

    private static void dropTables(Statement st) throws SQLException {
        dropTable(st, "achievment_history");
        dropTable(st, "achievments");
        dropTable(st, "MAILS");
        dropTable(st, "FRIENDS");
        dropTable(st, "HISTORY");
        dropTable(st, "ANSWERS");
        dropTable(st, "QUESTIONS");
        dropTable(st, "QUIZES");
        dropTable(st, "USERS");
    }

    private static void dropTable(Statement st, String tableName) throws SQLException {
        st.execute("DROP TABLE IF EXISTS " + tableName + ";");
    }

    private static void createTables(Statement st) throws SQLException {
        st.execute(CREATE_USERS);
        st.execute(CREATE_QUIZZES);
        st.execute(CREATE_QUESTIONS);
        st.execute(CREATE_ANSWERS);
        st.execute(CREATE_HISTORY);
        st.execute(CREATE_FRIENDS);
        st.execute(CREATE_MAILS);
        st.execute(CREATE_ACHIEVEMENTS);
        st.execute(CREATE_ACHIEVEMENTS_HISTORY);
    }

}
