package com.nfs.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("base"), 900, 600);
        switchPage("views/dashboard");
        setPageBottons();
        stage.initStyle(StageStyle.UNDECORATED); // Remove the default window header
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }

    // a funtction to swithc between page by adding and removing them from the anchor pane with the id of "contentPane" and also load an unload the controllers
    public static void switchPage(String fxml) throws IOException {
        // use function to load fxml file
        Parent root = loadFXML(fxml);
        // get contentPane by fx:id
        AnchorPane contentPane = (AnchorPane) scene.lookup("#contentPane");

        // remove all children
        contentPane.getChildren().clear();

        // add new page
        contentPane.getChildren().add(root);

    }


    // close window fx:id closeAppwindow
    public static void setPageBottons() throws IOException {
        // get closeAppwindow by fx:id
        Button closeApp = (Button) scene.lookup("#closeApp");
        // get iconife by fx:id
        Button iconifieApp = (Button) scene.lookup("#iconifieApp");

        // close window fx:id closeAppwindow
        closeApp.setOnMouseClicked(e -> {
            System.exit(0);
        });

        // iconifie window fx:id iconifieApp
        iconifieApp.setOnMouseClicked(e -> {
            Stage stage = (Stage) iconifieApp.getScene().getWindow();
            stage.setIconified(true);
        });

        
    }

}