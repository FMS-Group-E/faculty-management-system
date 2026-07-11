package com.faculty.dao;

import com.faculty.model.Course;
import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private static final String SELECT_BASE =
        "SELECT c.*, d.dept_name, l.full_name AS lecturer_name FROM courses c " +
        "LEFT JOIN departments d ON c.department_id = d.department_id " +
        "LEFT JOIN lecturers l  ON c.lecturer_id   = l.lecturer_id ";

    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SELECT_BASE + "ORDER BY c.course_name")) {
            while (rs.next()) list.add(mapCourse(rs));
        } catch (SQLException e) {
            System.err.println("CourseDAO.findAll error: " + e.getMessage());
        }
        return list;
    }

    public Course findById(int id) {
        String sql = SELECT_BASE + "WHERE c.course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCourse(rs);
            }
        } catch (SQLException e) {
            System.err.println("CourseDAO.findById error: " + e.getMessage());
        }
        return null;
    }

    public List<Course> findByLecturerId(int lecturerId) {
        List<Course> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE c.lecturer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapCourse(rs));
            }
        } catch (SQLException e) {
            System.err.println("CourseDAO.findByLecturerId error: " + e.getMessage());
        }
        return list;
    }

    public boolean insert(Course c) {
        String sql = "INSERT INTO courses (course_code, course_name, credits, department_id, lecturer_id, semester, description) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getDepartmentId());
            ps.setInt(5, c.getLecturerId());
            ps.setInt(6, c.getSemester());
            ps.setString(7, c.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("CourseDAO.insert error: " + e.getMessage());
        }
        return false;
    }

    public boolean update(Course c) {
        String sql = "UPDATE courses SET course_code=?, course_name=?, credits=?, department_id=?, lecturer_id=?, semester=?, description=? WHERE course_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getDepartmentId());
            ps.setInt(5, c.getLecturerId());
            ps.setInt(6, c.getSemester());
            ps.setString(7, c.getDescription());
            ps.setInt(8, c.getCourseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("CourseDAO.update error: " + e.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("CourseDAO.delete error: " + e.getMessage());
        }
        return false;
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setCourseCode(rs.getString("course_code"));
        c.setCourseName(rs.getString("course_name"));
        c.setCredits(rs.getInt("credits"));
        c.setDepartmentId(rs.getInt("department_id"));
        c.setLecturerId(rs.getInt("lecturer_id"));
        c.setSemester(rs.getInt("semester"));
        c.setDescription(rs.getString("description"));
        try { c.setDepartmentName(rs.getString("dept_name")); }    catch (SQLException ignored) {}
        try { c.setLecturerName(rs.getString("lecturer_name")); }  catch (SQLException ignored) {}
        return c;
    }
}
