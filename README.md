# 🎓 Faculty Management System

**FacultyPortal** — A Java Swing desktop application for academic administration, built as a group assignment for **CTEC 22043 – Advanced Java Programming**.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Database Setup](#database-setup)
  - [Running the Application](#running-the-application)
- [Default Credentials](#default-credentials)
- [User Roles](#user-roles)
- [Sample Data](#sample-data)
- [Team](#team)

---

## Overview

The **Faculty Management System** is a desktop-based academic administration platform that enables three types of users — **Admins**, **Lecturers**, and **Students** — to manage and view institutional data including students, courses, lecturers, departments, degrees, enrollments, and timetables.

It is built using **Java Swing** for the GUI and **MySQL** as the backend database, connected via JDBC.

---

## Features

### 🔐 Authentication
- Role-based login system supporting Admin, Lecturer, and Student roles
- Secure session management with credential validation

### 🛠️ Admin Dashboard
- **Student Management** — Add, view, edit, and delete student records
- **Lecturer Management** — Manage lecturer profiles and department assignments
- **Course Management** — Create and manage courses with credits and semester info
- **Department Management** — Manage academic departments and heads of department
- **Degree Management** — Configure degree programs linked to departments

### 👨‍🏫 Lecturer Dashboard
- View assigned courses and enrolled students
- Access timetable information

### 👩‍🎓 Student Dashboard
- View personal profile and enrolled courses
- View grades and timetable

---

## Tech Stack

| Layer        | Technology                         |
|--------------|------------------------------------|
| Language     | Java 11+                           |
| GUI          | Java Swing                         |
| Database     | MySQL 8.x                          |
| JDBC Driver  | `mysql-connector-j-8.0.33.jar`     |
| Architecture | MVC (Model-View-Controller)        |
| Pattern      | Singleton (DatabaseConnection)     |

---

## Project Structure

```
Faculty-Management-System/
├── src/
│   └── com/
│       └── faculty/
│           ├── main/
│           │   └── Main.java                   # Application entry point
│           ├── model/
│           │   ├── User.java                   # User entity model
│           │   └── Student.java                # Student entity model
│           ├── view/
│           │   ├── LoginView.java              # Login screen
│           │   ├── AdminDashboardView.java     # Admin dashboard
│           │   ├── LecturerDashboardView.java  # Lecturer dashboard
│           │   ├── StudentDashboardView.java   # Student dashboard
│           │   └── panels/
│           │       └── AdminStudentsPanel.java # Student CRUD panel
│           ├── controller/
│           │   └── StudentController.java      # Student business logic
│           ├── dao/
│           │   ├── UserDAO.java                # User database operations
│           │   └── StudentDAO.java             # Student database operations
│           └── util/
│               ├── DatabaseConnection.java     # Singleton DB connection
│               ├── UITheme.java                # Global UI theme & colors
│               └── LucideIcon.java             # Custom vector icon renderer
├── database/
│   ├── schema.sql                              # Database schema (DDL)
│   └── sample_data.sql                         # Seed data for testing
├── lib/
│   └── mysql-connector-j-8.0.33.jar           # MySQL JDBC driver
├── bin/                                        # Compiled class files
└── README.md
```

---

## Database Schema

The system uses a MySQL database named `faculty_management_system` with the following tables:

| Table         | Description                                       |
|---------------|---------------------------------------------------|
| `users`       | Authentication — stores credentials and roles     |
| `students`    | Student profiles linked to users and degrees      |
| `lecturers`   | Lecturer profiles linked to users and departments |
| `courses`     | Courses with credits, semester, and lecturer      |
| `departments` | Academic departments with HoD info                |
| `degrees`     | Degree programs linked to departments             |
| `enrollments` | Student–course enrollment with grade tracking     |
| `timetable`   | Course schedule with day, time, and location      |

---

## Getting Started

### Prerequisites

- **Java JDK 11** or higher — [Download](https://adoptium.net/)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/mysql/)
- An IDE such as **IntelliJ IDEA**, **Eclipse**, or **NetBeans** (recommended)

---

### Database Setup

1. Start your MySQL server.

2. Open a MySQL client (MySQL Workbench, terminal, etc.) and run:

```sql
-- Step 1: Create the database and tables
SOURCE /path/to/database/schema.sql;

-- Step 2: Insert sample data
SOURCE /path/to/database/sample_data.sql;
```

Or run both files via MySQL CLI:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/sample_data.sql
```

3. **Configure database credentials** in `src/com/faculty/util/DatabaseConnection.java`:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/faculty_management_system";
private static final String USER     = "root";
private static final String PASSWORD = "your_password_here"; // ← change this
```

---

### Running the Application

#### Option A — Using an IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, or NetBeans).
2. Add `lib/mysql-connector-j-8.0.33.jar` to the project classpath / build path.
3. Run `src/com/faculty/main/Main.java`.

#### Option B — Command Line

```bash
# From the project root directory

# Compile (Windows)
javac -cp "lib/mysql-connector-j-8.0.33.jar" -d bin src/com/faculty/main/*.java src/com/faculty/model/*.java src/com/faculty/view/*.java src/com/faculty/view/panels/*.java src/com/faculty/controller/*.java src/com/faculty/dao/*.java src/com/faculty/util/*.java

# Run (Windows)
java -cp "bin;lib/mysql-connector-j-8.0.33.jar" com.faculty.main.Main
```

> **Note:** Use `;` as the classpath separator on Windows, and `:` on Linux/macOS.

---

## Default Credentials

After loading `sample_data.sql`, use the following credentials:

| Role     | Username    | Password      |
|----------|-------------|---------------|
| Admin    | `admin`     | `admin123`    |
| Student  | `student1`  | `student123`  |
| Student  | `student2`  | `student123`  |
| Student  | `student3`  | `student123`  |
| Lecturer | `lecturer1` | `lecturer123` |
| Lecturer | `lecturer2` | `lecturer123` |
| Lecturer | `lecturer3` | `lecturer123` |

> ⚠️ **Warning:** Passwords in `sample_data.sql` are stored in plain text for demonstration purposes only. In a production system, always use a secure hashing algorithm such as BCrypt.

---

## User Roles

| Role       | Access Level                                                               |
|------------|----------------------------------------------------------------------------|
| `ADMIN`    | Full CRUD access to students, lecturers, courses, departments, and degrees |
| `LECTURER` | Read access to assigned courses, student lists, and timetables             |
| `STUDENT`  | Read access to own profile, enrolled courses, grades, and timetable        |

---

## Sample Data

The seed file (`sample_data.sql`) includes:

- **4 Departments**: Computer Science, Information Technology, Mathematics, Electrical Engineering
- **4 Degrees**: BSc programs in CS, IT, Mathematics, and Electrical Engineering
- **3 Lecturers**: Specializing in Software Engineering, Database Systems, and Algorithms
- **6 Courses**: OOP, DBMS, Data Structures, Advanced Java, Web Technologies, Computer Networks
- **3 Students**: Enrolled in multiple courses with recorded grades
- **6 Timetable Entries**: Scheduled across Monday–Friday with room locations

---

## Team

Developed by **Group E** as part of the **CTEC 22043 – Advanced Java Programming** module.

---

## License

This project was created for academic purposes as part of a university group assignment.
