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

public class MissingValues {
    // Solution 1: Remove missing records
    public static Instances removeMissingRecords(Instances data, String attributeName) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the RemoveWithValues filter to remove instances with missing values on the specified attribute
        try {
            Filter filter = new weka.filters.unsupervised.instance.RemoveWithValues();
            filter.setInputFormat(filteredData);
            filter.setOptions(new String[]{"-S", String.valueOf(filteredData.attribute(attributeName).index() + 1), "-C", "last"});
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }

    // Solution 2: Fill missing values with a global constant or attribute mean/median
    // with mean
    public static Instances fillMissingValuesWithMean(Instances data, String attributeName) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter to fill missing values on the specified attribute
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-A", String.valueOf(filteredData.attribute(attributeName).index() + 1)});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }
    
    // with median
    public static Instances fillMissingValuesWithMedian(Instances data){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with median option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "0"});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }
    // with mode
    public static Instances fillMissingValuesWithMode(Instances data){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with mode option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "1"});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }

    // with custom value
    public static Instances fillMissingWithCustomValue(Instances data, double value){
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with custom value option to fill missing values
        try {
            ReplaceMissingValues filter = new ReplaceMissingValues();
            filter.setOptions(new String[]{"-M", "2", "-R", "first-last", "-V", String.valueOf(value)});
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        return filteredData;
    }

    // Solution 3: Fill missing values using backward/forward fill method
    public static Instances fillMissingValuesWithNeighbor(Instances data) {
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

        return data;
    }

    // TODO: Solution 4: Fill missing values using KNN
}
