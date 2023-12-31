package com.nfs.app.preprocessing;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Normalize;

import com.nfs.app.App;

import weka.core.Attribute;
import weka.core.Instance;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class Preprocessing {
    private Instances instances;

    public Preprocessing(Instances instances) {
        this.instances = instances;
    }

    public void removeAttributes(String[] attributeNames) throws Exception {
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndicesArray(getAttributeIndices(attributeNames));
        removeFilter.setInputFormat(instances);
        instances = Filter.useFilter(instances, removeFilter);
    }

    public void removeInstancesWithNullValue(String attributeName) {
        int attributeIndex = instances.attribute(attributeName).index();
        instances.deleteWithMissing(attributeIndex);
    }



    public void nominalToBinary(String attributeName) throws Exception {
        NominalToBinary nominalToBinary = new NominalToBinary();
        nominalToBinary.setAttributeIndices(attributeName);
        nominalToBinary.setInputFormat(instances);
        instances = Filter.useFilter(instances, nominalToBinary);
    }

    public void convertStringToNominal() throws Exception {
        StringToNominal stringToNominalFilter = new StringToNominal();
        stringToNominalFilter.setInputFormat(instances);
        instances = Filter.useFilter(instances, stringToNominalFilter);
    }

    public void removeAttributes(int[] indices) throws Exception {
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndicesArray(indices);
        removeFilter.setInputFormat(instances);
        instances = Filter.useFilter(instances, removeFilter);
    }



    public void nominalToBinary(String[] attributeNames) throws Exception {
        for (String attributeName : attributeNames) {
            nominalToBinary(attributeName);
        }
    }

    public void normalizeAttribute(String attributeName) throws Exception {
        Normalize normalize = new Normalize();
        normalize.setScale(2.0);
        normalize.setInputFormat(instances);
        instances = Filter.useFilter(instances, normalize);
    }

    public void normalizeAttribute(String[] attributeNames) throws Exception {
        for (String attributeName : attributeNames) {
            normalizeAttribute(attributeName);
        }
    }

    private int[] getAttributeIndices(String[] attributeNames) {
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

    public Instances getInstances() {
        return instances;
    }

    public void setInstances(Instances instances) {
        this.instances = instances;
    }

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
        System.out.println("DATA \n "+data.toSummaryString());
        return data;
    }

    public static Instances fillMissingWithCustomValue(Instances data, double value, String attributeName) {
        // Create a new Instances object to store the filtered data
        Instances filteredData = new Instances(data);

        // Apply the ReplaceMissingValues filter with custom value option to fill
        // missing values
        try {
            ReplaceMissingWithUserConstant filter = new ReplaceMissingWithUserConstant();
            filter.setOptions(new String[] { "-A", attributeName, "-N", String.valueOf(value), "-R", "1" });
            filter.setInputFormat(filteredData);
            filteredData = Filter.useFilter(filteredData, filter);
        } catch (Exception e) {
            System.out.println("Failed to fill missing values with custom value: " + e.getMessage());
            App.showExceptionWindow(e);
        }

        return filteredData;
    }
    
   

    public static Instances fillMissingValuesWithMean(Instances data, String attributeName) {

        // Find the index of the specified attribute
        int attributeIndex = data.attribute(attributeName).index();

        // Compute the mean value for the attribute
        double mean = data.meanOrMode(attributeIndex);

        // Replace missing values with the mean value
        for (Instance instance : data) {
            if (instance.isMissing(attributeIndex)) {
                instance.setValue(attributeIndex, mean);
            }
        }

        return data;
        
    }
}
