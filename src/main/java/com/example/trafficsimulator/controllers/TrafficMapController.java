package com.example.trafficsimulator.controllers;

import com.example.trafficsimulator.models.*;
import com.example.trafficsimulator.scenes.TrafficAnalysis;
import com.example.trafficsimulator.scenes.TrafficMap;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class TrafficMapController {
    private String zone;
    private Vector<Node> nodes = new Vector<>();
    private Vector<Line> edges = new Vector<>();
    private Node node1;
    private Node node2;
    private int clickCount = 0;
    private TrafficMap trafficMap;

    @FXML private AnchorPane topAnchorPane;
    @FXML private AnchorPane centerAnchorPane;

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
        exitButton.setLayoutX(1536 - 30);
        fiveSpeedButton.setLayoutX(1536 - 30);
        twoSpeedButton.setLayoutX(fiveSpeedButton.getLayoutX() - 40);
        oneSpeedButton.setLayoutX(twoSpeedButton.getLayoutX() - 40);
        topAnchorPane.setOnMousePressed(e ->topAnchorPane.setOnMouseDragged(e2 -> {
            Stage stage = (Stage) topAnchorPane.getScene().getWindow();
            stage.setX(e2.getScreenX() - e.getSceneX());
            stage.setY(e2.getScreenY() - e.getSceneY());
        }));
    }

    public void exitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void stopButtonAction() throws IOException {
        List<Car> cars = trafficMap.getSimulation().getCars();
        double averageRunningTime = 0;
        double averageWaitingTime = 0;
        int carsArrived = 0;

        for(Car car : cars) {
            car.setRunning(false);
            car.interrupt();
        }

        for(Car car : cars) {
            if(car.isArrived()) {
                carsArrived++;
                averageRunningTime += car.getRunningTime();
                averageWaitingTime += car.getWaitingTime();
            }
        }

        averageRunningTime = averageRunningTime/(carsArrived* 1000);
        averageWaitingTime = averageWaitingTime/(carsArrived* 1000);

        System.out.println("Simulation stopped!");
        for(Car car : cars){
            System.out.println(car.getName() + " arrived in: " + car.getRunningTime()/1000 + " seconds, total wait: " + car.getWaitingTime()/1000 + " seconds");
        }

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

        TrafficAnalysis trafficAnalysis = new TrafficAnalysis(stage, zone, carsArrived, averageRunningTime, averageWaitingTime);
        trafficAnalysis.show();

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
        //closestNodeListener();
        mapNodesListener();
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

    public void closestNodeListener(){
        centerAnchorPane.setOnMouseClicked(event -> {
            Node temp = new Node(event.getX(), event.getY());
            Node closest = nodes.get(0);
            for(Node node : nodes) {
                if(node.distanceTo(temp) < closest.distanceTo(temp))
                    closest = node;
            }
            System.out.println("Closest node: " + closest);
        });
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

    public void drawCar(Node node, String color, String nodeType){
        Circle circle = new Circle(node.getX(), node.getY(), 5);
        if(nodeType.equals("start"))
            circle.setStroke(Color.web("#00ff00"));
        else if(nodeType.equals("end"))
            circle.setStroke(Color.web("#ff0000"));
        else
            circle.setStroke(Color.web(color));

        circle.fillProperty().setValue(Color.web(color));
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

    public List<Node> getNodes() {
        DatabaseManager databaseManager = new DatabaseManager();
        nodes = databaseManager.loadNodes(zone);

        List<Node> nodesList = new ArrayList<>();
        for(Node node : nodes) {
            nodesList.add(new Node(node.getX(), node.getY()));
        }
        return nodesList;
    }

    public List<Edge> getEdges() {
        DatabaseManager databaseManager = new DatabaseManager();
        edges = databaseManager.loadEdges(zone);
        List<Edge> edgesList = new ArrayList<>();
        for(Line edge : edges) {
            edgesList.add(new Edge(new Node(edge.getStartX(), edge.getStartY()), new Node(edge.getEndX(), edge.getEndY())));
        }
        return edgesList;
    }

    public void setTrafficMap(TrafficMap trafficMap) {
        this.trafficMap = trafficMap;
    }
    private Map<Car, ImageView> carImageViewMap = new HashMap<>();
    public void moveCar(Car car, List<Node> path) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                CountDownLatch latch = new CountDownLatch(path.size());
                long startTime = System.currentTimeMillis();

                Platform.runLater(() -> {
                    ImageView carImageView = car.getCarImageView();
                    double carSize = 16;

                    carImageView.setFitWidth(carSize);
                    carImageView.setFitHeight(carSize);

                    double carX = car.getStart().getX() - carSize / 2; // Calculate the X coordinate for the car image
                    double carY = car.getStart().getY() - carSize / 2; // Calculate the Y coordinate for the car image

                    carImageView.setLayoutX(carX);
                    carImageView.setLayoutY(carY);

                    centerAnchorPane.getChildren().add(carImageView);

                    carImageViewMap.put(car, carImageView);
                    latch.countDown();
                });

                for (int i = 1; i < path.size(); i++) {
                    Node previousNode = path.get(i - 1);
                    Node currentNode = path.get(i);
                    Thread.sleep(200);

                    synchronized (currentNode) {
                        long start = System.currentTimeMillis();
                        while (currentNode.isOccupied() || currentNode.getTrafficLightColor() != 2 || !car.isRunning()) { // 2 = green
                            try {
                                currentNode.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        long end = System.currentTimeMillis();
                        car.addWaitingTime(end - start);
                        // Mark the current node as occupied by the car
                        currentNode.setOccupied(true);
                        currentNode.setOccupiedBy(car);
                    }

                    Platform.runLater(() -> {

                        ImageView previousImageView = carImageViewMap.get(car);
                        centerAnchorPane.getChildren().remove(previousImageView);

                        ImageView carImageView = car.getCarImageView();
                        double carSize = 16;

                        carImageView.setFitWidth(carSize);
                        carImageView.setFitHeight(carSize);

                        double carX = currentNode.getX() - carSize / 2; // Calculate the X coordinate for the car image
                        double carY = currentNode.getY() - carSize / 2; // Calculate the Y coordinate for the car image

                        carImageView.setLayoutX(carX);
                        carImageView.setLayoutY(carY);

                        centerAnchorPane.getChildren().add(carImageView);
                        carImageViewMap.put(car, carImageView);

                        synchronized (previousNode) {
                            previousNode.setOccupied(false);
                            previousNode.notifyAll();
                        }


                        if(currentNode.equals(car.getDestination())) {
                            synchronized (currentNode) {
                                currentNode.setOccupied(false);
                                currentNode.notifyAll();
                            }
                            Platform.runLater(() -> {
                                ImageView imageView1 = carImageViewMap.get(car);
                                centerAnchorPane.getChildren().remove(imageView1);
                                carImageViewMap.remove(car);
                            });
                            cancel();
                            long endTime = System.currentTimeMillis();
                            car.setArrived(true);
                            car.setRunningTime(endTime - startTime);
                        }
                        latch.countDown();
                    });
                }
                latch.await(); // Wait for all UI updates to complete

                return null;
            }
        };

        Thread thread = new Thread(task); // Create a separate thread for the task
        thread.setDaemon(true); // Set the thread as a daemon thread to allow application exit
        thread.start(); // Start the thread
    }

    private final Map<TrafficLight, Circle> trafficLightCircles = new HashMap<>();

    public void updateTrafficLight(TrafficLight trafficLight, int color) {
        Platform.runLater(() -> {
            Circle circle = trafficLightCircles.get(trafficLight);
            if (circle != null) {
                centerAnchorPane.getChildren().remove(circle);
            }

            Node node;
            if (color == 0) {                          // 0 = red
                node = trafficLight.getRed();
            } else if (color == 1 || color == 3) {     // 1,3 = yellow
                node = trafficLight.getYellow();
            } else {                                   // 2 = green
                node = trafficLight.getGreen();
            }

            Circle newCircle = new Circle(node.getX(), node.getY(), 5);
            if (color == 0) {
                newCircle.setStroke(Color.web("#ff0000"));
                newCircle.setFill(Color.web("#ff0000"));
            } else if (color == 1 || color == 3) {
                newCircle.setStroke(Color.web("#ffff00"));
                newCircle.setFill(Color.web("#ffff00"));
            } else {
                newCircle.setStroke(Color.web("#00ff00"));
                newCircle.setFill(Color.web("#00ff00"));
            }

            centerAnchorPane.getChildren().add(newCircle);
            trafficLightCircles.put(trafficLight, newCircle);
        });
    }


}
