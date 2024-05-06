package com.example.cpuscheduler;

import javafx.animation.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GanttChart {
    private ArrayList<GanttChartCell> cells;
    public static Boolean DrawOver=false;
    public GanttChart() {
        this.cells = new ArrayList<>();
    }

    public GanttChart(ArrayList<GanttChartCell> cells) {
        this.cells = cells;
    }

    public ArrayList<GanttChartCell> getCells() {
        return cells;
    }
    public Timeline timeline;

    public void setCells(ArrayList<GanttChartCell> cells) {
        this.cells = cells;
    }

    public void addCell(GanttChartCell x) {
        cells.add(x);
    }

    private void removeDuplicates() {
        for (int i = 1; i < cells.size()-1; i++) {
            if (cells.get(i).getValue() == cells.get(i - 1).getValue()) {
                cells.remove(i);
            }
        }
    }

    private void fixDoubleProblem(){
        for (int i = 1; i < cells.size(); i++) {
            if (new DecimalFormat("###.###").format(cells.get(i).getBegin()).equals(new DecimalFormat("###.###").format(cells.get(i - 1).getBegin()))) {
                cells.remove(i-1);
            }
        }
    }

    public void draw(HBox node, Scene scene, boolean drawDuplicates) {
        double percentage = 0.9;
        double minWidth = 25;
        double totalTime = cells.get(cells.size() - 1).getBegin();
        //node.setAlignment(Pos.CENTER);
        fixDoubleProblem();
        if (!drawDuplicates)
            removeDuplicates();

        int max = cells.size() - 1;


        for (int i = 0; i < max; i++) {

            VBox v = new VBox();
            Label PID = new Label(cells.get(i).getValue());
            Label time = new Label(new DecimalFormat("###.###").format(cells.get(i).getBegin()));
            PID.setPrefHeight(30);
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 1))));

            PID.setPrefHeight(30);
            PID.setAlignment(Pos.CENTER);
            v.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);
            PID.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);
            time.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);

            v.setMinWidth(minWidth);
            PID.setMinWidth(minWidth);
            time.setMinWidth(minWidth);


            v.getChildren().add(PID);
            v.getChildren().add(time);
            node.getChildren().add(v);

        }

        VBox v = new VBox();

            Label PID = new Label(cells.get(cells.size() - 1).getValue());
            Label time = new Label(new DecimalFormat("###.###").format(cells.get(cells.size() - 2).getBegin()));
            PID.setPrefHeight(30);
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 1, 1, 1))));

            PID.setPrefHeight(30);
            PID.setAlignment(Pos.CENTER);
            v.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
            PID.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
            time.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);



            v.setMinWidth(minWidth);
            PID.setMinWidth(minWidth);
            time.setMinWidth(minWidth);

            v.getChildren().add(PID);
            v.getChildren().add(time);
            node.getChildren().add(v);

            v.getChildren().clear();

        Label empty = new Label();
        empty.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 1))));
        empty.setPrefHeight(30);

        Label lastTime = new Label(new DecimalFormat("###.###").format(cells.get(cells.size()-1).getBegin()));


        v.getChildren().addAll(empty, lastTime);
    }
    public void drawL(HBox node, Scene scene, boolean drawDuplicates,ObservableList<Process> processes) {
        double percentage = 0.9;
        double minWidth = 25;
        double totalTime = cells.get(cells.size() - 1).getBegin();
        fixDoubleProblem();
        if (!drawDuplicates)
            removeDuplicates();

        int max = cells.size() - 1;
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), new EventHandler<>() {
            double currentXValue = 0.0;
            int i = -1;
            double currentWidth = minWidth;
            boolean newBox = false;
            VBox v1 = new VBox();
            Label PID1 = new Label(cells.get(0).getValue());
            Label time1 = new Label(new DecimalFormat("###.###").format(cells.get(0).getBegin()));

            @Override
            public void handle(ActionEvent actionEvent) {
                if(i == -1){
                    i++;
                    DrawOver=false;
                    PID1.setPrefHeight(30);
                    PID1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 1))));

                    PID1.setPrefHeight(30);
                    v1.setPrefWidth(minWidth);
                    PID1.setAlignment(Pos.CENTER);
                    v1.getChildren().add(PID1);
                    v1.getChildren().add(time1);
                    node.getChildren().add(v1);

                }

                //for (int i = 0; i < max; i++) {
                    double wid = (percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);


                if(newBox){
                    currentWidth = minWidth;
                     v1 = new VBox();
                     PID1 = new Label(cells.get(i).getValue());
                     time1 = new Label(new DecimalFormat("###.###").format(cells.get(i).getBegin()));
                    PID1.setPrefHeight(30);
                    v1.setPrefWidth(minWidth);
                    PID1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 1))));

                    PID1.setPrefHeight(30);
                    PID1.setAlignment(Pos.CENTER);
                    v1.getChildren().add(PID1);
                    v1.getChildren().add(time1);
                    node.getChildren().add(v1);
                }

                if(currentWidth< wid){
                    if(processes.get(i).getGuiRemainingTime()>0){
                        processes.get(i).setGuiRemainingTime(Math.round((processes.get(i).getGuiRemainingTime()-0.2) * 100) / 100.0);}
                    v1.setPrefWidth(v1.getPrefWidth()+5);
                    currentWidth+=5;
                    newBox = false;
                }
                    //v1.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);

                    PID1.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);
                    time1.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);

                    v1.setMinWidth(minWidth);
                    PID1.setMinWidth(minWidth);
                    time1.setMinWidth(minWidth);



                   if(currentWidth>= wid) {
                       processes.get(i).setGuiRemainingTime(0);
                      cells.get(i).setFlag(true);
                       i++;
                       newBox= true;
                   }

                    if(i>= max){
                        VBox v = new VBox();
                        Label PID = new Label(cells.get(cells.size() - 1).getValue());
                        Label time = new Label(new DecimalFormat("###.###").format(cells.get(cells.size() - 2).getBegin()));
                        PID.setPrefHeight(30);
                        PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 1, 1, 1))));

                        PID.setPrefHeight(30);
                        PID.setAlignment(Pos.CENTER);
                        v.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
                        PID.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
                        time.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);



                        v.setMinWidth(minWidth);
                        PID.setMinWidth(minWidth);
                        time.setMinWidth(minWidth);

                        v.getChildren().add(PID);
                        v.getChildren().add(time);
                        node.getChildren().add(v);

                        v.getChildren().clear();

                        Label empty = new Label();
                        empty.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 1))));
                        empty.setPrefHeight(30);

                        Label lastTime = new Label(new DecimalFormat("###.###").format(cells.get(cells.size()-1).getBegin()));


                        v.getChildren().addAll(empty, lastTime);
                        timeline.pause();
                        DrawOver=true;
                    }
               //}

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();


    }
    /// animation ghalat




    public void draw(HBox node, Scene scene) {
        draw(node, scene, false);
    }

    public void drawWithDuplicates(HBox node, Scene scene) {
        draw(node, scene, true);
    }

//////Live
    public void drawLive(HBox node, Scene scene,ObservableList<Process> processes) {
        drawL(node, scene, false, processes);
    }

    public void drawLiveWithDuplicates(HBox node, Scene scene , ObservableList<Process> processes) {
        drawL(node, scene, true,processes);
    }


}
