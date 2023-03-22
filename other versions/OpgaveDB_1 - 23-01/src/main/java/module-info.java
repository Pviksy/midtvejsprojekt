module com.opgavedb_1.opgavedb_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;
    requires org.simplejavamail;
    requires org.simplejavamail.core;




    opens com.opgavedb_1 to javafx.fxml;
    exports com.opgavedb_1;
    exports com.opgavedb_1.entities;

    exports com.opgavedb_1.presentation.instructor;
    opens com.opgavedb_1.presentation.instructor to javafx.fxml;

    exports com.opgavedb_1.presentation.admin;
    opens com.opgavedb_1.presentation.admin to javafx.fxml;

    exports com.opgavedb_1.presentation.client;
    opens com.opgavedb_1.presentation.client to javafx.fxml;

    exports com.opgavedb_1.presentation.login;
    opens com.opgavedb_1.presentation.login to javafx.fxml;
    exports com.opgavedb_1.entities.objects;
}