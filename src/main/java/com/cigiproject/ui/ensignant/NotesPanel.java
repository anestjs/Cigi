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
    private List<Student> students;
    private int currentStudentIndex;

    // Définir les couleurs
    private static final Color BLEU_CLAIR = new Color(227, 242, 253); // Hex: 0xE3F2FD
    private static final Color BLEU_FONCE = new Color(0, 126, 164);   // Hex: 0x007EA4
    private static final Color ORANGE = new Color(255, 165, 0);       // Couleur orange

    public NotesPanel() {
        setBackground(BLEU_CLAIR); // Définir la couleur de fond
        setLayout(new BorderLayout());

        // Initialiser les DAOs
        professorDao = new ProfessorDaoImpl();
        classDao = new ClassDaoImpl();
        moduleDao = new ModuleDaoImpl();

        // Créer le panneau d'entrée
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(BLEU_CLAIR); // Définir la couleur de fond

        // Sélection de la classe
        JLabel classLabel = new JLabel("Sélectionner la Classe :");
        classLabel.setForeground(BLEU_FONCE); // Définir la couleur du texte
        inputPanel.add(classLabel);

        classComboBox = new JComboBox<>();
        classComboBox.setBackground(Color.WHITE); // Définir la couleur de fond
        classComboBox.setForeground(BLEU_FONCE);  // Définir la couleur du texte
        chargerClasses();
        inputPanel.add(classComboBox);

        // Sélection du module
        JLabel moduleLabel = new JLabel("Sélectionner le Module :");
        moduleLabel.setForeground(BLEU_FONCE); // Définir la couleur du texte
        inputPanel.add(moduleLabel);

        moduleComboBox = new JComboBox<>();
        moduleComboBox.setBackground(Color.WHITE); // Définir la couleur de fond
        moduleComboBox.setForeground(BLEU_FONCE);  // Définir la couleur du texte
        inputPanel.add(moduleComboBox);

        // Charger les modules lorsqu'une classe est sélectionnée
        classComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chargerModules();
            }
        });

        // Boutons pour les actions
        JButton viewStudentsButton = new JButton("Voir les Étudiants");
        styleButton(viewStudentsButton, ORANGE, BLEU_FONCE); // Appliquer le style au bouton

        JButton assignGradeButton = new JButton("Attribuer une Note");
        styleButton(assignGradeButton, ORANGE, BLEU_FONCE); // Appliquer le style au bouton

        // Ajouter des écouteurs d'événements aux boutons
        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherEtudiants();
            }
        });

        assignGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attribuerNote();
            }
        });

        // Ajouter les boutons au panneau d'entrée
        inputPanel.add(viewStudentsButton);
        inputPanel.add(assignGradeButton);

        // Créer la zone de résultat
// Créer la zone de résultat
resultArea = new JTextArea();
resultArea.setEditable(false);
resultArea.setBackground(Color.WHITE); // Changed to white
resultArea.setForeground(BLEU_FONCE); // Définir la couleur du texte
resultArea.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Définir la police
JScrollPane scrollPane = new JScrollPane(resultArea);
scrollPane.setBorder(BorderFactory.createLineBorder(BLEU_FONCE, 1)); // Ajouter une bordure/ Ajouter une bordure

        // Ajouter les composants au panneau principal
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor); // Définir la couleur de fond
        button.setForeground(fgColor); // Définir la couleur du texte
        button.setFont(new Font("SansSerif", Font.BOLD, 12)); // Définir la police
        button.setFocusPainted(false); // Supprimer la bordure de focus
        button.setBorder(BorderFactory.createLineBorder(BLEU_FONCE, 1)); // Ajouter une bordure
    }

    private void chargerClasses() {
        // Utiliser classDao.findAll() pour obtenir la liste des classes
        List<Class> classes = classDao.findAll();
        for (Class clazz : classes) {
            classComboBox.addItem(clazz);
        }
    }

    private void chargerModules() {
        moduleComboBox.removeAllItems();
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedClass != null) {
            List<Module> modules = classDao.getClassModules(selectedClass.getClass_id());
            for (Module module : modules) {
                moduleComboBox.addItem(module);
            }
        }
    }

    private void afficherEtudiants() {
        resultArea.setText(""); // Effacer la zone de résultat
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedClass != null) {
            students = classDao.getEnrolledStudents(selectedClass.getClass_id());
            StringBuilder result = new StringBuilder("Étudiants dans la Classe " + selectedClass.getName() + " :\n");
            for (Student student : students) {
                result.append("CNE : ").append(student.getCne())
                      .append(", Nom : ").append(student.getUser().getFirstname())
                      .append(" ").append(student.getUser().getLastname()).append("\n");
            }
            resultArea.setText(result.toString());
        }
    }

    private void attribuerNote() {
        Module selectedModule = (Module) moduleComboBox.getSelectedItem();
        Class selectedClass = (Class) classComboBox.getSelectedItem();
        if (selectedModule == null || selectedClass == null) {
            resultArea.setText("Veuillez sélectionner une classe et un module.");
            return;
        }

        if (students == null || students.isEmpty()) {
            resultArea.setText("Aucun étudiant trouvé dans la classe sélectionnée.");
            return;
        }

        currentStudentIndex = 0;
        attribuerNoteAuProchainEtudiant(selectedModule);
    }

    private void attribuerNoteAuProchainEtudiant(Module selectedModule) {
        if (currentStudentIndex < students.size()) {
            Student student = students.get(currentStudentIndex);
            String note = JOptionPane.showInputDialog(this, "Entrez la note pour " + student.getUser().getFirstname() + " " + student.getUser().getLastname() + " :");

            if (note != null && !note.isEmpty()) {
                professorDao.assignGradeToStudent(student.getCne(), selectedModule.getModule_id(), note);
                resultArea.append("Note attribuée à " + student.getUser().getFirstname() + " " + student.getUser().getLastname() + " : " + note + "\n");
                currentStudentIndex++;
                attribuerNoteAuProchainEtudiant(selectedModule); // Attribuer la note au prochain étudiant
            } else {
                resultArea.append("La note ne peut pas être vide pour " + student.getUser().getFirstname() + " " + student.getUser().getLastname() + "\n");
            }
        } else {
            resultArea.append("Toutes les notes ont été attribuées.\n");
        }
    }
}