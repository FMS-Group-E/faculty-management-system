package com.faculty.view;

import com.faculty.model.User;
import com.faculty.model.Lecturer;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;
import com.faculty.view.panels.*;

import javax.swing.*;
import java.awt.*;

/**
 * View: Lecturer Dashboard – blue solid sidebar + white content area.
 */
public class LecturerDashboardView extends JFrame {

    private final User     user;
    private final Lecturer lecturer;
    private JPanel         contentArea;
    private JButton        activeNavBtn = null;

    public LecturerDashboardView(User user, Lecturer lecturer) {
        this.user     = user;
        this.lecturer = lecturer;
        initUI();
    }

    private void initUI() {
        setTitle("FacultyPortal - Lecturer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(UITheme.BG_DARK);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UITheme.BG_DARK);
        contentArea.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        mainArea.add(contentArea, BorderLayout.CENTER);
        add(mainArea, BorderLayout.CENTER);

        showPanel("profile");
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Greeting banner
        String name = lecturer != null ? lecturer.getFullName() : user.getUsername();
        String firstName = name.contains(" ") ? name.substring(0, name.indexOf(' ')) : name;

        JPanel greetPanel = new JPanel();
        greetPanel.setLayout(new BoxLayout(greetPanel, BoxLayout.Y_AXIS));
        greetPanel.setBackground(UITheme.BG_SIDEBAR);
        greetPanel.setBorder(BorderFactory.createEmptyBorder(28, 20, 18, 20));
        greetPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        greetPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JLabel avatarIcon = new JLabel(new LucideIcon("lecturer", 32, Color.WHITE));
        avatarIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetPanel.add(avatarIcon);
        greetPanel.add(Box.createVerticalStrut(10));

        JLabel greetLbl = new JLabel("Welcome, " + firstName);
        greetLbl.setFont(new Font("Helvetica", Font.BOLD, 16));
        greetLbl.setForeground(Color.WHITE);
        greetLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetPanel.add(greetLbl);

        sidebar.add(greetPanel);
        sidebar.add(sidebarSep());
        sidebar.add(Box.createVerticalStrut(10));

        // Nav
        sidebar.add(navBtn("Profile Details", "lecturer", "profile"));
        sidebar.add(navBtn("Timetable",       "dashboard","timetable"));
        sidebar.add(navBtn("My Courses",      "course",   "courses"));

        sidebar.add(Box.createVerticalGlue());

        // Logout circle
        JPanel logoutWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        logoutWrapper.setBackground(UITheme.BG_SIDEBAR);
        logoutWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JButton logoutBtn = circleLogoutButton();
        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        });
        logoutWrapper.add(logoutBtn);
        sidebar.add(logoutWrapper);

        return sidebar;
    }

    private JButton navBtn(String text, String iconName, String panelKey) {
        JButton btn = new JButton(text);
        LucideIcon defIcon = new LucideIcon(iconName, 18, UITheme.SIDEBAR_TEXT_MUTED);
        LucideIcon hovIcon = new LucideIcon(iconName, 18, Color.WHITE);

        btn.setIcon(defIcon);
        btn.setIconTextGap(14);
        btn.setFont(new Font("Helvetica", Font.PLAIN, 13));
        btn.setForeground(UITheme.SIDEBAR_TEXT_MUTED);
        btn.setBackground(UITheme.BG_SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != activeNavBtn) {
                    btn.setBackground(UITheme.SIDEBAR_HOVER);
                    btn.setForeground(Color.WHITE);
                    btn.setIcon(hovIcon);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeNavBtn) {
                    btn.setBackground(UITheme.BG_SIDEBAR);
                    btn.setForeground(UITheme.SIDEBAR_TEXT_MUTED);
                    btn.setIcon(defIcon);
                }
            }
        });

        btn.addActionListener(e -> {
            if (activeNavBtn != null) {
                activeNavBtn.setBackground(UITheme.BG_SIDEBAR);
                activeNavBtn.setForeground(UITheme.SIDEBAR_TEXT_MUTED);
            }
            activeNavBtn = btn;
            btn.setBackground(UITheme.SIDEBAR_ACTIVE);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Helvetica", Font.BOLD, 13));
            btn.setIcon(hovIcon);
            showPanel(panelKey);
        });

        return btn;
    }

    private JButton circleLogoutButton() {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(191, 219, 254));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
            }
        };
        btn.setIcon(new LucideIcon("logout", 20, UITheme.PRIMARY));
        btn.setPreferredSize(new Dimension(48, 48));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setIcon(new LucideIcon("logout", 20, UITheme.PRIMARY_DARK));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setIcon(new LucideIcon("logout", 20, UITheme.PRIMARY));
            }
        });
        return btn;
    }

    private JSeparator sidebarSep() {
        JSeparator sep = new JSeparator();
        sep.setForeground(UITheme.SIDEBAR_SEPARATOR);
        sep.setBackground(UITheme.SIDEBAR_SEPARATOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private void showPanel(String panelName) {
        contentArea.removeAll();
        JPanel panel;
        switch (panelName) {
            case "timetable": panel = new LecturerTimetablePanel(lecturer);           break;
            case "courses":   panel = new LecturerCoursesPanel(lecturer);             break;
            default:          panel = new LecturerProfilePanel(user, lecturer, this); break;
        }
        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }
}
