import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/HotelManagement";
        String user = "root";
        String password = "root1234";
        return DriverManager.getConnection(url, user, password);
    }
}