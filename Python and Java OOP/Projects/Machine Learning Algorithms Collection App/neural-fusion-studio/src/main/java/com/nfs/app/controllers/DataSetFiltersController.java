/**
 * @author abdobella
 * Date: Dec 20, 2023
 * Time: 3:50:28 PM
*/
package com.nfs.app.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nfs.app.App;
import com.nfs.app.preprocessing.MissingValues;
import com.nfs.app.preprocessing.RemoveDuplicates;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import weka.core.Instances;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import weka.core.Attribute;
import weka.core.Instance;

public class DataSetFiltersController {

    private Instances dataSet;

    private HashMap<String, CheckBox> checkBoxMap = new HashMap<>();

    @FXML
    private Label attr_info_missing;

    @FXML
    private TextField textFieldFiltersOptions;

    @FXML
    private Button close_filter_pane_btn;

    @FXML
    private Label attr_info_name;

    @FXML
    private VBox filters_vbox;

    @FXML
    private Label attr_info_type;

    @FXML
    private Label attr_info_unique;

    @FXML
    private FlowPane instancesPane;

    @FXML
    private Button select_all_button;

    @FXML
    private Button select_invert_button;

    @FXML
    private Button select_none_button;

    @FXML
    private Button applyiFIlterBtn;

    @FXML
    private Label sum_of_weights_lbl;

    @FXML
    private Label instances_lbl;

    @FXML
    private Label nb_attr_lbl;

    @FXML
    private Label relation_name_lbl;

    @FXML
    private VBox statictics_vbox;

    @FXML
    private Label statistic_lbl1;

    @FXML
    private Label statistic_lbl2;

    @FXML
    private Pane chart_pane;

    @FXML
    private Pane filters_pane_container;

    @FXML
    void on_filters_click(ActionEvent event) {
        filters_pane_container.setVisible(true);
        filters_vbox.getChildren().clear();
        filters_vbox.getChildren().add(createFilterPane_MissingValues());
        filters_vbox.getChildren().add(createFilterPane_duplicate());
    }

    @FXML
    void close_filter_pane(ActionEvent event) {
        filters_pane_container.setVisible(false);
        filters_vbox.getChildren().clear();
    }

    public void onInit() {
        String relation_name_lbl_updated = this.dataSet.relationName();
        if (this.dataSet.relationName().length() > 26) {
            relation_name_lbl_updated = this.dataSet.relationName().substring(0, 26) + "...";
        }
        relation_name_lbl.setText(relation_name_lbl_updated);
        nb_attr_lbl.setText(String.valueOf(dataSet.numAttributes()));
        instances_lbl.setText(String.valueOf(dataSet.numInstances()));
        sum_of_weights_lbl.setText(String.valueOf(dataSet.sumOfWeights()));
        statictics_vbox.getChildren().clear();
        initialize_statictics();
        show_info(0);
        initialize_chart_filters();
    }

    public void initialize_statictics() {
        if (dataSet != null) {
            createStatictic(0);
        }
    }

