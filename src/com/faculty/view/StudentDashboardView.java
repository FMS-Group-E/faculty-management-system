package com.faculty.view;

import com.faculty.model.User;
import com.faculty.model.Student;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;
import com.faculty.view.panels.*;

import javax.swing.*;
import java.awt.*;

/**
 * View: Dashboard for Student role - profile, timetable, and enrolled courses with vector icons.
 */
public class StudentDashboardView extends JFrame {

    private final User    user;
    private final Student student;

    private JPanel contentArea;
    private JLabel pageTitle;

    public StudentDashboardView(User user, Student student) {
        this.user    = user;
        this.student = student;
        initUI();
    }

    private void initUI() {
        setTitle("FacultyPortal - Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_DARK);

        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = buildSidebar();
        add(sidebar, BorderLayout.WEST);

        // Main content wrapper
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(UITheme.BG_DARK);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(14, 24, 14, 24)
        ));
        pageTitle = new JLabel("My Profile");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pageTitle.setForeground(UITheme.TEXT_PRIMARY);
        topBar.add(pageTitle, BorderLayout.WEST);

        String dispName = student != null ? student.getFullName() : user.getUsername();
        JLabel userLabel = new JLabel("Student: " + dispName);
        userLabel.setFont(UITheme.FONT_BODY);
        userLabel.setForeground(UITheme.TEXT_MUTED);
        topBar.add(userLabel, BorderLayout.EAST);

        mainArea.add(topBar, BorderLayout.NORTH);

        // Content Area
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
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UITheme.BORDER_COLOR));

        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 18));
        logoPanel.setBackground(UITheme.BG_SIDEBAR);
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logoIcon = new JLabel(new LucideIcon("student", 28, UITheme.PRIMARY));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(UITheme.BG_SIDEBAR);

        JLabel logoText = new JLabel("FacultyPortal");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 17));
        logoText.setForeground(UITheme.TEXT_PRIMARY);

        JLabel logoSubText = new JLabel("STUDENT PORTAL");
        logoSubText.setFont(new Font("Segoe UI", Font.BOLD, 9));
        logoSubText.setForeground(UITheme.SUCCESS);

        textPanel.add(logoText);
        textPanel.add(logoSubText);

        logoPanel.add(logoIcon);
        logoPanel.add(textPanel);
        sidebar.add(logoPanel);

        sidebar.add(Box.createVerticalStrut(12));

        // User Greeting
        String name = student != null ? student.getFullName() : user.getUsername();
        JLabel greet = new JLabel("Welcome, " + name);
        greet.setFont(UITheme.FONT_SMALL);
        greet.setForeground(UITheme.TEXT_MUTED);
        greet.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        greet.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(greet);

        sidebar.add(sidebarSep());
        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(sidebarButton("My Profile", "lecturer", "profile", "My Profile"));
        sidebar.add(sidebarButton("Timetable", "dashboard", "timetable", "Timetable"));
        sidebar.add(sidebarButton("My Courses", "course", "courses", "My Courses"));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(sidebarSep());

        // Sign Out
        JButton logoutBtn = new JButton("Sign Out");
        logoutBtn.setIcon(new LucideIcon("logout", 18, UITheme.DANGER));
        logoutBtn.setIconTextGap(12);
        logoutBtn.setFont(UITheme.FONT_BODY);
        logoutBtn.setForeground(UITheme.DANGER);
        logoutBtn.setBackground(UITheme.BG_SIDEBAR);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        });
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(8));

        return sidebar;
    }

    private JSeparator sidebarSep() {
        JSeparator sep = new JSeparator();
        sep.setForeground(UITheme.BORDER_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JButton sidebarButton(String text, String iconName, String panelName, String title) {
        JButton btn = new JButton(text);
        LucideIcon defIcon = new LucideIcon(iconName, 18, UITheme.TEXT_MUTED);
        LucideIcon hovIcon = new LucideIcon(iconName, 18, UITheme.PRIMARY);

        btn.setIcon(defIcon);
        btn.setIconTextGap(12);
        btn.setFont(UITheme.FONT_BODY);
        btn.setForeground(UITheme.TEXT_MUTED);
        btn.setBackground(UITheme.BG_SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(UITheme.BG_CARD);
                btn.setForeground(UITheme.PRIMARY);
                btn.setIcon(hovIcon);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(UITheme.BG_SIDEBAR);
                btn.setForeground(UITheme.TEXT_MUTED);
                btn.setIcon(defIcon);
            }
        });
        btn.addActionListener(e -> { pageTitle.setText(title); showPanel(panelName); });
        return btn;
    }

    private void showPanel(String panelName) {
        contentArea.removeAll();
        JPanel panel;
        switch (panelName) {
            case "timetable":
                panel = new StudentTimetablePanel(student);
                break;
            case "courses":
                panel = new StudentCoursesPanel(student);
                break;
            default:
                panel = new StudentProfilePanel(user, student, this);
                break;
        }
        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    /** Called to refresh profile after edit. */
    public void refreshStudent(Student updated) {
        showPanel("profile");
    }
}
