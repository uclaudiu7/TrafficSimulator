package com.example.trafficsimulator.models;

public class Simulation {
    private String zone;
    private int cars;
    private int intensity;
    private int hazard;

    private int[][] map;

    public Simulation(String zone, int cars, int intensity, int hazard) {
        this.zone = zone;
        this.cars = cars;
        this.intensity = intensity;
        this.hazard = hazard;
    }



}
