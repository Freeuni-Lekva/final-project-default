package History;
import Quizs.*;
import Users.IUserDao;
import Users.UserDao;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class HistoryDaoTest extends TestCase {
    private IHistoryDao hDao;
    private IUserDao uDao;
    private IQuizDao qDao;
    @Override
    protected void setUp() throws Exception {
        DatabaseConnection dbConn = new DatabaseConnection();
        TestHelper.deleteAndCreateDatabase(dbConn);
        hDao = new HistoryDao(new DatabaseConnection().getConnection());
        uDao = new UserDao(new DatabaseConnection().getConnection());
        qDao = new QuizDao();
        Date date = new Date(System.currentTimeMillis());
        History history1 = new History(1,1,1,10,date,date);
        History history2 = new History(1,1,2,10,date,date);
        History history3 = new History(1,1,2,20,date,date);
        History history4 = new History(1,2,2,30,date,date);

        uDao.addUser("user1","pass");
        uDao.addUser("user2","pass");
        qDao.addQuiz("testQuiz1","asd1",1,1,true,false,true,true);
        qDao.addQuiz("testQuiz2","asd2",1,1,true,false,true,true);
        hDao.addHistoryEntry(history1);
        hDao.addHistoryEntry(history2);
        hDao.addHistoryEntry(history3);
        hDao.addHistoryEntry(history4);
    }

    public void test1() throws SQLException, ClassNotFoundException {
        ArrayList <History> ar = hDao.getAllHistory();
        assertEquals(ar.size(),4);
        ar = hDao.getUsersFromHistory(1);
        assertEquals(ar.size(),1);
        ar = hDao.getUsersFromHistory(2);
        assertEquals(ar.size(),3);
        ar = hDao.getQuizzesFromHistory(1);
        assertEquals(ar.size(),3);
        ar = hDao.getQuizzesFromHistory(2);
        assertEquals(ar.size(),1);
    }

    public void test2() throws SQLException, ClassNotFoundException{
        ArrayList <History> ar = hDao.getScore(2,1);
        assertEquals(ar.get(0).getScore(),20);
        assertEquals(ar.get(1).getScore(),10);
        HistorySummary hs = hDao.getHistorySummary(2);
        assertEquals(hs.getAverage_score(),20.0);
        hs = hDao.getHistorySummary(1);
        assertEquals(hs.getAverage_score(),10.0);
    }

    public void test3() throws SQLException, ClassNotFoundException{
        ArrayList <History> ar = hDao.getRecentTestTakers(2);
        assertEquals(ar.size(),3);
        ArrayList <Quiz> qz = hDao.getPopularQuizzes(5);
        assertEquals(qz.get(0).getId(),2);
        assertEquals(qz.get(1).getId(),1);
        ArrayList <Quiz> q = hDao.getRecentQuizzesTakenBy(2,5);
        assertEquals(q.size(),1);
        q = hDao.getRecentQuizzesTakenBy(1,5);
        assertEquals(q.size(),3);
    }
}
