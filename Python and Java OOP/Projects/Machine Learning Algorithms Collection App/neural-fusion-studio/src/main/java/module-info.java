module com.nfs.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires weka.stable;

    opens com.nfs.app to javafx.fxml, weka.stable;
    exports com.nfs.app;
}
