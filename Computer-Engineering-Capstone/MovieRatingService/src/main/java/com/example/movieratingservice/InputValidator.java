package com.example.movieratingservice;
//This is a file
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    //Validate Email
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //Validate password

    private static boolean isValidPassword(String password) {
        // Password should contain at least 8 characters, including at least one uppercase letter, one lowercase letter, and one digit.
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
