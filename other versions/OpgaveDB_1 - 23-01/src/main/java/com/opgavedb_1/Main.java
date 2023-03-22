package com.opgavedb_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Instructor/instructor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Image icon = new Image("file:src/main/resources/media/logo.png");

        //gem tidligere dimensioner på stage
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();

        //ved skift af scene sættes stage til tidligere dimensioner
        stage.setWidth(prevWidth);
        stage.setHeight(prevHeight);

        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.getIcons().add(icon);
        stage.setTitle("OpgaveDB");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}