package com.example.movieratingservice;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FirebaseDatabaseManager {

    private String userID;


    public FirebaseDatabaseManager() {
        initializeFirebase();
    }

    private void initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("Computer-Engineering-Capstone/movieapp-e3f11-firebase-adminsdk-vskir-a4ead6f6a2.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://movieapp-e3f11-default-rtdb.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
            }
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
        userData.put("firstName", user.getFirstName());
        userData.put("lastName", user.getLastName());
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        userData.put("balance", user.getBalance());
        DatabaseReference ref = getDatabase().getReference("users");
        ref.child(uuid).setValueAsync(userData);
        userID = uuid;
        return "User registered successfully";
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
                            result[0] = "Incorrect Password. Please Check Again";
                        }
                    }
                } else {
                    result[0] = "Account Doesn't Exist. Please Check Again";
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


    public Users getUserDetails(String userId) {
        DatabaseReference ref = getDatabase().getReference("users").child(userId);
        final Users[] user = {null};
        final boolean[] isCompleted = {false};

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user[0] = dataSnapshot.getValue(Users.class);
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

        return user[0];
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
                        } else {
                            System.out.println("Failed to parse a movie data.");
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
        System.out.println("Fetching movie with ID: " + movieId);
        DatabaseReference ref = getDatabase().getReference("movies").child(movieId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Data snapshot for movie exists.");
                    try {
                        Movies movie = dataSnapshot.getValue(Movies.class);
                        if (movie != null) {
                            System.out.println("Successfully retrieved movie: " + movie.getTitle());
                            callback.onSuccess(movie.toString());
                        } else {
                            System.out.println("Failed to parse movie data.");
                            callback.onFailure("Failed to parse movie data");
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing data: " + e.getMessage());
                        callback.onFailure("Error processing data: " + e.getMessage());
                    }
                } else {
                    System.out.println("No movie found with ID: " + movieId);
                    callback.onFailure("Movie not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database error: " + databaseError.getMessage());
                callback.onFailure(databaseError.getMessage());
            }
        });
    }


    public void searchMoviesByTitle(String title, DataCallback callback) {
        DatabaseReference ref = getDatabase().getReference("movies");
        Query query = ref.orderByChild("original_title").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder movies = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Movies movie = snapshot.getValue(Movies.class);
                        if (movie != null) {
                            movies.append(movie.toString()).append("\n");
                        } else {
                            System.out.println("Failed to parse movie data.");
                        }
                    }
                    callback.onSuccess(movies.toString());
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
        DatabaseReference ref = getDatabase().getReference("movie_genres");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder allGenres = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MovieGenre genre = snapshot.getValue(MovieGenre.class);
                        if (genre != null) {
                            genre.setUuid(snapshot.getKey());
                            allGenres.append(genre.toString()).append("\n");
                        } else {
                            System.out.println("Failed to parse genre data.");
                        }
                    }
                    callback.onSuccess(allGenres.toString());
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
        DatabaseReference ref = getDatabase().getReference("movie_genres").child(genreId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    MovieGenre genre = dataSnapshot.getValue(MovieGenre.class);
                    if (genre != null) {
                        genre.setUuid(genreId);  // Set the UUID for completeness
                        callback.onSuccess(genre.toString());
                    } else {
                        callback.onFailure("Failed to parse genre data");
                    }
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
        DatabaseReference ref = getDatabase().getReference("rented_movies");
        Query query = ref.orderByChild("user_id").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder rentedMovies = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        if (rentedMovie != null) {
                            rentedMovies.append(rentedMovie.toString()).append("\n");
                        } else {
                            System.out.println("Failed to parse rented movie data.");
                        }
                    }
                    callback.onSuccess(rentedMovies.toString());
                } else {
                    callback.onFailure("No rented movies found for user ID: " + userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public void getUserRentalByStatus(String userId, String status, DataCallback callback) {
        DatabaseReference ref = getDatabase().getReference("rented_movies");
        Query query = ref.orderByChild("user_id").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder filteredMovies = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        if (rentedMovie != null && rentedMovie.getStatus().equals(status)) {
                            filteredMovies.append(rentedMovie.toString()).append("\n");
                        }
                    }
                    if (filteredMovies.length() > 0) {
                        callback.onSuccess(filteredMovies.toString());
                    } else {
                        callback.onFailure("No rented movies found with status: " + status);
                    }
                } else {
                    callback.onFailure("No rented movies found for user ID: " + userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }
        public void isRentedBefore(String userId, String movieId, DataCallback callback) {
            DatabaseReference ref = getDatabase().getReference("rented_movies");
            Query query = ref.orderByChild("user_id").equalTo(userId);
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
                        callback.onSuccess(String.valueOf(isRented));
                    } else {
                        callback.onSuccess("false");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFailure(databaseError.getMessage());
                }
            });
        }

}


