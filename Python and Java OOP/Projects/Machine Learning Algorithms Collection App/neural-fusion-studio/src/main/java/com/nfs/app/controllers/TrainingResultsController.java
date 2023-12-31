/**
 * @author abdobella
 * Date: 29 déc. 2023
 * Time: 12:43:42
*/
package com.nfs.app.controllers;

import java.io.FileOutputStream;

import com.nfs.app.algorithms.Algorithm_Abstract;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class TrainingResultsController {

    @FXML
    private Pane resultParent;
    @FXML
    private TextArea resultsTextArea;

    private Instances dataSet;
    private Algorithm_Abstract model;

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    public void setModel(Algorithm_Abstract model) {
        this.model = model;
    }


    public void setResult() {
        try {
            resultsTextArea.setText(String.valueOf(model.getEvaluationResults().toSummaryString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveModel() {
        try {
            // Ask the user for the model name
            String modelName = "model"; // Default model name

            // Prompt the user for the model name
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Save Model");
            dialog.setHeaderText("Enter the model name");
            dialog.setContentText("Model Name:");

            // Show the dialog and wait for the user's input
            Optional<String> result = dialog.showAndWait();

            // If the user clicked OK, save the model
            if (result.isPresent()) {
                modelName = result.get();
            } else {
                // User canceled the dialog, do nothing
                return;
            }

            // if the dir models in C:\Users\abdob\Downloads\ does not exist, create it
            if (!Paths.get(System.getProperty("user.home") + File.separator+"Downloads" + File.separator + "models").toFile().exists()) {
                Paths.get(System.getProperty("user.home") + File.separator+"Downloads" + File.separator + "models").toFile().mkdir();
            }

            // Serialize and save the model object
            String filePath = System.getProperty("user.home") + File.separator+"Downloads" + File.separator + "models" + File.separator + modelName + ".ser";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(model);
            objectOut.close();
            fileOut.close();

            System.out.println("Model saved successfully to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeResultWindow() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }

}
