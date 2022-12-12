package com.example.groupbschedulingsoftwaresefall2022;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

public class Event {

    private Calendar eventDate;
    @PropertyName("name")
    private String name;
    private String eventDescription;
    private int duration;
    @PropertyName("associatedUser")
    private String username;
    @PropertyName("start time")
    private String start;
    @PropertyName("end time")
    private String end;

    /**
     * fully loaded Event constructor
     * @param eMonth month of the event
     * @param eDay day of the event
     * @param eYear year of the event
     * @param eHour hour of the event
     * @param eMin minute of the event
     * @param d duration of the event in minutes
     * @param eName name of the event
     * @param username name of the user creating the Event; this is needed to properly reference
     *                 the correct User in the DB.
     */
    public Event(int eMonth, int eDay, int eYear,
                 int eHour, int eMin, int d, String eName, String username, String start, String end) {
        eventDate = Calendar.getInstance();
        eventDate.set(eYear, eMonth-1, eDay, eHour, eMin, 0);
        duration = d;
        name = eName;
        this.username = username;
        this.start = start;
        this.end = end;
    }

    /**
     * default constructor for Event; a unique ID is still issued since
     * this is still an event, just an empty one
     */
    public Event() {
        eventDate = Calendar.getInstance();
        duration = -1;
        name = "unknown";
        eventDescription = "unknown";
        username = "unknown";
        start = "unknown";
        end = "unknown";
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
        this.name = e.name;
        this.eventDescription = e.eventDescription;
        this.username = e.username;
        this.start = e.start;
        this.end = e.end;
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
    public Integer getMonth() { return this.eventDate.get(Calendar.MONTH)+1; }
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
        return this.name;
    }

    /**
     * @return username associated with calling Event object
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return start associated with calling Event object
     */
    public String getStartTime() {
        return start;
    }

    /**
     * @return end associated with calling Event object
     */
    public String getEndTime() {
        return end;
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
    @PropertyName("associatedUser")
    public void setname(String name) {
        this.name = name;
    }

    /**
     * sets the associated username of calling Event object
     * @param username
     */
    @PropertyName("associatedUser")
    public void setUser(String username) {
        this.username = username;
    }

    @PropertyName("start time")
    public void setStart(String time) {
        this.start = time;
    }

    @PropertyName("end time")
    public void setEnd(String time) {
        this.end = time;
    }

    /**
     * toString for Event class
     * @return eventID, name, date, duration (in minutes), and description
     */
    @Override public String toString() {
        String s = "";
        //add eventDate info
        s += "event name: " + this.name + "\nevent date: "
                + this.eventDate.getTime() + "\nevent duration: " + this.duration
                + "\nevent description: " + this.eventDescription;
        return s;
    }

    /**
     *
     * @return Map usable to send to Firebase
     */
    public Map<String, Object> createHashMapForTransfer() {
        Map<String, Object> input = new HashMap<>();
        input.put("day", this.getDay());
        input.put("name", this.getEventName());
        input.put("associatedUser", this.getUsername());
        input.put("start time", this.getStartTime());
        input.put("end time", this.getEndTime());
        input.put("year", this.getYear());
        input.put("month", this.getMonth());
        return input;
    }

    /**
     *
     * @return Map with basically nothing in it
     */
    public static Map<String, Object> createDefaultMapForPlaceHolder() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("day", "today");
        return inputMap;
    }
}
