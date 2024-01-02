package com.nfs.app.preprocessing;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class PreprocessingTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy dataset for testing
        Instances instances = createDummyDataset();

        // Test removeAttributes(String[] attributeNames)
        String[] attributeNamesToRemove = {"mx-missile", "immigration"};
        Preprocessing preprocessing = new Preprocessing(instances);
        // preprocessing.removeAttributes(attributeNamesToRemove);
        System.out.println("Attributes removed successfully.");
        // save copy of the dataset
        saveInstancesToArffFile(instances, "original");
        saveInstancesToArffFile(RemoveDuplicates.removeDuplicates(instances), "remove-duplicates");

    }

    private static Instances createDummyDataset() throws Exception {
        // Create a dummy dataset for testing
        Instances instances = DataImportation.getDataAsInstances("csv", "C:/Users/abdob/Downloads/penguins_binary_classification.csv");

        // Add attributes and instances to the dataset

        return instances;
    }
    private static void saveInstancesToArffFile(Instances instances, String filename) throws Exception {
                ArffSaver saver = new ArffSaver();
                saver.setInstances(instances);
                saver.setFile(new File("C:/Git-Hub/Master-Big-Data-Business-Intelligence/Python and Java OOP/Projects/Machine Learning Algorithms Collection App/neural-fusion-studio/src/main/java/com/nfs/app/preprosessing_tests/"+filename+".arff"));
                saver.writeBatch();
            }
}