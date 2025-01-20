package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Class;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.dao.impl.ClassDaoImpl;
import main.java.com.cigiproject.dao.impl.ModuleDaoImpl;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;

public class AddModuleDialog extends JDialog {
    private static final Color UMI_BLUE = new Color(0, 127, 163);
    private static final Color UMI_ORANGE = new Color(255, 140, 0);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);

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

        // Name Field
        addFormRow(formPanel, "Name:", createStyledTextField(), 0, gbc);

        // Semester Combo
        semesterComboBox = createStyledComboBox(Semester.values());
        addFormRow(formPanel, "Semester:", semesterComboBox, 1, gbc);

        // Year Combo
        yearComboBox = createStyledComboBox(Year.values());
        addFormRow(formPanel, "Year:", yearComboBox, 2, gbc);

        // Professor Combo
        professorComboBox = createStyledComboBox(profDao.findAll().toArray(new Professor[0]));
        addFormRow(formPanel, "Professor:", professorComboBox, 3, gbc);

        // Class Combo
        classComboBox = createStyledComboBox(classDao.findAll().toArray(new Class[0]));
        addFormRow(formPanel, "Class:", classComboBox, 4, gbc);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton saveButton = createStyledButton("Save", UMI_BLUE);
        JButton cancelButton = createStyledButton("Cancel", Color.GRAY);

        saveButton.addActionListener(e -> saveModule());
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
        nameField = new JTextField();
        nameField.setFont(INPUT_FONT);
        nameField.setPreferredSize(new Dimension(200, 35));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UMI_BLUE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return nameField;
    }

    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(INPUT_FONT);
        comboBox.setPreferredSize(new Dimension(200, 35));
        comboBox.setBorder(BorderFactory.createLineBorder(UMI_BLUE));
        comboBox.setBackground(BACKGROUND_COLOR);
        return comboBox;
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

    private void saveModule() {
        String name = nameField.getText();
        if (name.trim().isEmpty()) {
            showStyledMessage("Please enter a module name", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Module module = new Module();
        module.setName(name);
        module.setSemester((Semester) semesterComboBox.getSelectedItem());
        module.setYear((Year) yearComboBox.getSelectedItem());
        module.setProfessor((Professor) professorComboBox.getSelectedItem());
        module.setClassEntity((Class) classComboBox.getSelectedItem());

        if (moduleDao.save(module)) {
            showStyledMessage("Module added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showStyledMessage("Failed to add module", "Error", JOptionPane.ERROR_MESSAGE);
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