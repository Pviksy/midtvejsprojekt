package com.opgavedb_1;

//Mikkel

/*
* Generelle bemærkninger, i stedet for at lave de samme kommentarer mange steder
* - Index -1 i elementer som ListView og ComboBox er når intet er valgt, deraf if (ting != 1)
*
* - Store dele af præsentationslaget gør brug af arraylister for at opbevare objekterne.
* Fælles for dem er at objektet hentes ved at finde index i fx ListView, og hente objektet fra den tilsvarende
* arraylist. (Tvivl om hvorvidt dette er den bedste løsning har strejfet mig)
*
* Kommentarer er så vidt muligt undladt, hvis det fremgår af navngivningen
*
* */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Image icon = new Image("file:src/main/resources/media/logo.png");

        //gem tidligere dimensioner på stage
        double prevWidth = stage.getWidth();
        double prevHeight = stage.getHeight();

        //ved skift af scene sættes stage til tidligere dimensioner
        stage.setWidth(prevWidth);
        stage.setHeight(prevHeight);

        stage.setMaximized(true);
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