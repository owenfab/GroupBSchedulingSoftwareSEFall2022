package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button test;
    private Button share;

    private EditText usernameField;
    private EditText eventField;

    private TextView selectedDay;

    private FirebaseFirestore fb;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareFragment newInstance(String param1, String param2) {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        fb = FirebaseFirestore.getInstance();

        test = (Button)view.findViewById(R.id.test);
        share = (Button)view.findViewById(R.id.share_button);

        selectedDay = (TextView)view.findViewById(R.id.selectedDay2);

        usernameField = (EditText)view.findViewById(R.id.username);
        eventField = (EditText)view.findViewById(R.id.eventName);

        String user = this.getArguments().getString("username");
        int day = this.getArguments().getInt("day");
        int month = this.getArguments().getInt("month");
        int year = this.getArguments().getInt("year");

        selectedDay.setText(year + " " + month + " " + day);

        share.setOnClickListener(new View.OnClickListener() {
            boolean isUserFound;
            boolean isEventFound;
            Event eventFound;
            String userFound;
            CollectionReference userRef = fb.collection("users");
            CollectionReference eventRef = fb.collection("events");
            ArrayList<User> usersInDB = new ArrayList<>();
            ArrayList<Event> eventsInDB = new ArrayList<>();
            @Override
            public void onClick(View view) {
                String shareName = usernameField.getText().toString();
                String eventName = eventField.getText().toString();
                userRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qs) {
                        if (!qs.isEmpty()) {
                            List<DocumentSnapshot> docList = qs.getDocuments();
                            for (DocumentSnapshot u : docList) {
                                User user = new User((String)u.get("userID"));
                                usersInDB.add(user);
                            }
                        }
                    }
                });
                eventRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qs) {
                        List<DocumentSnapshot> docList = qs.getDocuments();
                        for (DocumentSnapshot e : docList) {
                            Event event = new Event(month,day,year,0,0,0,
                                    (String)e.get("name"), (String)e.get("associatedUser"),
                                    (String)e.get("start time"),(String)e.get("end time"));
                            eventsInDB.add(event);
                        }
                    }
                });
                //handle username field integrity
                if (shareName.trim().equals("")) {
                    Toast.makeText(getActivity(),"Please fill out all fields",
                            Toast.LENGTH_SHORT).show();
                } else if (shareName.trim().equals(user)) {
                    Toast.makeText(getActivity(),"Please choose another user",
                            Toast.LENGTH_SHORT).show();
                } else {
                    for (User u : usersInDB) {
                        if (u.getUserID().equals(shareName)) {
                            isUserFound = true;
                            break;
                        }
                    }
                }

                //handle event field integrity
                if (eventName.trim().equals("")) {
                    Toast.makeText(getActivity(),"Please fill out all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    for (Event e: eventsInDB) {
                        if (e.getUsername().equals(user) && e.getEventName().equals(eventName)
                                && e.getDay() == day && e.getMonth() == month
                                && e.getYear() == year) {
                            eventFound = new Event(e);
                            isEventFound = true;
                            break;
                        }
                    }
                }

                if(isUserFound && isEventFound) {
                    Map<String, Object> input = eventFound.createHashMapForTransfer();
                    input.put("sharedTo", shareName);
                    String docName = user + "TO" + shareName + "EVENT" + eventFound.getEventName();
                    try {
                        fb.collection("shares").document(docName).set(input);
                        Toast.makeText(getActivity(),
                                "Success! Please tell the other user to accept your event",
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(),"Error; please try again",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"Error; please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToCalendar = new Intent(getActivity(), calendarView.class);
                backToCalendar.putExtra("username", user);
                requireActivity().startActivity(backToCalendar);
            }
        });

        return view;
    }
}