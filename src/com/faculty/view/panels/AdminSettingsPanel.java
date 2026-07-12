package com.faculty.view.panels;

import com.faculty.model.User;
import com.faculty.util.UITheme;
import com.faculty.util.LucideIcon;

import javax.swing.*;
import java.awt.*;

/**
 * Panel: Admin settings for account info and changing the admin password.
 */
public class AdminSettingsPanel extends JPanel {

    private final User user;

    public AdminSettingsPanel(User user) {
        this.user = user;
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

        JLabel title = new JLabel("Admin Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Update administrator security and configuration parameters.");
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

        // Username
        JTextField usernameField = UITheme.styledField();
        usernameField.setText(user.getUsername());
        usernameField.setEnabled(false);
        row = addFieldRow(card, gbc, row, "Username", usernameField);

        // Email
        JTextField emailField = UITheme.styledField();
        emailField.setText(user.getEmail() != null ? user.getEmail() : "admin@facultyportal.com");
        emailField.setEnabled(false);
        row = addFieldRow(card, gbc, row, "Email Address", emailField);

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
        card.setPreferredSize(new Dimension(550, 680));
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
}
