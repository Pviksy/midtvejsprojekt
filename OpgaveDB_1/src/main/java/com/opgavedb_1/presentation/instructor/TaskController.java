package com.opgavedb_1.presentation.instructor;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.File;
import com.opgavedb_1.entities.objects.Tag;
import com.opgavedb_1.entities.objects.Task;
import com.opgavedb_1.entities.objects.Video;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/*
Jeg er opmærksom på redundansen imellem denne klasse og MaterialController.
Jeg kunne forestille mig en bedre løsning ved at lave en klasse der overordnet
tager sig af at filtrere i listviews efter tags etc, og nedarve fra den,
men i skrivende stund har jeg ikke tid til at eksperimentere med det,
da jeg føler mig usikker på hvordan det skal fungere.
- Mikkel
 */

public class TaskController implements Initializable {

    @FXML
    private TextField createNewTaskInput;

    @FXML
    private ListView fileListView;

    @FXML
    private ListView videoListView;

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

    //filteredTasks starter med alle, men ændrer sig efter valgt tag i combobox
    private ArrayList<Task> filteredTasks = db.getAllTasks();
    private ArrayList<Tag> allTags = db.getAllTags();
    private ArrayList<File> allFiles = db.getAllFiles();
    private ArrayList<File> filteredFiles = allFiles;
    private ArrayList<File> selectedTaskFiles = new ArrayList<>();
    private ArrayList<Video> allVideos = db.getAllVideos();
    private ArrayList<Video> selectedTaskVideos = new ArrayList<>();
    private ArrayList<Video> filteredVideos = allVideos;


    //initialiserer tag filteret så det kan resettes af "vis alle" knappen
    private int selectedTagTaskFilterIndex;
    private int selectedTagMaterialsFilterIndex;

    //selectedTask ændres enten ved at vælge en task fra listen til højre, eller ved at oprette en ny task i toppen
    private Task selectedTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAllTasks();
        showAllFilesAndVideos();

        for (Tag tag : allTags) {
                 tagTaskFilter.getItems().add(tag.getTitle());
            tagMaterialsFilter.getItems().add(tag.getTitle());
                 updateTaskTag.getItems().add(tag.getTitle());
        }
    }

    private void showAllFilesAndVideos() {
        fileListView.getItems().clear();
        videoListView.getItems().clear();

        for (int i = 0; i < allFiles.size(); i++) {
            fileListView.getItems().add(allFiles.get(i).getTitle());
        }
        for (int i = 0; i < allVideos.size(); i++) {
            videoListView.getItems().add(allVideos.get(i).getTitle());
        }
    }

    private void showAllTasks() {
        taskListView.getItems().clear();

        for (int i = 0; i < filteredTasks.size(); i++) {
            taskListView.getItems().add(filteredTasks.get(i).getTitle());
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
        int selectedFileIndex = fileListView.getSelectionModel().getSelectedIndex();

        if (selectedFileIndex != -1 && selectedTask != null) {
            File selectedFile = filteredFiles.get(selectedFileIndex);

            System.out.println(selectedFile.getId());
            System.out.println(selectedTask.getId());

            db.addFileToTask(selectedTask, selectedFile);
            selectedTaskFiles.add(selectedFile);
            selectedTaskFileListView.getItems().add(selectedFile.getTitle());
        }
    }

    @FXML
    void addVideoToSelectedTask(ActionEvent event) {
        int selectedVideoIndex = videoListView.getSelectionModel().getSelectedIndex();

        if (selectedVideoIndex != -1 && selectedTask != null) {
            Video selectedVideo = filteredVideos.get(selectedVideoIndex);

            db.addVideoToTask(selectedTask, selectedVideo);
            selectedTaskVideos.add(selectedVideo);
            selectedTaskVideoListView.getItems().add(selectedVideo.getTitle());
        }
    }

    @FXML
    void assignTagToSelectedTask(ActionEvent event) {
        int selectedTagIndex = updateTaskTag.getSelectionModel().getSelectedIndex();

        if (selectedTagIndex != -1) {
            Tag selectedTag = allTags.get(selectedTagIndex);

            db.addTagToTask(selectedTask, selectedTag);
        }
    }

    @FXML
    void createNewTask(ActionEvent event) {
        String newTaskTitle = createNewTaskInput.getText();

        if (newTaskTitle.length() > 0) {
            Task newTask = new Task(newTaskTitle);
            filteredTasks.add(newTask);

            db.createNewTask(newTask);
            //taskListView.getItems().add(newTaskTitle);

            createNewTaskInput.clear();

            taskNameHeader.setText(newTask.getTitle());
            taskListView.getItems().clear();
            showAllTasks();

            selectedTask = db.getLatestTask();

            //en ny opgave har ingen filer eller videoer tilknyttet ved oprettelse
            selectedTaskFileListView.getItems().clear();
            selectedTaskVideoListView.getItems().clear();
        }
    }

    @FXML
    void deleteSelectedTask(ActionEvent event) {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        taskListView.getItems().remove(selectedIndex);

        Task selectedTask = filteredTasks.get(selectedIndex);
        filteredTasks.remove(selectedTask);

        db.deleteTask(selectedTask);
    }

    @FXML
    void editSelectedTaskContent(ActionEvent event) {
        selectedTask = selectTask();

        if (selectedTask != null) {
            taskNameHeader.setText(selectedTask.getTitle());

            ArrayList<File> filesForThisTask = db.getFilesByTask(selectedTask);
            ArrayList<Video> videosForThisTask = db.getVideosByTask(selectedTask);

            showSelectedTaskContent(filesForThisTask, videosForThisTask);
        }
    }

    private void showSelectedTaskContent(ArrayList<File> files, ArrayList<Video> videos) {

        selectedTaskFileListView.getItems().clear();
        selectedTaskVideoListView.getItems().clear();

        for (int i = 0; i < files.size(); i++) {
            selectedTaskFileListView.getItems().add(files.get(i).getTitle());
        }
        for (int i = 0; i < videos.size(); i++) {
            selectedTaskVideoListView.getItems().add(videos.get(i).getTitle());
        }

        selectedTaskFiles = files;
        selectedTaskVideos = videos;
    }

    private Task selectTask() {
        int selectedTaskIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedTaskIndex != -1) {
            return filteredTasks.get(selectedTaskIndex);
        } else
            return null;
    }

    @FXML
    void updateSelectedTask(ActionEvent event) {
        String updatedTaskTitle = updateTaskInput.getText();
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();

        //forudsætter at tekstfeltet ikke er tomt, og at der vælges et element fra listview
        if (updatedTaskTitle.length() > 0 && selectedIndex >= 0) {
            taskListView.getItems().set(selectedIndex, updatedTaskTitle);

            Task selectedTask = filteredTasks.get(selectedIndex);
            selectedTask.setTitle(updatedTaskTitle);
            db.updateTaskTitle(selectedTask);

            updateTaskInput.clear();
        }
    }

    @FXML
    void removeFileFromSelectedTask(ActionEvent event) {
        int selectedFileIndex = selectedTaskFileListView.getSelectionModel().getSelectedIndex();

        if (selectedFileIndex != -1) {
            File selectedFile = selectedTaskFiles.get(selectedFileIndex);

            db.deleteTaskFile(selectedTask, selectedFile);

            selectedTaskFileListView.getItems().remove(selectedFileIndex);
        }
    }

    @FXML
    void removeVideoFromSelectedTask(ActionEvent event) {
        int selectedVideoIndex = selectedTaskVideoListView.getSelectionModel().getSelectedIndex();

        if (selectedVideoIndex != -1) {
            Video selectedVideo = selectedTaskVideos.get(selectedVideoIndex);

            db.deleteTaskVideo(selectedTask, selectedVideo);

            selectedTaskVideoListView.getItems().remove(selectedVideoIndex);
        }
    }

