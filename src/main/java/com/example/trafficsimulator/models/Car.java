package com.example.trafficsimulator.models;

public class Car {
    private String name;
    private Node position;

    private Node destination;

    public Car(String name, Node position, Node destination) {
        this.name = name;
        this.position = position;
        this.destination = destination;
    }

    public String getName() { return name; }
    public Node getPosition() { return position; }
    public void setPosition(Node position) { this.position = position; }
    public Node getDestination() { return destination; }

}
