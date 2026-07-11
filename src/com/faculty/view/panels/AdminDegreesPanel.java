package com.faculty.view.panels;

import com.faculty.controller.DegreeController;
import com.faculty.controller.DepartmentController;
import com.faculty.model.Degree;
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
 * Panel: Admin - Degree CRUD management with search and filters.
 */
public class AdminDegreesPanel extends JPanel {

    private final DegreeController ctrl = new DegreeController();
    private final DepartmentController deptCtrl = new DepartmentController();
    private JTable table;
    private DefaultTableModel model;
    private List<Degree> degrees = new ArrayList<>();

    private JTextField        searchField;
    private JComboBox<Object> deptFilterCombo;

    public AdminDegreesPanel() {
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
        
        JLabel title = new JLabel("Degree Directory");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Manage faculty degree programs, code systems, and durations.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Right Actions (Add / Edit / Delete)
        JPanel actionBtnGrid = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBtnGrid.setBackground(UITheme.BG_DARK);
        
        JButton addBtn    = UITheme.primaryButton("Add Degree");
        addBtn.setIcon(new LucideIcon("degree", 14, Color.WHITE));
        addBtn.setIconTextGap(8);
        
        JButton editBtn   = UITheme.successButton("Edit");
        editBtn.setIcon(new LucideIcon("edit", 14, Color.WHITE));
        editBtn.setIconTextGap(8);
        
        JButton deleteBtn = UITheme.dangerButton("Delete");
        deleteBtn.setIcon(new LucideIcon("trash", 14, Color.WHITE));
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
        String[] cols = {"ID", "Degree Code", "Degree Name", "Department", "Duration (Years)", "Description"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(5).setPreferredWidth(250);
        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // --- PAGINATION FOOTER ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        JLabel pageInfo = new JLabel("Showing 1 to " + degrees.size() + " entries");
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
        degrees = ctrl.getAllDegrees();
        performFilter();
    }

    private void performFilter() {
        model.setRowCount(0);
        String query = searchField.getText().toLowerCase().trim();
        Object selectedDept = deptFilterCombo.getSelectedItem();
        
        for (Degree d : degrees) {
            // Text search
            boolean matchesQuery = d.getDegreeName().toLowerCase().contains(query) ||
                                   d.getDegreeCode().toLowerCase().contains(query) ||
                                   (d.getDescription() != null && d.getDescription().toLowerCase().contains(query));
            
            // Department filter
            boolean matchesDept = true;
            if (selectedDept instanceof Department) {
                matchesDept = d.getDepartmentId() == ((Department) selectedDept).getDepartmentId();
            }
            
            if (matchesQuery && matchesDept) {
                model.addRow(new Object[]{
                    d.getDegreeId(),
                    d.getDegreeCode(),
                    d.getDegreeName(),
                    d.getDepartmentName() != null ? d.getDepartmentName() : "—",
                    d.getDurationYears(),
                    d.getDescription() != null ? d.getDescription() : "—"
                });
            }
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a degree to edit."); return; }
        int id = (int) model.getValueAt(row, 0);
        Degree found = null;
        for (Degree d : degrees) {
            if (d.getDegreeId() == id) { found = d; break; }
        }
        if (found != null) showForm(found);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a degree to delete."); return; }
        int id = (int) model.getValueAt(row, 0);
        if (ctrl.deleteDegree(id)) loadData();
    }

    private void showForm(Degree degree) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
            degree == null ? "Add Degree" : "Edit Degree", true);
        dialog.setSize(460, 420);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField codeField = UITheme.styledField();
        JTextField nameField = UITheme.styledField();
        JTextField durField = UITheme.styledField();
        JTextArea descArea = UITheme.styledTextArea();
        JComboBox<Department> deptCombo = UITheme.styledCombo();

        List<Department> depts = deptCtrl.getAllDepartments();
        for (Department dept : depts) {
            deptCombo.addItem(dept);
        }

        if (degree != null) {
            codeField.setText(degree.getDegreeCode());
            nameField.setText(degree.getDegreeName());
            durField.setText(String.valueOf(degree.getDurationYears()));
            descArea.setText(degree.getDescription());
            for (int i = 0; i < deptCombo.getItemCount(); i++) {
                if (((Department) deptCombo.getItemAt(i)).getDepartmentId() == degree.getDepartmentId()) {
                    deptCombo.setSelectedIndex(i);
                    break;
                }
            }
        }

        int row = 0;
        row = addRow(panel, gbc, row, "Degree Code", codeField);
        row = addRow(panel, gbc, row, "Degree Name", nameField);
        row = addRow(panel, gbc, row, "Duration (Years)", durField);

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label("Department"), gbc);
        gbc.gridx = 1;
        panel.add(deptCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label("Description"), gbc);
        gbc.gridx = 1;
        JScrollPane descSP = new JScrollPane(descArea);
        descSP.setPreferredSize(new Dimension(250, 70));
        panel.add(descSP, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton(degree == null ? "Add Degree" : "Save Changes");
        saveBtn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        saveBtn.addActionListener(e -> {
            Degree d = degree == null ? new Degree() : degree;
            d.setDegreeCode(codeField.getText().trim());
            d.setDegreeName(nameField.getText().trim());
            try {
                d.setDurationYears(Integer.parseInt(durField.getText().trim()));
            } catch (NumberFormatException ex) {
                d.setDurationYears(3);
            }
            d.setDescription(descArea.getText().trim());
            Department dept = (Department) deptCombo.getSelectedItem();
            if (dept != null) {
                d.setDepartmentId(dept.getDepartmentId());
            }

            boolean ok = degree == null ? ctrl.addDegree(d) : ctrl.updateDegree(d);
            if (ok) {
                dialog.dispose();
                loadData();
            }
        });
        panel.add(saveBtn, gbc);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private int addRow(JPanel p, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.insets = new Insets(6, 6, 6, 6);
        p.add(UITheme.label(label), gbc);
        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(250, 36));
        p.add(field, gbc);
        return row + 1;
    }
}
