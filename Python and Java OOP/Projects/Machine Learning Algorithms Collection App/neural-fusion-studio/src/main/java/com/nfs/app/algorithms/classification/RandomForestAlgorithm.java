/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:05:06 PM
*/
package com.nfs.app.algorithms.classification;

import com.nfs.app.algorithms.Algorithm_Abstract;

import weka.classifiers.trees.RandomForest;

public class RandomForestAlgorithm extends Algorithm_Abstract {
    private RandomForest randomForest;

    public RandomForestAlgorithm() {
        randomForest = new RandomForest();
    }

    @Override
    public void setOptions(String options) throws Exception {
        // Set specific options here
        randomForest.setOptions(options.split(" "));
    }

    @Override
    public void evaluate() {
        try {
            // Build the random forest model
            randomForest.buildClassifier(dataset);

            // Print evaluation results
            System.out.println("Random Forest Evaluation Results:");
            System.out.println(evaluation.toSummaryString());

            // Get default parameters
            System.out.println("Default Parameters:");
            System.out.println(randomForest.getOptions().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDefaultOptions() {
        String[] options = randomForest.getOptions();
        String optionString = "";
        for (String option : options) {
            optionString += option + " ";
        }
        return optionString;
    }
}
