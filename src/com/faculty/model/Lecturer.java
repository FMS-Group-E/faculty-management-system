package com.faculty.model;

/**
 * Lecturer entity.
 */
public class Lecturer {
    private int    lecturerId;
    private int    userId;
    private String fullName;
    private String employeeId;
    private String email;
    private String mobile;
    private int    departmentId;
    private String departmentName; 
    private String specialization;

    public Lecturer() {}

    public Lecturer(int lecturerId, int userId, String fullName, String employeeId,
                    String email, String mobile, int departmentId, String specialization) {
        this.lecturerId    = lecturerId;
        this.userId        = userId;
        this.fullName      = fullName;
        this.employeeId    = employeeId;
        this.email         = email;
        this.mobile        = mobile;
        this.departmentId  = departmentId;
        this.specialization= specialization;
    }

    public int    getLecturerId()     { return lecturerId;    }
    public int    getUserId()         { return userId;        }
    public String getFullName()       { return fullName;      }
    public String getEmployeeId()     { return employeeId;    }
    public String getEmail()          { return email;         }
    public String getMobile()         { return mobile;        }
    public int    getDepartmentId()   { return departmentId;  }
    public String getDepartmentName() { return departmentName;}
    public String getSpecialization() { return specialization;}

    public void setLecturerId(int lecturerId)        { this.lecturerId    = lecturerId;    }
    public void setUserId(int userId)                { this.userId        = userId;        }
    public void setFullName(String fullName)         { this.fullName      = fullName;      }
    public void setEmployeeId(String employeeId)     { this.employeeId    = employeeId;    }
    public void setEmail(String email)               { this.email         = email;         }
    public void setMobile(String mobile)             { this.mobile        = mobile;        }
    public void setDepartmentId(int departmentId)    { this.departmentId  = departmentId;  }
    public void setDepartmentName(String name)       { this.departmentName= name;          }
    public void setSpecialization(String s)          { this.specialization= s;             }

    @Override
    public String toString() { return fullName + " (" + employeeId + ")"; }
}
