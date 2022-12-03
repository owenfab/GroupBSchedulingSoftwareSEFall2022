package com.example.groupbschedulingsoftwaresefall2022;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class User {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userID;

    public User(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return this.userID;
    }

    @Override public String toString() {
        return "userID: " + this.getUserID();
    }
    public void addUserDocument() {
        Map<String, Object> entry = new HashMap<>();
        entry.put("userID", getUserID());
        db.collection("users").document(this.getUserID()).set(entry);
    }
}
