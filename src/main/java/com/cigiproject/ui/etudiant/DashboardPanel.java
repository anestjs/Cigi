package main.java.com.cigiproject.ui.etudiant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setBackground(new Color(0xE3F2FD)); // Light blue background
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        // Welcome Card with orange.png background
        JPanel welcomeCard = createWelcomeCard();
        add(welcomeCard, BorderLayout.NORTH);

        // Quick Access Cards
        JPanel quickAccessPanel = createQuickAccessPanel();
        add(quickAccessPanel, BorderLayout.CENTER);

        // Additional Content Section
        JPanel additionalContentPanel = createAdditionalContentPanel();
        add(additionalContentPanel, BorderLayout.SOUTH);
    }

    private JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                // Load the orange.png image
                backgroundImage = new ImageIcon(getClass().getResource("../../../../../resources/images/orange.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
                g2d.dispose();
            }
        };
        welcomeCard.setBorder(new EmptyBorder(15, 15, 15, 15)); // Add padding
        welcomeCard.setPreferredSize(new Dimension(500, 150)); // Set size

        // Welcome Message
        JLabel welcomeLabel = new JLabel("Bienvenue sur votre Dashboard Étudiant", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20)); // Bold font
        welcomeLabel.setForeground(Color.WHITE); // White text

        // Subtitle
        JLabel subtitleLabel = new JLabel(
            "<html>Accédez à vos cours, consultez vos notes, et restez informé des dernières annonces.<br>Votre réussite commence ici !</html>",
            SwingConstants.LEFT
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Regular font
        subtitleLabel.setForeground(Color.WHITE); // White text

        welcomeCard.add(welcomeLabel, BorderLayout.NORTH);
        welcomeCard.add(subtitleLabel, BorderLayout.CENTER);

        return welcomeCard;
    }

    private JPanel createQuickAccessPanel() {
        JPanel quickAccessPanel = new JPanel(new GridLayout(1, 3, 15, 0)); // 3 columns, 15px gap
        quickAccessPanel.setBorder(new EmptyBorder(15, 0, 0, 0)); // Add padding
        quickAccessPanel.setOpaque(false); // Transparent background

        // Quick Access Card 1: Mes Modules
        JPanel coursCard = createQuickAccessCard("Mes Modules", "Accéder à vos cours", new Color(0x007EA4), "../../../../../resources/icons/modulescolor.png");
        quickAccessPanel.add(coursCard);

        // Quick Access Card 2: Mes Notes (Middle card with a different color)
        JPanel notesCard = createQuickAccessCard("Mes Notes", "Consulter vos notes", new Color(0x1E90FF), "../../../../../resources/icons/notecolor.png");
        quickAccessPanel.add(notesCard);

        // Quick Access Card 3: Annonces
        JPanel annoncesCard = createQuickAccessCard("Annonces", "Voir les dernières annonces", new Color(0x2C3E50), "../../../../../resources/icons/announcecolor.png");
        quickAccessPanel.add(annoncesCard);

        return quickAccessPanel;
    }

    private JPanel createQuickAccessCard(String title, String description, Color color, String iconPath) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(0, 0, color, getWidth(), getHeight(), new Color(color.getRed(), color.getGreen(), color.getBlue(), 150));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Rounded corners
                g2d.dispose();
            }
        };
        card.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        card.setPreferredSize(new Dimension(200, 70)); // Reduced height

        // Load and resize the icon
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        int iconSize = 50; // Icon size
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

        // Icon Label
        JLabel iconLabel = new JLabel(resizedIconIcon);
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // Add padding to the right of the icon

        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false); // Transparent background

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14)); // Bold font
        titleLabel.setForeground(Color.WHITE); // White text

        // Description
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Smaller font
        descriptionLabel.setForeground(new Color(0xEEEEEE)); // Light gray text

        // Add components to text panel
        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);

        // Add icon and text panel to card
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Transparent background
        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.CENTER);

        card.add(contentPanel, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(color.brighter(), 2)); // Add border on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(new EmptyBorder(10, 10, 10, 10)); // Remove border on exit
            }
        });

        return card;
    }

    private JPanel createAdditionalContentPanel() {
        JPanel additionalContentPanel = new JPanel(new BorderLayout());
        additionalContentPanel.setBackground(new Color(0xE3F2FD)); // Light blue background
        additionalContentPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding
    
        // Section Title
        JLabel sectionTitle = new JLabel("Dernières Annonces", SwingConstants.LEFT);
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 18)); // Bold font
        sectionTitle.setForeground(new Color(0x007EA4)); // Dark blue text
        sectionTitle.setBorder(new EmptyBorder(0, 0, 10, 0)); // Add spacing below the title
        additionalContentPanel.add(sectionTitle, BorderLayout.NORTH);
    
        // Content Panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                // Gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0xE3F2FD), getWidth(), getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Rounded corners
                g2d.dispose();
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 1), // Dark blue border
            BorderFactory.createEmptyBorder(15, 15, 15, 15) // Add padding
        ));
    
        // Example Announcements
        String[] announcements = {
            "Nouveau cours disponible : Introduction à l'IA",
            "Date limite pour soumettre le projet de fin d'études : 15 Décembre 2025",
            "Réunion des étudiants : 10 Novembre 2025 à 14h"
        };
    
        // Load announcement icon
        ImageIcon announcementIcon = new ImageIcon(getClass().getResource("../../../../../resources/icons/announcecolor.png"));
        int iconSize = 16; // Icon size
        Image resizedIcon = announcementIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon resizedAnnouncementIcon = new ImageIcon(resizedIcon);
    
        for (String announcement : announcements) {
            JPanel announcementPanel = new JPanel(new BorderLayout());
            announcementPanel.setOpaque(false); // Transparent background
            announcementPanel.setBorder(new EmptyBorder(5, 0, 5, 0)); // Add spacing
    
            // Icon Label
            JLabel iconLabel = new JLabel(resizedAnnouncementIcon);
            iconLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // Add padding to the right of the icon
    
            // Announcement Label
            JLabel announcementLabel = new JLabel(announcement);
            announcementLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Regular font
            announcementLabel.setForeground(new Color(0x333333)); // Dark gray text
    
            // Add icon and text to the panel
            announcementPanel.add(iconLabel, BorderLayout.WEST);
            announcementPanel.add(announcementLabel, BorderLayout.CENTER);
    
            // Hover effect
            announcementPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    announcementPanel.setBackground(new Color(0, 126, 164, 20)); // Light blue hover effect with 20 alpha
                }
    
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    announcementPanel.setBackground(new Color(0x00000000)); // Transparent background on exit
                }
            });
    
            contentPanel.add(announcementPanel);
        }
    
        // Add vertical glue to push content to the top
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.setPreferredSize(new Dimension(contentPanel.getPreferredSize().width, 180)); 
    
        additionalContentPanel.add(contentPanel, BorderLayout.CENTER);
    
        return additionalContentPanel;
    }
}