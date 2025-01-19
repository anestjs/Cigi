package main.java.com.cigiproject.ui.etudiant;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Settings Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}