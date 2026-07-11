package com.faculty.view.panels;

import com.faculty.model.Lecturer;
import com.faculty.model.Course;
import com.faculty.controller.CourseController;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LecturerCoursesPanel extends JPanel {

    private final Lecturer        lecturer;
    private final CourseController ctrl = new CourseController();

    public LecturerCoursesPanel(Lecturer lecturer) {
        this.lecturer = lecturer;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
    }

    private void buildUI() {
        // --- HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("My Teaching Courses");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("View courses you are currently assigned to teach this semester.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"#", "Code", "Course Name", "Credits", "Semester", "Department"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        if (lecturer != null) {
            List<Course> courses = ctrl.getCoursesByLecturer(lecturer.getLecturerId());
            int i = 1;
            for (Course c : courses) {
                model.addRow(new Object[]{
                    i++,
                    c.getCourseCode(),
                    c.getCourseName(),
                    c.getCredits(),
                    c.getSemester(),
                    c.getDepartmentName() != null ? c.getDepartmentName() : "—"
                });
            }
        }

        JTable table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        int count = model.getRowCount();
        JLabel pageInfo = new JLabel("Showing " + count + " assigned course" + (count != 1 ? "s" : ""));
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
