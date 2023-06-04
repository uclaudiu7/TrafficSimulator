package com.example.trafficsimulator.models;

public class TrafficLight {
    private final int id;
    private final Node red;
    private final Node yellow;
    private final Node green;

    public TrafficLight(int id, Node red, Node yellow, Node green) {
        this.id = id;
        this.red = red;
        this.yellow = yellow;
        this.green = green;
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
