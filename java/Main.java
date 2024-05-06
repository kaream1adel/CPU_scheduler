package com.example.cpuscheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage primaryStage;
    public static Scene scene;
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage window) throws Exception{
        primaryStage = window;
        primaryStage.setTitle("CPU Scheduler");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("input_response.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }



}
