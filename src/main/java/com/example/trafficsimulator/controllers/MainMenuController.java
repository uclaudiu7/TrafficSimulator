package com.example.trafficsimulator.controllers;

import com.example.trafficsimulator.scenes.TrafficMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
        @FXML
        private AnchorPane topAnchorPane;

        @FXML
        private ChoiceBox<String> zoneChoiceBox;

        @FXML
        private Spinner<Integer> carsSpinner;

        @FXML
        private Slider intensitySlider;

        @FXML
        private Button startButton;

        @FXML
        private Button exitButton;

        @FXML
        private void initialize() {
                zoneChoiceBox.getItems().addAll("UAIC Corp C", "Pasarela Octav Bancila", "Piata Mihai Eminescu");
                zoneChoiceBox.getSelectionModel().selectFirst();

                // Set default value for carsSpinner, and set a min and max value
                carsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 1));
                intensitySlider.setMin(1);
                intensitySlider.setMax(5);
                intensitySlider.setValue(1);

                // Set default value for intensitySlider, and add a listener to update the value on drag
                intensitySlider.setOnMouseDragged(e -> intensitySlider.setValue((int) intensitySlider.getValue()));

                // Set up the custom window move functionality
                topAnchorPane.setOnMousePressed(e -> topAnchorPane.setOnMouseDragged(e2 -> {
                        Stage stage = (Stage) topAnchorPane.getScene().getWindow();
                        stage.setX(e2.getScreenX() - e.getSceneX());
                        stage.setY(e2.getScreenY() - e.getSceneY());
                }));

        }

        public void exitButtonAction() {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
                System.exit(0);
        }

        public void startButtonAction() throws IOException {
                Stage stage = (Stage) startButton.getScene().getWindow();
                stage.close();
                TrafficMap trafficMap = new TrafficMap(stage, zoneChoiceBox.getValue(), carsSpinner.getValue(), intensitySlider.getValue());
                trafficMap.show();
                trafficMap.beginSimulation();
        }

}
