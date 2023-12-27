/**
 * @author abdobella
 * Date: Dec 20, 2023
 * Time: 7:52:24 PM
*/

package com.nfs.app.algorithms;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.nfs.app.App;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class X extends Thread {

    public static void main(String[] args) {
        X x = new X();
        x.start();
    }

    @Override
    public void run() {
        // Update the file path accordingly
        String csvFilePath = "C:/Users/abdob/Downloads/penguins_binary_classification.csv";
        csvFilePath = "C:/Users/abdob/Downloads/vote.csv";

        readCSVFile(csvFilePath);

        // Do something with the instances if needed
        // System.out.println("CSV Instances: " + csvInstances);
    }

    private Instances readCSVFile(String filePath) {
        try {
            File file = new File(filePath);
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long estimatedReadingTimeInSeconds = fileSizeInKB / 10; // Assuming 10 KB/s reading speed

            System.out.println("Estimated reading time: " + formatTime(estimatedReadingTimeInSeconds));

            CSVLoader loader = new CSVLoader();
            loader.setSource(file);

            Instances data = loader.getDataSet();

            return data;
        } catch (Exception e) {
            App.showExceptionWindow(e);
            return null;
        }
    }

    private String formatTime(long seconds) {
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
