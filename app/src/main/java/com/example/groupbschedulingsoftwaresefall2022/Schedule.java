package com.example.groupbschedulingsoftwaresefall2022;

public class Schedule {

    private static int overallID = 0;
    private int scheduleID;
    private String scheduleName;
    private static int numSchedules = 0;
    private final int lengthOf = 50;
    private Event[] sched = new Event[lengthOf];

    /**
     * Schedule class default constructor; still assigns a unique ID because this is a Schedule
     * object, just an empty one
     */
    public Schedule() {
        scheduleName = "";
        scheduleID = getUniqueID();
        numSchedules++;
        for (int i = 0; i < lengthOf; i++) {
            this.sched[i] = new Event();
        }
    }

    /**
     * Schedule class constructor; creates a new Schedule object with the specified name and fills
     * it with default Event objects
     * @param name name of Schedule
     */
    public Schedule(String name) {
        scheduleID = getUniqueID();
        scheduleName = name;
        for (int i = 0; i < lengthOf; i++) {
            sched[i] = new Event();
        }
    }

    /**
     * Schedule class copy constructor; still assigns a unique ID value to the new object since
     * it will be treated as a disparate object with the same values as the argument Schedule object
     * @param other Schedule object to be copied
     */
    public Schedule(Schedule other) {
        this.scheduleID = getUniqueID();
        this.scheduleName = other.scheduleName;
        for (int i = 0; i < lengthOf; i++) {
            this.sched[i] = other.sched[i];
        }
    }

    /**
     * toString for Schedule class
     * @return ID, name and all contained Events of the Schedule object
     */
    @Override public String toString() {
        String s = "";
        s += "ID: " + this.scheduleID;
        s += "name: " + this.scheduleName;
        for (int i = 0; i < lengthOf; i++) {
            if (this.sched[i].getDuration() == -1)
                s += "default Event obj\n";
            else
                s += this.sched[i].toString() + "\n";
        }
        return s;
    }

    /**
     * creates a unique ID by incrementing a value
     * @return overallID++
     */
    private int getUniqueID() {
        return overallID++;
    }

}
