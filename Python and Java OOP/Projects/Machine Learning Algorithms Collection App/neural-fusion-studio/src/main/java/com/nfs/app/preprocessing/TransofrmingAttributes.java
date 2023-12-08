/**
 * @author abdobella
 * Date: Dec 07, 2023
 * Time: 2:48:26 PM
 */
package com.nfs.app.preprocessing;

import java.util.Map;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;


public class TransofrmingAttributes {
    private Instances data;

    public TransofrmingAttributes(Instances data) {
        this.data = data;
    }

    public Instances encodeOrdinal(String attributeName, Map<String, Integer> mapping) {
        Attribute attribute = data.attribute(attributeName);

        Instances newData = new Instances(data);

        // Iterate over each instance and replace the attribute values with the mapped values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            String value = instance.stringValue(attribute);

            if (mapping.containsKey(value)) {
                int mappedValue = mapping.get(value);
                instance.setValue(attribute, mappedValue);
            } else {
                // Handle unknown values or assign a default value
                // For example, you can assign a value of -1
                instance.setValue(attribute, -1);
            }
        }

        this.data = newData;
        return newData;
    }

    public Instances autoEncodeOrdinal(String attributeName) {
        Attribute attribute = data.attribute(attributeName);

        Instances newData = new Instances(data);

        // Iterate over each instance and replace the attribute values with the mapped values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            String value = instance.stringValue(attribute);

            int mappedValue = (int) attribute.indexOfValue(value);
            instance.setValue(attribute, mappedValue);
        }

        this.data = newData;
        return newData;
    }

    public Instances oneHotEncodeNominal(String attributeName) {
        Attribute attribute = data.attribute(attributeName);
        int numValues = attribute.numValues();

        Instances newData = new Instances(data);

        for (int i = 0; i < numValues; i++) {
            String value = attribute.value(i);

            // Create a new attribute for the one-hot encoded value
            Attribute newAttribute = new Attribute(attributeName + "_" + value);

            // Add the new attribute to the dataset
            newData.insertAttributeAt(newAttribute, newData.numAttributes());

            // Iterate over each instance and set the value for the new attribute
            for (int j = 0; j < newData.numInstances(); j++) {
                Instance instance = newData.instance(j);

                if (instance.stringValue(attribute) == value) {
                    instance.setValue(newAttribute, 1.0);
                } else {
                    instance.setValue(newAttribute, 0.0);
                }
            }
        }

        // Remove the original attribute
        newData.deleteAttributeAt(data.attribute(attributeName).index());

        this.data = newData;
        return newData;
    }

    public Instances minMaxScaler(String attributeName) {
        Attribute attribute = data.attribute(attributeName);
        double min = attribute.getLowerNumericBound();
        double max = attribute.getUpperNumericBound();

        Instances newData = new Instances(data);

        // Iterate over each instance and scale the attribute values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            double value = instance.value(attribute);

            // Scale the value between 0 and 1
            double scaledValue = (value - min) / (max - min);

            instance.setValue(attribute, scaledValue);
        }

        this.data = newData;
        return newData;
    }

    public Instances minMaxScaler(String attributeName, double min, double max) {
        Attribute attribute = data.attribute(attributeName);

        Instances newData = new Instances(data);

        // Iterate over each instance and scale the attribute values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            double value = instance.value(attribute);

            // Scale the value between the specified min and max
            double scaledValue = ((value - attribute.getLowerNumericBound()) / (attribute.getUpperNumericBound() - attribute.getLowerNumericBound())) * (max - min) + min;

            instance.setValue(attribute, scaledValue);
        }

        this.data = newData;
        return newData;
    }   

    public Instances standardScaler(String attributeName) {
        Attribute attribute = data.attribute(attributeName);

        Instances newData = new Instances(data);

        // Calculate the mean
        double mean = 0.0;
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            mean += instance.value(attribute);
        }
        mean /= newData.numInstances();

        // Calculate the standard deviation
        double std = 0.0;
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            std += Math.pow(instance.value(attribute) - mean, 2);
        }
        std = Math.sqrt(std / newData.numInstances());

        // Iterate over each instance and scale the attribute values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            double value = instance.value(attribute);

            // Scale the value
            double scaledValue = (value - mean) / std;

            instance.setValue(attribute, scaledValue);
        }

        this.data = newData;
        return newData;
    }

    public void convertToInt(String attributeName) {
        Attribute attribute = data.attribute(attributeName);

        Instances newData = new Instances(data);

        // Iterate over each instance and replace the attribute values with the mapped
        // values
        for (int i = 0; i < newData.numInstances(); i++) {
            Instance instance = newData.instance(i);
            float value = (float) instance.value(attribute);

            int mappedValue = (int) value;
            instance.setValue(attribute, mappedValue);
        }

        this.data = newData;
    }
    
    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }
    
}
