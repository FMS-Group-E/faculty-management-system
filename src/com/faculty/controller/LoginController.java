package com.faculty.controller;

import com.faculty.dao.UserDAO;
import com.faculty.dao.StudentDAO;
import com.faculty.dao.LecturerDAO;
import com.faculty.model.User;
import com.faculty.model.Student;
import com.faculty.model.Lecturer;
import com.faculty.view.LoginView;
import com.faculty.view.AdminDashboardView;
import com.faculty.view.StudentDashboardView;
import com.faculty.view.LecturerDashboardView;

import javax.swing.*;

/**
 * Controller: Handles login authentication and role-based navigation.
 */
public class LoginController {

    private final LoginView   view;
    private final UserDAO     userDAO     = new UserDAO();
    private final StudentDAO  studentDAO  = new StudentDAO();
    private final LecturerDAO lecturerDAO = new LecturerDAO();

    public LoginController(LoginView view) {
        this.view = view;
        initListeners();
    }

    private void initListeners() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getPasswordField().addActionListener(e -> handleLogin()); // Enter key
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            view.showError("Please enter username and password.");
            return;
        }

        User user = userDAO.authenticate(username, password);

        if (user == null) {
            view.showError("Invalid username or password.");
            return;
        }

        view.dispose();

        switch (user.getRole()) {
            case "ADMIN":
                SwingUtilities.invokeLater(() -> new AdminDashboardView(user).setVisible(true));
                break;
            case "STUDENT":
                Student student = studentDAO.findByUserId(user.getUserId());
                SwingUtilities.invokeLater(() -> new StudentDashboardView(user, student).setVisible(true));
                break;
            case "LECTURER":
                Lecturer lecturer = lecturerDAO.findByUserId(user.getUserId());
                SwingUtilities.invokeLater(() -> new LecturerDashboardView(user, lecturer).setVisible(true));
                break;
            default:
                view.showError("Unknown role: " + user.getRole());
        }
    }
}
