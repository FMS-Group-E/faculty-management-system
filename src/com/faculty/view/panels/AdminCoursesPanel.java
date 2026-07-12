package com.faculty.view.panels;

import com.faculty.controller.CourseController;
import com.faculty.controller.DepartmentController;
import com.faculty.controller.LecturerController;
import com.faculty.model.*;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminCoursesPanel extends JPanel {

    private final CourseController     ctrl      = new CourseController();
    private final DepartmentController deptCtrl  = new DepartmentController();
    private final LecturerController   lecCtrl   = new LecturerController();

    private JTable            table;
    private DefaultTableModel model;
    private List<Course>      courses = new ArrayList<>();

    private JTextField        searchField;
    private JComboBox<Object> deptFilterCombo;

    public AdminCoursesPanel() {
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
        loadData();
    }

    private void buildUI() {
        // --- HEADER ROW (Title & Add Button) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);
        
        JLabel title = new JLabel("Courses");
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Manage faculty course offerings, credit metrics, and assigned lecturers.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Right Actions (Add / Edit / Delete)
        JPanel actionBtnGrid = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBtnGrid.setBackground(UITheme.BG_DARK);
        
        JButton addBtn    = UITheme.primaryButton("Add new");
        addBtn.setIconTextGap(8);
        
        JButton editBtn   = UITheme.secondaryButton("Edit");
        editBtn.setIconTextGap(8);
        
        JButton deleteBtn = UITheme.secondaryButton("Delete");
        deleteBtn.setIconTextGap(8);
        
        addBtn.addActionListener(e -> showForm(null));
        editBtn.addActionListener(e -> editSelected());
        deleteBtn.addActionListener(e -> deleteSelected());

        actionBtnGrid.add(addBtn);
        actionBtnGrid.add(editBtn);
        actionBtnGrid.add(deleteBtn);
        headerPanel.add(actionBtnGrid, BorderLayout.EAST);

        // --- SEARCH & FILTER ROW ---
        JPanel searchFilterPanel = new JPanel(new BorderLayout(12, 0));
        searchFilterPanel.setBackground(UITheme.BG_DARK);
        searchFilterPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // Search Input
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

        // Department filter dropdown
        JPanel filtersWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filtersWrapper.setBackground(UITheme.BG_DARK);
        
        JLabel filterLbl = UITheme.label("FILTER BY:");
        deptFilterCombo = UITheme.styledCombo();
        deptFilterCombo.setPreferredSize(new Dimension(200, 36));
        deptFilterCombo.addItem("All Departments");
        List<Department> depts = deptCtrl.getAllDepartments();
        for (Department d : depts) {
            deptFilterCombo.addItem(d);
        }
        deptFilterCombo.addActionListener(e -> performFilter());

        filtersWrapper.add(filterLbl);
        filtersWrapper.add(deptFilterCombo);
        searchFilterPanel.add(filtersWrapper, BorderLayout.EAST);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setBackground(UITheme.BG_DARK);
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(12));
        topSection.add(searchFilterPanel);
        add(topSection, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"ID", "Code", "Course Name", "Credits", "Semester", "Department", "Lecturer"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- PAGINATION FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        JLabel pageInfo = new JLabel("Showing 1 to " + courses.size() + " entries");
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        navPanel.setBackground(UITheme.BG_DARK);
        JButton prevBtn = UITheme.primaryButton("<");
        prevBtn.setPreferredSize(new Dimension(32, 32));
        JButton nextBtn = UITheme.primaryButton(">");
        nextBtn.setPreferredSize(new Dimension(32, 32));
        navPanel.add(prevBtn);
        navPanel.add(nextBtn);
        footerPanel.add(navPanel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        courses = ctrl.getAllCourses();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();
        Object selectedDept = deptFilterCombo.getSelectedItem();
        
        for (Course c : courses) {
            // Text filter
            boolean matchesQuery = c.getCourseName().toLowerCase().contains(query) ||
                                   c.getCourseCode().toLowerCase().contains(query) ||
                                   (c.getDescription() != null && c.getDescription().toLowerCase().contains(query));
            
            // Department filter
            boolean matchesDept = true;
            if (selectedDept instanceof Department) {
                matchesDept = c.getDepartmentId() == ((Department) selectedDept).getDepartmentId();
            }
            
            if (matchesQuery && matchesDept) {
                model.addRow(new Object[]{
                    c.getCourseId(),
                    c.getCourseCode(),
                    c.getCourseName(),
                    c.getCredits(),
                    c.getSemester(),
                    c.getDepartmentName() != null ? c.getDepartmentName() : "—",
                    c.getLecturerName() != null ? c.getLecturerName() : "—"
                });
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course to edit."); return; }
        int id = (int) model.getValueAt(row, 0);
        Course found = null;
        for (Course c : courses) {
            if (c.getCourseId() == id) { found = c; break; }
        }
        if (found != null) showForm(found);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course to delete."); return; }
        int id = (int) model.getValueAt(row, 0);
        if (ctrl.deleteCourse(id)) loadData();
    }

    private void showForm(Course course) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
            course == null ? "Add Course" : "Edit Course", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx= 1.0;

        JTextField codeField = UITheme.styledField();
        JTextField nameField = UITheme.styledField();
        JTextField credField = UITheme.styledField();
        JTextField semField  = UITheme.styledField();
        JComboBox<Department> deptCombo = UITheme.styledCombo();
        JComboBox<Lecturer>   lecCombo  = UITheme.styledCombo();

        List<Department> depts = deptCtrl.getAllDepartments();
        for (Department d : depts) deptCombo.addItem(d);

        List<Lecturer> lecs = lecCtrl.getAllLecturers();
        lecCombo.addItem(new Lecturer()); // blank option
        for (Lecturer l : lecs) lecCombo.addItem(l);

        if (course != null) {
            codeField.setText(course.getCourseCode());
            nameField.setText(course.getCourseName());
            credField.setText(String.valueOf(course.getCredits()));
            semField.setText(String.valueOf(course.getSemester()));
            for (int i = 0; i < deptCombo.getItemCount(); i++) {
                if (((Department) deptCombo.getItemAt(i)).getDepartmentId() == course.getDepartmentId()) {
                    deptCombo.setSelectedIndex(i); break;
                }
            }
            for (int i = 0; i < lecCombo.getItemCount(); i++) {
                Lecturer l = (Lecturer) lecCombo.getItemAt(i);
                if (l != null && l.getLecturerId() == course.getLecturerId()) {
                    lecCombo.setSelectedIndex(i); break;
                }
            }
        }

        int row = 0;
        row = addRow(panel, gbc, row, "Course Code", codeField);
        row = addRow(panel, gbc, row, "Course Name", nameField);
        row = addRow(panel, gbc, row, "Credits",     credField);
        row = addRow(panel, gbc, row, "Semester",    semField);
        gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Department"), gbc);
        gbc.gridx = 1; panel.add(deptCombo, gbc); row++;
        gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Lecturer"),   gbc);
        gbc.gridx = 1; panel.add(lecCombo, gbc);  row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton(course == null ? "Add Course" : "Save Changes");
        saveBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        saveBtn.addActionListener(e -> {
            Course c = course == null ? new Course() : course;
            c.setCourseCode(codeField.getText().trim());
            c.setCourseName(nameField.getText().trim());
            try { c.setCredits(Integer.parseInt(credField.getText().trim())); } catch (NumberFormatException ex) { c.setCredits(3); }
            try { c.setSemester(Integer.parseInt(semField.getText().trim())); } catch (NumberFormatException ex) { c.setSemester(1); }
            Department dept = (Department) deptCombo.getSelectedItem();
            if (dept != null) c.setDepartmentId(dept.getDepartmentId());
            Lecturer lec = (Lecturer) lecCombo.getSelectedItem();
            if (lec != null) c.setLecturerId(lec.getLecturerId());

            boolean ok = course == null ? ctrl.addCourse(c) : ctrl.updateCourse(c);
            if (ok) { dialog.dispose(); loadData(); }
        });
        panel.add(saveBtn, gbc);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private int addRow(JPanel p, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.insets = new Insets(6, 6, 6, 6);
        p.add(UITheme.label(label), gbc);
        gbc.gridx = 1; field.setPreferredSize(new Dimension(250, 36)); p.add(field, gbc);
        return row + 1;
    }
}
