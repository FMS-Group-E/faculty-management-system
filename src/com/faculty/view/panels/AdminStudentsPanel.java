package com.faculty.view.panels;

import com.faculty.controller.StudentController;
import com.faculty.controller.DegreeController;
import com.faculty.dao.UserDAO;
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

/**
 * Panel: Admin - Student CRUD management with search and filters.
 */
public class AdminStudentsPanel extends JPanel {

    private final StudentController ctrl       = new StudentController();
    private final DegreeController  degreeCtrl = new DegreeController();
    private final UserDAO           userDAO    = new UserDAO();

    private JTable             table;
    private DefaultTableModel  model;
    private List<Student>      students = new ArrayList<>();

    private JTextField         searchField;
    private JComboBox<Object>  degreeFilterCombo;

    public AdminStudentsPanel() {
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
        loadData();
    }

    private void buildUI() {
        // --- HEADER ROW ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);
        
        JLabel title = new JLabel("Students");
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Manage student profiles, enrollments, and academic performance.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

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

        // Filters dropdown
        JPanel filtersWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filtersWrapper.setBackground(UITheme.BG_DARK);
        
        JLabel filterLbl = UITheme.label("FILTER BY:");
        degreeFilterCombo = UITheme.styledCombo();
        degreeFilterCombo.setPreferredSize(new Dimension(200, 36));
        degreeFilterCombo.addItem("All Degrees");
        List<Degree> degrees = degreeCtrl.getAllDegrees();
        for (Degree d : degrees) {
            degreeFilterCombo.addItem(d);
        }
        degreeFilterCombo.addActionListener(e -> performFilter());

        filtersWrapper.add(filterLbl);
        filtersWrapper.add(degreeFilterCombo);
        searchFilterPanel.add(filtersWrapper, BorderLayout.EAST);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setBackground(UITheme.BG_DARK);
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(12));
        topSection.add(searchFilterPanel);
        add(topSection, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"ID", "Student Number", "Full Name", "Email", "Mobile", "Degree", "Year"};
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
        
        JLabel pageInfo = new JLabel("Showing 1 to " + students.size() + " entries");
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
        students = ctrl.getAllStudents();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();
        Object selectedDegree = degreeFilterCombo.getSelectedItem();
        
        for (Student s : students) {
            // Text query matches
            boolean matchesQuery = s.getFullName().toLowerCase().contains(query) ||
                                   s.getStudentNumber().toLowerCase().contains(query) ||
                                   (s.getEmail() != null && s.getEmail().toLowerCase().contains(query));
            
            // Degree matches
            boolean matchesDegree = true;
            if (selectedDegree instanceof Degree) {
                matchesDegree = s.getDegreeId() == ((Degree) selectedDegree).getDegreeId();
            }
            
            if (matchesQuery && matchesDegree) {
                model.addRow(new Object[]{
                    s.getStudentId(),
                    s.getStudentNumber(),
                    s.getFullName(),
                    s.getEmail(),
                    s.getMobile(),
                    s.getDegreeName() != null ? s.getDegreeName() : "—",
                    s.getYearOfStudy()
                });
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select a student to edit."); return; }
        int id = (int) model.getValueAt(row, 0);
        Student found = null;
        for (Student s : students) {
            if (s.getStudentId() == id) { found = s; break; }
        }
        if (found != null) showForm(found);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select a student to delete."); return; }
        int id = (int) model.getValueAt(row, 0);
        if (ctrl.deleteStudent(id)) loadData();
    }

    private void showForm(Student student) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
            student == null ? "Add Student" : "Edit Student", true);
        dialog.setSize(500, 540);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField nameField    = UITheme.styledField();
        JTextField numField     = UITheme.styledField();
        JTextField emailField   = UITheme.styledField();
        JTextField mobileField  = UITheme.styledField();
        JTextField yearField    = UITheme.styledField();
        JTextField userField    = UITheme.styledField();
        JPasswordField passField= UITheme.styledPasswordField();
        JComboBox<Degree> degreeCombo = UITheme.styledCombo();

        List<Degree> degrees = degreeCtrl.getAllDegrees();
        for (Degree d : degrees) degreeCombo.addItem(d);

        if (student != null) {
            nameField.setText(student.getFullName());
            numField.setText(student.getStudentNumber());
            emailField.setText(student.getEmail());
            mobileField.setText(student.getMobile());
            yearField.setText(String.valueOf(student.getYearOfStudy()));
            userField.setEnabled(false);
            passField.setEnabled(false);
            userField.setText("(existing user)");
            for (int i = 0; i < degreeCombo.getItemCount(); i++) {
                if (((Degree) degreeCombo.getItemAt(i)).getDegreeId() == student.getDegreeId()) {
                    degreeCombo.setSelectedIndex(i); break;
                }
            }
        }

        int row = 0;
        row = addDialogRow(panel, gbc, row, "Full Name",       nameField);
        row = addDialogRow(panel, gbc, row, "Student Number",  numField);
        row = addDialogRow(panel, gbc, row, "Email",           emailField);
        row = addDialogRow(panel, gbc, row, "Mobile",          mobileField);
        row = addDialogRow(panel, gbc, row, "Year of Study",   yearField);

        gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Degree"), gbc);
        gbc.gridx = 1; panel.add(degreeCombo, gbc); row++;

        if (student == null) {
            row = addDialogRow(panel, gbc, row, "Username", userField);
            gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Password"), gbc);
            gbc.gridx = 1; panel.add(passField, gbc); row++;
        }

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton(student == null ? "Add Student" : "Save Changes");
        saveBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        saveBtn.addActionListener(e -> {
            Student s = student == null ? new Student() : student;
            s.setFullName(nameField.getText().trim());
            s.setStudentNumber(numField.getText().trim());
            s.setEmail(emailField.getText().trim());
            s.setMobile(mobileField.getText().trim());
            try { s.setYearOfStudy(Integer.parseInt(yearField.getText().trim())); } catch (NumberFormatException ex) { s.setYearOfStudy(1); }
            Degree deg = (Degree) degreeCombo.getSelectedItem();
            if (deg != null) s.setDegreeId(deg.getDegreeId());

            boolean ok;
            if (student == null) {
                String uname = userField.getText().trim();
                String pwd   = new String(passField.getPassword()).trim();
                User newUser = new User(0, uname, pwd, "STUDENT", s.getEmail());
                int uid = userDAO.insert(newUser);
                if (uid < 0) { JOptionPane.showMessageDialog(dialog, "Failed to create user account."); return; }
                s.setUserId(uid);
                ok = ctrl.addStudent(s);
            } else {
                ok = ctrl.updateStudent(s);
            }
            if (ok) { dialog.dispose(); loadData(); }
        });
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private int addDialogRow(JPanel p, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.insets = new Insets(6, 6, 6, 6);
        p.add(UITheme.label(label), gbc);
        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(250, 36));
        p.add(field, gbc);
        return row + 1;
    }
}
