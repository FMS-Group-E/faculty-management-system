-- ============================================================
-- Faculty Management System - Sample Data
-- CTEC 22043 Group Assignment
-- ============================================================

USE faculty_management_system;

-- ---------------------------------------------------------------
-- Departments
-- ---------------------------------------------------------------
INSERT INTO departments (dept_name, hod_name, staff_count, description) VALUES
('Computer Science', 'Dr. A. Perera', 12, 'Department of Computer Science and Software Engineering'),
('Information Technology', 'Dr. B. Fernando', 10, 'Department of Information Technology and Systems'),
('Mathematics', 'Dr. C. Silva', 8, 'Department of Applied Mathematics'),
('Electrical Engineering', 'Dr. D. Ranasinghe', 9, 'Department of Electrical and Electronic Engineering');

-- ---------------------------------------------------------------
-- Degrees
-- ---------------------------------------------------------------
INSERT INTO degrees (degree_name, degree_code, department_id, duration_years, description) VALUES
('BSc in Computer Science', 'BSC-CS', 1, 4, 'Bachelor of Science in Computer Science'),
('BSc in Information Technology', 'BSC-IT', 2, 3, 'Bachelor of Science in Information Technology'),
('BSc in Mathematics', 'BSC-MATH', 3, 3, 'Bachelor of Science in Applied Mathematics'),
('BSc in Electrical Engineering', 'BSC-EE', 4, 4, 'Bachelor of Science in Electrical Engineering');

-- ---------------------------------------------------------------
-- Users
-- ---------------------------------------------------------------
INSERT INTO users (username, password, role, email) VALUES
('admin', 'admin123', 'ADMIN', 'admin@faculty.ac.lk'),
('student1', 'student123', 'STUDENT', 'kasun@student.ac.lk'),
('student2', 'student123', 'STUDENT', 'nimal@student.ac.lk'),
('student3', 'student123', 'STUDENT', 'saman@student.ac.lk'),
('lecturer1', 'lecturer123', 'LECTURER', 'jayantha@faculty.ac.lk'),
('lecturer2', 'lecturer123', 'LECTURER', 'kumari@faculty.ac.lk'),
('lecturer3', 'lecturer123', 'LECTURER', 'asanka@faculty.ac.lk');

-- ---------------------------------------------------------------
-- Lecturers
-- ---------------------------------------------------------------
INSERT INTO lecturers (user_id, full_name, employee_id, email, mobile, department_id, specialization) VALUES
(5, 'Mr. Jayantha Bandara', 'EMP-001', 'jayantha@faculty.ac.lk', '0771234567', 1, 'Software Engineering'),
(6, 'Ms. Kumari Perera', 'EMP-002', 'kumari@faculty.ac.lk', '0779876543', 2, 'Database Systems'),
(7, 'Mr. Asanka Silva', 'EMP-003', 'asanka@faculty.ac.lk', '0775551234', 1, 'Algorithms and Data Structures');

-- ---------------------------------------------------------------
-- Courses
-- ---------------------------------------------------------------
INSERT INTO courses (course_code, course_name, credits, department_id, lecturer_id, semester, description) VALUES
('CTEC21011', 'Object Oriented Programming', 3, 1, 1, 1, 'Introduction to OOP concepts using Java'),
('CTEC21012', 'Database Management Systems', 3, 1, 2, 1, 'Fundamentals of relational databases and SQL'),
('CTEC21013', 'Data Structures and Algorithms', 3, 1, 3, 2, 'Core data structures and algorithm design'),
('CTEC22043', 'Advanced Java Programming', 3, 1, 1, 2, 'Java Swing, JDBC, and design patterns'),
('CTEC21021', 'Web Technologies', 3, 2, 2, 1, 'HTML, CSS, JavaScript and web frameworks'),
('CTEC21022', 'Computer Networks', 3, 2, 3, 2, 'Network protocols and communication');

-- ---------------------------------------------------------------
-- Students
-- ---------------------------------------------------------------
INSERT INTO students (user_id, full_name, student_number, email, mobile, degree_id, year_of_study) VALUES
(2, 'Kasun Rathnayake', 'CS/2021/001', 'kasun@student.ac.lk', '0712345678', 1, 2),
(3, 'Nimal Jayasinghe', 'CS/2021/002', 'nimal@student.ac.lk', '0723456789', 1, 2),
(4, 'Saman Kumara', 'IT/2022/001', 'saman@student.ac.lk', '0734567890', 2, 1);

-- ---------------------------------------------------------------
-- Enrollments
-- ---------------------------------------------------------------
INSERT INTO enrollments (student_id, course_id, grade) VALUES
(1, 1, 'A'),
(1, 2, 'B+'),
(1, 3, 'A-'),
(1, 4, NULL),
(2, 1, 'B'),
(2, 2, 'A'),
(2, 3, 'B+'),
(3, 5, 'A'),
(3, 6, 'B');

-- ---------------------------------------------------------------
-- Timetable
-- ---------------------------------------------------------------
INSERT INTO timetable (course_id, day_of_week, start_time, end_time, location) VALUES
(1, 'Monday',    '08:00:00', '10:00:00', 'Lab A-101'),
(2, 'Tuesday',   '10:00:00', '12:00:00', 'Lecture Hall B'),
(3, 'Wednesday', '13:00:00', '15:00:00', 'Lab A-102'),
(4, 'Thursday',  '08:00:00', '10:00:00', 'Lab A-101'),
(5, 'Friday',    '10:00:00', '12:00:00', 'Lecture Hall A'),
(6, 'Monday',    '13:00:00', '15:00:00', 'Lab B-201');
