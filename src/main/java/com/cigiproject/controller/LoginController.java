package main.java.com.cigiproject.controller;

import main.java.com.cigiproject.controller.admin.AdminDashboardController;
import main.java.com.cigiproject.controller.etudiant.EtudiantDashboardController;
import main.java.com.cigiproject.controller.professor.ProfessorDashboardController;
import main.java.com.cigiproject.dao.UserDao;
import main.java.com.cigiproject.dao.impl.UserDaoImpl;
import main.java.com.cigiproject.model.User;
import main.java.com.cigiproject.services.UserService;
import main.java.com.cigiproject.services.SessionService;
import main.java.com.cigiproject.ui.admin.MainCigiUi;
import main.java.com.cigiproject.ui.etudiant.MainEtudiant;
import main.java.com.cigiproject.ui.ensignant.MainEnsignant;
import main.java.com.cigiproject.ui.LoginUI;

import javax.swing.*;
import java.util.Optional;

public class LoginController {

    private LoginUI loginUI;
    private UserDaoImpl userDao = new UserDaoImpl();
    private UserService userService = new UserService(userDao);
    private SessionService sessionService = new SessionService(); // Add SessionService

    // Constructeur avec injection de UserDao
    public LoginController(LoginUI loginUI) {
        this.loginUI = loginUI;
        attachEventListeners();
    }

    private void attachEventListeners() {
        // Ajouter des écouteurs d'événements aux boutons
        loginUI.getLoginButton().addActionListener(e -> handleLogin());
        loginUI.getGoogleButton().addActionListener(e -> handleGoogleLogin());
    }

    private void handleLogin() {
        String email = loginUI.getEmailField().getText();
        char[] password = loginUI.getPasswordField().getPassword();

        // Valider les entrées
        if (email.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(loginUI, "Veuillez entrer votre email et votre mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Valider les identifiants avec UserDao
        Optional<User> userOptional = userService.authenticate(email, new String(password));
        if (userOptional.isPresent()) {
            // Récupérer l'utilisateur authentifié
            User user = userOptional.get();

            // Create a session for the authenticated user
            sessionService.createSession(user.getUser_id(), user.getEmail(), user.getRole().toString());

            // Vérifier le rôle de l'utilisateur et naviguer vers le tableau de bord approprié
            navigateBasedOnRole(user);
        } else {
            JOptionPane.showMessageDialog(loginUI, "Email ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleGoogleLogin() {
        JOptionPane.showMessageDialog(loginUI, "Connexion Google cliquée. Implémentez OAuth2.0 ici.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Navigue vers le tableau de bord approprié en fonction du rôle de l'utilisateur.
     *
     * @param user L'utilisateur authentifié.
     */
    private void navigateBasedOnRole(User user) {
        // Masquer l'interface de connexion
        loginUI.setVisible(false);

        // Vérifier le rôle de l'utilisateur et naviguer en conséquence
        switch (user.getRole().toString()) {
            case "Student":
                // Naviguer vers le tableau de bord étudiant
                MainEtudiant.EtudiantDashboard etudiantDashboard = new MainEtudiant.EtudiantDashboard();
                new EtudiantDashboardController(etudiantDashboard); // No token passed
                break;
            case "Professor":
                // Naviguer vers le tableau de bord enseignant
                MainEnsignant.EnsignantDashboard ensignantDashboard = new MainEnsignant.EnsignantDashboard();
                new ProfessorDashboardController(ensignantDashboard); // No token passed
                break;
            case "Admin":
                // Naviguer vers le tableau de bord administrateur
                MainCigiUi.CIGIDashboard cigiDashboard = new MainCigiUi.CIGIDashboard();
                new AdminDashboardController(cigiDashboard); // No token passed
                break;
            default:
                JOptionPane.showMessageDialog(loginUI, "Rôle inconnu. Accès refusé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
        }

        // Optionnellement, fermer l'interface de connexion pour libérer les ressources
        loginUI.dispose();
    }
}