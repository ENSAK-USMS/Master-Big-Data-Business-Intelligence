/**
 * @author abdobella
 * Date: Dec 07, 2023
 * Time: 2:32:27 PM
 */
package com.nfs.app.preprocessing;

import weka.core.Instances;
import weka.core.Instance;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class MissingValues {
    private Instances data;

    public MissingValues(Instances data) {
        this.data = data;
    }

    // Solution 1: Remove missing records
    public void removeMissingRecords() {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the RemoveWithValues filter to remove instances with missing values
        try {
            Filter filter = new weka.filters.unsupervised.instance.RemoveWithValues();
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the data with the filtered instances
        data = filteredData;
    }

    // Solution 2: Fill missing values with a global constant or attribute mean/median
    // with mean
    public void fillMissingValuesWithMean() {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter to fill missing values
        try {
            Filter filter = new ReplaceMissingValues();
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the data with the filtered instances
        data = filteredData;
    }
    
    // with median
    public void fillMissingValuesWithMedian(){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with median option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "0"});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the data with the filtered instances
        data = filteredData;
    }
    // with mode
    public void fillMissingValuesWithMode(){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with mode option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "1"});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the data with the filtered instances
        data = filteredData;
    }

    // with custom value
    public void fillMissingWithCustomValue(double value){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with custom value option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "2", "-R", "first-last", "-V", String.valueOf(value)});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the data with the filtered instances
        data = filteredData;
    }

    // Solution 3: Fill missing values using backward/forward fill method
    public void fillMissingValuesWithNeighbor() {
        // Iterate over each instance
        for (int i = 0; i < data.numInstances(); i++) {
            Instance instance = data.instance(i);

            // Iterate over each attribute
            for (int j = 0; j < data.numAttributes(); j++) {
                // Check if the attribute value is missing
                if (instance.isMissing(j)) {
                    // Fill the missing value with the previous or next value
                    if (i > 0 && !data.instance(i - 1).isMissing(j)) {
                        instance.setValue(j, data.instance(i - 1).value(j));
                    } else if (i < data.numInstances() - 1 && !data.instance(i + 1).isMissing(j)) {
                        instance.setValue(j, data.instance(i + 1).value(j));
                    }
                }
            }
        }
    }

    // TODO: Solution 4: Fill missing values using KNN
}
