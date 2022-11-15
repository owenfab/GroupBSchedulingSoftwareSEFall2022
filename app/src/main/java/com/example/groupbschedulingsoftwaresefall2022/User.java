package com.example.groupbschedulingsoftwaresefall2022;

public class User {

    private static int overallID = 0;
    private int userID;
    private String email;

    public User(String email) {
        userID = getUniqueID();
        this.email = email;
    }

    private int getUniqueID() {
        return overallID++;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    @Override public String toString() {
        return "userID: " + this.getUserID() + "\nemail: " + this.getEmail();
    }

}
