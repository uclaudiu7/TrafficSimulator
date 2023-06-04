package com.example.trafficsimulator.controllers;

import com.example.trafficsimulator.scenes.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TrafficAnalysisController {

    @FXML private AnchorPane topAnchorPane;
    @FXML private Label zoneLabel;
    @FXML private Label carsLabel;
    @FXML private Label waitingTimeLabel;
    @FXML private Label runningTimeLabel;


    @FXML
    private void initialize() {
        topAnchorPane.setOnMousePressed(e ->topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));
    }

    public void setZoneLabel(String zone) {
        zoneLabel.setText(zone);
    }

    public void setCarsLabel(String cars) {
        carsLabel.setText(cars);
    }

    public void setWaitingTimeLabel(String waitingTime) {
        waitingTimeLabel.setText(waitingTime);
    }

    public void setRunningTimeLabel(String runningTime) {
        runningTimeLabel.setText(runningTime);
    }

    public void startButtonAction() throws IOException {
        MainMenu mainMenu = new MainMenu((Stage) topAnchorPane.getScene().getWindow());
        mainMenu.show();
    }

    public void exitButtonAction() {
        Stage stage = (Stage) topAnchorPane.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

}
