package com.example.login;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {

    public Connection getConnection(){
        Connection databasing;
        String databaseName = "test";
        String databaseUser = "postgres";
        String databasePassword = "Actros5751";
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;
        databasing = null;
        try{
            Class.forName("org.postgresql.Driver");
            databasing = DriverManager.getConnection(url,databaseUser,databasePassword);


        }catch(Exception e) {
            e.printStackTrace();
        }
        return databasing;
    }
}
