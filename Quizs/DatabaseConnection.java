import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "");
    }

    public Connection getConnection(){
        return conn;
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }
}