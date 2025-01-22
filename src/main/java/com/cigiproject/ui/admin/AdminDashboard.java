package main.java.com.cigiproject.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JPanel {

    public AdminDashboard() {
        setBackground(new Color(0xE3F2FD));  
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));  

        // Carte de bienvenue
        JPanel welcomeCard = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                // Chargement de l'image de fond
                backgroundImage = new ImageIcon(getClass().getResource("../../../../../resources/images/orange.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessin de l'image de fond
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomeCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  
        welcomeCard.setPreferredSize(new Dimension(500, 137));  

        JLabel welcomeLabel = new JLabel("Bienvenue sur votre dashboard", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));  
        welcomeLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
            "<html>Notre projet est un système complet de gestion des étudiants conçu pour simplifier l'administration des étudiants, des modules, des notes et des professeurs. " +
            "Avec des fonctionnalités intuitives et une interface conviviale, les éducateurs peuvent gérer efficacement les dossiers académiques, suivre les progrès des étudiants et générer des rapports détaillés.</html>", 
            SwingConstants.LEFT
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));  
        subtitleLabel.setForeground(Color.WHITE);

        welcomeCard.add(welcomeLabel, BorderLayout.NORTH);
        welcomeCard.add(subtitleLabel, BorderLayout.SOUTH);

        add(welcomeCard, BorderLayout.NORTH);

        // Panneau des cartes de compteurs
        JPanel counterCardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));  
        counterCardsPanel.setBorder(new EmptyBorder(15, 0, 0, 0));  
        counterCardsPanel.setOpaque(false);  

        // Carte de compteur 1 : Total des étudiants
        JPanel counterCard1 = createCounterCard("Total Étudiants", "1 234", new Color(0x007EA4), "../../../../../resources/icons/studentcolor.png");
        counterCardsPanel.add(counterCard1);

        // Carte de compteur 2 : Total des professeurs
        JPanel counterCard2 = createCounterCard("Total Professeurs", "45", new Color(0x007EA4), "../../../../../resources/icons/teachercolor.png");
        counterCardsPanel.add(counterCard2);

        // Carte de compteur 3 : Total des modules
        JPanel counterCard3 = createCounterCard("Total Modules", "12", new Color(0x007EA4), "../../../../../resources/icons/modulescolor.png");
        counterCardsPanel.add(counterCard3);

        // Panneau wrapper pour les cartes de compteurs
        JPanel counterWrapperPanel = new JPanel(new BorderLayout());
        counterWrapperPanel.setOpaque(false);  
        counterWrapperPanel.add(counterCardsPanel, BorderLayout.NORTH);  

        add(counterWrapperPanel, BorderLayout.CENTER);

        // Panneau du slider d'images
        JPanel sliderPanel = createImageSlider();
        add(sliderPanel, BorderLayout.SOUTH);
    }

    private JPanel createCounterCard(String title, String value, Color textColor, String iconPath) {
        JPanel counterCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);  
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);  
                g2d.dispose();
            }
        };
        counterCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  
        counterCard.setPreferredSize(new Dimension(200, 120));  

        // Chargement et redimensionnement de l'icône
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        int iconSize = 60;  
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);  
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

        // Panneau de contenu
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));  
        contentPanel.setOpaque(false);  

        // Ajout de l'icône
        JLabel iconLabel = new JLabel(resizedIconIcon);
        contentPanel.add(iconLabel, BorderLayout.WEST);

        // Panneau de texte
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);  

        // Titre
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));  
        titleLabel.setForeground(textColor);  
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        textPanel.add(titleLabel);

        // Espacement
        textPanel.add(Box.createVerticalStrut(5));  

        // Valeur
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));  
        valueLabel.setForeground(textColor);  
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        textPanel.add(valueLabel);

        // Ajout du panneau de texte au panneau de contenu
        contentPanel.add(textPanel, BorderLayout.CENTER);

        // Ajout du panneau de contenu à la carte de compteur
        counterCard.add(contentPanel, BorderLayout.CENTER);

        return counterCard;
    }

    private JPanel createImageSlider() {
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(new Color(0xE3F2FD));  
        sliderPanel.setBorder(new EmptyBorder(15, 20, 15, 20));  

        // Configuration du CardLayout pour le slider
        CardLayout cardLayout = new CardLayout();
        JPanel imageSliderPanel = new JPanel(cardLayout);
        imageSliderPanel.setOpaque(false);  

        // Chemins des images
        String[] imagePaths = {
            "../../../../../resources/images/slide2.png",
            "../../../../../resources/images/slide1.png",
            "../../../../../resources/images/slide3.png"
        };

        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {  
                // Création d'un JLabel pour l'image redimensionnée
                JLabel imageLabel = new JLabel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Calcul de l'échelle pour redimensionner l'image
                        double scale = Math.max(
                            (double) getWidth() / icon.getIconWidth(),
                            (double) getHeight() / icon.getIconHeight()
                        );
                        int scaledWidth = (int) (icon.getIconWidth() * scale);
                        int scaledHeight = (int) (icon.getIconHeight() * scale);
                        int x = (getWidth() - scaledWidth) / 2;
                        int y = (getHeight() - scaledHeight) / 2;

                        // Dessin de l'image redimensionnée
                        g2d.drawImage(icon.getImage(), x, y, scaledWidth, scaledHeight, this);
                        g2d.dispose();
                    }
                };
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setPreferredSize(new Dimension(600, 180));  
                imageSliderPanel.add(imageLabel);
            } else {
                System.err.println("Échec du chargement de l'image : " + path);  
            }
        }

        // Boutons de navigation
        JButton prevButton = new JButton("←");  
        JButton nextButton = new JButton("→");  

        // Configuration des boutons
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);  
        prevButton.setFont(buttonFont);
        nextButton.setFont(buttonFont);

        prevButton.setBackground(new Color(0x007EA4));  
        nextButton.setBackground(new Color(0x007EA4));  

        prevButton.setForeground(Color.WHITE);  
        nextButton.setForeground(Color.WHITE);  

        prevButton.setFocusPainted(false);  
        nextButton.setFocusPainted(false);  

        prevButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));  
        nextButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));  

        // Gestion des événements de survol des boutons
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                prevButton.setBackground(new Color(0x005F7A));  
            }

            @Override
            public void mouseExited(MouseEvent e) {
                prevButton.setBackground(new Color(0x007EA4));  
            }
        });

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nextButton.setBackground(new Color(0x005F7A));  
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nextButton.setBackground(new Color(0x007EA4));  
            }
        });

        // Gestion des événements de clic des boutons
        prevButton.addActionListener(e -> cardLayout.previous(imageSliderPanel));
        nextButton.addActionListener(e -> cardLayout.next(imageSliderPanel));

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);  
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        buttonPanel.setOpaque(false);  
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));  

        // Ajout des composants au panneau du slider
        sliderPanel.add(imageSliderPanel, BorderLayout.CENTER);
        sliderPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Bordure du panneau du slider
        imageSliderPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 2),  
            BorderFactory.createEmptyBorder(5, 5, 5, 5)  
        ));

        return sliderPanel;
    }
}
