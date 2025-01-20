package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.dao.impl.ModuleDaoImpl;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;

public class EditModuleDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<Semester> semesterComboBox;
    private JComboBox<Year> yearComboBox;
    private JComboBox<Professor> professorComboBox;
    private JComboBox<Class> classComboBox;
    private ModuleDaoImpl moduleDao;
    private Module module;
    private ProfessorDaoImpl profDao;
    private ClassDaoImpl classDao;

    public EditModuleDialog(JFrame parent, Module module) {
        super(parent, "Edit Module", true);
        this.module = module;
        moduleDao = new ModuleDaoImpl();
        profDao = new ProfessorDaoImpl();
        classDao = new ClassDaoImpl();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Name:"));
        nameField = new JTextField(module.getName());
        add(nameField);

        add(new JLabel("Semester:"));
        semesterComboBox = new JComboBox<>(Semester.values());
        semesterComboBox.setSelectedItem(module.getSemester());
        add(semesterComboBox);

        add(new JLabel("Year:"));
        yearComboBox = new JComboBox<>(Year.values());
        yearComboBox.setSelectedItem(module.getYear());
        add(yearComboBox);

        add(new JLabel("Professor:"));
        professorComboBox = new JComboBox<>(profDao.findAll().toArray(new Professor[0]));
        professorComboBox.setSelectedItem(module.getProfessor());
        add(professorComboBox);

        add(new JLabel("Class:"));
        classComboBox = new JComboBox<>(classDao.findAll().toArray(new Class[0]));
        classComboBox.setSelectedItem(module.getClassEntity());
        add(classComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveModule());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void saveModule() {
        module.setName(nameField.getText());
        module.setSemester((Semester) semesterComboBox.getSelectedItem());
        module.setYear((Year) yearComboBox.getSelectedItem());
        module.setProfessor((Professor) professorComboBox.getSelectedItem());
        module.setClassEntity((Class) classComboBox.getSelectedItem());

        if (moduleDao.update(module)) {
            JOptionPane.showMessageDialog(this, "Module updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update module.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}