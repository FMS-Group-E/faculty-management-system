package com.faculty.main;

import com.faculty.view.LoginView;
import javax.swing.SwingUtilities;

/**
 * Main Entry Point for Faculty Management System application.
 */
public class Main {
    public static void main(String[] args) {
        // Run Swing GUI in Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
