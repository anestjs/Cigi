package main.java.com.cigiproject.ui.etudiant;

import javax.swing.*;
import java.awt.*;

public class ModulesPanel extends JPanel {
    public ModulesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Modules Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}