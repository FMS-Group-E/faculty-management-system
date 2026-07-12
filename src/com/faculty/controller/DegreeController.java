package com.faculty.controller;

import com.faculty.dao.DegreeDAO;
import com.faculty.model.Degree;

import javax.swing.*;
import java.util.List;

public class DegreeController {

    private final DegreeDAO degreeDAO = new DegreeDAO();

    public List<Degree> getAllDegrees() {
        return degreeDAO.findAll();
    }

    public Degree getDegree(int id) {
        return degreeDAO.findById(id);
    }

    public boolean addDegree(Degree degree) {
        if (!validate(degree)) return false;
        boolean ok = degreeDAO.insert(degree);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to add degree. Code may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateDegree(Degree degree) {
        if (!validate(degree)) return false;
        boolean ok = degreeDAO.update(degree);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to update degree.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean deleteDegree(int degreeId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this degree?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return false;
        boolean ok = degreeDAO.delete(degreeId);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to delete degree. It may still have associated records.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    private boolean validate(Degree d) {
        if (d.getDegreeName() == null || d.getDegreeName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Degree name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (d.getDegreeCode() == null || d.getDegreeCode().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Degree code is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
