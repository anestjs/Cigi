package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

import main.java.com.cigiproject.dao.impl.ModuleDaoImpl;
import main.java.com.cigiproject.model.*;
import main.java.com.cigiproject.model.Module;
import java.util.List;

public class ModulesPanel extends JPanel {
    private static final Color UMI_BLUE = new Color(0, 127, 163);
    private static final Color UMI_ORANGE = new Color(255, 140, 0);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TABLE_HEADER_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14);

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Module> allModules;
    private int currentPage = 1;
    private int rowsPerPage = 10;
    private JComboBox<Semester> semesterFilter;
    private JComboBox<Year> yearFilter;

    public ModulesPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Title
        JLabel titleLabel = new JLabel("Gestion des Modules");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(UMI_BLUE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search and Filter Panel
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchFilterPanel.setBackground(BACKGROUND_COLOR);

        // Filters
        semesterFilter = new JComboBox<>(Semester.values());
        semesterFilter.addActionListener(e -> filterTable());
        
        searchFilterPanel.add(new JLabel("Semestre:"));
        searchFilterPanel.add(semesterFilter);

        headerPanel.add(searchFilterPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);

        // Add CRUD buttons panel
        JPanel crudPanel = createActionPanel();
        tablePanel.add(crudPanel, BorderLayout.NORTH);

        // Create table
        String[] columnNames = {"ID", "Nom", "Semestre", "Année", "Professeur", "Classe"};
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
                        showModuleDetails(selectedRow);
                    }
                }
            }
        });

        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UMI_BLUE, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void showModuleDetails(int selectedRow) {
        int moduleId = (int) tableModel.getValueAt(selectedRow, 0);
        Module selectedModule = allModules.stream()
                .filter(module -> module.getModule_id() == moduleId)
                .findFirst()
                .orElse(null);

        if (selectedModule != null) {
            String details = String.format("""
                ID: %d
                Nom: %s
                Semestre: %s
                Année: %s
                Professeur: %s %s
                Classe: %s
                """,
                selectedModule.getModule_id(),
                selectedModule.getName(),
                selectedModule.getSemester(),
                selectedModule.getYear(),
                selectedModule.getProfessor().getUser().getFirstname(),
                selectedModule.getProfessor().getUser().getLastname(),
                selectedModule.getClassEntity() != null ? selectedModule.getClassEntity().getName() : "Non assigné"
            );

            JOptionPane.showMessageDialog(this, details, "Détails du Module", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(BACKGROUND_COLOR);
        actionPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
    
        JButton addButton = createStyledButton("Ajouter Module", UMI_BLUE);
        JButton editButton = createStyledButton("Modifier", UMI_ORANGE);
        JButton deleteButton = createStyledButton("Supprimer", Color.RED);
     
        addButton.addActionListener(e -> {
            AddModuleDialog addDialog = new AddModuleDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            addDialog.setVisible(true);
            populateTable();
        });
    
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int moduleId = (int) tableModel.getValueAt(selectedRow, 0);
                Module selectedModule = allModules.stream()
                        .filter(module -> module.getModule_id() == moduleId)
                        .findFirst()
                        .orElse(null);
    
                if (selectedModule != null) {
                    EditModuleDialog editDialog = new EditModuleDialog((JFrame) SwingUtilities.getWindowAncestor(this), selectedModule);
                    editDialog.setVisible(true);
                    populateTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un module à modifier.", "Aucune Sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
    
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int moduleId = (int) tableModel.getValueAt(selectedRow, 0);
    
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Êtes-vous sûr de vouloir supprimer ce module ?",
                    "Confirmer la Suppression",
                    JOptionPane.YES_NO_OPTION
                );
    
                if (confirm == JOptionPane.YES_OPTION) {
                    ModuleDaoImpl moduleDao = new ModuleDaoImpl();
                    boolean isDeleted = moduleDao.delete(moduleId);
    
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this, "Module supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        populateTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Échec de la suppression du module.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un module à supprimer.", "Aucune Sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
    
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
    
        return actionPanel;
    }

    private void filterTable() {
        Semester selectedSemester = (Semester) semesterFilter.getSelectedItem();
        List<Module> filteredModules = allModules.stream()
                .filter(module -> selectedSemester == null || module.getSemester() == selectedSemester)
                .toList();

        updateTableData(filteredModules);
    }

    private void updateTableData() {
        updateTableData(allModules);
    }

    private void updateTableData(List<Module> modules) {
        tableModel.setRowCount(0);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, modules.size());

        for (int i = start; i < end; i++) {
            Module module = modules.get(i);
            Object[] rowData = {
                module.getModule_id(),
                module.getName(),
                module.getSemester(),
                module.getYear(),
                String.format("%s %s", 
                    module.getProfessor().getUser().getFirstname(),
                    module.getProfessor().getUser().getLastname()),
                module.getClassEntity() != null ? module.getClassEntity().getName() : "Non assigné"
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
                
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                
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
                setContentAreaFilled(false);
                setBorderPainted(false);
            }
        };

        button.setFont(TABLE_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));

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

    private void populateTable() {
        ModuleDaoImpl moduleService = new ModuleDaoImpl();
        allModules = moduleService.findAll();
        updateTableData();
    }
}