package com.example.proiectbdfx1;

import java.sql.*;

public class CallProcedure {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/facultate";
        String user = "root";
        String password = "Behbahan2018$";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            // Prepare the stored procedure call
            CallableStatement callableStatement = connection.prepareCall("{call GetStudentGrades(?)}");

            // Set input parameter
            callableStatement.setInt(1, 123); // Replace 123 with the actual student ID

            // Execute the stored procedure
            boolean hasResults = callableStatement.execute();

            // Process the results if there are any
            if (hasResults) {
                ResultSet resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    String materieNume = resultSet.getString("materie_nume");
                    int notaCurs = resultSet.getInt("notaCurs");
                    int notaSeminar = resultSet.getInt("notaSeminar");
                    int notaLaborator = resultSet.getInt("notaLaborator");
                    double notaFinala = resultSet.getDouble("notaFinala");

                    // Do something with the retrieved data
                    System.out.println("Materie Nume: " + materieNume);
                    System.out.println("Nota Curs: " + notaCurs);
                    System.out.println("Nota Seminar: " + notaSeminar);
                    System.out.println("Nota Laborator: " + notaLaborator);
                    System.out.println("Nota Finala: " + notaFinala);
                }
                resultSet.close();
            }

            // Close the resources
            callableStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
