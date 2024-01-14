package com.example.proiectbdfx1;



import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddColleagueController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;

    @FXML
    public void handleAddTeacher() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String cnp = cnpField.getText();
        String iban = ibanField.getText();
        String department = departmentField.getText();
        String address = addressField.getText();
        String email = emailField.getText();

        addTeacherToDatabase(name, surname, cnp, iban, department, address, email);

        // Close the window after adding the teacher
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void addTeacherToDatabase(String name, String surname, String cnp, String iban, String department, String address, String email) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO facultate.profesor (nume, prenume, CNP, IBAN, departament, adresa, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, cnp);
                preparedStatement.setString(4, iban);
                preparedStatement.setString(5, department);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, email);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception as needed
            showErrorAlert("Error adding teacher to the database.");
        }
    }

    private void showErrorAlert(String message) {
        // Implement error alert logic here
    }
}