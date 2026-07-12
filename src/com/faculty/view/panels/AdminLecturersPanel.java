package com.faculty.view.panels;

import com.faculty.controller.LecturerController;
import com.faculty.controller.DepartmentController;
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
 *  Lecturer Panel
 */
public class AdminLecturersPanel extends JPanel {

    private final LecturerController   ctrl    = new LecturerController();
    private final DepartmentController deptCtrl= new DepartmentController();
    private final UserDAO              userDAO = new UserDAO();

    private JTable            table;
    private DefaultTableModel model;
    private List<Lecturer>    lecturers = new ArrayList<>();
    
    private JTextField        searchField;
    private JComboBox<Object> deptFilterCombo;

    public AdminLecturersPanel() {
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
        loadData();
    }

    private void buildUI() {
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);
        
        JLabel title = new JLabel("Lecturers");
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Manage academic staff profiles, tenure status, and departmental assignments.");
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

        // SEARCH & FILTER
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
        
        // Placeholder setup
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { performFilter(); }
            public void removeUpdate(DocumentEvent e) { performFilter(); }
            public void changedUpdate(DocumentEvent e) { performFilter(); }
        });
        
        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchWrapper.setBackground(UITheme.BG_DARK);
        searchWrapper.add(searchField);
        searchFilterPanel.add(searchWrapper, BorderLayout.WEST);

        // Department filter
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

        // Combine Header & Search bar
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setBackground(UITheme.BG_DARK);
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(12));
        topSection.add(searchFilterPanel);
        add(topSection, BorderLayout.NORTH);

        // Table part
        String[] cols = {"ID", "Employee ID", "Full Name", "Email", "Mobile", "Department", "Specialization"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = UITheme.styledScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Footer with pagaination
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        JLabel pageInfo = new JLabel("Showing 1 to " + lecturers.size() + " entries");
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
        lecturers = ctrl.getAllLecturers();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();
        Object selectedDept = deptFilterCombo.getSelectedItem();
        
        for (Lecturer l : lecturers) {
            // Text filter
            boolean matchesQuery = l.getFullName().toLowerCase().contains(query) ||
                                   l.getEmployeeId().toLowerCase().contains(query) ||
                                   (l.getEmail() != null && l.getEmail().toLowerCase().contains(query));
            
            // Department filter
            boolean matchesDept = true;
            if (selectedDept instanceof Department) {
                matchesDept = l.getDepartmentId() == ((Department) selectedDept).getDepartmentId();
            }
            
            if (matchesQuery && matchesDept) {
                model.addRow(new Object[]{
                    l.getLecturerId(),
                    l.getEmployeeId(),
                    l.getFullName(),
                    l.getEmail(),
                    l.getMobile(),
                    l.getDepartmentName() != null ? l.getDepartmentName() : "—",
                    l.getSpecialization() != null ? l.getSpecialization() : "—"
                });
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a lecturer to edit."); return; }
        int id = (int) model.getValueAt(row, 0);
        Lecturer found = null;
        for (Lecturer l : lecturers) {
            if (l.getLecturerId() == id) { found = l; break; }
        }
        if (found != null) showForm(found);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a lecturer to delete."); return; }
        int id = (int) model.getValueAt(row, 0);
        if (ctrl.deleteLecturer(id)) loadData();
    }

    private void showForm(Lecturer lecturer) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
            lecturer == null ? "Add Lecturer" : "Edit Lecturer", true);
        dialog.setSize(500, 520);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx= 1.0;

        JTextField nameField = UITheme.styledField();
        JTextField empField  = UITheme.styledField();
        JTextField emailField= UITheme.styledField();
        JTextField mobField  = UITheme.styledField();
        JTextField specField = UITheme.styledField();
        JTextField userField = UITheme.styledField();
        JPasswordField passField = UITheme.styledPasswordField();
        JComboBox<Department> deptCombo = UITheme.styledCombo();

        List<Department> depts = deptCtrl.getAllDepartments();
        for (Department d : depts) deptCombo.addItem(d);

        if (lecturer != null) {
            nameField.setText(lecturer.getFullName());
            empField.setText(lecturer.getEmployeeId());
            emailField.setText(lecturer.getEmail());
            mobField.setText(lecturer.getMobile());
            specField.setText(lecturer.getSpecialization());
            userField.setEnabled(false); passField.setEnabled(false);
            userField.setText("(existing user)");
            for (int i = 0; i < deptCombo.getItemCount(); i++) {
                if (((Department) deptCombo.getItemAt(i)).getDepartmentId() == lecturer.getDepartmentId()) {
                    deptCombo.setSelectedIndex(i); break;
                }
            }
        }

        int row = 0;
        row = addRow(panel, gbc, row, "Full Name",      nameField);
        row = addRow(panel, gbc, row, "Employee ID",    empField);
        row = addRow(panel, gbc, row, "Email",          emailField);
        row = addRow(panel, gbc, row, "Mobile",         mobField);
        row = addRow(panel, gbc, row, "Specialization", specField);
        gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Department"), gbc);
        gbc.gridx = 1; panel.add(deptCombo, gbc); row++;
        if (lecturer == null) {
            row = addRow(panel, gbc, row, "Username", userField);
            gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Password"), gbc);
            gbc.gridx = 1; panel.add(passField, gbc); row++;
        }

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton(lecturer == null ? "Add Lecturer" : "Save Changes");
        saveBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        saveBtn.addActionListener(e -> {
            Lecturer l = lecturer == null ? new Lecturer() : lecturer;
            l.setFullName(nameField.getText().trim());
            l.setEmployeeId(empField.getText().trim());
            l.setEmail(emailField.getText().trim());
            l.setMobile(mobField.getText().trim());
            l.setSpecialization(specField.getText().trim());
            Department dept = (Department) deptCombo.getSelectedItem();
            if (dept != null) l.setDepartmentId(dept.getDepartmentId());

            boolean ok;
            if (lecturer == null) {
                String uname = userField.getText().trim();
                String pwd   = new String(passField.getPassword()).trim();
                User newUser = new User(0, uname, pwd, "LECTURER", l.getEmail());
                int uid = userDAO.insert(newUser);
                if (uid < 0) { JOptionPane.showMessageDialog(dialog, "Failed to create user account."); return; }
                l.setUserId(uid);
                ok = ctrl.addLecturer(l);
            } else {
                ok = ctrl.updateLecturer(l);
            }
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
