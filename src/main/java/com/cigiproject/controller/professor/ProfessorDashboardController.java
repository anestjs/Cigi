package main.java.com.cigiproject.controller.professor;

import main.java.com.cigiproject.ui.ensignant.MainEnsignant;

public class ProfessorDashboardController {

    private MainEnsignant.EnsignantDashboard ensignantDashboard;

    public ProfessorDashboardController(MainEnsignant.EnsignantDashboard ensignantDashboard) {
        this.ensignantDashboard = ensignantDashboard;
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Ajouter des écouteurs d'événements pour les boutons ou autres composants de l'interface
        // Exemple : Gérer le clic sur un bouton de déconnexion
    }

    // Ajouter des méthodes pour gérer des actions spécifiques (ex : déconnexion, navigation)
}