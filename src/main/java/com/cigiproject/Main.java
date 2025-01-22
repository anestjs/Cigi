// package main.java.com.cigiproject;

// import main.java.com.cigiproject.ui.LoginUI;
// import main.java.com.cigiproject.controller.LoginController;
// import javax.swing.*;

// public class Main {
//     public static void main(String[] args) {
//         // Run the UI on the Event Dispatch Thread
//         SwingUtilities.invokeLater(() -> {
//             LoginUI loginUI = new LoginUI();
//             new LoginController(loginUI); // Connect the controller to the UI
//         });
//     }
// }




package main.java.com.cigiproject;

import main.java.com.cigiproject.services.SessionService;
import main.java.com.cigiproject.ui.LoginUI;
import main.java.com.cigiproject.controller.LoginController;
import main.java.com.cigiproject.dao.impl.ProfessorDaoImpl;
import main.java.com.cigiproject.model.Professor;
import main.java.com.cigiproject.model.User;



import main.java.com.cigiproject.controller.admin.AdminDashboardController;
import main.java.com.cigiproject.controller.etudiant.EtudiantDashboardController;
import main.java.com.cigiproject.controller.professor.ProfessorDashboardController;
import main.java.com.cigiproject.ui.admin.MainCigiUi;
import main.java.com.cigiproject.ui.etudiant.MainEtudiant;
import main.java.com.cigiproject.ui.ensignant.MainEnsignant;


import javax.swing.*;
import java.util.Optional;



public class Main {
    public static void main(String[] args) {
        // Run the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            SessionService sessionService = new SessionService();
            if (sessionService.validateSession()) {
                // If a valid session exists, navigate based on the user's role
                SessionService.Session activeSession = sessionService.getSession();
                int userId = activeSession.getId();

                ProfessorDaoImpl professorDao = new ProfessorDaoImpl();
                Optional<Professor> thisprof = professorDao.findByUser_UserId(userId);

                if (thisprof.isPresent()) {
                    User currentUser = thisprof.get().getUser();
                    navigateBasedOnRole(currentUser);
                } else {
                    // If the user is not found, show the login UI
                    showLoginUI();
                }
            } else {
                // If no valid session exists, show the login UI
                showLoginUI();
            }
        });
    }

    private static void showLoginUI() {
        LoginUI loginUI = new LoginUI();
        new LoginController(loginUI); // Connect the controller to the UI
    }

    /**
     * Navigates to the appropriate dashboard based on the user's role.
     *
     * @param user The authenticated user.
     */
    private static void navigateBasedOnRole(User user) {
        // Hide the login interface
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(false);

        // Check the user's role and navigate accordingly
        switch (user.getRole().toString()) {
            case "Student":
                // Navigate to the student dashboard
                MainEtudiant.EtudiantDashboard etudiantDashboard = new MainEtudiant.EtudiantDashboard();
                new EtudiantDashboardController(etudiantDashboard); // No token passed
                break;
            case "Professor":
                // Navigate to the professor dashboard
                MainEnsignant.EnsignantDashboard ensignantDashboard = new MainEnsignant.EnsignantDashboard();
                new ProfessorDashboardController(ensignantDashboard); // No token passed
                break;
            case "Admin":
                // Navigate to the admin dashboard
                MainCigiUi.CIGIDashboard cigiDashboard = new MainCigiUi.CIGIDashboard();
                new AdminDashboardController(cigiDashboard); // No token passed
                break;
            default:
                JOptionPane.showMessageDialog(loginUI, "Rôle inconnu. Accès refusé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }

        // Optionally, close the login interface to free up resources
        loginUI.dispose();
    }
}