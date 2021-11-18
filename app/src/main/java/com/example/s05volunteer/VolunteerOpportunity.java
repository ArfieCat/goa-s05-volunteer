package com.example.s05volunteer;

public class VolunteerOpportunity {
    String name;
    String description;
    String details;
    String date;
    String location;
    String email;
    int logoDrawable;
    int orgDrawable;
    boolean isRegistered;

    public VolunteerOpportunity(String name, String description, String details, String date, String location, String email, int logoDrawable, int orgDrawable) {
        this.name = name;
        this.description = description;
        this.details = details;
        this.date = date;
        this.location = location;
        this.email = email;
        this.logoDrawable = logoDrawable;
        this.orgDrawable = orgDrawable;
        this.isRegistered = false;
    }
}
