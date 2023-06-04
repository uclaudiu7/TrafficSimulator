package com.example.trafficsimulator.models;

import com.example.trafficsimulator.scenes.TrafficMap;

import java.util.*;

public class Simulation {
    private final TrafficMap trafficMap;
    private final String zone;
    private final int numberOfCars;
    private final double speed;
    private final Graph graph;
    private final List<Node> nodes;
    private List<TrafficLight> trafficLightPositions;
    private final List<Edge> edges;
    private final List<Car> cars;
    private final List<Car> finishedCars;

    public Simulation(String zone, int numberOfCars, double intensity, TrafficMap trafficMap) {
        this.zone = zone;
        this.numberOfCars = numberOfCars;
        this.speed = intensity;
        this.trafficMap = trafficMap;
        finishedCars = new ArrayList<>();
        nodes = trafficMap.getTrafficMapController().getNodes();
        edges = trafficMap.getTrafficMapController().getEdges();
        graph = new Graph(nodes, edges);
        setNeighbours();
        setReachableNodes();
        cars = generateCars();
        setTrafficLights();
        setSigns();
    }

    public void setSigns(){
        DatabaseManager databaseManager = new DatabaseManager();
        List<Node> signs = databaseManager.loadSigns(zone);

        for(Node sign : signs){
            int index = nodes.indexOf(sign);
            nodes.get(index).setIsSign(1);
        }
    }

    public void setTrafficLights() {
        DatabaseManager databaseManager = new DatabaseManager();
        List<Node> trafficLights = databaseManager.loadTrafficLights(zone);
        trafficLightPositions = databaseManager.loadTrafficLightPositions(zone);

        for (Node node : trafficLights) {
            int index = nodes.indexOf(node);
            nodes.get(index).setTrafficLightColor(node.getTrafficLightColor());
            nodes.get(index).setTrafficLightId(node.getTrafficLightId());
        }

        for(Node node : trafficLights){
            Thread thread = new Thread(() -> Simulation.this.scheduleTrafficLights(node, 10000 / (long) speed));
            thread.start();
        }
    }

    public void scheduleTrafficLights(Node node, long period){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int index = nodes.indexOf(node);
                nodes.get(index).switchTrafficLightColor();
                trafficMap.getTrafficMapController().updateTrafficLight(
                        trafficLightPositions.get(node.getTrafficLightId()-1),
                        nodes.get(index).getTrafficLightColor()
                );
                synchronized (nodes.get(index)) {
                    nodes.get(index).notifyAll();
                }

                if(nodes.get(index).getTrafficLightColor() == 1 || nodes.get(index).getTrafficLightColor() == 3){
                    cancel();

                    // Schedule a new task after the desired delay
                    Timer newTimer = new Timer();
                    TimerTask newTimerTask = new TimerTask() {
                        @Override
                        public void run() {
                            scheduleTrafficLights(node, 2000 / (long) speed);
                        }
                    };
                    newTimer.schedule(newTimerTask, 2000 / (long) speed);
                } else {
                    cancel();

                    // Schedule a new task after the desired delay
                    Timer newTimer = new Timer();
                    TimerTask newTimerTask = new TimerTask() {
                        @Override
                        public void run() {
                            scheduleTrafficLights(node,10000 / (long) speed);
                        }
                    };
                    newTimer.schedule(newTimerTask, 10000 / (long) speed);
                }
            }
        };

        timer.schedule(timerTask, 0, period);
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
            while(reachableNodes.size() == 0 || start.isOccupied()){
                index = random.nextInt(upperbound);
                start = nodes.get(index);
                reachableNodes = start.getReachableNodes();
            }
            int index2 = random.nextInt(reachableNodes.size());
            Node end = reachableNodes.get(index2);

            Car car = new Car(start, end);

            start.setOccupied(true);
            car.setTrafficMapController(trafficMap.getTrafficMapController());

            carList.add(car);
        }

        return carList;
    }

    public void addCar(){
        Random random = new Random();
        int upperbound = nodes.size();
        int index = random.nextInt(upperbound);
        Node start = nodes.get(index);

        List<Node> reachableNodes = start.getReachableNodes();
        while(reachableNodes.size() == 0 || start.isOccupied()){
            index = random.nextInt(upperbound);
            start = nodes.get(index);
            reachableNodes = start.getReachableNodes();
        }
        int index2 = random.nextInt(reachableNodes.size());
        Node end = reachableNodes.get(index2);

        Car car = new Car(start, end);

        start.setOccupied(true);
        car.setTrafficMapController(trafficMap.getTrafficMapController());

        cars.add(car);

        List<Node> path = graph.getShortestPath(car.getStart(), car.getDestination());
        car.setPath(path);
        car.start();
    }

    public void removeCar(Car car){
        cars.remove(car);
        finishedCars.add(car);
        addCar();
    }

    public void start(){
        for(Car car : cars){
            List<Node> path = graph.getShortestPath(car.getStart(), car.getDestination());
            car.setPath(path);
            car.start();
        }
    }
    public List<Car> getFinishedCars() { return finishedCars; }

}