//tag task filter
    @FXML
    void resetTagTaskFilter(ActionEvent event) {
        selectedTagTaskFilterIndex = -1;
        tagTaskFilter.valueProperty().set(null);

        filteredTasks = db.getAllTasks();
        showAllTasks();
    }

    @FXML
    void updateTagTaskFilter(ActionEvent event) {
        selectedTagTaskFilterIndex = tagTaskFilter.getSelectionModel().getSelectedIndex();

        if (selectedTagTaskFilterIndex == -1)
            showAllTasks();
        else {
            Tag selectedTag = allTags.get(selectedTagTaskFilterIndex);

            showTasksByTag(selectedTag);
            filteredTasks = db.getTasksByTag(selectedTag);
        }
    }

//tag materials filter
    @FXML
    void resetTagMaterialsFilter(ActionEvent event) {
        selectedTagMaterialsFilterIndex = -1;
        tagMaterialsFilter.valueProperty().set(null);


        filteredFiles = db.getAllFiles();
        filteredVideos = db.getAllVideos();
    }

    @FXML
    void updateTagMaterialsFilter(ActionEvent event) {
        selectedTagMaterialsFilterIndex = tagMaterialsFilter.getSelectionModel().getSelectedIndex();

        if (selectedTagMaterialsFilterIndex == -1)
            showAllFilesAndVideos();
        else {
            Tag selectedTag = allTags.get(selectedTagMaterialsFilterIndex);

            showFilesAndVideosByTag(selectedTag);
        }
    }

    private void showFilesAndVideosByTag(Tag tag) {
        fileListView.getItems().clear();
        videoListView.getItems().clear();

        for (int i = 0; i < db.getFilesByTag(tag).size(); i++) {
            fileListView.getItems().add(db.getFilesByTag(tag).get(i).getTitle());
        }
        for (int i = 0; i < db.getVideosByTag(tag).size(); i++) {
            videoListView.getItems().add(db.getVideosByTag(tag).get(i).getTitle());
        }

        //lister tilpasses filteret så der kan flyttes filer og videoer til selectedTask
        filteredFiles = db.getFilesByTag(tag);
        filteredVideos = db.getVideosByTag(tag);
    }

    @FXML
    public void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("Her på siden kan alt der har med opgaver at gøres.\n" +
                             "\n" +
                             "På listen til højre vælges den ønskede opgave, og der trykkes på ”Rediger den markerede opgave”.\n" +
                             "Herefter vil der kunne tilføjes filer og videoer til denne opgave, i midten af billedet.\n" +
                             "Nederst i billedet kan opgaven tildeles et eller flere tags.\n" +
                             "Øverst i billedet kan der oprettes en ny opgave, og der vil med det samme kunne tilføjes\n" +
                             "materialer og tags til denne.\n");
        alert.showAndWait();
    }

    public void logOut(ActionEvent event) throws IOException {
        URL url = new java.io.File("src/main/resources/com/opgavedb_1/Login/login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}