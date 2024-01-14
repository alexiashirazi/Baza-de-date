package com.example.proiectbdfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Profesor {
    public Profesor() {

    }

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




}
