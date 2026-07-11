package com.faculty.dao;

import com.faculty.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Timetable
 */
public class TimetableDAO {

    /*Get timetable rows for students*/
    public List<Object[]> getTimetableForStudent(int studentId) {
        List<Object[]> rows = new ArrayList<>();
        String sql =
            "SELECT c.course_name, c.course_code, t.day_of_week, t.start_time, t.end_time, t.location, l.full_name " +
            "FROM timetable t " +
            "JOIN courses c ON t.course_id = c.course_id " +
            "LEFT JOIN lecturers l ON c.lecturer_id = l.lecturer_id " +
            "JOIN enrollments e ON e.course_id = t.course_id " +
            "WHERE e.student_id = ? " +
            "ORDER BY FIELD(t.day_of_week,'Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'), t.start_time";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("day_of_week"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("course_name"),
                        rs.getString("course_code"),
                        rs.getString("location"),
                        rs.getString("full_name")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("TimetableDAO.getTimetableForStudent error: " + e.getMessage());
        }
        return rows;
    }

    /*Get timetable rows for lecturer*/
    public List<Object[]> getTimetableForLecturer(int lecturerId) {
        List<Object[]> rows = new ArrayList<>();
        String sql =
            "SELECT t.timetable_id, c.course_name, c.course_code, t.day_of_week, t.start_time, t.end_time, t.location " +
            "FROM timetable t " +
            "JOIN courses c ON t.course_id = c.course_id " +
            "WHERE c.lecturer_id = ? " +
            "ORDER BY FIELD(t.day_of_week,'Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'), t.start_time";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getInt("timetable_id"),
                        rs.getString("day_of_week"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("course_name"),
                        rs.getString("course_code"),
                        rs.getString("location")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("TimetableDAO.getTimetableForLecturer error: " + e.getMessage());
        }
        return rows;
    }

    /*Update an existing timetable*/
    public boolean updateTimetableEntry(int timetableId, String day, String startTime, String endTime, String location) {
        String sql = "UPDATE timetable SET day_of_week = ?, start_time = ?, end_time = ?, location = ? WHERE timetable_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, day);
            ps.setString(2, startTime);
            ps.setString(3, endTime);
            ps.setString(4, location);
            ps.setInt(5, timetableId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("TimetableDAO.updateTimetableEntry error: " + e.getMessage());
            return false;
        }
    }
}
