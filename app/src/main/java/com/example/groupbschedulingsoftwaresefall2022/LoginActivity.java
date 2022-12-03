package com.example.groupbschedulingsoftwaresefall2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button newUser;
    Button loginButton;

    TextView name;

    EditText username;

    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        fb = FirebaseFirestore.getInstance();

        newUser = (Button)findViewById(R.id.new_user_button);
        loginButton = (Button)findViewById(R.id.button2);
        username = (EditText)findViewById(R.id.editTextTextEmailAddress);
        name = (TextView)findViewById(R.id.textView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get username
                String userID = username.getText().toString();
                //check if empty
                if (userID.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter a username",
                            Toast.LENGTH_SHORT).show();
                }
                //otherwise
                else {
                    //attempt to read in data
                    DocumentReference documentReference = fb.collection("users").document(userID);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //if user is in db
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    //successful login!
                                    Intent toCreationActivity = new Intent(LoginActivity.this, calendarView.class);
                                    toCreationActivity.putExtra("username", userID);
                                    LoginActivity.this.startActivity(toCreationActivity);
                                }
                                //if user is not in db
                                else {
                                    Toast.makeText(LoginActivity.this, "Username not registered; please create an account",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            //if task failed
                            else {
                                Toast.makeText(LoginActivity.this, "Task failed; try again",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                username.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewUser()).commit();
            }
        });
    }

}