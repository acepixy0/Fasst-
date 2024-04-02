package com.example.movieratingservice;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
        Parent root = loader.load();

        ImageView splashImageView = (ImageView) loader.getNamespace().get("splashImageView");
        ProgressBar progressBar = (ProgressBar) loader.getNamespace().get("progressBar");

        splashImageView.setImage(new Image(getClass().getResourceAsStream("movie image.jpg")));

        Task<Void> loadDataTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(50);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(loadDataTask.progressProperty());

        loadDataTask.setOnSucceeded(event -> {
            primaryStage.hide();
            Application mainApp = new Application();
            Stage mainStage = new Stage();
            try {
                mainApp.start(mainStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainStage.show();
        });

        new Thread(loadDataTask).start();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Splash Screen");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
