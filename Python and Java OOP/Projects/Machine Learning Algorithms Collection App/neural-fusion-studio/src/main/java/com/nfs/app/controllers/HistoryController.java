package com.nfs.app.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

// import for sorting algorithms by date
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import com.nfs.app.App;
import com.nfs.app.algorithms.Algorithm_Abstract;

public class HistoryController implements Initializable {


    // create a hashmap to store the algorithm name and the algorithm object
    private ArrayList<Algorithm_Abstract> algorithms = new ArrayList<Algorithm_Abstract>();

    @FXML
    private VBox history_vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get the length of the hashmap
        

        
        // get the algorithms from downloads / models folder and add them to the hashmap
        File folder = new File(System.getProperty("user.home") + "/Downloads/models");
        File[] algorithmFiles = folder.listFiles();

        if (algorithmFiles != null) {
            for (File file : algorithmFiles) {
                // load the serialized object Algorithm_Abstract
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(file.toURI().toURL().openStream());
                    Algorithm_Abstract algorithm = (Algorithm_Abstract) objectInputStream.readObject();
                    objectInputStream.close();
                    // add the algorithm to the hashmap
                    algorithms.add(algorithm);
                    System.out.println("found algorithm " + algorithm.getName());
                } catch (IOException | ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Collections.sort(algorithms, new Comparator<Algorithm_Abstract>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                @Override
                public int compare(Algorithm_Abstract algorithm1, Algorithm_Abstract algorithm2) {
                    try {
                        Date date1 = dateFormat.parse(algorithm1.getDate());
                        Date date2 = dateFormat.parse(algorithm2.getDate());
                        return date1.compareTo(date2)*-1;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            System.out.println(algorithms);
            for (int i = 0; i < algorithms.size(); i++) {
                // get hashmap value by index
                Algorithm_Abstract algorithm = (Algorithm_Abstract) algorithms.get(i);
                createHistoryPane(i, algorithm);
                System.out.println("created history pane for " + algorithm.getName());
            }
            return;
        }
        System.out.println("No algorithms found");
    }

    private void createHistoryPane(int index,Algorithm_Abstract algorithm) {
        // load the history pane page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nfs/app/views/history_view/row_component.fxml"));
        Parent row_component;
        try {
            row_component = loader.load();
            // get the controller
            HistoryRowController historyRowController = loader.getController();
            // pass the algorithm object to the controller
            historyRowController.setAlgorithm(algorithm);
            // add the page to the history vbox
            this.history_vbox.getChildren().add(row_component);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
