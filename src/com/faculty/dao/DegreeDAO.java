package com.faculty.dao;

import com.faculty.model.Degree;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: Full CRUD for Degree.
 */
public class DegreeDAO {

    public List<Degree> findAll() {
        List<Degree> list = new ArrayList<>();
        String sql = "SELECT dg.*, dp.dept_name FROM degrees dg " +
                     "LEFT JOIN departments dp ON dg.department_id = dp.department_id " +
                     "ORDER BY dg.degree_name";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapDegree(rs));
        } catch (SQLException e) {
            System.err.println("DegreeDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public Degree findById(int id) {
        String sql = "SELECT dg.*, dp.dept_name FROM degrees dg " +
                     "LEFT JOIN departments dp ON dg.department_id = dp.department_id " +
                     "WHERE dg.degree_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapDegree(rs);
            }
        } catch (SQLException e) {
            System.err.println("DegreeDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Degree d) {
        String sql = "INSERT INTO degrees (degree_name, degree_code, department_id, duration_years, description) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDegreeName());
            ps.setString(2, d.getDegreeCode());
            ps.setInt(3, d.getDepartmentId());
            ps.setInt(4, d.getDurationYears());
            ps.setString(5, d.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DegreeDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Degree d) {
        String sql = "UPDATE degrees SET degree_name=?, degree_code=?, department_id=?, duration_years=?, description=? WHERE degree_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDegreeName());
            ps.setString(2, d.getDegreeCode());
            ps.setInt(3, d.getDepartmentId());
            ps.setInt(4, d.getDurationYears());
            ps.setString(5, d.getDescription());
            ps.setInt(6, d.getDegreeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DegreeDAO.update error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM degrees WHERE degree_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DegreeDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Degree mapDegree(ResultSet rs) throws SQLException {
        Degree d = new Degree();
        d.setDegreeId(rs.getInt("degree_id"));
        d.setDegreeName(rs.getString("degree_name"));
        d.setDegreeCode(rs.getString("degree_code"));
        d.setDepartmentId(rs.getInt("department_id"));
        d.setDurationYears(rs.getInt("duration_years"));
        d.setDescription(rs.getString("description"));
        try { d.setDepartmentName(rs.getString("dept_name")); } catch (SQLException ignored) {}
        return d;
    }
}
