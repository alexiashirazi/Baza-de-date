package com.example.proiectbdfx1;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class addAdministrator {

    @FXML
    private TextField numeField;

    @FXML
    private TextField prenumeField;

    @FXML
    private TextField adresaField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField IBANField;

    @FXML
    private TextField CNPField;

    @FXML
    private Button saveButton;

    @FXML
    private void handleSave() {
        // Validate input fields (you can add more sophisticated validation)
        if (validateFields()) {
            // Get the input values
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            String adresa = adresaField.getText();
            String email = emailField.getText();
            String IBAN = IBANField.getText();
            String CNP = CNPField.getText();

            // Insert new administrator into the database
            addAdministratorToDatabase(nume, prenume, adresa, email, IBAN, CNP);
            // Close the add administrator window
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }


    private boolean validateFields() {
        // Add your validation logic here
        // Return true if the input is valid, otherwise show an error message and return false
        return true;
    }

    private boolean addAdministratorToDatabase(String nume, String prenume, String adresa, String email, String IBAN, String CNP) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            if (connection != null) {
                System.out.println("Connected to the database.");

                String query = "INSERT INTO facultate.administrator (nume, prenume, adresa, email, IBAN, CNP) VALUES (?, ?, ?, ?, ?, ?)";
                System.out.println("SQL Query: " + query);  // Print SQL query for debugging

                try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, nume);
                    preparedStatement.setString(2, prenume);
                    preparedStatement.setString(3, adresa);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, IBAN);
                    preparedStatement.setString(6, CNP);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Administrator added successfully to the database.");

                        // Retrieve generated key if needed
                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                System.out.println("Generated Key: " + generatedKeys.getLong(1));
                            }
                        }

                        showInformationAlert("Administrator added successfully to the database.");
                        return true;
                    } else {
                        System.out.println("No rows were added. Error adding administrator to the database.");
                        showErrorAlert("Error adding administrator to the database.");
                        return false;
                    }
                }
            } else {
                showErrorAlert("Failed to connect to the database.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error connecting to the database.");
            return false;
        }
    }



    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}