package com.example.movieratingservice.viewmodel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController extends BaseController {

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


    public void initialize() {

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