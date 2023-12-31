/**
 * @author abdobella
 * Date: 24 déc. 2023
 * Time: 11:43:59
*/
package com.nfs.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import weka.core.Instances;
import weka.core.matrix.Matrix;


public class DataSetVisualizerController {

    private Instances dataSet;

    @FXML
    private GridPane heatMapGrid;

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    public void addInstancesGrid() {
        // for the first 8 attributes we want to create a heatmap loop over the first 8 attributes for 8 attributes and add a rectangle to the grid
        for (int i = 0; i < 7; i++) {
            // for each attribute we want to create a rectangle
            for (int j = 0; j < 7; j++) {
                Rectangle rectangle = new Rectangle();
                // set size to grid cell size
                rectangle.setWidth((heatMapGrid.getPrefWidth() / 7) - 5);
                rectangle.setHeight((heatMapGrid.getPrefHeight() / 7) - 5);
                // add border
                // rectangle.setStroke(Color.BLACK);
                // rectangle.setStrokeWidth(1);
                // call a function to get the colerlation between the two attributes
                String hexColor = getColorHeatMap(i, j);
                // set the color of the rectangle
                rectangle.setStyle("-fx-fill: " + hexColor + ";");
                // add the rectangle to the grid
                heatMapGrid.add(rectangle, i, j);
                // center the rectangle
                GridPane.setHalignment(rectangle, javafx.geometry.HPos.CENTER);

            }
        }
    }

    private String getColorHeatMap(int i, int j) {
        Instances data = dataSet;
        // calculate the correlation between the two attributes
        Matrix correlation = calculateCovarianceMatrix(data);
        // get the correlation value
        double correlationValue = correlation.get(i, j);
        

        // map the number to a color between blue and white
        correlationValue = Math.max(-1.0, Math.min(1.0, correlationValue));

        // 70 125
        // put the number between 70 and 125
        // with 70 being 1 and 125 being -1
        double red = 32;
        double blue = 209;
        // get the corelation value 
        double green = convertNumber((int) (correlationValue));

        // get color from blue to white 
        Color color = Color.rgb((int) red, (int) green, (int) blue);


        String hexColor = String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
        // return the color
        return hexColor;
    }
    
    public static int convertNumber(int input) {
        // Given correspondences
        int x1 = -1;
        int y1 = 125;
        int x2 = 1;
        int y2 = 65;

        // Calculate the slope (m) and y-intercept (b) for the linear equation
        double slope = (double) (y2 - y1) / (x2 - x1);
        double yIntercept = y1 - slope * x1;

        // Use the linear equation to find the output for the given input
        int output = (int) Math.round(slope * input + yIntercept);

        return output;
    }

    public static Matrix calculateCovarianceMatrix(Instances data) {
        int numAttributes = data.numAttributes();
        int numInstances = data.numInstances();

        // Calculate the mean vector
        double[] meanValues = new double[numAttributes];
        for (int i = 0; i < numAttributes; i++) {
            for (int j = 0; j < numInstances; j++) {
                meanValues[i] += data.instance(j).value(i);
            }
            meanValues[i] /= numInstances;
        }

        // Compute the covariance matrix
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

    // close the page
    @FXML
    public void closeDataSetVisualizer() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }
}
