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

public class StudentsTeacher {




        @FXML
        private Button Calendar;

        @FXML
        private Button Students;

        @FXML
        private Button Colleagues;

        @FXML
        private Button Gradebook;

        @FXML
        private Button AboutYou;

        @FXML
        private Button LogOut;

        @FXML
        private TableView<StudentTeacher> tabel;

        @FXML
        private TableColumn<StudentTeacher, String> nume;

        @FXML
        private TableColumn<StudentTeacher, String> prenume;

        @FXML
        private TableColumn<StudentTeacher, Integer> anDeStudiu;

        @FXML
        private Button cauta;

        @FXML
        private TextField searchBar;
        @FXML
        public void handleCauta() {
            tabel.setItems(getCollegues(Integer.valueOf(searchBar.getText())));
        }



        @FXML
        private void handleCalendarButton() {
            switchScene("Calendar.fxml", 1200, 700, Calendar);
        }

        @FXML
        private void handleStudentsButton() {
            switchScene("Students_profesor.fxml", 1200, 700, Students);
        }

        @FXML
        private void handleColleaguesButton() {
            switchScene("Colleagues.fxml", 1200, 700, Colleagues);
        }

        @FXML
        private void handleGradebookButton() {
            switchScene("GradebookProfesor.fxml", 1200, 700, Gradebook);
        }

        @FXML
        private void handleAboutYouButton() {
            switchScene("AboutYouProfesor.fxml", 1200, 700, AboutYou);
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
            nume.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("nume"));
            prenume.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("prenume"));
            anDeStudiu.setCellValueFactory(new PropertyValueFactory<StudentTeacher, Integer>("anDeStudiu"));
            tabel.setItems(getCollegues(-1));

        }



        public ObservableList<StudentTeacher> getCollegues(Integer anDeStudiu) {
            // Replace with your actual database connection details
            String url = "jdbc:mysql://localhost:3306/facultate";
            String dbUsername = "root";
            String dbPassword = "Behbahan2018$";
            ObservableList<StudentTeacher> colegi = FXCollections.observableArrayList();

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
                    StudentTeacher coleg = new StudentTeacher(rs.getString("nume"), rs.getString("prenume"), rs.getString("anDeStudiu"));
                    colegi.add(coleg);
                }
            } catch (SQLException e) {
                System.err.println("Error connecting to the database:");
                e.printStackTrace();
            }

            return colegi;
        }




    }


