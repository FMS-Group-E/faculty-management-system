package com.faculty.view.panels;

import com.faculty.model.User;
import com.faculty.model.Student;
import com.faculty.controller.StudentController;
import com.faculty.dao.DegreeDAO;
import com.faculty.model.Degree;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentProfilePanel extends JPanel {

    private final User              user;
    private       Student           student;
    private final StudentController ctrl = new StudentController();
    private final DegreeDAO         degreeDAO = new DegreeDAO();

    private JTextField  nameField, numField, emailField, mobileField, yearField;
    private JComboBox<Degree> degreeCombo;

    public StudentProfilePanel(User user, Student student, JFrame parentFrame) {
        this.user    = user;
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

        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("View and update your personal information and academic details.");
        subtitle.setFont(UITheme.FONT_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // --- FORM CARD ---
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(UITheme.cardBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 12, 8, 12);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;

        int row = 0;

        row = addFieldRow(card, gbc, row, "Full Name",      nameField  = UITheme.styledField());
        row = addFieldRow(card, gbc, row, "Student Number", numField   = UITheme.styledField());
        row = addFieldRow(card, gbc, row, "Email",          emailField = UITheme.styledField());
        row = addFieldRow(card, gbc, row, "Mobile",         mobileField= UITheme.styledField());
        row = addFieldRow(card, gbc, row, "Year of Study",  yearField  = UITheme.styledField());

        // Degree combo
        gbc.gridx = 0; gbc.gridy = row;
        card.add(UITheme.label("Degree"), gbc);
        gbc.gridx = 1;
        degreeCombo = UITheme.styledCombo();
        List<Degree> degrees = degreeDAO.findAll();
        for (Degree d : degrees) degreeCombo.addItem(d);
        card.add(degreeCombo, gbc);
        row++;

        // Populate
        if (student != null) {
            nameField.setText(student.getFullName());
            numField.setText(student.getStudentNumber());
            emailField.setText(student.getEmail());
            mobileField.setText(student.getMobile());
            yearField.setText(String.valueOf(student.getYearOfStudy()));
            for (int i = 0; i < degreeCombo.getItemCount(); i++) {
                if (((Degree) degreeCombo.getItemAt(i)).getDegreeId() == student.getDegreeId()) {
                    degreeCombo.setSelectedIndex(i); break;
                }
            }
        }

        // Save button
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 12, 12, 12);
        JButton saveBtn = UITheme.primaryButton("Save Changes");
        saveBtn.setIcon(new LucideIcon("edit", 14, Color.WHITE));
        saveBtn.setIconTextGap(8);
        saveBtn.setPreferredSize(new Dimension(200, 40));
        saveBtn.addActionListener(e -> saveProfile());
        card.add(saveBtn, gbc);
        row++;

        // Separator
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 12, 12, 12);
        JSeparator sep = new JSeparator();
        sep.setForeground(UITheme.BORDER_COLOR);
        card.add(sep, gbc);
        row++;

        // Section Title
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 12, 16, 12);
        JLabel pwdTitle = new JLabel("Security & Password");
        pwdTitle.setFont(UITheme.FONT_HEADING);
        pwdTitle.setForeground(UITheme.PRIMARY);
        card.add(pwdTitle, gbc);
        gbc.gridwidth = 1;
        row++;

        // Password fields
        JPasswordField currPassField = UITheme.styledPasswordField();
        JPasswordField newPassField = UITheme.styledPasswordField();
        JPasswordField confPassField = UITheme.styledPasswordField();

        row = addFieldRow(card, gbc, row, "Current Password", currPassField);
        row = addFieldRow(card, gbc, row, "New Password",     newPassField);
        row = addFieldRow(card, gbc, row, "Confirm Password", confPassField);

        // Update Password Button
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 12, 12, 12);
        JButton changePwdBtn = UITheme.primaryButton("Update Password");
        changePwdBtn.setIcon(new LucideIcon("lock", 14, Color.WHITE));
        changePwdBtn.setIconTextGap(8);
        changePwdBtn.setPreferredSize(new Dimension(200, 40));
        changePwdBtn.addActionListener(e -> {
            String currentInput = new String(currPassField.getPassword()).trim();
            String newInput = new String(newPassField.getPassword()).trim();
            String confirmInput = new String(confPassField.getPassword()).trim();

            if (currentInput.isEmpty() || newInput.isEmpty() || confirmInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All password fields are required.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!currentInput.equals(user.getPassword())) {
                JOptionPane.showMessageDialog(this, "Incorrect current password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!newInput.equals(confirmInput)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            com.faculty.dao.UserDAO udao = new com.faculty.dao.UserDAO();
            if (udao.updatePassword(user.getUserId(), newInput)) {
                user.setPassword(newInput);
                JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                currPassField.setText("");
                newPassField.setText("");
                confPassField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(changePwdBtn, gbc);
        row++;

        // Spacer to push card to top
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        card.add(Box.createVerticalGlue(), gbc);

        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setBackground(UITheme.BG_DARK);
        card.setPreferredSize(new Dimension(550, 780));
        cardWrapper.add(card, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(cardWrapper);
        sp.setBorder(null);
        sp.setBackground(UITheme.BG_DARK);
        sp.getViewport().setBackground(UITheme.BG_DARK);
        add(sp, BorderLayout.CENTER);
    }

    private int addFieldRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(UITheme.label(label), gbc);
        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(280, 36));
        panel.add(field, gbc);
        return row + 1;
    }

    private void saveProfile() {
        if (student == null) return;
        student.setFullName(nameField.getText().trim());
        student.setStudentNumber(numField.getText().trim());
        student.setEmail(emailField.getText().trim());
        student.setMobile(mobileField.getText().trim());
        try { student.setYearOfStudy(Integer.parseInt(yearField.getText().trim())); } catch (NumberFormatException ignored) {}
        Degree selected = (Degree) degreeCombo.getSelectedItem();
        if (selected != null) student.setDegreeId(selected.getDegreeId());

        if (ctrl.updateStudentProfile(student)) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
