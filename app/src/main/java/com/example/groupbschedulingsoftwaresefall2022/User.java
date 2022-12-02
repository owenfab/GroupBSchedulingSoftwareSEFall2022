package com.example.groupbschedulingsoftwaresefall2022;

import static android.content.ContentValues.TAG;

import android.util.Log;
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
    private String email;
    private String password;

    public User(String userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
    }

    public User(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getEmail() {
        return this.email;
    }

    @Override public String toString() {
        return "userID: " + this.getUserID() + "\nemail: " + this.getEmail();
    }
    public void addUserDocument() {
        Map<String, Object> entry = new HashMap<>();
        entry.put("userID", getUserID());

        db.collection("users")
            .add(entry)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot written with ID: "
                        + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception k) {
                    Log.w(TAG, "Error adding doc", k);
                }
            });
    }
}
