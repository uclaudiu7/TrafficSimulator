package com.example.trafficsimulator.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final double x;
    private final double y;
    private Car ocupant;
    List<Node> neighbours;
    List<Node> reachableNodes;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<>();
        reachableNodes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return  "[" + x +
                ", " + y + "]";
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

    public double getX() { return x; }
    public double getY() { return y; }
    public void setOcupant(Car ocupant) { this.ocupant = ocupant; }
    public Car getOcupant() { return ocupant; }
    public boolean isOcupied() { return ocupant != null; }
    public Double distanceTo(Node node) {
        return Math.sqrt(Math.pow(this.x - node.x, 2) + Math.pow(this.y - node.y, 2));
    }

    public void addReachableNodes(Node node, Node current) {
        List<Node> neighbours = node.getNeighbours();
        if(neighbours != null){
            for(Node neighbour : neighbours){
                if(!reachableNodes.contains(neighbour) && !neighbour.equals(current)){
                    reachableNodes.add(neighbour);
                    addReachableNodes(neighbour, node);
                }
            }
        }
    }

    public List<Node> getReachableNodes() {
        return reachableNodes;
    }

    public void addNeighbour(Node node) {
        neighbours.add(node);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

}
