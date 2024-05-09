package com.example.movieratingservice.validation;
//This is a file
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public static String validateFirstName(String firstName) {
        if (firstName.isEmpty()) {
            return "First Name cannot be empty.";
        } else if (!firstName.matches("^[A-Z][a-zA-Z]*")) {
            return "First Name should only contain letters.\nThe first letter must be capitalized";
        }
        return "";

    }

    public static String validateLastName(String lastName) {
        if (lastName.isEmpty()) {
            return "Last Name cannot be empty.";
        } else if (!lastName.matches("^[A-Z][a-zA-Z'-]*$")) {
            return "Last Name should only contain letters.\nThe first letter must be capitalized. The apostrophes or hyphens is allowed in the last name.";
        }
        return "";
    }

    public static String validateEmail(String email) {
        if (email.isEmpty()) {
            return "Email cannot be empty.";
        } else if (!email.matches("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,4}$")) {
            return "Email must contain a-z or 0-9.\nThe domain extension allows 2-4 letters.";
        }
        return "";
    }

    public static String validatePassword(String password) {
        if (password.isEmpty()) {
            return "Password cannot be empty.";
        } else if (!password.matches(".{8,}")) {
            return "Password must be 8 characters long or more.";
        }
        return "";
    }


}
