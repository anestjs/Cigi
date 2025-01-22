package main.java.com.cigiproject.ui.ensignant;

import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.model.Module;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.services.SessionService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Panneau pour afficher les modules assignés à un professeur.
 */
public class ModulesPanel extends JPanel {
    private static final Color UMI_BLUE = new Color(0, 127, 163); // Bleu UMI
    private static final Color UMI_ORANGE = new Color(255, 140, 0); // Orange UMI
    private static final Color BACKGROUND_COLOR = Color.WHITE; // Fond blanc
    private static final Color TABLE_HEADER_COLOR = new Color(240, 240, 240); // Gris clair pour l'en-tête
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24); // Police du titre
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 14); // Police du tableau

    private DefaultTableModel tableModel;
    private JTable table;

    /**
     * Constructeur du panneau des modules.
     */
    public ModulesPanel() {
        setLayout(new BorderLayout(20, 20)); // Espacement entre les composants
        setBackground(BACKGROUND_COLOR); // Fond blanc
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marges

        // Créer le panneau d'en-tête
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Créer le panneau du tableau
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }

    /**
     * Crée le panneau d'en-tête avec le titre.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Titre
        JLabel titleLabel = new JLabel("Modules Assignés au Professeur");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(UMI_BLUE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        return headerPanel;
    }

    /**
     * Crée le panneau du tableau.
     */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);

        // Récupération de la session active
        SessionService sessionService = new SessionService();
        SessionService.Session activeSession = sessionService.getSession();
        int userId = activeSession.getId();

        // Récupération du professeur connecté
        ProfessorDaoImpl professorDao = new ProfessorDaoImpl();
        Optional<Professor> professorOptional = professorDao.findByUser_UserId(userId);

        if (professorOptional.isPresent()) {
            Professor professor = professorOptional.get();
            int professorId = professor.getProfessor_id();

            // Récupération des modules assignés au professeur
            List<Module> modules = professorDao.getAssignedModules(professorId);

            // Création du modèle de tableau
            String[] columnNames = {"ID Module", "Nom", "Semestre", "Année"};
            tableModel = new DefaultTableModel(columnNames, 0);

            // Remplissage du modèle avec les données des modules
            for (Module module : modules) {
                Object[] row = {
                        module.getModule_id(),
                        module.getName(),
                        module.getSemester(),
                        module.getYear()
                };
                tableModel.addRow(row);
            }

            // Création du tableau
            table = new JTable(tableModel);
            styleTable(table);

            // Ajout du tableau dans un JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createLineBorder(UMI_BLUE, 1));
            scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

            tablePanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            // Gestion du cas où aucun professeur n'est trouvé
            JLabel errorLabel = new JLabel("Aucun professeur trouvé pour cet utilisateur.", SwingConstants.CENTER);
            errorLabel.setFont(TABLE_FONT);
            errorLabel.setForeground(Color.RED);
            errorLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            tablePanel.add(errorLabel, BorderLayout.CENTER);
        }

        return tablePanel;
    }

    /**
     * Applique un style moderne au tableau.
     */
    private void styleTable(JTable table) {
        table.setFont(TABLE_FONT);
        table.setRowHeight(40); // Hauteur des lignes
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230)); // Couleur de la grille
        table.setSelectionBackground(UMI_BLUE.brighter()); // Couleur de sélection
        table.setSelectionForeground(Color.WHITE); // Texte en blanc lors de la sélection
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Style de l'en-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR); // Fond gris clair
        header.setForeground(UMI_BLUE); // Texte en bleu UMI
        header.setFont(TABLE_FONT.deriveFont(Font.BOLD)); // Police en gras
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Hauteur de l'en-tête

        // Centrer le texte dans les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}