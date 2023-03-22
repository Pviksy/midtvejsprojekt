package com.opgavedb_1.presentation.instructor;

//Mikkel og Jonas

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TagsController implements Initializable {

    @FXML
    private TextField createNewTagInput;

    @FXML
    private ListView tagListView;

    @FXML
    private TextField updateTagInput;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");
    private ArrayList<Tag> allTags = db.getAllTags();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //tags opbevares i en arrayliste for at beholde relationen til deres id, selvom kun titel vises i listview
        for (int i = 0; i < allTags.size(); i++) {
            tagListView.getItems().add(allTags.get(i).getTitle());
        }

    }

    @FXML
    void createNewTag(ActionEvent event) {
        String newTagTitle = createNewTagInput.getText();

        if (newTagTitle.length() > 0) {
            Tag tag = new Tag(newTagTitle);
            allTags.add(tag);

            db.createNewTag(tag);
            tagListView.getItems().add(newTagTitle);

            createNewTagInput.clear();
        }
    }

    @FXML
    void updateSelectedTag(ActionEvent event) {
        String updatedTagTitle = updateTagInput.getText();
        int selectedIndex = tagListView.getSelectionModel().getSelectedIndex();

        //forudsætter at tekstfeltet ikke er tomt, og at der vælges et element fra listview
        if (updatedTagTitle.length() > 0 && selectedIndex >= 0) {
            tagListView.getItems().set(selectedIndex, updatedTagTitle);

            Tag selectedTag = allTags.get(selectedIndex);
            selectedTag.setTitle(updatedTagTitle);
            db.updateTagTitle(selectedTag);

            updateTagInput.clear();
        }
    }

    @FXML
    void deleteSelectedTag(ActionEvent event) {
        int selectedIndex = tagListView.getSelectionModel().getSelectedIndex();
        tagListView.getItems().remove(selectedIndex);

        Tag selectedTag = allTags.get(selectedIndex);
        allTags.remove(selectedTag);

        db.deleteTag(selectedTag);
    }

    @FXML
    public void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("Vælg et tagnavn og tryk på knappen ”Opret tag”, for at oprette et nyt tag.\n" +
                             "\n" +
                             "Til højre kan der skiftes navn på et eksisterende tag, eller det kan slettes.\n" +
                             "Vælg tagget på listen, og gør derefter brug af knapperne nederst til højre.\n");
        alert.showAndWait();
    }

    public void logOut(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/com/opgavedb_1/Login/login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
