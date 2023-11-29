module com.nfs.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nfs.app to javafx.fxml;
    exports com.nfs.app;
}
