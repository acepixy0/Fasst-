package com.example.movieratingservice.viewmodel;

import com.example.movieratingservice.database.FirebaseDatabaseManager;
import com.example.movieratingservice.model.Movies;
import com.example.movieratingservice.util.DataCallback;

import java.util.List;

public class MovieManager {

    private FirebaseDatabaseManager db;

    public void rentMovie(String movieId, DataCallback callback){
        db.rentMovie(movieId, callback);
    }

    public MovieManager() {
        this.db = new FirebaseDatabaseManager();
    }

    public void getAllMovies(DataCallback callback) {

        db.getAllMovies(callback);
    }

    public void getRentedMovies(DataCallback callback) {

        db.getRentedMovies(callback);
    }


    public List<Movies> getMovieList(){
        return db.getMovieList();
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
