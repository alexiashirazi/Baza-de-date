package com.example.proiectbdfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    private final Map<ZonedDateTime, List<CalendarActivity>> calendarActivitiesMap = new HashMap<>();
    private ZonedDateTime dateFocus;
    private ZonedDateTime today;

    public int dayClick = -1;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private GridPane calendar;

    private Stage eventPopupStage;
    private EventPopupController eventPopupController;

    @FXML
    private Button Calendar;
    @FXML
    private Button Students;
    @FXML
    private Button Teachers;
    @FXML
    private Button Gradebook;
    @FXML
    private Button AboutYou;
    @FXML
    private Button Chat;
    @FXML
    private Button Logout;


    @FXML
    private void handleCalendarButton() {
        switchScene("Calendar_students.fxml", 1200, 700, Calendar);
    }

    @FXML
    private void handleStudentsButton() {
        switchScene("Students_students.fxml", 1200, 700, Students);
    }

    @FXML
    private void handleTeachersButton() {
        switchScene("Students_profesor_pt_student.fxml", 1200, 700, Teachers);
    }

    @FXML
    private void handleGradebookButton() {
        switchScene("Gradebook_students.fxml", 1200, 700, Gradebook);
    }

    @FXML
    private void handleAboutYouButton() {
        switchScene("AboutYou_students.fxml", 1200, 700, AboutYou);
    }


    @FXML
    private void handleChatButton() {
        switchScene("Chat_students.fxml", 1200, 700, Chat);
    }


    @FXML
    private void handleLogOut() {
        switchScene("hello-view.fxml", 500, 400, Logout);
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

    private void updateCalendar() {
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        updateCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        updateCalendar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    public void saveEvent(String eventName, String eventTime) {
        closeEventPopup();

        if (dayClick == -1) {
            System.err.println("No Click");
            return;
        }

        int hour;
        try {
            hour = Integer.parseInt(eventTime);
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer format for event time");
            System.err.println(eventTime);
            return;
        }

        ZonedDateTime time = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), dayClick, 0, 0, 0, 0, dateFocus.getZone());
        ZonedDateTime time2 = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), dayClick, hour, 0, 0, 0, dateFocus.getZone());
        CalendarActivity newEvent = new CalendarActivity(time2, eventName, 0);

        List<CalendarActivity> eventsForDay = calendarActivitiesMap.get(time);
        if (eventsForDay == null) {
            eventsForDay = new ArrayList<>();
            eventsForDay.add(newEvent);
            calendarActivitiesMap.put(time, eventsForDay);
        } else {
            eventsForDay.add(newEvent);
            calendarActivitiesMap.put(time, eventsForDay);
        }


        dateFocus = time; // Update dateFocus to the selected day
        updateCalendar();
        closeEventPopup();
    }

    public void showEventPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPopup.fxml"));
            Parent root = loader.load();

            eventPopupStage = new Stage();
            eventPopupStage.initModality(Modality.APPLICATION_MODAL);
            eventPopupStage.setTitle("Event Details");
            eventPopupStage.setScene(new Scene(root));

            eventPopupController = loader.getController();
            eventPopupController.setCalendarController(this);

            eventPopupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeEventPopup() {
        if (eventPopupStage != null) {
            eventPopupStage.close();
        }
    }

    private void drawCalendar() {
        calendar.getChildren().clear();
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        ZonedDateTime startOfMonth = dateFocus.withDayOfMonth(1);
        startOfMonth.withHour(0).withMinute(0).withSecond(0).withNano(0);
        int dateOffset = startOfMonth.getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);

                stackPane.setOnMouseClicked(event -> {
                    dayClick = calculatedDate - dateOffset;
                    showEventPopup();
                });

                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    int lastDayOfMonth = startOfMonth.plusMonths(1).minusDays(1).getDayOfMonth();

                    if (currentDate <= lastDayOfMonth) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        ZonedDateTime currentDay = startOfMonth.plusDays(currentDate - 1);
                        List<CalendarActivity> calendarActivities = calendarActivitiesMap.get(currentDay);

                        System.err.println(currentDay);

                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }

                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.add(stackPane, j, i);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }
}