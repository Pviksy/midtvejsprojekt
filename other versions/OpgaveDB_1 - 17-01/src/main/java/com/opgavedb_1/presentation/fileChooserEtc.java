package com.opgavedb_1.presentation;

import com.opgavedb_1.data.DataAccessLayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class fileChooserEtc implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private Button selectFile;

    @FXML
    private WebView webPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    void saveFile(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();

        File selectedDirectory = dc.showDialog(null);


        if(selectedDirectory == null){
            //No Directory selected
        }else{
            System.out.println(selectedDirectory.getAbsolutePath());

            //gem path som herunder
            File path = new File(selectedDirectory.getAbsolutePath() + "filename" + ".docx");

            /*
            lav fileoutputstream til path og inkluder navnet, som sandsynligvis
            bliver navnet på opgaven, og hvis det kun bliver .docx filer så
            er det let at tilføje som illustreret herover

            FileOutputStream os = new FileOutputStream(path);
            os.write(db.getFile());
            os.close();

             */
        }
    }

    @FXML
    public void selectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Word Files", "*.docx"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            DataAccessLayer db = new DataAccessLayer("FileDB");

            db.addFile(file.getAbsolutePath());



        }


        //int BUFFER = 4096;
        //String output_path = "C:\\Users\\pvikp\\Dropbox\\#Programmering\\Databaser\\test_output.docx";
        //DataAccessLayer db = new DataAccessLayer("FileDB");
        //String fileBinary = db.getFiles();
        //try (
        //    OutputStream is = new OutputStream(output_path);
        //    ) {
        //    byte[] imageBuffer = new byte[BUFFER];
        //    int bytesRead = -1;
        //}
        //catch (IOException e) {
        //    e.printStackTrace();
        //}
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String master = "https://www.youtube.com/watch?v=NPqtL1dtrlA";
        String target = "watch?v=";
        String replacement = "embed/";
        String processed = master.replace(target, replacement);
        //WebEngine engine = webPlayer.getEngine();
        //engine.load(processed);

        //DataAccessLayer db = new DataAccessLayer("FileDB");
        //db.getFiles();



    }
}
