module com.opgavedb_1.opgavedb_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens com.opgavedb_1 to javafx.fxml;
    exports com.opgavedb_1;
    exports com.opgavedb_1.presentation;
    opens com.opgavedb_1.presentation to javafx.fxml;
}