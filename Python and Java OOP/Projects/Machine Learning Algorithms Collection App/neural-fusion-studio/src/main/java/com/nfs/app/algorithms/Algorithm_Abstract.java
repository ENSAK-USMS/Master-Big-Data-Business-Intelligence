/**
 * @author abdobella
 * Date: Dec 24, 2023
 * Time: 4:59:32 PM
*/
package com.nfs.app.algorithms;

import weka.classifiers.Evaluation;
import weka.core.Instances;

public abstract class Algorithm_Abstract {
        protected Instances dataset;
        protected Evaluation evaluation;

        public void setup(Instances dataset) {
            this.dataset = dataset;
        }

        public abstract void setOptions(String options) throws Exception;

        public abstract void evaluate();

        public String getDefaultOptions() {
            return "";
        }
    }
