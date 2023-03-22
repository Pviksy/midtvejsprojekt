package com.opgavedb_1.presentation.instructor;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.service.DataSingleton;
import com.opgavedb_1.entities.User;
import com.opgavedb_1.entities.objects.Tag;
import com.opgavedb_1.entities.objects.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;



public class ClientlistController implements Initializable {

    @FXML
    private TextField clientFilterField;

    @FXML
    private TableView<User> clientTable;

    @FXML
    private TableColumn<User, String> team;

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

    @FXML
    private CheckBox checkboxEmailTeam;

    @FXML
    private CheckBox checkboxEmailClient;

    @FXML
    private ComboBox tagFilter;

    @FXML
    private ListView taskListView;


    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    private ArrayList<Tag> allTags = db.getAllTags();
    private ArrayList<Task> allTasks = db.getAllTasks();
    private ArrayList<Task> filteredTasks = allTasks;

    private int selectedTagFilterIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (Tag tag : allTags) {
            tagFilter.getItems().add(tag.getTitle());
        }

        showAllTasks();

        checkboxEmailTeam.setSelected(true);
        checkboxEmailClient.setSelected(true);

        ObservableList<User> userList = FXCollections.observableArrayList(db.getAllClients());

               team.setCellValueFactory(new PropertyValueFactory<>("team_title"));
              email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
          firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
           lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<User> filteredUserList = new FilteredList<>(userList, b -> true);

        //alt dette opdateres hver gang input i tekstfeltet ændres
        clientFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUserList.setPredicate(user -> {

                //hvis søgefeltet er tomt, vises alle klienter
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //det har ikke indflydelse om indtastningen består af store/små bogstaver
                String lowerCaseFilter = newValue.toLowerCase();

                //logikken der fortæller om nogle af felterne indeholder det indtastede
                if (user.getTeam_title().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (user.getEmail().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (String.valueOf(user.getPhonenumber()).contains(lowerCaseFilter))
                    return true;
                else if (user.getFirstname().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (user.getLastname().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (user.getAddress().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else
                    return false;
            });
        });

        SortedList<User> sortedUserList = new SortedList<>(filteredUserList);

        sortedUserList.comparatorProperty().bind(clientTable.comparatorProperty());

        clientTable.setItems(sortedUserList);
    }

    @FXML
    private void resetTagFilter(ActionEvent event) {
        selectedTagFilterIndex = -1;
        tagFilter.valueProperty().set(null);

        allTasks = db.getAllTasks();
        filteredTasks = allTasks;
    }

    @FXML
    private void updateTagFilter(ActionEvent event) {
        selectedTagFilterIndex = tagFilter.getSelectionModel().getSelectedIndex();

        if (selectedTagFilterIndex == -1)
            showAllTasks();
        else {
            Tag selectedTag = allTags.get(selectedTagFilterIndex);

            showTasksByTag(selectedTag);
            filteredTasks = db.getTasksByTag(selectedTag);
        }
    }

    private void showAllTasks() {
        taskListView.getItems().clear();

        for (int i = 0; i < allTasks.size(); i++) {
            taskListView.getItems().add(allTasks.get(i).getTitle());
        }
    }

    private void showTasksByTag(Tag tag) {
        taskListView.getItems().clear();

        for (int i = 0; i < db.getTasksByTag(tag).size(); i++) {
            taskListView.getItems().add(db.getTasksByTag(tag).get(i).getTitle());
        }
    }

    DataSingleton selectedClient = DataSingleton.getInstance();

    @FXML
    private void goToEditClient(ActionEvent event) throws IOException {

        int selectedTableIndex = clientTable.getSelectionModel().getSelectedIndex();

        selectedClient.setClient(clientTable.getItems().get(selectedTableIndex));

        URL url = new File("src/main/resources/com/opgavedb_1/Instructor/RedigerKlient.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //servicemetoder
    private User selectClient() {
        int selectedClientIndex = clientTable.getSelectionModel().getSelectedIndex();

        if (selectedClientIndex != -1) {
            return clientTable.getItems().get(selectedClientIndex);
        } else
            return null;
    }
    private Task selectTask() {
        int selectedTaskIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedTaskIndex != -1) {
            return filteredTasks.get(selectedTaskIndex);
        } else
            return null;
    }

    @FXML
    private void sendTaskToTeam(ActionEvent event) {

        if (selectClient() != null && selectTask() != null) {
            db.giveTaskToTeam(selectClient(), selectTask());

            if (checkboxEmailTeam.isSelected())
                db.sendTaskEmailsToTeam(selectClient());
        }
    }

    @FXML
    private void sendTaskToClient(ActionEvent event) {

        if (selectClient() != null && selectTask() != null) {
            db.giveTaskToClient(selectClient(), selectTask());

            if (checkboxEmailClient.isSelected())
                db.sendTaskEmailToClient(selectClient());
        }
    }

    @FXML
    private void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("For at redigere i en klients oplysninger, \n" +
                             "tryk på en klient og tryk derefter på ”Rediger oplysninger” knappen.\n" +
                             "\n" +
                             "For at sende en opgave til en klient, eller klientens hold,\n" +
                             "vælges en klient på listen samt en opgave til højre\n" +
                             "og der trykkes på en af knapperne nederst i højre hjørne.\n" +
                             "Sæt flueben hvis en e-mail notifikation\n" +
                             "skal sendes til den valgte klient, eller dennes hold.\n" +
                             "\n" +
                             "Øverst til højre kan opgaverne i listen, filtreres efter tag\n" +
                             "Ved brug af søgefeltet, kan listen over klienter filtreres efter alle viste oplysninger");
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
