package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class ModulesPanel extends JPanel {
    public ModulesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Modules Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}