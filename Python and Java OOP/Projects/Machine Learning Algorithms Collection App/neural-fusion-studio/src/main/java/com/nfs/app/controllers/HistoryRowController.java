/**
 * @author abdobella
 * Date: Dec 31, 2023
 * Time: 9:27:16 PM
*/
package com.nfs.app.controllers;

import java.io.IOException;
import java.util.Date;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

    private Algorithm_Abstract algorithm;





    @FXML
    private void onDropDownClick() {
        if (!line.isVisible()) {
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                    new KeyValue(historyPane.prefHeightProperty(), historyPane.getPrefHeight())),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(historyPane.prefHeightProperty(), 140.0)),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(dropDown.rotateProperty(), 90))
            );
            timeline.setOnFinished(event -> {
                // Perform action after animation finishes
                line.setVisible(true);
                // add to history pane the return of the algorithm.getotherresults
                GridPane otherResults = algorithm.getOtherResults();
                // add id for easy removal
                otherResults.setId("otherResults");
                otherResults.setLayoutY(64);
                otherResults.setLayoutX(45);
                historyPane.getChildren().add(otherResults);
            });
            timeline.play();
        } else {
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                    new KeyValue(historyPane.prefHeightProperty(), historyPane.getPrefHeight())),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(historyPane.prefHeightProperty(), 54.0)),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(dropDown.rotateProperty(), 0))
            );
            timeline.setOnFinished(event -> {
                // Perform action after animation finishes
                line.setVisible(false);
            });
            timeline.play();
            // remove the other results
            historyPane.getChildren().remove(historyPane.lookup("#otherResults"));
        }
    }





    public void setAlgorithm(Algorithm_Abstract algorithm) {
        dataset.setText(algorithm.getDataset().relationName());
        // get string date and cconvert it to date
        String dateStr = algorithm.getDate();
        date.setText(dateStr);  
        name.setText(algorithm.getName()); 
        paramsToDisplay.setText(algorithm.getAccuracy());
        this.algorithm = algorithm;
    }

    @FXML
    public void swithToHomePage() throws IOException {
        App.switchPage("views/dashboard/index");
    }
}










