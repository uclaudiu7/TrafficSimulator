package com.example.trafficsimulator.models;

import javafx.scene.shape.Line;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DatabaseManager {
    private Connection conn = null;

    public DatabaseManager() {
        try {
            String url = "jdbc:mysql://localhost:/traffic_simulator";
            String user = "root";
            String password = null;
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNodes(Vector<Node> nodes, String zone) {
        String sql = null;
        if(zone.equals("UAIC Corp C")) {
            sql = "INSERT INTO nodes_uaic (x, y) VALUES (?, ?)";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "INSERT INTO nodes_pasarela (x, y) VALUES (?, ?)";
        } else {
            sql = "INSERT INTO nodes_eminescu (x, y) VALUES (?, ?)";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Node node : nodes) {
                pstmt.setDouble(1, node.getX());
                pstmt.setDouble(2, node.getY());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertEdges(Vector<Line> edges, String zone) {
        String sql = null;
        if(zone.equals("UAIC Corp C")) {
            sql = "INSERT INTO edges_uaic (x1, y1, x2, y2) VALUES (?, ?, ?, ?)";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "INSERT INTO edges_pasarela (x1, y1, x2, y2) VALUES (?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO edges_eminescu (x1, y1, x2, y2) VALUES (?, ?, ?, ?)";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Line edge : edges) {
                pstmt.setDouble(1, edge.getStartX());
                pstmt.setDouble(2, edge.getStartY());
                pstmt.setDouble(3, edge.getEndX());
                pstmt.setDouble(4, edge.getEndY());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vector<Line> loadEdges(String zone){
        Vector<Line> edges = new Vector<>();
        String sql = null;
        if(zone.equals("UAIC Corp C")) {
            sql = "SELECT * FROM edges_uaic";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "SELECT * FROM edges_pasarela";
        } else {
            sql = "SELECT * FROM edges_eminescu";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            while(pstmt.getResultSet().next()){
                Line edge = new Line();
                edge.setStartX(pstmt.getResultSet().getDouble("x1"));
                edge.setStartY(pstmt.getResultSet().getDouble("y1"));
                edge.setEndX(pstmt.getResultSet().getDouble("x2"));
                edge.setEndY(pstmt.getResultSet().getDouble("y2"));
                edges.add(edge);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return edges;
    }

    public Vector<Node> loadNodes(String zone){
        Vector<Node> nodes = new Vector<>();
        String sql;
        if(zone.equals("UAIC Corp C")) {
            sql = "SELECT * FROM nodes_uaic";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "SELECT * FROM nodes_pasarela";
        } else {
            sql = "SELECT * FROM nodes_eminescu";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            while(pstmt.getResultSet().next()){
                Node node = new Node(pstmt.getResultSet().getDouble("x"), pstmt.getResultSet().getDouble("y"));
                nodes.add(node);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nodes;
    }

    public List<Node> loadTrafficLights(String zone){
        List<Node> trafficLights = new ArrayList<>();
        String sql;
        if(zone.equals("UAIC Corp C")) {
            sql = "SELECT * FROM lights_uaic";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "SELECT * FROM lights_pasarela";
        } else {
            sql = "SELECT * FROM lights_eminescu";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            while(pstmt.getResultSet().next()){
                Node node = new Node(pstmt.getResultSet().getDouble("x"), pstmt.getResultSet().getDouble("y"));
                int color = pstmt.getResultSet().getInt("light");
                int id = pstmt.getResultSet().getInt("light_id");
                if(color == 0)
                    node.setTrafficLightColor(0);
                else if(color == 1)
                    node.setTrafficLightColor(2);
                node.setTrafficLightId(id);
                trafficLights.add(node);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return trafficLights;
    }

    public List<TrafficLight> loadTrafficLightPositions(String zone){
        List<TrafficLight> trafficLights = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        String sql;
        if(zone.equals("UAIC Corp C")) {
            sql = "SELECT * FROM lights_pos_uaic";
        } else if (zone.equals("Pasarela Octav Bancila")) {
            sql = "SELECT * FROM lights_pos_pasarela";
        } else {
            sql = "SELECT * FROM lights_pos_eminescu";
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            while(pstmt.getResultSet().next()){
                Node node = new Node(pstmt.getResultSet().getDouble("x"), pstmt.getResultSet().getDouble("y"));
                int id = pstmt.getResultSet().getInt("light_id");
                node.setTrafficLightId(id);
                nodes.add(node);
                if(nodes.size() == 3){
                    TrafficLight trafficLight = new TrafficLight(id ,nodes.get(0), nodes.get(1), nodes.get(2));
                    trafficLights.add(trafficLight);
                    nodes.clear();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return trafficLights;
    }

}

