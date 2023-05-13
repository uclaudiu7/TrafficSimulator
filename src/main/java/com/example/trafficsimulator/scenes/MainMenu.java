package com.example.trafficsimulator.scenes;

import com.example.trafficsimulator.TrafficSimulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainMenu {

    private final Stage stage;
    private final Scene scene;

    public MainMenu(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TrafficSimulator.class.getResource("main-menu.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(Objects.requireNonNull(TrafficSimulator.class.getResource("main-menu.css")).toExternalForm());
        stage.setTitle("Traffic Simulator");
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
