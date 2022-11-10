package com.example.groupbschedulingsoftwaresefall2022;

import java.util.Calendar;

public class Event {

    private static int eventID = 0;
    private Calendar eventDate;
    private String eventName;
    private String eventDescription;
    private int duration;

    public Event(int eMonth, int eDay, int eYear,
                 int eHour, int eMin, int d, String eName, String eDr) {
        eventID = getUniqueID();
        eventDate = Calendar.getInstance();
        eventDate.set(eYear, eMonth-1, eDay, eHour, eMin, 0);
        duration = d;
        eventName = eName;
        eventDescription = eDr;
    }

    public Event() {
        eventID = getUniqueID();
        eventDate = Calendar.getInstance();
        duration = -1;
        eventName = "unknown";
        eventDescription = "unknown";
    }

    public Event(Event e) {
        this.eventID = getUniqueID();
        this.eventDate = Calendar.getInstance();
        this.eventDate.set(e.eventDate.get(Calendar.YEAR), e.eventDate.get(Calendar.MONTH),
                e.eventDate.get(Calendar.DATE), e.eventDate.get(Calendar.HOUR),
                e.eventDate.get(Calendar.MINUTE), 0);
        this.duration = e.duration;
        this.eventName = e.eventName;
        this.eventDescription = e.eventDescription;
    }

    public int getID() {
        return this.eventID;
    }

    public int getYear() {
        return this.eventDate.get(Calendar.YEAR);
    }

    public int getMonth() {
        return this.eventDate.get(Calendar.MONTH);
    }

    public int getDay() {
        return this.eventDate.get(Calendar.DATE);
    }

    public int getHour() {
        return this.eventDate.get(Calendar.HOUR);
    }

    public int getMinute() {
        return this.eventDate.get(Calendar.MINUTE);
    }

    public int getDuration() {
        return this.duration;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    private int getUniqueID() {
        return eventID++;
    }

    @Override public String toString() {
        String s = "";
        //add eventDate info
        s += "eventID: " + this.eventID + "\nevent name: " + this.eventName + "\nevent date: "
                + this.eventDate.getTime() + "\nevent duration: " + this.duration
                + "\nevent description: " + this.eventDescription;
        return s;
    }

}
