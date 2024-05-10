package com.example.movieratingservice.viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {

    protected void navigateTo(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHome(ActionEvent event) throws IOException {
        navigateTo("/com/example/movieratingservice/DashboardScreen.fxml", event);
    }

    public void switchToAllMovies(ActionEvent event) throws IOException {
        navigateTo("/com/example/movieratingservice/MovieScreen.fxml", event);
    }

    public void switchToMyRentals(ActionEvent event) throws IOException {
        navigateTo("/com/example/movieratingservice/RentedMoviesScreen.fxml", event);
    }
}
