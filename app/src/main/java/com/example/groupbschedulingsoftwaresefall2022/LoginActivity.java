package com.example.groupbschedulingsoftwaresefall2022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button newUser;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newUser = findViewById(R.id.new_user_button);
        newUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.new_user_button) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewUser()).commit();
        }
    }

    public void login(View view) {
        username = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String userID = username.getText().toString();
        if (userID.equals("owenfab")) {
            Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT);
        }
        else
            Toast.makeText(getApplicationContext(), "This username is not registered", Toast.LENGTH_LONG);
    }
}