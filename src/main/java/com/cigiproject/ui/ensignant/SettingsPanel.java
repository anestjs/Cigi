package main.java.com.cigiproject.ui.ensignant;

import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.SessionService;
import main.java.com.cigiproject.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class SettingsPanel extends JPanel {
    private UserDaoImpl userDao;
    private User currentUser;

    private JTextField emailField;
    private JPasswordField passwordField;

    public SettingsPanel() {
        SessionService sessionService = new SessionService();
        SessionService.Session activeSession = sessionService.getSession();
        int userId = activeSession.getId();

        ProfessorDaoImpl professorDao = new ProfessorDaoImpl();
        Optional<Professor> thisprof = professorDao.findByUser_UserId(userId);

        this.currentUser = thisprof.get().getUser();
        this.userDao = new UserDaoImpl();

        setBackground(new Color(245, 245, 245)); // Light gray background
        setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 126, 164)); // Blue background
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel titleLabel = new JLabel("Paramètres du Compte");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // White text
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Create the form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.anchor = GridBagConstraints.WEST;

        // Email field
        JLabel emailLabel = new JLabel("Nouvel Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField = new JTextField(currentUser.getEmail(), 20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light gray border
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Nouveau Mot de Passe:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light gray border
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Save button
        JButton saveButton = new JButton("Enregistrer les Modifications");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 126, 164)); // Blue color
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(saveButton);

        // Add components to the panel
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveChanges() {
        String newEmail = emailField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();

        if (newEmail.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "L'email et le mot de passe ne peuvent pas être vides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserService userservice = new UserService(userDao);
        newPassword = userservice.hashPassword(newPassword);

        // Update the user object
        currentUser.setEmail(newEmail);
        currentUser.setPassword(newPassword);

        // Call the DAO to update the user in the database
        boolean isUpdated = userDao.update_user_setting(currentUser);

        if (isUpdated) {
            JOptionPane.showMessageDialog(this, "Paramètres mis à jour avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la mise à jour des paramètres.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}