package com.example.trafficsimulator.scenes;

import com.example.trafficsimulator.TrafficSimulator;
import com.example.trafficsimulator.controllers.TrafficMapController;
import com.example.trafficsimulator.models.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TrafficMap {

    private final TrafficMapController trafficMapController;
    private final Stage stage;
    private final Scene scene;
    private final String zone;
    private final int cars;
    private final double intensity;
    private final double hazard;
    private Simulation simulation;

    public TrafficMap(Stage stage, String zone, int cars, double intensity, double hazard) throws IOException {
        this.stage = stage;
        this.cars = cars;
        this.intensity = intensity;
        this.hazard = hazard;
        this.zone = zone;

        FXMLLoader fxmlLoader = new FXMLLoader(TrafficSimulator.class.getResource("traffic-map.fxml"));
        scene = new Scene(fxmlLoader.load(), 1536, 864);
        scene.getStylesheets().add(Objects.requireNonNull(TrafficSimulator.class.getResource("traffic-map.css")).toExternalForm());
        stage.setTitle(zone + " Traffic Map");

        TrafficMapController controller = fxmlLoader.getController();
        controller.setZoneLabel("Zone: " + zone);
        controller.setCarsLabel("Cars: " + cars);
        String oneDecimalIntensity = String.format("%.1f", intensity);
        controller.setIntensityLabel("Intensity: " + oneDecimalIntensity + "%");
        String oneDecimalHazard = String.format("%.1f", hazard);
        controller.setHazardLabel("Hazard probability: " + oneDecimalHazard + "%");
        controller.setMap(zone);

        trafficMapController = fxmlLoader.getController();
        trafficMapController.setTrafficMap(this);
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    public TrafficMapController getTrafficMapController() {
        return trafficMapController;
    }

    public void beginSimulation() {
        simulation = new Simulation(zone, cars, intensity, hazard, this);
        simulation.start();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
