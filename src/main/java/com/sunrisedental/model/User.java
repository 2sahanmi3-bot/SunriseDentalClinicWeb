package com.sunrisedental.model;

public class User {

    private int userId;
    private String username;
    private String password;
    private String role;
    private boolean active;

    public User(
            int userId,
            String username,
            String password) {

        this(
                userId,
                username,
                password,
                null
        );
    }

    public User(
            int userId,
            String username,
            String password,
            String role) {

        this(
                userId,
                username,
                password,
                role,
                true
        );
    }

    public User(
            int userId,
            String username,
            String password,
            String role,
            boolean active) {

        this.userId =
                userId;

        this.username =
                username;

        this.password =
                password;

        this.role =
                role;

        this.active =
                active;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }
}