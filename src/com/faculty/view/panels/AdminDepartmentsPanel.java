package com.faculty.view.panels;

import com.faculty.controller.DepartmentController;
import com.faculty.model.Department;
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
 * Panel: Admin - Department CRUD management with search and filters.
 */
public class AdminDepartmentsPanel extends JPanel {

    private final DepartmentController ctrl = new DepartmentController();
    private JTable            table;
    private DefaultTableModel model;
    private List<Department>  departments = new ArrayList<>();

    private JTextField        searchField;

    public AdminDepartmentsPanel() {
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
        
        JLabel title = new JLabel("Departments");
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Manage faculty departments, heads of department, and staffing counts.");
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

        // --- SEARCH ROW ---
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

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setBackground(UITheme.BG_DARK);
        topSection.add(headerPanel);
        topSection.add(Box.createVerticalStrut(12));
        topSection.add(searchFilterPanel);
        add(topSection, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"ID", "Department Name", "Head of Department", "Staff Count", "Description"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(4).setPreferredWidth(250);
        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- PAGINATION FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        JLabel pageInfo = new JLabel("Showing 1 to " + departments.size() + " entries");
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
        departments = ctrl.getAllDepartments();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();
        
        for (Department d : departments) {
            boolean matchesQuery = d.getDeptName().toLowerCase().contains(query) ||
                                   (d.getHodName() != null && d.getHodName().toLowerCase().contains(query)) ||
                                   (d.getDescription() != null && d.getDescription().toLowerCase().contains(query));
            
            if (matchesQuery) {
                model.addRow(new Object[]{
                    d.getDepartmentId(),
                    d.getDeptName(),
                    d.getHodName() != null ? d.getHodName() : "—",
                    d.getStaffCount(),
                    d.getDescription() != null ? d.getDescription() : "—"
                });
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a department to edit."); return; }
        int id = (int) model.getValueAt(row, 0);
        Department found = null;
        for (Department d : departments) {
            if (d.getDepartmentId() == id) { found = d; break; }
        }
        if (found != null) showForm(found);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a department to delete."); return; }
        int id = (int) model.getValueAt(row, 0);
        if (ctrl.deleteDepartment(id)) loadData();
    }

    private void showForm(Department dept) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
            dept == null ? "Add Department" : "Edit Department", true);
        dialog.setSize(460, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx= 1.0;

        JTextField nameField  = UITheme.styledField();
        JTextField hodField   = UITheme.styledField();
        JTextField countField = UITheme.styledField();
        JTextArea  descArea   = UITheme.styledTextArea();

        if (dept != null) {
            nameField.setText(dept.getDeptName());
            hodField.setText(dept.getHodName());
            countField.setText(String.valueOf(dept.getStaffCount()));
            descArea.setText(dept.getDescription());
        }

        int row = 0;
        row = addRow(panel, gbc, row, "Department Name", nameField);
        row = addRow(panel, gbc, row, "Head of Dept",    hodField);
        row = addRow(panel, gbc, row, "Staff Count",     countField);

        gbc.gridx = 0; gbc.gridy = row; panel.add(UITheme.label("Description"), gbc);
        gbc.gridx = 1;
        JScrollPane descSP = new JScrollPane(descArea);
        descSP.setPreferredSize(new Dimension(250, 70));
        panel.add(descSP, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton(dept == null ? "Add Department" : "Save Changes");
        saveBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        saveBtn.addActionListener(e -> {
            Department d = dept == null ? new Department() : dept;
            d.setDeptName(nameField.getText().trim());
            d.setHodName(hodField.getText().trim());
            try { d.setStaffCount(Integer.parseInt(countField.getText().trim())); } catch (NumberFormatException ex) { d.setStaffCount(0); }
            d.setDescription(descArea.getText().trim());

            boolean ok = dept == null ? ctrl.addDepartment(d) : ctrl.updateDepartment(d);
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
