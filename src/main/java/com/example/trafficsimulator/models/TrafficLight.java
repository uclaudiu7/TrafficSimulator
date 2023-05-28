package com.example.trafficsimulator.models;

import java.util.ArrayList;
import java.util.List;

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

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(red);
        nodes.add(yellow);
        nodes.add(green);
        return nodes;
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
