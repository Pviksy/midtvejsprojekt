package NoFXML;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    Button btn = new Button("Switch scene");
    VBox centerVBox = new VBox();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane borderPane = createBorderPane();
        TabPane tabPane = createTabPane();

        Scene scene = new Scene(tabPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("resono.css").toExternalForm());

        tabPane.getTabs().add(createTab(createBorderPane(), "Other Tab"));
        tabPane.getTabs().add(createTab(createBorderPane(), "Admin Tab"));

        btn.setOnAction(event -> {


            centerVBox.getChildren().add(new Label("New label"));

        });

        //borderPane.setCenter();

        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

    }

    TabPane createTabPane() {
        TabPane tabPane = new TabPane();

        return tabPane;
    }

    Tab createTab(Node tabContent, String tabName) {
        Tab tab = new Tab(tabName);
        tab.setContent(tabContent);
        return tab;
    }

    BorderPane createBorderPane() {
        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(new Label("Hello left"));
        borderPane.setRight(new VBox(new Label("Username"),
                new TextField(),
                new Label("Password"),
                new PasswordField(),
                new Button("Log in")));

        borderPane.setBottom(btn);

        borderPane.setCenter(centerVBox);

        return borderPane;
    }

    WebView createWebView(String url) {
        //"https://www.youtube.com/watch?v=NPqtL1dtrlA"

        WebView webPlayer = new WebView();

        String target = "watch?v=";
        String replacement = "embed/";
        String processed = url.replace(target, replacement);
        WebEngine engine = webPlayer.getEngine();
        engine.load(processed);

        return webPlayer;
    }
}
