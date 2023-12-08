package com.nfs.app.controllers;

import java.io.IOException;

import com.nfs.app.App;

import javafx.fxml.FXML;

public class BaseController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
