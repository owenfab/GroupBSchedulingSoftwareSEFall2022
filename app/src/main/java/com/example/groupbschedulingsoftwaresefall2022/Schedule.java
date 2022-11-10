package com.example.groupbschedulingsoftwaresefall2022;

public class Schedule {

    private static int overallID = 0;
    private int scheduleID;
    private String scheduleName;
    private static int numSchedules = 0;
    private final int lengthOf = 50;
    private Event[] sched = new Event[lengthOf];

    public Schedule() {
        scheduleName = "";
        scheduleID = getUniqueID();
        numSchedules++;
        for (int i = 0; i < lengthOf; i++) {
            this.sched[i] = new Event();
        }
    }

    public Schedule(String name) {
        scheduleID = getUniqueID();
        scheduleName = name;
        for (int i = 0; i < lengthOf; i++) {
            sched[i] = new Event();
        }
    }

    public Schedule(Schedule other) {
        this.scheduleID = getUniqueID();
        this.scheduleName = other.scheduleName;
        for (int i = 0; i < lengthOf; i++) {
            this.sched[i] = other.sched[i];
        }
    }

    @Override public String toString() {
        String s = "";
        s += "ID: " + this.scheduleID;
        s += "name: " + this.scheduleName;
        for (int i = 0; i < lengthOf; i++) {
            s += sched[i] + "\n";
        }
        return s;
    }

    private int getUniqueID() {
        return overallID++;
    }

}
