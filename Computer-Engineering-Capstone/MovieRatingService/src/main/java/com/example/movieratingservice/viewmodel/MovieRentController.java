package com.example.movieratingservice.viewmodel;

import com.example.movieratingservice.model.Movies;
import com.example.movieratingservice.util.DataCallback;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;


public class MovieRentController extends BaseController  {

    @FXML
    private Button homeBtn;
    @FXML
    private Button allMoviesBtn;
    @FXML
    private Button myRentalBtn;
    @FXML
    private Button paymentHistoryBtn;
    @FXML
    private Button accountSettingsBtn;
    @FXML
    private ImageView moviePosterImg;
    @FXML
    private Text movieTitleTxt;
    @FXML
    private Text movieReleaseDateTxt;
    @FXML
    private Button rentBtn;
    @FXML
    private Button goBackBtn;
    @FXML
    private Text descriptionTxt;
    @FXML
    private Text tagLineTxt;
    @FXML
    private Text moviePriceTxt;
    @FXML
    private Text movieGenreTxt;
    @FXML
    private Text certificationTxt;
    @FXML
    private Text runtimeTxt;
    @FXML
    private Text voteCountTxt;
    @FXML
    private Text voteAverageTxt;

    private String movieID;

    public void setDescriptionText(String text) {
        String[] words = text.split("\\s+");
        StringBuilder formattedText = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            formattedText.append(word).append(" ");
            wordCount++;
            if (wordCount >= 25) {
                formattedText.append("\n");
                wordCount = 0;
            }
        }

        descriptionTxt.setText(formattedText.toString().trim());
    }

    public void setData(Movies movie) {

        String baseUrl = "https://image.tmdb.org/t/p/w500";

        Image image;
        if (movie.getPoster_path() != null && !movie.getPoster_path().isEmpty()) {
            String fullImageUrl = baseUrl + movie.getPoster_path();
            image = new Image(fullImageUrl, true);
        } else {
            image = new Image(getClass().getResourceAsStream("/com/example/movieratingservice/noimage.jpg"));
        }
        this.movieID = movie.getId();
        moviePosterImg.setImage(image);
        movieTitleTxt.setText(defaultValue(movie.getTitle()));
        movieReleaseDateTxt.setText(defaultValue(movie.getRelease_date()));
        setDescriptionText(defaultValue(movie.getOverview()));
        moviePriceTxt.setText(defaultValue(movie.getPrice()));
        certificationTxt.setText(defaultValue(movie.getUS_certification()));
        runtimeTxt.setText(defaultValue(formatTime(movie.getRuntime())));
        voteCountTxt.setText(defaultValue(movie.getVote_count()));
        voteAverageTxt.setText(defaultValue(movie.getVote_average()));

    }


   private String formatTime(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d hr %02d min", hours, minutes);
    }


    @FXML
    private void handleHomeButtonAction(ActionEvent event) throws IOException {
        switchToHome(event);
    }

    @FXML
    private void handleAllMoviesButtonAction(ActionEvent event) throws IOException {
        switchToAllMovies(event);
    }

    @FXML
    private void handleMyRentalButtonAction(ActionEvent event) throws IOException {
        switchToMyRentals(event);
    }

    @FXML
    private void switchToPaymenthistory(ActionEvent event) {
    }

    @FXML
    private void switchToAccountSettings(ActionEvent event) {
    }

    @FXML
    private void rentAMovie(ActionEvent event) {
        MovieManager mm = new MovieManager();

        mm.rentMovie(this.movieID, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println(data);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText(data);
                    alert.showAndWait();
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println(error);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(error);
                    alert.showAndWait();
                });
            }
        });
    }



    private String defaultValue(String value) {
        return (value == null || value.isEmpty()) ? "N/A" : value;
    }

    private String defaultValue(Double value) {
        return (value == null || value == 0) ? "N/A" : String.format("%.2f", value);
    }

    private String defaultValue(Integer value) {
        return (value == null || value == 0) ? "N/A" : Integer.toString(value);
    }

}
