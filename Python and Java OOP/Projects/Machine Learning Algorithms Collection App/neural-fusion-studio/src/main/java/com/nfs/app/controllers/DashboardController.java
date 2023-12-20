/**
 * @author abdobella
 * Date: Dec 10, 2023
 * Time: 1:36:27 AM
*/
package com.nfs.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.animation.RotateTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import weka.core.Instances;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.IOException;

import com.nfs.app.App;
import com.nfs.app.preprocessing.DataImportation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class DashboardController {

    @FXML
    private AnchorPane dashboardAnchorPane;
    
    @FXML
    private Pane outerCirclePane;

    @FXML
    private Group dataSetSubCircleAnimation1, dataSetSubCircleAnimation2, dataSetSubCircleAnimation3,
            dataSetLoadedIcon;

    @FXML
    private ProgressIndicator dataSetOpenProgress;

    private Instances dataSet;


    // create a function to let the user load a dataset allowed types are csv and arff funton name openDataSet
    @FXML
    private void openDataSet() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Dataset");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads")); // Set initial directory to the download folder
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
            new FileChooser.ExtensionFilter("ARFF Files", "*.arff")
        );
        
        Stage stage = (Stage) outerCirclePane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            dataSetOpenProgress.setVisible(true);
            // The user selected a file, you can perform further actions with the file here
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            String fileType = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            String filePath = selectedFile.getAbsolutePath();
            try {
                Task<Void> loadDataSetTask = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    dataSet = DataImportation.getDataAsInstances(fileType, filePath);
                                    return null;
                                }
                            };

                            loadDataSetTask.setOnSucceeded(event -> {
                                dataSetLoadedIcon.setVisible(true);
                                dataSetOpenProgress.setVisible(false);
                            });
                loadDataSetTask.setOnFailed(event -> {
                    dataSetOpenProgress.setVisible(false);
                });
                new Thread(loadDataSetTask).start();
                // TODO: create some animation showing that the data is loaded
                // System.out.println(dashboardAnchorPane);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No file selected");
            // Handle the case where the user canceled the file selection
        }
    }

    @FXML 
    private void showDataSetEditPage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/data-set-edit.fxml"));
            Parent dataSetEditPage = loader.load();
            DataSetEditController dataSetEditController = loader.getController();
            
            // Now you can access the methods or properties of the DataSetEditController
            dataSetEditController.createTable(dataSet);
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetEditPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDatasetFiltersPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/filters.fxml"));
            Parent dataSetFiltersPage = loader.load();
            DataSetFiltersController dataSetFiltersController = loader.getController();
            
            // // Now you can access the methods or properties of the DataSetFiltersController
            // dataSetFiltersController.createTable(dataSet);
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetFiltersPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
































































    // i want to change the possition of the circle in animation when the mouse is hovering outside the circle

    @FXML
    private void onTestCircleHoverEntered() {
        // Create a TranslateTransition object
        animateElement(dataSetSubCircleAnimation1, -75, -75);
        animateElement(dataSetSubCircleAnimation2, -65-((Circle) dataSetSubCircleAnimation2.getChildren().get(0)).getRadius()*2, 0,0.125);
        animateElement(dataSetSubCircleAnimation3, -75, 75,0.25);
    }
    
    
    
    @FXML
    private void onTestCircleHoverExited() {
        // Create a TranslateTransition object
        animateElement(dataSetSubCircleAnimation1, 0, 0);
        animateElement(dataSetSubCircleAnimation2, 0, 0,0.125);
        animateElement(dataSetSubCircleAnimation3, 0, 0,0.25);
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
