package com.example.demo;

public class User {

    private Long id;
    private String username;
    private String password;
    private String role;
    private String email;

    public User(Long id, String email, String password, String role, String username) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public User(String email, String password, String role, String username) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
