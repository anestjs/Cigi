package main.java.com.cigiproject.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {

    public RegisterUI() {
 
        setTitle("School Registration");
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
        gbc.insets = new Insets(10, 15, 10, 15);  
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

 
        JLabel welcomeLabel = new JLabel("Create an Account");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));  
        welcomeLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(welcomeLabel, gbc);

 
        JLabel signUpLabel = new JLabel("Register to get started");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 16));  
        signUpLabel.setForeground(new Color(0x7E7E7E));  
        gbc.gridy = 1;
        rightPanel.add(signUpLabel, gbc);

 
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        firstNameLabel.setForeground(new Color(0x007EA4));  
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        rightPanel.add(firstNameLabel, gbc);

 
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(280, 40));  
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 14));  
        firstNameField.setBackground(new Color(0xF5F5F5));  
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        rightPanel.add(firstNameField, gbc);

 
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        lastNameLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(lastNameLabel, gbc);

 
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(280, 40));  
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 14));  
        lastNameField.setBackground(new Color(0xF5F5F5));  
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        rightPanel.add(lastNameField, gbc);

 
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        emailLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 4;
        rightPanel.add(emailLabel, gbc);

 
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(280, 40));  
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));  
        emailField.setBackground(new Color(0xF5F5F5));  
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 4;
        rightPanel.add(emailField, gbc);

 
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        passwordLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 5;
        rightPanel.add(passwordLabel, gbc);

 
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(280, 40));  
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));  
        passwordField.setBackground(new Color(0xF5F5F5));  
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 5;
        rightPanel.add(passwordField, gbc);

 
        JLabel passwordVerificationLabel = new JLabel("Verify Password:");
        passwordVerificationLabel.setFont(new Font("Arial", Font.BOLD, 14));  
        passwordVerificationLabel.setForeground(new Color(0x007EA4));  
        gbc.gridx = 0;
        gbc.gridy = 6;
        rightPanel.add(passwordVerificationLabel, gbc);

 
        JPasswordField passwordVerificationField = new JPasswordField();
        passwordVerificationField.setPreferredSize(new Dimension(280, 40));  
        passwordVerificationField.setFont(new Font("Arial", Font.PLAIN, 14));  
        passwordVerificationField.setBackground(new Color(0xF5F5F5));  
        passwordVerificationField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1),  
            BorderFactory.createEmptyBorder(5, 10, 5, 10)  
        ));
        gbc.gridx = 1;
        gbc.gridy = 6;
        rightPanel.add(passwordVerificationField, gbc);

 
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(280, 45));  
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));  
        registerButton.setBackground(new Color(0xF7941D));  
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));  
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
 
                System.out.println("Register button clicked");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        rightPanel.add(registerButton, gbc);

 
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
 
        SwingUtilities.invokeLater(() -> new RegisterUI());
    }
}