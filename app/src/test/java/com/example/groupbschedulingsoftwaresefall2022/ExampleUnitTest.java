package com.example.groupbschedulingsoftwaresefall2022;

import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        Event e1 = new Event();
        e1.setEventName("Test Event");
        e1.setEventDescription("This is a test event description!");
        e1.setMonth(11);
        e1.setDay(13);
        e1.setHour(9);
        e1.setMinute(4);
        e1.setDuration(10);
        System.out.println(e1.toString());
    }

}