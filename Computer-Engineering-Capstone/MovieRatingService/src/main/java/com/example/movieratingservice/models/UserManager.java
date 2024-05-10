package com.example.movieratingservice.models;

import com.example.movieratingservice.firebase.FirebaseDatabaseManager;
import com.example.movieratingservice.InputValidator;
import com.example.movieratingservice.Users;

import java.util.concurrent.CompletableFuture;

public class UserManager {

    private FirebaseDatabaseManager db;
    public UserManager() {
        this.db = FirebaseDatabaseManager.getInstance();
    }

    public void registerUser(String firstName, String lastName, String email, String password, double balance) {
        String msg;
        msg = InputValidator.validateFirstName(firstName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateLastName(lastName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validatePassword(password);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        Users user = new Users(firstName, lastName, email, password, balance);
        db.registerUser(user);
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
    public void updateUserDetails(String userId, String firstName, String lastName, String email) {
        String msg;
        msg = InputValidator.validateFirstName(firstName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateLastName(lastName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        db.updateUserDetails(userId, firstName, lastName, email);
    }
    public void updateUserPassword(String userId, String password, String newPassword) {
        String validationMessage = InputValidator.validatePassword(newPassword);
        db.updateUserPassword(userId, password, newPassword);
    }


    public String userID(){
        return db.getLastRegisteredUserId();
    }
}
