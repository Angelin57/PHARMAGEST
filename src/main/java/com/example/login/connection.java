package com.example.login;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {
    public Connection databasing;

    public Connection getConnection(){
        String databaseName = "test";
        String databaseUser = "postgres";
        String databasePassword = "Actros5751";
        String url = "jdbc:postgresql://localhost/" + databaseName;

        try{
            Class.forName("org.postgresql.Driver");
            databasing = DriverManager.getConnection("databaseUser,databasePassword");

        }catch(Exception e) {
            e.printStackTrace();
        }
        return databasing;
    }
}
