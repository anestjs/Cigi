package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class ProfPanel extends JPanel {
    public ProfPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Professors Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}