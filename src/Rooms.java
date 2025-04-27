import java.sql.*;
import java.util.Scanner;

public class Rooms {

    public static void viewRooms() {
        System.out.println("\n--- List of Rooms ---");

        String query = "SELECT * FROM Rooms";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                System.out.println("No rooms found.");
            } else {
                do {
                    int roomId = rs.getInt("RoomID");
                    String roomNumber = rs.getString("RoomNumber");
                    String roomType = rs.getString("RoomType");
                    String bedType = rs.getString("BedType");
                    double pricePerNight = rs.getDouble("PricePerNight");
                    String status = rs.getString("Status");

                    System.out.println("Room ID: " + roomId + ", Room Number: " + roomNumber +
                            ", Type: " + roomType + ", Bed Type: " + bedType +
                            ", Price per Night: " + pricePerNight + ", Status: " + status);
                } while (rs.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRoom(Scanner scanner) {
        System.out.println("\n--- Add New Room ---");

        System.out.print("Enter Room Number: ");
        String roomNumber = scanner.next();
        System.out.print("Enter Room Type (Single, Double, Suite): ");
        String roomType = scanner.next();
        System.out.print("Enter Bed Type (King, Queen, Twin): ");
        String bedType = scanner.next();
        System.out.print("Enter Price per Night: ");
        double pricePerNight = scanner.nextDouble();
        System.out.print("Enter Room Status (Available, Booked, Maintenance): ");
        String status = scanner.next();

        String query = "INSERT INTO Rooms (RoomNumber, RoomType, BedType, PricePerNight, Status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roomNumber);
            stmt.setString(2, roomType);
            stmt.setString(3, bedType);
            stmt.setDouble(4, pricePerNight);
            stmt.setString(5, status);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room added successfully!");
            } else {
                System.out.println("Failed to add room.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a room's details
    public static void updateRoom(Scanner scanner) {
        System.out.println("\n--- Update Room Details ---");

        System.out.print("Enter Room ID to update: ");
        int roomId = scanner.nextInt();
        System.out.print("Enter new Room Type (Single, Double, Suite): ");
        String roomType = scanner.next();
        System.out.print("Enter new Bed Type (King, Queen, Twin): ");
        String bedType = scanner.next();
        System.out.print("Enter new Price per Night: ");
        double pricePerNight = scanner.nextDouble();
        System.out.print("Enter new Room Status (Available, Booked, Maintenance): ");
        String status = scanner.next();

        String query = "UPDATE Rooms SET RoomType = ?, BedType = ?, PricePerNight = ?, Status = ? WHERE RoomID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roomType);
            stmt.setString(2, bedType);
            stmt.setDouble(3, pricePerNight);
            stmt.setString(4, status);
            stmt.setInt(5, roomId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("Failed to update room.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a room
    public static void deleteRoom(Scanner scanner) {
        System.out.println("\n--- Delete Room ---");

        System.out.print("Enter Room ID to delete: ");
        int roomId = scanner.nextInt();

        String query = "DELETE FROM Rooms WHERE RoomID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Failed to delete room.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
