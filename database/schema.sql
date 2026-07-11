-- ============================================================
-- Faculty Management System - Database Schema
-- CTEC 22043 Group Assignment
-- ============================================================

CREATE DATABASE IF NOT EXISTS faculty_management_system;
USE faculty_management_system;

-- ---------------------------------------------------------------
-- Table: departments
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS departments (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    dept_name       VARCHAR(100) NOT NULL,
    hod_name        VARCHAR(100),
    staff_count     INT DEFAULT 0,
    description     TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------
-- Table: degrees
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS degrees (
    degree_id       INT AUTO_INCREMENT PRIMARY KEY,
    degree_name     VARCHAR(100) NOT NULL,
    degree_code     VARCHAR(20) NOT NULL UNIQUE,
    department_id   INT,
    duration_years  INT DEFAULT 3,
    description     TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Table: users  (authentication)
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    user_id         INT AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    role            ENUM('ADMIN','STUDENT','LECTURER') NOT NULL,
    email           VARCHAR(100),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------
-- Table: lecturers
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS lecturers (
    lecturer_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT UNIQUE,
    full_name       VARCHAR(100) NOT NULL,
    employee_id     VARCHAR(20) NOT NULL UNIQUE,
    email           VARCHAR(100),
    mobile          VARCHAR(20),
    department_id   INT,
    specialization  VARCHAR(100),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Table: courses
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS courses (
    course_id       INT AUTO_INCREMENT PRIMARY KEY,
    course_code     VARCHAR(20) NOT NULL UNIQUE,
    course_name     VARCHAR(100) NOT NULL,
    credits         INT DEFAULT 3,
    department_id   INT,
    lecturer_id     INT,
    semester        INT DEFAULT 1,
    description     TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE SET NULL,
    FOREIGN KEY (lecturer_id)   REFERENCES lecturers(lecturer_id)   ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Table: students
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS students (
    student_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT UNIQUE,
    full_name       VARCHAR(100) NOT NULL,
    student_number  VARCHAR(20) NOT NULL UNIQUE,
    email           VARCHAR(100),
    mobile          VARCHAR(20),
    degree_id       INT,
    year_of_study   INT DEFAULT 1,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)   REFERENCES users(user_id)     ON DELETE SET NULL,
    FOREIGN KEY (degree_id) REFERENCES degrees(degree_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------
-- Table: enrollments
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id   INT AUTO_INCREMENT PRIMARY KEY,
    student_id      INT NOT NULL,
    course_id       INT NOT NULL,
    grade           VARCHAR(5) DEFAULT NULL,
    enrolled_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_enrollment (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id)  REFERENCES courses(course_id)   ON DELETE CASCADE
);

-- ---------------------------------------------------------------
-- Table: timetable
-- ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS timetable (
    timetable_id    INT AUTO_INCREMENT PRIMARY KEY,
    course_id       INT NOT NULL,
    day_of_week     ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday') NOT NULL,
    start_time      TIME NOT NULL,
    end_time        TIME NOT NULL,
    location        VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);
