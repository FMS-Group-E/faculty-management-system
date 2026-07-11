package com.faculty.dao;

import com.faculty.model.Student;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: Full CRUD for Student.
 */
public class StudentDAO {

    private static final String SELECT_BASE =
        "SELECT s.*, dg.degree_name FROM students s " +
        "LEFT JOIN degrees dg ON s.degree_id = dg.degree_id ";

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_BASE + "ORDER BY s.full_name")) {
            while (rs.next()) list.add(mapStudent(rs));
        } catch (SQLException e) {
            System.err.println("StudentDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public Student findById(int id) {
        String sql = SELECT_BASE + "WHERE s.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapStudent(rs);
            }
        } catch (SQLException e) {
            System.err.println("StudentDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    public Student findByUserId(int userId) {
        String sql = SELECT_BASE + "WHERE s.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapStudent(rs);
            }
        } catch (SQLException e) {
            System.err.println("StudentDAO.findByUserId error: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Student s) {
        String sql = "INSERT INTO students (user_id, full_name, student_number, email, mobile, degree_id, year_of_study) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getFullName());
            ps.setString(3, s.getStudentNumber());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getMobile());
            ps.setInt(6, s.getDegreeId());
            ps.setInt(7, s.getYearOfStudy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("StudentDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Student s) {
        String sql = "UPDATE students SET full_name=?, student_number=?, email=?, mobile=?, degree_id=?, year_of_study=? WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setString(2, s.getStudentNumber());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getMobile());
            ps.setInt(5, s.getDegreeId());
            ps.setInt(6, s.getYearOfStudy());
            ps.setInt(7, s.getStudentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("StudentDAO.update error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("StudentDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setUserId(rs.getInt("user_id"));
        s.setFullName(rs.getString("full_name"));
        s.setStudentNumber(rs.getString("student_number"));
        s.setEmail(rs.getString("email"));
        s.setMobile(rs.getString("mobile"));
        s.setDegreeId(rs.getInt("degree_id"));
        s.setYearOfStudy(rs.getInt("year_of_study"));
        try { s.setDegreeName(rs.getString("degree_name")); } catch (SQLException ignored) {}
        return s;
    }
}
