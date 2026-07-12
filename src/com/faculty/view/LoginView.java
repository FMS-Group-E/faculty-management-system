package com.faculty.view;

import com.faculty.controller.LoginController;
import com.faculty.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        loginButton;
    private JLabel         errorLabel;

    public LoginView() {
        UITheme.setupLookAndFeel();
        initComponents();
        new LoginController(this);
    }

    private void initComponents() {
        setTitle("Faculty Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with dark background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.BG_DARK);
        setContentPane(mainPanel);

        // Center card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        // Title
        JLabel titleLabel = new JLabel("FacultyPortal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Academic Administration System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(UITheme.TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(UITheme.BORDER_COLOR);
        sep.setBackground(UITheme.BORDER_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));


        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UITheme.BG_CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets   = new Insets(6, 0, 6, 0);
        gbc.fill     = GridBagConstraints.HORIZONTAL;
        gbc.weightx  = 1.0;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(UITheme.label("USERNAME"), gbc);
        gbc.gridy = 1;
        usernameField = UITheme.styledField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridy = 2;
        formPanel.add(UITheme.label("PASSWORD"), gbc);
        gbc.gridy = 3;
        passwordField = UITheme.styledPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        formPanel.add(passwordField, gbc);

        // Error label
        gbc.gridy = 4;
        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER);
        formPanel.add(errorLabel, gbc);

        // Login button
        gbc.gridy = 5;
        loginButton = UITheme.primaryButton("Sign In");
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        loginButton.setPreferredSize(new Dimension(300, 44));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        formPanel.add(loginButton, gbc);

        card.add(titleLabel);
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(sep);
        card.add(Box.createVerticalStrut(24));
        card.add(formPanel);

        // Outer padding
        JPanel outerPad = new JPanel(new GridBagLayout());
        outerPad.setBackground(UITheme.BG_DARK);
        outerPad.add(card);
        mainPanel.add(outerPad, BorderLayout.CENTER);

    }

    public JTextField     getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JButton        getLoginButton()   { return loginButton;   }

    public void showError(String msg) {
        errorLabel.setText(msg);
    }
}
