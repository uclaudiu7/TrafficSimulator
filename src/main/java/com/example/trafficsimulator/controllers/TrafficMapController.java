package com.example.trafficsimulator.controllers;

import com.example.trafficsimulator.models.DatabaseManager;
import com.example.trafficsimulator.models.Node;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HexFormat;
import java.util.Objects;
import java.util.Vector;

public class TrafficMapController {
    private String zone;
    private Vector<Node> nodes = new Vector<>();
    private Vector<Line> edges = new Vector<>();
    private Node node1;
    private Node node2;
    private int clickCount = 0;

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
    @FXML private Button loadButton;

    @FXML
    private void initialize() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        exitButton.setLayoutX(bounds.getWidth() - 30);
        fiveSpeedButton.setLayoutX(bounds.getWidth() - 30);
        twoSpeedButton.setLayoutX(fiveSpeedButton.getLayoutX() - 40);
        oneSpeedButton.setLayoutX(twoSpeedButton.getLayoutX() - 40);
        mapNodesListener();
    }

    public void exitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void pauseButtonAction() {
        System.out.println("Pause button pressed: " + pauseButton.isSelected());
        //TODO: Implement
//        DatabaseManager databaseManager = new DatabaseManager();
//        databaseManager.insertNodes(nodes, zone);
//        databaseManager.insertEdges(edges, zone);
    }

    public void loadButtonAction() {
        DatabaseManager databaseManager = new DatabaseManager();
        nodes = databaseManager.loadNodes(zone);
        for(Node node : nodes) {
            drawNode(node);
        }
        edges = databaseManager.loadEdges(zone);
        for(Line edge : edges) {
            edge.setStroke(Color.web("#b7b7b7"));
            centerAnchorPane.getChildren().add(edge);
        }
    }

    public void oneSpeedButtonAction() {
        //TODO: Implement
        //mapNodesListener();
    }

    public void twoSpeedButtonAction() {
        //TODO: Implement
        //mapEdgesListener();
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

    public void mapNodesListener(){
        centerAnchorPane.setOnMouseClicked(event -> {
            Node node = new Node(event.getX(), event.getY());
            nodes.add(node);
            System.out.println("Node added: " + node);
            drawNode(node);
        });
    }

    public void drawNode(Node node) {
        if(node == null)
            return;
        Circle circle = new Circle(node.getX(), node.getY(), 2);
        circle.setStroke(Color.web("#b7b7b7"));
        centerAnchorPane.getChildren().add(circle);
    }

    public void mapEdgesListener() {
        centerAnchorPane.setOnMouseClicked(event -> {
            clickCount++;
            Node temp = new Node(event.getX(), event.getY());
            Node closest = nodes.get(0);
            for(Node node : nodes) {
                if(node.distanceTo(temp) < closest.distanceTo(temp))
                    closest = node;
            }
            if(clickCount % 2 == 1) {
                node1 = closest;
            }
            else {
                node2 = closest;
                System.out.println("Edge added: " + node1 + " " + node2);
                edges.add(new Line(node1.getX(), node1.getY(), node2.getX(), node2.getY()));
                drawEdge(node1, node2);
            }
        });
    }

    public void drawEdge(Node A, Node B) {
        if(A == null || B == null)
            return;
        Line line = new Line(A.getX(), A.getY(), B.getX(), B.getY());
        line.setStroke(Color.web("#b7b7b7"));
        centerAnchorPane.getChildren().add(line);
    }

}
