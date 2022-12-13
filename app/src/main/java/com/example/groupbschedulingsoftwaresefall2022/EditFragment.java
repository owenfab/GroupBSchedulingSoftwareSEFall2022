package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass. Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private FirebaseFirestore fb;

  private Button cancelButton;
  private Button submitButton;
  private Button deleteButton;

  private EditText nameEdit;
  private EditText startEdit;
  private EditText endEdit;
  private EditText dayEdit;
  private EditText monthEdit;
  private EditText yearEdit;

  public EditFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment EditFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static EditFragment newInstance(String param1, String param2) {
    EditFragment fragment = new EditFragment();
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
    View view = inflater.inflate(R.layout.fragment_edit, container, false);

    fb = FirebaseFirestore.getInstance();

    nameEdit = (EditText) view.findViewById(R.id.editEventName);
    startEdit = (EditText) view.findViewById(R.id.editStartTime);
    endEdit = (EditText) view.findViewById(R.id.editEndTime);
    dayEdit = (EditText) view.findViewById(R.id.editDay);
    monthEdit = (EditText) view.findViewById(R.id.editMonth);
    yearEdit = (EditText) view.findViewById(R.id.editYear);

    cancelButton = (Button)view.findViewById(R.id.cancel_button);
    submitButton = (Button)view.findViewById(R.id.submitButton);
    deleteButton = (Button)view.findViewById(R.id.delete_button);

    int day = this.getArguments().getInt("day");
    int month = this.getArguments().getInt("month");
    int year = this.getArguments().getInt("year");
    String username = this.getArguments().getString("username");
    String start = this.getArguments().getString("start time");
    String end = this.getArguments().getString("end time");
    String evname = this.getArguments().getString("event name");

    nameEdit.setText(evname);
    startEdit.setText(start);
    endEdit.setText(end);
    dayEdit.setText(""+day);
    monthEdit.setText(""+month);
    yearEdit.setText(""+year);

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        boolean worked = false;
        Event e = new Event(month,day,year,0,0,0,evname,username,start,end);
        Map<String, Object> input = e.createHashMapForTransfer();
        try {
          fb.collection("events").document(evname).set(input);
          worked = true;
        } catch (Exception ex) {
          Toast.makeText(getActivity(), "Error; try again",
                  Toast.LENGTH_SHORT).show();
        }
        if (worked) {
          Intent toCalActivity = new Intent(getActivity(), calendarView.class);
          toCalActivity.putExtra("username", username);
          requireActivity().startActivity(toCalActivity);
        }
      }
    });

    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        boolean worked = false;
        String name = nameEdit.getText().toString().trim();
        String startTime = startEdit.getText().toString().trim();
        String endTime = endEdit.getText().toString().trim();
        String dayStr = dayEdit.getText().toString().trim();
        Integer dayEv = Integer.parseInt(dayStr);
        String monthStr = monthEdit.getText().toString().trim();
        Integer monthEv = Integer.parseInt(monthStr);
        String yearStr = yearEdit.getText().toString().trim();
        Integer yearEv = Integer.parseInt(yearStr);
        System.out.println("day: " + dayEv + " month: " + monthEv + " year: " + yearEv);
        //check if any are empty
        if (name.equals("") || startTime.equals("") || endTime.equals("")
                || dayStr.equals("") || monthStr.equals("") || yearStr.equals("")) {
          Toast.makeText(getActivity(), "Please fill out all fields",
                  Toast.LENGTH_SHORT).show();
        } else {
          Event e = new Event(monthEv,dayEv,yearEv,0,0,0,name,username,startTime,endTime);
          Map<String, Object> input = e.createHashMapForTransfer();
          System.out.println(e.toString());
          try {
            fb.collection("events").document(name).set(input);
            worked = true;
          } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error; try again",
                    Toast.LENGTH_SHORT).show();
          }
          if (worked) {
            Intent toCalActivity = new Intent(getActivity(), calendarView.class);
            toCalActivity.putExtra("username", username);
            requireActivity().startActivity(toCalActivity);
          }
        }
      }
    });

    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent toCalActivity = new Intent(getActivity(), calendarView.class);
        toCalActivity.putExtra("username", username);
        requireActivity().startActivity(toCalActivity);
      }
    });

    return view;
  }

}