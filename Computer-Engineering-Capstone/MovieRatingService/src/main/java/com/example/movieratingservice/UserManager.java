package com.example.movieratingservice;

import java.util.concurrent.CompletableFuture;

public class UserManager {

    private FirebaseDatabaseManager db;
    public UserManager() {
        this.db = FirebaseDatabaseManager.getInstance();
    }

    public CompletableFuture<String> registerUser(String firstName, String lastName, String email, String password, double balance) {
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
        return db.registerUser(user);
    }

    public CompletableFuture<String> loginUser(String email, String password) {
        String msg;
        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validatePassword(password);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        return db.loginUser(email, password);
    }

    public CompletableFuture<String> updateUserDetails(String userId, String firstName, String lastName, String email) {
        String msg;
        msg = InputValidator.validateFirstName(firstName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateLastName(lastName);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        msg = InputValidator.validateEmail(email);
        if (!msg.isEmpty()) CompletableFuture.completedFuture(msg);

        return db.updateUserDetails(userId, firstName, lastName, email);
    }
    public CompletableFuture<String> updateUserPassword(String userId, String password, String newPassword) {
        String validationMessage = InputValidator.validatePassword(newPassword);
        if (!validationMessage.isEmpty()) return CompletableFuture.completedFuture(validationMessage);
        return db.updateUserPassword(userId, password, newPassword);
    }


    public String userID(){
        return db.getLastRegisteredUserId();
    }
}
