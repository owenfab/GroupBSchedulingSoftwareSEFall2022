package com.example.groupbschedulingsoftwaresefall2022;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupbschedulingsoftwaresefall2022.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;

public class calendarView extends AppCompatActivity {

    private EditText editText;
    private EditText startTime;
    private EditText endTime;
    private TextView textView2;
    private TextView selectText;

    private CalendarView calendarView;

    private String selectedDate;
    private String start, end;

    private mySQLiteDBHandler dbHandler;
    private SQLiteDatabase sqLiteDatabase;

    private Button createButton;
    private Button viewButton;
    private Button logoutButton;
    private Button shareButton;
    private Button sharedWithMe;

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
        viewButton = (Button)findViewById(R.id.viewButton);
        shareButton = (Button)findViewById(R.id.buttonShare);
        sharedWithMe = (Button)findViewById(R.id.viewShared);
        logoutButton = (Button)findViewById(R.id.logout_button);

        textView2 = (TextView)findViewById(R.id.textView2);
        selectText = (TextView)findViewById(R.id.selectedText);

        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginActivity = new Intent(calendarView.this,LoginActivity.class);
                calendarView.this.startActivity(toLoginActivity);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + month + dayOfMonth;
                System.err.println("selectedDate: "+selectedDate);
                evyear = year;
                evmonth = month;
                evday = dayOfMonth;
                System.out.println(evday + " " + evmonth + " " + evyear);
                dateViewed.setText(year + " " + (month+1) + " " + dayOfMonth);
                ReadDatabase(view);
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

                    InsertDatabase(name,selectedDate);
                } catch (Exception ex) {
                    Toast.makeText(calendarView.this, "Error; try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDVActivity = new Intent(calendarView.this, DayViewActivity.class);
                toDVActivity.putExtra("username", username);
                toDVActivity.putExtra("selectedDay", evday);
                toDVActivity.putExtra("selectedMonth", evmonth+1);
                toDVActivity.putExtra("selectedYear", evyear);
                toDVActivity.putExtra("day", evday);
                toDVActivity.putExtra("month", evmonth);
                toDVActivity.putExtra("year", evyear);
                toDVActivity.putExtra("mode", "view");
                calendarView.this.startActivity(toDVActivity);
            }
        });

        sharedWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDVActivity = new Intent(calendarView.this, DayViewActivity.class);
                toDVActivity.putExtra("username", username);
                toDVActivity.putExtra("selectedDay", evday);
                toDVActivity.putExtra("selectedMonth", evmonth+1);
                toDVActivity.putExtra("selectedYear", evyear);
                toDVActivity.putExtra("day", evday);
                toDVActivity.putExtra("month", evmonth);
                toDVActivity.putExtra("year", evyear);
                toDVActivity.putExtra("mode", "share");
                calendarView.this.startActivity(toDVActivity);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setVisibility(View.GONE);
                createButton.setVisibility(View.GONE);
                viewButton.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                startTime.setVisibility(View.GONE);
                endTime.setVisibility(View.GONE);
                endTime.setVisibility(View.GONE);
                selectText.setVisibility(View.GONE);
                dateViewed.setVisibility(View.GONE);
                logoutButton.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                shareButton.setVisibility(View.GONE);
                sharedWithMe.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putInt("day", evday);
                bundle.putInt("month", evmonth+1);
                bundle.putInt("year", evyear);
                bundle.putString("username", username);
                ShareFragment frag = new ShareFragment();
                frag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
            }
        });

        try{

            dbHandler = new mySQLiteDBHandler(this, "CalendarDatabase", null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS EventCalendar(Date TEXT, Event TEXT)");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertDatabase(String name, String selectedDate){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event", name);
        long value = sqLiteDatabase.insert("EventCalendar", null, contentValues);
        System.err.println("InsertDatabase value: "+value+" : "+selectedDate+" : "+name);
    }

    public void ReadDatabase(View view){
        System.err.println("ReadDatabase selectedDate: .... "+selectedDate);
        String query = "Select Event from EventCalendar where Date = " + selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if(cursor!=null){
                    String existingText="";
                    if(cursor.moveToFirst()){
                        do {
                            existingText+=cursor.getString(0)+"\n";
                        }while(cursor.moveToNext());
                    }
                    textView2.setText("Events: "+existingText);
            }else{
                System.err.println("cursor is null ");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}