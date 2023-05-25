package com.example.trafficsimulator.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private DatabaseManager dbManager = new DatabaseManager();
    private String zone;
    private int cars;
    private double intensity;
    private double hazard;
    private Car car;
    private Node node;
    private int[][] map;

    private Graph graph;

    public Simulation(String zone, int cars, double intensity, double hazard) {
        this.zone = zone;
        this.cars = cars;
        this.intensity = intensity;
        this.hazard = hazard;
    }


    public List<Car> generateCars(String zone) throws SQLException {
        List<Node> carLocation = dbManager.getCarPosition(zone, cars);
        List<Car> carList = new ArrayList<>();
        for(int i = 0;i<cars;i++){
            car = new Car("Car"+i,carLocation.get(i));
            carList.add(car);
        }
        carList.forEach(car1 -> System.out.println(car1.getName() + " " + car1.getPosition()));
        return carList;
    }

    public List<Node> generateNodes(String zone) throws SQLException {
        //nodeList.forEach(node1 -> System.out.println(node1.getName() + " " + node1.getPosition()));
        return dbManager.getNodes(zone);
    }

    public List<Edge> generateEdges(String zone) throws SQLException {
        //edgeList.forEach(edge1 -> System.out.println(edge1.getName() + " " + edge1.getFrom() + " " + edge1.getTo()));
        return dbManager.getEdges(zone);
    }
}
