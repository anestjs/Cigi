package main.java.com.cigiproject.controller.etudiant;

import main.java.com.cigiproject.ui.etudiant.MainEtudiant;

public class EtudiantDashboardController {

    private MainEtudiant.EtudiantDashboard etudiantDashboard;

    public EtudiantDashboardController(MainEtudiant.EtudiantDashboard etudiantDashboard) {
        this.etudiantDashboard = etudiantDashboard;
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Attach event listeners for buttons or other UI components here
        // Example: Handle logout button click
    }

    // Add methods to handle specific actions (e.g., logout, navigation)
}