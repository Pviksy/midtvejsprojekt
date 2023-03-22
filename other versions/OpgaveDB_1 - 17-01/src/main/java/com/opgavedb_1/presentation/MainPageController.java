package com.opgavedb_1.presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private TabPane mainPageTabPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        borderPane.setCenter(new Label("Hello"));
        borderPane2.setLeft(new Label("Left"));

        Button btn = new Button("Click me");

        borderPane2.setCenter(btn);


        Tab mainTab = createTab(borderPane, "Main tab");
        Tab secondTab = createTab(borderPane2, "2nd tab");

        mainPageTabPane.setMinHeight(600);
        mainPageTabPane.setMinWidth(800);

        mainPageTabPane.getTabs().add(mainTab);
        mainPageTabPane.getTabs().add(secondTab);



        btn.setOnAction(event -> createNewTab(mainPageTabPane, new BorderPane(), "New tab"));

    }

    void createNewTab(TabPane tabPane, Node tabContent, String tabName) {
        Tab tab = new Tab(tabName);
        tab.setContent(tabContent);
        tabPane.getTabs().add(tab);

        //return tab;
    }

    //Node er superklasse til bl.a. alle panes så man kan vælge hvilken
    Tab createTab(Node tabContent, String tabName) {
        Tab tab = new Tab(tabName);
        tab.setContent(tabContent);

        return tab;
    }

    @FXML
    void switchSceneTest(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/com/opgavedb_1/createAccount.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
