package com.example.trafficsimulator.models;

import com.example.trafficsimulator.controllers.TrafficMapController;

import java.util.List;

public class Car extends Thread {
    private TrafficMapController trafficMapController;
    private final String name;
    private Node start;
    private Node current;
    private final Node destination;
    private List<Node> path;
    private boolean running;
    private final String color;

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
    }

    @Override
    public void run(){
        move();
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