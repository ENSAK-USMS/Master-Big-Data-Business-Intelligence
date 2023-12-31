/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:04:29 PM
*/
package com.nfs.app.algorithms.classification;

import java.io.Serializable;

import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomTree;

public class DecisionTreeAlgorithm extends Algorithm_Abstract implements Serializable {
    private RandomTree randomTree;

    public DecisionTreeAlgorithm() {
        randomTree = new RandomTree();
    }

    @Override
    public void setOptions(String options) throws Exception {
        // Set specific options here
        randomTree.setOptions(options.split(" "));
    }

    @Override
    public void evaluate() {
        try {
            // Build the decision tree model
            randomTree.buildClassifier(dataset);

            // Initialize evaluation
            Evaluation evaluation = new Evaluation(dataset);

            // Evaluate the model
            evaluation.evaluateModel(randomTree, dataset);

            // Print evaluation results
            System.out.println("Decision Tree Evaluation Results:");
            System.out.println(evaluation.toSummaryString());

            // Get default parameters
            System.out.println("Default Parameters:");
            System.out.println(randomTree.getOptions().toString());
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
    }

    @Override
    public String getDefaultOptions() {
        String[] options =  randomTree.getOptions();
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

