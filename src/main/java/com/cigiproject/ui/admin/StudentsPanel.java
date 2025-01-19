package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import java.awt.*;

public class StudentsPanel extends JPanel {
    public StudentsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(new JLabel("Students Panel Content Here", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}