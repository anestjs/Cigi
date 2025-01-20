package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.ProfessorService;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.ProfessorService;
import java.util.List;

public class ProfPanel extends JPanel {
    private static final Color UMI_BLUE = new Color(0, 127, 163);
    private static final Color UMI_ORANGE = new Color(255, 140, 0);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TABLE_HEADER_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14);

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Professor> allProfessors;
    private int currentPage = 1;
    private int rowsPerPage = 10;

    public ProfPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
    }

    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        paginationPanel.setBackground(BACKGROUND_COLOR);

        JButton prevButton = createStyledButton("Previous", UMI_BLUE);
        JButton nextButton = createStyledButton("Next", UMI_BLUE);
        JLabel pageLabel = new JLabel("Page 1");
        pageLabel.setFont(TABLE_FONT);

        // Set smaller width for pagination buttons
        prevButton.setPreferredSize(new Dimension(80, 35)); // Smaller width
        nextButton.setPreferredSize(new Dimension(80, 35)); // Smaller width

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableData();
                pageLabel.setText("Page " + currentPage);
            }
        });

        nextButton.addActionListener(e -> {
            int maxPages = (int) Math.ceil((double) allProfessors.size() / rowsPerPage);
            if (currentPage < maxPages) {
                currentPage++;
                updateTableData();
                pageLabel.setText("Page " + currentPage);
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextButton);

        return paginationPanel;
    }

    private void updateTableData() {
        tableModel.setRowCount(0);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allProfessors.size());

        for (int i = start; i < end; i++) {
            Professor professor = allProfessors.get(i);
            User user = professor.getUser();
            Object[] rowData = {
                    professor.getProfessor_id(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getRole().name()
            };
            tableModel.addRow(rowData);
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Professors Management");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(UMI_BLUE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JTextField searchField = createStyledTextField("Search professors...");
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);

        // Add CRUD buttons on top of the table
        JPanel crudPanel = createActionPanel();
        tablePanel.add(crudPanel, BorderLayout.NORTH);

        // Create the table
        String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        // Add double-click listener to the table
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detect double-click
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // Ensure a row is selected
                        showProfessorDetails(selectedRow);
                    }
                }
            }
        });

        populateTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UMI_BLUE, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void showProfessorDetails(int selectedRow) {
        // Get the professor ID from the selected row
        int professorId = (int) tableModel.getValueAt(selectedRow, 0);

        // Find the professor in the list
        Professor selectedProfessor = allProfessors.stream()
                .filter(prof -> prof.getProfessor_id() == professorId)
                .findFirst()
                .orElse(null);

        if (selectedProfessor != null) {
            // Display detailed information in a dialog
            User user = selectedProfessor.getUser();
            String details = "ID: " + selectedProfessor.getProfessor_id() + "\n" +
                    "First Name: " + user.getFirstname() + "\n" +
                    "Last Name: " + user.getLastname() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Role: " + user.getRole().name();

            JOptionPane.showMessageDialog(this, details, "Professor Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align buttons to the left
        actionPanel.setBackground(BACKGROUND_COLOR);
        actionPanel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Add some padding below the buttons

        JButton addButton = createStyledButton("Add Professor", UMI_BLUE);
        JButton editButton = createStyledButton("Edit", UMI_ORANGE);
        JButton deleteButton = createStyledButton("Delete", Color.RED);

        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);

        return actionPanel;
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

    private void populateTable(DefaultTableModel tableModel) {
        ProfessorService professorService = new ProfessorService();
        allProfessors = professorService.getAllProfessors();
        updateTableData();
    }
}