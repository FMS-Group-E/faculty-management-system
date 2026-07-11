package com.faculty.controller;

import com.faculty.dao.CourseDAO;
import com.faculty.model.Course;

import javax.swing.*;
import java.util.List;

public class CourseController {

    private final CourseDAO courseDAO = new CourseDAO();

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public Course getCourse(int id) {
        return courseDAO.findById(id);
    }

    public List<Course> getCoursesByLecturer(int lecturerId) {
        return courseDAO.findByLecturerId(lecturerId);
    }

    public boolean addCourse(Course course) {
        if (!validate(course)) return false;
        boolean ok = courseDAO.insert(course);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to add course. Course code may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean updateCourse(Course course) {
        if (!validate(course)) return false;
        boolean ok = courseDAO.update(course);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to update course.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    public boolean deleteCourse(int courseId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return false;
        boolean ok = courseDAO.delete(courseId);
        if (!ok) JOptionPane.showMessageDialog(null, "Failed to delete course.", "Error", JOptionPane.ERROR_MESSAGE);
        return ok;
    }

    private boolean validate(Course c) {
        if (c.getCourseCode() == null || c.getCourseCode().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course code is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (c.getCourseName() == null || c.getCourseName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
