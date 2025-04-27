import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Bookings {

    // Method to view all bookings
    public static void viewBookings() {
        System.out.println("\n--- List of Bookings ---");

        String query = "SELECT b.BookingID, g.FirstName, g.LastName, r.RoomNumber, b.CheckInDate, b.CheckOutDate, b.NumberOfGuests, b.TotalAmount " +
                "FROM Bookings b " +
                "JOIN Guests g ON b.GuestID = g.GuestID " +
                "JOIN Rooms r ON b.RoomID = r.RoomID";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Check if there are any bookings
            if (!rs.next()) {
                System.out.println("No bookings found.");
            } else {
                // Print the booking details
                do {
                    int bookingId = rs.getInt("BookingID");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String roomNumber = rs.getString("RoomNumber");
                    Date checkInDate = rs.getDate("CheckInDate");
                    Date checkOutDate = rs.getDate("CheckOutDate");
                    int numberOfGuests = rs.getInt("NumberOfGuests");
                    double totalAmount = rs.getDouble("TotalAmount");

                    System.out.println("Booking ID: " + bookingId + ", Guest: " + firstName + " " + lastName +
                            ", Room: " + roomNumber + ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate +
                            ", Number of Guests: " + numberOfGuests + ", Total Amount: " + totalAmount);
                } while (rs.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create a new booking with automatic price calculation
    public static void createBooking(Scanner scanner) {
        System.out.println("\n--- Create New Booking ---");

        System.out.print("Enter Guest ID: ");
        int guestId = scanner.nextInt();
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();
        System.out.print("Enter Check-in Date (yyyy-mm-dd): ");
        String checkInDate = scanner.next();
        System.out.print("Enter Check-out Date (yyyy-mm-dd): ");
        String checkOutDate = scanner.next();
        System.out.print("Enter Number of Guests: ");
        int numberOfGuests = scanner.nextInt();

        // Get room price per night
        double pricePerNight = getRoomPrice(roomId);

        // Calculate number of nights
        int numberOfNights = calculateNights(checkInDate, checkOutDate);

        // Calculate total amount
        double totalAmount = pricePerNight * numberOfNights;

        System.out.println("Room price per night: $" + pricePerNight);
        System.out.println("Number of nights: " + numberOfNights);
        System.out.println("Total Amount: $" + totalAmount);

        String query = "INSERT INTO Bookings (GuestID, RoomID, CheckInDate, CheckOutDate, NumberOfGuests, TotalAmount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, guestId);
            stmt.setInt(2, roomId);
            stmt.setString(3, checkInDate);
            stmt.setString(4, checkOutDate);
            stmt.setInt(5, numberOfGuests);
            stmt.setDouble(6, totalAmount);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking created successfully!");
            } else {
                System.out.println("Failed to create booking.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get room price per night
    private static double getRoomPrice(int roomId) {
        String query = "SELECT PricePerNight FROM Rooms WHERE RoomID = ?";
        double price = 0.0;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("PricePerNight");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    // Helper method to calculate number of nights between two dates
    private static int calculateNights(String checkInDate, String checkOutDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date checkIn = sdf.parse(checkInDate);
            java.util.Date checkOut = sdf.parse(checkOutDate);

            long diffInMillies = checkOut.getTime() - checkIn.getTime();
            int nights = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return Math.max(nights, 1); // Minimum 1 night
        } catch (ParseException e) {
            e.printStackTrace();
            return 1; // Default to 1 night if there's an error
        }
    }

    // Method to update booking details
    public static void updateBooking(Scanner scanner) {
        System.out.println("\n--- Update Booking Details ---");

        System.out.print("Enter Booking ID to update: ");
        int bookingId = scanner.nextInt();
        System.out.print("Enter new Check-in Date (yyyy-mm-dd): ");
        String checkInDate = scanner.next();
        System.out.print("Enter new Check-out Date (yyyy-mm-dd): ");
        String checkOutDate = scanner.next();
        System.out.print("Enter new Number of Guests: ");
        int numberOfGuests = scanner.nextInt();

        // Get room ID from booking
        int roomId = getRoomIdFromBooking(bookingId);

        // Get room price per night
        double pricePerNight = getRoomPrice(roomId);

        // Calculate number of nights
        int numberOfNights = calculateNights(checkInDate, checkOutDate);

        // Calculate total amount
        double totalAmount = pricePerNight * numberOfNights;

        System.out.println("Room price per night: $" + pricePerNight);
        System.out.println("Number of nights: " + numberOfNights);
        System.out.println("New Total Amount: $" + totalAmount);

        String query = "UPDATE Bookings SET CheckInDate = ?, CheckOutDate = ?, NumberOfGuests = ?, TotalAmount = ? WHERE BookingID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, checkInDate);
            stmt.setString(2, checkOutDate);
            stmt.setInt(3, numberOfGuests);
            stmt.setDouble(4, totalAmount);
            stmt.setInt(5, bookingId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking updated successfully!");
            } else {
                System.out.println("Failed to update booking.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get room ID from booking
    private static int getRoomIdFromBooking(int bookingId) {
        String query = "SELECT RoomID FROM Bookings WHERE BookingID = ?";
        int roomId = 0;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                roomId = rs.getInt("RoomID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomId;
    }

    // Method to delete a booking
    public static void deleteBooking(Scanner scanner) {
        System.out.println("\n--- Delete Booking ---");

        System.out.print("Enter Booking ID to delete: ");
        int bookingId = scanner.nextInt();

        String query = "DELETE FROM Bookings WHERE BookingID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking deleted successfully!");
            } else {
                System.out.println("Failed to delete booking.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}