package com.example.movieratingservice;
//This is a file 2.0
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException, Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public class SplashScreen extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("splash_screen.fxml"));
            Parent root = loader.load();

            // Get references to the splash image view and progress bar
            ImageView splashImageView = (ImageView) loader.getNamespace().get("splashImageView");
            ProgressBar progressBar = (ProgressBar) loader.getNamespace().get("progressBar");

            // Set the image for the splash screen programmatically
            splashImageView.setImage(new Image(getClass().getResourceAsStream("splash_image.jpg")));

            // Create a Task to simulate loading data
            Task<Void> loadDataTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Simulate loading data
                    for (int i = 0; i <= 100; i++) {
                        updateProgress(i, 100);
                        Thread.sleep(50);
                    }
                    return null;
                }
            };

            // Bind the progress of the task to the progress bar
            progressBar.progressProperty().bind(loadDataTask.progressProperty());

            // When the task finishes, close the splash screen and show the main application window
            loadDataTask.setOnSucceeded(event -> {
                primaryStage.hide();
                // You can replace MainApplication with your main application class
                Application mainApp = new Application();
                Stage mainStage = new Stage();
                try {
                    mainApp.start(mainStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mainStage.show();
            });

            // Start the task
            new Thread(loadDataTask).start();

            // Set the scene and show the stage
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
    public static void main(String[] args) {
        launch();
    }

}