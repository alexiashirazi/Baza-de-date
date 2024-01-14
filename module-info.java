module com.example.proiectbdfx1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proiectbdfx1 to javafx.fxml;
    exports com.example.proiectbdfx1;
}