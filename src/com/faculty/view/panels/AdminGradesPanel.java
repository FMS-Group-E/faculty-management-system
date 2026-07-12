package com.faculty.view.panels;

import com.faculty.dao.CourseDAO;
import com.faculty.dao.EnrollmentDAO;
import com.faculty.dao.StudentDAO;
import com.faculty.model.Course;
import com.faculty.model.Enrollment;
import com.faculty.model.Student;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel: Admin Grade Management panel to add, edit, and delete student grades.
 */
public class AdminGradesPanel extends JPanel {

    private final EnrollmentDAO enrollDAO = new EnrollmentDAO();
    private final StudentDAO    studentDAO = new StudentDAO();
    private final CourseDAO     courseDAO = new CourseDAO();

    private JTable            table;
    private DefaultTableModel model;
    private List<Enrollment>  enrollments = new ArrayList<>();

    private JTextField        searchField;
    private JLabel            pageInfo;

    public AdminGradesPanel() {
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
        loadData();
    }

    private void buildUI() {
        // --- HEADER ROW (Title & Actions) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("Grade Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Assign grades to students, register new course enrollments, or remove students from courses.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Actions
        JPanel actionBtnGrid = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBtnGrid.setBackground(UITheme.BG_DARK);

        JButton addBtn = UITheme.primaryButton("Enroll Student");
        addBtn.setIcon(new LucideIcon("student", 14, Color.WHITE));
        addBtn.setIconTextGap(8);

        JButton editBtn = UITheme.successButton("Edit Grade");
        editBtn.setIcon(new LucideIcon("edit", 14, Color.WHITE));
        editBtn.setIconTextGap(8);

        JButton deleteBtn = UITheme.dangerButton("Delete");
        deleteBtn.setIcon(new LucideIcon("trash", 14, Color.WHITE));
        deleteBtn.setIconTextGap(8);

        addBtn.addActionListener(e -> showAddEnrollmentForm());
        editBtn.addActionListener(e -> editSelectedGrade());
        deleteBtn.addActionListener(e -> deleteSelectedEnrollment());

        actionBtnGrid.add(addBtn);
        actionBtnGrid.add(editBtn);
        actionBtnGrid.add(deleteBtn);
        headerPanel.add(actionBtnGrid, BorderLayout.EAST);

        // --- SEARCH ROW ---
        JPanel searchFilterPanel = new JPanel(new BorderLayout(12, 0));
        searchFilterPanel.setBackground(UITheme.BG_DARK);
        searchFilterPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        searchField = UITheme.styledField();
        searchField.setPreferredSize(new Dimension(320, 36));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { performFilter(); }
            public void removeUpdate(DocumentEvent e) { performFilter(); }
            public void changedUpdate(DocumentEvent e) { performFilter(); }
        });

        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchWrapper.setBackground(UITheme.BG_DARK);
        searchWrapper.add(searchField);
        searchFilterPanel.add(searchWrapper, BorderLayout.WEST);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setBackground(UITheme.BG_DARK);
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(12));
        topSection.add(searchFilterPanel);
        add(topSection, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"Enrollment ID", "Student Name", "Course Code", "Course Name", "Grade"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        pageInfo = new JLabel("Showing 0 entries");
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        enrollments = enrollDAO.findAll();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();

        for (Enrollment e : enrollments) {
            boolean matches = true;
            if (!query.isEmpty()) {
                String studentName = e.getStudentName() != null ? e.getStudentName().toLowerCase() : "";
                String courseName = e.getCourseName() != null ? e.getCourseName().toLowerCase() : "";
                String courseCode = e.getCourseCode() != null ? e.getCourseCode().toLowerCase() : "";
                matches = studentName.contains(query) || courseName.contains(query) || courseCode.contains(query);
            }

            if (matches) {
                model.addRow(new Object[]{
                    e.getEnrollmentId(),
                    e.getStudentName() != null ? e.getStudentName() : "—",
                    e.getCourseCode() != null ? e.getCourseCode() : "—",
                    e.getCourseName() != null ? e.getCourseName() : "—",
                    e.getGrade() != null ? e.getGrade() : "—"
                });
            }
        }

        int count = model.getRowCount();
        pageInfo.setText("Showing " + count + " enrollment" + (count != 1 ? "s" : ""));
    }

    private void editSelectedGrade() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an enrollment row to edit grade.");
            return;
        }

        int enrollmentId = (int) model.getValueAt(selectedRow, 0);
        String studentName = (String) model.getValueAt(selectedRow, 1);
        String courseName = (String) model.getValueAt(selectedRow, 3);
        String currentGrade = (String) model.getValueAt(selectedRow, 4);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Student Grade", true);
        dialog.setSize(450, 260);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Info labels
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JLabel nameLabel = new JLabel("Student: " + studentName);
        nameLabel.setFont(UITheme.FONT_BODY);
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);
        panel.add(nameLabel, gbc);
        row++;

        gbc.gridy = row;
        JLabel crsLabel = new JLabel("Course: " + courseName);
        crsLabel.setFont(UITheme.FONT_BODY);
        crsLabel.setForeground(UITheme.TEXT_PRIMARY);
        panel.add(crsLabel, gbc);
        gbc.gridwidth = 1;
        row++;

        // Combo for grade
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label("Grade"), gbc);
        gbc.gridx = 1;
        JComboBox<String> gradeCombo = UITheme.styledCombo();
        String[] grades = {"—", "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        for (String g : grades) gradeCombo.addItem(g);
        gradeCombo.setSelectedItem(currentGrade);
        panel.add(gradeCombo, gbc);
        row++;

        // Save
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton("Save Grade");
        saveBtn.addActionListener(e -> {
            String selectedGrade = (String) gradeCombo.getSelectedItem();
            if ("—".equals(selectedGrade)) selectedGrade = null;

            if (enrollDAO.updateGrade(enrollmentId, selectedGrade)) {
                dialog.dispose();
                loadData();
                JOptionPane.showMessageDialog(this, "Grade updated successfully.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to update grade.");
            }
        });
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteSelectedEnrollment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an enrollment to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this course enrollment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int enrollmentId = (int) model.getValueAt(selectedRow, 0);
            if (enrollDAO.delete(enrollmentId)) {
                loadData();
                JOptionPane.showMessageDialog(this, "Enrollment deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete enrollment.");
            }
        }
    }

    private void showAddEnrollmentForm() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Register Enrollment", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Student combo box selector
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label("Student"), gbc);
        gbc.gridx = 1;
        JComboBox<StudentSelectorWrapper> studentCombo = UITheme.styledCombo();
        List<Student> students = studentDAO.findAll();
        for (Student s : students) {
            studentCombo.addItem(new StudentSelectorWrapper(s));
        }
        panel.add(studentCombo, gbc);
        row++;

        // Course selector
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label("Course"), gbc);
        gbc.gridx = 1;
        JComboBox<CourseSelectorWrapper> courseCombo = UITheme.styledCombo();
        List<Course> courses = courseDAO.findAll();
        for (Course c : courses) {
            courseCombo.addItem(new CourseSelectorWrapper(c));
        }
        panel.add(courseCombo, gbc);
        row++;

        // Save button
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 8, 8);
        JButton saveBtn = UITheme.primaryButton("Confirm Enrollment");
        saveBtn.addActionListener(e -> {
            StudentSelectorWrapper sw = (StudentSelectorWrapper) studentCombo.getSelectedItem();
            CourseSelectorWrapper cw = (CourseSelectorWrapper) courseCombo.getSelectedItem();

            if (sw == null || cw == null) {
                JOptionPane.showMessageDialog(dialog, "Please select both a student and a course.");
                return;
            }

            if (enrollDAO.insert(sw.student.getStudentId(), cw.course.getCourseId())) {
                dialog.dispose();
                loadData();
                JOptionPane.showMessageDialog(this, "Student enrolled successfully.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to enroll student. (Student may already be enrolled in this course).");
            }
        });
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // Helper wrapper classes for Combobox display
    private static class StudentSelectorWrapper {
        final Student student;
        StudentSelectorWrapper(Student student) { this.student = student; }
        @Override
        public String toString() { return student.getFullName() + " (" + student.getStudentNumber() + ")"; }
    }

    private static class CourseSelectorWrapper {
        final Course course;
        CourseSelectorWrapper(Course course) { this.course = course; }
        @Override
        public String toString() { return course.getCourseCode() + " - " + course.getCourseName(); }
    }
}