    @FXML
    void remove_selected_attributes(ActionEvent event) {
        if (!checkBoxMap.isEmpty()) {
            Iterator<Map.Entry<String, CheckBox>> iterator = checkBoxMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, CheckBox> entry = iterator.next();
                CheckBox checkBox = entry.getValue();
                if (checkBox.isSelected()) {
                    String attribute_name = checkBox.getId();
                    int index_of_attribute = dataSet.attribute(attribute_name).index();
                    dataSet.deleteAttributeAt(index_of_attribute);
                    iterator.remove();
                }
            }
            instancesPane.getChildren().clear();
            addInstancesGrid();
            onInit();
        }
    }

    private void createStatictic(int i) {
        String attributeName = dataSet.attribute(i).name();
        String countText = "";
        int mean = 0;
        int std = 0;
        int min = 0;
        int max = 0;

        if (!dataSet.attribute(i).isNumeric()) {
            countText = String.valueOf(dataSet.attributeStats(i).nominalCounts.length);
            statictics_vbox.getChildren().add(createXMLHBox(attributeName, countText));
            statistic_lbl1.setText("Label");
            statistic_lbl2.setText("Count");
        } else {
            mean = (int) dataSet.attributeStats(i).numericStats.mean;
            std = (int) dataSet.attributeStats(i).numericStats.stdDev;
            min = (int) dataSet.attributeStats(i).numericStats.min;
            max = (int) dataSet.attributeStats(i).numericStats.max;
            statistic_lbl1.setText("Metric");
            statistic_lbl2.setText("Value");
            statictics_vbox.getChildren().add(createXMLHBox("Min", String.valueOf(min)));
            statictics_vbox.getChildren().add(createXMLHBox("Max", String.valueOf(max)));
            statictics_vbox.getChildren().add(createXMLHBox("Std", String.valueOf(std)));
            statictics_vbox.getChildren().add(createXMLHBox("Mean", String.valueOf(mean)));
        }
    }

    @FXML
    void closeDataSetEdit(ActionEvent event) {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }

    @FXML
    void invert_selected_items(ActionEvent event) {
        if (!checkBoxMap.isEmpty()) {
            for (CheckBox checkBox : checkBoxMap.values()) {
                checkBox.setSelected(!checkBox.isSelected());
            }
        }
    }

    @FXML
    void select_all(ActionEvent event) {
        if (!checkBoxMap.isEmpty()) {
            for (CheckBox checkBox : checkBoxMap.values()) {
                checkBox.setSelected(true);
            }
        }
    }

    @FXML
    void unselect_all(ActionEvent event) {
        if (!checkBoxMap.isEmpty()) {
            for (CheckBox checkBox : checkBoxMap.values()) {
                checkBox.setSelected(false);
            }
        }

    }

    @FXML
    public void closeDataSetEdit() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }

    public void addInstancesGrid() {
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
        hBox.setStyle("-fx-border-width: 2; -fx-border-color: #6c6c6c;");

        CheckBox checkBox = new CheckBox();
        checkBox.setId(labelName);
        checkBoxMap.put(labelName, checkBox);
        HBox.setMargin(checkBox, new Insets(5.0, 5.0, 5.0, 5.0));

        Label label = new Label(labelName);
        label.setFont(new Font(14.0));
        HBox.setMargin(label, new Insets(5.0, 5.0, 5.0, 5.0));

        hBox.getChildren().addAll(checkBox, label);

        // when you click on the box, it will select the corresponding attribute
        hBox.setOnMouseClicked(event -> {
            int index_of_attribute = dataSet.attribute(labelName).index();
            show_info(index_of_attribute);
            destroyVBox();
            createStatictic(index_of_attribute);
            chart_pane.getChildren().clear();
            visualizeData(index_of_attribute);
        });

        return hBox;
    }

    private void show_info(int index_of_attribute) {
        String attr_name = dataSet.attribute(index_of_attribute).name();
        if (attr_name.length() > 9) {
            attr_name = attr_name.substring(0, 9) + "...";
        }

        attr_info_name.setText(attr_name);
        attr_info_type.setText(dataSet.attribute(index_of_attribute).type() == 0 ? "Numeric" : "Nominal");
        attr_info_unique.setText(String.valueOf(dataSet.attribute(index_of_attribute).numValues()));
        attr_info_missing.setText(String.valueOf(dataSet.attributeStats(index_of_attribute).missingCount));
    }

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    private HBox createXMLHBox(String labelText, String countText) {
        HBox hBox = new HBox();
        hBox.setPrefHeight(30.0);
        hBox.setPrefWidth(200.0);
        hBox.setStyle("-fx-background-color: transparent;");

        Label label = new Label(labelText);
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(30.0);
        label.setPrefWidth(142.0);
        label.setStyle("-fx-background-color: transparent;");
        label.setTextFill(Color.web("#d1d116"));
        label.setPadding(new Insets(3.0, 5.0, 3.0, 5.0));

        Label countLabel = new Label(countText);
        countLabel.setAlignment(Pos.CENTER);
        countLabel.setPrefHeight(30.0);
        countLabel.setPrefWidth(142.0);
        countLabel.setStyle("-fx-background-color: transparent;");
        countLabel.setTextFill(Color.web("#d1d116"));
        countLabel.setPadding(new Insets(3.0, 5.0, 3.0, 5.0));

        hBox.getChildren().addAll(label, countLabel);

        return hBox;
    }

    // function that destroys all elements in the vbox if there is any
    public void destroyVBox() {
        if (!statictics_vbox.getChildren().isEmpty()) {
            statictics_vbox.getChildren().clear();
        }
    }

    private void initialize_chart_filters() {
        if (dataSet != null) {
            visualizeData(0);
        }
    }

    private void visualizeData(int i) {
        if (dataSet.attribute(i).isNominal()) {
            visualizeData_categorical(i);
        } else {
            visualizeData_numerical(i);
        }
    }

    public void visualizeData_categorical(int index_of_attribute) {
        List<String> categories = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        // Get the attribute values and counts from your dataset
        Attribute attribute = dataSet.attribute(index_of_attribute);
        for (int i = 0; i < attribute.numValues(); i++) {
            categories.add(attribute.value(i));
            counts.add(dataSet.attributeStats(index_of_attribute).nominalCounts[i]);
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < categories.size(); i++) {
            series.getData().add(new XYChart.Data<>(categories.get(i), counts.get(i)));
        }

        barChart.getData().add(series);

        barChart.setPrefHeight(202);
        barChart.setPrefWidth(432.0);
        barChart.setLegendVisible(false);

        // Add the chart to the chart_pane
        chart_pane.getChildren().add(barChart);
    }

    public void visualizeData_numerical(int index_of_attribute) {
        List<Double> values = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        // Get the attribute values and counts from your dataset
        Attribute attribute = dataSet.attribute(index_of_attribute);
        for (int i = 0; i < dataSet.numInstances(); i++) {
            Instance instance = dataSet.instance(i);
            double value = instance.value(index_of_attribute);
            values.add(value);
        }

        // Calculate the count of each unique value
        Map<Double, Integer> valueCounts = new HashMap<>();
        for (double value : values) {
            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
        }

        // Sort the values in ascending order
        List<Double> sortedValues = new ArrayList<>(valueCounts.keySet());
        Collections.sort(sortedValues);

        // Populate the counts list
        for (double value : sortedValues) {
            counts.add(valueCounts.get(value));
        }

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < sortedValues.size(); i++) {
            series.getData().add(new XYChart.Data<>(sortedValues.get(i), counts.get(i)));
        }

        lineChart.getData().add(series);

        lineChart.setPrefHeight(202);
        lineChart.setPrefWidth(432.0);
        lineChart.setLegendVisible(false);

        // Add the chart to the chart_pane
        chart_pane.getChildren().add(lineChart);
    }

    public Pane createFilterPane_MissingValues() {

        Pane pane = new Pane();
        pane.setPrefWidth(459);
        pane.setPrefHeight(40);
        pane.setStyle("-fx-background-color: #1a1a1a;");
        // add margin to the pane top 2, right 2, bottom 2, left 2
        VBox.setMargin(pane, new Insets(4, 4, 0, 4));

        ImageView imageView = App.loadImage("images/drop_down.png");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setLayoutX(14);
        imageView.setLayoutY(8);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        Label label = new Label();
        label.setLayoutX(39);
        label.setLayoutY(8);
        label.setStyle("-fx-background-color: #1a1a1a;");
        label.setText("Missing values");
        label.setTextFill(Color.web("#d1d116"));
        label.setPadding(new Insets(3, 5, 3, 5));

        VBox vBox = new VBox();
        vBox.setLayoutX(24);
        vBox.setLayoutY(33);
        vBox.setPrefWidth(413);

        Pane removeMissingValuesPane = new Pane();
        removeMissingValuesPane.setPrefHeight(40);
        removeMissingValuesPane.setPrefWidth(414);
        removeMissingValuesPane.setStyle("-fx-background-color: #2a2a2a;");
        removeMissingValuesPane.setVisible(false);

        Label removeMissingValuesLabel = new Label();
        removeMissingValuesLabel.setLayoutX(14);
        removeMissingValuesLabel.setLayoutY(8);
        removeMissingValuesLabel.setStyle("-fx-background-color: transparent;");
        removeMissingValuesLabel.setText("Remove");
        removeMissingValuesLabel.setTextFill(Color.web("#d1d116"));
        removeMissingValuesLabel.setPadding(new Insets(3, 5, 3, 5));

        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(removeMissingValuesPane, new Insets(4, 4, 0, 4));

        removeMissingValuesPane.getChildren().add(removeMissingValuesLabel);

        Pane replaceWithMeanPane = new Pane();
        replaceWithMeanPane.setPrefHeight(40);
        replaceWithMeanPane.setPrefWidth(414);
        replaceWithMeanPane.setStyle("-fx-background-color: #2a2a2a;");
        replaceWithMeanPane.setVisible(false);

        Label replaceWithMeanLabel = new Label();
        replaceWithMeanLabel.setLayoutX(14);
        replaceWithMeanLabel.setLayoutY(8);
        replaceWithMeanLabel.setStyle("-fx-background-color: transparent;");
        replaceWithMeanLabel.setText("Replace with mean");
        replaceWithMeanLabel.setTextFill(Color.web("#d1d116"));
        replaceWithMeanLabel.setPadding(new Insets(3, 5, 3, 5));

        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(replaceWithMeanPane, new Insets(4, 4, 0, 4));

        replaceWithMeanPane.getChildren().add(replaceWithMeanLabel);

        Pane replaceWithNeighborPane = new Pane();
        replaceWithNeighborPane.setPrefHeight(40);
        replaceWithNeighborPane.setPrefWidth(414);
        replaceWithNeighborPane.setStyle("-fx-background-color: #2a2a2a;");
        replaceWithNeighborPane.setVisible(false);

        Label replaceWithNeighborLabel = new Label();
        replaceWithNeighborLabel.setLayoutX(14);
        replaceWithNeighborLabel.setLayoutY(8);
        replaceWithNeighborLabel.setStyle("-fx-background-color: transparent;");
        replaceWithNeighborLabel.setText("Replace with neighbor");
        replaceWithNeighborLabel.setTextFill(Color.web("#d1d116"));
        replaceWithNeighborLabel.setPadding(new Insets(3, 5, 3, 5));

        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(replaceWithNeighborPane, new Insets(4, 4, 0, 4));

        replaceWithNeighborPane.getChildren().add(replaceWithNeighborLabel);

        Pane replaceWithCustomValuePane = new Pane();
        replaceWithCustomValuePane.setPrefHeight(40);
        replaceWithCustomValuePane.setPrefWidth(414);
        replaceWithCustomValuePane.setStyle("-fx-background-color: #2a2a2a;");
        replaceWithCustomValuePane.setVisible(false);

        Label replaceWithCustomValueLabel = new Label();
        replaceWithCustomValueLabel.setLayoutX(14);
        replaceWithCustomValueLabel.setLayoutY(8);
        replaceWithCustomValueLabel.setStyle("-fx-background-color: transparent;");
        replaceWithCustomValueLabel.setText("Replace with custom value");
        replaceWithCustomValueLabel.setTextFill(Color.web("#d1d116"));
        replaceWithCustomValueLabel.setPadding(new Insets(3, 5, 3, 5));

        Label ValueFieldLabel = new Label();
        ValueFieldLabel.setLayoutX(300);
        ValueFieldLabel.setLayoutY(8);
        ValueFieldLabel.setStyle("-fx-background-color: transparent;");
        ValueFieldLabel.setText("value :");
        ValueFieldLabel.setTextFill(Color.web("#d1d116"));
        ValueFieldLabel.setPadding(new Insets(3, 5, 3, 5));

        TextField textField = new TextField();
        textField.setLayoutX(340.0);
        textField.setLayoutY(8.0);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(50.0);
        //
        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(replaceWithCustomValuePane, new Insets(4, 4, 0, 4));

        replaceWithCustomValuePane.getChildren().addAll(replaceWithCustomValueLabel, ValueFieldLabel, textField);

        vBox.getChildren().addAll(removeMissingValuesPane, replaceWithMeanPane, replaceWithCustomValuePane,
                replaceWithNeighborPane);

        pane.getChildren().addAll(imageView, label, vBox);

        // Toggle filter panes visibility on image click
        imageView.setOnMouseClicked(event -> {
            if (removeMissingValuesPane.isVisible()) {
                removeMissingValuesPane.setVisible(false);
                replaceWithMeanPane.setVisible(false);
                replaceWithCustomValuePane.setVisible(false);
                replaceWithNeighborPane.setVisible(false);
                pane.setPrefHeight(40);
                imageView.setRotate(0);
            } else {
                removeMissingValuesPane.setVisible(true);
                replaceWithMeanPane.setVisible(true);
                replaceWithCustomValuePane.setVisible(true);
                replaceWithNeighborPane.setVisible(true);
                pane.setPrefHeight(212);
                imageView.setRotate(90);

            }
        });

        replaceWithMeanPane.setOnMouseClicked(event -> {

            textFieldFiltersOptions.setText("Replace with mean");

            filters_pane_container.setVisible(false);
            filters_vbox.getChildren().clear();
        });

        removeMissingValuesPane.setOnMouseClicked(event -> {

            textFieldFiltersOptions.setText("Remove missing values");

            filters_pane_container.setVisible(false);
            filters_vbox.getChildren().clear();
        });

        replaceWithNeighborPane.setOnMouseClicked(event -> {

            textFieldFiltersOptions.setText("Replace with neighbor");

            filters_pane_container.setVisible(false);
            filters_vbox.getChildren().clear();
        });

        replaceWithCustomValuePane.setOnMouseClicked(event -> {

            if (!checkBoxMap.isEmpty()) {
                Iterator<Map.Entry<String, CheckBox>> iterator = checkBoxMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, CheckBox> entry = iterator.next();
                    CheckBox checkBox = entry.getValue();
                    if (checkBox.isSelected()) {
                        String attribute_name = checkBox.getId();
                        MissingValues.fillMissingWithCustomValue(dataSet, attribute_name,
                                Double.valueOf(textField.getText()));
                    }
                }
            }

            filters_pane_container.setVisible(false);
            filters_vbox.getChildren().clear();

            instancesPane.getChildren().clear();
            addInstancesGrid();
            onInit();
        });

        return pane;
    }

    public Pane createFilterPane_duplicate() {

        Pane pane = new Pane();
        pane.setPrefWidth(459);
        pane.setPrefHeight(40);
        pane.setStyle("-fx-background-color: #1a1a1a;");
        // add margin to the pane top 2, right 2, bottom 2, left 2
        VBox.setMargin(pane, new Insets(4, 4, 0, 4));

        ImageView imageView = App.loadImage("images/drop_down.png");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setLayoutX(14);
        imageView.setLayoutY(8);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        Label label = new Label();
        label.setLayoutX(39);
        label.setLayoutY(8);
        label.setStyle("-fx-background-color: #1a1a1a;");
        label.setText("Duplicated values");
        label.setTextFill(Color.web("#d1d116"));
        label.setPadding(new Insets(3, 5, 3, 5));

        VBox vBox = new VBox();
        vBox.setLayoutX(24);
        vBox.setLayoutY(33);
        vBox.setPrefWidth(413);

        Pane removeDuplicatesValuesPane = new Pane();
        removeDuplicatesValuesPane.setPrefHeight(40);
        removeDuplicatesValuesPane.setPrefWidth(414);
        removeDuplicatesValuesPane.setStyle("-fx-background-color: #2a2a2a;");
        removeDuplicatesValuesPane.setVisible(false);

        Label removeDuplicatesValuesLabel = new Label();
        removeDuplicatesValuesLabel.setLayoutX(14);
        removeDuplicatesValuesLabel.setLayoutY(8);
        removeDuplicatesValuesLabel.setStyle("-fx-background-color: transparent;");
        removeDuplicatesValuesLabel.setText("Remove duplicates values");
        removeDuplicatesValuesLabel.setTextFill(Color.web("#d1d116"));
        removeDuplicatesValuesLabel.setPadding(new Insets(3, 5, 3, 5));

        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(removeDuplicatesValuesPane, new Insets(4, 4, 0, 4));

        removeDuplicatesValuesPane.getChildren().add(removeDuplicatesValuesLabel);

        Pane removeDuplicatesAttributesPane = new Pane();
        removeDuplicatesAttributesPane.setPrefHeight(40);
        removeDuplicatesAttributesPane.setPrefWidth(414);
        removeDuplicatesAttributesPane.setStyle("-fx-background-color: #2a2a2a;");
        removeDuplicatesAttributesPane.setVisible(false);

        Label removeDuplicatesAttributesLabel = new Label();
        removeDuplicatesAttributesLabel.setLayoutX(14);
        removeDuplicatesAttributesLabel.setLayoutY(8);
        removeDuplicatesAttributesLabel.setStyle("-fx-background-color: transparent;");
        removeDuplicatesAttributesLabel.setText("Remove duplicates attributes");
        removeDuplicatesAttributesLabel.setTextFill(Color.web("#d1d116"));
        removeDuplicatesAttributesLabel.setPadding(new Insets(3, 5, 3, 5));

        // add margin top 4, right 4, bottom 0, left 4
        VBox.setMargin(removeDuplicatesAttributesPane, new Insets(4, 4, 0, 4));

        removeDuplicatesAttributesPane.getChildren().add(removeDuplicatesAttributesLabel);

        vBox.getChildren().addAll(removeDuplicatesValuesPane, removeDuplicatesAttributesPane);

        pane.getChildren().addAll(imageView, label, vBox);

        // Toggle filter panes visibility on image click
        imageView.setOnMouseClicked(event -> {
            if (removeDuplicatesValuesPane.isVisible()) {
                removeDuplicatesValuesPane.setVisible(false);
                removeDuplicatesAttributesPane.setVisible(false);
                pane.setPrefHeight(40);
                imageView.setRotate(0);
            } else {
                removeDuplicatesValuesPane.setVisible(true);
                removeDuplicatesAttributesPane.setVisible(true);
                pane.setPrefHeight(126);
                imageView.setRotate(90);

            }
        });

        removeDuplicatesAttributesPane.setOnMouseClicked(event -> {
            textFieldFiltersOptions.setText("Remove duplicates attributes");

            filters_pane_container.setVisible(false);

            filters_vbox.getChildren().clear();
            instancesPane.getChildren().clear();

            addInstancesGrid();
            onInit();
        });

        removeDuplicatesValuesPane.setOnMouseClicked(event -> {
            textFieldFiltersOptions.setText("Remove duplicates values");

            filters_pane_container.setVisible(false);

            filters_vbox.getChildren().clear();
            instancesPane.getChildren().clear();

            addInstancesGrid();
            onInit();
        });

        return pane;
    }

    @FXML
    void applyFilterBtnClick(ActionEvent event) {
        switch (textFieldFiltersOptions.getText()) {
            case "Remove duplicates values":
                RemoveDuplicates removeDuplicates1 = new RemoveDuplicates(dataSet);
                this.dataSet = removeDuplicates1.removeDuplicates();
                instancesPane.getChildren().clear();
                addInstancesGrid();
                onInit();
                break;
            case "Remove duplicates attributes":
                RemoveDuplicates removeDuplicates2 = new RemoveDuplicates(dataSet);
                this.dataSet = removeDuplicates2.removeDuplicateAttributes();
                instancesPane.getChildren().clear();
                addInstancesGrid();
                onInit();
                break;
            case "Remove missing values":
                if (!checkBoxMap.isEmpty()) {
                    Iterator<Map.Entry<String, CheckBox>> iterator = checkBoxMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, CheckBox> entry = iterator.next();
                        CheckBox checkBox = entry.getValue();
                        if (checkBox.isSelected()) {
                            String attribute_name = checkBox.getId();
                            this.dataSet = MissingValues.removeMissingRecords(this.dataSet, attribute_name);
                        }
                    }
                }
                instancesPane.getChildren().clear();
                addInstancesGrid();
                onInit();
                break;
            case "Replace with mean":
                if (!checkBoxMap.isEmpty()) {
                    Iterator<Map.Entry<String, CheckBox>> iterator = checkBoxMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, CheckBox> entry = iterator.next();
                        CheckBox checkBox = entry.getValue();
                        if (checkBox.isSelected()) {
                            String attribute_name = checkBox.getId();
                            this.dataSet = MissingValues.fillMissingValuesWithMean(this.dataSet, attribute_name);
                        }
                    }
                }
                instancesPane.getChildren().clear();
                addInstancesGrid();
                onInit();
                break;
            case "Replace with neighbor":
                if (!checkBoxMap.isEmpty()) {
                    Iterator<Map.Entry<String, CheckBox>> iterator = checkBoxMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, CheckBox> entry = iterator.next();
                        CheckBox checkBox = entry.getValue();
                        if (checkBox.isSelected()) {
                            String attribute_name = checkBox.getId();
                            this.dataSet = MissingValues.fillMissingValuesWithNeighbor(this.dataSet, attribute_name);
                        }
                    }
                }
                instancesPane.getChildren().clear();
                addInstancesGrid();
                onInit();
                break;
            case "Replace with custom value":
                break;
            default:
                break;
        }
    }

}
