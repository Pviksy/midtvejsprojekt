package com.opgavedb_1.presentation.instructor;

//Mikkel og Jonas

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Team;
import com.opgavedb_1.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
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

        emailInput.clear();
        phonenumberInput.clear();
        firstnameInput.clear();
        lastnameInput.clear();
        addressInput.clear();

        //husk at sætte combobox fra start, eller ikke tillade at oprette bruger hvis den ikke indeholder en værdi
    }
}