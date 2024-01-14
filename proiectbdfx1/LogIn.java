package com.example.proiectbdfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogIn {
    public LogIn(){

    }

    public TextField getUsername() {
        return username;
    }

    public PasswordField getPassword() {
        return password;
    }

    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password; // Use PasswordField for password

    @FXML
    private Button button;

    @FXML
    private ComboBox<String> comboBox;


    @FXML
    public void initialize() {
        // Add items to the ComboBox
        comboBox.getItems().addAll("Administrator", "Student", "Profesor");

        // Set default selection
        comboBox.getSelectionModel().selectFirst();
    }


    private void switchScene(String fxmlFileName, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 1200, 700));

            newStage.show();

            // Close the current stage if needed
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public void userLogIn(ActionEvent event ) throws IOException{
        checkLogin();

    }


    private void checkLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        String selection = this.comboBox.getValue();

        if (!validateUser(username, selection, password)) {
            wrongLogIn.setText("Invalid data");
        } else {
            UserCredentials.setUsername(username);
            UserCredentials.setPassword(password);
            wrongLogIn.setText("Connected");
            if ("administrator".equalsIgnoreCase(selection)) {
                System.out.println("Switching to administrator.fxml");
                switchScene("administrator.fxml", 1200, 700);
            } else if ("student".equalsIgnoreCase(selection)) {
                System.out.println("Switching to student.fxml");
                switchScene("student.fxml", 1200, 700);
            } else if ("profesor".equalsIgnoreCase(selection)) {
                System.out.println("Switching to profesor.fxml");
                switchScene("profesor.fxml", 1200, 700);
            } else {
                System.out.println("Invalid selection");
            }
        }
    }



    // Validate email, category, and password
    private boolean validateUser(String email, String selectedCategory, String password) {
        // Replace with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try {

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }



        // Check if the email exists in the respective table for the specified category
        String query = "SELECT COUNT(*) FROM ";

        // Choose the appropriate table based on the selected category
        switch (selectedCategory.toLowerCase()) {
            case "student":
                query += "student WHERE email = ? and CNP=?";
                break;
            case "profesor":
                query += "profesor WHERE email =? and CNP=?";
                break;
            case "administrator":
                query += "administrator WHERE email = ? and CNP=?";
                break;
            default:
                return false;
        }

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Email exists for the specified category, now validate the password
                    return true;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception as needed
        }

        return false;
    }


    // Validate the password based on the category



}

