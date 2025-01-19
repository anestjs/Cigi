package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    public StatsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Statistics Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}