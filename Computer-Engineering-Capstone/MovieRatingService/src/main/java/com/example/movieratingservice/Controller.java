package com.example.movieratingservice;
//This is a file
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    public void initialize() {

        // for testing firbase db purpose.. i will remove this later.
        FirebaseDatabaseManager db = new FirebaseDatabaseManager();
        db.getDatabase();

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}