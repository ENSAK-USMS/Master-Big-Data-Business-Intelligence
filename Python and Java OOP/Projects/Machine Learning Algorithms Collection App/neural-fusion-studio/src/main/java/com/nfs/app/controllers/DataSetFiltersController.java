/**
 * @author abdobella
 * Date: Dec 20, 2023
 * Time: 3:50:28 PM
*/
package com.nfs.app.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import weka.core.Instances;

public class DataSetFiltersController {

    private Instances dataSet;

    @FXML
    private FlowPane instancesPane;
    

    @FXML
    public void closeDataSetEdit() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }

    public void addInstancesGrid() {
        // Assume your Instances object has attributes, and you want to display them
        for (int i = 0; i < dataSet.numAttributes(); i++) {
            String attributeName = dataSet.attribute(i).name();


            HBox hBox = createHBox(attributeName);

            instancesPane.getChildren().add(hBox);
        }
    }

    public HBox createHBox(String labelName) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(39.0);
        hBox.setPrefWidth(164.8);
        hBox.setStyle("-fx-border-width: 5; -fx-border-color: #676767;");

        CheckBox checkBox = new CheckBox();
        HBox.setMargin(checkBox, new Insets(5.0, 5.0, 5.0, 5.0));

        Label label = new Label(labelName);
        label.setFont(new Font(14.0));
        HBox.setMargin(label, new Insets(5.0, 5.0, 5.0, 5.0));

        hBox.getChildren().addAll(checkBox, label);

        return hBox;
    }


    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }
}
