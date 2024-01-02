/**
 * Date: Dec 07, 2023
 * Time: 2:32:27 PM
 */
package com.nfs.app.preprocessing;

import weka.core.Instances;

import com.nfs.app.App;

import weka.core.Instance;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class MissingValues {
    /**
     * Removes instances with missing values in the specified attribute from the
     * given dataset.
     *
     * @param data          The dataset containing instances.
     * @param attributeName The name of the attribute with missing values.
     * @return The dataset with missing records removed.
     */
    public static Instances removeMissingRecords(Instances data, String attributeName) {
        try {
            // remove row with missing values in the specified attribute
            data.removeIf(instance -> instance.isMissing(data.attribute(attributeName)));
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }
        System.out.println("DATA \n " + data.toSummaryString());
        return data;
    }

    /**
     * Fills missing values in the specified attribute with the mean value of the
     * attribute.
     *
     * @param data          The dataset containing instances.
     * @param attributeName The name of the attribute with missing values.
     * @return The dataset with missing values filled using the mean value.
     */
    public static Instances fillMissingValuesWithMean(Instances data, String attributeName) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter to fill missing values on the specified attribute
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-A", String.valueOf(filteredData.attribute(attributeName).index() + 1)});
            filter.setInputFormat(filteredData);
            filter.setOptions(new String[]{"-S", String.valueOf(filteredData.attribute(attributeName).index() + 1), "-C", "last"});
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }
    


    /**
     * Fills missing values in the specified attribute with a custom value.
     *
     * @param data          The dataset containing instances.
     * @param attributeName The name of the attribute with missing values.
     * @param value         The custom value to fill missing values with.
     * @return The dataset with missing values filled using the custom value.
     */
    public static Instances fillMissingWithCustomValue(Instances data, String attributeName, double value) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);
        System.out.println("the custom value is "+value+ " and the attribute name is "+attributeName);

        // Apply the ReplaceMissingValues filter with custom value option to fill
        // missing values
        try {
            ReplaceMissingWithUserConstant filter = new ReplaceMissingWithUserConstant();
            filter.setOptions(new String[] {  "-A", attributeName, "-N", String.valueOf(value), "-R", "1" });
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            System.out.println("Failed to fill missing values with custom value: " + e.getMessage());
            App.showExceptionWindow(e);
        }

        System.out.println(filteredData.toSummaryString());

        return filteredData;
    }

    // Solution 3: Fill missing values using backward/forward fill method
    public static Instances fillMissingValuesWithNeighbor(Instances data, String attributeName) {
        // Get the index of the attribute
        int attributeIndex = data.attribute(attributeName).index();

        // Iterate over each instance
        for (int i = 0; i < data.numInstances(); i++) {
            Instance instance = data.instance(i);

            // Check if the attribute value is missing
            if (instance.isMissing(attributeIndex)) {
                // Fill the missing value with the previous or next value
                if (i > 0 && !data.instance(i - 1).isMissing(attributeIndex)) {
                    instance.setValue(attributeIndex, data.instance(i - 1).value(attributeIndex));
                } else if (i < data.numInstances() - 1 && !data.instance(i + 1).isMissing(attributeIndex)) {
                    instance.setValue(attributeIndex, data.instance(i + 1).value(attributeIndex));
                }
            }
        }

        return data;
    }

    // TODO: Solution 4: Fill missing values using KNN
}
