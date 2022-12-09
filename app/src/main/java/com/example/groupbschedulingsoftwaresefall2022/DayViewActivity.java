package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class  DayViewActivity extends AppCompatActivity {

    private RecyclerView eventDay;
    private ArrayList<Event> eventArrayList;
    private DayAdapter dayAdapter;
    private FirebaseFirestore fb;

    private Button returnButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayview);
        fb = FirebaseFirestore.getInstance();

        //get username and day data from CalendarView
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int day =  intent.getIntExtra("selectedDay", 0);
        int month =  intent.getIntExtra("selectedMonth", 0);
        int year =  intent.getIntExtra("selectedYear", 0);

        eventDay = findViewById(R.id.idDayEventViews);
        returnButton = (Button)findViewById(R.id.returnButton);

        eventArrayList = new ArrayList<>();
        eventDay.setHasFixedSize(true);
        eventDay.setLayoutManager(new LinearLayoutManager(this));

        dayAdapter = new DayAdapter(eventArrayList, this);





        CollectionReference eventRef = fb.collection("Event");
        eventRef.whereEqualTo("associatedUser", username);
        eventRef.whereEqualTo("day", day);
        eventRef.whereEqualTo("month", month);
        eventRef.whereEqualTo("year", year);

        eventDay.setAdapter(dayAdapter);
        eventDay.addOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                PopupMenu event_menu = new PopupMenu(DayViewActivity.this, eventDay);
                event_menu.getMenuInflater().inflate(R.menu.event_menu, event_menu.getMenu());
                event_menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals(R.string.edit_label)) {
                            returnButton.setVisibility(View.GONE);
                            eventDay.setVisibility(View.GONE);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditFragment()).commit();

                        }
                        return true;
                    }
                });
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        eventRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : docList) {
                        Event e = d.toObject(Event.class);
                        eventArrayList.add(e);
                    }
                    dayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DayViewActivity.this, "No data found in db", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DayViewActivity.this, "Failure to get data", Toast.LENGTH_SHORT).show();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retCalActivity = new Intent(DayViewActivity.this, calendarView.class);
                DayViewActivity.this.startActivity(retCalActivity);
            }
        });


    }

}
