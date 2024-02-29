module com.example.movieratingservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.movieratingservice to javafx.fxml;
    exports com.example.movieratingservice;
}