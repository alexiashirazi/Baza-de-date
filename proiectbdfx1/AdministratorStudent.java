package com.example.proiectbdfx1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class AdministratorStudent {

    @FXML
    private Button Student;
    @FXML
    private Button Profesor;
    @FXML
    private Button SuperAdmin;
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
    private TableColumn<StudentTeacher, String> nrDeTelefon;
    @FXML
    private TableColumn<StudentTeacher, String> CNP;
    @FXML
    private TableColumn<StudentTeacher, String> IBAN;
    @FXML
    private TableColumn<StudentTeacher, String> adresa;
    @FXML
    private TableColumn<StudentTeacher, String> email;

    @FXML
    private Button deleteButton;

    @FXML
    private void handleDelete() {
        StudentTeacher selectedColleague = tabel.getSelectionModel().getSelectedItem();

        if (selectedColleague != null) {
            if (showConfirmationAlert("Are you sure you want to delete this colleague?")) {
                deleteStudent(selectedColleague);
                tabel.getItems().remove(selectedColleague);
            }
        } else {
            showErrorAlert("Please select a colleague to delete.");
        }
    }

    @FXML
    private Button addButton;

    @FXML
    public void handleAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addStudent.fxml"));
            Parent root = fxmlLoader.load();

            // Pass the main controller to the child controller
            AddStudentController addStudentController = fxmlLoader.getController();

            // Create a new Stage for the "Add Student" view
            Stage addStudentStage = new Stage();
            addStudentStage.setScene(new Scene(root));
            addStudentStage.setTitle("Add Student");

            // Set the modality to APPLICATION_MODAL to make it a pop-up window
            addStudentStage.initModality(Modality.APPLICATION_MODAL);

            // Show the "Add Student" window and wait for it to be closed before proceeding
            addStudentStage.showAndWait();

            // Reload the table after adding a student if searchBar is not empty
            if (!searchBar.getText().isEmpty()) {
                tabel.setItems(getStudents(Integer.valueOf(searchBar.getText())));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        } catch (NumberFormatException e) {
            showErrorAlert("Please enter a valid integer in the search bar.");
            e.printStackTrace();
        }
    }



    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }



    @FXML
    private Button cauta;

    @FXML
    private TextField searchBar;

    public void handleCauta() {
        tabel.setItems(getStudents(Integer.valueOf(searchBar.getText())));
    }


    @FXML
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("prenume"));
        anDeStudiu.setCellValueFactory(new PropertyValueFactory<StudentTeacher, Integer>("anDeStudiu"));
        CNP.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("CNP"));
        IBAN.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("IBAN"));
        adresa.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("adresa"));
        email.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("email"));
        nrDeTelefon.setCellValueFactory(new PropertyValueFactory<StudentTeacher, String>("nrDeTelefon"));




        tabel.setItems(getStudents(-1));
        tabel.setEditable(true);

        nume.setEditable(true);
        prenume.setEditable(true);
        anDeStudiu.setEditable(true);
        CNP.setEditable(true);
        IBAN.setEditable(true);
        adresa.setEditable(true);
        email.setEditable(true);
        nrDeTelefon.setEditable(true);

        tabel.setItems(getStudents(-1));
        setCellValueFactories();


    }
    private void setCellValueFactories() {
        nume.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setNume(event.getNewValue());
           updateDatabase(student);
        });

        prenume.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setPrenume(event.getNewValue());
            updateDatabase(student);
        });
        anDeStudiu.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setAnDeStudiu(String.valueOf(event.getNewValue()));
            updateDatabase(student);
        });
        CNP.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setCNP(event.getNewValue());
            updateDatabase(student);
        });
        IBAN.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setIBAN(event.getNewValue());
            updateDatabase(student);
        });
        adresa.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setAdresa(event.getNewValue());
            updateDatabase(student);
        });
        email.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setEmail(event.getNewValue());
            updateDatabase(student);
        });
        nrDeTelefon.setOnEditCommit(event -> {
            StudentTeacher student = event.getRowValue();
            student.setNrDeTelefon(event.getNewValue());
            updateDatabase(student);
        });

    }

    private void updateDatabase(StudentTeacher student) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "UPDATE facultate.student SET nume=?, prenume=?, anDeStudiu=?, CNP=?, IBAN=?, adresa=?, email=?, nrDeTelefon=? WHERE id=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setString(1, student.getNume());
                prep.setString(2, student.getPrenume());
                prep.setString(3, student.getAnDeStudiu());
                prep.setString(4, student.getCNP());
                prep.setString(5, student.getIBAN());
                prep.setString(6, student.getAdresa());
                prep.setString(7, student.getEmail());
                prep.setString(8, student.getNrDeTelefon());



                // Assuming you have methods to get student and subject IDs
                int studentId = getStudentIDByCNP(student.getCNP());


               // prep.setInt(5, studentId);


                prep.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }
    }

    public static Integer getStudentIDByCNP(String CNP) {
        int id = 0;

        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "20072003Robert";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sqlQuery = "SELECT id FROM facultate.student WHERE CNP=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setInt(1, id);
                prep.setString(2,CNP);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }

        return id;
    }
    @FXML
    private void handleLogOutAdministrator() {
        switchScene("hello-view.fxml", 500, 400, LogOut);
    }

    @FXML
    private void handleStudentAdministrator() {
        switchScene("studentAdministrator.fxml", 1200, 700, Student);
    }

    @FXML
    private void handleProfesorAdministrator() {
        switchScene("profesorAdministartor.fxml", 1200, 700, Profesor);
    }

    @FXML
    private void handleSuperAdminAdministrator() {
        if (isSuperAdmin()) {
            switchScene("superAdmin.fxml", 1200, 700, SuperAdmin);
        } else {
            showErrorAlert("Not SuperAdmin");
        }

    }

    public AdministratorStudent() {

    }


    private void switchScene(String fxmlFileName, int width, int height, Button button) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.show();

            // Close the current stage if needed
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    boolean isSuperAdmin() {
        String email = UserCredentials.getUsername();
        String cnp = UserCredentials.getPassword();

        try {
            String url = "jdbc:mysql://localhost:3306/facultate";
            String dbUsername = "root";
            String dbPassword = "Behbahan2018$";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);


            // Query to retrieve superadministrator_bool for the given email and cnp
            String query = "SELECT superadministrator_bool FROM facultate.administrator WHERE email = ? AND cnp = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, cnp);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getBoolean("superadministrator_bool");
                    } else {
                        // User not found in the database
                        System.out.println("User not found in the database.");
                        return false; // Or handle it according to your requirements
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQL exception as needed
                return false; // Or handle it according to your requirements
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other exceptions as needed
            return false; // Or handle it according to your requirements
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Apply custom styles from the CSS file
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/org/proiect/proiectjavafxbd/custom-alert.css").toExternalForm());
        dialogPane.getStyleClass().add("error-alert");

        alert.showAndWait();
    }






    public ObservableList<StudentTeacher> getStudents(Integer anDeStudiu) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<StudentTeacher> students = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            PreparedStatement prep;
            if (anDeStudiu != -1) {
                prep = connection.prepareStatement("SELECT nume, prenume, anDeStudiu, CNP, adresa, nrDeTelefon, email, IBAN FROM facultate.student WHERE student.anDeStudiu = ? AND email != ?");
                prep.setInt(1, anDeStudiu);
                prep.setString(2, UserCredentials.getUsername());

            } else {
                prep = connection.prepareStatement("SELECT nume, prenume, anDeStudiu, CNP, adresa, nrDeTelefon, email, IBAN FROM facultate.student WHERE email != ?");
                prep.setString(1, UserCredentials.getUsername());
            }

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                StudentTeacher student = new StudentTeacher(
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("anDeStudiu"),
                        rs.getString("CNP"),
                        rs.getString("adresa"),
                        rs.getString("nrDeTelefon"),
                        rs.getString("email"),
                        rs.getString("IBAN")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }

        return students;
    }

    private void deleteStudent(StudentTeacher student) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "DELETE FROM facultate.student WHERE CNP = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, student.getCNP());
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Student deleted successfully from the database.");
                } else {
                    System.out.println("No rows were deleted. Student with CNP: " + student.getCNP() + " not found.");
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error deleting student from the database.");
            e.printStackTrace();
        }
    }




}