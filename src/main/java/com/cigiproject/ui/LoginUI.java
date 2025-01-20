package main.java.com.cigiproject.ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton googleButton;

    public LoginUI() {
        // Paramètres de la fenêtre
        setTitle("Connexion à l'école");
        setSize(1000, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Image latérale
        ImageIcon sideImg = loadImage("../../../../resources/images/side-img.png"); // Chemin mis à jour
        if (sideImg != null) {
            Image scaledSideImg = sideImg.getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
            JLabel sideImgLabel = new JLabel(new ImageIcon(scaledSideImg));
            mainPanel.add(sideImgLabel, BorderLayout.WEST);
        }

        // Panneau droit (formulaire de connexion)
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(500, 700));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Contraintes de mise en page
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Titre de bienvenue
        JLabel welcomeLabel = new JLabel("Content de vous revoir !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(0x007EA4));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(welcomeLabel, gbc);

        // Sous-titre
        JLabel signInLabel = new JLabel("Connectez-vous à votre compte");
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        signInLabel.setForeground(new Color(0x7E7E7E));
        gbc.gridy = 1;
        rightPanel.add(signInLabel, gbc);

        // Champ email
        ImageIcon emailIcon = loadImage("../../../../resources/images/email-icon.png");
        if (emailIcon != null) {
            Image scaledEmailIcon = emailIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel emailLabel = new JLabel("Email :", new ImageIcon(scaledEmailIcon), SwingConstants.LEFT);
            emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
            emailLabel.setForeground(new Color(0x007EA4));
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            rightPanel.add(emailLabel, gbc);
        }

        emailField = new JTextField();
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

        // Champ mot de passe
        ImageIcon passwordIcon = loadImage("../../../../resources/images/password-icon.png");
        if (passwordIcon != null) {
            Image scaledPasswordIcon = passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel passwordLabel = new JLabel("Mot de passe :", new ImageIcon(scaledPasswordIcon), SwingConstants.LEFT);
            passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
            passwordLabel.setForeground(new Color(0x007EA4));
            gbc.gridx = 0;
            gbc.gridy = 3;
            rightPanel.add(passwordLabel, gbc);
        }

        passwordField = new JPasswordField();
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

        // Bouton de connexion
        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0xF7941D));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(loginButton, gbc);

        // Bouton Google
        ImageIcon googleIcon = loadImage("../../../../resources/images/google-icon.png");
        if (googleIcon != null) {
            Image scaledGoogleIcon = googleIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            googleButton = new JButton("Continuer avec Google", new ImageIcon(scaledGoogleIcon));
            googleButton.setPreferredSize(new Dimension(300, 45));
            googleButton.setFont(new Font("Arial", Font.BOLD, 14));
            googleButton.setBackground(Color.WHITE);
            googleButton.setForeground(new Color(0x007EA4));
            googleButton.setFocusPainted(false);
            googleButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x007EA4), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            gbc.gridy = 5;
            rightPanel.add(googleButton, gbc);
        }

        // Ajouter le panneau principal à la fenêtre
        add(mainPanel);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    // Méthode pour charger les images
    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + path);
            e.printStackTrace();
            return null;
        }
    }

    // Getters pour les composants de l'interface
    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getGoogleButton() {
        return googleButton;
    }
}