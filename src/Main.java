import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // First handle the login using Swing instead of console
        SwingUtilities.invokeLater(() -> {
            showLoginDialog();
        });
    }

    private static void showLoginDialog() {
        // Create a login dialog
        JFrame loginFrame = new JFrame("Hotel Management System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLocationRelativeTo(null);

        // Use BorderLayout for the main frame
        loginFrame.setLayout(new BorderLayout());

        // Create a panel for input fields with GridLayout
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Create components
        JLabel staffIdLabel = new JLabel("Staff ID:");
        JTextField staffIdField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Add input components to panel
        inputPanel.add(staffIdLabel);
        inputPanel.add(staffIdField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Create a panel for the login button with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // Create a full-width login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(360, 30));
        buttonPanel.add(loginButton);

        // Create a panel for the registration link with FlowLayout
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Create hyperlink for staff registration
        JLabel createAccountLink = new JLabel("Create New Staff Account");
        createAccountLink.setForeground(Color.BLUE);
        createAccountLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountLink.setFont(new Font(createAccountLink.getFont().getName(), Font.PLAIN, 12));


        linkPanel.add(createAccountLink);

        // Add panels to frame
        loginFrame.add(inputPanel, BorderLayout.NORTH);
        loginFrame.add(buttonPanel, BorderLayout.CENTER);
        loginFrame.add(linkPanel, BorderLayout.SOUTH);

        // Add login button action
        loginButton.addActionListener(e -> {
            try {
                int staffId = Integer.parseInt(staffIdField.getText());
                String password = new String(passwordField.getPassword());

                boolean isAuthenticated = loginAuth.authenticate(staffId, password);

                if (isAuthenticated) {
                    loginFrame.dispose(); // Close the login window

                    // Launch the main application with dynamic data handling
                    SwingUtilities.invokeLater(() -> {
                        new HotelManagementSystemUI();
                    });
                } else {
                    JOptionPane.showMessageDialog(loginFrame,
                            "Invalid Staff ID or Password. Please try again.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(loginFrame,
                        "Please enter a valid Staff ID (numeric value).",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add click event for the create account link
        createAccountLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open staff registration form
                showStaffRegistrationDialog(loginFrame);
            }
        });

        // Show the login frame
        loginFrame.setVisible(true);
    }

    private static void showStaffRegistrationDialog(JFrame parentFrame) {
        // Create a dialog for staff registration
        JDialog registrationDialog = new JDialog(parentFrame, "Create New Staff Account", true);
        registrationDialog.setSize(450, 400);
        registrationDialog.setLocationRelativeTo(parentFrame);
        registrationDialog.setLayout(new BorderLayout());

        // Create a panel for input fields with GridLayout
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Create components
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Manager", "Receptionist", "Housekeeper", "Maintenance"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        // Add components to panel
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(roleLabel);
        inputPanel.add(roleComboBox);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Create buttons
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        // Set button sizes
        Dimension buttonSize = new Dimension(120, 30);
        registerButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        // Add buttons to panel
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // Add panels to dialog
        registrationDialog.add(inputPanel, BorderLayout.CENTER);
        registrationDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Add register button action
        registerButton.addActionListener(e -> {
            // Validate input fields
            if (firstNameField.getText().trim().isEmpty() ||
                    lastNameField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty() ||
                    passwordField.getPassword().length == 0) {

                JOptionPane.showMessageDialog(registrationDialog,
                        "All fields are required. Please complete the form.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get form data
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            // Add staff to database
            boolean success = Staff.addStaff(firstName, lastName, phone, email, password, role);

            if (success) {
                JOptionPane.showMessageDialog(registrationDialog,
                        "Staff account created successfully.\nPlease note your Staff ID to login.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                registrationDialog.dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(registrationDialog,
                        "Failed to create staff account. Please try again.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add cancel button action
        cancelButton.addActionListener(e -> registrationDialog.dispose());

        // Show the dialog
        registrationDialog.setVisible(true);
    }
}