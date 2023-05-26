package com.example.trafficsimulator.models;

public class Edge {
    private final Node start;
    private final Node end;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getStart() { return start; }
    public Node getEnd() { return end; }

}
