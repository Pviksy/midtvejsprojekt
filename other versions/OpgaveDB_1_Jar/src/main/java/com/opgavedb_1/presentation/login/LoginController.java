package com.opgavedb_1.presentation.login;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.service.DataSingleton;
import com.opgavedb_1.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label errorMessage;

    public void logIn(ActionEvent event) throws IOException {
        URL login = checkLoginDetails();

        //hvis login er null, skal ingen ny scene loades, da det betyder loginoplysningerne ikke fandtes i databasen
        if (login != null) {
            Parent root = FXMLLoader.load(login);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        }
    }

    private URL checkLoginDetails() throws MalformedURLException {
        DataAccessLayer db = new DataAccessLayer("OpgaveDB");

        String fxmlPath = null;

        String email = emailInput.getText();
        String password = passwordInput.getText();

        if (email.length() > 0 && password.length() > 0) {

            User user = db.getUserByEmail(email);

            //hvis user er null, er den indtastede email ikke er tilknyttet en bruger i databasen
            if (user != null) {
                if (Integer.toString(user.getPincode()).equals(password)) {

                    int user_type = user.getUser_type_id();

                    switch (user_type) {
                        case 1 ->   //admin
                                fxmlPath = "src/main/resources/com/opgavedb_1/Admin/admin.fxml";
                        case 2 ->   //instructor
                                fxmlPath = "src/main/resources/com/opgavedb_1/Instructor/instructor.fxml";
                        case 3 -> { //client
                            fxmlPath = "src/main/resources/com/opgavedb_1/Client/client.fxml";
                            //for at loade de rigtige materialer til en klient, overføres klientens id til ClientController vha singleton klassen
                            DataSingleton userInformation = DataSingleton.getInstance();
                            userInformation.setClient(user);
                        }
                    }

                    return new File(fxmlPath).toURI().toURL();
                } else
                    errorMessage.setText("Forkert password");
            } else
                errorMessage.setText("Bruger findes ikke");
        } else
            errorMessage.setText("Angiv venligst E-mail og password");

        return null;
    }
}