package com.example.trafficsimulator.models;

public class TrafficLight {
    private int id;
    private Node red;
    private Node yellow;
    private Node green;

    public TrafficLight(int id, Node red, Node yellow, Node green) {
        this.id = id;
        this.red = red;
        this.yellow = yellow;
        this.green = green;
    }

    public int getId() {
        return id;
    }

    public Node getRed() {
        return red;
    }

    public Node getYellow() {
        return yellow;
    }

    public Node getGreen() {
        return green;
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                "id=" + id +
                ", red=" + red +
                ", yellow=" + yellow +
                ", green=" + green +
                '}';
    }
}
