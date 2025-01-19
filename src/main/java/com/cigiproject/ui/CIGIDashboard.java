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
        // Set the title of the dashboard
        setTitle("CIGI Dashboard - Gestion des Étudiants");

        // Set the size of the window (smaller size)
        setSize(1000, 700); // Adjusted to make the frame smaller

        // Prevent the window from being resized
        setResizable(false);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Ensure the application exits when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main layout panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top bar panel
        JPanel topBar = createTopBar();
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Sidebar panel
        JPanel sideBar = createSideBar();
        mainPanel.add(sideBar, BorderLayout.WEST);

        // Content area
        JPanel contentPanel = createContentArea();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Make the window visible
        setVisible(true);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0x007EA4));
        topBar.setPreferredSize(new Dimension(1000, 60)); // Adjusted height

        // Load the logo image from the resources folder
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("../../../../resources/images/logo.png"));

        // Resize the logo to a normal size (e.g., 50 pixels in height)
        int logoHeight = 50;  // Adjusted height
        Image logoImage = logoIcon.getImage();
        Image resizedLogo = logoImage.getScaledInstance(-1, logoHeight, Image.SCALE_SMOOTH);  // Maintain aspect ratio
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogo);

        JLabel logoLabel = new JLabel(resizedLogoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));  // Add left margin

        JButton uploadButton = new JButton("CIGI 2025");
        uploadButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        uploadButton.setBackground(new Color(0xF7941D));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        topBar.add(logoLabel, BorderLayout.WEST);  // Add logo to the left
        topBar.add(uploadButton, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSideBar() {
        JPanel sideBar = new JPanel() {
            private Image backgroundImage;

            {
                // Load the background image from the resources folder
                backgroundImage = new ImageIcon(getClass().getResource("../../../../resources/images/gray.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        sideBar.setBackground(new Color(0x2C3E50));  // Darker color for a more modern feel
        sideBar.setPreferredSize(new Dimension(228, 700)); // Increased width to 220
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Change "Menu" to "Teacher Dashboard"
        JLabel menuTitle = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 18)); // Adjusted font size
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setBorder(new EmptyBorder(20, 10, 20, 10)); // Adjusted padding

        // Menu items with icons
        String[] menuItems = {"Étudiants", "Gestion des Notes", "Gestion des Modules", "Professeurs", "Statistiques", "Se Déconnecter"};
        String[] iconPaths = {
            "../../../../resources/icons/student.png",  // Student icon
            "../../../../resources/icons/notes.png",    // Notes icon
            "../../../../resources/icons/modules.png",  // Modules icon
            "../../../../resources/icons/teacher.png",  // Professor icon
            "../../../../resources/icons/stats.png",    // Statistics icon
            "../../../../resources/icons/logout.png"    // Logout icon
        };

        sideBar.add(menuTitle);
        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createIconButton(iconPaths[i], menuItems[i]);  // Create button with icon
            configureMenuButton(menuButton);
            sideBar.add(Box.createVerticalStrut(10)); // Adjusted spacing
            sideBar.add(menuButton);
        }

        return sideBar;
    }

    private JButton createIconButton(String iconPath, String text) {
        // Create the button without text in the constructor
        JButton button = new RoundedButton("", 10);  // Empty text in the constructor
        button.setLayout(new BorderLayout());

        // Load the icon image
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));

        // Increase the icon size to 32x32 for better quality
        int iconSize = 18;  // Adjusted size
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);  // Use SCALE_SMOOTH for better quality
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

        // Create icon label
        JLabel iconLabel = new JLabel(resizedIconIcon);
        iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));  // Add padding

        // Create text label
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Adjusted font size

        // Create a panel to hold the icon and text
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);  // Make the panel transparent
        buttonPanel.add(iconLabel, BorderLayout.WEST);
        buttonPanel.add(textLabel, BorderLayout.CENTER);

        // Add the panel to the button
        button.add(buttonPanel);

        return button;
    }

    private void configureMenuButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Adjusted font size
        button.setBackground(new Color(0x5D6D7E));  // Light grayish-blue
        button.setForeground(Color.WHITE);  // White text for contrast
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(206, 40)); // Adjusted size to fit the new sidebar width
        button.setBorder(new EmptyBorder(8, 15, 8, 15)); // Adjusted padding
        button.setOpaque(false);  // Ensure the button is transparent
        button.setContentAreaFilled(false);  // Ensure the background is filled

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x4A6572));  // Slightly darker grayish-blue on hover
                button.setForeground(Color.WHITE);  // Keep text white
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0x5D6D7E));  // Return to light grayish-blue
                button.setForeground(Color.WHITE);  // Keep text white
            }
        });
    }

    private JPanel createContentArea() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0xE3F2FD));  // Light blue background
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Adjusted padding

        // Welcome Card
        JPanel welcomeCard = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                // Load the background image from the resources folder
                backgroundImage = new ImageIcon(getClass().getResource("../../../../resources/images/orange.png")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        welcomeCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Adjusted padding
        welcomeCard.setPreferredSize(new Dimension(500, 120)); // Adjusted size

        JLabel welcomeLabel = new JLabel("Welcome to your dashboard", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20)); // Adjusted font size
        welcomeLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
            "<html>Our project is a comprehensive student management system designed to streamline the administration of students, modules, grades, and professors. " +
            "With intuitive features and a user-friendly interface, educators can efficiently manage academic records, track student progress, and generate detailed reports. " +
            ".</html>", 
            SwingConstants.LEFT
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Adjusted font size
        subtitleLabel.setForeground(Color.WHITE);

        welcomeCard.add(welcomeLabel, BorderLayout.NORTH);
        welcomeCard.add(subtitleLabel, BorderLayout.SOUTH);

        contentPanel.add(welcomeCard, BorderLayout.NORTH);

        // Counter Cards Panel
        JPanel counterCardsPanel = new JPanel(new GridLayout(1, 3, 15, 0)); // 1 row, 3 columns, with 15px horizontal gap
        counterCardsPanel.setBorder(new EmptyBorder(15, 0, 0, 0)); // Adjusted padding
        counterCardsPanel.setOpaque(false); // Make the panel transparent

        // Counter Card 1
        JPanel counterCard1 = createCounterCard("Total Students", "1,234", new Color(0x007EA4), "../../../../resources/icons/studentcolor.png");
        counterCardsPanel.add(counterCard1);

        // Counter Card 2
        JPanel counterCard2 = createCounterCard("Total Professors", "45", new Color(0x007EA4), "../../../../resources/icons/teachercolor.png");
        counterCardsPanel.add(counterCard2);

        // Counter Card 3
        JPanel counterCard3 = createCounterCard("Total Modules", "12", new Color(0x007EA4), "../../../../resources/icons/modulescolor.png");
        counterCardsPanel.add(counterCard3);

        // Wrap the counterCardsPanel in another panel to control its height
        JPanel counterWrapperPanel = new JPanel(new BorderLayout());
        counterWrapperPanel.setOpaque(false); // Make it transparent
        counterWrapperPanel.add(counterCardsPanel, BorderLayout.NORTH); // Add counterCardsPanel to the top

        contentPanel.add(counterWrapperPanel, BorderLayout.CENTER);

        // Add the image slider panel
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
                g2d.setColor(Color.WHITE); // White background
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Rounded corners with 15px radius
                g2d.dispose();
            }
        };
        counterCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Adjusted padding
        counterCard.setPreferredSize(new Dimension(200, 120)); // Adjusted size

        // Load the icon image
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        int iconSize = 60; // Adjusted size
        Image resizedIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH); // Use SCALE_SMOOTH for better quality
        ImageIcon resizedIconIcon = new ImageIcon(resizedIcon);

        // Create a panel for the icon, title, and value
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0)); // 10px horizontal gap
        contentPanel.setOpaque(false); // Make the panel transparent

        // Add the icon to the left
        JLabel iconLabel = new JLabel(resizedIconIcon);
        contentPanel.add(iconLabel, BorderLayout.WEST);

        // Create a panel for the title and value
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false); // Make the panel transparent

        // Add the title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Adjusted font size
        titleLabel.setForeground(textColor); // Use the provided text color
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left
        textPanel.add(titleLabel);

        // Add some vertical space between title and value
        textPanel.add(Box.createVerticalStrut(5)); // Adjusted spacing

        // Add the value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 28)); // Adjusted font size
        valueLabel.setForeground(textColor); // Use the provided text color
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left
        textPanel.add(valueLabel);

        // Add the text panel to the content panel
        contentPanel.add(textPanel, BorderLayout.CENTER);

        // Add the content panel to the card
        counterCard.add(contentPanel, BorderLayout.CENTER);

        return counterCard;
    }

    private JPanel createImageSlider() {
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBackground(new Color(0xE3F2FD)); // Light blue background
        sliderPanel.setBorder(new EmptyBorder(15, 20, 15, 20)); // Adjusted padding
    
        // Create a CardLayout for the image slider
        cardLayout = new CardLayout();
        imageSliderPanel = new JPanel(cardLayout);
        imageSliderPanel.setOpaque(false); // Make it transparent
    
        // Add images to the slider
        String[] imagePaths = {
            "../../../../resources/images/slide1.png",
            "../../../../resources/images/slide2.png",
            "../../../../resources/images/slide3.png"
        };
    
        for (String path : imagePaths) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) { // Ensure the image is loaded
                // Create a JLabel to display the image
                JLabel imageLabel = new JLabel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        // Draw the image as a "cover" (maintain aspect ratio and fill the space)
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                        // Calculate scaling to maintain aspect ratio
                        double scale = Math.max(
                            (double) getWidth() / icon.getIconWidth(),
                            (double) getHeight() / icon.getIconHeight()
                        );
                        int scaledWidth = (int) (icon.getIconWidth() * scale);
                        int scaledHeight = (int) (icon.getIconHeight() * scale);
                        int x = (getWidth() - scaledWidth) / 2;
                        int y = (getHeight() - scaledHeight) / 2;
    
                        // Draw the scaled image
                        g2d.drawImage(icon.getImage(), x, y, scaledWidth, scaledHeight, this);
                        g2d.dispose();
                    }
                };
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setPreferredSize(new Dimension(600, 180)); // Adjusted size
                imageSliderPanel.add(imageLabel);
            } else {
                System.err.println("Failed to load image: " + path); // Debugging: Print error if image fails to load
            }
        }
    
        // Add navigation buttons with modern styling
        JButton prevButton = new JButton("←"); // Left arrow
        JButton nextButton = new JButton("→"); // Right arrow
    
        // Style the buttons
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14); // Adjusted font size
        prevButton.setFont(buttonFont);
        nextButton.setFont(buttonFont);
    
        prevButton.setBackground(new Color(0x007EA4)); // Blue background
        nextButton.setBackground(new Color(0x007EA4)); // Blue background
    
        prevButton.setForeground(Color.WHITE); // White text
        nextButton.setForeground(Color.WHITE); // White text
    
        prevButton.setFocusPainted(false); // Remove focus border
        nextButton.setFocusPainted(false); // Remove focus border
    
        prevButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Adjusted padding
        nextButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Adjusted padding
    
        // Add hover effects
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                prevButton.setBackground(new Color(0x005F7A)); // Darker blue on hover
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                prevButton.setBackground(new Color(0x007EA4)); // Restore original color
            }
        });
    
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nextButton.setBackground(new Color(0x005F7A)); // Darker blue on hover
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                nextButton.setBackground(new Color(0x007EA4)); // Restore original color
            }
        });
    
        // Add action listeners for navigation
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
    
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false); // Make it transparent
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        buttonPanel.setOpaque(false); // Make it transparent
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0)); // Adjusted padding
    
        // Add the image slider and buttons to the main panel
        sliderPanel.add(imageSliderPanel, BorderLayout.CENTER);
        sliderPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Add a subtle shadow to the slider
        imageSliderPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x007EA4), 2), // Blue border
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
        ));
    
        return sliderPanel;
    }

    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new CIGIDashboard());
    }
}

// Custom RoundedButton class for sidebar buttons
class RoundedButton extends JButton {
    private int cornerRadius;

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);  // Make the button transparent
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