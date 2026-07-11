package com.faculty.controller;

import com.faculty.dao.StudentDAO;
import com.faculty.model.Student;

import javax.swing.*;
import java.util.List;

/**
 * Controller: Business logic for Student management (Admin CRUD).
 */
public class StudentController {

    private final StudentDAO studentDAO = new StudentDAO();

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public Student getStudent(int id) {
        return studentDAO.findById(id);
    }

    public boolean addStudent(Student student) {
        if (!validate(student)) return false;
        boolean ok = studentDAO.insert(student);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to add student. Student number may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateStudent(Student student) {
        if (!validate(student)) return false;
        boolean ok = studentDAO.update(student);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to update student.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean deleteStudent(int studentId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return false;
        boolean ok = studentDAO.delete(studentId);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateStudentProfile(Student student) {
        return studentDAO.update(student);
    }

    private boolean validate(Student s) {
        if (s.getFullName() == null || s.getFullName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (s.getStudentNumber() == null || s.getStudentNumber().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student number is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
