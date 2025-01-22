package main.java.com.cigiproject.ui.ensignant;

import main.java.com.cigiproject.services.SessionService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainEnsignant {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnsignantDashboard());
    }

    public static class EnsignantDashboard extends JFrame {

        private JPanel contentPanel;
        private SessionService sessionService; // Add SessionService instance

        public EnsignantDashboard() {
            sessionService = new SessionService(); // Initialize SessionService
            setTitle("Teacher Dashboard - CIGI");

            setSize(1000, 700);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout());

            JPanel topBar = createTopBar();
            mainPanel.add(topBar, BorderLayout.NORTH);

            JPanel sideBar = createSideBar();
            mainPanel.add(sideBar, BorderLayout.WEST);

            contentPanel = createContentArea();
            mainPanel.add(contentPanel, BorderLayout.CENTER);

            add(mainPanel);
            setVisible(true);
        }

        private JPanel createTopBar() {
            JPanel topBar = new JPanel(new BorderLayout());
            topBar.setBackground(new Color(0x007EA4));
            topBar.setPreferredSize(new Dimension(1000, 60));

            ImageIcon logoIcon = new ImageIcon(getClass().getResource("../../../../../resources/images/logo.png"));
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
                    backgroundImage = new ImageIcon(getClass().getResource("../../../../../resources/images/gray.png")).getImage();
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

            JLabel menuTitle = new JLabel("Dashboard d'ensignant", SwingConstants.CENTER);
            menuTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
            menuTitle.setForeground(Color.WHITE);
            menuTitle.setBorder(new EmptyBorder(20, 8, 20, 10));

            String[] menuItems = {"Dashboard", "Announcements", "Notes", "Modules", "Settings", "Se Déconnecter"};
            String[] iconPaths = {
                "../../../../../resources/icons/teacher.png",
                "../../../../../resources/icons/teacher.png",
                "../../../../../resources/icons/notes.png",
                "../../../../../resources/icons/modules.png",
                "../../../../../resources/icons/teacher.png",
                "../../../../../resources/icons/logout.png"
            };

            sideBar.add(menuTitle);
            for (int i = 0; i < menuItems.length; i++) {
                JButton menuButton = createIconButton(iconPaths[i], menuItems[i]);
                configureMenuButton(menuButton);

                String panelName = menuItems[i];
                menuButton.addActionListener(e -> {
                    if (panelName.equals("Se Déconnecter")) {
                        // Handle logout action
                        int confirm = JOptionPane.showConfirmDialog(
                            EnsignantDashboard.this,
                            "Are you sure you want to log out?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            sessionService.invalidateSession(); // Invalidate the session
                            dispose(); // Close the current window
                            // Optionally, you can navigate back to the login screen here
                            // Example: new LoginUI().setVisible(true);
                        }
                    } else {
                        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
                        cardLayout.show(contentPanel, panelName);
                    }
                });

                sideBar.add(Box.createVerticalStrut(10));
                sideBar.add(menuButton);
            }

            sideBar.add(Box.createVerticalGlue());

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
            contentPanel.setLayout(new CardLayout());
            contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel dashboardPanel = new DashboardPanel();
            JPanel announcementsPanel = new AnnouncementsPanel();
            JPanel notesPanel = new NotesPanel();
            JPanel modulesPanel = new ModulesPanel();
            JPanel settingsPanel = new SettingsPanel();

            contentPanel.add(dashboardPanel, "Dashboard");
            contentPanel.add(announcementsPanel, "Announcements");
            contentPanel.add(notesPanel, "Notes");
            contentPanel.add(modulesPanel, "Modules");
            contentPanel.add(settingsPanel, "Settings");

            // Set the default view to Dashboard
            CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
            cardLayout.show(contentPanel, "Dashboard");

            return contentPanel;
        }
    }

    static class RoundedButton extends JButton {
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
}