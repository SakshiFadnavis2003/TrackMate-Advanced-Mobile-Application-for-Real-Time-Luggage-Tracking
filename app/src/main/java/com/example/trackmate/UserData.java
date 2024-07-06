package com.example.trackmate;

public class UserData {
    private String name;
    private String email;
    private String contact;
    private String userId;

    public UserData(String name, String email, String contact, String userId) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.userId = userId;
    }

    // Getters and Setters (if needed)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
