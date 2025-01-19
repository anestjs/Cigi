package main.java.com.cigiproject.ui.ensignant;

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

        // Additional Content Section (Optional)
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
        JLabel welcomeLabel = new JLabel("Bienvenue sur votre Dashboard Enseignant", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20)); // Bold font
        welcomeLabel.setForeground(Color.WHITE); // White text

        // Subtitle
        JLabel subtitleLabel = new JLabel(
            "<html>Gérez vos cours, consultez les notes des étudiants, et organisez vos modules.<br>Votre outil de gestion pédagogique commence ici !</html>",
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

        // Quick Access Card 1: Annonces
        JPanel annoncesCard = createQuickAccessCard("Mes Annonces", "Gérer vos annonces", new Color(0x007EA4), "../../../../../resources/icons/announcecolor.png");
        quickAccessPanel.add(annoncesCard);

        // Quick Access Card 2: Notes (Middle card with a different color)
        JPanel notesCard = createQuickAccessCard("Notes des Étudiants", "Consulter et saisir les notes", new Color(0x1E90FF), "../../../../../resources/icons/notecolor.png");
        quickAccessPanel.add(notesCard);

        // Quick Access Card 3: Modules
        JPanel modulesCard = createQuickAccessCard("Modules", "Organiser vos modules", new Color(0x2C3E50), "../../../../../resources/icons/modulescolor.png");
        quickAccessPanel.add(modulesCard);

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
        JLabel sectionTitle = new JLabel("Statistiques Récentes", SwingConstants.LEFT);
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
    
        // Example Statistics
        String[] statistics = {
            "Nombre total d'étudiants : 120",
            "Nombre total de cours : 8",
            "Taux de réussite moyen : 85%"
        };
    
        // Load statistics icon
        ImageIcon statsIcon = new ImageIcon(getClass().getResource("../../../../../resources/icons/statscolor.png"));
        int iconSize = 16; // Icon size
        Image resizedIcon = statsIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon resizedStatsIcon = new ImageIcon(resizedIcon);
    
        for (String stat : statistics) {
            JPanel statPanel = new JPanel(new BorderLayout());
            statPanel.setOpaque(false); // Transparent background
            statPanel.setBorder(new EmptyBorder(5, 0, 5, 0)); // Add spacing
    
            // Icon Label
            JLabel iconLabel = new JLabel(resizedStatsIcon);
            iconLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // Add padding to the right of the icon
    
            // Stat Label
            JLabel statLabel = new JLabel(stat);
            statLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Regular font
            statLabel.setForeground(new Color(0x333333)); // Dark gray text
    
            // Add icon and text to the panel
            statPanel.add(iconLabel, BorderLayout.WEST);
            statPanel.add(statLabel, BorderLayout.CENTER);
    
            // Hover effect
            statPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    statPanel.setBackground(new Color(0, 126, 164, 20)); // Light blue hover effect with 20 alpha
                }
    
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    statPanel.setBackground(new Color(0x00000000)); // Transparent background on exit
                }
            });
    
            contentPanel.add(statPanel);
        }
    
        // Add vertical glue to push content to the top
        contentPanel.add(Box.createVerticalGlue());
    
        // Set preferred size for the content panel
        contentPanel.setPreferredSize(new Dimension(contentPanel.getPreferredSize().width, 180)); // Increased height
    
        additionalContentPanel.add(contentPanel, BorderLayout.CENTER);
    
        return additionalContentPanel;
    }
}