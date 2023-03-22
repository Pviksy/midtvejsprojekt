package com.opgavedb_1.presentation.instructor;

//Mikkel og Jonas

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Team;
import com.opgavedb_1.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateNewClientController implements Initializable {

    @FXML
    private ComboBox teamPicker;

    @FXML
    private TextField firstnameInput, lastnameInput, emailInput, phonenumberInput, addressInput;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    ArrayList<Team> allTeams = db.getAllTeams();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < allTeams.size(); i++) {
            teamPicker.getItems().add(allTeams.get(i).getTitle());
        }
    }

    @FXML
    void createNewClient() {

        int selectedIndex = teamPicker.getSelectionModel().getSelectedIndex();
        Team selectedTeam = allTeams.get(selectedIndex);

              String email = emailInput.getText();
        String phonenumber = phonenumberInput.getText();
          String firstname = firstnameInput.getText();
           String lastname = lastnameInput.getText();
            String address = addressInput.getText();
               int team_id = selectedTeam.getId();

        User newUser = new User(email, phonenumber, firstname, lastname, address, team_id);

        db.createNewClient(newUser);
        db.sendWelcomeEmailToClient(newUser);

        emailInput.clear();
        phonenumberInput.clear();
        firstnameInput.clear();
        lastnameInput.clear();
        addressInput.clear();

        //husk at sætte combobox fra start, eller ikke tillade at oprette bruger hvis den ikke indeholder en værdi
    }

    @FXML
    public void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("Indsæt personlige oplysninger på den nye klient i de relevante tekstfelter.\n" +
                             "Vælg et hold øverst til højre, eller vælg privatperson hvis klienten ikke skal tildeles et hold.\n" +
                             "Tryk herefter på ”Opret klient” knappen.\n" +
                             "\n" +
                             "En e-mail vil automatisk blive sendt til den angivne e-mailadresse, med klientens\n" +
                             "nye loginoplysninger, og med en kort velkomstbesked.");
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