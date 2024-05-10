package com.example.movieratingservice.viewmodel;

import com.example.movieratingservice.model.Movies;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieController {

    @FXML
    private ImageView moviePosterImg;
    @FXML
    private Label movieTitleLbl;
    @FXML
    private Label movieYearLbl;

    private Movies movie;

    public void setData(Movies movie){
        this.movie = movie;
        String baseUrl = "https://image.tmdb.org/t/p/w500";

        Image image;
        if (movie.getPoster_path() != null && !movie.getPoster_path().isEmpty()) {
            String fullImageUrl = baseUrl + movie.getPoster_path();
            image = new Image(fullImageUrl, true);
        } else {
            image = new Image(getClass().getResourceAsStream("/com/example/movieratingservice/noimage.jpg"));
        }

        moviePosterImg.setImage(image);
        movieTitleLbl.setText(movie.getTitle());

        if (movie.getRelease_date() != null && !movie.getRelease_date().isEmpty()) {
            String year = movie.getRelease_date().split("-")[0]; // Splits the date and takes the first part (year)
            movieYearLbl.setText(year);
        } else {
            movieYearLbl.setText("");
        }
    }

    @FXML
    private void onPosterClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/MovieRentScreen.fxml"));
            Parent rentScreen = loader.load();

            MovieRentController rentController = loader.getController();
            rentController.setData(this.movie);

            Stage stage = (Stage) moviePosterImg.getScene().getWindow();
            Scene scene = new Scene(rentScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

