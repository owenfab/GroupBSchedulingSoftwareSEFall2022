package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.groupbschedulingsoftwaresefall2022", appContext.getPackageName());
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