package com.example.movieratingservice.viewmodel;

import com.example.movieratingservice.database.FirebaseDatabaseManager;
import com.example.movieratingservice.model.Users;
import com.example.movieratingservice.validation.InputValidator;

public class UserManager {

    private FirebaseDatabaseManager db;
    public UserManager() {

        this.db = new FirebaseDatabaseManager();
    }

    public String registerUser(String firstName, String lastName, String email, String password, double balance) {
        String msg;
        msg = InputValidator.validateFirstName(firstName);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validateLastName(lastName);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validatePassword(password);
        if (!msg.isEmpty()) return msg;

        Users user = new Users(firstName, lastName, email, password, balance);
        return db.registerUser(user);
    }

    public String loginUser(String email, String password) {
        String msg;
        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validatePassword(password);
        if (!msg.isEmpty()) return msg;

        return db.loginUser(email, password);
    }

    public Users getUserDetails(String userId) {
        return db.getUserDetails(userId);
    }


    public String updateUserDetails(String userId, String firstName, String lastName, String email) {
        String msg;
        msg = InputValidator.validateFirstName(firstName);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validateLastName(lastName);
        if (!msg.isEmpty()) return msg;

        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) return msg;

        return db.updateUserDetails(userId, firstName, lastName, email);
    }
    public String updateUserPassword(String userId, String password, String newPassword) {
        String validationMessage = InputValidator.validatePassword(newPassword);
        if (!validationMessage.isEmpty()) return validationMessage;
        return db.updateUserPassword(userId, password, newPassword);
    }

    public String userID(){
        return db.getUserID();
    }



}
