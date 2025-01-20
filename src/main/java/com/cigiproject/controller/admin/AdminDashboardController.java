package main.java.com.cigiproject.controller.admin;

import main.java.com.cigiproject.ui.admin.MainCigiUi;

public class AdminDashboardController {

    private MainCigiUi.CIGIDashboard cigiDashboard;

    public AdminDashboardController(MainCigiUi.CIGIDashboard cigiDashboard) {
        this.cigiDashboard = cigiDashboard;
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Ajouter des écouteurs d'événements pour les boutons ou autres composants de l'interface
        // Exemple : Gérer le clic sur un bouton de déconnexion
    }

    // Ajouter des méthodes pour gérer des actions spécifiques (ex : déconnexion, navigation)
}