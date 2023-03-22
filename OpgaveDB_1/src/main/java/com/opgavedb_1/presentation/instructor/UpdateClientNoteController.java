package com.opgavedb_1.presentation.instructor;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.service.DataSingleton;
import com.opgavedb_1.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateClientNoteController implements Initializable {

    @FXML
    private Label clientNameNote;

    @FXML
    private TextArea clientNote;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    DataSingleton selectedClient = DataSingleton.getInstance();
    User client = selectedClient.getClient();

    String clientFirstname = client.getFirstname();
    String clientLastname = client.getLastname();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //vis klientens navn som label på siden
        clientNameNote.setText(clientFirstname + " " + clientLastname);

        clientNote.setText(db.getClientNote(client));
    }

    @FXML
    void saveClientNote(ActionEvent event) {
        String note = clientNote.getText();

        db.updateClientNote(client, note);
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
