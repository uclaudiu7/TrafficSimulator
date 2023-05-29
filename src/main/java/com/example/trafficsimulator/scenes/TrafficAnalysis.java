package com.example.trafficsimulator.scenes;

import com.example.trafficsimulator.TrafficSimulator;
import com.example.trafficsimulator.controllers.TrafficAnalysisController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TrafficAnalysis {
    private final Stage stage;
    private final Scene scene;
    private TrafficAnalysisController controller;
    private String zone;
    private int carsArrived;
    private long runningTime;
    private long waitingTime;


    public TrafficAnalysis(Stage stage, String zone, int carsArrived, long runningTime, long waitingTime) throws IOException {
        this.stage = stage;
        this.zone = zone;
        this.carsArrived = carsArrived;
        this.runningTime = runningTime;
        this.waitingTime = waitingTime;

        FXMLLoader fxmlLoader = new FXMLLoader(TrafficSimulator.class.getResource("traffic-analysis.fxml"));
        controller = fxmlLoader.getController();
        controller.setLabels(zone, carsArrived, runningTime, waitingTime);
        scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(Objects.requireNonNull(TrafficSimulator.class.getResource("main-menu.css")).toExternalForm());
        stage.setTitle("Traffic Analysis");
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
