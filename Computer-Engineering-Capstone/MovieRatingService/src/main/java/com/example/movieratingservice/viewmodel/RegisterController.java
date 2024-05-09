package com.example.movieratingservice.viewmodel;

import java.io.IOException;

import com.example.movieratingservice.model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField emailTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField lnameTF;
    @FXML
    private TextField fNameTF;
    @FXML
    private Button accountRegisterBtn;
    @FXML
    private Button splashBtn;

    private Alert alert;

    @FXML
    private void accountRegisterHandler(ActionEvent event) {
        String fName = fNameTF.getText();
        String lName = lnameTF.getText();
        String email = emailTF.getText();
        String password = passwordTF.getText();

        UserManager um = new UserManager();
        String registerMsg = um.registerUser( fName, lName, email, password, 10.0);

        if (!registerMsg.equals("User registered successfully")) {
            inputErrorAlert(registerMsg);
            return;
        }

        handleSuccessfulRegister(um);

    }

    private void handleSuccessfulRegister(UserManager um) {
        Users user = um.getUserDetails(um.userID());
        if (user == null) {
            System.out.println("Failed to fetch user details.");
            return;
        }

        System.out.println("User logged in successfully \n" + user.toString());
        switchToDashboard();
    }

    private void switchToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/DashboardScreen.fxml"));
            Parent dashboardScreen = loader.load();
            Stage stage = (Stage) accountRegisterBtn.getScene().getWindow();
            Scene scene = new Scene(dashboardScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard.");
        }
    }

    private void inputErrorAlert(String inputErrors) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(inputErrors.toString());
        alert.show();

    }

    @FXML
    private void switchToSplash(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/SplashScreen.fxml"));
            Parent loginScreen = loader.load();

            Stage stage = (Stage) splashBtn.getScene().getWindow();

            Scene scene = new Scene(loginScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
