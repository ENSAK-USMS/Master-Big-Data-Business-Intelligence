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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.animation.RotateTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import weka.core.Instances;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;
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
    private ImageView datasetIcon;

    @FXML
    private Group dataSetSubCircleAnimation1, dataSetSubCircleAnimation2, dataSetSubCircleAnimation3,
            dataSetLoadedIcon;

    @FXML
    private ProgressIndicator dataSetOpenProgress;

    @FXML
    private HBox clasificationOptions,clustringOptions,optionalArgs;

    @FXML
    private TextField optionsField;

    @FXML
    private ComboBox<String> targetClassesOptions;

    private Instances dataSet;

    private Algorithm_Abstract trainingAlgorithm;

    private String selectedAlgorithm;

    private HashMap<String, Algorithm_Abstract> algorithms = new HashMap<String, Algorithm_Abstract>();


    @FXML
    private void showClasificationOptions() {
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        // if optional args is visible hide it
        if(optionalArgs.isVisible()){
            optionalArgs.setVisible(false);
        }
        // if already selected hide the option
        if(clasificationOptions.isVisible()){
            clasificationOptions.setVisible(false);
            return;
        }
        clasificationOptions.setVisible(true);
        clustringOptions.setVisible(false);
    }

    @FXML
    private void showClustringOptions() {
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        // if optional args is visible hide it
        if(optionalArgs.isVisible()){
            optionalArgs.setVisible(false);
        }
        // if already selected hide the option
        if(clustringOptions.isVisible()){
            clustringOptions.setVisible(false);
            return;
        }
        clustringOptions.setVisible(true);
        clasificationOptions.setVisible(false);
    }


    @FXML
    private void selectAlgorithm(ActionEvent event) {
        // get the text of the button
        String algorithmName = ((Button) event.getSource()).getText();
        // if selected algorithm isnt null and is the same as the selected algorithm hide the optional args
        if(selectedAlgorithm != null && selectedAlgorithm.equals(algorithmName)){
            showOptionalArgs(event);
            return;
        }
        // if another algorithm is selected hide the optional args
        if(selectedAlgorithm != null && !selectedAlgorithm.equals(algorithmName)){
            optionalArgs.setVisible(false);
        }
        // set the selected algorithm
        selectedAlgorithm = algorithmName;

        showOptionalArgs(event);
        optionsField.setText(algorithms.get(selectedAlgorithm).getDefaultOptions());
    }


    private void showOptionalArgs(ActionEvent event) {

        // if already selected hide the option
        if(optionalArgs.isVisible()){
            optionalArgs.setVisible(false);
            return;
        }
        optionalArgs.setVisible(true);
    }

    @FXML
    private void setTrainingAlgorithm(){
        // get selected algorithm
        if(selectedAlgorithm == null){
            return;
        }
        // get the algorithm from the hashmap
        trainingAlgorithm = algorithms.get(selectedAlgorithm);
        // set the options
        try {
            trainingAlgorithm.setOptions(optionsField.getText());
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
        // hide optional args and algorithm options
        optionalArgs.setVisible(false);
        clasificationOptions.setVisible(false);

    }

    // on start get the selected algorithm and the optional args and run the algorithm
    @FXML
    private void runAlgorithm() {
        // check if a dataset is loaded and an algorithm is selected and the optional args are set
        if(!checkIfDataSetIsLoaded() || trainingAlgorithm == null){
            return;
        }

        

        // get the selected algorithm
        try {
            System.out.println(selectedAlgorithm);
            // get the optional args
            System.out.println(trainingAlgorithm.getDefaultOptions());

            trainingAlgorithm.setup(dataSet);
            trainingAlgorithm.setClassAttribute(targetClassesOptions.getValue());
            trainingAlgorithm.evaluate();
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
    }

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
                                loadAlgorithms();
                                setTargetClassesOptions();
                            });
                loadDataSetTask.setOnFailed(event -> {
                    dataSetOpenProgress.setVisible(false);
                });
                new Thread(loadDataSetTask).start();
                // TODO: create some animation showing that the data is loaded
                // System.out.println(dashboardAnchorPane);                
            } catch (Exception e) {
                App.showExceptionWindow(e);
            }

        } else {
            System.out.println("No file selected");
            // Handle the case where the user canceled the file selection
        }
    }
    private void loadAlgorithms() {
        algorithms.put("Linear Regression", new com.nfs.app.algorithms.classification.LinearRegressionAlgorithm());
        algorithms.put("Logistic Regression", new com.nfs.app.algorithms.classification.LogisticRegressionAlgorithm());
        algorithms.put("Decision Tree", new com.nfs.app.algorithms.classification.DecisionTreeAlgorithm());
        algorithms.put("Random Forest", new com.nfs.app.algorithms.classification.RandomForestAlgorithm());
        // algorithms.put("K-Means", new com.nfs.app.algorithms.clustering.KMeansAlgorithm());
        // algorithms.put("DBSCAN", new com.nfs.app.algorithms.clustering.DBSCANAlgorithm());
        // algorithms.put("Hierarchical Based", new com.nfs.app.algorithms.clustering.HierarchicalBasedAlgorithm());
        // algorithms.put("Density Based", new com.nfs.app.algorithms.clustering.DensityBasedAlgorithm()); 
    }

    private void setTargetClassesOptions() {
        targetClassesOptions.getItems().clear();
        for (int i = 0; i < dataSet.numAttributes(); i++) {
            targetClassesOptions.getItems().add(dataSet.attribute(i).name());
        }
    }




    @FXML 
    private void showDataSetEditPage(){
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/data-set-edit.fxml"));
            Parent dataSetEditPage = loader.load();
            DataSetEditController dataSetEditController = loader.getController();
            
            // Now you can access the methods or properties of the DataSetEditController
            dataSetEditController.createTable(dataSet);
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetEditPage);
        } catch (IOException e) {
            App.showExceptionWindow(e);
        }
    }

    

    @FXML
    private void showDatasetFiltersPage() {
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/filters.fxml"));
            Parent dataSetFiltersPage = loader.load();
            DataSetFiltersController dataSetFiltersController = loader.getController();
            
            // // Now you can access the methods or properties of the DataSetFiltersController
            dataSetFiltersController.setDataSet(dataSet);
            dataSetFiltersController.onInit();
            dataSetFiltersController.addInstancesGrid();
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetFiltersPage);
        } catch (IOException e) {
            App.showExceptionWindow(e);
        }
    }

    @FXML
    private void showDatasetVisualizerPage() {
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/visualizer.fxml"));
            Parent dataSetVisualizerPage = loader.load();
            DataSetVisualizerController dataSetVisualizerController = loader.getController();
            
            // // Now you can access the methods or properties of the DataSetVisualizerController
            dataSetVisualizerController.setDataSet(dataSet);
            dataSetVisualizerController.addInstancesGrid();
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetVisualizerPage);
        } catch (IOException e) {
            App.showExceptionWindow(e);
        }
    }
    
































































    // i want to change the possition of the circle in animation when the mouse is hovering outside the circle

    private Boolean checkIfDataSetIsLoaded() {
        if (dataSet == null) {
            // Bounce the dataset icon
            animateElement(datasetIcon, 0, -20, 0.1);
            animateElement(datasetIcon, 0, 0, 0.2);
            animateElement(datasetIcon, 0, -10, 0.3);
            animateElement(datasetIcon, 0, 0, 0.4);
            return false;
        }
        return true;
    }

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
