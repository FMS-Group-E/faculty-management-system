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
        setSize(680, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        // Root split panel
        JPanel root = new JPanel(new GridLayout(1, 2));
        setContentPane(root);

        // ── LEFT PANEL (blue gradient) ─────────────────────────────────────
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, UITheme.PRIMARY,
                    0, getHeight(), UITheme.PRIMARY_DARK
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        leftPanel.add(Box.createVerticalGlue());

        JLabel sysTitle = new JLabel("<html><center>Faculty Management<br>System</center></html>", SwingConstants.CENTER);
        sysTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
        sysTitle.setForeground(Color.WHITE);
        sysTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(sysTitle);

        leftPanel.add(Box.createVerticalGlue());

        JLabel facLabel = new JLabel("Faculty of Computing & Technology", SwingConstants.CENTER);
        facLabel.setFont(new Font("Helvetica", Font.BOLD, 11));
        facLabel.setForeground(new Color(191, 219, 254));
        facLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(facLabel);

        leftPanel.add(Box.createVerticalStrut(6));

        JLabel tagLine = new JLabel("Manage your academic journey", SwingConstants.CENTER);
        tagLine.setFont(new Font("Helvetica", Font.PLAIN, 11));
        tagLine.setForeground(new Color(147, 197, 253));
        tagLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(tagLine);

        // ── RIGHT PANEL (white) ────────────────────────────────────────────
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 36, 40, 36));

        // "Sign In" heading
        JLabel signInHeading = new JLabel("Sign In");
        signInHeading.setFont(new Font("Helvetica", Font.BOLD, 20));
        signInHeading.setForeground(UITheme.TEXT_PRIMARY);
        signInHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(signInHeading);

        // Underline accent
        JPanel accent = new JPanel();
        accent.setBackground(UITheme.PRIMARY);
        accent.setMaximumSize(new Dimension(40, 3));
        accent.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(4));
        rightPanel.add(accent);
        rightPanel.add(Box.createVerticalStrut(24));

        // ── Form ──────────────────────────────────────────────────────────
        // Username
        JLabel userLbl = fieldLabel("Username");
        userLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(userLbl);
        rightPanel.add(Box.createVerticalStrut(5));

        usernameField = UITheme.styledField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(usernameField);
        rightPanel.add(Box.createVerticalStrut(14));

        // Password
        JLabel passLbl = fieldLabel("Password");
        passLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(passLbl);
        rightPanel.add(Box.createVerticalStrut(5));

        passwordField = UITheme.styledPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(passwordField);
        rightPanel.add(Box.createVerticalStrut(16));

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(errorLabel);
        rightPanel.add(Box.createVerticalStrut(4));

        // Sign In button
        loginButton = UITheme.primaryButton("Sign In");
        loginButton.setFont(new Font("Helvetica", Font.BOLD, 15));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(loginButton);

        root.add(leftPanel);
        root.add(rightPanel);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private JLabel fieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Helvetica", Font.BOLD, 13));
        lbl.setForeground(UITheme.PRIMARY);
        return lbl;
    }

    // ── Accessors used by LoginController ────────────────────────────────────
    public JTextField     getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JButton        getLoginButton()   { return loginButton;   }

    public void showError(String msg) {
        errorLabel.setText(msg);
    }
}
