/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 5:04:29 PM
*/
package com.nfs.app.algorithms.classification;

import com.nfs.app.algorithms.Algorithm_Abstract;

import weka.classifiers.trees.RandomTree;

public class DecisionTreeAlgorithm extends Algorithm_Abstract {
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

            // Print evaluation results
            System.out.println("Decision Tree Evaluation Results:");
            System.out.println(evaluation.toSummaryString());

            // Get default parameters
            System.out.println("Default Parameters:");
            System.out.println(randomTree.getOptions().toString());
        } catch (Exception e) {
            e.printStackTrace();
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
}

