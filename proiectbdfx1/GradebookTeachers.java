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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.*;

import static com.example.proiectbdfx1.GradebookStudents.getId;

public class GradebookTeachers {
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
    private TableView<NotaPentruProfesor> tabel;

    @FXML
    private TableColumn<NotaPentruProfesor, String> numeStudent;

    @FXML
    private TableColumn<NotaPentruProfesor, String> Materie;

    @FXML
    private TableColumn<NotaPentruProfesor, Integer> notaCurs;

    @FXML
    private TableColumn<NotaPentruProfesor, Integer> notaSeminar;

    @FXML
    private TableColumn<NotaPentruProfesor, Integer> notaLaborator;
    @FXML
    private TableColumn<NotaPentruProfesor, Integer> notaFinala;


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

    public void initialize() {


            numeStudent.setCellValueFactory(new PropertyValueFactory<>("numeStudent"));
            Materie.setCellValueFactory(new PropertyValueFactory<>("Materie"));
            notaCurs.setCellValueFactory(new PropertyValueFactory<>("notaCurs"));
            notaSeminar.setCellValueFactory(new PropertyValueFactory<>("notaSeminar"));
            notaLaborator.setCellValueFactory(new PropertyValueFactory<>("notaLaborator"));
            notaFinala.setCellValueFactory(new PropertyValueFactory<>("notaFinala"));

            // Assuming you have a method like getNote in your code
            tabel.setItems(getNote());
        tabel.setEditable(true);


        notaCurs.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        notaSeminar.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        notaLaborator.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        notaFinala.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tabel.setEditable(true);

        // Set columns to be editable
        notaCurs.setEditable(true);
        notaSeminar.setEditable(true);
        notaLaborator.setEditable(true);
        notaFinala.setEditable(true);

        // Assuming you have a method like getNote in your code
        tabel.setItems(getNote());

        // Commit the changes when editing is finished
        setCellValueFactories();

    }

    private void setCellValueFactories() {
        notaCurs.setOnEditCommit(event -> {
            NotaPentruProfesor nota = event.getRowValue();
            nota.setNotaCurs(event.getNewValue());
            updateDatabase(nota);
        });

        notaSeminar.setOnEditCommit(event -> {
            NotaPentruProfesor nota = event.getRowValue();
            nota.setNotaSeminar(event.getNewValue());
            updateDatabase(nota);
        });

        notaLaborator.setOnEditCommit(event -> {
            NotaPentruProfesor nota = event.getRowValue();
            nota.setNotaLaborator(event.getNewValue());
            updateDatabase(nota);
        });

        notaFinala.setOnEditCommit(event -> {
            NotaPentruProfesor nota = event.getRowValue();
            nota.setNotaFinala(event.getNewValue());
            updateDatabase(nota);
        });
    }

    private void updateDatabase(NotaPentruProfesor nota) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "UPDATE facultate.catalog SET notaCurs=?, notaSeminar=?, notaLaborator=?, notaFinala=? WHERE id_student=? AND id_materie=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setInt(1, nota.getNotaCurs());
                prep.setInt(2, nota.getNotaSeminar());
                prep.setInt(3, nota.getNotaLaborator());
                prep.setInt(4, nota.getNotaFinala());

                // Assuming you have methods to get student and subject IDs
                int studentId = getStudentIdByName(nota.getNumeStudent());
                int subjectId = getSubjectIdByName(nota.getMaterie());

                prep.setInt(5, studentId);
                prep.setInt(6, subjectId);

                prep.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }
    }
    private int getStudentIdByName(String studentName) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "SELECT id FROM facultate.student WHERE nume=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setString(1, studentName);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }

        // Return a default value or handle the case when the student ID is not found
        return -1;
    }

    private int getSubjectIdByName(String subjectName) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "SELECT id FROM facultate.materie WHERE nume=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setString(1, subjectName);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }

        // Return a default value or handle the case when the subject ID is not found
        return -1;
    }


    public static String getStudentNameByID(int ID) {
        String name = null;

        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sqlQuery = "SELECT nume FROM facultate.student WHERE id=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setInt(1, ID);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        name = resultSet.getString("nume");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }

        return name;
    }

    public int get_prof_id(){

        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<NotaPentruProfesor> note = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            PreparedStatement prep = connection.prepareStatement("SELECT id from facultate.profesor where email = ? and cnp = ?");
            prep.setString(1, UserCredentials.getUsername());
            prep.setString(2, UserCredentials.getPassword());
            ResultSet rs = prep.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }
            return -1;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public ObservableList<NotaPentruProfesor> getNote() {

        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<NotaPentruProfesor> note = FXCollections.observableArrayList();

        try {
            int prof_id = get_prof_id();
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(getId(UserCredentials.getPassword()));
            System.out.println(UserCredentials.getUsername());

            PreparedStatement prep = connection.prepareStatement("SELECT facultate.catalog.notaCurs, facultate.catalog.notaSeminar, facultate.catalog.notaFinala, facultate.catalog.notaLaborator, facultate.materie.nume, student.nume as nume_student from facultate.catalog join facultate.activitate_didactica on facultate.activitate_didactica.idProfesor = ? join facultate.materie on facultate.materie.id = facultate.catalog.id_materie join facultate.student on facultate.student.id = facultate.catalog.id_student where facultate.catalog.id_materie = facultate.activitate_didactica.idMaterie ");
            prep.setInt(1, prof_id);
            ResultSet resultSet = prep.executeQuery();

            if (true) {

                while (resultSet.next()) {
                    NotaPentruProfesor nota = new NotaPentruProfesor(resultSet.getString("nume"),
                            resultSet.getString("nume_student"),
                            resultSet.getInt("notaCurs"),
                            resultSet.getInt("notaSeminar"),
                            resultSet.getInt("notaLaborator"), resultSet.getInt("notaFinala"));
                    note.add(nota);


                }
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
        return note;

    }
}