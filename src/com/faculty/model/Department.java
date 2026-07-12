package com.faculty.model;

/**
 * Model: Department entity.
 */
public class Department {
    private int    departmentId;
    private String deptName;
    private String hodName;
    private int    staffCount;
    private String description;

    public Department() {}

    public Department(int departmentId, String deptName, String hodName, int staffCount, String description) {
        this.departmentId = departmentId;
        this.deptName     = deptName;
        this.hodName      = hodName;
        this.staffCount   = staffCount;
        this.description  = description;
    }

    public int    getDepartmentId() { return departmentId; }
    public String getDeptName()     { return deptName;     }
    public String getHodName()      { return hodName;      }
    public int    getStaffCount()   { return staffCount;   }
    public String getDescription()  { return description;  }

    public void setDepartmentId(int departmentId)    { this.departmentId = departmentId; }
    public void setDeptName(String deptName)         { this.deptName     = deptName;     }
    public void setHodName(String hodName)           { this.hodName      = hodName;      }
    public void setStaffCount(int staffCount)        { this.staffCount   = staffCount;   }
    public void setDescription(String description)   { this.description  = description;  }

    @Override
    public String toString() { return deptName; }
}
