package com.example.movieratingservice.firebase;

import com.example.movieratingservice.*;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.remoteconfig.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirebaseDatabaseManager {

    private static FirebaseDatabaseManager instance;
    private FirebaseApp firebaseApp;

    private DatabaseReference database;

    private String lastRegisteredUserId;  // Field to store the UUID of the last registered user


    private ExecutorService executor = Executors.newCachedThreadPool();

    private FirebaseDatabaseManager() {
        initializeFirebase();
    }

    public static synchronized FirebaseDatabaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseDatabaseManager();
        }
        return instance;
    }

    private void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("path/to/your/firebase-key.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://your-database-url.firebaseio.com")
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    public void getAllMovies(DataCallback callback) {
        DatabaseReference ref = getDatabase().getReference("movies");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder allMovies = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Movies movie = snapshot.getValue(Movies.class);
                        if (movie != null) {
                            allMovies.append(movie.toString()).append("\n");
                        }
                    }
                    callback.onSuccess(allMovies.toString());
                } else {
                    callback.onFailure("No movies found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }
    public void getMovieById(String movieId, DataCallback callback) {
        database.child("movies").child(movieId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Movies movie = dataSnapshot.getValue(Movies.class);
                            callback.onSuccess(movie.toString());
                        } else {
                            callback.onFailure("Movie not found");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }

    public void searchMoviesByTitle(String title, DataCallback callback) {
        Query query = database.child("movies").orderByChild("title").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder builder = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Movies movie = snapshot.getValue(Movies.class);
                        builder.append(movie.toString()).append("\n");
                    }
                    callback.onSuccess(builder.toString());
                } else {
                    callback.onFailure("No movies found with the title: " + title);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public void getAllGenres(DataCallback callback) {
        database.child("genres").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder builder = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MovieGenre genre = snapshot.getValue(MovieGenre.class);
                        builder.append(genre.toString()).append("\n");
                    }
                    callback.onSuccess(builder.toString());
                } else {
                    callback.onFailure("No genres found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public void getGenreById(String genreId, DataCallback callback) {
        database.child("genres").child(genreId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            MovieGenre genre = dataSnapshot.getValue(MovieGenre.class);
                            callback.onSuccess(genre.toString());
                        } else {
                            callback.onFailure("Genre not found");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.getMessage());
                    }
                });
    }

    public void getRentedMoviesByUserId(String userId, DataCallback callback) {
        Query query = database.child("rentals").orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder builder = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        builder.append(rentedMovie.toString()).append("\n");
                    }
                    callback.onSuccess(builder.toString());
                } else {
                    callback.onFailure("No rentals found for this user");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public void getUserRentalByStatus(String userId, String status, DataCallback callback) {
        Query query = database.child("rentals").orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder builder = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        if (rentedMovie != null && rentedMovie.getStatus().equals(status)) {
                            builder.append(rentedMovie.toString()).append("\n");
                        }
                    }
                    callback.onSuccess(builder.toString());
                } else {
                    callback.onFailure("No rentals found for this user with the specified status");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public void isRentedBefore(String userId, String movieId, DataCallback callback) {
        Query query = database.child("rentals").orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean isRented = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        if (rentedMovie != null && rentedMovie.getMovie_id().equals(movieId)) {
                            isRented = true;
                            break;
                        }
                    }
                    callback.onSuccess(Boolean.toString(isRented));
                } else {
                    callback.onFailure("No rentals found for this user");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }



    public CompletableFuture<String> registerUser(Users user) {
        String uuid = UUID.randomUUID().toString();
        Map<String, Object> userData = Map.of(
                "first_name", user.getFirstName(),
                "last_name", user.getLastName(),
                "email", user.getEmail(),
                "balance", user.getBalance()
        );

        DatabaseReference ref = getDatabase().getReference("users");
        ApiFuture<Void> future = ref.child(uuid).setValueAsync(userData);

        return FirebaseUtil.toCompletableFuture(future)
                .thenApply(ignore -> "User registered successfully with ID: " + uuid);
    }

    public String getLastRegisteredUserId() {
        return lastRegisteredUserId;
    }

    public CompletableFuture<String> loginUser(String email, String password) {
        DatabaseReference ref = getDatabase().getReference("users");
        CompletableFuture<String> resultFuture = new CompletableFuture<>();

        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean valid = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String storedPassword = snapshot.child("password").getValue(String.class);
                        if (storedPassword != null && storedPassword.equals(password)) {
                            valid = true;
                            break;
                        }
                    }
                    if (valid) {
                        resultFuture.complete("User logged in successfully");
                    } else {
                        resultFuture.complete("Incorrect password");
                    }
                } else {
                    resultFuture.complete("Account doesn't exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                resultFuture.completeExceptionally(new Exception(databaseError.getMessage()));
            }
        });

        return resultFuture;
    }

    public CompletableFuture<String> updateUserDetails(String userId, String firstName, String lastName, String email) {
        DatabaseReference ref = getDatabase().getReference("users").child(userId);

        // Prepare the data to update
        Map<String, Object> updates = new HashMap<>();
        updates.put("first_name", firstName);
        updates.put("last_name", lastName);
        updates.put("email", email);

        // Update the data asynchronously
        ApiFuture<Void> future = ref.updateChildrenAsync(updates);

        // Convert ApiFuture<Void> to CompletableFuture<String>
        return FirebaseUtil.toCompletableFuture(future)
                .thenApply(ignore -> "User details updated successfully for user ID: " + userId)
                .exceptionally(e -> "Failed to update user details: " + e.getMessage());
    }

    public CompletableFuture<String> updateUserPassword(String userId, String currentPassword, String newPassword) {
        DatabaseReference ref = getDatabase().getReference("users").child(userId).child("password");

        // Fetch the current password and then update if it matches
        CompletableFuture<String> resultFuture = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(currentPassword)) {
                        // Current password is correct, proceed to update to new password
                        ApiFuture<Void> updateFuture = ref.setValueAsync(newPassword);
                        // Convert ApiFuture to CompletableFuture
                        FirebaseUtil.toCompletableFuture(updateFuture).thenAccept(voidResult -> {
                            resultFuture.complete("Password updated successfully.");
                        }).exceptionally(e -> {
                            resultFuture.completeExceptionally(new RuntimeException("Failed to update password.", e));
                            return null;
                        });
                    } else {
                        resultFuture.complete("Current password is incorrect.");
                    }
                } else {
                    resultFuture.complete("User not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                resultFuture.completeExceptionally(new RuntimeException("Database error: " + databaseError.getMessage()));
            }
        });

        return resultFuture;
    }

    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance(firebaseApp);
    }

}
