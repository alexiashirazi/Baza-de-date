package com.example.proiectbdfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatStudents {

    @FXML
    private Button Calendar;

    @FXML
    private Button Students;


    @FXML
    private Button Gradebook;

    @FXML
    private Button AboutYou;

    @FXML
    private Button LogOut;


    @FXML
    private Button Teachers;
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
    @FXML
    private ListView<String> chatListView;

    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextField messageTextField;

    private List<Message> messages; // You should have a Message class

    @FXML
    private ListView<String> messageListView;

    @FXML
    private Label chatRoomLabel;

    @FXML
    private void initialize() {
        messages = getChatMessagesFromDatabase(); // Implement this method to get messages from your database
        displayMessages();
    }
    @FXML
    private void messageClicked(MouseEvent event) {
        String selectedMessage = messageListView.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            chatRoomLabel.setText(selectedMessage);
        }
    }
    @FXML
    private void sendMessage(ActionEvent event) {
        String newMessage = messageTextField.getText();

        // Append the new message to the messageTextArea
        messageTextArea.appendText("\nYou: " + newMessage);

        // Save the new message to the database
        saveMessageToDatabase(newMessage);

        // Clear the messageTextField after sending the message
        messageTextField.clear();
    }

    private void saveMessageToDatabase(String messageText) {
        // Connect to your database and insert the new message
        // Example:
        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String insertQuery = "INSERT INTO facultate.chat (text, Idgrup, IdStudent) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set the values for the parameters
                preparedStatement.setString(1, messageText);
                preparedStatement.setInt(2, getStudentIdByEmail(UserCredentials.getUsername())/* Provide the actual group ID */);
                preparedStatement.setInt(3, getStudentIdGroup(getStudentIdByEmail(UserCredentials.getUsername())) /* Provide the actual student ID */);

                // Execute the insert query
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getStudentIdGroup(int idStudent){
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$"; try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "SELECT id_grup FROM facultate.inscris_grup WHERE id_student=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setInt(1, idStudent);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id_grup");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }


        // Return a default value or handle the case when the student ID is not found
        return -1;
    }

    private int getStudentIdByEmail(String email) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String dbUsername = "root";
        String dbPassword = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sqlQuery = "SELECT id FROM facultate.student WHERE email=?";
            try (PreparedStatement prep = connection.prepareStatement(sqlQuery)) {
                prep.setString(1, email);

                try (ResultSet resultSet = prep.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query:");
            e.printStackTrace();
        }

        // Return a default value or handle the case when the student ID is not found
        return -1;
    }
    private void displayMessages() {
        chatListView.getItems().clear();
        for (Message message : messages) {
            chatListView.getItems().add(message.toString());
        }
    }

    // Implement this method to retrieve messages from your database
    private List<Message> getChatMessagesFromDatabase() {
        List<Message> messages = new ArrayList<>();
        // Connect to your database and retrieve messages
        // Example:
        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "Behbahan2018$";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM facultate.chat");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");
                int groupId = resultSet.getInt("Idgrup");
                int studentId = resultSet.getInt("IdStudent");

                // Create Message objects and add to the list
                Message message = new Message(id, text, groupId, studentId);
                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
