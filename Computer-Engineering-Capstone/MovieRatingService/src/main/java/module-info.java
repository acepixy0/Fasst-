module com.example.movieratingservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    opens com.example.movieratingservice to javafx.fxml, firebase.admin;
    exports com.example.movieratingservice;
    exports com.example.movieratingservice.viewmodel;
    opens com.example.movieratingservice.viewmodel to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.model;
    opens com.example.movieratingservice.model to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.database;
    opens com.example.movieratingservice.database to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.util;
    opens com.example.movieratingservice.util to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.validation;
    opens com.example.movieratingservice.validation to firebase.admin, javafx.fxml;
}