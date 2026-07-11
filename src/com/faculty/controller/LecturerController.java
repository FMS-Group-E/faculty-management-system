package com.faculty.controller;

import com.faculty.dao.LecturerDAO;
import com.faculty.model.Lecturer;

import javax.swing.*;
import java.util.List;

/**
 *Lecturer management.
 */
public class LecturerController {

    private final LecturerDAO lecturerDAO = new LecturerDAO();

    public List<Lecturer> getAllLecturers() {
        return lecturerDAO.findAll();
    }

    public Lecturer getLecturer(int id) {
        return lecturerDAO.findById(id);
    }

    public boolean addLecturer(Lecturer lecturer) {
        if (!validate(lecturer)) return false;
        boolean ok = lecturerDAO.insert(lecturer);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to add lecturer. Employee ID may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateLecturer(Lecturer lecturer) {
        if (!validate(lecturer)) return false;
        boolean ok = lecturerDAO.update(lecturer);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to update lecturer.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean deleteLecturer(int lecturerId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this lecturer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return false;
        boolean ok = lecturerDAO.delete(lecturerId);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to delete lecturer.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateLecturerProfile(Lecturer lecturer) {
        return lecturerDAO.update(lecturer);
    }

    private boolean validate(Lecturer l) {
        if (l.getFullName() == null || l.getFullName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lecturer name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (l.getEmployeeId() == null || l.getEmployeeId().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Employee ID is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
