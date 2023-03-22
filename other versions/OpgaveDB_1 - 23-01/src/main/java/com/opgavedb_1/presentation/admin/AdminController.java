package com.opgavedb_1.presentation.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Circle circle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image profileImage = new Image("file:src/main/resources/media/ShyPepe.png");
        circle.setFill(new ImagePattern(profileImage));



    }

    public void logOut(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/com/opgavedb_1/login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
