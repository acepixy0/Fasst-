package com.example.movieratingservice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    public void initialize() {


        //userRegistration();
        //userLogin();
        //updateUserDetails();
        //updateUserPassword();



    }

    public void userRegistration(){
        UserManager um = new UserManager();
        String msg = um.registerUser("Jo", "Jo", "jom@j.com", "Qwerty123@", 100.0);
        System.out.println(msg);
    }

    public void userLogin(){
        UserManager um = new UserManager();
        String msg = um.loginUser("jj@jj.com", "Pwerty12345$");
        System.out.println(msg);
        System.out.println(um.userID());
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

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}