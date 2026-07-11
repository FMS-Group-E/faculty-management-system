package com.faculty.model;

/**
 * Enrollment entity linking
 */
public class Enrollment {
    private int    enrollmentId;
    private int    studentId;
    private String studentName;  
    private int    courseId;
    private String courseName;   
    private String courseCode;   
    private String grade;

    public Enrollment() {}

    public Enrollment(int enrollmentId, int studentId, int courseId, String grade) {
        this.enrollmentId = enrollmentId;
        this.studentId    = studentId;
        this.courseId     = courseId;
        this.grade        = grade;
    }

    public int    getEnrollmentId()  { return enrollmentId; }
    public int    getStudentId()     { return studentId;    }
    public String getStudentName()   { return studentName;  }
    public int    getCourseId()      { return courseId;     }
    public String getCourseName()    { return courseName;   }
    public String getCourseCode()    { return courseCode;   }
    public String getGrade()         { return grade;        }

    public void setEnrollmentId(int enrollmentId)    { this.enrollmentId = enrollmentId; }
    public void setStudentId(int studentId)          { this.studentId    = studentId;    }
    public void setStudentName(String studentName)   { this.studentName  = studentName;  }
    public void setCourseId(int courseId)            { this.courseId     = courseId;     }
    public void setCourseName(String courseName)     { this.courseName   = courseName;   }
    public void setCourseCode(String courseCode)     { this.courseCode   = courseCode;   }
    public void setGrade(String grade)               { this.grade        = grade;        }
}
