/**
 * @author abdobella
 * Date: Dec 10, 2023
 * Time: 1:36:27 AM
*/
package com.nfs.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.animation.RotateTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class DashboardController {
    
    @FXML
    private Pane outerCirclePane;

    @FXML
    private Circle testCircleAnimation1, testCircleAnimation2, testCircleAnimation3;



    // i want to change the possition of the circle in animation when the mouse is hovering outside the circle

    @FXML
    private void onTestCircleHoverEntered() {
        // Create a TranslateTransition object
        animateElement(testCircleAnimation1, -75, -75);
        animateElement(testCircleAnimation2, -65-testCircleAnimation2.getRadius()*2, 0,0.125);
        animateElement(testCircleAnimation3, -75, 75,0.25);
    }
    
    
    
    @FXML
    private void onTestCircleHoverExited() {
        // Create a TranslateTransition object
        animateElement(testCircleAnimation1, 0, 0);
        animateElement(testCircleAnimation2, 0, 0,0.125);
        animateElement(testCircleAnimation3, 0, 0,0.25);
    }


    private void animateElement(Node element, double newX, double newY, double delay) {
        // Create a TranslateTransition object
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.4), element);

        // Set the new position of the element
        translateTransition.setToX(newX);
        translateTransition.setToY(newY);

        // Set the easing function to make the animation start fast and end slow
        translateTransition.setInterpolator(Interpolator.EASE_OUT);

        // Set the delay time
        translateTransition.setDelay(Duration.seconds(delay));

        // Play the animation
        translateTransition.play();
    }

    private void animateElement(Node element, double newX, double newY) {
        // Create a TranslateTransition object
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.4), element);

        // Set the new position of the element
        translateTransition.setToX(newX);
        translateTransition.setToY(newY);

        // Set the easing function to make the animation start fast and end slow
        translateTransition.setInterpolator(Interpolator.EASE_OUT);

        // Play the animation
        translateTransition.play();
    }

    // on exit the circle will return to its original possition









    @FXML
    private void onCentercircleHoverEntered() {
        // Create a RotateTransition object
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.3), outerCirclePane);

        // Set the axis of rotation to Z-axis
        rotateTransition.setAxis(Rotate.Z_AXIS);

        // Set the angle of rotation to 45 degrees
        rotateTransition.setByAngle(45);

        // Create a StrokeTransition object for the circle
        Circle circle = (Circle) outerCirclePane.getChildren().get(6);
        StrokeTransition strokeTransition = new StrokeTransition(Duration.seconds(0.3), circle);
        strokeTransition.setToValue(Color.web("#b73437"));

        
        
        // Create a Timeline for the lines
        Timeline lineTimeline = new Timeline();
        lineTimeline.setAutoReverse(false);
        lineTimeline.setCycleCount(1);

        // circle strock width
        KeyValue kv_circle_strkewidth = new KeyValue(circle.strokeWidthProperty(), 2);
        KeyFrame kf_circle_strkewidth = new KeyFrame(Duration.seconds(0.3), kv_circle_strkewidth);
        lineTimeline.getKeyFrames().add(kf_circle_strkewidth);

        // Create KeyFrames for each line
        for (int i = 0; i <= 5; i++) {
            Line line = (Line) outerCirclePane.getChildren().get(i);
            KeyValue kv = new KeyValue(line.strokeProperty(), Color.web("#cf2e31"));
            KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
            KeyValue kv_strkewidth = new KeyValue(line.strokeWidthProperty(), 2);
            KeyFrame kf_strkewidth = new KeyFrame(Duration.seconds(0.3), kv_strkewidth);
            lineTimeline.getKeyFrames().add(kf_strkewidth);
            lineTimeline.getKeyFrames().add(kf);
        }

        // Play the animations
        rotateTransition.play();
        strokeTransition.play();
        lineTimeline.play();
    }

    @FXML
    private void onCentercircleHoverExited() {
        // Create a RotateTransition object
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.3), outerCirclePane);

        // Set the axis of rotation to Z-axis
        rotateTransition.setAxis(Rotate.Z_AXIS);

        // Set the angle of rotation to 45 degrees
        rotateTransition.setByAngle(-45);

        // Create a StrokeTransition object for the circle
        Circle circle = (Circle) outerCirclePane.getChildren().get(6);
        StrokeTransition strokeTransition = new StrokeTransition(Duration.seconds(0.3), circle);
        strokeTransition.setToValue(Color.BLACK);

        // Create a StrokeTransition object for the lines
        for (int i = 0; i <= 5; i++) {
            Line line = (Line) outerCirclePane.getChildren().get(i);
            StrokeTransition lineStrokeTransition = new StrokeTransition(Duration.seconds(0.3), line);
            lineStrokeTransition.setToValue(Color.BLACK);
            lineStrokeTransition.play();
        }

        // Play the animations
        rotateTransition.play();
        strokeTransition.play();
    }
}
