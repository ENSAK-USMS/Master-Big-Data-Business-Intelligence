/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:01:44 PM
*/
package com.nfs.app.algorithms.classification;
import java.io.Serializable;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

import javafx.scene.control.Label;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;

public class LinearRegressionAlgorithm extends Algorithm_Abstract implements Serializable {
    private LinearRegression linearRegression;


    public LinearRegressionAlgorithm() {
        linearRegression = new LinearRegression();
    }

    @Override
    public void setOptions(String options) throws Exception {
        // Set specific options here
        linearRegression.setOptions(options.split(" "));
    }

    @Override
    public void evaluate(){
        try {
            // Build the linear regression model
            linearRegression.buildClassifier(dataset);
            
            // Initialize evaluation
            evaluation = new Evaluation(dataset);

            // Evaluate the model
            evaluation.evaluateModel(linearRegression, dataset);


            

            // Print evaluation results
            System.out.println("Linear Regression Evaluation Results:");
            System.out.println(evaluation.toSummaryString());

            // Get default parameters
            System.out.println("Default Parameters:");
            System.out.println(linearRegression.getOptions().toString());
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
    }

    @Override
    public String getDefaultOptions() {
        String[] options = linearRegression.getOptions();
        String optionString = "";
        for (String option : options) {
            optionString += option + " ";
        }
        return optionString;
    }

    @Override
    public Evaluation getEvaluationResults() {
        return evaluation;
    }
    
    @Override
    public String getName() {
        // get the object name
        String objectName = this.getClass().getName();
        // get the last index of the dot
        int lastIndexOfDot = objectName.lastIndexOf(".");
        // get the name
        String name = objectName.substring(lastIndexOfDot + 1);
        return name;
    }

    @Override
    public String getAccuracy() {
        return "Not Available";
    }
    // get other results
    public GridPane getOtherResults() {
        GridPane gridPane = new GridPane();
        try {
            // Create and set labels for each result
            Label correlationLabel = new Label("Correlation coefficient: " + String.format("%.2f", evaluation.correlationCoefficient()));
            Label meanAbsoluteErrorLabel = new Label("Mean absolute error: " + String.format("%.2f", evaluation.meanAbsoluteError()));
            Label rootMeanSquaredErrorLabel = new Label("Root mean squared error: " + String.format("%.2f", evaluation.rootMeanSquaredError()));
            Label relativeAbsoluteErrorLabel = new Label("Relative absolute error: " + String.format("%.2f", evaluation.relativeAbsoluteError()) + " %");
            Label rootRelativeSquaredErrorLabel = new Label("Root relative squared error: " + String.format("%.2f", evaluation.rootRelativeSquaredError()));
            Label numInstancesLabel = new Label("Total Number of Instances: " + evaluation.numInstances());

            int labelWidth = 300;
            correlationLabel.setPrefWidth(labelWidth);
            meanAbsoluteErrorLabel.setPrefWidth(labelWidth);
            rootMeanSquaredErrorLabel.setPrefWidth(labelWidth);
            relativeAbsoluteErrorLabel.setPrefWidth(labelWidth);
            rootRelativeSquaredErrorLabel.setPrefWidth(labelWidth);
            numInstancesLabel.setPrefWidth(labelWidth);

            // Apply style to the labels
            String labelStyle = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #aba5a5;padding: 5px 0px 5px 0px;";
            correlationLabel.setStyle(labelStyle);
            meanAbsoluteErrorLabel.setStyle(labelStyle);
            rootMeanSquaredErrorLabel.setStyle(labelStyle);
            relativeAbsoluteErrorLabel.setStyle(labelStyle);
            rootRelativeSquaredErrorLabel.setStyle(labelStyle);
            numInstancesLabel.setStyle(labelStyle);

            // Add labels to the grid pane
            gridPane.add(correlationLabel, 0, 0);
            gridPane.add(meanAbsoluteErrorLabel, 1, 0);
            gridPane.add(rootMeanSquaredErrorLabel, 0, 1);
            gridPane.add(relativeAbsoluteErrorLabel, 1, 1);
            gridPane.add(rootRelativeSquaredErrorLabel, 0, 2);
            gridPane.add(numInstancesLabel, 1, 2);
        } catch (Exception e) {
            e.printStackTrace();
            Label notAvailableLabel = new Label("Not Available");
            notAvailableLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            gridPane.add(notAvailableLabel, 0, 0);
        }
        return gridPane;
    }
}

