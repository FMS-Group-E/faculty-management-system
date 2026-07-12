package com.faculty.view.panels;

import com.faculty.model.Student;
import com.faculty.dao.TimetableDAO;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel: Student timetable view with admin-style directory design.
 */
public class StudentTimetablePanel extends JPanel {

    private final Student      student;
    private final TimetableDAO dao = new TimetableDAO();

    public StudentTimetablePanel(Student student) {
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

        JLabel title = new JLabel("My Timetable");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("View your weekly class schedule, locations, and assigned lecturers.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"Day", "Start", "End", "Course", "Code", "Location", "Lecturer"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        if (student != null) {
            List<Object[]> rows = dao.getTimetableForStudent(student.getStudentId());
            for (Object[] row : rows) model.addRow(row);
        }

        JTable table = new JTable(model);
        UITheme.styleTable(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(90);
        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        int count = model.getRowCount();
        JLabel pageInfo = new JLabel("Showing " + count + " schedule entr" + (count != 1 ? "ies" : "y"));
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
