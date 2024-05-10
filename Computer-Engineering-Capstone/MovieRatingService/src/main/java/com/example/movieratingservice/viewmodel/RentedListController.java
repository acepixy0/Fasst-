package com.example.movieratingservice.viewmodel;

import com.example.movieratingservice.model.Movies;
import com.example.movieratingservice.util.DataCallback;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentedListController extends BaseController {

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
    private GridPane movieContainer;
    @FXML
    private HBox cardLayout;

    private List<Movies> allMovies;


    public void initialize() throws IOException {
        movies();
    }

    private void movies() {
        MovieManager mm = new MovieManager();

        mm.getRentedMovies(new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Success: " + data);
                Platform.runLater(() -> {
                    updateUI(mm.getMovieList());
                });
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching rented movies: " + error);
            }
        });
    }

    private void updateUI(List<Movies> movies) {
        movieContainer.getChildren().clear();
        int column = 0;
        int row = 1;
        for (Movies movie : movies) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/movieratingservice/movie.fxml"));
                VBox movieBox = fxmlLoader.load();
                MovieController movieController = fxmlLoader.getController();
                movieController.setData(movie);

                if (column == 5) {
                    column = 0;
                    ++row;
                }

                movieContainer.add(movieBox, column++, row);
                GridPane.setMargin(movieBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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


}
