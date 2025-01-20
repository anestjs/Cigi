package main.java.com.cigiproject.ui.admin;


import javax.swing.*;
import java.awt.*;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.dao.impl.ModuleDaoImpl;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;

public class AddModuleDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<Semester> semesterComboBox;
    private JComboBox<Year> yearComboBox;
    private JComboBox<Professor> professorComboBox;
    private JComboBox<Class> classComboBox;
    private ModuleDaoImpl moduleDao;
    private ProfessorDaoImpl profDao;
    private ClassDaoImpl classDao;

    public AddModuleDialog(JFrame parent) {
        super(parent, "Add Module", true);
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
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Semester:"));
        semesterComboBox = new JComboBox<>(Semester.values());
        add(semesterComboBox);

        add(new JLabel("Year:"));
        yearComboBox = new JComboBox<>(Year.values());
        add(yearComboBox);

        add(new JLabel("Professor:"));
        professorComboBox = new JComboBox<>(profDao.findAll().toArray(new Professor[0]));
        add(professorComboBox);

        add(new JLabel("Class:"));
        classComboBox = new JComboBox<>(classDao.findAll().toArray(new Class[0]));
        add(classComboBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveModule());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void saveModule() {
        String name = nameField.getText();
        Semester semester = (Semester) semesterComboBox.getSelectedItem();
        Year year = (Year) yearComboBox.getSelectedItem();
        Professor professor = (Professor) professorComboBox.getSelectedItem();
        Class classEntity = (Class) classComboBox.getSelectedItem();

        Module module = new Module();
        module.setName(name);
        module.setSemester(semester);
        module.setYear(year);
        module.setProfessor(professor);
        module.setClassEntity(classEntity);

        if (moduleDao.save(module)) {
            JOptionPane.showMessageDialog(this, "Module added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add module.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}