package com.nfs.app.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

public class HistoryController implements Initializable {


    // create a hashmap to store the algorithm name and the algorithm object
    private HashMap<String, Algorithm_Abstract> algorithms = new HashMap<String, Algorithm_Abstract>();

    @FXML
    private VBox history_vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can set the number of panes you want to create dynamically
        int numberOfPanes = 5;

        for (int i = 0; i < numberOfPanes; i++) {
            createHistoryPane(i);
        }

        // get the algorithms from downloads / models folder and add them to the hashmap
        File folder = new File(System.getProperty("user.home") + "/Downloads/models");
        File[] algorithmFiles = folder.listFiles();

        if (algorithmFiles != null) {
            for (File file : algorithmFiles) {
                // load the serialized object Algorithm_Abstract
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(file.toURI().toURL().openStream());
                    Algorithm_Abstract algorithm = (Algorithm_Abstract) objectInputStream.readObject();
                    objectInputStream.close();
                    // add the algorithm to the hashmap
                    algorithms.put(algorithm.getName(), algorithm);
                    System.out.println("found algorithm " + algorithm.getName());
                } catch (IOException | ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println(algorithms);
            return;
        }
        System.out.println("No algorithms found");
    }

    private void createHistoryPane(int index) {
        Pane historyPane = new Pane();
        historyPane.setId("history_pane" + index);
        historyPane.setPrefSize(780.0, 54.0);
        historyPane.setStyle("-fx-border-width: 0; -fx-background-color: #363636;");

        Line line1 = new Line(100.0, 55.0, 670.0, 55.0);
        line1.setStroke(javafx.scene.paint.Color.valueOf("#1a1a1a"));
        line1.setVisible(false);

        ImageView dropDown = App.loadImage("images/drop_down.png");
        dropDown.setFitHeight(25.0);
        dropDown.setFitWidth(25.0);
        dropDown.setLayoutX(14.0);
        dropDown.setLayoutY(16.0);
        dropDown.setOnMouseClicked(event -> onDropDownClick(historyPane, line1, dropDown));

        Line line2 = new Line(-100.0, 0.0, -100.0, 42.0);
        line2.setLayoutX(317.0);
        line2.setLayoutY(7.0);
        line2.setStroke(javafx.scene.paint.Color.valueOf("#1a1a1a"));

        Line line3 = new Line(-100.0, 0.0, -100.0, 42.0);
        line3.setLayoutX(505.0);
        line3.setLayoutY(7.0);
        line3.setStroke(javafx.scene.paint.Color.valueOf("#1a1a1a"));

        Line line4 = new Line(-100.0, 0.0, -100.0, 42.0);
        line4.setLayoutX(693.0);
        line4.setLayoutY(7.0);
        line4.setStroke(javafx.scene.paint.Color.valueOf("#1a1a1a"));

        historyPane.getChildren().addAll(dropDown, line1, line2, line3, line4);

        this.history_vbox.getChildren().add(historyPane);
    }

    private void onDropDownClick(Pane historyPane, Line line, ImageView dropDown) {
        if (!line.isVisible()) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(historyPane.prefHeightProperty(), historyPane.getPrefHeight())),
            new KeyFrame(Duration.seconds(0.25), new KeyValue(historyPane.prefHeightProperty(), 111.0)),
            new KeyFrame(Duration.seconds(0.25), new KeyValue(dropDown.rotateProperty(), 90)));
        timeline.play();
        line.setVisible(true);
        } else {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                new KeyValue(historyPane.prefHeightProperty(), historyPane.getPrefHeight())),
            new KeyFrame(Duration.seconds(0.25), new KeyValue(historyPane.prefHeightProperty(), 54.0)),
            new KeyFrame(Duration.seconds(0.25), new KeyValue(dropDown.rotateProperty(), 0)));
        timeline.play();
        line.setVisible(false);
        }
    }

}
