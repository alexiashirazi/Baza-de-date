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

public class AdministratorTeacher {

    @FXML
    private Button Student;
    @FXML
    private Button Profesor;
    @FXML
    private Button SuperAdmin;
    @FXML
    private Button LogOut;


    @FXML
    private TableView<Colleague> tabel;

    @FXML
    private TableColumn<Colleague, String> nume;

    @FXML
    private TableColumn<Colleague, String> prenume;
    @FXML
    private TableColumn<Colleague, String> CNP;
    @FXML
    private TableColumn<Colleague, String> IBAN;
    @FXML
    private TableColumn<Colleague, String> adresa;
    @FXML
    private TableColumn<Colleague, String> email;
    @FXML
    private TableColumn<Colleague, String> Departament;

    @FXML
    private Button deleteButton;

    @FXML
    private void handleDelete() {
        Colleague selectedColleague = tabel.getSelectionModel().getSelectedItem();

        if (selectedColleague != null) {
            if (showConfirmationAlert("Are you sure you want to delete this colleague?")) {
                deleteColleague(selectedColleague);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addColleague.fxml"));
            Parent root = fxmlLoader.load();

            // Pass the main controller to the child controller
            AddColleagueController addColleagueController = fxmlLoader.getController();

            // Create a new Stage for the "Add Colleague" view
            Stage addColleagueStage = new Stage();
            addColleagueStage.setScene(new Scene(root));
            addColleagueStage.setTitle("Add Colleague");

            // Set the modality to APPLICATION_MODAL to make it a pop-up window
            addColleagueStage.initModality(Modality.APPLICATION_MODAL);

            // Show the "Add Colleague" window and wait for it to be closed before proceeding
            addColleagueStage.showAndWait();

            // Reload the table after adding a teacher
            tabel.setItems(getCollegues(""));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
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

    @FXML
    public void handleCauta() {
        tabel.setItems(getCollegues(searchBar.getText()));
    }


    @FXML
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<Colleague, String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Colleague, String>("prenume"));
        CNP.setCellValueFactory(new PropertyValueFactory<Colleague, String>("CNP"));
        IBAN.setCellValueFactory(new PropertyValueFactory<Colleague, String>("IBAN"));
        Departament.setCellValueFactory(new PropertyValueFactory<Colleague, String>("Departament"));
        adresa.setCellValueFactory(new PropertyValueFactory<Colleague, String>("adresa"));
        email.setCellValueFactory(new PropertyValueFactory<Colleague, String>("email"));

        tabel.setItems(getCollegues(""));

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
        switchScene("profesorAdministrator.fxml", 1200, 700, Profesor);
    }

    @FXML
    private void handleSuperAdminAdministrator() {
        if (isSuperAdmin()) {
            switchScene("superAdmin.fxml", 1200, 700, SuperAdmin);
        } else {
            showErrorAlert("Not SuperAdmin");
        }

    }

    public AdministratorTeacher() {

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
        dialogPane.getStylesheets().add(getClass().getResource("/com/example/proiectbdfx1/custom-alert.css").toExternalForm());
        dialogPane.getStyleClass().add("error-alert");

        alert.showAndWait();
    }


    public ObservableList<Colleague> getCollegues(String CNP) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";
        ObservableList<Colleague> colegi = FXCollections.observableArrayList();

        try {

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println(UserCredentials.getUsername());
            PreparedStatement prep = connection.prepareStatement("select nume, prenume, CNP, IBAN, departament, adresa, email from facultate.profesor where CNP like ? and email!=? ");
            prep.setString(1, '%' + CNP + '%');
            prep.setString(2, UserCredentials.getUsername());
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Colleague coleg = new Colleague(rs.getString("nume"), rs.getString("prenume"), rs.getString("CNP"), rs.getString("IBAN"), rs.getString("Departament"), rs.getString("adresa"), rs.getString("email"));
                colegi.add(coleg);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }

        return colegi;

    }

    private void deleteColleague(Colleague colleague) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "DELETE FROM facultate.profesor WHERE CNP = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, colleague.getCNP());
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Colleague deleted successfully from the database.");
                } else {
                    System.out.println("No rows were deleted. Colleague with CNP: " + colleague.getCNP() + " not found.");
                }
            }
        } catch (SQLException e) {
            showErrorAlert("Error deleting colleague from the database.");
            e.printStackTrace();
        }
    }




}