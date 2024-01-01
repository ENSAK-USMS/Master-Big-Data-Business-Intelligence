/**
 * @author abdobella
 * Date: Dec 07, 2023
 * Time: 3:15:10 PM
*/
package com.nfs.app.preprocessing;

import com.nfs.app.App;

import weka.core.Instances;

public class RemoveDuplicates {
    private Instances data;
    
    public RemoveDuplicates(Instances data) {
        this.data = data;
    }

    // Solution 1: Remove duplicate records
    public Instances removeDuplicates() {
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

        // Update the data with the filtered instances
        return filteredData;
    }

    // Solution 2: Remove duplicate attributes
    public Instances removeDuplicateAttributes() {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // loop over the attributes and remove duplicate attributes
        for (int i = 0; i < filteredData.numAttributes(); i++) {
            for (int j = i + 1; j < filteredData.numAttributes(); j++) {
                if (filteredData.attribute(i).name().equals(filteredData.attribute(j).name())) {
                    filteredData.deleteAttributeAt(j);
                }
            }
        }
        // Update the data with the filtered instances
        return filteredData;
    }
}
