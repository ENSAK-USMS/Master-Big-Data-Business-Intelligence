/**
 * @author abdobella
 * Date: Dec 19, 2023
 * Time: 12:43:01 PM
*/
package com.nfs.app.controllers;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import weka.core.Attribute;
import weka.core.Instances;

public class DataSetEditController {
    @FXML
    private Pane tablePane;
    
    private TableView<ObservableList<StringProperty>> tableView;
    private ObservableList<ObservableList<StringProperty>> data;

    public void createTable(Instances dataSet) {
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        // Create table columns based on the number of attributes in the dataset
        for (int i = 0; i < dataSet.numAttributes(); i++) {
            final int columnIndex = i;
            TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>(dataSet.attribute(i).name());
            column.setCellValueFactory(
                new Callback<CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ObservableList<StringProperty>, String> param) {
                        return param.getValue().get(columnIndex);
                    }
                }
            );
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setOnEditCommit(
                (EventHandler<CellEditEvent<ObservableList<StringProperty>, String>>) new EventHandler<CellEditEvent<ObservableList<StringProperty>, String>>() {
                    @Override
                    public void handle(CellEditEvent<ObservableList<StringProperty>, String> event) {
                        ObservableList<StringProperty> row = event.getRowValue();
                    String newValue = event.getNewValue();
                    Attribute attribute = dataSet.attribute(columnIndex);
                    if (attribute.isNominal()) {
                        row.set(columnIndex, new SimpleStringProperty(newValue));
                    } else if (attribute.isString()) {
                        row.set(columnIndex, new SimpleStringProperty(newValue));
                    } else if (attribute.isDate()) {
                        // Handle date parsing and validation here
                        // For example, you can use SimpleDateFormat to parse the date string
                        // and validate it before setting the value in the row
                        // row.set(columnIndex, new SimpleStringProperty(newValue));
                        // answer :
                        
                    } else {
                        try {
                            double numericValue = Double.parseDouble(newValue);
                            row.set(columnIndex, new SimpleStringProperty(numericValue + ""));
                        } catch (NumberFormatException e) {
                            // Handle invalid numeric value input here
                            // For example, you can show an error message to the user
                        }
                    }
                    }
                }
            );
            tableView.getColumns().add(column);
        }

        // Add data rows to the table
        for (int i = 0; i < dataSet.numInstances(); i++) {
            ObservableList<StringProperty> row = FXCollections.observableArrayList();
            for (int j = 0; j < dataSet.numAttributes(); j++) {
                if (dataSet.attribute(j).isNominal() || dataSet.attribute(j).isString() || dataSet.attribute(j).isDate()) {
                    row.add(new SimpleStringProperty(dataSet.instance(i).stringValue(j)));
                } else {
                    row.add(new SimpleStringProperty(dataSet.instance(i).value(j) + ""));
                }
            }
            data.add(row);
        }

        tableView.setItems(data);
        tableView.setEditable(true);
        tableView.setPrefSize(600, 400);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablePane.getChildren().add(tableView);
    }
}

