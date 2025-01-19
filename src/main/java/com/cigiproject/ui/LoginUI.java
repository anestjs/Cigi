package main.java.com.cigiproject.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    public LoginUI() {
        // Frame setup
        setTitle("School Login");
        setSize(1000, 700); // Match the size of CIGIDashboard (1000x700)
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Left side image (wider)
        ImageIcon sideImg = loadImage("../../../../resources/images/side-img.png");
        Image scaledSideImg = sideImg.getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH); // Wider image (600x700)
        JLabel sideImgLabel = new JLabel(new ImageIcon(scaledSideImg));
        mainPanel.add(sideImgLabel, BorderLayout.WEST);

        // Right side panel (narrower)
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(500, 700)); // Narrower content area (400x700)
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for better structure
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Larger font size
        welcomeLabel.setForeground(new Color(0x007EA4)); // Blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(welcomeLabel, gbc);

        // Sign in label
        JLabel signInLabel = new JLabel("Sign in to your account");
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Slightly larger font size
        signInLabel.setForeground(new Color(0x7E7E7E)); // Gray
        gbc.gridy = 1;
        rightPanel.add(signInLabel, gbc);

        // Email label
        ImageIcon emailIcon = loadImage("../../../../resources/images/email-icon.png");
        Image scaledEmailIcon = emailIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Smaller icon
        JLabel emailLabel = new JLabel("Email:", new ImageIcon(scaledEmailIcon), SwingConstants.LEFT);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Smaller font size
        emailLabel.setForeground(new Color(0x007EA4)); // Blue
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        rightPanel.add(emailLabel, gbc);

        // Email text field
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 40)); // Adjusted width to fit narrower panel
        emailField.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size
        emailField.setBackground(new Color(0xF5F5F5)); // Light gray
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1), // Blue border
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        rightPanel.add(emailField, gbc);

        // Password label
        ImageIcon passwordIcon = loadImage("../../../../resources/images/password-icon.png");
        Image scaledPasswordIcon = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Smaller icon
        JLabel passwordLabel = new JLabel("Password:", new ImageIcon(scaledPasswordIcon), SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Smaller font size
        passwordLabel.setForeground(new Color(0x007EA4)); // Blue
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(passwordLabel, gbc);

        // Password text field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40)); // Adjusted width to fit narrower panel
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size
        passwordField.setBackground(new Color(0xF5F5F5)); // Light gray
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1), // Blue border
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        rightPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 45)); // Adjusted width to fit narrower panel
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font size
        loginButton.setBackground(new Color(0xF7941D)); // Orange
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Add padding
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add login logic here
                System.out.println("Login button clicked");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(loginButton, gbc);

        // Google button
        ImageIcon googleIcon = loadImage("../../../../resources/images/google-icon.png");
        Image scaledGoogleIcon = googleIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Smaller icon
        JButton googleButton = new JButton("Continue With Google", new ImageIcon(scaledGoogleIcon));
        googleButton.setPreferredSize(new Dimension(300, 45)); // Adjusted width to fit narrower panel
        googleButton.setFont(new Font("Arial", Font.BOLD, 14)); // Smaller font size
        googleButton.setBackground(Color.WHITE); // White background
        googleButton.setForeground(new Color(0x007EA4)); // Blue text
        googleButton.setFocusPainted(false);
        googleButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1), // Blue border
            BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding
        ));
        googleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add Google login logic here
                System.out.println("Google button clicked");
            }
        });
        gbc.gridy = 5;
        rightPanel.add(googleButton, gbc);

        // Add the main panel to the frame
        add(mainPanel);

        // Make the window visible
        setVisible(true);
    }

    // Helper method to load images from the classpath
    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}