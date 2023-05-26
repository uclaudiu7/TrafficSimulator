package com.example.trafficsimulator.models;

import java.util.List;

public class Graph {
    private final List<Node> nodes;

    private final List<Edge> edges;

    private List<Car> cars;

    private final int[][] map;

    public Graph(List<Node> nodes, List<Edge> edges, List<Car> cars) {
        this.nodes = nodes;
        this.edges = edges;
        this.cars = cars;
        this.map = new int[nodes.size()][nodes.size()];
    }

    public void createGraph() {
        //nodes.forEach(node -> System.out.println(node.getX() + " " + node.getY()));
        for(Edge edge : edges)
        {
            Node node1 = new Node(edge.getX1(),edge.getY1());
            Node node2 = new Node(edge.getX2(),edge.getY2());
            //System.out.println(node1.getX() + " " + node1.getY() + " " + node2.getX() + " " + node2.getY());
            for(Node node : nodes){
                if(node.getX() == node1.getX() && node.getY() == node1.getY())
                    node1 = node;
                if(node.getX() == node2.getX() && node.getY() == node2.getY())
                    node2 = node;
            }
            int index1 = nodes.indexOf(node1);
            //System.out.println(index1);
            int index2 = nodes.indexOf(node2);
            //System.out.println(index2);
            map[index1][index2] = 1;
        }
    }

    public void printGraph(){
        for(int i = 0;i <nodes.size();i++){
            for(int j = 0;j<nodes.size();j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
