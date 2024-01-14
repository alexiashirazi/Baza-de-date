package com.example.proiectbdfx1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class colegiStudent {

    @FXML
    private Button Calendar;
    @FXML
    private Button Students;
    @FXML
    private Button Teachers;
    @FXML
    private Button Gradebook;
    @FXML
    private Button AboutYou;
    @FXML
    private Button Chat;
    @FXML
    private Button Logout;


    @FXML
    private void handleCalendarButton() {
        switchScene("Calendar_students.fxml", 1200, 700, Calendar);
    }

    @FXML
    private void handleStudentsButton() {
        switchScene("Students_students.fxml", 1200, 700, Students);
    }

    @FXML
    private void handleTeachersButton() {
        switchScene("Students_profesor_pt_student.fxml", 1200, 700, Teachers);
    }

    @FXML
    private void handleGradebookButton() {
        switchScene("Gradebook_students.fxml", 1200, 700, Gradebook);
    }

    @FXML
    private void handleAboutYouButton() {
        switchScene("AboutYou_students.fxml", 1200, 700, AboutYou);
    }


    @FXML
    private void handleChatButton() {
        switchScene("Chat_students.fxml", 1200, 700, Chat);
    }


    @FXML
    private void handleLogOut() {
        switchScene("hello-view.fxml", 500, 400, Logout);
    }

    private void switchScene(String fxmlFileName, int width, int height, Button buton) {
        try {
            System.out.println(getClass().getResource(fxmlFileName));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));

            newStage.show();

            // Close the current stage if needed
            Stage currentStage = (Stage) buton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    private TableView<colegStudent> tabel;

    @FXML
    private TableColumn<colegStudent, String> nume;

    @FXML
    private TableColumn<colegStudent, String> prenume;

    @FXML
    private TableColumn<colegStudent, Integer> anDeStudiu;

    @FXML
    private Button cauta;

    @FXML
    private TextField searchBar;
    @FXML
    public void handleCauta() {
        tabel.setItems(getCollegues(Integer.valueOf(searchBar.getText())));
    }

    @FXML
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<colegStudent, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<colegStudent, String>("prenume"));
        anDeStudiu.setCellValueFactory(new PropertyValueFactory<colegStudent, Integer>("anDeStudiu"));
        tabel.setItems(getCollegues(-1));

    }

    public ObservableList<colegStudent> getCollegues(Integer anDeStudiu) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<colegStudent> colegi = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(UserCredentials.getUsername());
            PreparedStatement prep = connection.prepareStatement("select nume, prenume, anDeStudiu from facultate.student where student.anDeStudiu = ? and email!=? ");
            if (anDeStudiu != -1){
                prep = connection.prepareStatement("select nume, prenume, anDeStudiu from facultate.student where student.anDeStudiu = ? and email!=? ");
                prep.setInt(1, anDeStudiu);
                prep.setString(2, UserCredentials.getUsername());
            }else{
                prep = connection.prepareStatement("SELECT nume, prenume, anDeStudiu from facultate.student where email != ?");
                prep.setString(1, UserCredentials.getUsername());
            }
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                colegStudent coleg = new colegStudent(rs.getString("nume"), rs.getString("prenume"), rs.getString("anDeStudiu"));
                colegi.add(coleg);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }

        return colegi;
    }


}
