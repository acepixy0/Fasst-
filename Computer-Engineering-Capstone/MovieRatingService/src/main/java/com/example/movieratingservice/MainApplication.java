package com.example.movieratingservice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainApplication extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showSplashScreen();
    }
    private void showSplashScreen() throws Exception {
        // Load splash screen view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/SplashScreen.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Splash Screen");
        primaryStage.show();

        SplashScreen controller = loader.getController();
        controller.setMainApplication(this);
    }
    public void showMainStage() {
        try {
            // Load the main application stage after splash screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movieratingservice/main_page.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main Application");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}