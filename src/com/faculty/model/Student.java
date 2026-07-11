package com.faculty.model;

/**
 * Model: Student entity.
 */
public class Student {
    private int    studentId;
    private int    userId;
    private String fullName;
    private String studentNumber;
    private String email;
    private String mobile;
    private int    degreeId;
    private String degreeName;  // for display
    private int    yearOfStudy;

    public Student() {}

    public Student(int studentId, int userId, String fullName, String studentNumber,
                   String email, String mobile, int degreeId, int yearOfStudy) {
        this.studentId     = studentId;
        this.userId        = userId;
        this.fullName      = fullName;
        this.studentNumber = studentNumber;
        this.email         = email;
        this.mobile        = mobile;
        this.degreeId      = degreeId;
        this.yearOfStudy   = yearOfStudy;
    }

    public int    getStudentId()     { return studentId;     }
    public int    getUserId()        { return userId;        }
    public String getFullName()      { return fullName;      }
    public String getStudentNumber() { return studentNumber; }
    public String getEmail()         { return email;         }
    public String getMobile()        { return mobile;        }
    public int    getDegreeId()      { return degreeId;      }
    public String getDegreeName()    { return degreeName;    }
    public int    getYearOfStudy()   { return yearOfStudy;   }

    public void setStudentId(int studentId)          { this.studentId     = studentId;     }
    public void setUserId(int userId)                { this.userId        = userId;        }
    public void setFullName(String fullName)         { this.fullName      = fullName;      }
    public void setStudentNumber(String studentNumber){ this.studentNumber = studentNumber; }
    public void setEmail(String email)               { this.email         = email;         }
    public void setMobile(String mobile)             { this.mobile        = mobile;        }
    public void setDegreeId(int degreeId)            { this.degreeId      = degreeId;      }
    public void setDegreeName(String degreeName)     { this.degreeName    = degreeName;    }
    public void setYearOfStudy(int yearOfStudy)      { this.yearOfStudy   = yearOfStudy;   }

    @Override
    public String toString() { return fullName + " (" + studentNumber + ")"; }
}
