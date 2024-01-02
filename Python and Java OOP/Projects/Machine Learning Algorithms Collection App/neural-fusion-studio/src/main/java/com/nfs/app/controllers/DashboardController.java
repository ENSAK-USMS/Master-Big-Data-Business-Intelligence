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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import javafx.scene.control.Label;

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

    @FXML
    private Pane rightSideBarPane;

    @FXML
    private ImageView rightSideBarIcon;

    @FXML
    private Pane activatePane;

    public static Instances dataSet;

    private Algorithm_Abstract trainingAlgorithm;

    private String selectedAlgorithm;

    private HashMap<String, Algorithm_Abstract> algorithms = new HashMap<String, Algorithm_Abstract>();

    private String filePath;



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
                    this.filePath = selectedFile.getAbsolutePath();
                    loadAlgorithms();
                    setTargetClassesOptions();
                });
                loadDataSetTask.setOnFailed(event -> {
                    dataSetOpenProgress.setVisible(false);
                    System.out.println("Failed to load dataset");
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
            dataSetVisualizerController.initAttrComboBox();
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(dataSetVisualizerPage);
        } catch (IOException e) {
            App.showExceptionWindow(e);
        }
    }
    

    
    
    @FXML
    private void showTrainingResultsPage() {
        if(!checkIfDataSetIsLoaded()){
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/dashboard/training-results.fxml"));
            Parent trainingResultsPage = loader.load();
            TrainingResultsController trainingResultsController = loader.getController();
            
            // // Now you can access the methods or properties of the TrainingResultsController
            trainingResultsController.setDataSet(dataSet);
            System.out.println(trainingAlgorithm.getName());
            // set the evaluation results
            System.out.println(trainingAlgorithm.getEvaluationResults().toSummaryString());
            trainingAlgorithm.setFilePath(this.filePath);
            trainingResultsController.setModel(trainingAlgorithm);
            trainingResultsController.setResult();
            BaseController.blurBasePage();
            BaseController.addPageToBasePane(trainingResultsPage);
        } catch (IOException e) {
            App.showExceptionWindow(e);
            e.printStackTrace();
        }
    }


    @FXML
    private void showRightSideBar(MouseEvent event) {


        // change the image of the icon
        rightSideBarIcon.setImage(App.loadImage("/com/nfs/app/images/right-menu-hover.png").getImage());


        // text color #343434 paddint top and bottom 10 padding
        String style = "-fx-text-fill: #737070; -fx-font-weight: bold; -fx-padding: 4 0 4 0;";

        // add vbox to the right side bar pane size 200 * 150
        VBox rightSideBarVBox = new VBox();
        rightSideBarVBox.setPrefSize(200, 150);
        // add 6 label with min size of 200 * 25
        Label label1 = new Label("PC INFO"); 
        label1.setStyle(style);
        // get user windows edition
        String osName = System.getProperty("os.name");
        Label label2 = new Label(osName);
        label2.setStyle(style);
        // prossessor info
        String prossessorInfo = "Intel(R) Core(TM) i5-7400 CPU @ 3.00GHz";
        Label label3 = new Label(prossessorInfo);
        label3.setStyle(style);
        // Gpu info
        String gpuInfo = "NVIDIA GeForce GTX 1050 Ti";
        Label label4 = new Label(gpuInfo);
        label4.setStyle(style);
        // ram info
        String ramInfo =  "7.7 GB";
        Label label5 = new Label(ramInfo);
        label5.setStyle(style);
        // more info
        Label label6 = new Label("More Info");
        // underline the text
        label6.setUnderline(true);
        // onhover change the color of the text
        label6.setOnMouseEntered(e -> {
            label6.setStyle("-fx-text-fill: #b73437; -fx-font-weight: bold; -fx-padding: 4 0 4 0;");
        });
        // on exit change the color of the text
        label6.setOnMouseExited(e -> {
            label6.setStyle(style);
        });
        label6.setStyle(style);
        // add labels to vbox
        rightSideBarVBox.getChildren().addAll(label1,label2,label3,label4,label5,label6);

        // vbox lyout 35 10
        rightSideBarVBox.setLayoutX(35);
        rightSideBarVBox.setLayoutY(10);
        // add vbox to the right side bar pane
        rightSideBarPane.getChildren().add(rightSideBarVBox);

        


        // Create a timeline for the animation
        Timeline timeline = new Timeline();

        // Create a KeyValue for changing the prefWidth property
        KeyValue prefWidthKeyValue = new KeyValue(rightSideBarPane.prefWidthProperty(), 290);

        // Create a KeyValue for changing the layoutX property
        KeyValue layoutXKeyValue = new KeyValue(rightSideBarPane.layoutXProperty(), -50);

        // Create a KeyFrame with the desired duration and key values
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.3), prefWidthKeyValue, layoutXKeyValue);

        // Add the key frame to the timeline
        timeline.getKeyFrames().add(keyFrame);

        // Play the timeline animation
        timeline.play();
    }

    @FXML
    private void hideRightSideBar(MouseEvent event) {

        // Create a timeline for the animation
        Timeline timeline = new Timeline();

        // Create a KeyValue for changing the prefWidth property
        KeyValue prefWidthKeyValue = new KeyValue(rightSideBarPane.prefWidthProperty(), 29);

        // Create a KeyValue for changing the layoutX property
        KeyValue layoutXKeyValue = new KeyValue(rightSideBarPane.layoutXProperty(), 174);

        // Create a KeyFrame with the desired duration and key values
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.3), prefWidthKeyValue, layoutXKeyValue);

        // Add the key frame to the timeline
        timeline.getKeyFrames().add(keyFrame);

        // Set the OnFinished event handler
        timeline.setOnFinished(e -> {
            // change the image of the icon
            rightSideBarIcon.setImage(App.loadImage("/com/nfs/app/images/right-menu.png").getImage());
            // remove the vbox from the right side bar pane
            rightSideBarPane.getChildren().remove(1);
        });

        // Play the timeline animation
        timeline.play();
    }

    @FXML
    private void showActivatePane(){
        animateElement(activatePane, -270, 0);
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
