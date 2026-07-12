package com.faculty.view.panels;

import com.faculty.dao.*;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import java.awt.*;

public class AdminHomePanel extends JPanel {

    public AdminHomePanel() {
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 24));
        buildUI();
    }

    private void buildUI() {
        // --- HEADER SECTION ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);
        
        JLabel title = new JLabel("Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Real-time metrics compiled from the database.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        JPanel pillWrapper = new JPanel(new GridBagLayout());
        pillWrapper.setBackground(UITheme.BG_DARK);

        add(headerPanel, BorderLayout.NORTH);

        // --- STAT CARDS SECTION ---
        JPanel statsPanel = new JPanel(new GridLayout(1, 5, 14, 0));
        statsPanel.setBackground(UITheme.BG_DARK);

        int studentCount = new StudentDAO().findAll().size();
        int lecturerCount = new LecturerDAO().findAll().size();
        int deptCount = new DepartmentDAO().findAll().size();
        int degreeCount = new DegreeDAO().findAll().size();
        int courseCount = new CourseDAO().findAll().size();

        statsPanel.add(statCard("Total Faculty", String.valueOf(lecturerCount), "lecturer", UITheme.PRIMARY));
        statsPanel.add(statCard("Active Students", String.valueOf(studentCount), "student", UITheme.SUCCESS));
        statsPanel.add(statCard("Departments", String.valueOf(deptCount), "department", UITheme.SECONDARY));
        statsPanel.add(statCard("Degrees Offered", String.valueOf(degreeCount), "degree", UITheme.WARNING));
        statsPanel.add(statCard("Courses Active", String.valueOf(courseCount), "course", UITheme.DANGER));

        // Put everything together
        JPanel centerWrapper = new JPanel(new BorderLayout(0, 24));
        centerWrapper.setBackground(UITheme.BG_DARK);
        centerWrapper.add(statsPanel, BorderLayout.NORTH);

        add(centerWrapper, BorderLayout.CENTER);
    }

    private JPanel statCard(String title, String count, String iconName, Color accent) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(UITheme.BORDER_COLOR);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(0, 16));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Top Row (Icon & Pill)
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        // Icon Wrapper (rounded bg)
        JPanel iconWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
        };
        iconWrapper.setOpaque(false);
        iconWrapper.setPreferredSize(new Dimension(36, 36));
        iconWrapper.add(new JLabel(new LucideIcon(iconName, 18, accent)));
        top.add(iconWrapper, BorderLayout.WEST);

        // Status Pill
        JPanel pill = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };

        // Bottom Content (Title & Count)
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLbl.setForeground(UITheme.TEXT_MUTED);

        JLabel countLbl = new JLabel(count);
        countLbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        countLbl.setForeground(UITheme.TEXT_PRIMARY);

        content.add(titleLbl);
        content.add(Box.createVerticalStrut(4));
        content.add(countLbl);

        card.add(top, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);

        return card;
    }


}
