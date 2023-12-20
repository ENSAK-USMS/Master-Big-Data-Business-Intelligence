/**
 * @author abdobella
 * Date: Dec 20, 2023
 * Time: 3:50:28 PM
*/
package com.nfs.app.controllers;

import javafx.fxml.FXML;

public class DataSetFiltersController {
    @FXML
    public void closeDataSetEdit() {
        BaseController.unblurBasePage();
        BaseController.removePageFromBasePane();
    }
}
