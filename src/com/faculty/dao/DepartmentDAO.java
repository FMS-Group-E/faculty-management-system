package com.faculty.dao;

import com.faculty.model.Department;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO: Full CRUD for Department.
 */
public class DepartmentDAO {

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY dept_name";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapDept(rs));
        } catch (SQLException e) {
            System.err.println("DepartmentDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public Department findById(int id) {
        String sql = "SELECT * FROM departments WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapDept(rs);
            }
        } catch (SQLException e) {
            System.err.println("DepartmentDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    public boolean insert(Department d) {
        String sql = "INSERT INTO departments (dept_name, hod_name, staff_count, description) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDeptName());
            ps.setString(2, d.getHodName());
            ps.setInt(3, d.getStaffCount());
            ps.setString(4, d.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DepartmentDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Department d) {
        String sql = "UPDATE departments SET dept_name=?, hod_name=?, staff_count=?, description=? WHERE department_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDeptName());
            ps.setString(2, d.getHodName());
            ps.setInt(3, d.getStaffCount());
            ps.setString(4, d.getDescription());
            ps.setInt(5, d.getDepartmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DepartmentDAO.update error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM departments WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DepartmentDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Department mapDept(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setDepartmentId(rs.getInt("department_id"));
        d.setDeptName(rs.getString("dept_name"));
        d.setHodName(rs.getString("hod_name"));
        d.setStaffCount(rs.getInt("staff_count"));
        d.setDescription(rs.getString("description"));
        return d;
    }
}
