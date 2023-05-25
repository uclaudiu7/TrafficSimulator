package com.example.trafficsimulator.models;

import java.util.List;

public class Graph {
    private List<Node> nodes;

    private List<Edge> edges;

    private List<Car> cars;

    private int[][] map;

    public Graph(List<Node> nodes, List<Edge> edges, List<Car> cars) {
        this.nodes = nodes;
        this.edges = edges;
        this.cars = cars;
        this.map = new int[nodes.size()][nodes.size()];
    }

    public void createGraph() {
        for(int i = 0;i <nodes.size();i++){
            for(int j = 0;j<nodes.size();j++){
                if(edges.get(i).connectedTo(nodes.get(i),nodes.get(j))){
                    map[i][j] = 1;
                }else {
                    map[i][j] = 0;
                }
            }
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
