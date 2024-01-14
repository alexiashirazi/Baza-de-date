package com.example.proiectbdfx1;
public class UserCredentials {
    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserCredentials.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserCredentials.password = password;
    }
}
