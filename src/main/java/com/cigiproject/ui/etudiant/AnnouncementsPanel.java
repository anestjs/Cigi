package main.java.com.cigiproject.ui.etudiant;

import javax.swing.*;
import java.awt.*;

public class AnnouncementsPanel extends JPanel {
    public AnnouncementsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Announcements Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}