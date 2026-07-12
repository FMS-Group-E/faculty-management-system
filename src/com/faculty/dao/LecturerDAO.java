package com.faculty.dao;

import com.faculty.model.Lecturer;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lecturer DAO
 */
public class LecturerDAO {

    private static final String SELECT_BASE =
        "SELECT l.*, d.dept_name FROM lecturers l " +
        "LEFT JOIN departments d ON l.department_id = d.department_id ";

    public List<Lecturer> findAll() {
        List<Lecturer> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_BASE + "ORDER BY l.full_name")) {
            while (rs.next()) list.add(mapLecturer(rs));
        } catch (SQLException e) {
            System.err.println("LecturerDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public Lecturer findById(int id) {
        String sql = SELECT_BASE + "WHERE l.lecturer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapLecturer(rs);
            }
        } catch (SQLException e) {
            System.err.println("LecturerDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    public Lecturer findByUserId(int userId) {
        String sql = SELECT_BASE + "WHERE l.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapLecturer(rs);
            }
        } catch (SQLException e) {
            System.err.println("LecturerDAO.findByUserId error: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Lecturer l) {
        String sql = "INSERT INTO lecturers (user_id, full_name, employee_id, email, mobile, department_id, specialization) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, l.getUserId());
            ps.setString(2, l.getFullName());
            ps.setString(3, l.getEmployeeId());
            ps.setString(4, l.getEmail());
            ps.setString(5, l.getMobile());
            ps.setInt(6, l.getDepartmentId());
            ps.setString(7, l.getSpecialization());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("LecturerDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Lecturer l) {
        String sql = "UPDATE lecturers SET full_name=?, employee_id=?, email=?, mobile=?, department_id=?, specialization=? WHERE lecturer_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getFullName());
            ps.setString(2, l.getEmployeeId());
            ps.setString(3, l.getEmail());
            ps.setString(4, l.getMobile());
            ps.setInt(5, l.getDepartmentId());
            ps.setString(6, l.getSpecialization());
            ps.setInt(7, l.getLecturerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("LecturerDAO.update error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("LecturerDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Lecturer mapLecturer(ResultSet rs) throws SQLException {
        Lecturer l = new Lecturer();
        l.setLecturerId(rs.getInt("lecturer_id"));
        l.setUserId(rs.getInt("user_id"));
        l.setFullName(rs.getString("full_name"));
        l.setEmployeeId(rs.getString("employee_id"));
        l.setEmail(rs.getString("email"));
        l.setMobile(rs.getString("mobile"));
        l.setDepartmentId(rs.getInt("department_id"));
        l.setSpecialization(rs.getString("specialization"));
        try { l.setDepartmentName(rs.getString("dept_name")); } catch (SQLException ignored) {}
        return l;
    }
}
