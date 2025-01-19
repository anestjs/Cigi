package main.java.com.cigiproject.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CIGIDashboard extends JFrame {

    private JPanel imageSliderPanel;
    private CardLayout cardLayout;

    public CIGIDashboard() {
 
        setTitle("CIGI Dashboard - Gestion des Étudiants");

 
        setSize(1000, 700);  

 
        setResizable(false);

 
        setLocationRelativeTo(null);

 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 
        JPanel mainPanel = new JPanel(new BorderLayout());

 
        JPanel topBar = createTopBar();
        mainPanel.add(topBar, BorderLayout.NORTH);

 
        JPanel sideBar = createSideBar();
        mainPanel.add(sideBar, BorderLayout.WEST);

 
        JPanel contentPanel = createContentArea();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

 
        add(mainPanel);

 
        setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0x007EA4));
        topBar.setPreferredSize(new Dimension(1000, 60));  

 
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("../../../../resources/images/logo.png"));

 
        int logoHeight = 50;  
        Image logoImage = logoIcon.getImage();
        Image resizedLogo = logoImage.getScaledInstance(-1, logoHeight, Image.SCALE_SMOOTH);  
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);

        JLabel logoLabel = new JLabel(resizedLogoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));  

        JButton uploadButton = new JButton("CIGI 2025");
        uploadButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        uploadButton.setBackground(new Color(0xF7941D));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        topBar.add(logoLabel, BorderLayout.WEST);  
        topBar.add(uploadButton, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSideBar() {
        JPanel sideBar = new JPanel() {
            private Image backgroundImage;

            {
 
                backgroundImage = new ImageIcon(getClass().getResource("../../../../resources/images/gray.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
 
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        sideBar.setBackground(new Color(0x2C3E50));  
        sideBar.setPreferredSize(new Dimension(228, 700));  
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

 
        JLabel menuTitle = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 18));  
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setBorder(new EmptyBorder(20, 10, 20, 10));  

 
        String[] menuItems = {"Étudiants", "Gestion des Notes", "Gestion des Modules", "Professeurs", "Statistiques", "Se Déconnecter"};
        String[] iconPaths = {
            "../../../../resources/icons/student.png",  
            "../../../../resources/icons/notes.png",  
            "../../../../resources/icons/modules.png",  
            "../../../../resources/icons/teacher.png",  
            "../../../../resources/icons/stats.png",  
            "../../../../resources/icons/logout.png"  
        };

        sideBar.add(menuTitle);
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createIconButton(iconPaths[i], menuItems[i]);  
            configureMenuButton(menuButton);
            sideBar.add(Box.createVerticalStrut(10));  
            sideBar.add(menuButton);
        }

        return sideBar;
    }

    private JButton createIconButton(String iconPath, String text) {
 
        JButton button = new RoundedButton("", 10);  
        button.setLayout(new BorderLayout());

 
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));

 
        int iconSize = 18;  
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);  
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

 
        JLabel iconLabel = new JLabel(resizedIconIcon);
        iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));  

 
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));  

 
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);  
        buttonPanel.add(iconLabel, BorderLayout.WEST);
        buttonPanel.add(textLabel, BorderLayout.CENTER);

 
        button.add(buttonPanel);

        return button;
    }

    private void configureMenuButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));  
        button.setBackground(new Color(0x5D6D7E));  
        button.setForeground(Color.WHITE);  
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(206, 40));  
        button.setBorder(new EmptyBorder(8, 15, 8, 15));  
        button.setOpaque(false);  
        button.setContentAreaFilled(false);  

 
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x4A6572));  
                button.setForeground(Color.WHITE);  
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0x5D6D7E));  
                button.setForeground(Color.WHITE);  
            }
        });
    }

    private JPanel createContentArea() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0xE3F2FD));  
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));  

 
        JPanel welcomeCard = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
 
                backgroundImage = new ImageIcon(getClass().getResource("../../../../resources/images/orange.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
 
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomeCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  
        welcomeCard.setPreferredSize(new Dimension(500, 120));  

        JLabel welcomeLabel = new JLabel("Welcome to your dashboard", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));  
        welcomeLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
            "<html>Our project is a comprehensive student management system designed to streamline the administration of students, modules, grades, and professors. " +
            "With intuitive features and a user-friendly interface, educators can efficiently manage academic records, track student progress, and generate detailed reports. " +
            ".</html>", 
            SwingConstants.LEFT
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));  
        subtitleLabel.setForeground(Color.WHITE);

        welcomeCard.add(welcomeLabel, BorderLayout.NORTH);
        welcomeCard.add(subtitleLabel, BorderLayout.SOUTH);

        contentPanel.add(welcomeCard, BorderLayout.NORTH);

 
        JPanel counterCardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));  
        counterCardsPanel.setBorder(new EmptyBorder(15, 0, 0, 0));  
        counterCardsPanel.setOpaque(false);  

 
        JPanel counterCard1 = createCounterCard("Total Students", "1,234", new Color(0x007EA4), "../../../../resources/icons/studentcolor.png");
        counterCardsPanel.add(counterCard1);

 
        JPanel counterCard2 = createCounterCard("Total Professors", "45", new Color(0x007EA4), "../../../../resources/icons/teachercolor.png");
        counterCardsPanel.add(counterCard2);

 
        JPanel counterCard3 = createCounterCard("Total Modules", "12", new Color(0x007EA4), "../../../../resources/icons/modulescolor.png");
        counterCardsPanel.add(counterCard3);

 
        JPanel counterWrapperPanel = new JPanel(new BorderLayout());
        counterWrapperPanel.setOpaque(false);  
        counterWrapperPanel.add(counterCardsPanel, BorderLayout.NORTH);  

        contentPanel.add(counterWrapperPanel, BorderLayout.CENTER);

 
        JPanel sliderPanel = createImageSlider();
        contentPanel.add(sliderPanel, BorderLayout.SOUTH);

        return contentPanel;
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

 
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        int iconSize = 60;  
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);  
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

 
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));  
        contentPanel.setOpaque(false);  

 
        JLabel iconLabel = new JLabel(resizedIconIcon);
        contentPanel.add(iconLabel, BorderLayout.WEST);

 
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);  

 
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));  
        titleLabel.setForeground(textColor);  
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        textPanel.add(titleLabel);

 
        textPanel.add(Box.createVerticalStrut(5));  

 
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));  
        valueLabel.setForeground(textColor);  
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  
        textPanel.add(valueLabel);

 
        contentPanel.add(textPanel, BorderLayout.CENTER);

 
        counterCard.add(contentPanel, BorderLayout.CENTER);

        return counterCard;
    }

    private JPanel createImageSlider() {
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(new Color(0xE3F2FD));  
        sliderPanel.setBorder(new EmptyBorder(15, 20, 15, 20));  
    
 
        cardLayout = new CardLayout();
        imageSliderPanel = new JPanel(cardLayout);
        imageSliderPanel.setOpaque(false);  
    
 
        String[] imagePaths = {
            "../../../../resources/images/slide2.png",
            "../../../../resources/images/slide1.png",
            "../../../../resources/images/slide3.png"
        };
    
        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {  
 
                JLabel imageLabel = new JLabel() {
                    @Override
                    protected void paintComponent(Graphics g) {
 
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
 
                        double scale = Math.max(
                            (double) getWidth() / icon.getIconWidth(),
                            (double) getHeight() / icon.getIconHeight()
                        );
                        int scaledWidth = (int) (icon.getIconWidth() * scale);
                        int scaledHeight = (int) (icon.getIconHeight() * scale);
                        int x = (getWidth() - scaledWidth) / 2;
                        int y = (getHeight() - scaledHeight) / 2;
    
 
                        g2d.drawImage(icon.getImage(), x, y, scaledWidth, scaledHeight, this);
                        g2d.dispose();
                    }
                };
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setPreferredSize(new Dimension(600, 180));  
                imageSliderPanel.add(imageLabel);
            } else {
                System.err.println("Failed to load image: " + path);  
            }
        }
    
 
        JButton prevButton = new JButton("←");  
        JButton nextButton = new JButton("→");  
    
 
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
    
 
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(imageSliderPanel);
            }
        });
    
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(imageSliderPanel);
            }
        });
    
 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);  
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        buttonPanel.setOpaque(false);  
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));  
    
 
        sliderPanel.add(imageSliderPanel, BorderLayout.CENTER);
        sliderPanel.add(buttonPanel, BorderLayout.SOUTH);
    
 
        imageSliderPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 2),  
            BorderFactory.createEmptyBorder(5, 5, 5, 5)  
        ));
    
        return sliderPanel;
    }

    public static void main(String[] args) {
 
        SwingUtilities.invokeLater(() -> new CIGIDashboard());
    }
}

 
class RoundedButton extends JButton {
    private int cornerRadius;

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);  
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g2d);
        g2d.dispose();
    }
}