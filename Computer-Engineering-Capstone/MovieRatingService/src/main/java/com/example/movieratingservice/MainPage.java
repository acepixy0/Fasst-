package com.example.movieratingservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
public class MainPage {
    @FXML
    private VBox root;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private ListView<String> movieListView;
    private ObservableList<String> movies = FXCollections.observableArrayList(
            "Movie 1", "Movie 2", "Movie 3", "Movie 4", "Movie 5",
            "Movie 6", "Movie 7", "Movie 8", "Movie 9", "Movie 10"
    );

    @FXML
    public void initialize() {
        searchField.setPromptText("Search movies...");
        filterComboBox.getItems().addAll("Action", "Comedy", "Drama", "Sci-Fi", "Thriller");
        filterComboBox.setPromptText("Filter by Genre");

        movieListView.setPrefSize(400, 300);
        movieListView.setPlaceholder(new Label("No movies found"));
        movieListView.setItems(movies);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredItems(newValue, null));
        filterComboBox.setOnAction(event -> updateFilteredItems(searchField.getText(), filterComboBox.getSelectionModel().getSelectedItem()));
    }
    private void updateFilteredItems(String searchText, String genre) {
        ObservableList<String> filteredMovies = movies.filtered(movie ->
                (searchText == null || searchText.isEmpty() || movie.toLowerCase().contains(searchText.toLowerCase())) &&
                        (genre == null || movie.toLowerCase().contains(genre.toLowerCase()))
        );
        movieListView.setItems(filteredMovies);
    }
}
