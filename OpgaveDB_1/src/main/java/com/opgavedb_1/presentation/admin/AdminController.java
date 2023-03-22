package com.opgavedb_1.presentation.admin;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Label selectedInstructorNameLabel;

    @FXML
    private Button updateFirstnameButton, updateLastnameButton, updateEmailButton, updatePhonenumberButton, updateAddressButton;

    @FXML
    private Button createNewInstructorButton;

    @FXML
    private TextField firstnameInput, lastnameInput, emailInput, phonenumberInput, addressInput;

    @FXML
    private TableView<User> instructorTable;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, Integer> phonenumber;

    @FXML
    private TableColumn<User, String> firstname;

    @FXML
    private TableColumn<User, String> lastname;

    @FXML
    private TableColumn<User, String> address;

    private User selectedInstructor;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInstructorTable();
        setInputsAsEditable(false);
        disableUpdateButtons(true);
        createNewInstructorButton.setDisable(true);
    }

    private void setInstructorTable() {
        ObservableList<User> instructorList = FXCollections.observableArrayList(db.getAllInstructors());

        instructorTable.setItems(instructorList);

              email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
          firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
           lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    @FXML
    private void updateSelectedInstructor(ActionEvent event) {
        setInputsAsEditable(true);
        disableUpdateButtons(false);
        createNewInstructorButton.setDisable(true);

        selectedInstructor = instructorTable.getSelectionModel().getSelectedItem();

          firstnameInput.setText(selectedInstructor.getFirstname());
           lastnameInput.setText(selectedInstructor.getLastname());
              emailInput.setText(selectedInstructor.getEmail());
        phonenumberInput.setText(selectedInstructor.getPhonenumber());
            addressInput.setText(selectedInstructor.getAddress());

        selectedInstructorNameLabel.setText(selectedInstructor.getFirstname() + " " +
                                       selectedInstructor.getLastname());
    }

    private void setInputsAsEditable(Boolean b) {
          firstnameInput.setEditable(b);
           lastnameInput.setEditable(b);
              emailInput.setEditable(b);
        phonenumberInput.setEditable(b);
            addressInput.setEditable(b);
    }

    private void clearInputs() {
          firstnameInput.clear();
           lastnameInput.clear();
              emailInput.clear();
        phonenumberInput.clear();
            addressInput.clear();
    }

    private void disableUpdateButtons(Boolean b) {
          updateFirstnameButton.setDisable(b);
           updateLastnameButton.setDisable(b);
              updateEmailButton.setDisable(b);
        updatePhonenumberButton.setDisable(b);
            updateAddressButton.setDisable(b);
    }

    @FXML
    private void deleteSelectedInstructor(ActionEvent event) {
        db.deleteUser(instructorTable.getSelectionModel().getSelectedItem());
        setInstructorTable();
    }

    @FXML
    private void updateEmail(ActionEvent event) {
        String newEmail = emailInput.getText();

        db.updateUserEmail(selectedInstructor, newEmail);
        setInstructorTable();
    }

    @FXML
    private void updatePhonenumber(ActionEvent event) {
        String newPhonenumber = phonenumberInput.getText();

        db.updateUserPhonenumber(selectedInstructor, newPhonenumber);
        setInstructorTable();
    }

    @FXML
    private void updateFirstname(ActionEvent event) {
        String newFirstname = firstnameInput.getText();

        db.updateUserFirstname(selectedInstructor, newFirstname);
        selectedInstructorNameLabel.setText(newFirstname + " " + selectedInstructor.getLastname());
        setInstructorTable();
    }

    @FXML
    private void updateLastname(ActionEvent event) {
        String newLastname = lastnameInput.getText();

        db.updateUserLastname(selectedInstructor, newLastname);
        selectedInstructorNameLabel.setText(selectedInstructor.getFirstname() + " " + newLastname);
        setInstructorTable();
    }

    @FXML
    private void updateAddress(ActionEvent event) {
        String newAddress = addressInput.getText();

        db.updateUserAddress(selectedInstructor, newAddress);
        setInstructorTable();
    }

    @FXML
    private void startCreatingNewInstructor() {
        clearInputs();
        setInputsAsEditable(true);
        selectedInstructorNameLabel.setText("Ny vækstresonant");
        createNewInstructorButton.setDisable(false);
    }

    @FXML
    private void createNewInstructor(ActionEvent event) {
              String email = emailInput.getText();
        String phonenumber = phonenumberInput.getText();
          String firstname = firstnameInput.getText();
           String lastname = lastnameInput.getText();
            String address = addressInput.getText();

        if (inputsAreNotEmpty()) {
            User newInstructor = new User(email, phonenumber, firstname, lastname, address);

            db.createNewInstructor(newInstructor);
            db.sendLoginDetailsEmail(newInstructor);

            clearInputs();
            setInputsAsEditable(false);
            createNewInstructorButton.setDisable(true);
            selectedInstructorNameLabel.setText("-");

            setInstructorTable();
        } else
            selectedInstructorNameLabel.setText("Udfyld venligst alle felter");
    }

    private Boolean inputsAreNotEmpty() {
        if (      emailInput.getText().length() > 0 &&
            phonenumberInput.getText().length() > 0 &&
              firstnameInput.getText().length() > 0 &&
               lastnameInput.getText().length() > 0 &&
                addressInput.getText().length() > 0)
            return true;
        else
            return false;
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
