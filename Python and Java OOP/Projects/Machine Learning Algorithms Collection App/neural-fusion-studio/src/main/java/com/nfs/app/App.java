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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

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

        Group textGroup = new Group();
        Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25);

    //Welcome to Java string
    String welcome = "classification";
    double rotation = -80;

    double radius = 155d;

    //Loop
    for (char c : welcome.toCharArray()) {
        // ignore whitespace, otherwise add rotated char
        if (!Character.isWhitespace(c)) {
            Text text = new Text(Character.toString(c));
            text.setFont(font);

            Rotate rotationMatrix = new Rotate(rotation, 0, radius);
            text.getTransforms().add(rotationMatrix);

            textGroup.getChildren().add(text);
        }
        rotation += 5;
        textGroup.setTranslateX(422);
        textGroup.setTranslateY(113);
    }
    
    // add new page
    contentPane.getChildren().add(root);
    // contentPane.getChildren().add(textGroup);

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
        //         e.printStackTrace();
        //     }
        // }, 0, 1, TimeUnit.SECONDS);
    // }
}