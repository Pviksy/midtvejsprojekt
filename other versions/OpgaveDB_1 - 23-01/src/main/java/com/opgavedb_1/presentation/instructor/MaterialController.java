package com.opgavedb_1.presentation.instructor;

//Mikkel og Jonas

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.entities.objects.Tag;
import com.opgavedb_1.entities.objects.Video;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MaterialController implements Initializable {

    @FXML
    private ListView fileListView;

    @FXML
    private TextField createFileTitleInput;

    @FXML
    private TextField createVideoTitleInput;

    @FXML
    private TextField updateFileTitleInput;

    @FXML
    private TextField updateVideoTitleInput;

    @FXML
    private ComboBox<String> tagFilter;

    @FXML
    private ListView videoListView;

    @FXML
    private TextField videoURLInput;

    @FXML
    private ComboBox<String> addFileTagPicker;

    @FXML
    private ComboBox<String> addVideoTagPicker;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");
    private ArrayList<Tag> allTags = db.getAllTags();
    private ArrayList<com.opgavedb_1.entities.objects.File> allFiles = db.getAllFiles();
    private ArrayList<Video> allVideos = db.getAllVideos();

    //initialiserer tag filteret så det kan resettes af "vis alle" knappen
    private int selectedTagFilterIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //indsætter data i comboboxes
        for (Tag tag : allTags) {
              tagFilter.getItems().add(tag.getTitle());
             addFileTagPicker.getItems().add(tag.getTitle());
            addVideoTagPicker.getItems().add(tag.getTitle());
        }

        showAllFilesAndVideos();
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

    private void showFilesAndVideosByTag(Tag tag) {
        fileListView.getItems().clear();
        videoListView.getItems().clear();

        for (int i = 0; i < db.getFilesByTag(tag).size(); i++) {
            fileListView.getItems().add(db.getFilesByTag(tag).get(i).getTitle());
        }
        for (int i = 0; i < db.getVideosByTag(tag).size(); i++) {
            videoListView.getItems().add(db.getVideosByTag(tag).get(i).getTitle());
        }
    }

    @FXML
    void resetTagFilter(ActionEvent event) {
        selectedTagFilterIndex = -1;
        tagFilter.valueProperty().set(null);
    }

    @FXML
    void updateTagFilter(ActionEvent event) {
        selectedTagFilterIndex = tagFilter.getSelectionModel().getSelectedIndex();

        if (selectedTagFilterIndex == -1)
            showAllFilesAndVideos();
        else {
            Tag selectedTag = allTags.get(selectedTagFilterIndex);

            showFilesAndVideosByTag(selectedTag);
        }
    }

    @FXML
    void addNewFile(ActionEvent event) {
        String title = createFileTitleInput.getText();

        if (title.length() > 0) {

            FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Word Files", "*.docx"));

            File path = fileChooser.showOpenDialog(null);

            if (path != null) {
                String filePath = path.getAbsolutePath();

                db.addFile(filePath, title);

                com.opgavedb_1.entities.objects.File latestFile = db.getLatestFile();

                allFiles.add(latestFile);

                if (selectedTagFilterIndex == -1)
                    fileListView.getItems().add(latestFile.getTitle());
            }
        }

        createFileTitleInput.clear();
    }

    @FXML
    void addNewVideo(ActionEvent event) {
        String title = createVideoTitleInput.getText();
        String URL = videoURLInput.getText();

        if (title.length() > 0 && URL.length() > 0) {
            db.addNewVideo(title, URL);

            Video latestVideo = db.getLatestVideo();

            allVideos.add(latestVideo);

            if (selectedTagFilterIndex == -1)
                videoListView.getItems().add(latestVideo.getTitle());
        }

        createVideoTitleInput.clear();
        videoURLInput.clear();
    }

    @FXML
    void deleteSelectedFile(ActionEvent event) {
        int selectedIndex = fileListView.getSelectionModel().getSelectedIndex();
        fileListView.getItems().remove(selectedIndex);

        com.opgavedb_1.entities.objects.File selectedFile = allFiles.get(selectedIndex);

        allFiles.remove(selectedFile);

        db.deleteFile(selectedFile);
    }

    @FXML
    void deleteSelectedVideo(ActionEvent event) {
        int selectedIndex = videoListView.getSelectionModel().getSelectedIndex();
        videoListView.getItems().remove(selectedIndex);

        Video selectedVideo = allVideos.get(selectedIndex);

        allVideos.remove(selectedVideo);

        db.deleteVideo(selectedVideo);
    }

    @FXML
    void updateSelectedFile(ActionEvent event) {
        String updatedFileTitle = updateFileTitleInput.getText();
        int selectedIndex = fileListView.getSelectionModel().getSelectedIndex();

        if (updatedFileTitle.length() > 0 && selectedIndex >= 0) {
            fileListView.getItems().set(selectedIndex, updatedFileTitle);

            com.opgavedb_1.entities.objects.File selectedFile = allFiles.get(selectedIndex);
            selectedFile.setTitle(updatedFileTitle);
            db.updateFile(selectedFile);

            updateFileTitleInput.clear();
        }
    }

    @FXML
    void updateSelectedVideo(ActionEvent event) {
        String updatedVideoTitle = updateVideoTitleInput.getText();
        int selectedIndex = videoListView.getSelectionModel().getSelectedIndex();

        if (updatedVideoTitle.length() > 0 && selectedIndex >= 0) {
            videoListView.getItems().set(selectedIndex, updatedVideoTitle);

            Video selectedVideo = allVideos.get(selectedIndex);
            selectedVideo.setTitle(updatedVideoTitle);
            db.updateVideo(selectedVideo);

            updateVideoTitleInput.clear();
        }
    }

    @FXML
    void addTagFile(ActionEvent event) {
        int selectedTagIndex = addFileTagPicker.getSelectionModel().getSelectedIndex();
        int selectedFileIndex = fileListView.getSelectionModel().getSelectedIndex();

        if (selectedTagIndex >= 0 && selectedFileIndex >= 0) {

            Tag selectedTag = allTags.get(selectedTagIndex);
            com.opgavedb_1.entities.objects.File selectedFile = allFiles.get(selectedFileIndex);

            db.createTagFile(selectedTag, selectedFile);
        }
    }

    @FXML
    void addTagVideo(ActionEvent event) {
        int selectedTagIndex = addVideoTagPicker.getSelectionModel().getSelectedIndex();
        int selectedVideoIndex = videoListView.getSelectionModel().getSelectedIndex();

        if (selectedTagIndex >= 0 && selectedVideoIndex >= 0) {

            Tag selectedTag = allTags.get(selectedTagIndex);
            System.out.println(selectedTag.getTitle());
            Video selectedVideo = allVideos.get(selectedVideoIndex);
            System.out.println(selectedVideo.getTitle());

            db.createTagVideo(selectedTag, selectedVideo);
        }
    }

}
