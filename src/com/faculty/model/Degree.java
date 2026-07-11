package com.faculty.model;

/**
 * Model: Degree entity.
 */
public class Degree {
    private int    degreeId;
    private String degreeName;
    private String degreeCode;
    private int    departmentId;
    private String departmentName; // for display
    private int    durationYears;
    private String description;

    public Degree() {}

    public Degree(int degreeId, String degreeName, String degreeCode, int departmentId, int durationYears, String description) {
        this.degreeId      = degreeId;
        this.degreeName    = degreeName;
        this.degreeCode    = degreeCode;
        this.departmentId  = departmentId;
        this.durationYears = durationYears;
        this.description   = description;
    }

    public int    getDegreeId()      { return degreeId;      }
    public String getDegreeName()    { return degreeName;    }
    public String getDegreeCode()    { return degreeCode;    }
    public int    getDepartmentId()  { return departmentId;  }
    public String getDepartmentName(){ return departmentName;}
    public int    getDurationYears() { return durationYears; }
    public String getDescription()   { return description;   }

    public void setDegreeId(int degreeId)           { this.degreeId      = degreeId;      }
    public void setDegreeName(String degreeName)    { this.degreeName    = degreeName;    }
    public void setDegreeCode(String degreeCode)    { this.degreeCode    = degreeCode;    }
    public void setDepartmentId(int departmentId)   { this.departmentId  = departmentId;  }
    public void setDepartmentName(String name)      { this.departmentName= name;          }
    public void setDurationYears(int durationYears) { this.durationYears = durationYears; }
    public void setDescription(String description)  { this.description   = description;   }

    @Override
    public String toString() { return degreeName + " (" + degreeCode + ")"; }
}
