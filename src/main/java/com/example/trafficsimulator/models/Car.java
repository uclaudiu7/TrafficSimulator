package com.example.trafficsimulator.models;

import com.example.trafficsimulator.controllers.TrafficMapController;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Car extends Thread {
    private TrafficMapController trafficMapController;
    private final String name;
    private Node start;
    private Node current;
    private final Node destination;
    private List<Node> path;
    private boolean running;
    private final String color;
    private ImageView carImageView;

    public Car(String name, Node start, Node destination) {
        this.name = name;
        this.start = start;
        this.destination = destination;
        StringBuilder hex = new StringBuilder("#");
        for(int i = 0; i < 6; i++){
            hex.append(Integer.toHexString((int) (Math.random() * 16)));
        }
        color = hex.toString();
        running = true;
        generateRandomImage();
    }

    @Override
    public void run(){
        move();
    }

    public void generateRandomImage() {
        ColorAdjust colorAdjust = new ColorAdjust();
        Random random = new Random();
        int randomCar = random.nextInt(5);
        Image image = null;
        switch (randomCar) {
            case 0:
                image = new Image(Objects.requireNonNull(getClass().getResource("/cars/tesla.png")).toString());
                break;
            case 1:
                image = new Image(Objects.requireNonNull(getClass().getResource("/cars/taxi.png")).toString());
                break;
            case 2:
                image = new Image(Objects.requireNonNull(getClass().getResource("/cars/jeep.png")).toString());
                break;
            case 3:
                image = new Image(Objects.requireNonNull(getClass().getResource("/cars/bus.png")).toString());
                break;
            case 4:
                image = new Image(Objects.requireNonNull(getClass().getResource("/cars/truck.png")).toString());
                break;
        }
        carImageView = new ImageView(image);

        double hue = random.nextDouble() * 2 - 1; // Range from -1 to 1
        double saturation = random.nextDouble() * 0.8 + 1.2; // Range from 1.2 to 2
        double brightness = random.nextDouble() * 0.1 - 0.2; // Range from -1 to 1
        double contrast = random.nextDouble() * 0.8 + 1.2; // Range from 1.2 to 2

        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        
        carImageView.setEffect(colorAdjust);
    }

    public ImageView getCarImageView() {
        return carImageView;
    }

    public void move(){
        trafficMapController.moveCar(this, path);
    }

    public void setCurrent(Node current){
        this.current = current;
        System.out.println("Current: " + current);
    }
    public void setPath(List<Node> path){
        this.path = path;
    }

    public void setTrafficMapController(TrafficMapController trafficMapController){
        this.trafficMapController = trafficMapController;
    }
    public void setRunning(boolean running) { this.running = running; }
    public Node getStart() { return start; }
    public void setStart(Node start) { this.start = start; }
    public String getColor() { return color; }
    public Node getDestination() { return destination; }

}