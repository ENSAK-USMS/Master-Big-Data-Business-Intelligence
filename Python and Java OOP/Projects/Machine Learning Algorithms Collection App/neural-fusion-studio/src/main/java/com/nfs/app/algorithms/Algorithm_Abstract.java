/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 4:59:32 PM
*/
package com.nfs.app.algorithms;

import java.io.Serializable;

import weka.classifiers.Evaluation;
import weka.core.Instances;

public abstract class Algorithm_Abstract implements Serializable {
        protected Instances dataset;
        protected Evaluation evaluation;
        protected String filePath;
        protected String fileName;
        protected String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setup(Instances dataset) {
            this.dataset = dataset;
        }

        public abstract void setOptions(String options) throws Exception;

        public void setClassAttribute(String classAttribute) {
            if (dataset == null) {
                throw new IllegalStateException("Dataset is not set");
            }
            dataset.setClass(dataset.attribute(classAttribute));
        }

        public abstract void evaluate();

        public abstract Evaluation getEvaluationResults();

        public abstract String getAccuracy();

        public void setFilePath(String filePath) {
            this.filePath = filePath;
            // set file name
            // get the last index of the file separator
            int lastIndexOfFileSeparator = filePath.lastIndexOf(System.getProperty("file.separator"));
            // get the file name
            fileName = filePath.substring(lastIndexOfFileSeparator + 1);
        }

        public String getDefaultOptions() {
            return "";
        }

        public abstract String getName();

        // getdataset
        public Instances getDataset() {
            return dataset;
        }
    }
