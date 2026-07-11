package com.faculty.view.panels;

import com.faculty.model.Lecturer;
import com.faculty.dao.TimetableDAO;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Lecturer timetable
 */
public class LecturerTimetablePanel extends JPanel {

    private final Lecturer     lecturer;
    private final TimetableDAO dao = new TimetableDAO();
    private JTable            table;
    private DefaultTableModel model;
    private JLabel            pageInfo;

    public LecturerTimetablePanel(Lecturer lecturer) {
        this.lecturer = lecturer;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 16));
        buildUI();
        loadData();
    }

    private void buildUI() {
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UITheme.BG_DARK);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(UITheme.BG_DARK);

        JLabel title = new JLabel("My Timetable");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("View your weekly teaching schedule and assigned lecture locations.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Edit button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setBackground(UITheme.BG_DARK);
        JButton editBtn = UITheme.primaryButton("Edit Schedule");
        editBtn.setIcon(new LucideIcon("edit", 14, Color.WHITE));
        editBtn.setIconTextGap(8);
        editBtn.addActionListener(e -> showEditDialog());
        actionPanel.add(editBtn);
        headerPanel.add(actionPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Day", "Start", "End", "Course", "Code", "Location"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Hide Id column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        add(UITheme.styledScrollPane(table), BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(UITheme.BG_DARK);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        pageInfo = new JLabel("Showing 0 schedule entries");
        pageInfo.setFont(UITheme.FONT_SMALL);
        pageInfo.setForeground(UITheme.TEXT_MUTED);
        footerPanel.add(pageInfo, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        if (lecturer != null) {
            List<Object[]> rows = dao.getTimetableForLecturer(lecturer.getLecturerId());
            for (Object[] row : rows) model.addRow(row);
        }
        int count = model.getRowCount();
        pageInfo.setText("Showing " + count + " schedule entr" + (count != 1 ? "ies" : "y"));
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a timetable slot to edit.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int timetableId = (int) model.getValueAt(selectedRow, 0);
        String currentDay = (String) model.getValueAt(selectedRow, 1);
        String currentStart = (String) model.getValueAt(selectedRow, 2);
        String currentEnd = (String) model.getValueAt(selectedRow, 3);
        String courseName = (String) model.getValueAt(selectedRow, 4);
        String courseCode = (String) model.getValueAt(selectedRow, 5);
        String currentLocation = (String) model.getValueAt(selectedRow, 6);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Timetable Schedule", true);
        dialog.setSize(450, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UITheme.BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int r = 0;

        // Info
        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Course: " + courseCode + " - " + courseName);
        infoLabel.setFont(UITheme.FONT_HEADING);
        infoLabel.setForeground(UITheme.PRIMARY);
        panel.add(infoLabel, gbc);
        gbc.gridwidth = 1;
        r++;

        gbc.gridx = 0; gbc.gridy = r;
        panel.add(UITheme.label("Day of Week"), gbc);
        gbc.gridx = 1;
        JComboBox<String> dayCombo = UITheme.styledCombo();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (String day : days) {
            dayCombo.addItem(day);
        }
        dayCombo.setSelectedItem(currentDay);
        panel.add(dayCombo, gbc);
        r++;

        // Start time 
        gbc.gridx = 0; gbc.gridy = r;
        panel.add(UITheme.label("Start Time (HH:mm:ss)"), gbc);
        gbc.gridx = 1;
        JTextField startField = UITheme.styledField();
        startField.setText(currentStart);
        panel.add(startField, gbc);
        r++;

        // End time
        gbc.gridx = 0; gbc.gridy = r;
        panel.add(UITheme.label("End Time (HH:mm:ss)"), gbc);
        gbc.gridx = 1;
        JTextField endField = UITheme.styledField();
        endField.setText(currentEnd);
        panel.add(endField, gbc);
        r++;

        // Location 
        gbc.gridx = 0; gbc.gridy = r;
        panel.add(UITheme.label("Location"), gbc);
        gbc.gridx = 1;
        JTextField locField = UITheme.styledField();
        locField.setText(currentLocation);
        panel.add(locField, gbc);
        r++;

        // Action buttons
        gbc.gridx = 0; gbc.gridy = r; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        JButton saveBtn = UITheme.primaryButton("Save Changes");
        saveBtn.addActionListener(e -> {
            String selectedDay = (String) dayCombo.getSelectedItem();
            String startText = startField.getText().trim();
            String endText = endField.getText().trim();
            String locationText = locField.getText().trim();

            if (startText.isEmpty() || endText.isEmpty() || locationText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (dao.updateTimetableEntry(timetableId, selectedDay, startText, endText, locationText)) {
                dialog.dispose();
                loadData();
                JOptionPane.showMessageDialog(this, "Timetable schedule updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to update timetable entry. Check time format (HH:mm:ss).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
