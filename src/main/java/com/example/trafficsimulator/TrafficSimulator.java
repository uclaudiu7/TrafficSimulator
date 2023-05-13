package com.example.trafficsimulator;

import com.example.trafficsimulator.scenes.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class TrafficSimulator extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainMenu mainMenu = new MainMenu(stage);
        mainMenu.show();
    }

    public static void main(String[] args) {
        launch();
    }
}