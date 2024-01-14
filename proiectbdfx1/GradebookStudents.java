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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class GradebookStudents {

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
    private TableView<Nota> tabel;

    @FXML
    private TableColumn<Nota, String> materie_nume;

    @FXML
    private TableColumn<Nota, Integer> notaCurs;

    @FXML
    private TableColumn<Nota, Integer> notaSeminar;

    @FXML
    private TableColumn<Nota, Integer> notaLaborator;
    @FXML
    private TableColumn<Nota, Integer> notaFinala;

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
    public void initialize() {
        materie_nume.setCellValueFactory(new PropertyValueFactory<Nota, String>("materie_nume"));
        notaSeminar.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("notaSeminar"));
        notaCurs.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("notaCurs"));
        notaLaborator.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("notaLaborator"));
        notaFinala.setCellValueFactory(new PropertyValueFactory<Nota, Integer>("notaFinala"));

      tabel.setItems(getGrade());

    }
    public static int getId(String CNP) {
        int id = -1; // Initialize id to a default value (e.g., -1) in case no valid ID is found
        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Use a try-with-resources block to automatically close the resources
            PreparedStatement prep = connection.prepareStatement("SELECT id FROM facultate.student WHERE CNP=?");
            prep.setString(1, UserCredentials.getPassword()); // Use the provided CNP parameter, not UserCredentials.getPassword()

            try (ResultSet resultSet = prep.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id"); // Retrieve the ID from the result set
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }

        return id;
    }



    public ObservableList<Nota> getGrade() {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<Nota> note = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(getId(UserCredentials.getPassword()));
            System.out.println(UserCredentials.getUsername());
            CallableStatement callableStatement = connection.prepareCall("{call GetStudentGrades(?)}");
            callableStatement.setInt(1,getId(UserCredentials.getPassword())); // Replace 123 with the actual student ID

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    Nota nota = new Nota(resultSet.getString("materie_nume"),
                            resultSet.getString("notaCurs"),
                            resultSet.getString("notaSeminar"),
                            resultSet.getString("notaLaborator"),
                            resultSet.getString("notaFinala"));
                    note.add(nota);

                    // Access the actual column names using the text property
                    System.out.println("Materie Nume: " + materie_nume.getText());
                    System.out.println("Nota Curs: " + notaCurs.getText());
                    System.out.println("Nota Seminar: " + notaSeminar.getText());
                    System.out.println("Nota Laborator: " + notaLaborator.getText());
                    System.out.println("Nota Finala: " + notaFinala.getText());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
        return note;
    }


}

