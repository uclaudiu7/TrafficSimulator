package com.example.trafficsimulator.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class TrafficMapController {
    private String zone;

    @FXML private AnchorPane topAnchorPane;
    @FXML private AnchorPane centerAnchorPane;
    @FXML private AnchorPane bottomAnchorPane;

    @FXML private Label zoneLabel;
    @FXML private Label carsLabel;
    @FXML private Label intensityLabel;
    @FXML private Label hazardLabel;

    @FXML private ToggleButton pauseButton;
    @FXML private Button oneSpeedButton;
    @FXML private Button twoSpeedButton;
    @FXML private Button fiveSpeedButton;
    @FXML private Button exitButton;

    @FXML
    private void initialize() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        exitButton.setLayoutX(bounds.getWidth() - 30);
        fiveSpeedButton.setLayoutX(bounds.getWidth() - 30);
        twoSpeedButton.setLayoutX(fiveSpeedButton.getLayoutX() - 40);
        oneSpeedButton.setLayoutX(twoSpeedButton.getLayoutX() - 40);
    }

    public void exitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void pauseButtonAction() {
        System.out.println("Pause button pressed: " + pauseButton.isSelected());
        //TODO: Implement
    }

    public void oneSpeedButtonAction() {
        //TODO: Implement
    }

    public void twoSpeedButtonAction() {
        //TODO: Implement
    }

    public void fiveSpeedButtonAction() {
        //TODO: Implement
    }

    public void setZoneLabel(String text) {
        zoneLabel.setText(text);
    }

    public void setCarsLabel(String text) {
        carsLabel.setText(text);
    }

    public void setIntensityLabel(String text) {
        intensityLabel.setText(text);
    }

    public void setHazardLabel(String text) {
        hazardLabel.setText(text);
    }

    public void setMap(String zone){
        this.zone = zone;
        Image background;
        if(zone.equals("UAIC Corp C"))
            background = new Image(Objects.requireNonNull(getClass().getResource("/images/uaic.png")).toString());
        else if(zone.equals("Pasarela Octav Bancila"))
            background = new Image(Objects.requireNonNull(getClass().getResource("/images/pasarela.png")).toString());
        else
            background = new Image(Objects.requireNonNull(getClass().getResource("/images/eminescu.png")).toString());
        centerAnchorPane.setStyle("-fx-background-image: url('" + background.getUrl() + "'); ");
    }

}
