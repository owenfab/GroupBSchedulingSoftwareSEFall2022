package com.example.groupbschedulingsoftwaresefall2022;

import java.util.Calendar;

public class Event {

    private int eventID;
    private Calendar eventDate;
    private String eventName;
    private String eventDescription;

    public Event(int eID, int eMonth, int eDay, int eYear,
                 int eHour, int eMin, String eName, String eDr) {
        eventID = eID;
        eventDate = Calendar.getInstance();
        eventDate.set(eYear, eMonth-1, eDay, eHour, eMin, 0);
        eventName = eName;
        eventDescription = eDr;
    }

    public Event() {
        eventID = -1;
        eventDate = Calendar.getInstance();
        eventName = "unknown";
        eventDescription = "unknown";
    }

    public Event(Event e) {
        this.eventID = e.eventID;
        this.eventDate = Calendar.getInstance();
        this.eventDate.set(e.eventDate.get(Calendar.YEAR), e.eventDate.get(Calendar.MONTH),
                e.eventDate.get(Calendar.DATE), e.eventDate.get(Calendar.HOUR),
                e.eventDate.get(Calendar.MINUTE), 0);
        this.eventName = e.eventName;
        this.eventDescription = e.eventDescription;
    }

    public int getID() {
        return eventID;
    }

    @Override
    public String toString() {
        String s = "";
        //add eventDate info
        s += "eventID: " + this.eventID + "\nevent name: " + this.eventName + "\nevent date: " + this.eventDate.getTime()
                + "\nevent description: " + this.eventDescription;
        return s;
    }

}
