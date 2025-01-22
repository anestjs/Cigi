package main.java.com.cigiproject.ui.ensignant;

import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.dao.impl.ModuleDaoImpl;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.model.Student;
import main.java.com.cigiproject.model.Grade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NotesPanel extends JPanel {
    private ProfessorDaoImpl professorDao;
    private ClassDaoImpl classDao;
    private ModuleDaoImpl moduleDao;

    private JComboBox<Class> classComboBox;
    private JComboBox<Module> moduleComboBox;
    private JTextArea resultArea;

    public NotesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Initialize DAOs
        professorDao = new ProfessorDaoImpl();
        classDao = new ClassDaoImpl();
        moduleDao = new ModuleDaoImpl();

        // Create the input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Class selection
        inputPanel.add(new JLabel("Select Class:"));
        classComboBox = new JComboBox<>();
        loadClasses();
        inputPanel.add(classComboBox);

        // Module selection
        inputPanel.add(new JLabel("Select Module:"));
        moduleComboBox = new JComboBox<>();
        inputPanel.add(moduleComboBox);

        // Load modules when a class is selected
        classComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadModules();
            }
        });

        // Buttons for actions
        JButton viewStudentsButton = new JButton("View Students");
        JButton assignGradeButton = new JButton("Assign Grade");

        // Add action listeners to buttons
        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudents();
            }
        });

        assignGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignGrade();
            }
        });

        // Add buttons to the input panel
        inputPanel.add(viewStudentsButton);
        inputPanel.add(assignGradeButton);

        // Create the result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to the main panel
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadClasses() {
        // Use classDao.findAll() to get the list of classes
        List<Class> classes = classDao.findAll();
        for (Class clazz : classes) {
            classComboBox.addItem(clazz);
        }
    }

    private void loadModules() {
        moduleComboBox.removeAllItems();
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedClass != null) {
            List<Module> modules = moduleDao.getClassModules(selectedClass.getClass_id());
            for (Module module : modules) {
                moduleComboBox.addItem(module);
            }
        }
    }

    private void viewStudents() {
        resultArea.setText(""); // Clear the result area
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedClass != null) {
            List<Student> students = classDao.getEnrolledStudents(selectedClass.getClass_id());
            StringBuilder result = new StringBuilder("Students in Class " + selectedClass.getName() + ":\n");
            for (Student student : students) {
                result.append("CNE: ").append(student.getCne())
                      .append(", Name: ").append(student.getUser().getFirstname())
                      .append(" ").append(student.getUser().getLastname()).append("\n");
            }
            resultArea.setText(result.toString());
        }
    }

    private void assignGrade() {
        Module selectedModule = (Module) moduleComboBox.getSelectedItem();
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedModule == null || selectedClass == null) {
            resultArea.setText("Please select a class and module.");
            return;
        }

        // Get the first student in the class (for demonstration)
        List<Student> students = classDao.getEnrolledStudents(selectedClass.getClass_id());
        if (students.isEmpty()) {
            resultArea.setText("No students found in the selected class.");
            return;
        }

        Student student = students.get(0); // Assign grade to the first student
        String grade = JOptionPane.showInputDialog(this, "Enter grade for " + student.getUser().getFirstname() + ":");

        if (grade != null && !grade.isEmpty()) {
            professorDao.assignGradeToStudent(student.getCne(), selectedModule.getModule_id(), grade);
            resultArea.setText("Grade assigned successfully!");
        } else {
            resultArea.setText("Grade cannot be empty.");
        }
    }
}