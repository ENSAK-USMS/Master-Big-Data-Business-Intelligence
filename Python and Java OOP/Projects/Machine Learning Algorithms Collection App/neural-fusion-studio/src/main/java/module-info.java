module com.nfs.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires weka.stable;
    requires javafx.base;
    requires javafx.graphics;
    // controlsfx
    requires org.controlsfx.controls;

    opens com.nfs.app to javafx.fxml, weka.stable;
    opens com.nfs.app.controllers to javafx.fxml, weka.stable;
    exports com.nfs.app;
    exports com.nfs.app.controllers;
}
