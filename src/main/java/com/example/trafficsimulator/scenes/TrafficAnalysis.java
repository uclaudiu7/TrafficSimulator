package com.example.trafficsimulator.scenes;

import com.example.trafficsimulator.TrafficSimulator;
import com.example.trafficsimulator.controllers.TrafficAnalysisController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TrafficAnalysis {
    private final Stage stage;
    private final Scene scene;

    public TrafficAnalysis(Stage stage, String zone, int carsArrived, double runningTime, double waitingTime) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(TrafficSimulator.class.getResource("traffic-analysis.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(Objects.requireNonNull(TrafficSimulator.class.getResource("main-menu.css")).toExternalForm());
        stage.setTitle("Traffic Analysis");

        TrafficAnalysisController controller = fxmlLoader.getController();
        controller.setZoneLabel(zone);
        controller.setCarsLabel(String.valueOf(carsArrived));
        controller.setRunningTimeLabel(String.format("%.3f", runningTime) + " seconds");
        controller.setWaitingTimeLabel(String.format("%.3f", waitingTime) + " seconds");

    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
