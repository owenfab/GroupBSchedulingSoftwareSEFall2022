package com.example.groupbschedulingsoftwaresefall2022;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbschedulingsoftwaresefall2022.Event;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class calendarView extends AppCompatActivity {

    private EditText editText;
    private EditText startTime;
    private EditText endTime;

    private CalendarView calendarView;

    private String selectedDate;
    private String start, end;

    private mySQLiteDBHandler dbHandler;
    private SQLiteDatabase sqLiteDatabase;

    private Button createButton;

    private FirebaseFirestore fb;

    private TextView dateViewed;

    private int evmonth, evday, evyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        fb = FirebaseFirestore.getInstance();

        //receive username from LoginActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        dateViewed = (TextView)findViewById(R.id.selectedDate);

        editText = (EditText)findViewById(R.id.editTextTextPersonName);
        startTime = (EditText)findViewById(R.id.startTimeEditText);
        endTime = (EditText)findViewById(R.id.endTimeEditText);

        calendarView = (CalendarView)findViewById(R.id.calendarViewId);
        createButton = (Button)findViewById(R.id.buttonCreate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + month + dayOfMonth;
                System.out.print("selectedDate: "+selectedDate);
                evyear = year;
                evmonth = month;
                evday = dayOfMonth;
                dateViewed.setText(year + " " + (month+1) + " " + dayOfMonth);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> entry = Event.createDefaultMapForPlaceHolder();
                try {
                    String name = editText.getText().toString();
                    start = startTime.getText().toString();
                    end = endTime.getText().toString();
                    Event e = new Event(evmonth+1,evday,evyear,0,0,0,
                            name, username, start, end);
                    entry = e.createHashMapForTransfer();
                    fb.collection("events").document(name).set(entry);
                    Toast.makeText(calendarView.this, "Success!",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(calendarView.this, "Error; try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        try{

            dbHandler = new mySQLiteDBHandler(this, "CalendarDatabase", null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Date TEXT, Event TEXT)");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event", editText.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null, contentValues);

    }

    public void ReadDatabase(View view){
        String query = "Select Event from EventCalendar where Date = " + selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));
        }
        catch (Exception e){
            e.printStackTrace();
            editText.setText("");
        }
    }

}