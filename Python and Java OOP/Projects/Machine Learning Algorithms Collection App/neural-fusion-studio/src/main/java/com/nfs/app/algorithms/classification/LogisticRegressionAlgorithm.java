/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:03:28 PM
*/
package com.nfs.app.algorithms.classification;

import java.io.Serializable;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;

public class LogisticRegressionAlgorithm extends Algorithm_Abstract implements Serializable {
    private Logistic logistic;

    public LogisticRegressionAlgorithm() {
        logistic = new Logistic();
    }

    @Override
    public void setOptions(String options) throws Exception {
        // Set specific options here
        logistic.setOptions(options.split(" "));
    }

    @Override
    public void evaluate() {
        try {
            // Build the logistic regression model
            logistic.buildClassifier(dataset);

            // Initialize evaluation
            evaluation = new Evaluation(dataset);

            // Evaluate the model
            evaluation.evaluateModel(logistic, dataset);

            // Print evaluation results
            System.out.println("Logistic Regression Evaluation Results:");
            System.out.println(evaluation.toSummaryString());

            // Get default parameters
            System.out.println("Default Parameters:");
            System.out.println(logistic.getOptions().toString());
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
    }

    @Override
    public String getDefaultOptions() {
        String[] options = logistic.getOptions();
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
}

