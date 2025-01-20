package main.java.com.cigiproject;

import main.java.com.cigiproject.ui.LoginUI;
import main.java.com.cigiproject.controller.LoginController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Run the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();
            new LoginController(loginUI); // Connect the controller to the UI
        });
    }
}