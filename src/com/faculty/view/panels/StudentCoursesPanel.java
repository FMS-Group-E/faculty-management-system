package com.faculty.view.panels;

import com.faculty.model.Student;
import com.faculty.model.Enrollment;
import com.faculty.dao.EnrollmentDAO;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentCoursesPanel extends JPanel {

    private final Student       student;
    private final EnrollmentDAO enrollDAO = new EnrollmentDAO();

    public StudentCoursesPanel(Student student) {
        this.student = student;
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

        JLabel title = new JLabel("My Enrolled Courses");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("View your current course enrollments and grade statuses.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"#", "Course Code", "Course Name", "Grade"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        if (student != null) {
            List<Enrollment> enrollments = enrollDAO.findByStudentId(student.getStudentId());
            int i = 1;
            for (Enrollment e : enrollments) {
                model.addRow(new Object[]{
                    i++,
                    e.getCourseCode(),
                    e.getCourseName(),
                    e.getGrade() != null ? e.getGrade() : "—"
                });
            }
        }

        JTable table = new JTable(model);
        UITheme.styleTable(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        int count = model.getRowCount();
        JLabel pageInfo = new JLabel("Showing " + count + " enrolled course" + (count != 1 ? "s" : ""));
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
