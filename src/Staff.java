import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Staff {

    // Method to add a new staff member to the database
    public static boolean addStaff(String firstName, String lastName, String phone, String email, String password, String role) {
        String query = "INSERT INTO Staff (FirstName, LastName, Phone, Email, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, password);  // In real-world apps, the password should be hashed
            stmt.setString(6, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if insertion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    // Method to get a staff member by StaffID
    public static Staff getStaffById(int staffId) {
        String query = "SELECT * FROM Staff WHERE StaffID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Staff(
                        rs.getInt("StaffID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if staff is not found
    }

    // Method to update a staff member's details
    public static boolean updateStaff(int staffId, String firstName, String lastName, String phone, String email, String password, String role) {
        String query = "UPDATE Staff SET FirstName = ?, LastName = ?, Phone = ?, Email = ?, Password = ?, Role = ? WHERE StaffID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, password);  // In real-world apps, the password should be hashed
            stmt.setString(6, role);
            stmt.setInt(7, staffId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    // Method to delete a staff member from the database
    public static boolean deleteStaff(int staffId) {
        String query = "DELETE FROM Staff WHERE StaffID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, staffId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Return true if deletion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    // Method to get all staff members
    public static List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM Staff";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                staffList.add(new Staff(
                        rs.getInt("StaffID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;  // Return the list of staff members
    }

    // Instance variables for staff details
    private int staffId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String role;

    // Constructor to create a staff instance from the database
    public Staff(int staffId, String firstName, String lastName, String phone, String email, String role) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    // Default constructor
    public Staff() {}

    // Getters and setters for staff properties
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Override toString() to display staff information easily
    @Override
    public String toString() {
        return "StaffID: " + staffId + ", Name: " + firstName + " " + lastName + ", Role: " + role;
    }
}
