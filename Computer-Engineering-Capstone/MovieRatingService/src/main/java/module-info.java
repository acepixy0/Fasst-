module com.example.movieratingservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    opens com.example.movieratingservice to javafx.fxml;
    exports com.example.movieratingservice;
}