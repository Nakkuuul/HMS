import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginAuth {
    // Method to authenticate staff login
    public static boolean authenticate(int staffId, String password) {
        String query = "SELECT * FROM Staff WHERE StaffID = ? AND Password = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, staffId);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If exception occurs, authentication fails
        }
    }
}
