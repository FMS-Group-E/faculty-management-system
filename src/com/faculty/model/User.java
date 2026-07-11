package com.faculty.model;

/**
 * Model: User entity representing system authentication credentials.
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String role; // ADMIN, STUDENT, LECTURER
    private String email;

    public User() {}

    public User(int userId, String username, String password, String role, String email) {
        this.userId   = userId;
        this.username = username;
        this.password = password;
        this.role     = role;
        this.email    = email;
    }

    // Getters and Setters
    public int    getUserId()   { return userId;   }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role;     }
    public String getEmail()    { return email;    }

    public void setUserId(int userId)       { this.userId   = userId;   }
    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }
    public void setRole(String role)        { this.role     = role;     }
    public void setEmail(String email)      { this.email    = email;    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', role='" + role + "'}";
    }
}
