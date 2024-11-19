module com.example.lab3schronisko {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.lab3schronisko to javafx.fxml;
    opens com.example.lab3schronisko.controller to javafx.fxml;
    opens com.example.lab3schronisko.model to javafx.base;

    exports com.example.lab3schronisko;
    exports com.example.lab3schronisko.controller;
    exports com.example.lab3schronisko.model;
}
