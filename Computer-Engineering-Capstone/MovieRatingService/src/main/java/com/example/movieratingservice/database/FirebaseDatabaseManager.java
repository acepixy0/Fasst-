package com.example.movieratingservice.database;

import com.example.movieratingservice.util.DataCallback;
import com.example.movieratingservice.model.MovieGenre;
import com.example.movieratingservice.model.Movies;
import com.example.movieratingservice.model.RentedMovie;
import com.example.movieratingservice.model.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseDatabaseManager {

    private static String userID;
    private List<Movies> allMovies;


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
        this.userID = uuid;
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
                            setUserID(snapshot.getKey());
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
                    allMovies = new ArrayList<Movies>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Movies movie = snapshot.getValue(Movies.class);
                        if (movie != null) {
                            allMovies.add(movie);
                        } else {
                            System.out.println("Failed to parse a movie data.");
                        }
                    }
                    callback.onSuccess("Movies Successfully Fetched");
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

    public void getRentedMovies(DataCallback callback) {
        DatabaseReference rentedMoviesRef = getDatabase().getReference("rented_movies");
        Query userRentedMoviesQuery = rentedMoviesRef.orderByChild("user_id").equalTo(this.userID);

        userRentedMoviesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    allMovies = new ArrayList<>();
                    AtomicInteger moviesCount = new AtomicInteger((int) dataSnapshot.getChildrenCount());
                    for (DataSnapshot rentedMovieSnapshot : dataSnapshot.getChildren()) {
                        String movieId = rentedMovieSnapshot.child("movie_id").getValue(String.class);
                        DatabaseReference movieRef = getDatabase().getReference("movies").child(movieId);
                        movieRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot movieSnapshot) {
                                Movies movie = movieSnapshot.getValue(Movies.class);
                                if (movie != null) {
                                    allMovies.add(movie);
                                } else {
                                    System.out.println("Failed to parse movie data for movie ID: " + movieId);
                                }
                                if (moviesCount.decrementAndGet() == 0) {
                                    callback.onSuccess("Rented Movies for User Successfully Fetched");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Database error: " + databaseError.getMessage());
                            }
                        });
                    }
                } else {
                    callback.onFailure("No rented movies found for user ID: " + userID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public List<Movies> getMovieList() {
        System.out.println(this.allMovies);
        return this.allMovies;
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
                        genre.setUuid(genreId);
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

    public void rentMovie(String movieId, DataCallback callback) {
        System.out.println("Movie ID:" + movieId);
        System.out.println("User ID: " + this.userID);
        DatabaseReference rentedMoviesRef = getDatabase().getReference("rented_movies");
        DatabaseReference moviesRef = getDatabase().getReference("movies").child(movieId);
        DatabaseReference userRef = getDatabase().getReference("users").child(this.userID);

        Query query = rentedMoviesRef.orderByChild("user_id").equalTo(this.userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean canRent = true;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RentedMovie rentedMovie = snapshot.getValue(RentedMovie.class);
                        if (rentedMovie != null && rentedMovie.getMovie_id().equals(movieId) && rentedMovie.getStatus().equals("Rented")) {
                            callback.onFailure("You already have this movie rented.");
                            canRent = false;
                            break;
                        }
                    }
                }

                if (canRent) {
                    moviesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot movieSnapshot) {
                            Movies movie = movieSnapshot.getValue(Movies.class);
                            if (movie != null && movie.getAvailable_copies() > 0) {
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot userSnapshot) {
                                        Users user = userSnapshot.getValue(Users.class);
                                        if (user != null && user.getBalance() >= movie.getPrice()) {

                                            double newBalance = user.getBalance() - movie.getPrice();
                                            userRef.child("balance").setValueAsync(newBalance);
                                            moviesRef.child("available_copies").setValueAsync(movie.getAvailable_copies() - 1);

                                            RentedMovie newRental = new RentedMovie();
                                            newRental.setMovie_id(movieId);
                                            newRental.setUser_id(userID);
                                            newRental.setRental_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                            newRental.setRented_price(String.valueOf(movie.getPrice()));
                                            newRental.setStatus("Rented");

                                            rentedMoviesRef.push().setValueAsync(newRental);

                                            callback.onSuccess("Movie rented successfully. New Balance: " + newBalance);
                                        } else {
                                            callback.onFailure("Insufficient balance to rent this movie.");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        callback.onFailure("Failed to get user data: " + databaseError.getMessage());
                                    }
                                });
                            } else {
                                callback.onFailure("This movie is currently not available for rent.");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callback.onFailure("Failed to get movie data: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure("Database error: " + databaseError.getMessage());
            }
        });
    }


}


