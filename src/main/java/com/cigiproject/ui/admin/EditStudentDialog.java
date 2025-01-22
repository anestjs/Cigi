package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.dao.impl.StudentDaoImpl;

public class EditStudentDialog extends JDialog {
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField emailField;
    private JComboBox<Year> yearComboBox;
    private StudentDaoImpl studentDao;
    private UserDaoImpl userDao;
    private Student student;

    private static final Color UMI_ORANGE = new Color(255, 128, 0); // Couleur exemple

    public EditStudentDialog(JFrame parent, Student student) {
        super(parent, "Modifier l'étudiant", true);
        this.student = student;
        studentDao = new StudentDaoImpl();
        userDao = new UserDaoImpl();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 250);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE); // Fond blanc pour la boîte de dialogue

        // Étiquettes et champs
        add(createLabel("Prénom :"));
        firstnameField = createStyledTextField(student.getUser().getFirstname());
        add(firstnameField);

        add(createLabel("Nom :"));
        lastnameField = createStyledTextField(student.getUser().getLastname());
        add(lastnameField);

        add(createLabel("Email :"));
        emailField = createStyledTextField(student.getUser().getEmail());
        add(emailField);

        add(createLabel("Année :"));
        yearComboBox = createStyledComboBox(Year.values());
        yearComboBox.setSelectedItem(student.getYear());
        add(yearComboBox);

        // Boutons
        JButton saveButton = createStyledButton("Enregistrer", UMI_ORANGE);
        saveButton.addActionListener(e -> saveStudent());
        add(saveButton);

        JButton cancelButton = createStyledButton("Annuler", UMI_ORANGE);
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(UMI_ORANGE);
        return label;
    }

    private JTextField createStyledTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(150, 30));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        return textField;
    }

    private JComboBox<Year> createStyledComboBox(Year[] items) {
        JComboBox<Year> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(150, 30));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        return comboBox;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private void saveStudent() {
        // Mettre à jour les informations de l'utilisateur
        student.getUser().setFirstname(firstnameField.getText());
        student.getUser().setLastname(lastnameField.getText());
        student.getUser().setEmail(emailField.getText());

        // Mettre à jour l'année de l'étudiant
        student.setYear((Year) yearComboBox.getSelectedItem());

        // Enregistrer l'utilisateur et l'étudiant
        if (userDao.updateStudent(student.getUser()) && studentDao.update(student)) {
            JOptionPane.showMessageDialog(this, "L'étudiant a été mis à jour avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la mise à jour de l'étudiant.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
