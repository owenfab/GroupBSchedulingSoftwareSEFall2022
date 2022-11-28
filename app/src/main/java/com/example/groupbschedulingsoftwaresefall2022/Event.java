package com.example.groupbschedulingsoftwaresefall2022;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private int eventID;
    private Calendar eventDate;
    private String eventName;
    private String eventDescription;
    private int duration;
    private String username;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * fully loaded Event constructor
     * @param eMonth month of the event
     * @param eDay day of the event
     * @param eYear year of the event
     * @param eHour hour of the event
     * @param eMin minute of the event
     * @param d duration of the event in minutes
     * @param eName name of the event
     * @param eDr description of the event
     * @param username name of the user creating the Event; this is needed to properly reference
     *                 the correct User in the DB.
     */
    public Event(int eMonth, int eDay, int eYear,
                 int eHour, int eMin, int d, String eName, String eDr, String username) {
        eventDate = Calendar.getInstance();
        eventDate.set(eYear, eMonth-1, eDay, eHour, eMin, 0);
        duration = d;
        eventName = eName;
        eventDescription = eDr;
        this.username = username;
    }

    /**
     * default constructor for Event; a unique ID is still issued since
     * this is still an event, just an empty one
     */
    public Event() {
        eventDate = Calendar.getInstance();
        duration = -1;
        eventName = "unknown";
        eventDescription = "unknown";
        username = "unknown";
    }

    /**
     * copy constructor for Event; a unique ID is still issued so these will be treated as
     * separate events, just with the same data
     * @param e Event object to be copied
     */
    public Event(Event e) {
        this.eventDate = Calendar.getInstance();
        this.eventDate.set(e.eventDate.get(Calendar.YEAR), e.eventDate.get(Calendar.MONTH),
                e.eventDate.get(Calendar.DATE), e.eventDate.get(Calendar.HOUR),
                e.eventDate.get(Calendar.MINUTE), 0);
        this.duration = e.duration;
        this.eventName = e.eventName;
        this.eventDescription = e.eventDescription;
        this.username = e.username;
    }

    /**
     * @return ID of calling Event object
     */
    public int getID() {
        return this.eventID;
    }

    /**
     * @return year of calling Event object
     */
    public Integer getYear() {
        return this.eventDate.get(Calendar.YEAR);
    }

    /**
     * @return month of calling Event object
     */
    public Integer getMonth() { return this.eventDate.get(Calendar.MONTH); }
    /**
     * @return day of calling Event object
     */
    public Integer getDay() {
        return this.eventDate.get(Calendar.DATE);
    }

    /**
     * @return hour of calling Event object
     */
    public Integer getHour() {
        return this.eventDate.get(Calendar.HOUR);
    }

    /**
     * @return minute of calling Event object
     */
    public Integer getMinute() {
        return this.eventDate.get(Calendar.MINUTE);
    }

    /**
     * @return duration of calling Event object
     */
    public Integer getDuration() {
        return this.duration;
    }

    /**
     * @return name of calling Event object
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     * @return description of calling Event object
     */
    public String getEventDescription() {
        return this.eventDescription;
    }

    /**
     * @return username associated with calling Event object
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the year of calling Event object
     * @param year
     */
    public void setYear(int year) {
        this.eventDate.set(year, this.eventDate.get(Calendar.MONTH),
                this.eventDate.get(Calendar.DATE), this.eventDate.get(Calendar.HOUR),
                this.eventDate.get(Calendar.MINUTE), 0);
    }

    /**
     * sets the month of calling Event object
     * @param month
     */
    public void setMonth(int month) {
        this.eventDate.set(this.eventDate.get(Calendar.YEAR), month-1,
                this.eventDate.get(Calendar.DATE), this.eventDate.get(Calendar.HOUR),
                this.eventDate.get(Calendar.MINUTE), 0);
    }

    /**
     * sets the day of calling Event object
     * @param day
     */
    public void setDay(int day) {
        this.eventDate.set(this.eventDate.get(Calendar.YEAR), this.eventDate.get(Calendar.MONTH),
                day, this.eventDate.get(Calendar.HOUR),
                this.eventDate.get(Calendar.MINUTE), 0);
    }

    /**
     * sets the hour of calling Event object
     * @param hour
     */
    public void setHour(int hour) {
        this.eventDate.set(this.eventDate.get(Calendar.YEAR), this.eventDate.get(Calendar.MONTH),
                this.eventDate.get(Calendar.DATE), hour,
                this.eventDate.get(Calendar.MINUTE), 0);
    }

    /**
     * sets the minute of calling Event object
     * @param minute
     */
    public void setMinute(int minute) {
        this.eventDate.set(this.eventDate.get(Calendar.YEAR), this.eventDate.get(Calendar.MONTH),
                this.eventDate.get(Calendar.DATE), this.eventDate.get(Calendar.HOUR),
                minute, 0);
    }

    /**
     * sets the duration of calling Event object
     * @param d duration in minutes
     */
    public void setDuration(int d) {
        this.duration = d;
    }

    /**
     * sets the name of calling Event object
     * @param name
     */
    public void setEventName(String name) {
        this.eventName = name;
    }

    /**
     * sets the description of calling Event object
     * @param description
     */
    public void setEventDescription(String description) {
        this.eventDescription = description;
    }

    /**
     * sets the associated username of calling Event object
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * toString for Event class
     * @return eventID, name, date, duration (in minutes), and description
     */
    @Override public String toString() {
        String s = "";
        //add eventDate info
        s += "eventID: " + this.eventID + "\nevent name: " + this.eventName + "\nevent date: "
                + this.eventDate.getTime() + "\nevent duration: " + this.duration
                + "\nevent description: " + this.eventDescription;
        return s;
    }

    public void addEventDocument(Event e) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("day", e.getDay());
        entry.put("description", e.getEventDescription());
        entry.put("duration", e.getDuration());
        entry.put("hour", e.getHour());
        entry.put("min", e.getMinute());
        entry.put("month",e.getMonth());
        entry.put("name", e.getEventName());
        entry.put("userID", e.getUsername());
        entry.put("year", e.getYear());

        db.collection("events")
                .add(entry)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception k) {
                        Log.w(TAG, "Error adding doc", k);
                    }
                });
    }
}
