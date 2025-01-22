package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.dao.impl.StudentDaoImpl;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;

import java.util.List;
import java.awt.geom.RoundRectangle2D;

public class StudentsPanel extends JPanel {
    private static final Color UMI_BLUE = new Color(0, 127, 163);
    private static final Color UMI_ORANGE = new Color(255, 140, 0);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TABLE_HEADER_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14);

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Class> allClasses;
    private List<Student> currentStudents;
    private boolean showingClasses = true;
    private Class selectedClass;
    private JPanel contentPanel;
    private ClassDaoImpl classDao;
    private StudentDaoImpl studentDao;

    public StudentsPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        classDao = new ClassDaoImpl();
        studentDao = new StudentDaoImpl();

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        add(contentPanel, BorderLayout.CENTER);

        showClassesTable();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Gestion des classes");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(UMI_BLUE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        return headerPanel;
    }

    private void showClassesTable() {
        contentPanel.removeAll();
        showingClasses = true;

        // Modèle de table pour les classes
        String[] columnNames = {"ID de classe", "Nom de classe", "Nombre d'étudiants"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        // Écouteur de double-clic pour voir les étudiants
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        selectedClass = allClasses.get(selectedRow);
                        showStudentsTable(selectedClass);
                    }
                }
            }
        });

        // Remplir la table des classes
        populateClassesTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UMI_BLUE, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showStudentsTable(Class selectedClass) {
        contentPanel.removeAll();
        showingClasses = false;

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(BACKGROUND_COLOR);

        JButton backButton = createStyledButton("Retour", UMI_BLUE);
        // backButton.setSize(WIDTH, HEIGHT);
        backButton.addActionListener(e -> showClassesTable());
        topPanel.add(backButton);

        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Modèle de table pour les étudiants
        String[] columnNames = {"CNE", "Prénom", "Nom", "Année"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JPanel actionPanel = createStudentActionPanel();
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Remplir la table des étudiants
        populateStudentsTable(selectedClass.getClass_id());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UMI_BLUE, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createStudentActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(BACKGROUND_COLOR);
        actionPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton addButton = createStyledButton("Ajouter étudiant", UMI_BLUE);
        addButton.addActionListener(e -> {
            AddStudentDialog dialog = new AddStudentDialog((JFrame) SwingUtilities.getWindowAncestor(this), selectedClass.getClass_id());
            dialog.setVisible(true);
            populateStudentsTable(selectedClass.getClass_id());
        });

        JButton editButton = createStyledButton("Modifier", UMI_ORANGE);
        JButton deleteButton = createStyledButton("Supprimer", Color.RED);

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int studentCne = (int) tableModel.getValueAt(selectedRow, 0);
                Student selectedStudent = currentStudents.stream()
                        .filter(student -> student.getCne() == studentCne)
                        .findFirst()
                        .orElse(null);
                if (selectedStudent != null) {
                    EditStudentDialog dialog = new EditStudentDialog((JFrame) SwingUtilities.getWindowAncestor(StudentsPanel.this), selectedStudent);
                    dialog.setVisible(true);
                    populateStudentsTable(selectedClass.getClass_id()); // Rafraîchir la table après modification
                }
            } else {
                JOptionPane.showMessageDialog(StudentsPanel.this, "Veuillez sélectionner un étudiant à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int studentCne = (int) tableModel.getValueAt(selectedRow, 0);
                
                // Confirmer avant de supprimer
                int confirm = JOptionPane.showConfirmDialog(StudentsPanel.this, 
                    "Êtes-vous sûr de vouloir supprimer cet étudiant ?", 
                    "Confirmer la suppression", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
                    boolean deleted = studentDaoImpl.delete(studentCne);  // Assurez-vous que la méthode delete prend CNE en paramètre
                    if (!deleted) {
                        JOptionPane.showMessageDialog(StudentsPanel.this, 
                            "Erreur lors de la suppression de l'étudiant.", 
                            "Erreur", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                    populateStudentsTable(selectedClass.getClass_id()); // Rafraîchir la table après suppression
                }
            } else {
                JOptionPane.showMessageDialog(StudentsPanel.this, 
                    "Veuillez sélectionner un étudiant à supprimer.", 
                    "Avertissement", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    private void populateClassesTable() {
        tableModel.setRowCount(0);
        allClasses = classDao.findAll();

        for (Class cls : allClasses) {
            Object[] rowData = {
                cls.getClass_id(),
                cls.getName(),
                cls.getStudent_count() // Ajouter le champ de département manquant
            };
            tableModel.addRow(rowData);
        }
    }

    private void populateStudentsTable(Integer classId) {
        tableModel.setRowCount(0);
        currentStudents = classDao.getEnrolledStudents(classId); // Assurez-vous que StudentDaoImpl a cette méthode

        for (Student student : currentStudents) {
            Object[] rowData = {
                student.getCne(),
                student.getUser().getFirstname(),
                student.getUser().getLastname(),
                student.getYear()
            };
            tableModel.addRow(rowData);
        }
    }

    private void styleTable(JTable table) {
        table.setFont(TABLE_FONT);
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(UMI_BLUE.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(UMI_BLUE);
        header.setFont(TABLE_FONT.deriveFont(Font.BOLD));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(TABLE_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }
}
