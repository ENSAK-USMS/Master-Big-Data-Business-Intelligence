/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:01:44 PM
*/
package com.nfs.app.algorithms.classification;
import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;

public class LinearRegressionAlgorithm extends Algorithm_Abstract {
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
}

