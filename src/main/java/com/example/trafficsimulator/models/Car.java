package com.example.trafficsimulator.models;

public class Car {
    private final String name;
    private Node start;
    private final Node destination;
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
    }

    public String getName() { return name; }
    public Node getStart() { return start; }
    public void setStart(Node start) { this.start = start; }
    public String getColor() { return color; }
    public Node getDestination() { return destination; }

}
