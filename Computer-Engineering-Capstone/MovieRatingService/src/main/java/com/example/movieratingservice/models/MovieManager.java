package com.example.movieratingservice.models;

import com.example.movieratingservice.firebase.DataCallback;
import com.example.movieratingservice.firebase.FirebaseDatabaseManager;

public class MovieManager {

    private FirebaseDatabaseManager db;


    public MovieManager() {
        this.db = FirebaseDatabaseManager.getInstance();
    }

    public void getAllMovies(DataCallback callback) {
        db.getAllMovies(callback);
    }


    public void getMovieById(String movieId, DataCallback callback) {
        db.getMovieById(movieId, callback);
    }

    public void searchMoviesByTitle(String title, DataCallback callback) {
        db.searchMoviesByTitle(title, callback);
    }

    public void getAllGenres(DataCallback callback) {
        db.getAllGenres(callback);
    }

    public void getGenreById(String genreId, DataCallback callback) {
        db.getGenreById(genreId, callback);
    }

    public void getRentedMoviesByUserId(String userId, DataCallback callback) {
        db.getRentedMoviesByUserId(userId, callback);
    }

    public void getUserRentalByStatus(String userId, String status, DataCallback callback) {
        db.getUserRentalByStatus(userId, status, callback);
    }

    public void isRentedBefore(String userId, String movieId, DataCallback callback) {
        db.isRentedBefore(userId, movieId, callback);
    }

}
