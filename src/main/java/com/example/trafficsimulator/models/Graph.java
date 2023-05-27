package com.example.trafficsimulator.models;

import java.util.*;

public class Graph {
    private List<Node> nodes;
    private final List<Edge> edges;
    private List<Car> cars;
    private final int[][] map;
    private final Object lock;

    public Graph(List<Node> nodes, List<Edge> edges, List<Car> cars) {
        this.nodes = nodes;
        this.edges = edges;
        this.cars = cars;
        this.map = new int[nodes.size()][nodes.size()];
        this.lock = new Object();
        createGraph();
    }

    public void createGraph() {
        synchronized (lock) {
            for (Edge edge : edges) {
                Node start = edge.getStart();
                Node end = edge.getEnd();

                int index1 = nodes.indexOf(start);
                int index2 = nodes.indexOf(end);

                map[index1][index2] = 1;
            }
        }
    }

    public void setNodes(List<Node> nodes) {
        synchronized (lock) {
            this.nodes = nodes;
        }
    }

    public List<Node> getShortestPath(Node source, Node destination){
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parent = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.offer(source);
        visited.add(source);

        // BFS
        while(!queue.isEmpty()){
            Node current = queue.poll();
            if(current == destination){
                return constructPath(parent, source, destination);
            }
            for(Node neighbour : current.getNeighbours()){
                if(!visited.contains(neighbour)){
                    queue.offer(neighbour);
                    visited.add(neighbour);
                    parent.put(neighbour, current);
                }
            }
        }

        return null;
    }

    private List<Node> constructPath(Map<Node, Node> parent, Node source, Node destination){
        LinkedList<Node> path = new LinkedList<>();
        Node current = destination;
        while(current != source){
            path.addFirst(current);
            current = parent.get(current);
        }
        path.addFirst(source);
        return path;
    }

    public void printGraph() {
        synchronized (lock) {
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
