package com.example.proiectbdfx1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AboutYouTeachers {
    @FXML
    private Label nume;

    @FXML
    private Label prenume;

    @FXML
    private Label adresa;

    @FXML
    private Label nrTelefon;

    @FXML
    private Label IBAN;
    @FXML
    private Label CNP;
    @FXML
    private Label email;
    @FXML
    private Label Departament;

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
        try {
            showInfo();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    private void showInfo() throws IOException, SQLException {
        String Email = UserCredentials.getUsername();
        String cnp = UserCredentials.getPassword();  // Assuming CNP is stored in the password field

        // Assuming you have a method to retrieve user data from the database based on email and CNP
        AboutYouTeachers.UserData userData = getUserData(Email, cnp);

        // Set the data to corresponding labels
        nume.setText(userData.getNume());
        prenume.setText(userData.getPrenume());
        adresa.setText(userData.getAdresa());
        nrTelefon.setText(userData.getNrTelefon());
        IBAN.setText(userData.getIBAN());
        Departament.setText(userData.getDepartament());
        CNP.setText(userData.getCNP());
        email.setText(userData.getEmail());


    }

    private AboutYouTeachers.UserData getUserData(String email, String cnp) throws SQLException {
        // Replace these with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        // Replace this query with your actual query to retrieve user data
        String query = "SELECT nume, prenume, adresa, nrDeTelefon, IBAN, Departament, email, CNP FROM facultate.profesor WHERE (email = ? AND cnp = ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, cnp);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserData(
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("adresa"),
                            resultSet.getString("nrDeTelefon"),
                            resultSet.getString("IBAN"),
                            resultSet.getString("Departament"),
                            resultSet.getString("CNP"),
                            resultSet.getString("email")
                    );
                }
            }
        }

        // Return null if no user data is found
        return null;
    }


    // UserData class with appropriate getters and setters
    private static class UserData {
        private String nume;
        private String prenume;
        private String adresa;
        private String nrTelefon;
        private String IBAN;
        private String Departament;
        private String CNP;
        private String email;

        public UserData(String nume, String prenume, String adresa, String nrTelefon, String IBAN, String Departament, String CNP, String email) {
            this.nume = nume;
            this.prenume = prenume;
            this.adresa = adresa;
            this.nrTelefon = nrTelefon;
            this.IBAN = IBAN;
            this.Departament = Departament;
            this.CNP=CNP;
            this.email=email;
        }

        // Add getters and setters
        public String getNume() {
            return nume;
        }

        public String getPrenume() {
            return prenume;
        }

        public String getAdresa() {
            return adresa;
        }

        public String getNrTelefon() {
            return nrTelefon;
        }

        public String getIBAN() {
            return IBAN;
        }

        public String getDepartament() {
            return Departament;
        }

        public String getCNP() {
            return CNP;
        }

        public String getEmail() {
            return email;
        }
    }

}
