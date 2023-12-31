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
        preprocessing.removeAttributes(attributeNamesToRemove);
        System.out.println("Attributes removed successfully.");

        
        // Test ReplaceMissingValuesWithMean()
        // try {
        //     preprocessing.ReplaceMissingValuesWithMean();
        //     System.out.println("Missing values replaced with mean successfully.");
        // } catch (Exception e) {
        //     System.out.println("Failed to replace missing values with mean: " + e.getMessage());
        // }

        // Test removeInstancesWithNullValue(String attributeName)
        String attributeNameToRemove = "education-spending";
        preprocessing.removeInstancesWithNullValue(attributeNameToRemove);
        System.out.println("Instances with null value removed successfully.");

        // Test nominalToBinary(String attributeName)
        String attributeNameToConvert = "education-spending";
        try {
            preprocessing.nominalToBinary(attributeNameToConvert);
            System.out.println("Nominal attribute converted to binary successfully.");
        } catch (Exception e) {
            System.out.println("Failed to convert nominal attribute to binary: " + e.getMessage());
        }

        // Test convertStringToNominal()
        try {
            preprocessing.convertStringToNominal();
            System.out.println("String attributes converted to nominal successfully.");
        } catch (Exception e) {
            System.out.println("Failed to convert string attributes to nominal: " + e.getMessage());
        }

        // Test removeAttributes(int[] indices)
        int[] indicesToRemove = {0, 1};
        try {
            preprocessing.removeAttributes(indicesToRemove);
            System.out.println("Attributes removed successfully.");
        } catch (Exception e) {
            System.out.println("Failed to remove attributes: " + e.getMessage());
        }

        // Test nominalToBinary(String[] attributeNames)
        String[] attributeNamesToConvert = {"uperfund-right-to-sue", "crime"};
        try {
            preprocessing.nominalToBinary(attributeNamesToConvert);
            System.out.println("Nominal attributes converted to binary successfully.");
        } catch (Exception e) {
            System.out.println("Failed to convert nominal attributes to binary: " + e.getMessage());
        }

        // Test normalizeAttribute(String attributeName)
        String attributeNameToNormalize = "duty-free-exports";
        try {
            preprocessing.normalizeAttribute(attributeNameToNormalize);
            System.out.println("Attribute normalized successfully.");
        } catch (Exception e) {
            System.out.println("Failed to normalize attribute: " + e.getMessage());
        }

        // Test normalizeAttribute(String[] attributeNames)
        String[] attributeNamesToNormalize = {"attribute8", "attribute9"};
        try {
            preprocessing.normalizeAttribute(attributeNamesToNormalize);
            System.out.println("Attributes normalized successfully.");
        } catch (Exception e) {
            System.out.println("Failed to normalize attributes: " + e.getMessage());
        }

        System.out.println("Preprocessing tests completed successfully. the new dataset is:");
        System.out.println(preprocessing.getInstances().toSummaryString());
        // save the new dataset to a file
        Instances newDataset = preprocessing.getInstances();
        ArffSaver saver = new ArffSaver();
        saver.setInstances(newDataset);
        saver.setFile(new File("C:\\Git-Hub\\Master-Big-Data-Business-Intelligence\\Python and Java OOP\\Projects\\Machine Learning Algorithms Collection App\\n" + //
                "eural-fusion-studio\\src\\main\\java\\com\\n" + //
                "fs\\app\\preprosessing_tests\\newDataset.arff"));
        saver.writeBatch();
    }

    private static Instances createDummyDataset() throws Exception {
        // Create a dummy dataset for testing
        Instances instances = DataImportation.getDataAsInstances("csv", "C:\\Git-Hub\\Master-Big-Data-Business-Intelligence\\Python and Java OOP\\Projects\\Machine Learning Algorithms Collection App\\n" + //
                "eural-fusion-studio\\src\\main\\java\\com\\n" + //
                "fs\\app\\preprosessing_tests\\dataset_57_hypothyroid.csv");

        // Add attributes and instances to the dataset

        return instances;
    }
}