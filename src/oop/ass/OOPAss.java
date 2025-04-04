package oop.ass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

// Base User Class
class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String role;

    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

// Admin, Seller, Customer Classes
class Admin extends User {
    public Admin(int userId, String username, String password) {
        super(userId, username, password, "Admin");
    }
}

class Seller extends User {
    public Seller(int userId, String username, String password) {
        super(userId, username, password, "Seller");
    }
}

class Customer extends User {
    public Customer(int userId, String username, String password) {
        super(userId, username, password, "Customer");
    }
}

// Authentication System
class AuthenticationSystem {
    private Map<String, User> users = new HashMap<>();
    private int userIdCounter = 1;

    public void registerUser(String username, String password, String role) {
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists! Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser;
        switch (role.toLowerCase()) {
            case "admin":
                newUser = new Admin(userIdCounter++, username, password);
                break;
            case "seller":
                newUser = new Seller(userIdCounter++, username, password);
                break;
            case "customer":
                newUser = new Customer(userIdCounter++, username, password);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid role! Choose Admin, Seller, or Customer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        users.put(username, newUser);
        JOptionPane.showMessageDialog(null, role + " registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public User login(String username, String password) {
        if (!users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "User not found! Please register first.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null, "Incorrect password! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + user.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
        return user;
    }
}

// GUI Class
public class OOPAss extends JFrame {
    private AuthenticationSystem auth;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleDropdown;

    public OOPAss() {
        auth = new AuthenticationSystem();

        setTitle("E-Commerce System - Authentication");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Panel for form fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title Label
        JLabel titleLabel = new JLabel("E-Commerce Authentication");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        // Role Selection
        JLabel roleLabel = new JLabel("Role:");
        roleDropdown = new JComboBox<>(new String[]{"Admin", "Seller", "Customer"});

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Button Styling
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(50, 200, 100));
        registerButton.setForeground(Color.WHITE);

        // Add action listeners
        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(new RegisterAction());

        // Layout adjustments
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);
        gbc.gridx = 1;
        panel.add(roleDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
   
    // Login Action
    private class LoginAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            auth.login(username, password);
        }
    }

    // Register Action
    private class RegisterAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleDropdown.getSelectedItem();
            auth.registerUser(username, password, role);
        }
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(OOPAss::new);
    }
}