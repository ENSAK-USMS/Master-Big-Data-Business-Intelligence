/**
 * @author abdobella
 * Date: Nov 30, 2023
 * Time: 11:37:08 PM
*/
package com.nfs.app.preprocessing;


import java.io.File;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class DataImportation {

    public static Instances getDataAsInstances(String fileType, String filePath) throws Exception {
        DataSource source = new DataSource(filePath);
        Instances data;
        
        if (fileType.equalsIgnoreCase("arff")) {
            data = source.getDataSet();
        } else if (fileType.equalsIgnoreCase("csv")) {
            // Create a CSVLoader
            CSVLoader loader = new CSVLoader();
            File file = new File(filePath);
            loader.setSource(file);
            data = loader.getDataSet();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }

        System.out.println(data.toSummaryString());
        
        return data;
    }
    
}
