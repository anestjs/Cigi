package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;

public class EditProfessorDialog extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JComboBox<Role> roleComboBox;
    private ProfessorDaoImpl professorDao;
    private UserDaoImpl userDao;
    private Professor professor;

    public EditProfessorDialog(JFrame parent, Professor professor) {
        super(parent, "Modifier le professeur", true);
        this.professor = professor;
        professorDao = new ProfessorDaoImpl();
        userDao = new UserDaoImpl();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 250);
        setLocationRelativeTo(null);

        add(new JLabel("Prénom :"));
        firstNameField = new JTextField(professor.getUser().getFirstname());
        add(firstNameField);

        add(new JLabel("Nom :"));
        lastNameField = new JTextField(professor.getUser().getLastname());
        add(lastNameField);

        add(new JLabel("Email :"));
        emailField = new JTextField(professor.getUser().getEmail());
        add(emailField);

        // add(new JLabel("Rôle :"));
        // roleComboBox = new JComboBox<>(Role.values());
        // roleComboBox.setSelectedItem(professor.getUser().getRole());
        // add(roleComboBox);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> saveProfessor());
        add(saveButton);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void saveProfessor() {
        professor.getUser().setFirstname(firstNameField.getText());
        professor.getUser().setLastname(lastNameField.getText());
        professor.getUser().setEmail(emailField.getText());
        // professor.getUser().setRole((Role) roleComboBox.getSelectedItem());

        if (professorDao.update(professor)) {
            JOptionPane.showMessageDialog(this, "Professeur mis à jour avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Échec de la mise à jour du professeur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
