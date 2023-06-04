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
    private final double speed;
    private Simulation simulation;

    public TrafficMap(Stage stage, String zone, int cars, double speed) throws IOException {
        this.stage = stage;
        this.cars = cars;
        this.speed = speed;
        this.zone = zone;

        FXMLLoader fxmlLoader = new FXMLLoader(TrafficSimulator.class.getResource("traffic-map.fxml"));
        scene = new Scene(fxmlLoader.load(), 1536, 864);
        scene.getStylesheets().add(Objects.requireNonNull(TrafficSimulator.class.getResource("traffic-map.css")).toExternalForm());
        stage.setTitle(zone + " Traffic Map");

        TrafficMapController controller = fxmlLoader.getController();
        controller.setZoneLabel("Zone: " + zone);
        controller.setCarsLabel("Cars: " + cars);
        controller.setIntensityLabel("Speed: " + (int)speed + "x");
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
        simulation = new Simulation(zone, cars, speed, this);
        simulation.start();
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public double getSpeed() { return speed; }

}
