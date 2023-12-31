package com.nfs.app.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.nfs.app.App;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BaseController {


    @FXML
    private Pane sideBar,sideBarShadow1,sideBarShadow2;
    @FXML
    private Line sideBareHline1,sideBareHline2,sideBareHline3,sideBareHline4;
    @FXML
    private ImageView closeImage;
    @FXML
    private AnchorPane basePane;



    @FXML
    private void toggleSidebar() {
        double targetWidth = 0;
        double targetLayoutX = 0;
        double targetEndX = 0;

        if (sideBar.getPrefWidth() == 210) {
            // close the sidebar
            targetWidth = 70;
            targetLayoutX = 70.5;
            targetEndX = 70;
        } else {
            // open the sidebar
            targetWidth = 210;
            targetLayoutX = 210;
            targetEndX = 210;
        }

        // Create a timeline for the animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.prefWidthProperty(), sideBar.getPrefWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.minWidthProperty(), sideBar.getMinWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBar.maxWidthProperty(), sideBar.getMaxWidth())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBarShadow1.layoutXProperty(), sideBarShadow1.getLayoutX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBarShadow2.layoutXProperty(), sideBarShadow2.getLayoutX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline1.endXProperty(), sideBareHline1.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline2.endXProperty(), sideBareHline2.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline3.endXProperty(), sideBareHline3.getEndX())),
                new KeyFrame(Duration.ZERO, new KeyValue(sideBareHline4.endXProperty(), sideBareHline4.getEndX())),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.prefWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.minWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBar.maxWidthProperty(), targetWidth)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBarShadow1.layoutXProperty(), targetLayoutX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBarShadow2.layoutXProperty(), targetLayoutX+2)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline1.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline2.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline3.endXProperty(), targetEndX)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(sideBareHline4.endXProperty(), targetEndX))
        );

        // Play the timeline animation
        timeline.play();
    }



    public void setHoverEffect() {
        // get the scene
        Scene scene = App.getScene();
        // get all buttons with css class hoverable
        ArrayList<Button> hoverableButtons = new ArrayList<>();
        Parent root = (Parent) scene.getRoot();
        root.lookupAll(".hoverable").forEach(node -> {
            if (node instanceof Button) {
                hoverableButtons.add((Button) node);
            }
        });
        // add hover effect to all buttons
        for (Button button : hoverableButtons) {
            button.setOnMouseEntered(e -> {
                // get the parent group
                Group parent = (Group) button.getParent();
                // get the image view
                ImageView imageView = (ImageView) parent.getChildren().get(1);
                // get the image name
                String imageName = imageView.getImage().getUrl();
                // remove everything before "/images"
                String imagePath = imageName.substring(imageName.indexOf("images"));
                // get the new image name
                String newImageName = imagePath.replace(".png", "-hover.png");
                // load the new image
                ImageView newImageView = App.loadImage(newImageName);
                // set the new image
                imageView.setImage(newImageView.getImage());
            });
            button.setOnMouseExited(e -> {
                // get the parent group
                Group parent = (Group) button.getParent();
                // get the image view
                ImageView imageView = (ImageView) parent.getChildren().get(1);
                // get the image name
                String imageName = imageView.getImage().getUrl();
                // remove everything before "/images"
                String imagePath = imageName.substring(imageName.indexOf("images"));
                // get the new image name
                String newImageName = imagePath.replace("-hover.png", ".png");
                // load the new image
                ImageView newImageView = App.loadImage(newImageName);
                // set the new image
                imageView.setImage(newImageView.getImage());
            });
        }
    }
    // blur base page
    public static void blurBasePage() {
        AnchorPane pagePane = (AnchorPane) App.getScene().lookup("#pagePane");
        pagePane.setEffect(new javafx.scene.effect.BoxBlur(1, 1, 3));
    }
    // unblur base page
    public static void unblurBasePage() {
        AnchorPane pagePane = (AnchorPane) App.getScene().lookup("#pagePane");
        pagePane.setEffect(null);
    }
    // add page to base pane
    public static void addPageToBasePane(Parent root) {
        AnchorPane basePane = (AnchorPane) App.getScene().lookup("#basePane");
        basePane.getChildren().add(root);
    }
    // remove page from base pane check if it is not pagepane
    public static void removePageFromBasePane() {
        AnchorPane basePane = (AnchorPane) App.getScene().lookup("#basePane");
        if (basePane.getChildren().size() > 1) {
            basePane.getChildren().remove(basePane.getChildren().size() - 1);
        }
    }



    @FXML
    public void swithToHistoryPage() throws IOException {
        App.switchPage("views/History");
    }

    @FXML
    public void swithToHomePage() throws IOException {
        App.switchPage("views/dashboard/index");
    }



    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            setHoverEffect();
        }));
        timeline.play();
        System.out.println("BaseController initialized");
    }
}
