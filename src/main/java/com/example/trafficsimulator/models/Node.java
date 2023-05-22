package com.example.trafficsimulator.models;

public class Node {
    private double x;
    private double y;
    private Car ocupant;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return  "[" + x +
                ", " + y + "]";
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setOcupant(Car ocupant) { this.ocupant = ocupant; }
    public Car getOcupant() { return ocupant; }
    public boolean isOcupied() { return ocupant != null; }
    public Double distanceTo(Node node) {
        return Math.sqrt(Math.pow(this.x - node.x, 2) + Math.pow(this.y - node.y, 2));
    }
}
