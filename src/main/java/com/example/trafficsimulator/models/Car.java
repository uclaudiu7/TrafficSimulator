package com.example.trafficsimulator.models;

public class Car {
    private String name;
    private Node position;

    public Car(String name, Node position) {
        this.name = name;
        this.position = position;
    }

    public String getName() { return name; }
    public Node getPosition() { return position; }
    public void setPosition(Node position) { this.position = position; }

}
