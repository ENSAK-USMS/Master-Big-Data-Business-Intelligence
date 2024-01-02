/**
 * @author abdobella
 * Date: 24 déc. 2023
 * Time: 11:43:59
*/
package com.nfs.app.controllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.CheckComboBox;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.matrix.Matrix;

import java.util.ArrayList;

public class DataSetVisualizerController {

    @FXML
    private AnchorPane anchorPane;

    private Instances dataSet;
    private ArrayList<Attribute> attributesToDisplay = new ArrayList<>();
    private Instances modifiedDataSetToDisplay;

    @FXML
    private CheckComboBox<String> attrComboBox;

    @FXML
    private GridPane heatMapGrid;

    @FXML
    private VBox attrVboxList;

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    public void addInstancesGrid() {
        modifiedDataSetToDisplay = new Instances("EmptyDataSet", new ArrayList<>(), 0);
        fillModifiedDataset();

        int gridSize = 8;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (i < modifiedDataSetToDisplay.numAttributes() && j < modifiedDataSetToDisplay.numAttributes()) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth((heatMapGrid.getPrefWidth() / gridSize) - 5);
                    rectangle.setHeight((heatMapGrid.getPrefHeight() / gridSize) - 5);
                    String hexColor = getColorHeatMap(i, j);
                    rectangle.setStyle("-fx-fill: " + hexColor + ";");
                    heatMapGrid.add(rectangle, i, j);
                    GridPane.setHalignment(rectangle, javafx.geometry.HPos.CENTER);
                }
            }
        }
        heatMapGrid.setGridLinesVisible(true);
    }

    private void fillModifiedDataset() {
        for (Attribute attribute : attributesToDisplay) {
            modifiedDataSetToDisplay.insertAttributeAt(attribute, modifiedDataSetToDisplay.numAttributes());
        }
        for (int i = 0; i < dataSet.numInstances(); i++) {
            weka.core.DenseInstance instance = new weka.core.DenseInstance(modifiedDataSetToDisplay.numAttributes());
            instance.setDataset(modifiedDataSetToDisplay);
            for (int j = 0; j < attributesToDisplay.size(); j++) {
                instance.setValue(j, dataSet.instance(i).value(attributesToDisplay.get(j)));
            }
            modifiedDataSetToDisplay.add(instance);
        }
    }

    private String getColorHeatMap(int i, int j) {
        Matrix correlation = calculateCovarianceMatrix(modifiedDataSetToDisplay);
        double correlationValue = correlation.get(i, j);
        correlationValue = Math.max(-1.0, Math.min(1.0, correlationValue));
        double red = 32;
        double blue = 209;
        double green = convertNumber((int) correlationValue);
        Color color = Color.rgb((int) red, (int) green, (int) blue);
        if (i==j) {
            // #18319e
            color = Color.rgb(24, 49, 158);
        }
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static int convertNumber(int input) {
        int x1 = -1;
        int y1 = 125;
        int x2 = 1;
        int y2 = 65;
        double slope = (double) (y2 - y1) / (x2 - x1);
        double yIntercept = y1 - slope * x1;
        return (int) Math.round(slope * input + yIntercept);
    }

    public static Matrix calculateCovarianceMatrix(Instances data) {
        int numAttributes = data.numAttributes();
        int numInstances = data.numInstances();
        double[] meanValues = new double[numAttributes];
        for (int i = 0; i < numAttributes; i++) {
            for (int j = 0; j < numInstances; j++) {
                meanValues[i] += data.instance(j).value(i);
            }
            meanValues[i] /= numInstances;
        }
        Matrix covarianceMatrix = new Matrix(numAttributes, numAttributes);
        for (int i = 0; i < numAttributes; i++) {
            for (int j = i; j < numAttributes; j++) {
                double covariance = 0;
                for (int k = 0; k < numInstances; k++) {
                    covariance += (data.instance(k).value(i) - meanValues[i])
                            * (data.instance(k).value(j) - meanValues[j]);
                }
                covariance /= (numInstances - 1);
                covarianceMatrix.set(i, j, covariance);
                covarianceMatrix.set(j, i, covariance);
            }
        }
        return covarianceMatrix;
    }

    @FXML
    public void closeDataSetVisualizer() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }

    public void initAttrComboBox() {
        for (int i = 0; i < dataSet.numAttributes(); i++) {
            attrComboBox.getItems().add(dataSet.attribute(i).name());
        }
        attrComboBox.getCheckModel().checkIndices(0, 8);
        attrComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            attributesToDisplay.clear();
            attrVboxList.getChildren().clear();
            int index = 0;
            for (String attrName : attrComboBox.getCheckModel().getCheckedItems()) {
                Attribute attribute = dataSet.attribute(attrName);
                attributesToDisplay.add(attribute);
                Label label = new Label("     " + (index + 1) + " - " + attrName);
                label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #207dd1;");
                label.setPadding(new javafx.geometry.Insets(5, 0, 5, 0));
                attrVboxList.getChildren().add(label);
                index++;
            }
            heatMapGrid.getChildren().clear();
            addInstancesGrid();
            heatMapGrid.setGridLinesVisible(true);
        });
        attrComboBox.getCheckModel().clearChecks();
        attrComboBox.getCheckModel().checkIndices(0, 1, 2, 3, 4, 5, 6, 7);
        attributesToDisplay.clear();
        attrVboxList.getChildren().clear();
        int index = 0;
        for (String attrName : attrComboBox.getCheckModel().getCheckedItems()) {
            Attribute attribute = dataSet.attribute(attrName);
            attributesToDisplay.add(attribute);
            Label label = new Label("     " + (index + 1) + " - " + attrName);
            label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #207dd1;");
            label.setPadding(new javafx.geometry.Insets(5, 0, 5, 0));
            attrVboxList.getChildren().add(label);
            index++;
        }
        addInstancesGrid();
        heatMapGrid.setGridLinesVisible(true);
        // set percentages
        setChartPercentages(35,98,87,32);
        
    }

    private void setChartPercentages(int i, int j, int k, int l) {
        linPercentage("linePersantage", i);
        linPercentage("linePersantage1", j);
        linPercentage("linePersantage2", k);
        linPercentage("linePersantage3", l);
        circlePercentage("CirclePer", i);
        circlePercentage("CirclePer1", j);
        circlePercentage("CirclePer2", k);
        circlePercentage("CirclePer3", l);

    }

    public void linPercentage(String lineId,double percentage){
        // calc perstange coresponing to the line
        double linPercentage = 130*percentage/100;
        // get line from anchorPane
        Line line = (Line) anchorPane.lookup("#"+lineId);
        // set start x to start x + linepercentage
        line.setStartX(line.getStartX()+linPercentage);
        // get reverse line
        Line reverseLine = (Line) anchorPane.lookup("#"+lineId+"Reverse");
        // set end x to end x - linepercentage
        reverseLine.setEndX(reverseLine.getEndX()-130+linPercentage-4);
    }

    // circle percentage
    public void circlePercentage(String circleId,double percentage){
        // get line from anchorPane
        ProgressIndicator circle = (ProgressIndicator) anchorPane.lookup("#"+circleId);
        System.out.println(circleId);
        System.out.println(circle);
        circle.setProgress(percentage/100);
    }
}
