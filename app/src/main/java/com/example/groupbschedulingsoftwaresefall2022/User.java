package com.example.groupbschedulingsoftwaresefall2022;

public class User {

    private String userID;
    private String email;
    private String password;

    public User(String userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    @Override public String toString() {
        return "userID: " + this.getUserID() + "\nemail: " + this.getEmail();
    }

}
