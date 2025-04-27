import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HotelManagementSystemUI extends JFrame {

    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Main menu buttons
    private JButton roomsButton;
    private JButton bookingsButton;
    private JButton guestsButton;
    private JButton exitButton;

    // Rooms panel components
    private JPanel roomsPanel;
    private JButton viewRoomsButton;
    private JButton addRoomButton;
    private JButton updateRoomButton;
    private JButton deleteRoomButton;
    private JButton backFromRoomsButton;

    // Bookings panel components
    private JPanel bookingsPanel;
    private JButton viewBookingsButton;
    private JButton createBookingButton;
    private JButton updateBookingButton;
    private JButton deleteBookingButton;
    private JButton backFromBookingsButton;

    // Guests panel components
    private JPanel guestsPanel;
    private JButton viewGuestsButton;
    private JButton addGuestButton;
    private JButton updateGuestButton;
    private JButton deleteGuestButton;
    private JButton backFromGuestsButton;

    // Content display area
    private JTextArea displayArea;
    private JScrollPane scrollPane;

    public HotelManagementSystemUI() {
        // Set up the frame
        setTitle("Hotel Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize main components
        initComponents();

        // Set up the layout
        setupLayout();

        // Add action listeners
        addActionListeners();

        // Show the frame
        setVisible(true);
    }

    private void initComponents() {
        // Initialize main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // Initialize card panel with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize main menu buttons
        roomsButton = new JButton("Rooms Management");
        bookingsButton = new JButton("Bookings Management");
        guestsButton = new JButton("Guests Management");
        exitButton = new JButton("Exit");

        // Initialize rooms panel and its components
        roomsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        viewRoomsButton = new JButton("View Available Rooms");
        addRoomButton = new JButton("Add Room");
        updateRoomButton = new JButton("Update Room");
        deleteRoomButton = new JButton("Delete Room");
        backFromRoomsButton = new JButton("Back to Main Menu");

        // Initialize bookings panel and its components
        bookingsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        viewBookingsButton = new JButton("View Bookings");
        createBookingButton = new JButton("Create Booking");
        updateBookingButton = new JButton("Update Booking");
        deleteBookingButton = new JButton("Delete Booking");
        backFromBookingsButton = new JButton("Back to Main Menu");

        // Initialize guests panel and its components
        guestsPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        viewGuestsButton = new JButton("View Guests");
        addGuestButton = new JButton("Add Guest");
        updateGuestButton = new JButton("Update Guest");
        deleteGuestButton = new JButton("Delete Guest");
        backFromGuestsButton = new JButton("Back to Main Menu");

        // Initialize display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scrollPane = new JScrollPane(displayArea);
    }

    private void setupLayout() {
        // Create main menu panel
        JPanel mainMenuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainMenuPanel.add(roomsButton);
        mainMenuPanel.add(bookingsButton);
        mainMenuPanel.add(guestsButton);
        mainMenuPanel.add(exitButton);

        // Set up rooms panel
        roomsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        roomsPanel.add(viewRoomsButton);
        roomsPanel.add(addRoomButton);
        roomsPanel.add(updateRoomButton);
        roomsPanel.add(deleteRoomButton);
        roomsPanel.add(backFromRoomsButton);

        // Set up bookings panel
        bookingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bookingsPanel.add(viewBookingsButton);
        bookingsPanel.add(createBookingButton);
        bookingsPanel.add(updateBookingButton);
        bookingsPanel.add(deleteBookingButton);
        bookingsPanel.add(backFromBookingsButton);

        // Set up guests panel
        guestsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        guestsPanel.add(viewGuestsButton);
        guestsPanel.add(addGuestButton);
        guestsPanel.add(updateGuestButton);
        guestsPanel.add(deleteGuestButton);
        guestsPanel.add(backFromGuestsButton);

        // Add panels to card panel
        cardPanel.add(mainMenuPanel, "mainMenu");
        cardPanel.add(roomsPanel, "roomsMenu");
        cardPanel.add(bookingsPanel, "bookingsMenu");
        cardPanel.add(guestsPanel, "guestsMenu");

        // Add components to main panel
        mainPanel.add(cardPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
    }

    private void addActionListeners() {
        // Main menu buttons
        roomsButton.addActionListener(e -> cardLayout.show(cardPanel, "roomsMenu"));
        bookingsButton.addActionListener(e -> cardLayout.show(cardPanel, "bookingsMenu"));
        guestsButton.addActionListener(e -> cardLayout.show(cardPanel, "guestsMenu"));
        exitButton.addActionListener(e -> System.exit(0));

        // Rooms panel buttons
        backFromRoomsButton.addActionListener(e -> cardLayout.show(cardPanel, "mainMenu"));
        viewRoomsButton.addActionListener(e -> viewRooms());
        addRoomButton.addActionListener(e -> addRoom());
        updateRoomButton.addActionListener(e -> updateRoom());
        deleteRoomButton.addActionListener(e -> deleteRoom());

        // Bookings panel buttons
        backFromBookingsButton.addActionListener(e -> cardLayout.show(cardPanel, "mainMenu"));
        viewBookingsButton.addActionListener(e -> viewBookings());
        createBookingButton.addActionListener(e -> createBooking());
        updateBookingButton.addActionListener(e -> updateBooking());
        deleteBookingButton.addActionListener(e -> deleteBooking());

        // Guests panel buttons
        backFromGuestsButton.addActionListener(e -> cardLayout.show(cardPanel, "mainMenu"));
        viewGuestsButton.addActionListener(e -> viewGuests());
        addGuestButton.addActionListener(e -> addGuest());
        updateGuestButton.addActionListener(e -> updateGuest());
        deleteGuestButton.addActionListener(e -> deleteGuest());
    }

    // Utility method to capture console output from existing methods
    private String captureOutput(Runnable method) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        method.run();

        System.out.flush();
        System.setOut(old);

        return baos.toString();
    }

    // Room management methods
    private void viewRooms() {
        displayArea.setText("Viewing all available rooms...\n\n");

        // Capture the output from the original method
        String result = captureOutput(() -> Rooms.viewRooms());
        displayArea.append(result);
    }

    private void addRoom() {
        JTextField roomNumberField = new JTextField();
        JTextField roomTypeField = new JTextField();
        JTextField priceField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Room Number:"));
        inputPanel.add(roomNumberField);
        inputPanel.add(new JLabel("Room Type:"));
        inputPanel.add(roomTypeField);
        inputPanel.add(new JLabel("Price per Night:"));
        inputPanel.add(priceField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Add New Room", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String roomNumberStr = roomNumberField.getText();
                String roomType = roomTypeField.getText();
                String priceStr = priceField.getText();

                // Create a simulated scanner with input values
                String input = roomNumberStr + "\n" + roomType + "\n" + priceStr + "\n";
                Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                // Capture the output from the add method
                displayArea.setText("Adding new room...\n\n");
                String output = captureOutput(() -> Rooms.addRoom(scanner));
                displayArea.append(output);

                scanner.close();
            } catch (Exception e) {
                displayArea.setText("Error adding room: " + e.getMessage() + "\n");
            }
        }
    }

    private void updateRoom() {
        String roomNumber = JOptionPane.showInputDialog(this,
                "Enter Room Number to update:", "Update Room", JOptionPane.QUESTION_MESSAGE);

        if (roomNumber != null && !roomNumber.isEmpty()) {
            try {
                JTextField roomTypeField = new JTextField();
                JTextField priceField = new JTextField();
                JCheckBox availableCheckBox = new JCheckBox("Available", true);

                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Room Type:"));
                inputPanel.add(roomTypeField);
                inputPanel.add(new JLabel("Price per Night:"));
                inputPanel.add(priceField);
                inputPanel.add(new JLabel("Availability:"));
                inputPanel.add(availableCheckBox);

                int result = JOptionPane.showConfirmDialog(this, inputPanel,
                        "Update Room " + roomNumber, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String roomType = roomTypeField.getText();
                    String price = priceField.getText();
                    String availability = availableCheckBox.isSelected() ? "available" : "not available";

                    // Create a simulated scanner with input values
                    String input = roomNumber + "\n" + roomType + "\n" + price + "\n" + availability + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the update method
                    displayArea.setText("Updating room " + roomNumber + "...\n\n");
                    String output = captureOutput(() -> Rooms.updateRoom(scanner));
                    displayArea.append(output);

                    scanner.close();
                }
            } catch (Exception e) {
                displayArea.setText("Error updating room: " + e.getMessage() + "\n");
            }
        }
    }

    private void deleteRoom() {
        String roomNumber = JOptionPane.showInputDialog(this,
                "Enter Room Number to delete:", "Delete Room", JOptionPane.QUESTION_MESSAGE);

        if (roomNumber != null && !roomNumber.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Room " + roomNumber + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Create a simulated scanner with input values
                    String input = roomNumber + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the delete method
                    displayArea.setText("Deleting room " + roomNumber + "...\n\n");
                    String output = captureOutput(() -> Rooms.deleteRoom(scanner));
                    displayArea.append(output);

                    scanner.close();
                } catch (Exception e) {
                    displayArea.setText("Error deleting room: " + e.getMessage() + "\n");
                }
            }
        }
    }

    // Booking management methods
    private void viewBookings() {
        displayArea.setText("Viewing all bookings...\n\n");

        // Capture the output from the original method
        String result = captureOutput(() -> Bookings.viewBookings());
        displayArea.append(result);
    }

    private void createBooking() {
        JTextField roomNumberField = new JTextField();
        JTextField guestIdField = new JTextField();
        JTextField checkInField = new JTextField("YYYY-MM-DD");
        JTextField checkOutField = new JTextField("YYYY-MM-DD");

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Room Number:"));
        inputPanel.add(roomNumberField);
        inputPanel.add(new JLabel("Guest ID:"));
        inputPanel.add(guestIdField);
        inputPanel.add(new JLabel("Check-in Date:"));
        inputPanel.add(checkInField);
        inputPanel.add(new JLabel("Check-out Date:"));
        inputPanel.add(checkOutField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Create New Booking", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String roomNumber = roomNumberField.getText();
                String guestId = guestIdField.getText();
                String checkIn = checkInField.getText();
                String checkOut = checkOutField.getText();

                // Create a simulated scanner with input values
                String input = roomNumber + "\n" + guestId + "\n" + checkIn + "\n" + checkOut + "\n";
                Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                // Capture the output from the create method
                displayArea.setText("Creating new booking...\n\n");
                String output = captureOutput(() -> Bookings.createBooking(scanner));
                displayArea.append(output);

                scanner.close();
            } catch (Exception e) {
                displayArea.setText("Error creating booking: " + e.getMessage() + "\n");
            }
        }
    }

    private void updateBooking() {
        String bookingId = JOptionPane.showInputDialog(this,
                "Enter Booking ID to update:", "Update Booking", JOptionPane.QUESTION_MESSAGE);

        if (bookingId != null && !bookingId.isEmpty()) {
            try {
                JTextField roomNumberField = new JTextField();
                JTextField guestIdField = new JTextField();
                JTextField checkInField = new JTextField("YYYY-MM-DD");
                JTextField checkOutField = new JTextField("YYYY-MM-DD");

                JPanel inputPanel = new JPanel(new GridLayout(4, 2));
                inputPanel.add(new JLabel("Room Number:"));
                inputPanel.add(roomNumberField);
                inputPanel.add(new JLabel("Guest ID:"));
                inputPanel.add(guestIdField);
                inputPanel.add(new JLabel("Check-in Date:"));
                inputPanel.add(checkInField);
                inputPanel.add(new JLabel("Check-out Date:"));
                inputPanel.add(checkOutField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel,
                        "Update Booking " + bookingId, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String roomNumber = roomNumberField.getText();
                    String guestId = guestIdField.getText();
                    String checkIn = checkInField.getText();
                    String checkOut = checkOutField.getText();

                    // Create a simulated scanner with input values
                    String input = bookingId + "\n" + roomNumber + "\n" + guestId + "\n" + checkIn + "\n" + checkOut + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the update method
                    displayArea.setText("Updating booking " + bookingId + "...\n\n");
                    String output = captureOutput(() -> Bookings.updateBooking(scanner));
                    displayArea.append(output);

                    scanner.close();
                }
            } catch (Exception e) {
                displayArea.setText("Error updating booking: " + e.getMessage() + "\n");
            }
        }
    }

    private void deleteBooking() {
        String bookingId = JOptionPane.showInputDialog(this,
                "Enter Booking ID to delete:", "Delete Booking", JOptionPane.QUESTION_MESSAGE);

        if (bookingId != null && !bookingId.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Booking " + bookingId + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Create a simulated scanner with input values
                    String input = bookingId + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the delete method
                    displayArea.setText("Deleting booking " + bookingId + "...\n\n");
                    String output = captureOutput(() -> Bookings.deleteBooking(scanner));
                    displayArea.append(output);

                    scanner.close();
                } catch (Exception e) {
                    displayArea.setText("Error deleting booking: " + e.getMessage() + "\n");
                }
            }
        }
    }

    // Guest management methods
    private void viewGuests() {
        displayArea.setText("Viewing all guests...\n\n");

        // Capture the output from the original method
        String result = captureOutput(() -> Guests.viewGuests());
        displayArea.append(result);
    }

    private void addGuest() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Guest Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email Address:"));
        inputPanel.add(emailField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Add New Guest", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                // Create a simulated scanner with input values
                String input = name + "\n" + phone + "\n" + email + "\n";
                Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                // Capture the output from the add method
                displayArea.setText("Adding new guest...\n\n");
                String output = captureOutput(() -> Guests.addGuest(scanner));
                displayArea.append(output);

                scanner.close();
            } catch (Exception e) {
                displayArea.setText("Error adding guest: " + e.getMessage() + "\n");
            }
        }
    }

    private void updateGuest() {
        String guestId = JOptionPane.showInputDialog(this,
                "Enter Guest ID to update:", "Update Guest", JOptionPane.QUESTION_MESSAGE);

        if (guestId != null && !guestId.isEmpty()) {
            try {
                JTextField nameField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField emailField = new JTextField();

                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Name:"));
                inputPanel.add(nameField);
                inputPanel.add(new JLabel("Phone:"));
                inputPanel.add(phoneField);
                inputPanel.add(new JLabel("Email:"));
                inputPanel.add(emailField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel,
                        "Update Guest " + guestId, JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String phone = phoneField.getText();
                    String email = emailField.getText();

                    // Create a simulated scanner with input values
                    String input = guestId + "\n" + name + "\n" + phone + "\n" + email + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the update method
                    displayArea.setText("Updating guest " + guestId + "...\n\n");
                    String output = captureOutput(() -> Guests.updateGuest(scanner));
                    displayArea.append(output);

                    scanner.close();
                }
            } catch (Exception e) {
                displayArea.setText("Error updating guest: " + e.getMessage() + "\n");
            }
        }
    }

    private void deleteGuest() {
        String guestId = JOptionPane.showInputDialog(this,
                "Enter Guest ID to delete:", "Delete Guest", JOptionPane.QUESTION_MESSAGE);

        if (guestId != null && !guestId.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete Guest " + guestId + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Create a simulated scanner with input values
                    String input = guestId + "\n";
                    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

                    // Capture the output from the delete method
                    displayArea.setText("Deleting guest " + guestId + "...\n\n");
                    String output = captureOutput(() -> Guests.deleteGuest(scanner));
                    displayArea.append(output);

                    scanner.close();
                } catch (Exception e) {
                    displayArea.setText("Error deleting guest: " + e.getMessage() + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create and display the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new HotelManagementSystemUI();
        });
    }
}