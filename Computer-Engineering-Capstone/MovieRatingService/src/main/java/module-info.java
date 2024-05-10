module com.example.movieratingservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;

    opens com.example.movieratingservice to javafx.fxml, firebase.admin;
    exports com.example.movieratingservice;
    exports com.example.movieratingservice.controllers;
    opens com.example.movieratingservice.controllers to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.models;
    opens com.example.movieratingservice.models to firebase.admin, javafx.fxml;
    exports com.example.movieratingservice.firebase;
    opens com.example.movieratingservice.firebase to firebase.admin, javafx.fxml;
}