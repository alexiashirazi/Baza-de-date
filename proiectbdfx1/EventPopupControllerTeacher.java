package com.example.proiectbdfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EventPopupControllerTeacher implements Initializable {

    @FXML
    private TextField eventNameField;

    @FXML
    private TextField eventTimeField;

    private int clickedDate;  // Add this variable

    private CalendarControllerTeacher calendarControllerTeacher;

    public void setCalendarController(CalendarControllerTeacher calendarController) {
        this.calendarControllerTeacher = calendarController;
    }




    @FXML
    void handleSaveEvent(ActionEvent event) {
        // Implement the logic to save the event
        // You can access the event name and time using eventNameField.getText() and eventTimeField.getText()
        // Update the calendar data and redraw the calendar
        calendarControllerTeacher.saveEvent(eventNameField.getText(), eventTimeField.getText());
    }

    @FXML
    void handleCancel(ActionEvent event) {
        // Implement the logic to cancel the event creation/editing
        // Close the event popup
        calendarControllerTeacher.closeEventPopup();
    }

    public String geteventNameField(){

        return eventNameField.getText();

    }

    public String geteventTimeField(){

        return eventTimeField.getText();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}