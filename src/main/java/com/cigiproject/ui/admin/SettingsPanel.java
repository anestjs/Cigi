package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Settings Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}