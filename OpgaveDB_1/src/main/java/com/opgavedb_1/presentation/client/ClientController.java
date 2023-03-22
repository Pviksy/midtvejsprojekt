package com.opgavedb_1.presentation.client;

//Mikkel

import com.opgavedb_1.data.DataAccessLayer;
import com.opgavedb_1.service.DataSingleton;
import com.opgavedb_1.entities.User;
import com.opgavedb_1.entities.objects.Task;
import com.opgavedb_1.entities.objects.Video;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private ListView selectedTaskFileListView;

    @FXML
    private ListView selectedTaskVideoListView;

    @FXML
    private ListView taskListView;

    @FXML
    private WebView videoPlayer;

    DataAccessLayer db = new DataAccessLayer("OpgaveDB");

    DataSingleton userInformation = DataSingleton.getInstance();

    /*
    Brugeren der er logget ind hentes vha singleton klassen, og gemmes her til brug i diverse metoder i denne klasse
    Alt data der hentes fra databasen er naturligvis det, der er tilknyttet denne bruger.
     */
    private final User thisClient = userInformation.getClient();

    private ArrayList<Task> clientTasks = db.getTasksByClient(thisClient);

    //arraylists med materialer intialiseres tomme, så de kan fyldes efter brugerens input (valgt opgave)
    private ArrayList<com.opgavedb_1.entities.objects.File> taskFiles = new ArrayList<>();
    private ArrayList<Video> taskVideos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showClientTasks();
    }

    private void showClientTasks() {
        //bruger en metode med hensigten at navnet skal beskrive hvad der sker
        for (int i = 0; i < clientTasks.size(); i++) {
            taskListView.getItems().add(clientTasks.get(i).getTitle());
        }
    }

    @FXML
    private void goToTask(ActionEvent event) {
        int selectedTaskIndex = taskListView.getSelectionModel().getSelectedIndex();

        Task selectedTask = clientTasks.get(selectedTaskIndex);

        taskFiles = db.getFilesByTask(selectedTask);
        taskVideos = db.getVideosByTask(selectedTask);

        showTaskMaterials(taskFiles, taskVideos);
    }

    private void showTaskMaterials(ArrayList<com.opgavedb_1.entities.objects.File> files, ArrayList<Video> videos) {
        selectedTaskFileListView.getItems().clear();
        for (int i = 0; i < files.size(); i++) {
            selectedTaskFileListView.getItems().add(files.get(i).getTitle());
        }

        selectedTaskVideoListView.getItems().clear();
        for (int i = 0; i < videos.size(); i++) {
            selectedTaskVideoListView.getItems().add(videos.get(i).getTitle());
        }
    }

    @FXML
    void downloadSelectedFile(ActionEvent event) throws IOException {
        int selectedFileIndex = selectedTaskFileListView.getSelectionModel().getSelectedIndex();

        if (selectedFileIndex != -1) {
            com.opgavedb_1.entities.objects.File selectedFile = taskFiles.get(selectedFileIndex);

            DirectoryChooser dc = new DirectoryChooser();

            //her vælges af brugeren, hvor på deres computer den valgte fil skal gemmes
            File selectedDirectory = dc.showDialog(null);

            if (selectedDirectory == null) {
                //ingen path valgt - brugeren lukkede dialogboksen, hvor path kunne vælges
            } else {
                /*
                path opbygges med den returnerede File fra DirectoryChooser instansen.
                Der kaldes getAbsolutePath(), og den valgte fils titel hentes fra vores eget File objekt. Filformatet tilføjes til slut til pathen
                Systemet er afgrænset til kun at understøtte word-dokumenter, men kunne sagtens laves om til at understøtte flere formater i fremtiden
                */
                File path = new File(selectedDirectory.getAbsolutePath() + "\\" + selectedFile.getTitle() + ".docx");

                FileOutputStream os = new FileOutputStream(path);

                //skriver filen fra et bytearray på den valgte path
                os.write(db.getFile(selectedFile));
                os.close();
            }
        }
    }

    @FXML
    void playSelectedVideo(ActionEvent event) {
        int selectedVideoIndex = selectedTaskVideoListView.getSelectionModel().getSelectedIndex();

        if (selectedVideoIndex != -1) {
            Video selectedVideo = taskVideos.get(selectedVideoIndex);

            String URL = processURL(selectedVideo.getURL());

            WebEngine engine = videoPlayer.getEngine();
            engine.load(URL);
        }
    }

    //Casper
    private String processURL(String URL) {

        //webview viser ikke kun video - dette indlejrer videoen så netop kun den vises
        String master = URL;
        String target = "watch?v=";
        String replacement = "embed/";
        String processed = master.replace(target, replacement);

        return processed;
    }

    @FXML
    public void helpDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hjælp");
        alert.setContentText("Øverst til højre ses en liste over dine opgaver.\n" +
                             "Ved at vælge en opgave fra denne liste og trykke på ”Vis opgave” knappen,\n" +
                             "vil alle opgavens tilknyttede materialer indsættes i de to nederste lister.\n" +
                             "\n" +
                             "I listen over filer, kan filerne hentes ned på din computer ved at trykke på den ønskede fil,\n" +
                             "og herefter ”Hent fil” knappen.\n" +
                             "\n" +
                             "I listen over videoer, vil den valgte video afspilles direkte her i programmet, ved at trykke\n" +
                             "på den, efterfulgt af ”Afspil video” knappen.\n");
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
