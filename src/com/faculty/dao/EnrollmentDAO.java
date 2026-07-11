package com.faculty.dao;

import com.faculty.model.Enrollment;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * E n r o l l m e n t   D A O
 */
public class EnrollmentDAO {

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT e.*, c.course_name, c.course_code FROM enrollments e " +
                     "JOIN courses c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapEnrollment(rs));
            }
        } catch (SQLException e) {
            System.err.println("EnrollmentDAO.findByStudentId error: " + e.getMessage());
        }
        return list;
    }

    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT e.*, s.full_name AS student_name, c.course_name, c.course_code FROM enrollments e " +
                     "JOIN students s ON e.student_id = s.student_id " +
                     "JOIN courses  c ON e.course_id  = c.course_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Enrollment enr = mapEnrollment(rs);
                try { enr.setStudentName(rs.getString("student_name")); } catch (SQLException ignored) {}
                list.add(enr);
            }
        } catch (SQLException e) {
            System.err.println("EnrollmentDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public boolean updateGrade(int enrollmentId, String grade) {
        String sql = "UPDATE enrollments SET grade = ? WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grade);
            ps.setInt(2, enrollmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("EnrollmentDAO.updateGrade error: " + e.getMessage());
        }
        return false;
    }

    public boolean insert(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("EnrollmentDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("EnrollmentDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Enrollment mapEnrollment(ResultSet rs) throws SQLException {
        Enrollment e = new Enrollment();
        e.setEnrollmentId(rs.getInt("enrollment_id"));
        e.setStudentId(rs.getInt("student_id"));
        e.setCourseId(rs.getInt("course_id"));
        e.setGrade(rs.getString("grade"));
        try { e.setCourseName(rs.getString("course_name")); } catch (SQLException ignored) {}
        try { e.setCourseCode(rs.getString("course_code")); } catch (SQLException ignored) {}
        return e;
    }
}
