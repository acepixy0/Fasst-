package com.example.movieratingservice;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
public class FirebaseDatabaseManager {

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
}
