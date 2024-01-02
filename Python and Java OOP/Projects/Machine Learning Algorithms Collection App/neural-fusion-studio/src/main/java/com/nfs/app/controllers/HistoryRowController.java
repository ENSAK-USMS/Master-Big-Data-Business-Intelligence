/**
 * @author abdobella
 * Date: Dec 31, 2023
 * Time: 9:27:16 PM
*/
package com.nfs.app.controllers;

import java.util.Date;

import com.nfs.app.algorithms.Algorithm_Abstract;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class HistoryRowController {

    @FXML
    private Pane historyPane;
    @FXML
    private Line line;
    @FXML
    private ImageView dropDown;

    @FXML
    private Label dataset;

    @FXML
    private Label date;

    @FXML
    private Label name;

    @FXML
    private Label paramsToDisplay;





    @FXML
    private void onDropDownClick() {
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





    public void setAlgorithm(Algorithm_Abstract algorithm) {
        dataset.setText(algorithm.getDataset().relationName());
        // get string date and cconvert it to date
        String dateStr = algorithm.getDate();
        date.setText(dateStr);  
        name.setText(algorithm.getName());
        paramsToDisplay.setText(algorithm.getAccuracy());
    }

}










