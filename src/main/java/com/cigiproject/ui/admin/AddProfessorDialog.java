package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;

public class AddProfessorDialog extends JDialog {
    private static final Color UMI_BLUE = new Color(0, 127, 163);
    private static final Color UMI_ORANGE = new Color(255, 140, 0);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private ProfessorDaoImpl professorDao;
    private UserDaoImpl userDao;

    public AddProfessorDialog(JFrame parent) {
        super(parent, "Ajouter un professeur", true);
        professorDao = new ProfessorDaoImpl();
        userDao = new UserDaoImpl();
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Name Field
        firstNameField = createStyledTextField();
        addFormRow(formPanel, "Prénom:", firstNameField, 0, gbc);

        // Last Name Field
        lastNameField = createStyledTextField();
        addFormRow(formPanel, "Nom:", lastNameField, 1, gbc);

        // Email Field
        emailField = createStyledTextField();
        addFormRow(formPanel, "Email:", emailField, 2, gbc);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setFont(INPUT_FONT);
        passwordField.setPreferredSize(new Dimension(200, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UMI_BLUE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        addFormRow(formPanel, "Mot de passe:", passwordField, 3, gbc);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton saveButton = createStyledButton("Enregistrer", UMI_BLUE);
        JButton cancelButton = createStyledButton("Annuler", Color.GRAY);

        saveButton.addActionListener(e -> saveProfessor());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void addFormRow(JPanel panel, String labelText, JComponent component, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        
        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(UMI_BLUE);
        panel.add(label, gbc);

        // Component
        gbc.gridx = 1;
        gbc.weightx = 0.9;
        panel.add(component, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(INPUT_FONT);
        textField.setPreferredSize(new Dimension(200, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UMI_BLUE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };

        button.setFont(INPUT_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void saveProfessor() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate input fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showStyledMessage("Veuillez remplir tous les champs.", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new User object
        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.Professor); // Set role to PROFESSOR by default

        // Save the User
        boolean isUserSaved = userDao.save(user);
        if (!isUserSaved) {
            showStyledMessage("Échec de l'enregistrement de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new Professor object
        Professor professor = new Professor();
        professor.setUser(user);

        // Save the Professor
        boolean isProfessorSaved = professorDao.save(professor);
        if (isProfessorSaved) {
            showStyledMessage("Professeur ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showStyledMessage("Échec de l'enregistrement du professeur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showStyledMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", BACKGROUND_COLOR);
        UIManager.put("Panel.background", BACKGROUND_COLOR);
        UIManager.put("OptionPane.messageForeground", UMI_BLUE);
        UIManager.put("OptionPane.messageFont", LABEL_FONT);
        UIManager.put("OptionPane.buttonFont", INPUT_FONT);
        
        JOptionPane.showMessageDialog(
            this,
            message,
            title,
            messageType
        );
    }
}
