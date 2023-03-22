package com.opgavedb_1.presentation.instructor;

//Mikkel og Jonas

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Team;
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

public class TeamsController implements Initializable {

    @FXML
    private TextField createNewTeamInput;

    @FXML
    private ListView teamListView;

    @FXML
    private TextField updateTeamInput;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");
    ArrayList<Team> allTeams = db.getAllTeams();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //teams opbevares i en arrayliste for at beholde relationen til deres id, selvom kun titel vises i listview
        for (int i = 0; i < allTeams.size(); i++) {
            teamListView.getItems().add(allTeams.get(i).getTitle());
        }
    }

    @FXML
    void createNewTeam(ActionEvent event) {
        String newTeamTitle = createNewTeamInput.getText();

        if (newTeamTitle.length() > 0) {
            Team team = new Team(newTeamTitle);
            allTeams.add(team);

            db.createNewTeam(team);
            teamListView.getItems().add(newTeamTitle);

            createNewTeamInput.clear();
        }
    }

    @FXML
    void updateSelectedTeam(ActionEvent event) {
        String updatedTeamTitle = updateTeamInput.getText();
        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();

        //forudsætter at tekstfeltet ikke er tomt, og at der vælges et element fra listview
        if (updatedTeamTitle.length() > 0 && selectedIndex >= 0) {
            teamListView.getItems().set(selectedIndex, updatedTeamTitle);

            Team selectedTeam = allTeams.get(selectedIndex);
            selectedTeam.setTitle(updatedTeamTitle);
            db.updateTeam(selectedTeam);

            updateTeamInput.clear();
        }
    }

    @FXML
    void deleteSelectedTeam(ActionEvent event) {
        int selectedIndex = teamListView.getSelectionModel().getSelectedIndex();
        teamListView.getItems().remove(selectedIndex);

        Team selectedTeam = allTeams.get(selectedIndex);
        allTeams.remove(selectedTeam);

        db.deleteTeam(selectedTeam);
    }

    @FXML
    public void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("Vælg et holdnavn og tryk på knappen ”Opret hold”, for at oprette et nyt hold.\n" +
                             "\n" +
                             "Til højre kan der skiftes navn på et eksisterende hold, eller det kan slettes.\n" +
                             "Vælg holdet på listen, og gør derefter brug af knapperne nederst til højre.");
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