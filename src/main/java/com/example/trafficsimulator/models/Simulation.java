package com.example.trafficsimulator.models;

import com.example.trafficsimulator.scenes.TrafficMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final TrafficMap trafficMap;
    private final String zone;
    private final int numberOfCars;
    private final double intensity;
    private final double hazard;
    private int[][] map;
    private final Graph graph;
    private final List<Node> nodes;
    private final List<Edge> edges;
    private List<Car> cars;

    public Simulation(String zone, int numberOfCars, double intensity, double hazard, TrafficMap trafficMap) {
        this.zone = zone;
        this.numberOfCars = numberOfCars;
        this.intensity = intensity;
        this.hazard = hazard;
        this.trafficMap = trafficMap;
        nodes = trafficMap.getTrafficMapController().getNodes();
        edges = trafficMap.getTrafficMapController().getEdges();
        graph = new Graph(nodes, edges, cars);
        setNeighbours();
        setReachableNodes();
        cars = generateCars();
    }

    public void setNeighbours(){
        for(Edge edge : edges){
            Node start = edge.getStart();
            Node end = edge.getEnd();

            int index1 = nodes.indexOf(start);
            int index2 = nodes.indexOf(end);

            nodes.get(index1).addNeighbour(nodes.get(index2));
        }
    }

    public void setReachableNodes(){
        for(Node node : nodes){
            node.addReachableNodes(node, node);
        }
        graph.setNodes(nodes);
    }

    public List<Car> generateCars() {
        List<Car> carList = new ArrayList<>();
        Random random = new Random();
        int upperbound = nodes.size();

        for(int i = 0; i < numberOfCars; i++){
            int index = random.nextInt(upperbound);
            Node start = nodes.get(index);

            List<Node> reachableNodes = start.getReachableNodes();
            while(reachableNodes.size() == 0){
                index = random.nextInt(upperbound);
                start = nodes.get(index);
                reachableNodes = start.getReachableNodes();
            }
            int index2 = random.nextInt(reachableNodes.size());
            Node end = reachableNodes.get(index2);

            Car car = new Car("Car " + i, start, end);
            car.setTrafficMapController(trafficMap.getTrafficMapController());
            //TODO: create new thread for each car


            carList.add(car);
        }

        return carList;
    }

    public void start(){
        int i = 0;
        for(Car car : cars){
            System.out.println("Car " + i++ + " is moving from " + car.getStart() + " to " + car.getDestination());
            List<Node> path = graph.getShortestPath(car.getStart(), car.getDestination());
            car.setPath(path);
            //drawPath(path, car);
            car.start();
        }
    }

    public void drawPath(List<Node> path, Car car){
        for(Node node : path){
            System.out.println("for _____");
            if(node == path.get(0)){
                trafficMap.getTrafficMapController().drawCar(node, car.getColor(), "start");
            } else if(node == path.get(path.size()-1)){
                trafficMap.getTrafficMapController().drawCar(node, car.getColor(), "end");
            } else{
//                trafficMap.getTrafficMapController().drawCar(node, car.getColor(), "mid");
            }
        }
    }
}
