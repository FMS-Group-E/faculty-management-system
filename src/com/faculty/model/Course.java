package com.faculty.model;

public class Course {
    private int    courseId;
    private String courseCode;
    private String courseName;
    private int    credits;
    private int    departmentId;
    private String departmentName;
    private int    lecturerId;
    private String lecturerName;
    private int    semester;
    private String description;

    public Course() {}

    public Course(int courseId, String courseCode, String courseName, int credits,
                  int departmentId, int lecturerId, int semester, String description) {
        this.courseId     = courseId;
        this.courseCode   = courseCode;
        this.courseName   = courseName;
        this.credits      = credits;
        this.departmentId = departmentId;
        this.lecturerId   = lecturerId;
        this.semester     = semester;
        this.description  = description;
    }

    public int    getCourseId()      { return courseId;      }
    public String getCourseCode()    { return courseCode;    }
    public String getCourseName()    { return courseName;    }
    public int    getCredits()       { return credits;       }
    public int    getDepartmentId()  { return departmentId;  }
    public String getDepartmentName(){ return departmentName;}
    public int    getLecturerId()    { return lecturerId;    }
    public String getLecturerName()  { return lecturerName;  }
    public int    getSemester()      { return semester;      }
    public String getDescription()   { return description;   }

    public void setCourseId(int courseId)           { this.courseId     = courseId;      }
    public void setCourseCode(String courseCode)    { this.courseCode   = courseCode;    }
    public void setCourseName(String courseName)    { this.courseName   = courseName;    }
    public void setCredits(int credits)             { this.credits      = credits;       }
    public void setDepartmentId(int departmentId)   { this.departmentId = departmentId;  }
    public void setDepartmentName(String name)      { this.departmentName= name;         }
    public void setLecturerId(int lecturerId)       { this.lecturerId   = lecturerId;    }
    public void setLecturerName(String name)        { this.lecturerName = name;          }
    public void setSemester(int semester)           { this.semester     = semester;      }
    public void setDescription(String description)  { this.description  = description;   }

    @Override
    public String toString() { return courseName + " (" + courseCode + ")"; }
}
