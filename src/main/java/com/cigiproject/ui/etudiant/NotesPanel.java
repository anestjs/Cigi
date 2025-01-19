package main.java.com.cigiproject.ui.etudiant;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel {
    public NotesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Notes Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}