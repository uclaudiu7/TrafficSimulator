package com.example.trafficsimulator.controllers;

import com.example.trafficsimulator.scenes.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TrafficAnalysisController {

    @FXML private AnchorPane topAnchorPane;
    @FXML
    private void initialize() {
        topAnchorPane.setOnMousePressed(e ->topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));
    }

    public void setLabels(String zone, int carsArrived, long runningTime, long waitingTime) {
        System.out.println("Zone: " + zone);
        System.out.println("Cars arrived: " + carsArrived);
        System.out.println("Running time: " + runningTime);
        System.out.println("Waiting time: " + waitingTime);
    }

    public void startButtonAction() throws IOException {
        System.out.println("Start button pressed");
        MainMenu mainMenu = new MainMenu((Stage) topAnchorPane.getScene().getWindow());
        mainMenu.show();
    }

    public void exitButtonAction() {
        System.out.println("Exit button pressed");
        Stage stage = (Stage) topAnchorPane.getScene().getWindow();
        stage.close();
    }

}
