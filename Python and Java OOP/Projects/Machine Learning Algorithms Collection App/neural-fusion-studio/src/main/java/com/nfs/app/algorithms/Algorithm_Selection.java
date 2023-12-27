/**
 * @author abdobella
 * Date: Dec 02, 2023
 * Time: 3:00:57 PM
*/

package com.nfs.app.algorithms;

import com.nfs.app.App;
import com.nfs.app.algorithms.classification.DecisionTreeAlgorithm;
import com.nfs.app.algorithms.classification.LinearRegressionAlgorithm;
import com.nfs.app.algorithms.classification.LogisticRegressionAlgorithm;
import com.nfs.app.algorithms.classification.RandomForestAlgorithm;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class Algorithm_Selection {

    public static void main(String[] args) {
        try {
            // Load your dataset
            Instances dataset = loadDataset("src/main/java/com/nfs/app/preprosessing_tests/vote.arff");

            // Linear Regression
            LinearRegressionAlgorithm linearRegressionAlgorithm = new LinearRegressionAlgorithm();
            linearRegressionAlgorithm.setup(dataset);
            linearRegressionAlgorithm.setOptions("");
            linearRegressionAlgorithm.evaluate();

            // Logistic Regression
            LogisticRegressionAlgorithm logisticRegressionAlgorithm = new LogisticRegressionAlgorithm();
            logisticRegressionAlgorithm.setup(dataset);
            logisticRegressionAlgorithm.setOptions("");
            logisticRegressionAlgorithm.evaluate();

            // Decision Tree
            DecisionTreeAlgorithm decisionTreeAlgorithm = new DecisionTreeAlgorithm();
            decisionTreeAlgorithm.setup(dataset);
            decisionTreeAlgorithm.setOptions("");
            decisionTreeAlgorithm.evaluate();

            // Random Forest
            RandomForestAlgorithm randomForestAlgorithm = new RandomForestAlgorithm();
            randomForestAlgorithm.setup(dataset);
            randomForestAlgorithm.setOptions("");
            randomForestAlgorithm.evaluate();
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
    }

    private static Instances loadDataset(String path) throws Exception {
        // Implement code to load your dataset using Weka
        return ConverterUtils.DataSource.read(path);
    }  
}
