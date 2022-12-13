package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
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
import java.util.Map;


public class  DayViewActivity extends AppCompatActivity {

    private RecyclerView eventDay;
    private ArrayList<Event> eventArrayList;
    private DayAdapter dayAdapter;
    private FirebaseFirestore fb;

    private Button returnButton;
    private Button acceptButton;
    private Button editButton;

    private EditText editEvent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayview);
        fb = FirebaseFirestore.getInstance();

        //get username and day data from CalendarView
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        long day =  intent.getIntExtra("selectedDay", 0);
        long month =  intent.getIntExtra("selectedMonth", 0);
        long year =  intent.getIntExtra("selectedYear", 0);
        int evday = intent.getIntExtra("day", 0);
        int evmonth = intent.getIntExtra("month", 0);
        int evyear = intent.getIntExtra("year", 0);
        String mode = intent.getStringExtra("mode");

        eventDay = findViewById(R.id.idDayEventViews);

        returnButton = (Button)findViewById(R.id.returnButton);
        acceptButton = (Button)findViewById(R.id.acceptButton);
        editButton = (Button)findViewById(R.id.EditEventButton);

        editEvent = (EditText)findViewById(R.id.editEnter);

        if (mode.equals("view")) {
            acceptButton.setVisibility(View.GONE);
        } else if (mode.equals("share")) {
            acceptButton.setVisibility(View.VISIBLE);
            editEvent.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        }

        eventArrayList = new ArrayList<>();
        eventDay.setHasFixedSize(true);
        eventDay.setLayoutManager(new LinearLayoutManager(this));

        dayAdapter = new DayAdapter(eventArrayList, this);

        CollectionReference eventRef = fb.collection("events");
        CollectionReference sharedRef = fb.collection("shares");
        if (mode.equals("view")) {
            eventRef.whereEqualTo("associatedUser", username);
            eventRef.whereEqualTo("day", day);
            eventRef.whereEqualTo("month", month);
            eventRef.whereEqualTo("year", year);
        } else if (mode.equals("share")) {
            sharedRef.whereEqualTo("sharedTo", username);
            sharedRef.whereEqualTo("day", day);
            sharedRef.whereEqualTo("month", month);
            sharedRef.whereEqualTo("year", year);
        }

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

        if (mode.equals("view")) {
            eventRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : docList) {
                            String user = (String) d.get("associatedUser");
                            System.out.println("day" + day + " month " + month + " year " + year);
                            if ((long) d.get("day") == day && (long) d.get("month") == month
                                    && (long) d.get("year") == year && user.equals(username)) {
                                String evName = (String) d.get("name");
                                String start = (String) d.get("start time");
                                String end = (String) d.get("end time");
                                System.out.println("name: " + evName);
                                System.out.println("associatedUser: " + d.get("associatedUser"));
                                System.out.println("start time: " + d.get("start time"));
                                System.out.println("end time: " + d.get("end time"));
                                System.out.println("day: " + d.get("day") + " " + d.get("month") + " " + d.get("year"));

                                //make into object
                                Event e = new Event(0, 0, 0, 0, 0, 0,
                                        evName, username, start, end);
                                eventArrayList.add(e);
                            }
                        /*
                        Event e = d.toObject(Event.class);
                        System.out.println(e.toString());

                         */
                            //eventArrayList.add(e);

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
        }

        if (mode.equals("share")) {
            sharedRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : docList) {
                            String user = (String) d.get("sharedTo");
                            System.out.println("day" + day + " month " + month + " year " + year);
                            if ((long) d.get("day") == day && (long) d.get("month") == month
                                    && (long) d.get("year") == year && user.equals(username)) {
                                String evName = (String)d.get("associatedUser") + "TO" + user + "EVENT" + (String)d.get("name");
                                String start = (String) d.get("start time");
                                String end = (String) d.get("end time");
                                //make into object
                                Event e = new Event(evmonth+1, evday, evyear, 0, 0, 0,
                                        evName, username, start, end);
                                eventArrayList.add(e);
                            }
                        /*
                        Event e = d.toObject(Event.class);
                        System.out.println(e.toString());

                         */
                            //eventArrayList.add(e);

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
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retCalActivity = new Intent(DayViewActivity.this, calendarView.class);
                retCalActivity.putExtra("username", username);
                DayViewActivity.this.startActivity(retCalActivity);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            Event eventFound;
            @Override
            public void onClick(View view) {
                boolean exists = false;
                eventFound = new Event();
                String text = editEvent.getText().toString().trim();
                //data integrity
                //if field is empty
                if (text.equals("")) {
                    Toast.makeText(DayViewActivity.this, "Please enter an event name!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //otherwise check if this event exists
                    for (Event e : eventArrayList) {
                        if (e.getEventName().equals(text)) {
                            eventFound = new Event(e);
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        Toast.makeText(DayViewActivity.this,
                                "Please enter an event in this day's list",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //delete original event
                        fb.collection("events").document(eventFound.getEventName())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        eventDay.setVisibility(View.GONE);
                                        returnButton.setVisibility(View.GONE);
                                        editEvent.setVisibility(View.GONE);
                                        editButton.setVisibility(View.GONE);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("day", evday);
                                        bundle.putInt("month", evmonth+1);
                                        bundle.putInt("year", evyear);
                                        bundle.putString("username", username);
                                        bundle.putString("start time", eventFound.getStartTime());
                                        bundle.putString("end time", eventFound.getEndTime());
                                        bundle.putString("event name", eventFound.getEventName());
                                        System.out.println("from dayView to editFragment; month: " + evmonth+1);
                                        EditFragment frag = new EditFragment();
                                        frag.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, frag).commit();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DayViewActivity.this, "Error; please try again",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Event e : eventArrayList) {
                    try {
                        Map<String, Object> input = e.createHashMapForTransfer();
                        fb.collection("events").document(e.getEventName()).set(input);
                        Toast.makeText(DayViewActivity.this, "Success!",
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(DayViewActivity.this, "Error; try again",
                                Toast.LENGTH_SHORT).show();
                    }
                    fb.collection("shares").document(e.getEventName())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DayViewActivity.this, "Success!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DayViewActivity.this, "Failure",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

}
