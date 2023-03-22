package com.opgavedb_1.presentation.instructor;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.DataSingleton;
import com.opgavedb_1.entities.User;
import com.opgavedb_1.entities.objects.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateClientInfoController implements Initializable {

    @FXML
    private Label clientNameInfo;

    @FXML
    private TextField firstnameInput, lastnameInput, emailInput, phonenumberInput, addressInput;

    @FXML
    private ComboBox teamInput;

    @FXML
    private Button firstnameUpdate, lastnameUpdate, emailUpdate, phonenumberUpdate, addressUpdate, teamUpdate;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");
    ArrayList<Team> allTeams = db.getAllTeams();

    DataSingleton selectedClient = DataSingleton.getInstance();

    //den valgte klient opbevares så den er tilgængelig for alle metoder
    User client = selectedClient.getClient();

    String clientFirstname = client.getFirstname();
    String clientLastname = client.getLastname();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //vis klientens navn som label på siden
        clientNameInfo.setText(clientFirstname + " " + clientLastname);

        //klientens nuværende oplysninger indsættes
        firstnameInput.setText(client.getFirstname());
        lastnameInput.setText(client.getLastname());
        emailInput.setText(client.getEmail());
        phonenumberInput.setText(client.getPhonenumber());
        addressInput.setText(client.getAddress());
        teamInput.setValue(client.getTeam_title());

        //alle hold indsættes i combobox
        for (int i = 0; i < allTeams.size(); i++) {
            teamInput.getItems().add(allTeams.get(i).getTitle());
        }

    }



    @FXML
    void updateFirstname(ActionEvent event) {
        String newFirstname = firstnameInput.getText();

        db.updateClientFirstname(client, newFirstname);
        clientNameInfo.setText(newFirstname + " " + clientLastname);
    }

    @FXML
    void updateLastname(ActionEvent event) {
        String newLastname = lastnameInput.getText();

        db.updateClientLastname(client, newLastname);
        clientNameInfo.setText(clientFirstname + " " + newLastname);
    }

    @FXML
    void updateEmail(ActionEvent event) {
        String newEmail = emailInput.getText();

        db.updateClientEmail(client, newEmail);
    }

    @FXML
    void updatePhonenumber(ActionEvent event) {
        String newPhonenumber = phonenumberInput.getText();

        db.updateClientPhonenumber(client, newPhonenumber);
    }

    @FXML
    void updateAddress(ActionEvent event) {
        String newAddress = addressInput.getText();

        db.updateClientAddress(client, newAddress);
    }

    @FXML
    void updateTeam(ActionEvent event) {
        int selectedIndex = teamInput.getSelectionModel().getSelectedIndex();
        Team selectedTeam = allTeams.get(selectedIndex);

        db.updateClientTeam(client, selectedTeam);
    }




    @FXML
    void goToClientList(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/com/opgavedb_1/Instructor/instructor.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
