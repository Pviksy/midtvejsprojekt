module lvf.listviewfilter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens lvf.listviewfilter to javafx.fxml;
    exports lvf.listviewfilter;
}