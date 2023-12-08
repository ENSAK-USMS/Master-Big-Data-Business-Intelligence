package com.nfs.app.preprocessing;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Normalize;
import weka.core.Attribute;

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

    public void ReplaceMissingValuesWithMean() throws Exception {
        ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(instances);
        instances = Filter.useFilter(instances, replaceMissingValues);
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
}
