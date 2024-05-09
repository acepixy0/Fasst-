package com.example.movieratingservice.viewmodel;
import com.example.movieratingservice.util.DataCallback;
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

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private Button accountLoginBtn;
    @FXML
    private Button splashBtn;

    private Alert alert;
    private String userID;


    public void initialize() {

        /* Backend Functions */

        /* Account management */
        //userRegistration();
        //userLogin(); -- done
        //updateUserDetails();
        //updateUserPassword();

        /* Movies */
        //getAllMovies();
        //getMovieByID();
        //searchMovieByTitle();

        /* Movie Genres */
        //getAllGenres();
        //getGenreByID();

        /* Manage Rent Movie */
        //rentedMovieOfUser();
        //rentedMovieStatus();
        //isRentedBefore();


    }

    @FXML
    private void accountLoginHandler(ActionEvent event) {
        UserManager um = new UserManager();
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String loginMsg = um.loginUser(email, password);

        if (!loginMsg.equals("User logged in successfully")) {
            inputErrorAlert(loginMsg);
            return;
        }

        handleSuccessfulLogin(um);
    }

    private void handleSuccessfulLogin(UserManager um) {
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
            Stage stage = (Stage) accountLoginBtn.getScene().getWindow();
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



    public void isRentedBefore(){
        MovieManager mm = new MovieManager();
        String userId = "5c24410e-5bbe-4126-a8e7-29b2f0115cd4";
        String movieId = "ea8257de-8c2f-4233-978a-529f813ac921";
        mm.isRentedBefore(userId, movieId, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                if (data.equals("true")) {
                    System.out.println("The movie has been rented before by this user.");
                } else {
                    System.out.println("The movie has not been rented before by this user.");
                }
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error checking rental status: " + error);
            }
        });

    }

    public void rentedMovieStatus (){
        MovieManager mm = new MovieManager();
        String userId = "5c24410e-5bbe-4126-a8e7-29b2f0115cd4";
        String status = "rented";
        mm.getUserRentalByStatus(userId, status, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Filtered Rented Movies:\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching filtered rented movies: " + error);
            }
        });

    }

    public void rentedMovieOfUser(){
        MovieManager mm = new MovieManager();
        String userId = "5c24410e-5bbe-4126-a8e7-29b2f0115cd4";  // Example User ID
        mm.getRentedMoviesByUserId(userId, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Rented Movies for User ID " + userId + ":\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching rented movies: " + error);
            }
        });

    }
    public void searchMovieByTitle(){
        MovieManager mm = new MovieManager();
        String searchTitle = "Land of Bad";
        mm.searchMoviesByTitle(searchTitle, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Found Movies:\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error searching for movies: " + error);
            }
        });
    }

    public void getGenreByID(){
        MovieManager mm = new MovieManager();
        String genreUUID = "16ff2f7d-095f-4eeb-ad97-60e72a75c54f";
        mm.getGenreById(genreUUID, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Genre Data:\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching genre: " + error);
            }
        });

    }

    public void getAllGenres(){
        MovieManager mm = new MovieManager();
        mm.getAllGenres(new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("All Genres:\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching all genres: " + error);
            }
        });

    }

    public void getAllMovies(){
        MovieManager mm = new MovieManager();
        mm.getAllMovies(new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("All Movies:\n" + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching all movies: " + error);
            }
        });

    }
    public void getMovieByID(){
        MovieManager mm = new MovieManager();
        String movieUUID = "ec2ca79c-ae30-42bd-8657-6bbf0c9c2967";
        mm.getMovieById(movieUUID, new DataCallback() {
            @Override
            public void onSuccess(String data) {
                System.out.println("Movie Data: " + data);
            }

            @Override
            public void onFailure(String error) {
                System.out.println("Error fetching movie: " + error);
            }
        });

        System.out.println("done");
    }


    public void updateUserDetails() {
        UserManager um = new UserManager();
        String msg = um.updateUserDetails("6243e742-c96f-4d71-bd0c-8869a4708f8b", "Jjrr", "Jjjj", "jj@jj.com");
        System.out.println(msg);
    }

    public void updateUserPassword() {
        UserManager um = new UserManager();
        String msg = um.updateUserPassword("6243e742-c96f-4d71-bd0c-8869a4708f8b", "Pwerty12345$", "Qwerty123@");
        System.out.println(msg);
    }
}
