package lvf.listviewfilter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField filterField;

    @FXML
    private ListView<String> listView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> list = FXCollections.observableArrayList();

        Task task1 = new Task("Følelseshus", "Følelser");
        Task task2 = new Task("Armbøjninger", "Fysisk træning");
        Task task3 = new Task("Pladder", "Følelser");
        Task task4 = new Task("Løb en tur", "Fysisk træning");

        list.addAll(task1.getTitle(), task2.getTitle(), task3.getTitle(), task4.getTitle());



        listView.getItems().addAll(list);



    }



    SortedList<String> filter(ObservableList<String> list, TextField filterField) {
        FilteredList<String> filteredList = new FilteredList<>(list, b -> true);

        String blah = "";

        //alt dette opdateres hver gang filteret ændres
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(String -> {

                //hvis søgefeltet er tomt, vises alle klienter
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //det her ikke indflydelse om indtastningen består af store/små bogstaver
                String lowerCaseFilter = newValue.toLowerCase();

                //logikken der fortæller om nogle af felterne indeholder det indtastede
                if (blah.toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (blah.toLowerCase().contains(lowerCaseFilter))
                    return true;
                else
                    return false;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<String> sortedList = new SortedList<>(filteredList);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        //sortedList.comparatorProperty().bind(clientTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        return sortedList;

    }

}
