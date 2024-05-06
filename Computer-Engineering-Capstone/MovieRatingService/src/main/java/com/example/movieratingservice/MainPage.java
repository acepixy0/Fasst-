package com.example.movieratingservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
public class MainPage extends Application {
    private ObservableList<String> movies = FXCollections.observableArrayList(
            "Movie 1", "Movie 2", "Movie 3", "Movie 4", "Movie 5",
            "Movie 6", "Movie 7", "Movie 8", "Movie 9", "Movie 10"
    );

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Rental App");

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search movies...");

        // Filter menu
        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Action", "Comedy", "Drama", "Sci-Fi", "Thriller");
        filterComboBox.setPromptText("Filter by Genre");

        // Movie list
        ListView<String> movieListView = new ListView<>(movies);
        movieListView.setPrefSize(400, 300);
        movieListView.setPlaceholder(new Label("No movies found"));

        // Layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(searchField, filterComboBox, movieListView);

        // Apply search filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                movieListView.setItems(movies);
            } else {
                ObservableList<String> filteredMovies = movies.filtered(movie ->
                        movie.toLowerCase().contains(newValue.toLowerCase()));
                movieListView.setItems(filteredMovies);
            }
        });

        // Apply filter selection
        filterComboBox.setOnAction(event -> {
            String selectedGenre = filterComboBox.getSelectionModel().getSelectedItem();
            if (selectedGenre != null) {
                ObservableList<String> filteredMovies = movies.filtered(movie ->
                        movie.toLowerCase().contains(selectedGenre.toLowerCase()));
                movieListView.setItems(filteredMovies);
            }
        });

        // Set up the scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
