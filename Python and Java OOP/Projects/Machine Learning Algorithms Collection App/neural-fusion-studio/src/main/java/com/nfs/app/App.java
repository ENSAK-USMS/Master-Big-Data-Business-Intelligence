package com.nfs.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.control.Label;
import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static boolean exceptionWindowEventsAdded = false;
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("base"), 900, 600);
        stage.initStyle(StageStyle.UNDECORATED); // Remove the default window header
        // chage scene bg color to 191919
        scene.setFill(javafx.scene.paint.Color.valueOf("#191919"));
        stage.setScene(scene);
        switchPage("views/dashboard/index");
        setPageBottons();
        stage.show();
        // watchResourcesChanges();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    public static Scene getScene() {
        return scene;
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

    // a function to load images from resources
    public static ImageView loadImage(String path) {
        Image image = new Image(App.class.getResource(path).toString());
        ImageView imageView = new ImageView(image);
        return imageView;
    }



    // error window
    // TODO: Make it in its own file instead of visible false

    public static void showExceptionWindow(Exception exception) {
        Pane exceptionOuterPane = (Pane) scene.lookup("#exceptionOuterPane");
        Pane exceptionWindow = (Pane) scene.lookup("#exceptionWindow");

        String exceptionMessage = exception.getMessage();
        String exceptionName = exception.getClass().getName();
        String exceptionStackTrace = exception.toString();

        Label exceptionMessageLabel = (Label) exceptionWindow.lookup("#exceptionMessage");
        exceptionMessageLabel.setText(exceptionMessage);

        Label exceptionNameLabel = (Label) exceptionWindow.lookup("#exceptionName");
        if (exceptionName.length() > 35) {
            exceptionName = exceptionName.substring(0, 35) + "...";
        }
        exceptionNameLabel.setText(exceptionName);

        Group showStackTrace = (Group) exceptionWindow.lookup("#showStackTrace");
        Button closeButton = (Button) exceptionWindow.lookup("#closeExceptionButton");

        
        Label stackTraceLabel = (Label) exceptionWindow.lookup("#stackTrace");
        stackTraceLabel.setVisible(false);
        
        if (!exceptionWindowEventsAdded) {
            showStackTrace.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
                toggleStackTrace(exceptionWindow, exceptionStackTrace);
            });
            
            Pane closeArea = (Pane) exceptionOuterPane.lookup("#closeArea");
            closeArea.setOnMouseClicked(e -> {
                hideExceptionWindow(exceptionOuterPane);
            });
            
            closeButton.setOnMouseClicked(e -> {
                hideExceptionWindow(exceptionOuterPane);
            });
            
            exceptionWindowEventsAdded = true;
        }
        
        exceptionOuterPane.setVisible(true);
    }

    private static void hideExceptionWindow(Pane exceptionWindow) {
        Label stackTraceLabel = (Label) exceptionWindow.lookup("#stackTrace");
        stackTraceLabel.setVisible(false);
        stackTraceLabel.setPrefHeight(0);
        stackTraceLabel.setLayoutY(0);
        exceptionWindow.setPrefHeight(80);

        Label exceptionMessageLabel = (Label) exceptionWindow.lookup("#exceptionMessage");
        exceptionMessageLabel.setText("");

        Label exceptionNameLabel = (Label) exceptionWindow.lookup("#exceptionName");
        exceptionNameLabel.setText("");

        stackTraceLabel.setText("");

        exceptionWindow.setVisible(false);
        
    }

    private static void toggleStackTrace(Pane exceptionWindow, String exceptionStackTrace) {
        Label stackTraceLabel = (Label) exceptionWindow.lookup("#stackTrace");
        System.out.println("toggleStackTrace : " + stackTraceLabel.isVisible());

        if (stackTraceLabel.isVisible()) {
            stackTraceLabel.setVisible(false);
            stackTraceLabel.setPrefHeight(0);
            stackTraceLabel.setLayoutY(0);
            exceptionWindow.setPrefHeight(80);
        } else {
            stackTraceLabel.setText(exceptionStackTrace);
            stackTraceLabel.setPrefHeight(320);
            stackTraceLabel.setLayoutY(80);
            stackTraceLabel.setVisible(true);
            exceptionWindow.setPrefHeight(400);
        }
    }
    


    // TODO: Add a function to watch for changes in the resources folder and reload the page
    // private static void watchResourcesChanges() {
        
        // Path resourcesPath = Paths.get("src/main/resources");

        // System.out.println(resourcesPath);

        // ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        // executorService.scheduleAtFixedRate(() -> {
        //     try {
        //         WatchService watchService = FileSystems.getDefault().newWatchService();
        //         resourcesPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        //         WatchKey key;
        //         while ((key = watchService.take()) != null) {
        //             for (WatchEvent<?> event : key.pollEvents()) {
        //                 if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
        //                     // Reload the page here
        //                     // You can call a method or perform any necessary actions to reload the page
        //                     System.out.println("Resources changed. Reloading page...");
        //                 }
        //             }
        //             key.reset();
        //         }
        //     } catch (IOException | InterruptedException e) {
        //         App.showExceptionWindow(e);
        //     }
        // }, 0, 1, TimeUnit.SECONDS);
    // }
}