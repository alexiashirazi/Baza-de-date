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

public class ColleaguesStudent {
    @FXML
    private Button Calendar;

    @FXML
    private Button Students;


    @FXML
    private Button Gradebook;

    @FXML
    private Button AboutYou;

    @FXML
    private Button LogOut;


    @FXML
    private Button Teachers;
    @FXML
    private Button Chat;

    @FXML
    private TableView<ColleagueStudent> tabel;

    @FXML
    private TableColumn<ColleagueStudent, String> nume;

    @FXML
    private TableColumn<ColleagueStudent, String> prenume;

    @FXML
    private TableColumn<ColleagueStudent, String> Departament;

    @FXML
    private Button cauta;

    @FXML
    private TextField searchBar;
    @FXML
    public void handleCauta() {
        tabel.setItems(getCollegues(searchBar.getText()));
        System.out.println((searchBar.getText()));
    }




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
        switchScene("hello-view.fxml", 500, 400, LogOut);
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
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<ColleagueStudent, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<ColleagueStudent, String>("prenume"));
        Departament.setCellValueFactory(new PropertyValueFactory<ColleagueStudent, String>("Departament"));
        tabel.setItems(getCollegues(""));

    }



    public ObservableList<ColleagueStudent> getCollegues(String Departament) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<ColleagueStudent> colegi = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(UserCredentials.getUsername());
            PreparedStatement prep;

            prep = connection.prepareStatement("select nume, prenume, Departament from facultate.profesor where profesor.Departament like ? and email != ?");
            prep.setString(1, '%' + Departament + '%');
            prep.setString(2, UserCredentials.getUsername());

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                ColleagueStudent coleg = new ColleagueStudent(rs.getString("nume"), rs.getString("prenume"), rs.getString("Departament"));
                colegi.add(coleg);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }

        return colegi;
    }




}
