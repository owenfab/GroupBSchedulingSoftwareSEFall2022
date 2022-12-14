package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button back;

    public NewUser() {
        // Required empty public constructor
    }

    public static NewUser newInstance(String param1, String param2) {
        NewUser fragment = new NewUser();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        EditText newUserName = view.findViewById(R.id.editTextUsername);
        Button createNewUser = view.findViewById(R.id.createacc_id);
        back = (Button)view.findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLogin = new Intent(getActivity(), LoginActivity.class);
                requireActivity().startActivity(backToLogin);
            }
        });

        createNewUser.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View view) {
                String str = newUserName.getText().toString();

                User u_new = new User(str);
                try {
                    u_new.addUserDocument();
                    Toast.makeText(getActivity(), "Username Registered!",
                            Toast.LENGTH_LONG).show();
                } catch(Exception ex) {
                    Toast.makeText(getActivity(), "Task failed; please try again",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    return view;
    }
}