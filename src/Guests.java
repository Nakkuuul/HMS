import java.sql.*;
import java.util.Scanner;

public class Guests {

    // Method to view all guests
    public static void viewGuests() {
        System.out.println("\n--- List of Guests ---");

        String query = "SELECT * FROM Guests";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Check if there are any guests
            if (!rs.next()) {
                System.out.println("No guests found.");
            } else {
                // Print the guest details
                do {
                    int guestId = rs.getInt("GuestID");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String phone = rs.getString("Phone");
                    String email = rs.getString("Email");
                    String address = rs.getString("Address");

                    System.out.println("Guest ID: " + guestId + ", Name: " + firstName + " " + lastName +
                            ", Phone: " + phone + ", Email: " + email + ", Address: " + address);
                } while (rs.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new guest
    public static void addGuest(Scanner scanner) {
        System.out.println("\n--- Add New Guest ---");

        System.out.print("Enter First Name: ");
        String firstName = scanner.next();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.next();
        System.out.print("Enter Phone: ");
        String phone = scanner.next();
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Address: ");
        scanner.nextLine(); // Consume the leftover newline
        String address = scanner.nextLine();

        String query = "INSERT INTO Guests (FirstName, LastName, Phone, Email, Address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("New guest added successfully!");
            } else {
                System.out.println("Failed to add guest.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update guest details
    public static void updateGuest(Scanner scanner) {
        System.out.println("\n--- Update Guest Details ---");

        System.out.print("Enter Guest ID to update: ");
        int guestId = scanner.nextInt();
        System.out.print("Enter new Phone: ");
        String phone = scanner.next();
        System.out.print("Enter new Email: ");
        String email = scanner.next();
        System.out.print("Enter new Address: ");
        scanner.nextLine(); // Consume the leftover newline
        String address = scanner.nextLine();

        String query = "UPDATE Guests SET Phone = ?, Email = ?, Address = ? WHERE GuestID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, phone);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setInt(4, guestId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Guest details updated successfully!");
            } else {
                System.out.println("Failed to update guest.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a guest
    public static void deleteGuest(Scanner scanner) {
        System.out.println("\n--- Delete Guest ---");

        System.out.print("Enter Guest ID to delete: ");
        int guestId = scanner.nextInt();

        String query = "DELETE FROM Guests WHERE GuestID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, guestId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Guest deleted successfully!");
            } else {
                System.out.println("Failed to delete guest.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
