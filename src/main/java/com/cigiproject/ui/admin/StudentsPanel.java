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
import main.java.com.cigiproject.services.*;
import java.util.List;
import java.awt.geom.Rectangle2D;
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

        JLabel titleLabel = new JLabel("Classes Management");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(UMI_BLUE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        return headerPanel;
    }

    private void showClassesTable() {
        contentPanel.removeAll();
        showingClasses = true;

        // Create the table model for classes
        String[] columnNames = {"Class ID", "Class Name", "Department"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        // Add double-click listener
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

        // Populate classes table
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

        // Create back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(BACKGROUND_COLOR);
        JButton backButton = createStyledButton("Back to Classes", UMI_BLUE);
        backButton.addActionListener(e -> showClassesTable());
        topPanel.add(backButton);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Create the table model for students
        String[] columnNames = {"CNE", "First Name", "Last Name", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        // Add action buttons
        JPanel actionPanel = createStudentActionPanel();
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Populate students table
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

        JButton addButton = createStyledButton("Add Student", UMI_BLUE);
        
        addButton.addActionListener(e -> {
            // AddStudentDialog dialog = new AddStudentDialog((JFrame) SwingUtilities.getWindowAncestor(StudentsPanel.this));
            dialog.setVisible(true);
            // Refresh the table after adding a professor
            populateClassesTable();
        });
        
        // int selectedClassId = getSelectedClassId(); // Get selected class ID
        // if (selectedClassId != -1) {
        //     AddStudentDialog dialog = new AddStudentDialog(parentFrame, selectedClassId);
        //     dialog.setVisible(true);
        // }


        JButton editButton = createStyledButton("Edit", UMI_ORANGE);
        JButton deleteButton = createStyledButton("Delete", Color.RED);

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
                // cls.getDepartment()
            };
            tableModel.addRow(rowData);
        }
    }

    private void populateStudentsTable(Integer classId) {
        tableModel.setRowCount(0);
        currentStudents = classDao.getEnrolledStudents(classId);
        
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

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(TABLE_FONT);
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

                // Paint rounded background
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

                // Paint text
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(text, g2);
                int x = (getWidth() - (int) r.getWidth()) / 2;
                int y = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
                g2.drawString(text, x, y);
                g2.dispose();
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setContentAreaFilled(false); // Fix for gray rectangle on hover
                setBorderPainted(false);
            }
        };

        button.setFont(TABLE_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35)); // Adjusted width for CRUD buttons

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // ... (include the same styling methods from ProfPanel: styleTable, createStyledButton)
}