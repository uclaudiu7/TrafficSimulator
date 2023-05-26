package com.example.trafficsimulator.models;

import com.example.trafficsimulator.scenes.TrafficMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final TrafficMap trafficMap;
    private DatabaseManager dbManager = new DatabaseManager();
    private String zone;
    private int cars;
    private double intensity;
    private double hazard;
    private Car car;
    private Node node;
    private int[][] map;

    private Graph graph;

    public Simulation(String zone, int cars, double intensity, double hazard, TrafficMap trafficMap) {
        this.zone = zone;
        this.cars = cars;
        this.intensity = intensity;
        this.hazard = hazard;
        this.trafficMap = trafficMap;
    }


    public List<Car> generateCars() throws SQLException {
        List<Node> carLocation = dbManager.getCarPosition(zone, cars);
        List<Car> carList = new ArrayList<>();
        List<Node> destination = dbManager.getNodes(zone);
        Random random = new Random();
        int upperbound = destination.size()-1;
        for(int i = 0;i<cars;i++){
            car = new Car("Car"+i,carLocation.get(i),destination.get(random.nextInt(upperbound)));
            carList.add(car);
        }
        //carList.forEach(car1 -> System.out.println(car1.getName() + " " + car1.getPosition() + " " + car1.getDestination()));
        return carList;
    }

    public List<Node> generateNodes() throws SQLException {
        //nodeList.forEach(node1 -> System.out.println(node1.getName() + " " + node1.getPosition()));
        return dbManager.getNodes(zone);
    }

    public List<Edge> generateEdges() throws SQLException {
        //edgeList.forEach(edge1 -> System.out.println(edge1.getName() + " " + edge1.getFrom() + " " + edge1.getTo()));
        return dbManager.getEdges(zone);
    }
    public void moveCar(Car car){
        Random random  = new Random();
        List<Node> nodeList = dbManager.getNodes(zone);
        int upperbound = nodeList.size()-1;

        int index = random.nextInt(upperbound);

        int index2 = random.nextInt(upperbound);
        Node node = nodeList.get(index);
        Node node2 = nodeList.get(index2);

        car.setPosition(node);

        trafficMap.updateCarPosition(car, node);
        trafficMap.updateCarPosition(car, node2);
    }
}
