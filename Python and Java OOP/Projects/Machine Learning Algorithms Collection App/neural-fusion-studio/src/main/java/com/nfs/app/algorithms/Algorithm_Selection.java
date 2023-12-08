/**
 * @author abdobella
 * Date: Dec 02, 2023
 * Time: 3:00:57 PM
*/

package com.nfs.app.algorithms;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.RandomTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class Algorithm_Selection {

    public static void main(String[] args) {
        try {
            // Load your dataset
            Instances dataset = loadDataset("src/main/java/com/nfs/app/preprosessing_tests/vote.arff");

            // Linear Regression
            // linearRegression(dataset);

            // Polynomial Regression
            logisticRegression(dataset);

            // Decision Tree
            decisionTree(dataset);

            // Random Forest
            randomForest(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean hasMissingValue(Instances dataset) {
        for (int i = 0; i < dataset.numInstances(); i++) {
            Instance instance = dataset.instance(i);
            for (int j = 0; j < instance.numAttributes(); j++) {
                if (instance.isMissing(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int[] getAttributeIndices(Instances instances,String[] attributeNames) {
        int[] attributeIndices = new int[attributeNames.length];

        for (int i = 0; i < attributeNames.length; i++) {
            Attribute attribute = instances.attribute(attributeNames[i]);
            if (attribute != null) {
                attributeIndices[i] = attribute.index();
            } else {
                throw new IllegalArgumentException("Attribute not found: " + attributeNames[i]);
            }
        }

        return attributeIndices;
    }

    private static Instances loadDataset(String path) throws Exception {
        // Implement code to load your dataset using Weka
        return ConverterUtils.DataSource.read(path);
    }

    public static void linearRegression(Instances dataset) {
        try {
            // check if there is only two attributes
            if (dataset.numAttributes() != 2) {
                throw new IllegalArgumentException("Dataset should have only two attributes.");
            }
            // get x attr in a array of double
            double[] x = dataset.attributeToDoubleArray(0);
            // get y attr in a array of double
            double[] y = dataset.attributeToDoubleArray(1);

            LinearRegression linearRegression = new LinearRegression(x, y);

            // Print the linear regression model
            System.out.println("Linear Regression Model:");
            System.out.println("y = " + linearRegression.intercept() + " + " + linearRegression.slope() + " * x");
            System.out.println("R^2 = " + linearRegression.R2());
            
        } catch (Exception e) {
            System.out.println("Failed to evaluate linear regression model. \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void logisticRegression(Instances dataset) throws Exception {
        try {
            dataset.setClassIndex(dataset.numAttributes() - 1);
            // Create and build the polynomial regression model
            Logistic logistic = new Logistic(); 
            logistic.buildClassifier(dataset);

            // Evaluate the model
            Evaluation eval = new Evaluation(dataset);
            eval.evaluateModel(logistic, dataset);

            // Print evaluation results
            System.out.println("Logistic Regression Evaluation Results:");
            System.out.println(eval.toSummaryString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decisionTree(Instances dataset) throws Exception {
        try {

            if(hasMissingValue(dataset)){
                throw new Exception("Please Handle The missing values First");
            }
            
            dataset.setClassIndex(dataset.numAttributes() - 1);
            // Create and build the decision tree model
            RandomTree randomTree = new RandomTree();
            randomTree.buildClassifier(dataset);

            // Evaluate the model
            Evaluation eval = new Evaluation(dataset);
            eval.evaluateModel(randomTree, dataset);

            // Print evaluation results
            System.out.println("Decision Tree Evaluation Results:");
            System.out.println(eval.toSummaryString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void randomForest(Instances dataset) throws Exception {
        try {

            if(hasMissingValue(dataset)){
                throw new Exception("Please Handle The missing values First");
            }
            
            dataset.setClassIndex(dataset.numAttributes() - 1);
            // Create and build the random forest model
            RandomForest randomForest = new RandomForest();
            randomForest.buildClassifier(dataset);

            // Evaluate the model
            Evaluation eval = new Evaluation(dataset);
            eval.evaluateModel(randomForest, dataset);

            // Print evaluation results
            System.out.println("Random Forest Evaluation Results:");
            System.out.println(eval.toSummaryString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
