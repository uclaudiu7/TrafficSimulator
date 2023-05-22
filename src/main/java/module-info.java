module com.example.trafficsimulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.trafficsimulator to javafx.fxml;
    exports com.example.trafficsimulator;
    exports com.example.trafficsimulator.controllers;
    opens com.example.trafficsimulator.controllers to javafx.fxml;
    exports com.example.trafficsimulator.scenes;
    opens com.example.trafficsimulator.scenes to javafx.fxml;
}