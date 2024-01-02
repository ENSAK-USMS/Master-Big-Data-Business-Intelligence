package com.nfs.app.preprocessing;

import com.nfs.app.App;

import weka.core.Instances;

public class RemoveDuplicates {
    

    // Solution 1: Remove duplicate records
    public static Instances removeDuplicates(Instances data) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the RemoveDuplicates filter to remove duplicate instances
        try {
            weka.filters.Filter filter = new weka.filters.unsupervised.instance.RemoveDuplicates();
            filter.setInputFormat(filteredData);
            filteredData = weka.filters.Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            App.showExceptionWindow(e);
        }

        // Return the filtered instances
        return filteredData;
    }

    // Solution 2: Remove duplicate attributes
    public static Instances removeDuplicateAttributes(Instances data) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Loop over the attributes and remove duplicate attributes
        for (int i = 0; i < filteredData.numAttributes(); i++) {
            for (int j = i + 1; j < filteredData.numAttributes(); j++) {
                if (filteredData.attribute(i).equals(filteredData.attribute(j))) {
                    filteredData.deleteAttributeAt(j);
                }
            }
        }
        // Return the filtered instances
        return filteredData;
    }
}
