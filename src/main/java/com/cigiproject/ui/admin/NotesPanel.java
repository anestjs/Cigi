package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel {
    public NotesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Notes Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}