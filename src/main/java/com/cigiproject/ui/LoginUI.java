package main.java.com.cigiproject.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    public LoginUI() {
 
        setTitle("School Login");
        setSize(1000, 700);  
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setLayout(new BorderLayout());

 
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

 
        ImageIcon sideImg = loadImage("../../../../resources/images/side-img.png");
        Image scaledSideImg = sideImg.getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);  
        JLabel sideImgLabel = new JLabel(new ImageIcon(scaledSideImg));
        mainPanel.add(sideImgLabel, BorderLayout.WEST);

 
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(500, 700));  
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());  
        mainPanel.add(rightPanel, BorderLayout.CENTER);

 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

 
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));  
        welcomeLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(welcomeLabel, gbc);

 
        JLabel signInLabel = new JLabel("Sign in to your account");
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 16));  
        signInLabel.setForeground(new Color(0x7E7E7E));  
        gbc.gridy = 1;
        rightPanel.add(signInLabel, gbc);

 
        ImageIcon emailIcon = loadImage("../../../../resources/images/email-icon.png");
        Image scaledEmailIcon = emailIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);  
        JLabel emailLabel = new JLabel("Email:", new ImageIcon(scaledEmailIcon), SwingConstants.LEFT);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        emailLabel.setForeground(new Color(0x007EA4));  
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        rightPanel.add(emailLabel, gbc);

 
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 40));  
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));  
        emailField.setBackground(new Color(0xF5F5F5));  
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        rightPanel.add(emailField, gbc);

 
        ImageIcon passwordIcon = loadImage("../../../../resources/images/password-icon.png");
        Image scaledPasswordIcon = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);  
        JLabel passwordLabel = new JLabel("Password:", new ImageIcon(scaledPasswordIcon), SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        passwordLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(passwordLabel, gbc);

 
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));  
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));  
        passwordField.setBackground(new Color(0xF5F5F5));  
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        rightPanel.add(passwordField, gbc);

 
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 45));  
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));  
        loginButton.setBackground(new Color(0xF7941D));  
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));  
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
 
                System.out.println("Login button clicked");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(loginButton, gbc);

 
        ImageIcon googleIcon = loadImage("../../../../resources/images/google-icon.png");
        Image scaledGoogleIcon = googleIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);  
        JButton googleButton = new JButton("Continue With Google", new ImageIcon(scaledGoogleIcon));
        googleButton.setPreferredSize(new Dimension(300, 45));  
        googleButton.setFont(new Font("Arial", Font.BOLD, 14));  
        googleButton.setBackground(Color.WHITE);  
        googleButton.setForeground(new Color(0x007EA4));  
        googleButton.setFocusPainted(false);
        googleButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(10, 15, 10, 15)  
        ));
        googleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
 
                System.out.println("Google button clicked");
            }
        });
        gbc.gridy = 5;
        rightPanel.add(googleButton, gbc);

 
        add(mainPanel);

 
        setVisible(true);
    }

 
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
 
        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}