package com.example.trafficsimulator.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final double x;
    private final double y;
    private int isSign;
    private final List<Node> neighbours;
    private final List<Node> reachableNodes;
    private final Object lock;
    private Car occupant;
    private int trafficLightColor; // 0 = red, 1 = yellow, 2 = green, 3 = yellow
    private int trafficLightId;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
        this.reachableNodes = new ArrayList<>();
        this.lock = new Object();
        this.occupant = null;
        this.trafficLightColor = 2;
        this.isSign = 0;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Double.compare(node.getX(), getX()) == 0 && Double.compare(node.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setIsSign(int isSign) { this.isSign = isSign; }
    public int getIsSign() { return isSign; }

    public void setTrafficLightColor(int trafficLightColor) {
        synchronized (lock) {
            this.trafficLightColor = trafficLightColor;
        }
    }

    public int getTrafficLightColor() {
        synchronized (lock) {
            return trafficLightColor;
        }
    }
    public void switchTrafficLightColor() {
        synchronized (lock) {
            trafficLightColor = (trafficLightColor + 1) % 4;
        }
    }

    public void setTrafficLightId(int trafficLightId) {
        synchronized (lock) {
            this.trafficLightId = trafficLightId;
        }
    }

    public int getTrafficLightId() {
        synchronized (lock) {
            return trafficLightId;
        }
    }

    public boolean isOccupied() {
        synchronized (lock) {
            return occupant != null;
        }
    }

    public void setOccupied(boolean occupied) {
        synchronized (lock) {
            if (!occupied) {
                occupant = null;
            }
        }
    }

    public void addReachableNodes(Node node, Node current) {
        synchronized (lock) {
            List<Node> neighbours = node.getNeighbours();
            if (neighbours != null) {
                for (Node neighbour : neighbours) {
                    if (!reachableNodes.contains(neighbour) && !neighbour.equals(current)) {
                        reachableNodes.add(neighbour);
                        addReachableNodes(neighbour, node);
                    }
                }
            }
        }
    }

    public List<Node> getReachableNodes() {
        synchronized (lock) {
            return reachableNodes;
        }
    }

    public void addNeighbour(Node node) {
        synchronized (lock) {
            neighbours.add(node);
        }
    }

    public List<Node> getNeighbours() {
        synchronized (lock) {
            return neighbours;
        }
    }

    public void setOccupiedBy(Car car) {
        synchronized (lock) {
            occupant = car;
        }
    }
}

