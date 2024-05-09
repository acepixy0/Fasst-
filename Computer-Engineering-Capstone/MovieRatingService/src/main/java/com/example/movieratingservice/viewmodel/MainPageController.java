package com.example.movieratingservice.viewmodel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    private Button loginScreenBTN;
    @FXML
    private Button registerScreenBTN;


    @FXML
    private void switchToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/RegisterScreen.fxml"));
            Parent registerScreen = loader.load();

            Stage stage = (Stage) registerScreenBTN.getScene().getWindow();

            Scene scene = new Scene(registerScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/LoginScreen.fxml"));
            Parent loginScreen = loader.load();

            Stage stage = (Stage) loginScreenBTN.getScene().getWindow();

            Scene scene = new Scene(loginScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
