package com.example.proiectbdfx1;



import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudentController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField prenumeField;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField andestudentField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nrDETelefonlField;

    @FXML
    public void handleAddStudent() {
        String name = nameField.getText();
        String surname = prenumeField.getText();
        String cnp = cnpField.getText();
        String iban = ibanField.getText();
        String department = andestudentField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String nrDeTelefon = nrDETelefonlField.getText();

        addStudentToDatabase(name, surname, cnp, iban, department, address, email, nrDeTelefon);

        // Close the window after adding the student
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void addStudentToDatabase(String name, String surname, String cnp, String iban, String department, String address, String email, String nrDeTelefon) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO facultate.student (nume, prenume, CNP, IBAN, anDeStudiu, adresa, email, nrDeTelefon) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, cnp);
                preparedStatement.setString(4, iban);
                preparedStatement.setString(5, department);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, nrDeTelefon);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception as needed
            showErrorAlert("Error adding student to the database.");
        }
    }

    private void showErrorAlert(String message) {
        // Implement error alert logic here
    }
}