package com.example.movieratingservice;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseDatabaseManager {

    private String userID;


    public FirebaseDatabaseManager() {
        initializeFirebase();
    }

    private void initializeFirebase() {
        try {

            FileInputStream serviceAccount = new FileInputStream("Computer-Engineering-Capstone/movieapp-e3f11-firebase-adminsdk-vskir-a4ead6f6a2.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://movieapp-e3f11-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }


    public String registerUser(Users user) {
        String uuid = UUID.randomUUID().toString();
        Map<String, Object> userData = new HashMap<>();
        userData.put("first_name", user.getFirstName());
        userData.put("last_name", user.getLastName());
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        userData.put("balance", user.getBalance());
        DatabaseReference ref = getDatabase().getReference("users");
        ref.child(uuid).setValueAsync(userData);
        return "User registered successfully with ID: " + uuid;
    }

    public String loginUser(String email, String password) {
        DatabaseReference ref = getDatabase().getReference("users");
        final String[] result = {""};
        final boolean[] isCompleted = {false};

        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String storedPassword = snapshot.child("password").getValue(String.class);
                        if (storedPassword.equals(password)) {
                            result[0] = "User logged in successfully";
                            userID = snapshot.getKey();
                        } else {
                            result[0] = "incorrect password";
                        }
                    }
                } else {
                    result[0] = "account doesn't exist";
                }
                isCompleted[0] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
                isCompleted[0] = true;
            }
        });

        while (!isCompleted[0]) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        return result[0];
    }


    public String updateUserDetails(String userId, String firstName, String lastName, String email) {
        DatabaseReference ref = getDatabase().getReference("users/" + userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("first_name", firstName);
        updates.put("last_name", lastName);
        updates.put("email", email);
        ref.updateChildrenAsync(updates);
        return "User details updated successfully";
    }

    public String updateUserPassword(String userId, String currentPassword, String newPassword) {
        DatabaseReference ref = getDatabase().getReference("users/" + userId);
        final String[] result = new String[1];
        final boolean[] isCompleted = {false};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child("password").getValue(String.class);
                    if (password != null && password.equals(currentPassword)) {
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("password", newPassword);
                        ref.updateChildrenAsync(updates);
                        result[0] = "User password updated successfully";
                    } else {
                        result[0] = "Current password is incorrect";
                    }
                } else {
                    result[0] = "User not found";
                }
                isCompleted[0] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
                isCompleted[0] = true;
            }
        });


        return result[0];
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}


