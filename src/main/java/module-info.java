module com.example.dictonary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dictonary to javafx.fxml;
    exports com.example.dictonary;
}