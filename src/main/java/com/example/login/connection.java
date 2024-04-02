package com.example.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection databasing = null;
        String databaseName = "pharmagest";
        String databaseUser = "postgres";
        String databasePassword = "5751";
        String url = "jdbc:postgresql://localhost:5432/" + databaseName;

        try {
            Class.forName("org.postgresql.Driver");
            databasing = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return databasing;
    }

    // Ajoutez cette méthode pour exécuter les requêtes de mise à jour
    public static void dbExecuteUpdate(String updateStmt) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = new connection().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.println("Error occurred while executing update: " + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
