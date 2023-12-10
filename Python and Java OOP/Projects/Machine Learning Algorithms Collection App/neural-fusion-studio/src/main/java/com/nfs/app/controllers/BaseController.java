package com.nfs.app.controllers;

import java.io.IOException;

import com.nfs.app.App;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BaseController {


    @FXML
    private Pane sideBar,sideBarShadow1,sideBarShadow2;
    @FXML
    private Line sideBareHline1,sideBareHline2,sideBareHline3,sideBareHline4;
    

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }



    @FXML
    private void toggleSidebar() {
        double targetWidth = 0;
        double targetLayoutX = 0;
        double targetEndX = 0;

        if (sideBar.getPrefWidth() == 210) {
            // close the sidebar
            targetWidth = 70;
            targetLayoutX = 70.5;
            targetEndX = 70;
        } else {
            // open the sidebar
            targetWidth = 210;
            targetLayoutX = 210;
            targetEndX = 210;
        }

        // Create a timeline for the animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.prefWidthProperty(), sideBar.getPrefWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.minWidthProperty(), sideBar.getMinWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.maxWidthProperty(), sideBar.getMaxWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBarShadow1.layoutXProperty(), sideBarShadow1.getLayoutX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBarShadow2.layoutXProperty(), sideBarShadow2.getLayoutX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline1.endXProperty(), sideBareHline1.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline2.endXProperty(), sideBareHline2.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline3.endXProperty(), sideBareHline3.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline4.endXProperty(), sideBareHline4.getEndX())),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.prefWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.minWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.maxWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBarShadow1.layoutXProperty(), targetLayoutX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBarShadow2.layoutXProperty(), targetLayoutX+2)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline1.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline2.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline3.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline4.endXProperty(), targetEndX))
        );

        // Play the timeline animation
        timeline.play();
    }
}
