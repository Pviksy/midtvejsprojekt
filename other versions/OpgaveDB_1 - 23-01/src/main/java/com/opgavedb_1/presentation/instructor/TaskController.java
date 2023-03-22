package com.opgavedb_1.presentation.instructor;

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Tag;
import com.opgavedb_1.entities.objects.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    private TextField createNewTaskInput;

    @FXML
    private ListView selectedTaskFileListView;

    @FXML
    private ListView selectedTaskVideoListView;

    @FXML
    private ComboBox tagMaterialsFilter;

    @FXML
    private ComboBox tagTaskFilter;

    @FXML
    private ListView taskListView;

    @FXML
    private Label taskNameHeader;

    @FXML
    private TextField updateTaskInput;

    @FXML
    private ComboBox updateTaskTag;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    private ArrayList<Task> allTasks = db.getAllTasks();
    private ArrayList<Tag> allTags = db.getAllTags();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showAllTasks();

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

    @FXML
    void addFileToSelectedTask(ActionEvent event) {

    }

    @FXML
    void addVideoToSelectedTask(ActionEvent event) {

    }

    @FXML
    void assignTagToSelectedTask(ActionEvent event) {

    }

    @FXML
    void createNewTask(ActionEvent event) {
        String newTaskTitle = createNewTaskInput.getText();

        if (newTaskTitle.length() > 0) {
            Task task = new Task(newTaskTitle);
            allTasks.add(task);

            db.createNewTask(task);
            //taskListView.getItems().add(newTaskTitle);

            createNewTaskInput.clear();
        }
    }

    @FXML
    void deleteSelectedTask(ActionEvent event) {

    }

    @FXML
    void editSelectedTaskContent(ActionEvent event) {

    }

    @FXML
    void resetTagMaterialsFilter(ActionEvent event) {

    }

    @FXML
    void resetTagTaskFilter(ActionEvent event) {

    }

    @FXML
    void updateSelectedTask(ActionEvent event) {

    }

    @FXML
    void updateTagMaterialsFilter(ActionEvent event) {

    }

    @FXML
    void updateTagTaskFilter(ActionEvent event) {

    }


}




