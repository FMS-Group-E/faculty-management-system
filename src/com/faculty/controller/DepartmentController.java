package com.faculty.controller;

import com.faculty.dao.DepartmentDAO;
import com.faculty.model.Department;

import javax.swing.*;
import java.util.List;

public class DepartmentController {

    private final DepartmentDAO deptDAO = new DepartmentDAO();

    public List<Department> getAllDepartments() {
        return deptDAO.findAll();
    }

    public Department getDepartment(int id) {
        return deptDAO.findById(id);
    }

    public boolean addDepartment(Department dept) {
        if (!validate(dept)) return false;
        boolean ok = deptDAO.insert(dept);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to add department.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateDepartment(Department dept) {
        if (!validate(dept)) return false;
        boolean ok = deptDAO.update(dept);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to update department.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean deleteDepartment(int departmentId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this department?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return false;
        boolean ok = deptDAO.delete(departmentId);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to delete department. It may still have associated records.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    private boolean validate(Department d) {
        if (d.getDeptName() == null || d.getDeptName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
